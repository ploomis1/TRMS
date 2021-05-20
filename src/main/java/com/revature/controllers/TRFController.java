package com.revature.controllers;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.TRMS.Driver;
import com.revature.beans.TRF;
import com.revature.beans.TRF.Event;
import com.revature.beans.User;
import com.revature.beans.User.Role;
import com.revature.data.factory.BeanFactory;
import com.revature.data.factory.Log;
import com.revature.services.TRFService;
import com.revature.services.TRFServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;
import com.revature.utils.S3Util;

import io.javalin.http.Context;

@Log
public class TRFController {
	private static TRFService ts = (TRFService) BeanFactory.getFactory().get(TRFService.class, TRFServiceImpl.class);
	private static UserService us = (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class);
	private static final Logger log = LogManager.getLogger(TRFController.class);
	/*
	 * print out form info needed/format
	 * need to have routs to submit form
	 * Update form(Benco,Employee)
	 * Approve form(only list of approved people during the process)
	 * Deny form(only people during process)
	 * View form
	 * request additional info
	 */

	
	/*
	 * things needed when filling out form
	 * 1. formID?
	 * 2. userID
	 * 3. EventDate
	 * 4. time
	 * 5. location
	 * 6. description
	 * 7. cost
	 * 8. gradingFormat
	 * 9. type of event
	 * 10. work related justification
	 * 11. optional- attachments, emails and approval type
	 * 12. work time going to be missed
	 */
	public static void submitForm(Context ctx) {
		User u = ctx.sessionAttribute("User");
		if(u==null) {
			//log.trace("User not logged in");
			ctx.status(403);
			return;
		}
		TRF form= new TRF();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		form.setFormID(Integer.valueOf(ctx.formParam("formID")));
		form.setUserID(u.getId());
		form.setEventDate(LocalDate.parse(ctx.formParam("start date"),formatter));
		form.setTime(ctx.formParam("time"));
		form.setLocation(ctx.formParam("location"));
		form.setDescription(ctx.formParam("descriptiom"));
		Double c =Double.valueOf(ctx.formParam("cost"));
		form.setCost(c);
		Event e =Event.valueOf(ctx.formParam("event type"));
		form.setEvent(e);
		form.setJustification(ctx.formParam("justification"));
		form.setHoursMissed(Integer.valueOf(ctx.formParam("hours missed")));
		try {
		form.setPassingGrade(Integer.valueOf(ctx.formParam("passing grade")));
		}catch (Exception ex) {
			
		}
		//then need to check for attachments, maybe add after form added?  
		TRF forms=ts.addForm(form);
		if(forms!=null) {
			ctx.json(forms);
			ctx.status(201);
			return;
		}
		
		ctx.status(500);
	}
	
	public static void viewForm(Context ctx) {
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.formParam(":formID"));
		
