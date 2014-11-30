package com.google.cloud.training.conference;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

/*
 * The following imports require that the Google Calendar APIs have been added to your project. 
 * In Eclipse, select the Google menu > Add Google APIs > Calendar API
 */

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;

import com.google.api.services.calendar.Calendar;

public class ShowCalendarInfo extends AbstractAppEngineAuthorizationCodeServlet {
  
  // The scope of the APIs for which permission is needed
  String scope = // TODO
  
  // For a single scope, turn the scope into a singleton collection
  Iterable<String> scopeList = Collections.singleton(scope);

  // Get future events from the user's calendar.
  // doGet() gets invoked after the authentication process has completed.
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    // TODO implement this method
  }
  
  @Override
  // Start the process of authenticating the user and getting their permission
  // to access the APIs defined by the scopeList
  protected AuthorizationCodeFlow initializeFlow(Iterable<String> scopeList) throws IOException {
    // TODO implement this method
  }

  @Override
  // Get the URI to redirect to at the completion of the authorization process
  public String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    // TODO implement this method
  }
}
