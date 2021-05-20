package com.revature.beans;

import java.util.Date;
import java.util.List;

import com.revature.beans.User.Role;

import java.time.*;

public class TRF {
	
	public static enum Event{
		UNIVERSITYCOURSE (0.8),
		SEMINAR (0.6), 
		CERTPREP (0.75), 
		CERTIFICATION (1.0), 
		TECHNICALTRAINING (0.9),
		OTHER (0.3);
		
		private double percent;
		
		Event(double percent){
			this.percent=percent;
		}
		
		public double getPercent() {
			return percent;
		}
	}
	//from form
	private int formID;//formID
	private int userID;//userID
	private LocalDate eventDate;//date of event
	private LocalDate submittedDate;//date form submitted
	private String time;//time of event
	private String location;//location of event
	private String description;//description of event
	private double cost;//cost of event
	private String justification;//justification for reim
	private double requestAmount;
	private List<String> attachments;
	private String gradingFormatid;//
	private int passingGrade;//grade to pass class
	private int hoursMissed;//time of work missed
	private Event event;//type of event
	private int passingGradeDefault = 70;//default passing grade
	private boolean denied;
	private boolean approved;//18
	private boolean allApproved;
	
	//created for process
	public boolean urgent;//if event is less than two weeks away
	private String presentationID;// presentationID
	private boolean presentation;//maybe have in grading format
	private boolean approvalEmail;//says there is an approvalEmail sent
	private String approvalRole;//Role that approved email
	private String emailID;//id of email
	private double availableReim;//amount available for user
	private double projectedReim;// amount projected for file
	private double reimAmount;//amount that will be reimbursed
	private int status; //what reviewer should be looking at this
	private List<Integer> reviewers;// instead just make ints for all reviewers?
	private int supervisorID;
	private boolean supervisorApproval;
	private boolean superIsHead;
	private boolean superIsCeo;
	private String supervisorApprovalDate;
	private int headID;
	private boolean headApproval;
	private String headApprovalDate;
	private String denialReason;
	private boolean requestForInfo;
	private List<String> information;//list of information requested
	//22
	//created for benco
	private int bencoID;
	private boolean bencoApproval;
	private String bencoApprovalDate;
	private boolean amountChanged;//when set, have user cancel form
	private boolean exceedingFunds;
	private int grade;
	private boolean passed;
	private String fundsExceeded;
//8
	
