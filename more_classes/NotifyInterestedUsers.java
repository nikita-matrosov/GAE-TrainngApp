package com.google.cloud.training.conference;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;

import java.util.Date;
import java.util.logging.Logger;
import java.text.DateFormat;

public class NotifyInterestedUsers extends HttpServlet {
  
  private static Logger log = Logger.getLogger("NotifyInterestedUsers");
  

  // App Engine applications can only send emails on behalf of the application's developers.
  // TODO set emailFrom to the email address of a developer for your application
  String fromEmail = "yourusername@gmail.com";   
  /*
   * This method sends an email to everyone who has registered a preference to be notified about
   * conferences that relate to this conference's topic.
   */
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    // Get the confKeyString value from the request
    String confKeyString = req.getParameter("confKeyString");

    // To DO
    // Get the conference Entity that corresponds to the confKeyString
    // then call emailPotentialAttendees(), passing the conference Entity
    // Use a try/catch statement to catch EntityNotFoundException
  }

  /*
   * Send an email about the new conference to everyone who has registered an interest in the
   * conference's topic.
   */
  public void emailPotentialAttendees(Entity conference) {
    // Get a handle to the DatstoreService
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    // Get the details of the conference
    String topic = (String) conference.getProperty("topic");
    String confName = (String) conference.getProperty("confName");

    String city = (String) conference.getProperty("city");
    String startDateString = "";
    Date startDate = (Date) conference.getProperty("startDate");
    if (startDate != null) {
      startDateString = DateFormat.getDateInstance(DateFormat.MEDIUM).format(startDate);
    }

    // Create the message body
    String msgBody =
        "Hi! \n We want to let you know that a conference called " + confName
            + "has been scheduled to start on " + startDateString + " in " + city
            + ".We thought you would like to know because you are interested "
            + "in conferences about " + topic + ".";

    // Create a Query to get RegisteredUsers.
    // TODO

    // Create a filter to find users whose interests match the given topic.
    // TODO
    
    // Set the filter on the query
    // TODO

    // TODO Submit the query
    PreparedQuery results = 

    // Iterate over the results
    for (Entity user : results.asIterable()) {
      // Add a log entry for each email to be sent
      log.info("Send email to : " + user.getProperty("mainEmail"));
      String userEmail = (String) user.getProperty("mainEmail");

      // Send email to each user in the result set
      Properties props = new Properties();
      Session session = Session.getDefaultInstance(props, null);

      try {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(fromEmail, "Conference Central Admin"));
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
        msg.setSubject("Conference you might be interested in: " + confName);
        msg.setText(msgBody);
        Transport.send(msg);

      } catch (AddressException e) {
        // ...
      } catch (MessagingException e) {
        // ...
      } catch (UnsupportedEncodingException e) {
        // ...
      }
    }
  }
}
