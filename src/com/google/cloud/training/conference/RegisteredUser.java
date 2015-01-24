package com.google.cloud.training.conference;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class RegisteredUser {
  
  // Get a handle to the DatastoreService
  public static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  /*
   *  The constructor creates a RegisteredUser entity,
   *  sets its properties, and saves it in the datastore
   */
  public RegisteredUser (String mainEmail, String personName, 
     String notificationEmail, String[] topicArray) {
     
    // Create a RegisteredUser entity, specifying the mainEmail to use in the key
    // Check if the RegisteredUser is already in the datastore
    Key userKey = KeyFactory.createKey("RegisteredUser",  mainEmail);
    Entity userEntity;
    try {
      userEntity = datastore.get(userKey);
    }
    catch (EntityNotFoundException e) {
      userEntity = new Entity("RegisteredUser", mainEmail);
    }

    // Set properties on the RegisteredUser entity
    userEntity.setProperty("name", personName);
    userEntity.setProperty("mainEmail", mainEmail);
    userEntity.setProperty("notificationEmail", notificationEmail);
    if (topicArray != null) {
      userEntity.setProperty("interests", Arrays.asList(topicArray));
    }

    // Save the entity in the datastore 
    datastore.put(userEntity);
  }
  
  /*
   * Get the RegisteredUser from the datastore if the user
   * has already been saved 
   */
  public static Entity getUser (String mainEmail) throws EntityNotFoundException
  {
    // Get the RegisteredUser from the datastore if the user
    // has already been saved
    Key userKey = KeyFactory.createKey("RegisteredUser",  mainEmail);
    Entity user = datastore.get(userKey);
    return user;
  }

      
}

