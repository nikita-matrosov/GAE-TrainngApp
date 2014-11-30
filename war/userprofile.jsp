<%@ page import="com.google.appengine.api.users.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="java.security.Principal" %>
<%@ page import="com.google.appengine.api.datastore.Query.*" %>
<%@ page import="com.google.cloud.training.conference.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.DateFormat" %>

<%@ include file="header.jsp" %>

<!-- 
  This page displays the user's profile. 
  It includes a form to allow users to select topics that they are interested in.
  It displays the conferences that the user has scheduled, if any.
  It displays the conferences that the user has registered to attend, if any.
 -->

<H1>User Profile Page</H1>
<%
    //package com.google.cloud.training.conference;

    // TODO Get the logged in user's email.
    // Use userPrincipal, which is defined in header.jsp (which is included)
    String mainEmail = "username@company.com";  // Get the user's email dynamically
    
    // Set the main email as the default notification email address
    String notificationEmail = mainEmail;

    // Set the default name to the email address without the domain
    // For example, the name of appdeveloper@gmail.com would be appdeveloper
    String personName = mainEmail.split("@")[0];

    // Add a variable to hold the topics that the user is interested in
    ArrayList<String> interests = new ArrayList<String>();
    
    %>
    
    <h2>Create Your User Profile</h2>
    
 <!--  Write the HTML to display the form where users enter their preferences -->
        <p><b>Your main email is: </b> <%= mainEmail %> </p> 
        <!--  TODO set the action of the form -->
        <form method=post>
            <p><b>What is your name?</b></p>
            <input type=text value="<%= personName %>" name=personname size="50"/>
            
            <p><b>What conference topics are you interested in?</b></p>
            
            <!--  Display a list of topics -->
            <!--  For each topic, check if the user is already interested in it.
                  If so, display the select menu option as selected -->
            <select name="topics" multiple size=<%= topicList.length %>>  
             <%
             // topicList is a list of topics defined in constants.jsp, such as 
             // {"Medical Innovations", "Programming Languages", "Web Technologies", "Movie Making"}
             
             String selected = "";
             for (String topic : topicList)
             {   selected = "";
                  // Is the user interested in this topic?
                  // interests is a String of the user's preselected interests, if any
                   if ((interests !=null ) && (interests.contains(topic))){
                    selected = " selected ";
                   } 
                  // Display the <option> menu item for the current topic
                  %><option value="<%= topic %>" <%= selected %> ><%= topic %> </option> <% 
             } %>
            </select>
            
            <p><b>What is your email for receiving notifications?</b></p>
            <input type=text value="<%=notificationEmail %>" name=username size="50"/></p>
            <input type=submit value="Update my user profile" id=updateprofile />
         </form> 
       
         
<%@ include file="footer.jsp" %>