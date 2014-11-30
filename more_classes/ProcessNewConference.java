package com.google.cloud.training.conference;

import java.io.IOException;
import javax.servlet.http.*;

import com.google.appengine.api.backends.BackendServiceFactory;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/*
 * This class processes newly scheduled conferences to perform additional tasks
 */

public class ProcessNewConference extends HttpServlet {

  /*
   * doPost() orchestrates the tasks that need to be performed on the newly scheduled conference
   */
  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

    // Get the confKeyString value from the request
    String confKeyString = req.getParameter("confKeyString");

    try {
      // Get the Conference
      Conference conference = new Conference(confKeyString);
      Entity confEntity = conference.getConferenceEntity();

      // Announce the conference
      announceConference(confEntity);

      // Create a task to email people who might want to attend the conference
      createTasktoEmailPotentialAttendees(confKeyString);

      // Put the conference in a pull queue where it will wait to be reviewed
      sendConfToPullQueueForReview(confKeyString);

    } catch (EntityNotFoundException e) {
      e.printStackTrace();
    }
  }

  /*
   * When a new conference is scheduled, the application displays an announcement at the top of
   * every page.
   */
  public void announceConference(Entity confEntity) {

    // TODO fill in this method
  }


  /*
   * Add a push task to a backend queue that notifies people who might be interested in the new
   * conference
   */
  public void createTasktoEmailPotentialAttendees(String confKeyString) {
    // TODO Create the TaskOptions for the task

    // TODO Set the confKeyString as a param of the TaskOptions

    // TODO Set the Host header to use the backend named notify-backend
    // (The backend is configured in backend.xml)

    // TODO Add the task to the queue with the given options.
    // (The queue is configured in queues.xml)

    // TODO Add the TaskOptions to the queue
  }

  /*
   * Add the conference to a pull queue where it will wait to be reviewed by an application
   * administrator
   */
  public void sendConfToPullQueueForReview(String confKeyString) {
    // TODO Get the queue

    // TODO Create the TaskOptions.
    // Set the method to pull and set the payload to the confKeyString

    // TODO Add the task options to the queue
  }


}