		if(u==null||!ts.reviewOrUser(formid, u.getId())||ts.isDeniedOrApproved(formid)) {
			ctx.status(403);
			return;
		}
		ctx.json(ts.getForm(formid));
	}
	/*
	 * nees to get formid
	 * needs to verify benco has approved
	 * needs to verify user passed
	 * needs to verify presentation passed?
	 * needs to check if denied
	 *
	 */
	public static void approveReim(Context ctx) {
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(u==null||!ts.reviewOrUser(formid, u.getId())||ts.isDeniedOrApproved(formid)||!ts.passed(formid)) {
			ctx.status(403);
			return;
		}
		ts.approveReim(formid);
	}
	
	public static void cancelRequest(Context ctx) {
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(u==null||!ts.checkUser(u.getId(), formid)) {
			ctx.status(403);
			return;
		}
		ts.cancelRequest(formid);
	}
	/*
	 * checks if user is logged in
	 * calls service layer to get forms that have status associated with userID
	 * gets list back and prints to front end as json
	 */
	public static void viewFormsAwaiting(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");//need to verify that user is in list
	//	ctx.json("hello");
		if(u==null) {
			//log.trace("User not logged in");
			ctx.status(403);
			return;
		}

		ctx.json(ts.getFormsForReview(u.getId()));
	}
	/*
	 * get path param formid
	 * verify user is logged in, form is associated with user, form is not denied
	 * gets presentation from front end and sends to S3
	 * sends name of presentation to service layer to add to Form
	 */
	public static void uploadPresentation(Context ctx) {//need to verify id matches trf user//if denied dont show
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		
		if(u==null||!ts.checkUser(u.getId(),formid)||ts.isDeniedOrApproved(formid)) {
			ctx.status(403);
			return;
		}
		String name = ctx.header("filename");
		//String id = ctx.header("atID");
		byte[] bytes = ctx.bodyAsBytes();
		try {
			S3Util.getInstance().uploadToBucket(name, bytes);
			ts.addPresentation(formid, name);
		} catch(Exception e) {
			ctx.status(500);
		}
	}
	/*
	 * get path param formid
	 * verifies user is logged in, user is a reviewer or creater of form, verifies form is not denied
	 * calls service layer to get name of file associated with form,and sends data on who is viewing pres
	 * calls s3 to get presentation and returns to front end
	 */
	public static void viewPresentation(Context ctx) {//need to also send user info in to make sure all people have view presentation//if denied dont show
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(u==null||!ts.reviewOrUser(formid, u.getId())||ts.isDeniedOrApproved(formid)) {
			ctx.status(403);
			return;
		}
		String name = ts.getPresentation(formid,u.getId());
		if(name.equals(null)) {
			ctx.status(503);
			return;
		}
		try {
			InputStream s = S3Util.getInstance().getObject(name);
			ts.viewPresentation(formid,u.getId());
			ctx.json(name);
			ctx.result(s);
		} catch(Exception e) {
			ctx.status(500);
		}
	}
	
	public static void presentationPassed(Context ctx) {
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(u==null||!ts.reviewOrUser(formid, u.getId())||ts.isDeniedOrApproved(formid)||!ts.presentationViewed(formid)) {
			ctx.status(403);
			return;
		}
		boolean passed = Boolean.valueOf(ctx.formParam("passed"));
		ts.presentationPassedOrDenied(formid, passed);
	}
	
	/*
	 * get path param formid
	 * verifies user is logged in, user is creator of form, and form is not denied
	 * gets grade from front end
	 * sends grade to service layer to add to form
	 */
	public static void uploadGrade(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(u==null||!ts.checkUser(u.getId(),formid)||ts.isDenied(formid)) {
			ctx.status(403);
			return;
		}
		ts.addGrade(formid, Integer.valueOf(ctx.formParam("grade")));
		
		
	}
	/*
	 * get path param formid
	 * verifies user is logged in, verifies user is a reviewer, verifies role is Benco
	 * gets amount to change reim to
	 * gets reason for change, maybe not this
	 */
	public static void updateReimbursement(Context ctx) {//need to check if benco//if denied dont show
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(u==null||!u.getRole().equals(Role.BENCO)||!ts.getReviewers(formid, u.getId())||ts.isDeniedOrApproved(formid)) {
			System.out.println(u.getRole().equals(Role.BENCO)+" "+ts.getReviewers(formid, u.getId())+" "+ts.isDeniedOrApproved(formid));
			ctx.status(403);
			return;
		}
		String reason =ctx.formParam("reason");
		ts.updateReimburement(formid,Double.valueOf(ctx.formParam("amount")),reason);
	}
	/*
	 * get path param formid
	 * verify user is logged in, user is a reviewer, form is not denied
	 * sends info to Service layer
	 */
	public static void approveForm(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");
		
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		//need to call service to check if user is in form reviewers
		if(!ts.statusUserIsUser(formid, u.getId())||ts.isDeniedOrApproved(formid)||u==null) {
			ctx.status(403);
			return;
		}
		ts.approveForm(formid, u.getId(), u.getRole());
		
	}
	/*
	 * get path param formid
	 * verifies user is logged in, user is a reviewer, form is not denied already
	 * gets reason of denial
	 * sends info to service layer
	 */
	public static void denyForm(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");

		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(!ts.statusUserIsUser(formid, u.getId())||ts.isDeniedOrApproved(formid)||u==null) {
			ctx.status(403);
			return;
		}
		String reason = ctx.sessionAttribute("denialReason");
		ts.denyForm(formid, reason);
		
	}
	/*
	 * get path param formid
	 * verifies user is logged in, form creater is user, form is not denied
	 * gets email and sends to aws
	 * sends approval type and filename to service layer
	 */
	public static void uploadApprovalEmail(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(u==null||!ts.checkUser(u.getId(),formid)||ts.isDeniedOrApproved(formid)) {
			ctx.status(403);
			return;
		}
		//check user is only one adding if(!ts.checkUser(u.getID(),formid))	
		String name = ctx.header("filename");
		//need to get type of approval
		Role type=Role.valueOf(ctx.header("approvalType"));
		byte[] bytes = ctx.bodyAsBytes();
		try {
			S3Util.getInstance().uploadToBucket(name, bytes);
			ts.addEmail(formid, name, type);
			//call service to update info
		} catch(Exception e) {
			ctx.status(500);
		}
		ctx.status(204);
	}
	/*
	 * get path param formid
	 * verifies user is logged in, user is form creator or reviewer, form is not denied
	 * gets name from service layer
	 * gets email form s3
	 */
	public static void getApprovalEmail(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(!ts.reviewOrUser(formid, u.getId())||ts.isDeniedOrApproved(formid)||u==null) {
			ctx.status(403);
			return;
		}
		//String name = ctx.pathParam(":email");
		String name = ts.getEmail(formid);
		if(name.equals(null)) {
			ctx.status(503);
			return;
		}
		try {
			InputStream s = S3Util.getInstance().getObject(name);
			ctx.result(s);
		} catch(Exception e) {
			ctx.status(500);
		}
	}
	/*
	 * verifies user is logged in
	 * gets forms created by user
	 * sends forms to front-end
	 */
	public static void viewMyForms(Context ctx) {
		User u = ctx.sessionAttribute("User");//need to verify that user is in list
		if(u==null) {
			//log.trace("User not logged in");
			ctx.status(403);
			return;
		}
		ctx.json(ts.getMyForms(u.getId()));
	}
	/*
	 * get path param formid
	 * verifies user is logged in, user is form creator or reviewer, form is not denied
	 * gets name of user and request
	 * sends info to service layer
	 */
	public static void requestInformation(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(!ts.reviewOrUser(formid, u.getId())||ts.isDeniedOrApproved(formid)||u==null) {
			ctx.status(403);
			return;
		}
		String info = new StringBuilder(u.getName()).append(" : ").append(ctx.formParam("request")).toString();
		ts.addInformation(formid, info);
	}
	/*
	 * get path param formid
	 * verifies user is logged in, user is form creator or reviewer, form is not denied
	 * gets request info from service layer
	 * prints to front end as json
	 */
	public static void getRequestInformation(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		//need to think about this, reviewers and user can view infor
		
		if(!ts.reviewOrUser(formid, u.getId())||ts.isDeniedOrApproved(formid)||u==null) {
			ctx.status(403);
			return;
		}
		ctx.json(ts.getInformation(formid));
	}
	/*
	 * get path param formid
	 * verifies user is logged in, form creater is user, form is not denied
	 * gets gf from front end
	 * sends to s3
	 * sends name and formid to service layer
	 */
	public static void uploadGradingFormat(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(u==null||!ts.checkUser(u.getId(),formid)||ts.isDeniedOrApproved(formid)) {
			ctx.status(403);
			return;
		}
		
		String name = ctx.header("filename");
		//String id = ctx.header("atID");
		byte[] bytes = ctx.bodyAsBytes();
		try {
			S3Util.getInstance().uploadToBucket(name, bytes);
			ts.addGradingFormat(formid, name);
		} catch(Exception e) {
			ctx.status(500);
		}
	}
	/*
	 * get path param formid
	 * verifies user is logged in, user is form creator or reviewer, form is not denied
	 * gets name of gf from service layer
	 * calls s3 to get gf
	 * puts to front end
	 */
	public static void getGradingFormat(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(!ts.reviewOrUser(formid, u.getId())||ts.isDeniedOrApproved(formid)||u==null) {
			ctx.status(403);
			return;
		}
		//String name = ctx.pathParam(":gradingFormat");
		String name = ts.getGradingFormat(formid);
		if(name.equals(null)) {
			ctx.status(503);
			return;
		}
		try {
			InputStream s = S3Util.getInstance().getObject(name);
			ctx.result(s);
		} catch(Exception e) {
			ctx.status(500);
		}
		
	}
	/*
	 * get path param formid
	 * verifies user is logged in, form creater is user, form is not denied
	 * gets attachment
	 * sends to s3
	 * sends name to service layer
	 */
	
	public static void uploadAttachments(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");
		///need to see file extension and add to email or attachments
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		if(u==null||!ts.checkUser(u.getId(),formid)||ts.isDeniedOrApproved(formid)) {
			ctx.status(403);
			return;
		}
		String name = ctx.header("filename");
		//String id = ctx.header("atID");
		byte[] bytes = ctx.bodyAsBytes();
		try {
			S3Util.getInstance().uploadToBucket(name, bytes);
		} catch(Exception e) {
			ctx.status(500);
		}
		
		ctx.status(204);
	}
	/*
	 * get path param formid
	 * verifies user is logged in, user is form creator or reviewer, form is not denied
	 * gets name of attachments from pathParam
	 * calls aws to get attachments
	 * puts to front end
	 */
	public static void getAttachments(Context ctx) {//if denied dont show
		User u = ctx.sessionAttribute("User");
		int formid = Integer.valueOf(ctx.pathParam(":formID"));
		//maybe user can view these outside of my forms? maybe need to do
		//getreviewrs && checkUser?
		if(!ts.reviewOrUser(formid, u.getId())||ts.isDeniedOrApproved(formid)||u==null) {
			ctx.status(403);
			return;
		}
		String name = ctx.pathParam(":attachment");//need to think if this is what i want
		try {
			InputStream s = S3Util.getInstance().getObject(name);
			ctx.result(s);
		} catch(Exception e) {
			ctx.status(500);
		}
	}
	

}
