<%@ page import="com.google.appengine.api.datastore.*"%>
<%@ page import="com.google.appengine.api.taskqueue.*"%>
<%@ page import="com.google.appengine.api.taskqueue.Queue"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.concurrent.TimeUnit"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="com.google.appengine.api.taskqueue.TransientFailureException"%>
<%@ page import="com.google.apphosting.api.ApiProxy.ApiDeadlineExceededException"%>

<%@ include file="header.jsp"%>

<%!
    private static Logger logger = Logger.getLogger("reviewconferences.jsp");
%>

<%
	// This method pulls the conferences from the queue that are waiting
    // to be reviewed, and generates an HTML table row for each one.
    String reviewNewConferences() {
 
        String content = "";

        // TODO Get a handle to the DatstoreService

        // TODO Get the review-conference-queue queue

        List<TaskHandle> tasks = null;

        // Lease the tasks and process them
        try {
            // TODO Lease 4 tasks for 10 seconds
            tasks =

            // For each task, write a table row so that the admin can review the conference details
            for (TaskHandle leasedTask : tasks) {
                // TODO Get the confKeyString from the payload, and convert it to a Key

                try {
                    // TODO Get the Conference entity from the datastore
                    
                    // TODO Get the name of the task
                    
                    // Get the HTML for the table row that shows info about the conference
                    content += addTableRow(conference, taskName);

                } catch (EntityNotFoundException e) {
                    logger.info("Conference entity is missing");
                }
            } // ends for loop          
        } catch (TransientFailureException e) {
            logger.info("TransientFailureException encountered");
        } catch (ApiDeadlineExceededException e) {
            logger.info("ApiDeadlineExceededException encountered");
        }

        // Return the HTML for the table rows
        return content;
    }


// This function that deletes the named task from the pull queue
String deleteReviewTask(String taskName, String confName) {

     // TODO Get the queue (the name of the queue is review-conference-queue)
     
     // TODO Delete the task whose name is taskName
     // If the deletion is successful, return a confirmation message
     // otherwise return the message:
     // <p>Conference  " + confName"  was not reviewed and approved. 
     // Perhaps someone else has already approved it?</p>
}


// Return a table that lists conferences that have not yet been audited manually
String displayNewConferencesTable () {
	// Start the table
    String content = getTableIntro();
 
    // Get the conferences to review
    content += reviewNewConferences();
    
    // End the table
    content += getTableEnd(); 
    
    return content;
}

String getTableIntro() {
    // Write the instructions to the admin to review the conference
    String confForm = "<p>Every new conference must be reviewed by an administrator.</p>"
            + "<p>Review the conferences listed here. Check that the details are complete."
            + " Send email to the organizer to provide a personal touch, and to start the conversation about billing."
            + " When you have completed the preliminary approval, approve the conference.</p>";

    // Write the header for the table that lists the conferences that need to be reviewed
    confForm += "<table border=\"1\" cellpadding=\"5\">"
            + "<tr><th>Conference Name</th><th>" + "City</th><th>"
            + "Organizer</th><th>" + "Start Date</th><th>"
            + "End Date</th><th>" + "MaxAttendees</th><th>"
            + "Review and approve</th></tr>\n";
    return confForm;
}

String getTableEnd() {
    return "</table>";
}

/*
* Write a table row containing information about a conference.
*/
    String addTableRow(Entity conference, String taskName) {
        String confName = (String) conference.getProperty("confName");
        String city = (String) conference.getProperty("city");
        String startDateString = "";
        Date startDate = (Date) conference.getProperty("startDate");
        if (startDate != null) {
            startDateString = DateFormat.getDateInstance(DateFormat.MEDIUM)
                    .format(startDate);
        }
        String endDateString = "";
        Date endDate = (Date) conference.getProperty("endDate");
        if (endDate != null) {
            endDateString = DateFormat.getDateInstance(DateFormat.MEDIUM)
                    .format(endDate);
        }
        Long maxAttendees = (Long) conference.getProperty("maxAttendees");

        String organizer = (String) conference.getProperty("organizer");

        // Display the conference details in a table row.
        // Include hidden elements to track the taskName and the confName.
        // The last cell includes a form that is activated when the reviewer
        // submits "Review completed" button
        String confRow = "<tr><td>"
                + confName
                + "</td><td>"
                + city
                + "</td><td>"
                + organizer
                + "</td><td>"
                + startDateString
                + "</td><td>"
                + endDateString
                + "</td><td>"
                + maxAttendees
                + "</td><td>"
                + "<form action=\"reviewconferences\">"
                + "<input type=\"hidden\" name=\"taskName\" value=\""
                + taskName
                + "\">"
                + "<input type=\"hidden\" name=\"confName\" value=\""
                + confName
                + "\">"
                + "<input type=\"submit\" name=\"reviewdone\" value=\"Review completed\" /></td></tr></form>";
        return confRow;
    }
%>
  
<!--  HTML part of the page, to display the conferences that need to be reviewed -->
<h3>Review Conferences</h3>

<!--  Check if the page was invoked with the "reviewconference" parameters -->
<%
// Review new conferences
String reviewconfs=request.getParameter("reviewconference");
 if (reviewconfs != null) {
       %>
<%= displayNewConferencesTable() %>
<%} %>

<!--  Check if the page was invoked with the "reviewdone" parameter -->
<%
// Review new conferences
String reviewdone=request.getParameter("reviewdone");
 if (reviewdone != null) {
  String confName = request.getParameter("confName");
  String taskName = request.getParameter("taskName");
       %>
<%= deleteReviewTask(taskName, confName) %>
<%} %>


<p>Check this button to list conferences that need to be reviewed</p>

<form method="post" action="/reviewconferences">
 <p>
  <input type="submit" value="Review New Conferences" name="reviewconference" />
 </p>
</form>

<%@ include file="footer.jsp"%>
