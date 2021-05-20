package com.revature.services;

import java.util.List;


import com.revature.beans.TRF;
import com.revature.beans.User.Role;
import com.revature.data.factory.Log;

/*
 * Other things needed to do
 * Catch Exceptions and send to Service Layer?
 * Need user info to send form to correct person to review
 * Need to call file manager for s3
 * 
 * 
 * Methods needed
 * Form to send to front end?
 * Get Form created sent in from service layer
 * 
 * Update forms
 * Update amount for reimbursement
 * Remove or Delete form?
 * 
 * Thinking these for other service------
 * Send to correct person for more information?
 * Get more information and send back?
 * Send form to correct person
 * Check to see if approved in timely matter
 * 
 * 
 * 4/20 update
 * Bring in form information from controller.(need file information sent in)(Maybe create two adds for this?)
 * 1.Need to check if it was subbmitted late, if so deny
 * 2.Need to check if less than two weeks away, if so mark urgent
 * 3. Need to check if passing grade provided, if not mark as default passing grade
 * 4. Need to see if grading format needs presentation, if so set flag
 * 5. need to check if approval email sent, if so set flag.
 * (Maybe create another add for this)Maybe need to check somewhere down the line)(maybe need to add user info to email)
 * 6. Need to check if user dept is benco, make sure benco assigned is not them.
 * 7. Need to get supervisor info, dept head info, and assign benco to it, along with benco head
 * if supervisor is head, set flag
 * 
 * During the process
 * 1. Need to send info to supervisor
 * 2. supervisor approves, denies and gives reason, or asks for more info.
 * 	if denied, flag set ,super provides information why, and form is saved.
 * 		if asks for more info, set flag, get who needs to give info(user in this), get info asked, and update status?
 * 		send to user. 
 * 		User then adds info that was requested.(Possibly through string or with files)
 * 		Once user sent info, flag is removed and sent to supervisor again.
 * 			if approval takes to long auto approved(need to set time for this)
 * 3. If approved by supervisor or auto approved, check flag to see if head, if not sent to head, if so skip 4 and go to benco
 * 4. Head can deny, approve, ask for information
 * 	if denied, flag set and form is saved
 * 		if need more info, flag is set, get who needs to add more info, and info asked,
 *		 whoever adds more info(possible file or string) flag reset and sent back to head
 * 			if approval takes to long auto approve(need to set approval time for this)
 * 5. if approved, or auto approved moves onto benco
 * 6. Benco can deny, approve, request info, change amount to be less, change amount to be more
 * 	if denied, set flag and save file
 * 		if request info, set flag, get user info is requested, get info needed from benco
 * 		info is added by requested user, can be files or string, or both, flag reset
 * 		info is sent back to benco
 * 			if amount to be less, flagged set and sent to user.
 * 			user can cancel, or not
 * 			if cancelled set flag and save
 * 			if not proceed
 * 				if amount to be more
 * 				flag is set, benco provides reason why.
 * 	if approved set flag and wait for user to add passing grade
 * 7. User adds grade information or presentation(both maybe files)
 * 	if grade
 * 	benco verifies grade.
 * 		if passing flag is set, money is given to user and user pending changes, and totalreim changes
 * 		if not passing flag is set, pending amount changes. file saved
 * 	if presentation
 * 	supervisor verifies presentation, and presented to all parties
 * 		if satisfactory, flag is set, money is given to user, update pendingreim and totalreim
 * 		if not satisfactory, flag is set, update pendingreim, savefile
 * 	
 * 
 * 
 * So we need add()
 * takes in form from controller.
 * verifies date
 * checks if needed to be urgent
 */
@Log
public interface TRFService {
	
	public TRF addForm(TRF form);
	public TRF getForm(int id);
	public boolean updateForm(TRF form);
	public boolean approveForm(int formid, int id, Role role);//maybe use rxjava to send to next person?
	public boolean denyForm(int formID, String denialreason);//maybe add another if no reason needed
	public boolean addPresentation(int formID, String presentationID);
	public String getPresentation(int formID, int userID);
	public boolean addAttachment(int formID, String attachment);
	public String getAttachment(int attachmentID);//maybe form id?
	public boolean addEmail(int formID, String emailID, Role approvalRole);
	public String getEmail(int emailID);//maybe form id?
	public boolean updateReimburement(int formID, double reim,String reason);
	public int getReimburesment(int formID);
	public boolean addInformation(int form, String informationID);
	public List<String> getInformation(int informationID);//maybe form id?
	public boolean addGradingFormat(int formID, String gradingFormatID);
	public String getGradingFormat(int formID); //maybe form id?
	public void checkTime();//maybe form id?
	public void addGrade(int formID, int grade );//maybe this approves it?
	public boolean getReviewers(int formID,int userID);//maybe create this in user service
	public boolean reviewOrUser(int formID, int userID);
	public boolean checkUser(int userID, int formID);
	public List<TRF> getFormsForReview(int userID);
	public List<TRF> getMyForms(int userID);
	public boolean isDeniedOrApproved(int formID);
	public boolean passed(int formID);
	public void endOfYear();
	public void approveReim(int formID);
	public void presentationPassedOrDenied(int formID, boolean passed);
	public void cancelRequest(int formID);
	public boolean isDenied(int formID);
	public boolean statusUserIsUser(int formID, int userID);
	public boolean isDeniedOrAllApproved(int formID);
	public boolean presentationViewed(int formid);
	public void viewPresentation(int formid, int userid);
	
	
	
	

}
