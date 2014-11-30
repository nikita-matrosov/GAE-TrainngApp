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
  
  // TODO Get a handle to the DatastoreService
  
  /*
   *  The constructor creates a RegisteredUser entity,
   *  sets its properties, and saves it in the datastore
   */
  public RegisteredUser (String mainEmail, String personName, 
     String notificationEmail, String[] topicArray) {
     
    // Create a RegisteredUser entity, specifying the mainEmail to use in the key
    // TODO

    // Set properties on the RegisteredUser entity
    // Set the properties:
    // name
    // mainEmail
    // notificationEmail
    // interests
    // TODO

    // Save the entity in the datastore 
    // TODO
  }
  
  /*
   * Get the RegisteredUser from the datastore if the user
   * has already been saved 
   */
  /*
  public static Entity getUser (String mainEmail) throws EntityNotFoundException
  {   
    // TODO implement this method
  }
  */
  
      
}

