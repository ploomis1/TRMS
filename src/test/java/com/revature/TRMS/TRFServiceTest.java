package com.revature.TRMS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.runners.MockitoJUnitRunner;

import com.revature.beans.TRF;
import com.revature.beans.TRF.Event;
import com.revature.beans.User;
import com.revature.beans.User.Role;
import com.revature.data.TRFDAOImpl;
import com.revature.data.UserDAOImpl;
import com.revature.services.TRFServiceImpl;
import com.revature.services.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class TRFServiceTest {
	
	private TRFServiceImpl service;
	private TRFDAOImpl dao;
	private UserServiceImpl userService;
	private List<TRF> forms;
	private TRF form;
	private TRF temp;
	private User user;
	private User superv;
	List<Integer> review;
	List<Integer> view;
	
	@Before
	public void setUpForm() {
		this.service = new TRFServiceImpl(mock(TRFDAOImpl.class),mock(UserServiceImpl.class));
		this.dao = mock(TRFDAOImpl.class);
		this.userService=  new UserServiceImpl(mock(UserDAOImpl.class));
		service.setTRFDAO(dao);
		forms= new ArrayList<TRF>();
		review=new ArrayList<Integer>();
		review.add(1);
		review.add(2);
		review.add(3);
		view =new ArrayList<Integer>();
		view.add(1);
		view.add(2);
		view.add(3);
		
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
		form.setReviewers(review);
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
		form.setViewed(view);
		form.setRequestCancelled(false);
		
		forms.add(form);
		
		
		temp= new TRF();
		temp.setFormID(1);
		temp.setUserID(0);
		temp.setEventDate(LocalDate.of(2021, 4, 25));
		temp.setSubmittedDate(LocalDate.now());
		temp.setTime(null);
		temp.setLocation(null);
		temp.setDescription(null);
		temp.setCost(0);
		temp.setJustification(null);
		temp.setRequestAmount(0);
		temp.setAttachments(null);
		temp.setPassingGrade(0);
		temp.setHoursMissed(0);
		temp.setEvent(null);
		temp.setDenied(false);
		temp.setApproved(false);
		temp.setAllApproved(false);
		temp.setUrgent(false);
		temp.setPresentationID("presentation.pdf");
		temp.setPresentation(false);
		temp.setApprovalEmail(false);
		temp.setApprovalRole(null);
		temp.setEmailID(null);
		temp.setAvailableReim(0);
		temp.setProjectedReim(0);
		temp.setReimAmount(0);
		temp.setStatus(0);
		temp.setReviewers(null);
		temp.setSupervisorID(0);
		temp.setSupervisorApproval(false);
		temp.setSuperIsHead(false);
		temp.setSuperIsCeo(false);
		temp.setSupervisorApprovalDate(null);
		temp.setHeadID(0);
		temp.setHeadApproval(false);
		temp.setHeadApprovalDate(null);
		temp.setDenialReason(null);
		temp.setRequestForInfo(false);
		temp.setInformation(null);
		temp.setBencoID(0);
		temp.setBencoApproval(false);
		temp.setBencoApprovalDate(null);
		temp.setAmountChanged(false);
		temp.setExceedingFunds(false);
		temp.setGrade(0);
		temp.setPassed(false);
		temp.setFundsExceeded(null);
		temp.setPresentationPassed(false);
		temp.setPresentationViewed(false);
		temp.setViewed(null);
		temp.setRequestCancelled(false);
		
		forms.add(temp);
		
		user=new User();
		user.setId(0);
		user.setDepartmentid(0);
		user.setName(null);
		user.setPassword(null);
		user.setPendingReim(0);
		user.setReimbursementTotalForYear(0.0);
		user.setRole(null);
		user.setSupervisorid(1);
		
		superv = new User();
		superv.setId(1);
		superv.setDepartmentid(0);
		superv.setName(null);
		superv.setPassword(null);
		superv.setPendingReim(0);
		superv.setReimbursementTotalForYear(0);
		superv.setRole(Role.SUPERVISOR);
		superv.setSupervisorid(0);
		
	}
	
	@After
	public void teardown() {
		service=null;
		dao=null;
		userService=null;
		forms=null;
		form=null;
		temp=null;
		user=null;
		superv=null;
		review=null;
		view=null;
	}
	
//	@Test
//	public void addFormCreatesForm() throws Exception {
//		TRF forms= service.addForm(form);
//		doNothing().when(dao).addForm(any());
//		when(userService.getReviewers(any())).thenReturn(review);
//		when(userService.getUser(any())).thenReturn(superv);
//		doNothing().when(userService).updateUser(any());
//		assertEquals("",forms.getSupervisorID(),superv.getId());//maybe more tests that this
//	}
	@Test
	public void getFormReturnsForm() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		when(userService.getUser(form.getUserID())).thenReturn(user);
		TRF testForm = service.getForm(form.getFormID());
		assertEquals("Should have same formid", testForm.getFormID(),form.getFormID());
		
	}
	@Test
	public void updateFormCallsDAO() {//void method
		boolean called = service.updateForm(form);
		assertEquals("",true,called);
	}
	@Test
	public void getReviewersReturnsTrue() throws Exception {//maybe callls getForm?
		when(dao.getForm(form.getFormID())).thenReturn(form);
		boolean bool = service.getReviewers(form.getFormID(), superv.getId());
		assertEquals("Should be true", true, bool);
	}
	@Test
	public void approveFormApprovesCorrectRoleForm() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		service.approveForm(form.getFormID(), 1, Role.SUPERVISOR);
		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
		verify(dao).updateForm(captor.capture());
		final TRF test=captor.getValue();
		
		assertEquals("Should be approved",true , test.isSupervisorApproval() );
	}
