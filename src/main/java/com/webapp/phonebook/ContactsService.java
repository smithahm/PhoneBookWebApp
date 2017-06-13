package com.webapp.phonebook;
import com.google.gson.Gson;
import com.mongodb.*;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ContactsService {

    private final DB db;
    private final DBCollection collection;

    public ContactsService(DB db) {
        this.db = db;
        this.collection = db.getCollection("contacts");
    }

    public List<Contacts> findAll() {
        List<Contacts> contacts = new ArrayList<Contacts>();
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            DBObject dbObject = dbObjects.next();
            contacts.add(new Contacts((BasicDBObject) dbObject));
        }
        return contacts;
    }

    public void createNewContact(String body) {
    	Contacts contacts = new Gson().fromJson(body, Contacts.class);
        collection.insert(new BasicDBObject("firstName", contacts.getfirstName()).append("lastName", contacts.getLastName()).append("emailID", contacts.getEmailID()).append("phoneNumber", contacts.getPhone()));
    }

    public Contacts find(String id) {
        return new Contacts((BasicDBObject) collection.findOne(new BasicDBObject("_id", new ObjectId(id))));
    }

    public Object delete(String contactId) {
        return collection.remove(new BasicDBObject().append("_id", new ObjectId(contactId)));
    }
    
    public Contacts update(String contactId, String body) {
        Contacts contacts = new Gson().fromJson(body, Contacts.class);
        collection.update(new BasicDBObject("_id", new ObjectId(contactId)), new BasicDBObject("$set", new BasicDBObject("firstName", contacts.getfirstName()).append("lastName", contacts.getLastName()).append("emailID", contacts.getEmailID()).append("phoneNumber", contacts.getPhone())));
        return this.find(contactId);
    }

}
