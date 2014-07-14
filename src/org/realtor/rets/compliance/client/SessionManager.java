/*
 * Created on Nov 11, 2004
 *
 */
package org.realtor.rets.compliance.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.realtor.rets.compliance.client.http.RetsHttpRequest;
import org.realtor.rets.compliance.client.http.RetsHttpResponse;
import org.realtor.rets.compliance.client.persistence.PersistenceException;
import org.realtor.rets.compliance.client.user.UserNotFoundException;

import java.util.HashMap;
import java.util.Map;


/**
 * @author jthomas
 *
 */
public class SessionManager implements RequestListener {
    private static Log log = LogFactory.getLog(SessionManager.class);
    Map<String,ClientSession> sessionsMap  = new HashMap<String,ClientSession>();
    Map<String, ClientSession> usernameMap = new HashMap();
    
    public ClientSession getSessionFromUsername(String username) {
        return usernameMap.get(username);
    }
    
    public ClientSession getSessionFromSessionId(String sessionId) {
        return sessionsMap.get(sessionId);
    }

    public void setSession(String username, ClientSession session)
            throws PersistenceException {
        setSession(username, null,  session);
    }

    public void setSession(String username, String sessionId, ClientSession session)
            throws PersistenceException {
        log.debug("XXXXX Setting Session: username:"+username+" sessionId:"+sessionId+"ClientSession:"+session.toString());

        if(username!=null && username.length()>0)usernameMap.put(username, session);
        if(sessionId!=null && sessionId.length()>0)sessionsMap.put(sessionId, session);
        session.save();
    }

    public void processResponse(RetsHttpRequest request, RetsHttpResponse response)
            throws UserNotFoundException, PersistenceException {
        ClientSession session = null;
        String sessionId = null;
        String username = request.getRemoteUser();

        String[] cookieNames = {"RETS-Session-ID", "JSESSIONID", "ASP.NET_SessionId"};
        String cookieName = null;
        //find cookie session id
        for (String cn : cookieNames){
            sessionId = request.getCookieValue(cn);
            if (sessionId != null && sessionId.length()>0){
                cookieName = cn;
                break;
            }
        }
        log.info("username:"+username+" path:"+request.getPath()+" cookie \""+cookieName+"\": sessionID="+ sessionId);
        log.debug("usernameMap:"+usernameMap.toString());
        log.debug("sessionsMap:"+sessionsMap.toString());

		session = getSessionFromSessionId(sessionId);

        if (session == null) {
            //. Not logged in yet. Need to fetch by username
            if (username == null) {
                log.error("No session cookie and no Remote User name - Unable to route request ");
            } else {
                session = this.getSessionFromUsername(username);
                if (session == null) {
                    log.debug("loading session for "+username);
                    session = ClientComplianceApp.getComplianceDAO().loadSession(username);
                }

                //setSession(username, sessionId, session);
            }
        }

        if (session == null){
            log.error("NO SESSION FOUND FOR "+request.getPath());
        }
        else {
            session.processResponse(request, response);

            // because some people change the cookie every response
            //find cookie session id
            for (String cn : cookieNames){
                sessionId = response.getCookieValue(cn);
                if (sessionId != null && sessionId.length()>0){
                    cookieName = cn;
                    break;
                }
            }
            username=session.getUsername();
            log.debug("response Cookie:"+sessionId+" for "+username);
            log.debug("response cookies:"+response.cookies.toString());
            setSession(username, sessionId, session);
        }
    }
}
