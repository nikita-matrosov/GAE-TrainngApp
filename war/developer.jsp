<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="com.google.appengine.api.datastore.Query.*" %>
<%@ include file="header.jsp" %>

<h1>Developer page</h1>

<!--  This page includes tools that are useful while you are developing
your application -->

<%
    // Get the logged in user
    Principal userPrinc = request.getUserPrincipal();

    // Get the logged in user's email
    String mainEmail = userPrinc.getName();
%>

<%!
// Delete every single entity of the given kind
 void deleteTickets (String kind) {   
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();   
    
    // Create a filter to get all the entities of the applicable kind
    Query tixQuery = new Query(kind);
    PreparedQuery results = datastore.prepare(tixQuery);
    
    // Delete all the entities
    for (Entity ticket : results.asIterable()) {
      datastore.delete(ticket.getKey());
    }
}
%>

<H2>Welcome <%= mainEmail %></H2>

<p>This page is for developers who are working on the application.</p>

<!--  Check if the page was invoked with the "deleteall" parameters --> 
<% 
// Delete all the entities of the given kind
String deleteall=request.getParameter("deleteall");
String kind = request.getParameter("kind");
   if (deleteall != null) {
	   if (deleteall.equals("yes")) {
         deleteTickets(kind);

// Forward back to /developers.jsp so that the "deleteall" parameter is not reinvoked
// if we refresh the page
   response.sendRedirect("/developer");
	   }
   }
   %>
   
<h3>Delete Entities with a sledge hammer</h3>
<p>This form lets you delete ALL entities of a given KIND</p>
<form action="/developer" method="POST">
   <p>Kind: <input value="Ticket" name="kind">
   <select name="deleteall">
       <option value="no">NO, leave my entities alone!!!!</option>
       <option value="yes">Delete all entities of this kind</option>
   </select>
<input type="submit" value="submit" />
</form>

<%@ include file="footer.jsp" %>