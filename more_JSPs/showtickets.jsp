<%@ page import="java.util.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="com.google.appengine.api.datastore.Query.*" %>

<%@ include file="header.jsp" %>

<h1>Show Tickets</h1>

<%
  // The request includes the conference name and the conference key string
  String confKeyString = request.getParameter("confKeyString");
  String confName = request.getParameter("confName");
  %>
  
<p>Conference name is <%= confName %> </p>

<%
	// Create the query to get the first 20 tickets for the given conference

    // Get a handle to the datastore service
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    
    // Create a query to get entities of kind Ticket TODO
    
    // Create a filter to check that the tickets are available TODO
    
    // Create a filter to check that the ticket is for the applicable conference TODO

    // Combine the filters TODO
   
    // Attach the combination filter to the query TODO

    // Sort the tickets in descending number TODO

    // Get the first 20 tickets TODO
    List<Entity> tickets = ...; // FIX

    // Print info about each ticket
	for (Entity ticket : tickets) {
		Long ticketNumber = (Long) ticket.getProperty("ticketNumber");
		String ticketStatus = (String) ticket.getProperty("status");
		String ticketConf = (String) ticket.getProperty("confName");

		// Get a string of the ticket's key
		Key ticketKey = ticket.getKey();
		String ticketKeyString = KeyFactory.keyToString(ticketKey);%> 
        <!--  Display information about each ticket -->      
        <p>Ticket number 
        <%= ticketNumber %>  for conference <%= ticketConf %> is <%= ticketStatus %>  
        <a href="/buyticket?ticketKeyString=<%= ticketKeyString %>"> [Purchase] </a><%
   }  %>
  
    