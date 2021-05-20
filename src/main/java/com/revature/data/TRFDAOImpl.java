package com.revature.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.beans.TRF;
import com.revature.beans.TRF.Event;
import com.revature.beans.User.Role;
import com.revature.data.factory.Log;
import com.revature.utils.CassandraUtil;
@Log
public class TRFDAOImpl implements TRFDAO {
	private CqlSession session;// = CassandraUtil.getInstance().getSession();
	
	public TRFDAOImpl() {
		this.session = CassandraUtil.getInstance().getSession();
	}
	
	public TRFDAOImpl(CqlSession session) {
		this.session=session;
	}
	
	@Override
	public void addForm(TRF form) throws Exception{
		String query = "Insert into trf (formID, userID, eventDate, submittedDate,time,"//5
				+ "location, description,cost,justification,requestAmount,attachments,"//6
				+ "gradingFormatID,passingGrade,hoursMissed,event,passingGradeDefault,"//5
				+ "denied,approved,urgent,presentationID,presentation,approvalEmail,approvalRole,"//7
				+ "emailID,availableReim,projectedReim,reimAmount,status,reviewers,supervisorID,"//7
				+ "supervisorApproval,superIsHead,superIsCeo,headID,supervisorApprovalDate,"//5
				+ "headApproval,denialReason,requestForInfo,headApprovalDate,"//3
				+ "information,bencoID,bencoApproval,bencoApprovalDate,amountChanged,"//6
				+ "exceedingFunds,grade,passed,fundsExceeded,presentationPassed,presentationViewed,"//6
				+ "viewed,requestCancelled)"//2
				+ "values (?,?,?,?,?,"//5
				+ "?,?,?,?,?,?"//6
				+ ",?,?,?,?,?"//5
				+ ",?,?,?,?,?,?,?"//7
				+ ",?,?,?,?,?,?,?"//7
				+ ",?,?,?,?,?"//,?"//5
				+ ",?,?,?"//3
				+ ",?,?,?,?,?,?"//6
				+ ",?,?,?,?,?,?"//6
				+ ",?,?"//,?,?"//2
				+ "); ";
		SimpleStatement s = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(form.getFormID(), form.getUserID(), form.getEventDate().toString(), //3
				form.getSubmittedDate().toString(),form.getTime(),form.getLocation(), form.getDescription(),//4
				form.getCost(),form.getJustification(),form.getRequestAmount(),form.getAttachments(),//4
				form.getGradingFormatid(),form.getPassingGrade(),form.getHoursMissed(),form.getEvent().toString(),//4
				form.getPassingGradeDefault(),form.isDenied(),form.isApproved(),form.isUrgent(),//4
				form.getPresentationID(),form.isPresentation(),form.isApprovalEmail(),form.getApprovalRole(),//4
				form.getEmailID(),form.getAvailableReim(),form.getProjectedReim(),form.getReimAmount(),//4
				form.getStatus(),form.getReviewers(),form.getSupervisorID(),form.isSupervisorApproval(),//4
				form.isSuperIsHead(),form.isSuperIsCeo(),form.getHeadID(),form.getSupervisorApprovalDate(),//4
				form.isHeadApproval(),form.getDenialReason(),form.isRequestForInfo(),form.getHeadApprovalDate(),//4
				form.getInformation(),form.getBencoID(),form.isBencoApproval(),form.getBencoApprovalDate(),//4
				form.isAmountChanged(),form.isExceedingFunds(),form.getGrade(),form.isPassed(),form.getFundsExceeded(),//5
				form.isPresentationPassed(),form.isPresentationViewed(),form.getViewed(),form.isRequestCancelled()//4
				);
		session.execute(bound);

	}
	@Override
	public void updateForm(TRF form) throws Exception{
		String query = "update trf set eventDate = ?, submittedDate = ?,time = ?,"
				+ "location = ?, description = ?,cost = ?,justification = ?,requestAmount = ?,attachments = ?,"
				+ "gradingFormatID = ?,passingGrade = ?,hoursMissed = ?,event = ?,passingGradeDefault = ?,"
				+ "denied = ?,approved = ?,urgent = ?,presentationID = ?,presentation = ?,approvalEmail = ?,approvalRole = ?,"
				+ "emailID = ?,availableReim = ?,projectedReim = ?,reimAmount = ?,status = ?,reviewers = ?,supervisorID = ?,"
				+ "supervisorApproval = ?,superIsHead = ?,superIsCeo = ?,supervisorApprovalDate = ?,headID  = ?,"
				+ "headApproval = ?,headApprovalDate = ?,denialReason = ?,"
				+ "requestForInfo = ?,information = ?,bencoID = ?,bencoApproval = ?,bencoApprovalDate = ?,amountChanged = ?,"
				+ "exceedingFunds = ?,grade = ?,passed = ?,fundsExceeded = ?,presentationPassed = ?,presentationViewed = ?,"
				+ "viewed = ?,requestCancelled = ? "
				+ "where formID = ? and userID = ?";
		SimpleStatement s = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(form.getEventDate().toString(), //3
				form.getSubmittedDate().toString(),form.getTime(),form.getLocation(), form.getDescription(),//4
				form.getCost(),form.getJustification(),form.getRequestAmount(),form.getAttachments(),//4
				form.getGradingFormatid(),form.getPassingGrade(),form.getHoursMissed(),form.getEvent().toString(),//4
				form.getPassingGradeDefault(),form.isDenied(),form.isApproved(),form.isUrgent(),//4
				form.getPresentationID(),form.isPresentation(),form.isApprovalEmail(),form.getApprovalRole(),//4
				form.getEmailID(),form.getAvailableReim(),form.getProjectedReim(),form.getReimAmount(),//4
				form.getStatus(),form.getReviewers(),form.getSupervisorID(),form.isSupervisorApproval(),//4
				form.isSuperIsHead(),form.isSuperIsCeo(),form.getSupervisorApprovalDate(),form.getHeadID(),//4
				form.isHeadApproval(),form.getHeadApprovalDate(),form.getDenialReason(),form.isRequestForInfo(),//4
				form.getInformation(),form.getBencoID(),form.isBencoApproval(),form.getBencoApprovalDate(),//4
				form.isAmountChanged(),form.isExceedingFunds(),form.getGrade(),form.isPassed(),form.getFundsExceeded(),//5
				form.isPresentationPassed(),form.isPresentationViewed(),form.getViewed(),form.isRequestCancelled(),form.getFormID(),form.getUserID());
		session.execute(bound);
	}

