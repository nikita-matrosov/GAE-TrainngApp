package com.google.cloud.training.conference;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;


public class CheckForNearlySoldOutConfs extends HttpServlet {

  /*
   * This method gets invoked by a cron job. This method checks if any conferences are nearly sold
   * out, and if so, updates the latestAnnouncement so that the website will show an announcement
   * about the conference.
   */

  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    // Get a handle to the DatstoreService
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    // Get all conferences
    Query confQuery = new Query("Conference");
    
    // Filter on conferences that are still available for purchase
    Query.Filter availabilityFilter = new FilterPredicate("numTixAvailable", FilterOperator.GREATER_THAN, 0);
    
    // Add the filter to the query
    confQuery.setFilter(availabilityFilter);

    PreparedQuery results = datastore.prepare(confQuery);

    String announceString = "";

    // Iterate over the results
    // For each conference, check if there are more than 25% of the tickets left
    if (results != null) {
      for (Entity conference : results.asIterable()) {
        long maxAttendees = (Long) conference.getProperty("maxAttendees");
        long numTixAvailable = (Long) conference.getProperty("numTixAvailable");
        String confName = (String) conference.getProperty("confName");

        // For simplicity here, just find conferences that have less than or equal to
        // 25 percent of their tickets still available
        if ((numTixAvailable > 0) && (maxAttendees > 0) && (numTixAvailable / maxAttendees <= 0.25)) {
          announceString += " " + confName + ",";
        }
      }
      // If we found some conferences that are nearly sold out, proceed,
      // otherwise we are done
      if (announceString != "") {
        announceString =
            "<p class=\"announcement\">Last chance to sign up! These conferences are nearly sold out: "
                + announceString + "</p>";
        Announcement.setLatestAnnouncement(announceString);
      }
    }
  }

}
