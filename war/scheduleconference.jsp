<%@ include file="header.jsp" %>
<h1>Schedule a new conference</h1>
    
<!-- Write the HTML to display the form -->

<!--  Display buttons to allow the user to display their calendar in this  page -->
 <%-- include file="getuserscalendar.jsp" --%>

<!--  TODO set the action of the form -->
<form method=post>

  <p><b>What is the title of your conference? </b><input name="confName" size="30"/></p>
  
  <p><b>What is the topic of your conference?</b><select name="topic">
  <%
      // topicList is a list of topics such as 
      // {"Medical Innovations", "Programming Languages", "Web Technologies", "Movie Making"}
      // It is defined in constants.jsp
      for (String topic : topicList)
      {
    	  %><option value="<%= topic %>"><%= topic %> </option> <% 
      }
  %>
     </select>
  </p>

  <p><b>Enter a summary of your conference.</b><br></p>
     <p><textarea name="confDesc" rows="6" cols="100"> 
          Who should attend? What will they get out of it? 
        </textarea>
     </p>

  <p><b>Where would you like to hold your conference? </b><select name="city"> 
      <%
      // cityList is a list of cities such as 
      // {"London", "Chicago", "San Francisco", "Paris"}
      // It is defined in constants.jsp
       for (String city : cityList)
      {
          %><option value="<%= city %>"><%= city %> </option> <% 
      }
  %>
      </select></p>
      
  <p><b>What is the maximum number of attendees? </b>
  <input name="maxAttendees" value="5" /><i>Must be an integer</i></p>

  <p><b>What date does your conference start? </b><input name="startDate" type="date">
  
  <!--  Show a link to the user's calendar -->
  <a href="/calendarinfo" target="new_tab">[View my calendar events]</a><br></p> 
  
  <p><b>What date does your conference end? </b><input name="endDate" type="date"></p>
    
  <p><input type=submit value="Schedule conference" id=scheduleconference /></p>
  </form>

<%@ include file="footer.jsp" %>