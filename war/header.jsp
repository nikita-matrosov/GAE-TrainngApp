<%@ page import="java.security.Principal" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="java.util.logging.*" %>
<%@ page import="com.google.appengine.api.memcache.*" %>

<%@ include file="constants.jsp" %>

<head><title>Conference Central</title>

<!-- Add the stylesheet -->
<link rel="stylesheet" type="text/css" href="css/mystyle.css"></link>
</head>
<html>

<body>

<%!
    private static Logger log = Logger.getLogger("header.jsp");
%>
    
<center><img src="images/header_collage.gif" width="100%" class="headerimage"></center>
    
<p class="topnav">
      <span class="nav-item"><a href="/">Home</a></span>
      <span class="nav-item"><a href="/listconferences">Upcoming Conferences</a></span>
      <span class="nav-item"><a href="/scheduleconference">Create Conference</a></span> 
      <span class="nav-item"><a href="/venues">Venues</a></span>
      <span class="nav-item"><a href="/userprofile">User Profile</a></span>

  <%
  	// Get a handle to the UserService
  	UserService userService = UserServiceFactory.getUserService();

  	// Get the logged in user
  	Principal userPrincipal = request.getUserPrincipal();

  	String requestUri = request.getRequestURI();
  	String loginLink = userService.createLoginURL(requestUri);
  	String logoutLink = userService.createLogoutURL(requestUri);

  	// If the user is not logged in, 
  	// display a "login" link, with css style class="nav-item"
	  if (userPrincipal == null) {
  %>
	<span="nav-item"> <a href="<%= loginLink %>">Sign In</a></span></p>
	<%
		}


  	// If the user is logged in as an admin, 
  	// display a link to the developer page (/developer)
  	if (userService.isUserAdmin()) {
  %>
	<p><span="nav-item"> <a href="/developer">Developer Page</a></span></p>
	<%
		}

  	// If the user is logged in, 
  	// display a "sign out" link, with css style class="nav-item"
  	if (userPrincipal != null) {
  	String mainEmail = userPrincipal.getName();
  %>
	<p>Hello, <%= mainEmail %></p>
	<p><span="nav-item"> <a href="<%= logoutLink %>">Sign Out</a></span></p>
	<%
		}
  %>
<hr>