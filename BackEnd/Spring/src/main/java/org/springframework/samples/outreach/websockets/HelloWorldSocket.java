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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
/**
 * chat websocket server
 * @author creimers
 * @author kschrock
 */
@ServerEndpoint("/notify/{username}")
@Component
public class HelloWorldSocket {
	
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
    public void onMessage(String usersSubbed, String eventInfo) throws IOException
    {  
    // Handle new messages
    logger.info("Entered into Message: Got Message:"+eventInfo);
    String username = sessionUsernameMap.get(usersSubbed);
    sendMessageToPArticularUser(usersSubbed, "[DM] " + username + ": " + eventInfo);
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

