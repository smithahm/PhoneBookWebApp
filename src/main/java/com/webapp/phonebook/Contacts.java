package com.webapp.phonebook;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

public class Contacts {
	  private String id;
	  private String firstName;
	  private String lastName;
	  private String emailID;
	  private String phoneNumber;
	  private List<Group> groups;

	    public Contacts(BasicDBObject dbObject) {
	        this.id = ((ObjectId) dbObject.get("_id")).toString();
	        this.firstName = dbObject.getString("firstName");
	        this.lastName = dbObject.getString("lastName");
	        this.emailID = dbObject.getString("emailID");
	        this.phoneNumber = dbObject.getString("phoneNumber");
	        this.groups = (List<Group>) dbObject.get("groups");
	    }

	    public String getfirstName() {
	        return firstName;
	    }

	    public String getLastName() {
	        return lastName;
	    }
	    public String getEmailID() {
	        return emailID;
	    }
	    public String getPhone() {
	        return phoneNumber;
	    };
	    
	    public List<Group> getGroups(){
	    	return this.groups;
	    }

}
