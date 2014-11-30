package com.google.cloud.training.conference;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;

public class CalendarDetails {
  /*
   * Use the Calendar API to get a list of upcoming events for the AUTHENTICATED user
   */

  public static String getCalendarItems(Calendar service) throws IOException {

    // Get the primary calendar for the authenticated user
    CalendarListEntry calendarListEntry = service.calendarList().get("primary").execute();
    String calendarInfo = "<h2>Calendar for " + calendarListEntry.getSummary() + "</h2>";

    Events events = null;
    Date now = new Date();
    DateTime today = new DateTime(now);

    // Get the events from the calendar
    events = service.events().list("primary").setMaxResults(20).setTimeMin(today).execute();

    // Get the event items (the actual events)
    List<Event> items = events.getItems();

    // Iterate over the events, printing info about each one
    if (items != null) {
      for (Event event : items) {
        DateTime startTime = null;

        // Get the start time, if known
        // Single events use getDate() for the start time.
        // Recurring events use getDateTime() for the start time.
        if (event.getStart() != null) {
          if (event.getStart().getDate() != null) {
            startTime = event.getStart().getDate();
          } else {
            startTime = event.getStart().getDateTime();
          }
        }

        // Get the creator's email
        String orgEmail = "Organizer's email";
        if (event.getCreator() != null) {
          orgEmail = event.getCreator().getEmail();
        }

        // Print info about the event
        calendarInfo +=
            "<h3 style=\"color:blue\";>" + event.getSummary() + "</h3>" + "<p> organized by "
                + orgEmail + "</p>" + "<p>scheduled for " + startTime + "</p>";
      }
    }
    return calendarInfo;
  }

}
