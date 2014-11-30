package com.google.cloud.training.conference;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;
import com.google.cloud.training.conference.EntityUtils;

import java.security.Principal;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class BuyTicket extends HttpServlet {
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    resp.setContentType("text/html");
    PrintWriter writer = resp.getWriter();

    // Get a handle to the DatstoreService
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    // Get the user's email
    Principal userPrincipal = req.getUserPrincipal();
    String mainEmail = userPrincipal.getName();

    // Get the value for ticketKeyString submitted by the form
    String ticketKeyString = req.getParameter("ticketKeyString");

    // Create the key for the ticket
    Key ticketKey = KeyFactory.stringToKey(ticketKeyString);

    Entity ticket;
    Entity conference;

    // Get the Ticket and the Conference entity
    try {
      // Get the ticket entity, given the ticketKey
      // TODO

      // Get the Conference entity.
      // (The Conference is the ticket's parent.)
      // TODO
    } catch (EntityNotFoundException e) {
      writer.println("<p>Could not find either the Conference or Ticket entity, sorry<p>");
      return;
    }

    // Get the RegisteredUser Entity
    Entity user;
    try {
      user = RegisteredUser.getUser(mainEmail);
    } catch (EntityNotFoundException e) {
      writer.println("<p>You must register a <a href=\"/userprofile\">User Profile</a>"
          + "before you can buy a ticket for a conference.</p>");
      return;
    }

    // Create a cross-group transaction
    // TODO

    // Begin the transaction
    // TODO

    try {

      // Update the Ticket entity to set the status property to purchased
      // and to set the owner property to the mainEmail of the purchaser
      // TODO

      // Put the ticket back into the datastore
      // TODO

      // Decrease the value of the Conference entity's numTixAvailable property
      // TODO

      // Put the conference back into the datastore
      // TODO

      // Call addTicketToUser() to update the user's
      // tickets property to include the newly purchased ticket
      // as a string of the ticket's key.
      // Throws EntityNotFound if the user entity is not found
      EntityUtils.addValueToMVProperty(user, "tickets", ticketKeyString);

      // Put the user back into the datastore
      // TODO

      // Commit the transaction
      // TODO
    } catch (EntityNotFoundException e) {
      e.printStackTrace();
    } finally {
      // If the transaction is still active (not committed)
      // roll it back
      // TODO
    }

    // Redirect to the user profile page
    resp.sendRedirect("/userprofile");
  }


}