	@Override
	public TRF getForm(int id) throws Exception{
		TRF form;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		//form.setEventDate(LocalDate.parse(ctx.formParam("start date"),formatter));
		String query = "select * from trf where formID =?";
		BoundStatement bound = session.prepare(query).bind(id);
		ResultSet rs = session.execute(bound);
		Row data = rs.one();
		if(data != null) {
			form = new TRF();
			form.setUserID(data.getInt("userID"));
			form.setEventDate(LocalDate.parse(data.getString("eventDate"),formatter));///
			form.setSubmittedDate(LocalDate.parse(data.getString("submittedDate"),formatter));
			form.setTime(data.getString("time"));
			form.setLocation(data.getString("location"));
			form.setDescription(data.getString("description"));
			form.setCost(data.getDouble("cost"));
			form.setJustification(data.getString("justification"));
			form.setRequestAmount(data.getDouble("requestAmount"));
			form.setAttachments(data.getList("attachments", String.class));
			form.setGradingFormatid(data.getString("gradingFormatID"));
			form.setPassingGrade(data.getInt("passingGrade"));
			form.setHoursMissed(data.getInt("hoursMissed"));
			form.setEvent(Event.valueOf(data.getString("event")));
			form.setPassingGradeDefault(data.getInt("passingGradeDefault"));
			form.setDenied(data.getBoolean("denied"));
			form.setApproved(data.getBoolean("approved"));
			form.setUrgent(data.getBoolean("urgent"));
			form.setPresentationID(data.getString("presentationID"));
			form.setPresentation(data.getBoolean("presentation"));//
			form.setApprovalEmail(data.getBoolean("approvalEmail"));
			form.setApprovalRole(data.getString("approvalRole"));
			form.setEmailID(data.getString("emailID"));
			form.setAvailableReim(data.getDouble("availableReim"));
			form.setProjectedReim(data.getDouble("projectedReim"));
			form.setReimAmount(data.getDouble("reimAmount"));
			form.setStatus(data.getInt("status"));
			form.setReviewers(data.getList("reviewers", Integer.class));
			form.setSupervisorID(data.getInt("supervisorID"));
			form.setSupervisorApproval(data.getBoolean("supervisorApproval"));
			form.setSuperIsHead(data.getBoolean("superIsHead"));
			form.setSuperIsCeo(data.getBoolean("superIsCeo"));
			form.setSupervisorApprovalDate(data.getString("supervisorApprovalDate"));
			form.setHeadID(data.getInt("headID"));
			form.setHeadApproval(data.getBoolean("headApproval"));
			form.setHeadApprovalDate(data.getString("headApprovalDate"));
			form.setDenialReason(data.getString("denialReason"));
			form.setRequestForInfo(data.getBoolean("requestForInfo"));
			form.setInformation(data.getList("information", String.class));
			form.setBencoID(data.getInt("bencoID"));//
			form.setBencoApproval(data.getBoolean("bencoApproval"));
			form.setBencoApprovalDate(data.getString("bencoApprovalDate"));
			form.setAmountChanged(data.getBoolean("amountChanged"));
			form.setExceedingFunds(data.getBoolean("exceedingFunds"));
			form.setGrade(data.getInt("grade"));
			form.setPassed(data.getBoolean("passed"));
			form.setFundsExceeded(data.getString("fundsExceeded"));
			form.setPresentationPassed(data.getBoolean("presentationPassed"));
			form.setPresentationViewed(data.getBoolean("presentationViewed"));
			form.setViewed(data.getList("viewed", Integer.class));
			form.setRequestCancelled(data.getBoolean("requestCancelled"));
			form.setFormID(id);
			return form;
		}
		return null;
		// TODO Auto-generated method stub

	}

