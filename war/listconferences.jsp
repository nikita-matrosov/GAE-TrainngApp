<%@ page import="java.util.*" %>
<%@ page import="com.google.appengine.api.datastore.*" %>
<%@ page import="com.google.appengine.api.datastore.Query.*" %>
<%@ page import="java.text.DateFormat" %>

<%@ include file="header.jsp" %>

<%!
    // Define a function to print the table heading
    String startTable() {
        return ("<p><table cellpadding=\"5px\" border=\"1\">"
                + "<tr><th>Conference Title</th><th width=\"25%\">Description</th>"
                + "<th>Organizer</th><th>Category</th>"
                + "<th>City</th><th>Start Date</th>"
                + "<th>End Date</th><th>Max Attendees</th><th>Buy ticket</th>"
                + "<th>Conf Key</th></tr>");
    }

    String endTable() {
        return ("</table><br /><br /></p>");
    }
%>

<%!
    // Define a function to return a table row with info about a conference
    String formatConferenceRow(String confName, String confDesc, String topic, String organizer,
                               String city, String startDate, String endDate, String maxAttendees, String confKeyString) {
        // Define the action for the Buy Ticket Link TODO 5
        String action = "";
        String confString =
                "<tr><td>" + confName + "</td><td>" + confDesc + "</td><td>" + organizer
                        + "</td><td>" + topic + "</td><td>"
                        + city + "</td><td>" + startDate + "</td>" + "<td>" + endDate + "</td><td>"
                        + maxAttendees + "</td><td><a href=\"" + action + "\"</a>Buy Ticket<td>"
                        + confKeyString + "</td></tr>";
        return confString;
    }
%>


<%!
    // Define a function to get the properties of a conference
    String printConferenceRow(Entity conference) {

        // Get the entity's key
        Key confKey = conference.getKey();
        // Get a string of the key
        String confKeyString = KeyFactory.keyToString(conference.getKey());

        // Get the other properties, confName, confDesc and so on
        String confName = (String) conference.getProperty("confName");
        String confDesc = (String) conference.getProperty("confDesc");
        String topic = (String) conference.getProperty("topic");
        String city = (String) conference.getProperty("city");
        String organizer = (String) conference.getProperty("organizer");

        // Get the start and end date.
        // Check the dates exist, and then convert them to a printable format.
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

        // maxAttendees is stored as a long; convert to a string
        String maxAttendees = conference.getProperty("maxAttendees").toString();

        // Get the HTML for a row that display info about the conference
        String confRow = formatConferenceRow(confName, confDesc, topic,
                organizer, city, startDateString, endDateString, maxAttendees,
                confKeyString);
        return confRow;
    }
%>

<%!
    // Display the query results.
// Iterate over each result and print it out as a table row.
    String displayQueryResults(String tableHeading, PreparedQuery results) {
        //  Write the table heading
        String content = "<h3>" + tableHeading + "</h3>";
        content += startTable();
        // Iterate over the results, printing one row per conference
        if (results != null) {
            for (Entity conference : results.asIterable()) {
                content += printConferenceRow(conference);
            }
        }
        // end the table
        content += endTable();
        return content;
    }%>

<h1>List Conferences</h1>

<%
    // Get a handle to the datastore service
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    // *** Get all conferences, sorted alphabetically ***
    String tableHeading = "<h3>All Conferences Sorted Alphabetically</h3>";

    // Create the Query to get all the conferences
    Query confQuery = new Query("Conference");
    // Add a sort order to the query to sort by ascending values of confName
    confQuery.addSort("confName", SortDirection.ASCENDING);
    // Submit the query
    PreparedQuery results = datastore.prepare(confQuery);
%>

<!-- Display the results -->
<%=displayQueryResults(tableHeading, results) %>

<% // *** Show conferences in London ***
    tableHeading = "<h3>All Conferences in London </h3>";

    // Add a filter to your query to filter by city = "London"
    Query.Filter cityFilter = new FilterPredicate("city", FilterOperator.EQUAL, "London");
    confQuery.setFilter(cityFilter);
    // Submit the query
    results = datastore.prepare(confQuery);
%>
<!-- Display the results -->
<%=displayQueryResults(tableHeading, results) %>

<% // *** Show Programming Conferences in London ***
    tableHeading = "<h3>Programming Conferences in London </h3>";

    // Create a filter to your query to filter by topic = "Programming Languages"
    Query.Filter topicFilter = new FilterPredicate("topic", FilterOperator.EQUAL, "Programming Languages");

    // Create the composite filter
    Query.Filter comboFilter = CompositeFilterOperator.and(cityFilter, topicFilter);
    // Add the composite filter to the query
    confQuery.setFilter(comboFilter);
    // Submit the query
    results = datastore.prepare(confQuery);
%>
<!-- Display the results -->
<%=displayQueryResults(tableHeading, results) %>


<% // *** Filter on maxAttendees ***

    // Add a filter to your query to filter for maxAttendees >= 50
    tableHeading = "<h3>All Conferences with More than 50 Attendees</h3>";

    Query confSecondQuery = new Query("Conference").addSort("maxAttendees", SortDirection.DESCENDING);
    // Create the filter for maxAttendees
    Query.Filter maxAttendeesFilter = new FilterPredicate("maxAttendees", FilterOperator.GREATER_THAN, 50l);
    // Add the filter to the query
    confSecondQuery.setFilter(maxAttendeesFilter);
    // Submit the query
    results = datastore.prepare(confSecondQuery);
%>
<!-- Display the results -->
<%=displayQueryResults(tableHeading, results) %>

<% // *** Show Programming Conferences in London ***
    // organized by me
    tableHeading = "<h3>Programming Conferences in London organized by ME</h3>";

    // Create a filter to your query to filter by topic = "Programming Languages"
    Query.Filter topicFilterCool =
            new FilterPredicate("topic", FilterOperator.EQUAL, "Programming Languages");

    // Create a filter to your query to filter by organizer
    Query.Filter organizerFilter =
            new FilterPredicate("organizer", FilterOperator.EQUAL, "nikita.matrosov@teamdev.com");

    // Create the composite filter
    Query.Filter comboFilterCool =
            CompositeFilterOperator.and(cityFilter, topicFilterCool, organizerFilter);

    // Add the composite filter to the query
    confQuery.setFilter(comboFilterCool);

    // Submit the query
    results = datastore.prepare(confQuery);
%>
<!-- Display the results -->
<%=displayQueryResults(tableHeading, results) %>


<%@ include file="footer.jsp" %>