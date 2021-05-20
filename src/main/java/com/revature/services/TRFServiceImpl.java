package com.revature.services;

import java.io.InputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Days;

import com.revature.beans.TRF;
import com.revature.beans.User;
import com.revature.beans.User.Role;
import com.revature.data.TRFDAO;
import com.revature.data.TRFDAOImpl;
import com.revature.data.factory.BeanFactory;
import com.revature.data.factory.Log;
import com.revature.utils.S3Util;
import java.time.temporal.ChronoUnit;

@Log
public class TRFServiceImpl implements TRFService {
	
	
	private  TRFDAO td; //= (TRFDAO) BeanFactory.getFactory().get(TRFDAO.class, TRFDAOImpl.class);
	private  UserService us; //= (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class);
	
	
	private static final Logger log = LogManager.getLogger(TRFServiceImpl.class);
		
	public TRFServiceImpl() {
		td =(TRFDAO) BeanFactory.getFactory().get(TRFDAO.class, TRFDAOImpl.class);
		us = (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class);
	}
	
	public TRFServiceImpl(TRFDAOImpl tdao, UserServiceImpl user) {
		td=tdao;
		us=user;
		//TRFServiceImpl.td=tdao;
	}
	
	@Override
	public TRF addForm(TRF form) {
		List<Integer> review =us.getReviewers(form.getUserID());
		form.setReviewers(review);
		User u = us.getUser(form.getUserID());
		int superid= us.getSupervisor(form.getUserID());
		User superv = us.getUser(superid);
		if(superv.getRole().equals(Role.HEAD)) {
			form.setSuperIsHead(true);
			form.setSupervisorID(superid);
			form.setBencoID(review.get(1));
		}else if(superv.getRole().equals(Role.CEO)) {
			form.setSuperIsCeo(true);
			form.setSupervisorID(superid);
			form.setBencoID(review.get(1));
		}else {
			form.setSupervisorID(superid);
			form.setHeadID(review.get(1));
			form.setBencoID(review.get(2));
		}
		form.setStatus(superid);
		
		if(form.getPassingGrade()==0) {//probably break on this, actually worked!!
			form.setPassingGrade(form.getPassingGradeDefault());
		}
		
		//need to think more on this, check yearly amount of user?
		Double reim = form.getEvent().getPercent() * form.getCost();
		if(reim>1000.00) {
			reim=1000.00;
		}
		Double available = 1000.00-(u.getPendingReim()+u.getReimbursementTotalForYear());//this did not work
		form.setAvailableReim(available);
		form.setProjectedReim(Math.min(reim,available));
		System.out.println("reim: "+reim+"   available: "+available+"  math.min: "+Math.min(reim,available)+" availableBefore: "+u.getPendingReim()+u.getReimbursementTotalForYear());
		u.setPendingReim(u.getPendingReim()+form.getProjectedReim());
		us.updateUser(u);
		LocalDate localDate = LocalDate.now();
		form.setSubmittedDate(localDate);
		LocalDate temp = localDate.plusDays(14);//if given date is later, positive: if given is earlier negative
		if(form.getEventDate().compareTo(temp)<0) {
			form.setUrgent(true);
		}else {
			form.setUrgent(false);
		}
		try {
			td.addForm(form);
			return form;
		}catch (Exception e) {
			return form;
		}
		
	}