	@Override
	public List<TRF> getForms() throws Exception{
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		List<TRF> forms = new ArrayList<TRF>();
		String query = "select * from trf";
		ResultSet rs = session.execute(query);
		
		rs.forEach(data ->{
			TRF form = new TRF();
			form.setUserID(data.getInt("userID"));
			form.setEventDate(LocalDate.parse(data.getString("eventDate"),formatter));///
			form.setSubmittedDate(LocalDate.parse(data.getString("submittedDate"),formatter));
			form.setTime(data.getString("time"));
			form.setLocation(data.getString("location"));
			form.setDescription(data.getString("description"));
			form.setCost(data.getDouble("cost"));
			form.setJustification(data.getString("justification"));
			form.setRequestAmount(data.getDouble("requestAmount"));
			form.setAttachments(data.getList("attachments", String.class));
			form.setGradingFormatid(data.getString("gradingFormatID"));
			form.setPassingGrade(data.getInt("passingGrade"));
			form.setHoursMissed(data.getInt("hoursMissed"));
			form.setEvent(Event.valueOf(data.getString("event")));
			form.setPassingGradeDefault(data.getInt("passingGradeDefault"));
			form.setDenied(data.getBoolean("denied"));
			form.setApproved(data.getBoolean("approved"));
			form.setUrgent(data.getBoolean("urgent"));
			form.setPresentationID(data.getString("presentationID"));
			form.setPresentation(data.getBoolean("presentation"));//
			form.setApprovalEmail(data.getBoolean("approvalEmail"));
			form.setApprovalRole(data.getString("approvalRole"));
			form.setEmailID(data.getString("emailID"));
			form.setAvailableReim(data.getDouble("availableReim"));
			form.setProjectedReim(data.getDouble("projectedReim"));
			form.setReimAmount(data.getDouble("reimAmount"));
			form.setStatus(data.getInt("status"));
			form.setReviewers(data.getList("reviewers", Integer.class));
			form.setSupervisorID(data.getInt("supervisorID"));
			form.setSupervisorApproval(data.getBoolean("supervisorApproval"));
			form.setSuperIsHead(data.getBoolean("superIsHead"));
			form.setSuperIsCeo(data.getBoolean("superIsCeo"));
			form.setSupervisorApprovalDate(data.getString("supervisorApprovalDate"));
			form.setHeadID(data.getInt("headID"));
			form.setHeadApproval(data.getBoolean("headApproval"));
			form.setHeadApprovalDate(data.getString("headApprovalDate"));
			form.setDenialReason(data.getString("denialReason"));
			form.setRequestForInfo(data.getBoolean("requestForInfo"));
			form.setInformation(data.getList("information", String.class));
			form.setBencoID(data.getInt("bencoID"));//
			form.setBencoApproval(data.getBoolean("bencoApproval"));
			form.setBencoApprovalDate(data.getString("bencoApprovalDate"));
			form.setAmountChanged(data.getBoolean("amountChanged"));
			form.setExceedingFunds(data.getBoolean("exceedingFunds"));
			form.setGrade(data.getInt("grade"));
			form.setPassed(data.getBoolean("passed"));
			form.setFundsExceeded(data.getString("fundsExceeded"));
			form.setPresentationPassed(data.getBoolean("presentationPassed"));
			form.setPresentationViewed(data.getBoolean("presentationViewed"));
			form.setViewed(data.getList("viewed", Integer.class));
			form.setRequestCancelled(data.getBoolean("requestCancelled"));
			form.setFormID(data.getInt("formID"));
			
			forms.add(form);
		});
		return forms;
	}
	@Override
	public void createTRFTable() throws Exception{
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS trf (")
			.append("formID int, userID int, eventDate text, submittedDate text,time text,")
			.append("location text, description text,cost double,justification text,requestAmount double,attachments list<text>,")
			.append("gradingFormatID text,passingGrade int,hoursMissed int,event text,passingGradeDefault int,")
			.append("denied boolean,approved boolean,urgent boolean,presentationID text,presentation boolean,approvalEmail boolean,approvalRole text,")
			.append("emailID text,availableReim double,projectedReim double,reimAmount double,status int,reviewers list<int>,supervisorID int,")
			.append("supervisorApproval boolean,superIsHead boolean,superIsCeo boolean,supervisorApprovalDate text,headID int,")
			.append("headApproval boolean,headApprovalDate text,denialReason text,")
			.append("requestForInfo boolean,information list<text>,bencoID int,bencoApproval boolean,bencoApprovalDate text,amountChanged boolean,")
			.append("exceedingFunds boolean,grade int,passed boolean,fundsExceeded text,presentationPassed boolean,presentationViewed boolean,")
			.append("viewed list<int>,requestCancelled boolean, primary key(formID, userID));");
				
		CassandraUtil.getInstance().getSession().execute(sb.toString());
		
	}
	


}
