package com.google.cloud.training.conference;

import java.util.ArrayList;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;


public class EntityUtils {
  
  /* 
   * Add a String value to a multi-valued property
   * 
   * This method adds the given value to the list of values
   * in the given property on the given entity. 
   * If the property has no values already, 
   * add the given value as the first value, 
   * otherwise add it to the existing values.
   */
    public static void addValueToMVProperty(Entity ent, String property, String value)
      throws EntityNotFoundException {
      
      ArrayList<String> valueList;
      // Check if the property alreqdy exists (which means it already has a value)
      if (ent.hasProperty(property)) {
        // Get the existing values as an ArrayList
        valueList = (ArrayList<String>) ent.getProperty(property);       
      } else {
        // This is the first value in the property
        valueList = new ArrayList<String>();
      }
      // Add the entity to the list of values for the property
      valueList.add(value);

      // Save the list of values back to the property on the RegisteredUser entity
      ent.setProperty(property, valueList);
    }

}