	//created for after approval
	private boolean presentationPassed;
	private boolean presentationViewed;
	private List<Integer> viewed;
	private boolean requestCancelled;
	//4
	
	
	public String getApprovalRole() {
		return approvalRole;
	}
	public boolean isAllApproved() {
		return allApproved;
	}
	public void setAllApproved(boolean allApproved) {
		this.allApproved = allApproved;
	}
	public void setApprovalRole(String approvalRole) {
		this.approvalRole = approvalRole;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public int getFormID() {
		return formID;
	}
	public void setFormID(int formID) {
		this.formID = formID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public LocalDate getEventDate() {
		return eventDate;
	}
	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}
	public LocalDate getSubmittedDate() {
		return submittedDate;
	}
	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public double getRequestAmount() {
		return requestAmount;
	}
	public void setRequestAmount(double requestAmount) {
		this.requestAmount = requestAmount;
	}
	public List<String> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	public String getGradingFormatid() {
		return gradingFormatid;
	}
	public void setGradingFormatid(String gradingFormatid) {
		this.gradingFormatid = gradingFormatid;
	}
	public int getPassingGrade() {
		return passingGrade;
	}
	public void setPassingGrade(int passingGrade) {
		this.passingGrade = passingGrade;
	}
	public int getHoursMissed() {
		return hoursMissed;
	}
	public void setHoursMissed(int hoursMissed) {
		this.hoursMissed = hoursMissed;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public int getPassingGradeDefault() {
		return passingGradeDefault;
	}
	public void setPassingGradeDefault(int passingGradeDefault) {
		this.passingGradeDefault = passingGradeDefault;
	}
	public boolean isDenied() {
		return denied;
	}
	public void setDenied(boolean denied) {
		this.denied = denied;
	}
	public boolean isUrgent() {
		return urgent;
	}
	public void setUrgent(boolean urgent) {
		this.urgent = urgent;
	}
	public String getPresentationID() {
		return presentationID;
	}
	public void setPresentationID(String presentationID) {
		this.presentationID = presentationID;
	}
	public boolean isPresentation() {
		return presentation;
	}
	public void setPresentation(boolean presentation) {
		this.presentation = presentation;
	}
	public boolean isApprovalEmail() {
		return approvalEmail;
	}
	public void setApprovalEmail(boolean approvalEmail) {
		this.approvalEmail = approvalEmail;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public double getAvailableReim() {
		return availableReim;
	}
	public void setAvailableReim(double availableReim) {
		this.availableReim = availableReim;
	}
	public double getProjectedReim() {
		return projectedReim;
	}
	public void setProjectedReim(double projectedReim) {
		this.projectedReim = projectedReim;
	}
	public double getReimAmount() {
		return reimAmount;
	}
	public void setReimAmount(double reimAmount) {
		this.reimAmount = reimAmount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<Integer> getReviewers() {
		return reviewers;
	}
	public void setReviewers(List<Integer> reviewers) {
		this.reviewers = reviewers;
	}
	public int getSupervisorID() {
		return supervisorID;
	}
	public void setSupervisorID(int supervisorID) {
		this.supervisorID = supervisorID;
	}
	public boolean isSupervisorApproval() {
		return supervisorApproval;
	}
	public void setSupervisorApproval(boolean supervisorApproval) {
		this.supervisorApproval = supervisorApproval;
	}
	public boolean isSuperIsHead() {
		return superIsHead;
	}
	public void setSuperIsHead(boolean superIsHead) {
		this.superIsHead = superIsHead;
	}
	public boolean isSuperIsCeo() {
		return superIsCeo;
	}
	public void setSuperIsCeo(boolean superIsCeo) {
		this.superIsCeo = superIsCeo;
	}
	public String getSupervisorApprovalDate() {
		return supervisorApprovalDate;
	}
	public void setSupervisorApprovalDate(String supervisorApprovalDate) {
		this.supervisorApprovalDate = supervisorApprovalDate;
	}
	public int getHeadID() {
		return headID;
	}
	public void setHeadID(int headID) {
		this.headID = headID;
	}
	public boolean isHeadApproval() {
		return headApproval;
	}
	public void setHeadApproval(boolean headApproval) {
		this.headApproval = headApproval;
	}
	public String getHeadApprovalDate() {
		return headApprovalDate;
	}
	public void setHeadApprovalDate(String headApprovalDate) {
		this.headApprovalDate = headApprovalDate;
	}
	public String getDenialReason() {
		return denialReason;
	}
	public void setDenialReason(String denialReason) {
		this.denialReason = denialReason;
	}
	public boolean isRequestForInfo() {
		return requestForInfo;
	}
	public void setRequestForInfo(boolean requestForInfo) {
		this.requestForInfo = requestForInfo;
	}
	public List<String> getInformation() {
		return information;
	}
	public void setInformation(List<String> information) {
		this.information = information;
	}
	public int getBencoID() {
		return bencoID;
	}
	public void setBencoID(int bencoID) {
		this.bencoID = bencoID;
	}
	public boolean isBencoApproval() {
		return bencoApproval;
	}
	public void setBencoApproval(boolean bencoApproval) {
		this.bencoApproval = bencoApproval;
	}
	public String getBencoApprovalDate() {
		return bencoApprovalDate;
	}
	public void setBencoApprovalDate(String bencoApprovalDate) {
		this.bencoApprovalDate = bencoApprovalDate;
	}
	public boolean isAmountChanged() {
		return amountChanged;
	}
	public void setAmountChanged(boolean amountChanged) {
		this.amountChanged = amountChanged;
	}
	public boolean isExceedingFunds() {
		return exceedingFunds;
	}
	public void setExceedingFunds(boolean exceedingFunds) {
		this.exceedingFunds = exceedingFunds;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public boolean isPassed() {
		return passed;
	}
	public void setPassed(boolean passed) {
		this.passed = passed;
	}
	public String getFundsExceeded() {
		return fundsExceeded;
	}
	public void setFundsExceeded(String fundsExceeded) {
		this.fundsExceeded = fundsExceeded;
	}
	public boolean isPresentationPassed() {
		return presentationPassed;
	}
	public void setPresentationPassed(boolean presentationPassed) {
		this.presentationPassed = presentationPassed;
	}
	public boolean isPresentationViewed() {
		return presentationViewed;
	}
	public void setPresentationViewed(boolean presentationViewed) {
		this.presentationViewed = presentationViewed;
	}
	public List<Integer> getViewed() {
		return viewed;
	}
	public void setViewed(List<Integer> viewed) {
		this.viewed = viewed;
	}
	public boolean isRequestCancelled() {
		return requestCancelled;
	}
	public void setRequestCancelled(boolean requestCancelled) {
		this.requestCancelled = requestCancelled;
	}
	

	
	
	
	
	


	
//	private boolean approved;
//	private boolean approvedByBenCO;
	
	//grading format
	/*
	 * type of event, maybe enum,
	 * university courses, seminars, 
	 * certification preparation classes, certifications, and technical training.  
	 */
	//work-related justification
	//maybe track where in the process it is at(or have other service doing this)
	//files uploaded

	
}
