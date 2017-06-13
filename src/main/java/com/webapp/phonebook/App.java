package com.webapp.phonebook;

import com.mongodb.*;

import spark.Spark;

import static spark.Spark.setIpAddress;
import static spark.Spark.setPort;

public class App {
    private static final String IP_ADDRESS =  "localhost";
    private static final int PORT = 8081;

    public static void main(String[] args) throws Exception {
        setIpAddress(IP_ADDRESS);
        setPort(PORT);
        Spark.staticFileLocation("/public");
        new ContactsResource(new ContactsService(mongo()));
        new GroupResource(new GroupService(mongo()));
    }

    private static DB mongo() throws Exception {
    	MongoClient mongoClient = new MongoClient();
    	DB db = mongoClient.getDB("admin");
        if (db.authenticate("root", "password".toCharArray())) {
            return db;
        } else {
            throw new RuntimeException("Not able to authenticate with MongoDB");
        }
    }
}
