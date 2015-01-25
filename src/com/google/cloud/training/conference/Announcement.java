package com.google.cloud.training.conference;

import java.util.Date;
import java.util.List;

import com.google.api.client.util.store.DataStoreFactory;
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
    Entity announcement = new Entity("Announcement");
    
    announcement.setProperty("content", content);
    announcement.setProperty("announceTime", new Date());

    DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
    datastoreService.put(announcement);

    MemcacheServiceFactory.getMemcacheService().put("latestAnnouncement", content, Expiration.byDeltaSeconds(3600));

  }
  
  /* Get the latest announcement.
   * First check memcache.
   * If no announcement found in memcache,check the datastore. 
   * If an announcement is found in the datastore,
   * save it in memcache.
   */
  public static String getLatestAnnouncement() {
    MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();

    String latestAnnouncement = (String) memcacheService.get("latestAnnouncement");

    // Check if the latestAnnouncment is null
    if (latestAnnouncement == null) {

      DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
      Query query = new Query("Announcement");
      query.addSort("announceTime", Query.SortDirection.DESCENDING);

      List<Entity> announcements = datastoreService.prepare(query).asList(FetchOptions.Builder.withLimit(1));

      // If we found any Announcement entities
      if (announcements.size() > 0) {

        MemcacheServiceFactory.getMemcacheService().put(
                "latestAnnouncement",
                announcements.get(0).getProperty("content"),
                Expiration.byDeltaSeconds(3600));

      } else {
        latestAnnouncement = "";
      }
    }
    // Return the latest announcement
    return latestAnnouncement;
  }

}
