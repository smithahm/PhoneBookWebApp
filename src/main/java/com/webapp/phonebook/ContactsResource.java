package com.webapp.phonebook;

import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;

public class ContactsResource {

    private static final String API_CONTEXT = "/api/v1";

    private final ContactsService contactService;

    public ContactsResource(ContactsService contactService) {
        this.contactService = contactService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(API_CONTEXT + "/contacts", "application/json", new Route() {
			public Object handle(Request request, Response response) {
				contactService.createNewContact(request.body());
			    response.status(201);
			    return response;
			}
		}, new JsonTransformer());

        get(API_CONTEXT + "/contacts/:id", "application/json", new Route() {
			public Object handle(Request request, Response response) {
				return contactService.find(request.params(":id"));
			}
		}, new JsonTransformer());

        get(API_CONTEXT + "/contacts", "application/json", new Route() {
			public Object handle(Request request, Response response) {
				return contactService.findAll();
			}
		}, new JsonTransformer());

        delete(API_CONTEXT + "/contacts/:id", "application/json", new Route() {
			public Object handle(Request request, Response response) {
				return contactService.delete(request.params(":id"));
			}
		});
        
        put(API_CONTEXT + "/contacts/:id", "application/json", new Route() {
			public Object handle(Request request, Response response) {
				return contactService.update(request.params(":id"), request.body());
			}
		}, new JsonTransformer());
    }

}
