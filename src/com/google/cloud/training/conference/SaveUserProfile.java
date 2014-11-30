package com.google.cloud.training.conference;

import java.io.IOException;
import javax.servlet.http.*;
import java.security.Principal;

@SuppressWarnings("serial")
public class SaveUserProfile extends HttpServlet {
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    // Get the logged in user's email
    Principal userPrincipal = req.getUserPrincipal();
    String mainEmail = userPrincipal.getName();

    // Get the values submitted by the form
    // TODO

    // Create and save a RegisteredUser entity.
    // Use the RegisteredUser class.
    // TODO

    // Redirect back to the User Profile page
    resp.sendRedirect("/userprofile");
  }

  // If a user come to this page as a GET, redirect to the User Profile page
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    resp.sendRedirect("/userprofile");
  }
}
