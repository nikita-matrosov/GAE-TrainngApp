package com.google.cloud.training.conference;

import java.util.logging.*;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import static com.google.appengine.api.taskqueue.TaskOptions.Builder.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.memcache.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import java.util.ArrayList;

public class Conference {

  private static Logger log = Logger.getLogger("Conference");

  public Entity conferenceEntity;
  String conferenceKeyString;
  Key conferenceKey;
  String confName;

  // Get a handle to the DatstoreService
  private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

  public Conference(String mainEmail, String confName, String confDesc, String topic, String city,
      String maxAttendees, String startDateString, String endDateString) {

    // Convert maxAttendees to a Long before saving it
    Long mL = Long.parseLong(maxAttendees);

    // Convert the startDate and endDate to Dates
    // The format is 2013-04-26

    Date startDate;
    try {
      startDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(startDateString);
    } catch (ParseException e) {
      startDate = null;
    }

    Date endDate;
    try {
      endDate = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(endDateString);
    } catch (ParseException e) {
      endDate = null;
    }
    
    // Create an entity of kind "Conference"
    Entity confEntity = new Entity("Conference", confName);
    
    // Set the following properties on confEntity:
    // confName, confDesc, organizer, topic,city, 
    // maxAttendees, numTixAvailable
    // startDate, endDate

    // Save the organizer's mainEmail.
    // Initially, numTixAvailable and maxAttendees are the same value
    // both are Longs.
    confEntity.setProperty("confName", confName);
    confEntity.setProperty("confDesc", confDesc);
    confEntity.setProperty("organizer", mainEmail);
    confEntity.setProperty("topic", topic);
    confEntity.setProperty("city", city);
    confEntity.setProperty("maxAttendees", maxAttendees);
    confEntity.setProperty("numTixAvailable", maxAttendees);
    confEntity.setProperty("startDate", startDate);
    confEntity.setProperty("endDate", endDate);

    // Associate the Conference entity with the Conference object
    conferenceEntity = confEntity; 

    // Save the entity in the datastore
    Key confKey = saveConference();

  }

  /*
   * This constructor gets the Conference entity from the datastore.
   */
  public Conference(String confKeyString) throws EntityNotFoundException {
    Key confKey = KeyFactory.stringToKey(confKeyString);
    Entity confEntity = datastore.get(confKey);
    // Let the Conference object keep track of its corresponding Entity
    setConferenceEntity(confEntity);
  }

  public Entity getConferenceEntity() {
    return conferenceEntity;
  }

  public void setConferenceEntity(Entity confEntity) {
    conferenceEntity = confEntity;
  }

// Save the conference in the datastore
  public Key saveConference() {
    Key mykey = datastore.put(conferenceEntity);

    return mykey;
  }

}
