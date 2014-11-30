package com.google.cloud.training.conference;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;


public class Announcement {

  /* Create an Announcement entity.
   * Set its content property to the passed in string.
   * Save the announcement in memcache and in the datastore.
   */
  public static void setLatestAnnouncement(String content) {
    // TODO Create an Announcement entity
    
    // TODO set the content property

    // TODO set the announceTime property to the current time (as a Date)
    announceEntity.setProperty("announceTime", new Date());
    
    // TODO Get a handle to the DatastoreService
    
    // TODO save the Announcement entity in the datastore

    // TODO Get a handle to the Memcache Service

    // TODO Put the latest announcement in memcache, with an expiration of 1 hour (3600 seconds)

  }
  
  /* Get the latest announcement.
   * First check memcache.
   * If no announcement found in memcache,check the datastore. 
   * If an announcement is found in the datastore,
   * save it in memcache.
   */
  public static String getLatestAnnouncement() {
    // TODO Get a handle to the Memcache Service
    
    // TODO get the latestAnnouncement value from memcache
    String latestAnnouncement = ...

    // Check if the latestAnnouncment is null
    if (latestAnnouncement == null) { 
      
      // TODO Set a default value for the latestAnouncement

      // TODO Get a handle to the datastore service

      // TODO Create a Query to get all the Announcements

      // TODO Sort the Query by descending announceTime

      // TODO Submit a query to get the top result

      // If we found any Announcement entities
      if (announcements.size() > 0) {

        // TODO get the first one in the list of results
        
        // TODO get its content property.

        // TODO Put the latestAnnouncement into memcache

      }
    }
    // Return the latest announcement
    return latestAnnouncement;
  }

}
