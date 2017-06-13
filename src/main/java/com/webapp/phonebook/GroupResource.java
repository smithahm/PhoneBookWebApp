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

public class GroupResource {

    private static final String API_CONTEXT = "/api/v1";

    private final GroupService groupService;

    public GroupResource(GroupService groupService) {
        this.groupService = groupService;
        setupEndpoints();
    }

    private void setupEndpoints() {
        post(API_CONTEXT + "/groups", "application/json", new Route() {
			public Object handle(Request request, Response response) {
				groupService.createNewGroup(request.body());
			    response.status(201);
			    return response;
			}
		}, new JsonTransformer());

        get(API_CONTEXT + "/groups/:id/members", "application/json", new Route() {
			public Object handle(Request request, Response response) {
				return groupService.getMembers(request.params(":id"));
			}
		}, new JsonTransformer());

        get(API_CONTEXT + "/groups", "application/json", new Route() {
			public Object handle(Request request, Response response) {
				return groupService.findAll();
			}
		}, new JsonTransformer());

        delete(API_CONTEXT + "/groups/:id", "application/json", new Route() {
			public Object handle(Request request, Response response) {
				return groupService.delete(request.params(":id"));
			}
		});
        
        put(API_CONTEXT + "/groups/:id", "application/json", new Route() {
			public Object handle(Request request, Response response) {
				return groupService.update(request.params(":id"), request.body());
			}
		}, new JsonTransformer());
        
        put(API_CONTEXT + "/groups/:id/:contact", "application/json", new Route() {
			public Object handle(Request request, Response response) {
				return groupService.update(request.params(":id"), request.body(), request.params(":contact"));
			}
		}, new JsonTransformer());
    }

}
