package com.revature.TRMS;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import com.datastax.oss.driver.api.core.CqlSession;
//import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
//import com.datastax.oss.driver.api.core.cql.BoundStatement;
//import com.datastax.oss.driver.api.core.cql.ResultSet;
//import com.datastax.oss.driver.api.core.cql.SimpleStatement;
//import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.datastax.oss.driver.api.core.config.DriverConfigLoader;
import com.revature.controllers.TRFController;
import com.revature.controllers.UserController;
import com.revature.data.TRFDAO;
import com.revature.data.TRFDAOImpl;
import com.revature.data.UserDAO;
import com.revature.data.UserDAOImpl;
import com.revature.data.factory.BeanFactory;
import com.revature.data.factory.Log;
import com.revature.services.TRFService;
import com.revature.services.TRFServiceImpl;
import com.revature.services.UserService;
import com.revature.services.UserServiceImpl;
//import com.revature.utils.CassandraUtil;

import io.javalin.Javalin;
/**
 * Hello world!
 *
 */
@Log
public class Driver 
{
	private static UserService us = (UserService) BeanFactory.getFactory().get(UserService.class, UserServiceImpl.class);
	private static TRFService ts = (TRFService) BeanFactory.getFactory().get(TRFService.class, TRFServiceImpl.class);
	private static UserDAO ud = (UserDAO) BeanFactory.getFactory().get(UserDAO.class, UserDAOImpl.class);
	private static TRFDAO td = (TRFDAO) BeanFactory.getFactory().get(TRFDAO.class, TRFDAOImpl.class);
	private static final Logger log = LogManager.getLogger(Driver.class);
    public static void main( String[] args )
    {
    	/*
    	 * Nneed to initialize keyspace
    	 * Need to setup each table, maybe create in DAO?
    	 */
        System.out.println( "Hello World!" );
        
//        Counters count = new Counters();
//        System.out.println(count.getUserid());
//        Counters counts = new Counters();
//        System.out.println(counts.getUserid());
       // dbcreation();
       // us.createUserTable();
        
//        try {
//			td.createTRFTable();
//			//ud.createUserTable();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        javalin();
        //checkDates();
    }
    public static void checkDates() {
    	
    	ts.checkTime();
    }
    
//    public static void dbcreation() {
//            StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append("TRMS with replication = {")
//                    .append("'class':'SimpleStrategy','replication_factor':1};");
//            DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
//            CqlSession.builder().withConfigLoader(loader).build().execute(sb.toString());
//            
//           
//        }
    public static void javalin() {
    	
    	Javalin app = Javalin.create().start(8080);
    	//maybe have method call for checking dates to see if auto approve or email needs to be sent
    	/*
    	 * Form controller
    	 * Prints form format?
    	 * Gets form sent in
    	 * Requests to see form
    	 * Update Form(Only BenCO)
    	 * 
    	 */
    	//user controllers
    	app.put("/users", UserController::createUser);
    	app.post("/users", UserController::login);
    	app.delete("/users", UserController::logout);
    	app.get("/users", UserController::getUsers);
//    	
//    	
    	app.put("/trf", TRFController::submitForm);
    	app.get("/trf", TRFController::viewMyForms);
    	
    	app.get("/trf/user", TRFController::viewFormsAwaiting);
    	
    
    	app.get("/trf/formID", TRFController::viewForm);
    	app.put("/trf/:formID/presentation", TRFController::uploadPresentation);
    	app.get("/trf/:formID/presentation", TRFController::viewPresentation);
    	app.put("/trf/:formID/presentaion/passed", TRFController::presentationPassed);
    	
    	app.put("/trf/:formID/attachments", TRFController::uploadAttachments);
    	app.get("/trf/:formID/attachments/:attachment", TRFController::getAttachments);

    	app.put("/trf/:formID/email", TRFController::uploadApprovalEmail);
    	app.get("/trf/:formID/email", TRFController::getApprovalEmail);
//    	
    	app.put("/trf/:formID/grade", TRFController::uploadGrade);
    	
    	app.put("/trf/:formID/information", TRFController::requestInformation);
    	app.get("/trf/:formID/information", TRFController::getRequestInformation);

    	app.put("/trf/:formID/approve", TRFController::approveForm);
    	app.put("/trf/:formID/deny", TRFController::denyForm);
    	app.put("/trf/:formID/cancel", TRFController::cancelRequest);
    	app.put("/trf/:formID/approveReim", TRFController::approveReim);
    	
    	app.put("/trf/:formID/gradingFormat", TRFController::uploadGradingFormat);
    	app.get("/trf/:formID/gradingFormat", TRFController::getGradingFormat);

    	app.put("/trf/:formID/updateReimbursement", TRFController::updateReimbursement);
    	//maybe just have another one for changing it for more? or just make a string that has reason why
    	//form controllers
    }
}