//	@Test
//	public void denyFormDeniesForm() throws Exception {
//		when(dao.getForm(form.getFormID())).thenReturn(form);
//		when(userService.getUser(form.getUserID())).thenReturn(user);
//		doReturn(true).when(userService).updateUser(user);
//		service.denyForm(form.getFormID(), "not work related");
//		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
//		verify(dao).updateForm(captor.capture());
//		final TRF test=captor.getValue();
//		assertEquals("",true,test.isDenied());
//		
//	}
	@Test
	public void addPresentationAddsPresentation() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		service.addPresentation(form.getFormID(), "hello-kitty");
		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
		verify(dao).updateForm(captor.capture());
		final TRF test=captor.getValue();
		assertEquals("","hello-kitty",test.getPresentationID());
	}
	@Test
	public void getPresentationReturnsString() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		String pres=service.getPresentation(form.getFormID(),superv.getId());

		assertEquals("","presentation.pdf",pres);
	}
//	@Test
//	public void addAttachmentAddsAttachment() throws Exception {
//		when(dao.getForm(temp.getFormID())).thenReturn(temp);
//		String test="attachment";
//		List<String> testList = new ArrayList<String>();
//		testList.add(test);
//		service.addAttachment(temp.getFormID(), test);
//		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
//		verify(dao).updateForm(captor.capture());
//		final TRF testF=captor.getValue();
//		assertEquals("",testList,testF.getAttachments());
//	}
	@Test
	public void getAttachmentReturnsString() {
		
	}
	@Test
	public void addEmailAddsEmail() throws Exception {
		when(dao.getForm(temp.getFormID())).thenReturn(temp);
		service.addEmail(temp.getFormID(), "hello-kitty", Role.SUPERVISOR);
		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
		verify(dao).updateForm(captor.capture());
		final TRF test=captor.getValue();
		assertEquals("","hello-kitty",test.getEmailID());
		
	}
	@Test
	public void getEmailReturnsString() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		String test =service.getEmail(form.getFormID());
//		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
//		verify(dao).updateForm(captor.capture());
//		final TRF test=captor.getValue();
		assertEquals("",form.getEmailID(),test);
	}
//	@Test
//	public void updateReimUpdatesReim() throws Exception {
//		when(dao.getForm(form.getFormID())).thenReturn(form);
//		when(userService.getUser(form.getUserID())).thenReturn(user);
//		when(userService.updateUser(any(User.class))).thenReturn(true);
//		service.updateReimburement(form.getFormID(), 100.00, null);
//		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
//		verify(dao).updateForm(captor.capture());
//		final TRF test=captor.getValue();
//		assertEquals(100.00, test.getReimAmount(),0.0);
//	}
//	@Test
//	public void addInformationAddsInformation() throws Exception {
//		when(dao.getForm(form.getFormID())).thenReturn(form);
//		service.addInformation(form.getFormID(), "testing");
//		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
//		verify(dao).updateForm(captor.capture());
//		final TRF test=captor.getValue();
//		assertEquals("","testing",test.getInformation().get(0));
//		
//	}
	@Test
	public void getInformationReturnsListOfStrings() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		List<String> test = new ArrayList<String>();
		test.add("hello");
		form.setInformation(test);
		List<String> temp =service.getInformation(form.getFormID());
		assertEquals("",test,temp);
		
	}
	@Test
	public void addGradingFormatAddsGradingFormat() throws Exception {
		when(dao.getForm(temp.getFormID())).thenReturn(temp);
		String testS ="gf";
		service.addGradingFormat(temp.getFormID(),"gf");
		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
		verify(dao).updateForm(captor.capture());
		final TRF test=captor.getValue();
		assertEquals("",testS,test.getGradingFormatid());
	}
	@Test
	public void getGradingFormatReturnsString() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		String test = service.getGradingFormat(form.getFormID());
		assertEquals("",form.getGradingFormatid(),test);
	}
