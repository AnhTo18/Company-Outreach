package org.springframework.samples.outreach.websockets;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.outreach.company.Company;
import org.springframework.samples.outreach.company.CompanyRepository;
import org.springframework.stereotype.Component;
import org.springframework.samples.outreach.events.*;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * chat websocket server
 * @author creimers
 * @author kschrock
 */
@ServerEndpoint("/notify/{username}")
@Component
public class HelloWorldSocket {
	
	
	@Autowired
    CompanyRepository companyRepository;
	
	@Autowired
    EventRepository eventRepository;
	
	// Store all socket session and their corresponding username.
    private static Map<Session, String> sessionUsernameMap = new HashMap<>();
    private static Map<String, Session> usernameSessionMap = new HashMap<>();
    
    private final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    
    
    @OnOpen
    public void onOpen(
         Session session,
         @PathParam("username") String username) throws IOException
    {
        logger.info("Entered into Open");
        sessionUsernameMap.put(session, username);
        usernameSessionMap.put(username, session);
       
        String message="User:" + username + " has Joined the Chat";
        broadcast(message);

    }
 
    
    @OnMessage
    public void onMessage(Session session, String eventInfo) throws IOException 
    {  
    	logger.info("enter onmessage");
    	ObjectMapper mapper = new ObjectMapper();
    	try {
    		 
    		Event event = mapper.readValue(eventInfo, Event.class);
    		logger.info("Created event object from json data");
    		Company company = event.getCompany();
    		company = companyRepository.findCompanyByUsername(company.getUsername());
    		logger.info("company name is" + company.getCompanyName());
    		event.setCompany(company);
    		company.getEvents().add(event);
    		companyRepository.save(company);
    		companyRepository.flush();
    		eventRepository.save(event);
    		eventRepository.flush();
    		 logger.info("Entered into Message: Got Message:"+eventInfo);
    	}
    	
    	catch (IOException e){
    		logger.info("didn't work");
    		e.printStackTrace();
    	}
    	
    // Handle new messages
  
//    String username = sessionUsernameMap.get(usersSubbed);
//    sendMessageToPArticularUser(usersSubbed, "[DM] " + username + ": " + eventInfo);
    }
  

    @OnClose
    public void onClose(Session session) throws IOException
    {
    	logger.info("Entered into Close");
    	
    	String username = sessionUsernameMap.get(session);
    	sessionUsernameMap.remove(session);
    	usernameSessionMap.remove(username);
        
    	String message= username + " disconnected";
        broadcast(message);
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) 
    {
        // Do error handling here
    	logger.info("Entered into Error");
    	throwable.printStackTrace();
    }
    
	private void sendMessageToPArticularUser(String username, String message) 
    {	
    	try {
    		usernameSessionMap.get(username).getBasicRemote().sendText(message);
        } catch (IOException e) {
        	logger.info("Exception: " + e.getMessage().toString());
            e.printStackTrace();
        }
    }
    
    private static void broadcast(String message) 
    	      throws IOException 
    {	  
    	sessionUsernameMap.forEach((session, username) -> {
    		synchronized (session) {
	            try {
	                session.getBasicRemote().sendText(message);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
}

