package com.webapp.phonebook;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;

public class Group {
	private String name;
	private String id;
	private List<Contacts> members;
	
	public Group(BasicDBObject dbObject){
		    this.id = ((ObjectId) dbObject.get("_id")).toString();
	        this.name = dbObject.getString("name");
	        this.members = (List<Contacts>) dbObject.get("members");
	    }

	    public String getName() {
	        return name;
	    }
	    
	    public List<Contacts> getMembers(){
	    	return this.members;
	    }

}