	@Override
	public TRF getForm(int id) {
		try {
		return td.getForm(id);
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean updateForm(TRF form) {
		try {
			td.updateForm(form);
			return true;
		}catch (Exception e) {
			return false;
		}
		//return true;
	}
	
	@Override
	public boolean getReviewers(int formID,int userID) {//not necessary
		 TRF form =getForm(formID);
		List<Integer> review =form.getReviewers();
		for(int in:review) {
			if(in==userID) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean approveForm(int formid, int id, Role role) {//most though process in this
		TRF form = getForm(formid);//maybe get form from controller
		
		if(form.getSupervisorID()==id) {
			form.setSupervisorApproval(true);
			System.out.println();
			form.setSupervisorApprovalDate(LocalDate.now().toString());
			//Role.valueOf(form.getApprovalRole()).equals(Role.HEAD)
			if(form.isSuperIsHead()||form.isSuperIsCeo()|| Role.HEAD.toString().equals(form.getApprovalRole())) {
				form.setStatus(form.getBencoID());
			}else {
				form.setStatus(form.getHeadID());
			}
		}
		if(form.getHeadID()==id) {
			form.setHeadApproval(true);
			form.setHeadApprovalDate(LocalDate.now().toString());
			form.setStatus(form.getBencoID());
		}
		if(form.getBencoID()==id) {
			form.setBencoApproval(true);
			form.setBencoApprovalDate(LocalDate.now().toString());
			form.setStatus(-1);
			form.setAllApproved(true);
		}
		
		if(updateForm(form)) {
			return true;
		}		
		return false;
	}

	@Override
	public boolean denyForm(int formID, String denialReason) {//only for super?
		TRF form = getForm(formID);
		form.setDenied(true);
		form.setDenialReason(denialReason);
		User u= us.getUser(form.getFormID());
		if(form.getReimAmount()==0) {
			u.setPendingReim(u.getPendingReim()-form.getProjectedReim());
		}else {
			u.setPendingReim(u.getPendingReim()-form.getReimAmount());
		}
		
		if(updateForm(form)) {
			us.updateUser(u);
			return true;
		}
		return false;
	}

	@Override
	public boolean addPresentation(int formID, String presentationID) {
		TRF form = getForm(formID);
		form.setPresentationID(presentationID);
		form.setPresentation(true);
		if(updateForm(form)) {
		return true;
		}
		return false;
	}

	@Override
	public String getPresentation(int formID, int userID) {
		TRF form = getForm(formID);
		if(form.getPresentationID().equals(null)) {
			return null;
		}
		List <Integer> viewed=form.getViewed();
		viewed.add(userID);
		return form.getPresentationID();
		
	}

	@Override
	public boolean addAttachment(int formID, String attachment) {
		TRF form = getForm(formID);
		List<String> attach =form.getAttachments();
		attach.add(attachment);
			if(updateForm(form)) {
				return true;
			}

		return false;
	}

	@Override
	public String getAttachment(int attachmentID) {//controller doing this dude
		try {
			
		}catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public boolean addEmail(int formid, String emailID, Role approvalRole) {
		TRF form = getForm(formid);
		form.setEmailID(emailID);
		form.setApprovalEmail(true);
		form.setApprovalRole(approvalRole.toString());
		if(approvalRole.equals(Role.SUPERVISOR)||approvalRole.equals(Role.CEO)) {
			if(form.isSuperIsHead()||form.isSuperIsCeo()) {
				form.setStatus(form.getBencoID());
				form.setSupervisorApproval(true);
			}else {
			form.setStatus(form.getHeadID());
			form.setSupervisorApproval(true);
			}
		}else if(approvalRole.equals(Role.HEAD)) {
			if(form.isSuperIsHead()||form.isSuperIsCeo()) {
				form.setStatus(form.getBencoID());
				form.setHeadApproval(true);
			}
		}
		
		//need to then change who views forms bc of email
		if(updateForm(form)) {
			return true;
		}
		
		return false;
	}

	@Override
	public String getEmail(int formID) {
		TRF form = getForm(formID);
		if(form.getEmailID().equals(null)) {
			return null;
		}
		return form.getEmailID();
	}

	@Override
	public boolean updateReimburement(int formID,double reim,String reason) {//work on
		TRF form = getForm(formID);
		form.setReimAmount(reim);
		form.setAmountChanged(true);
		if(form.getAvailableReim()<reim) {
			form.setExceedingFunds(true);
			form.setFundsExceeded(reason);
		}
		User u = us.getUser(form.getUserID());
		u.setPendingReim(reim);
		us.updateUser(u);
		if(updateForm(form)) {
			return true;
		}
		try {
			
		}catch (Exception e) {
			
		}
		return false;
	}

	@Override
	public int getReimburesment(int formID) {
		try {
			
		}catch (Exception e) {
			
		}
		return 0;
	}

	@Override
	public boolean addInformation(int formID, String informationID) {
		TRF form = getForm(formID);
		form.setRequestForInfo(true);
		List <String> info = form.getInformation();
		info.add(informationID);
		if(updateForm(form)) {
			return true;
		}

		return false;
	}

	@Override
	public List<String> getInformation(int formID) {
		TRF form = getForm(formID);
		return form.getInformation();
	}

	@Override
	public boolean addGradingFormat(int formID, String gradingFormatID) {
		TRF form = getForm(formID);
		form.setGradingFormatid(gradingFormatID);
		updateForm(form);
		return false;
	}

	@Override
	public String getGradingFormat(int formID) {
		TRF form = getForm(formID);
		return form.getGradingFormatid();
	}

	@Override
	public void checkTime() {
		//this needs some thought
		//maybe just send in formID?
		LocalDate date =LocalDate.now();
		//first get all forms
		//check to see if super has approved, if not then check if submitted date is < 3 days await from today, if so auto-approve supervisor
		//check to see if headisSuper or CEO, if not check super approval date, if <3 days form today, auto-approve head
		//then check if head has approved, if so check date of super approval, if <3 days, send email to benco head
		
		try {
			List<TRF> forms= td.getForms().stream().filter((t)-> !t.isApproved()).filter((t)->!t.isDenied()).collect(Collectors.toList());
			
			List<TRF> noApproval = forms.stream().filter((t)-> t.getSupervisorApprovalDate().equals(null)).collect(Collectors.toList());
			
			List<TRF> superApprovedforms=forms.stream().filter((t)-> !t.getSupervisorApprovalDate().equals(null))
					.filter((h)-> h.getHeadApprovalDate().equals(null)).filter((b)->b.getBencoApprovalDate().equals(null)).collect(Collectors.toList());
					//t.getHeadApprovalDate().equals(null)&&t.getBencoApprovalDate().equals(null)).collect(Collectors.toList());
			
			List<TRF> headApproved = forms.stream().filter((t)-> !t.getHeadApprovalDate().equals(null))
					.filter((b)->b.getBencoApprovalDate().equals(null)).collect(Collectors.toList());
			
			for(int i=0;i<noApproval.size();i++) {
				
				if(Duration.between(noApproval.get(i).getSubmittedDate().atStartOfDay(), LocalDate.now()).toDays()>3) {
					noApproval.get(i).setSupervisorApproval(true);
					noApproval.get(i).setSupervisorApprovalDate(LocalDate.now().toString());
				}
			}
			for(int i=0;i<superApprovedforms.size();i++) {
				if(Duration.between(noApproval.get(i).getSubmittedDate().atStartOfDay(), LocalDate.now()).toDays()>3) {
					superApprovedforms.get(i).setHeadApproval(true);
					superApprovedforms.get(i).setHeadApprovalDate(LocalDate.now().toString());
				}
			}
			for(int i=0;i<headApproved.size();i++) {
				if(Duration.between(noApproval.get(i).getSubmittedDate().atStartOfDay(), LocalDate.now()).toDays()>3) {
					headApproved.get(i).setHeadApproval(true);
					headApproved.get(i).setHeadApprovalDate(LocalDate.now().toString());
				}
			}
			//for(int)
			
			System.out.println(noApproval);
			System.out.println(superApprovedforms);
			System.out.println(headApproved);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public List<TRF> getFormsForReview(int userID) {//need to see if is denied
		try {
			List<TRF> forms = td.getForms().stream().filter((t) -> t.getStatus()==userID).collect(Collectors.toList());
			List<TRF> form = forms.stream().filter((t) -> !t.isDenied()).filter((a)-> !a.isApproved()).collect(Collectors.toList());
			return form;
		}catch (Exception e) {
			
		}
		return null;
	}


	@Override
	public List<TRF> getMyForms(int userID) {
		try {
			List<TRF> forms = td.getForms().stream().filter((t) -> t.getUserID()==userID).collect(Collectors.toList());
			return forms;
		}catch (Exception e) {
			
		}
		return null;
	}


	@Override
	public void addGrade(int formID, int grade) {
		TRF form = getForm(formID);
		if(form.getGrade()==0) {
			form.setGrade(grade);
			if(grade>=form.getPassingGrade()) {
				form.setPassed(true);
			}else {
				form.setPassed(false);
				form.setDenied(true);
				form.setDenialReason("Did not pass");
				User u= us.getUser(form.getUserID());
				if(form.getReimAmount()==0) {
					u.setPendingReim(u.getPendingReim()-form.getProjectedReim());
				}else {
					u.setPendingReim(u.getPendingReim()-form.getReimAmount());
				}
				us.updateUser(u);
			}
			updateForm(form);
		}
		
	}


	@Override
	public boolean checkUser(int userID, int formID) {
		TRF form = getForm(formID);
		if(form.getUserID()==userID) {
			return true;
		}
		return false;
	}


	@Override
	public boolean isDeniedOrApproved(int formID) {
		TRF form = getForm(formID);
		if(form.isDenied()||form.isApproved()) {
			return true;
		}
		return false;
	}


	@Override
	public boolean reviewOrUser(int formID, int userID) {
		//System.out.println("hello");
		 TRF form =getForm(formID);
		List<Integer> review =form.getReviewers();
		for(int in:review) {
			if(in==userID) {
				return true;
			}
		}
		//System.out.println(form.getUserID()+" "+userID);
		if(form.getUserID()==userID) {
			return true;
		}
		
	
		return false;
	}


	@Override
	public boolean passed(int formID) {
		TRF form = getForm(formID);
		if(form.isPassed()) {
			return true;
		}
		return false;
	}


	@Override
	public void endOfYear() {
		LocalDate date = LocalDate.now();
		
		
	}


	@Override
	public void approveReim(int formID) {
		TRF form = getForm(formID);
		if(form.getReimAmount()==0) {//verifies benco hasn't changed reim amount
			form.setReimAmount(form.getProjectedReim());
		}
		User u =us.getUser(form.getUserID());
		double approved=u.getReimbursementTotalForYear();
		double pending =u.getPendingReim();
		pending = pending-form.getReimAmount();
		approved=approved+form.getReimAmount();
		u.setReimbursementTotalForYear(approved);
		u.setPendingReim(pending);
		us.updateUser(u);
		
		form.setApproved(true);
		updateForm(form);
	}


	@Override
	public void presentationPassedOrDenied(int formID, boolean passed) {
		TRF form = getForm(formID);
		form.setPassed(passed);
		form.setPresentationPassed(passed);
		if(!passed) {
			form.setDenied(true);
			form.setDenialReason("Didn't pass Presentation");
			User u= us.getUser(form.getUserID());
			if(form.getReimAmount()==0) {
				u.setPendingReim(u.getPendingReim()-form.getProjectedReim());
			}else {
				u.setPendingReim(u.getPendingReim()-form.getReimAmount());
			}
			us.updateUser(u);
		}
		updateForm(form);
		
		
	}


	@Override
	public void cancelRequest(int formID) {
		TRF form = getForm(formID);
		form.setDenied(true);
		
	}


	@Override
	public boolean isDenied(int formID) {
		TRF form = getForm(formID);
		if(form.isDenied()) {
			return true;
		}
		return false;
	}


	@Override
	public boolean statusUserIsUser(int formID, int userID) {
		TRF form = getForm(formID);
		if(form.getStatus()==userID) {
			return true;
		}
		return false;
	}


	@Override
	public boolean isDeniedOrAllApproved(int formID) {
		TRF form = getForm(formID);
		if(form.isDenied()||form.isAllApproved()) {
			return true;
		}
		return false;
	}


	@Override
	public boolean presentationViewed(int formid) {
		TRF form=getForm(formid);
		return form.isPresentationViewed();
	}


	@Override
	public void viewPresentation(int formid, int userid) {
		TRF form = getForm(formid);
		List<Integer> viewed =form.getViewed();
		boolean already=false;
		for(int i=0;i<viewed.size();i++) {
			if(viewed.get(i)==userid) {
				already=true;
			}
		}
		if(!already) {
			viewed.add(userid);
			form.setViewed(viewed);
			if(viewed.size()==form.getReviewers().size()) {
				form.setPresentationViewed(true);
			}
			updateForm(form);
		}
		
		
	}
	
	public void setTRFDAO(TRFDAO dao) {
		td=dao;
	}
	
	public void setUserService(UserServiceImpl userImpl) {
		us=userImpl;
	}



}