//	@Test
//	public void checkTimeChecksDates() throws Exception {
//		//when(dao.getForms()).thenReturn(forms);
//	}
	@Test
	public void getFormsForReviewReturnsListWithFormWithStatusOfUser() throws Exception {
		when(dao.getForms()).thenReturn(forms);
		List<TRF> temp = service.getFormsForReview(superv.getId());
		assertEquals("",form,temp.get(0));
	}
	@Test
	public void getMyFormsReturnsListOfFormsUserSubmitted() throws Exception {
		when(dao.getForms()).thenReturn(forms);
		List<TRF> temp = service.getMyForms(form.getUserID());
		assertEquals("",form,temp.get(0));
	}
	@Test
	public void addGradeAddsGrade() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		when(userService.getUser(user.getId())).thenReturn(user);
		service.addGrade(form.getUserID(), 70);
		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
		verify(dao).updateForm(captor.capture());
		final TRF test=captor.getValue();
		assertEquals("",true,test.isPassed());
	}
	@Test
	public void checkUserReturnsBoolean() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		boolean test=service.checkUser(form.getUserID(), form.getFormID());
		assertEquals("",true,test);
	}
	@Test
	public void isDeniedOrApprovedReturnsBoolean() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		boolean test = service.isDeniedOrApproved(form.getFormID());
		assertEquals("",false,test);
	}
	@Test
	public void reviewOrUserReturnsBoolean() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		boolean test = service.reviewOrUser(form.getFormID(), 2);
		assertEquals("",true,test);
	}
	@Test
	public void passedReturnsBoolean() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		boolean test = service.passed(form.getFormID());
		assertEquals("",false,test);
	}
//	@Test
//	public void approveReimChangesUserReimAmount() throws Exception {
//		when(dao.getForm(form.getFormID())).thenReturn(form);
//		when(userService.getUser(form.getUserID())).thenReturn(user);
//		service.approveReim(form.getFormID());
//		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
//		verify(dao).updateForm(captor.capture());
//		final TRF test=captor.getValue();
//		assertEquals("",true,test.isApproved());
//	}
	@Test
	public void presentationPassedPassesPresentation() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		when(userService.getUser(0)).thenReturn(user);
		service.presentationPassedOrDenied(0, true);
		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
		verify(dao).updateForm(captor.capture());
		final TRF test=captor.getValue();
		assertEquals("",true,test.isPassed());
	}
	@Test
	public void isDeniedReturnsBoolean() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		boolean test =service.isDenied(0);
		assertEquals("",false,test);
	}
	@Test
	public void statusUserIsUserReturnsBoolean() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		boolean test = service.statusUserIsUser(0, 1);
		assertEquals("",true,test);
	}
	@Test
	public void isDeniedOrAllApprovedReturnsBoolean() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		boolean test = service.isDeniedOrAllApproved(0);
		assertEquals("",false,test);
	}
	@Test
	public void presentationViewedReturnsBoolean() throws Exception {
		when(dao.getForm(form.getFormID())).thenReturn(form);
		boolean test = service.presentationViewed(0);
		assertEquals("",false,test);
	}
//	@Test
//	public void viewPresentationUpdatesViewedList() throws Exception {
//		when(dao.getForm(temp.getFormID())).thenReturn(temp);
//		service.viewPresentation(0, 1);
//		final ArgumentCaptor<TRF> captor = ArgumentCaptor.forClass(TRF.class);
//		verify(dao).updateForm(captor.capture());
//		final TRF test=captor.getValue();
//		assertEquals("",superv.getId(),test.getViewed().get(0));
//		
//	}
}
