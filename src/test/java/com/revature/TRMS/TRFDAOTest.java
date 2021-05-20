package com.revature.TRMS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.datastax.oss.driver.api.core.CqlSession;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.revature.beans.TRF;
import com.revature.beans.TRF.Event;
import com.revature.data.TRFDAOImpl;
import com.revature.utils.CassandraUtil;

@RunWith(MockitoJUnitRunner.class)
public class TRFDAOTest {
	public TRFDAOImpl dao;
	public TRF form;
	public CassandraUtil cass;
	
	@Mock
	public CqlSession session;
	
	
	
	
	@Before
	public void setUp() {
		dao= new TRFDAOImpl(mock(CqlSession.class));
		
		form= new TRF();
		form.setFormID(0);
		form.setUserID(0);
		form.setEventDate(LocalDate.of(2021, 10, 8));
		form.setSubmittedDate(LocalDate.now());
		form.setTime(null);
		form.setLocation(null);
		form.setDescription(null);
		form.setCost(0);
		form.setJustification(null);
		form.setRequestAmount(0);
		form.setAttachments(null);
		form.setGradingFormatid("GradingFormat.png");
		form.setPassingGrade(70);
		form.setHoursMissed(0);
		form.setEvent(Event.CERTIFICATION);
		form.setDenied(false);
		form.setApproved(false);
		form.setAllApproved(false);
		form.setUrgent(false);
		form.setPresentationID("presentation.pdf");
		form.setPresentation(true);
		form.setApprovalEmail(true);
		form.setApprovalRole("SUPERVISOR");
		form.setEmailID("ApprovalEmail.msg");
		form.setAvailableReim(0);
		form.setProjectedReim(0);
		form.setReimAmount(0);
		form.setStatus(1);
		form.setReviewers(null);
		form.setSupervisorID(1);
		form.setSupervisorApproval(false);
		form.setSuperIsHead(false);
		form.setSuperIsCeo(false);
		form.setSupervisorApprovalDate(null);
		form.setHeadID(2);
		form.setHeadApproval(false);
		form.setHeadApprovalDate(null);
		form.setDenialReason(null);
		form.setRequestForInfo(false);
		form.setInformation(null);
		form.setBencoID(3);
		form.setBencoApproval(false);
		form.setBencoApprovalDate(null);
		form.setAmountChanged(false);
		form.setExceedingFunds(false);
		form.setGrade(0);
		form.setPassed(false);
		form.setFundsExceeded(null);
		form.setPresentationPassed(false);
		form.setPresentationViewed(true);
		form.setViewed(null);
		form.setRequestCancelled(false);
	}
	
	@After
	public void tearDown() {
		dao=null;
		form=null;
		session=null;
		cass=null;
	}
	
	
	@Test
	public void addFormCallsUtil() throws Exception {
		dao.addForm(form);
		verify(cass,times(1)).getSession();
		
	}
	
	@Test
	public void updateFormCallsUtil() throws Exception {
		dao.updateForm(form);
		verify(cass,times(1)).getSession();
	}
	
	@Test
	public void getFormCallsUtil() throws Exception {
		dao.getForm(form.getFormID());
		verify(cass,times(1)).getSession();
	}
	
	@Test
	public void getFormsCallsUtil() throws Exception {
		dao.getForms();
		verify(cass,times(1)).getSession();
	}
	
	

}
