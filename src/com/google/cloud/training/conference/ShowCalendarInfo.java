package com.google.cloud.training.conference;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

/*
 * The following imports require that the Google Calendar APIs have been added to your project.
 * In Eclipse, select the Google menu > Add Google APIs > Calendar API
 */

public class ShowCalendarInfo extends AbstractAppEngineAuthorizationCodeServlet {
  
  // The scope of the APIs for which permission is needed
  String scope = "https://www.googleapis.com/auth/calendar";
  
  // For a single scope, turn the scope into a singleton collection
  Iterable<String> scopeList = Collections.singleton(scope);

  // Get future events from the user's calendar.
  // doGet() gets invoked after the authentication process has completed.
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    PrintWriter writer = resp.getWriter();
    resp.setContentType("text/html; charset=UTF-8");
    writer.println("<h3>We are authenticated, that is good to know</h3>");
  }
  
  @Override
  // Start the process of authenticating the user and getting their permission
  // to access the APIs defined by the scopeList
  protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
    return AuthUtils.initializeFlow(scopeList);
  }

  @Override
  // Get the URI to redirect to at the completion of the authorization process
  public String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    return AuthUtils.getRedirectUri(req);
  }
}
