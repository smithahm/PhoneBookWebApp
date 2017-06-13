package com.webapp.phonebook;
import com.google.gson.Gson;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GroupService {

    private final DB db;
    private final DBCollection collection;

    public GroupService(DB db) {
        this.db = db;
        this.collection = db.getCollection("groups");
    }

    public List<Group> findAll() {
        List<Group> groups = new ArrayList<Group>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            groups.add(new Group((BasicDBObject) dbObject));
        }
        return groups;
    }

    public void createNewGroup(String body) {
    	Group groups = new Gson().fromJson(body, Group.class);
        collection.insert(new BasicDBObject("name", groups.getName()).append("members", new ArrayList<Contacts>()));
    }

    public Group find(String id) {
        return new Group((BasicDBObject) collection.findOne(new BasicDBObject("_id", new ObjectId(id))));
    }

    public Object delete(String groupID) {
        return collection.remove(new BasicDBObject().append("_id", new ObjectId(groupID)));
    }
    
    public Group update(String groupID, String body) {
        Group groups = new Gson().fromJson(body, Group.class);
        collection.update(new BasicDBObject("_id", new ObjectId(groupID)), new BasicDBObject("$set", new BasicDBObject("name", groups.getName()).append("members", groups.getMembers())));
        return this.find(groupID);
    }
    
    public List<Contacts> getMembers(String id){
    	if( new Group((BasicDBObject) collection.findOne(new BasicDBObject("_id", new ObjectId(id)))).getMembers() != null)
    		return new Group((BasicDBObject) collection.findOne(new BasicDBObject("_id", new ObjectId(id)))).getMembers();
    	else
    		return new ArrayList<Contacts>();
    }

	public Group update(String groupID, String body, String contact) {
		 Group groups = new Gson().fromJson(body, Group.class);
	     collection.update(new BasicDBObject("_id", new ObjectId(groupID)), new BasicDBObject("$push", new BasicDBObject().append("members", contact)));
	     return this.find(groupID);
	}

}
