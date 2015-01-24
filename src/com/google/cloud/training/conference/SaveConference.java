package com.google.cloud.training.conference;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


import java.security.Principal;

@SuppressWarnings("serial")
public class SaveConference extends HttpServlet {
  
  // Get a handle to the DatastoreService
  public static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    PrintWriter writer = resp.getWriter();

    // Get the logged in user's email
    Principal userPrincipal = req.getUserPrincipal();
    String mainEmail = userPrincipal.getName();

    // Get the values sent by the form
    String topic = req.getParameter("topic");
    String city = req.getParameter("city");
    String maxAttendees = req.getParameter("maxAttendees");
    String confName = req.getParameter("confName");
    String confDesc = req.getParameter("confDesc");
    String startDate = req.getParameter("startDate");
    String endDate = req.getParameter("endDate");

    Conference conference = null;

    try {
      // Make sure we have the RegisteredUser in the datastore.
      // Throws EntityNotFound if we don`t find the RegisteredUser Entity in the datastore.
      Entity user = RegisteredUser.getUser(mainEmail);
      conference =
          new Conference(mainEmail, confName, confDesc, topic, city, maxAttendees, startDate,
              endDate);

      // Get a string of the conference entity's key
      Key confKey = conference.getConferenceEntity().getKey();
      String confKeyString = KeyFactory.keyToString(confKey);
      
      // Update the RegisteredUser's scheduledConferences property
      // to include the string of the conference key
      EntityUtils.addValueToMVProperty(user, "scheduledConferences", confKeyString);
      
      // Save the RegisteredUser entity back to the datastore
      datastore.put(user);

      // Redirect to /showtickets
      // resp.sendRedirect("/showtickets?&confKeyString=" + confKeyString + "&confName=" + confName);
      resp.sendRedirect("/listconferences");

    } catch (EntityNotFoundException e) {
      // EntityNotFound is thrown if
      // mainEmail does not correspond to a Registered User
      resp.setContentType("text/html");
      writer.println("<p>You must register a <a href=\"/userprofile\">User Profile</a>"
          + " before you can schedule a conference.</p>");
    }
  }

  /*
   * If a user come to this page as a GET, redirect to the Create Conference page
   */

  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.sendRedirect("/createconference");
  }
}
