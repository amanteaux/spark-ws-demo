package org.devteam.web;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.SparkBase.staticFileLocation;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.devteam.web.filters.AuthenticationFilter;
import org.devteam.web.filters.JsonFilter;
import org.devteam.web.ws.SupervisionWs;
import org.devteam.web.ws.UserWs;

import spark.Route;

/**
 * Configure the web application routes
 */
@Singleton
public class Router {

	private final UserWs userWs;
	private final SupervisionWs supervisionWs;

	private final JsonFilter jsonFilter;
	private final AuthenticationFilter authenticationFilter;

	@Inject
	public Router(UserWs userWs, SupervisionWs supervisionWs,
				  JsonFilter jsonFilter, AuthenticationFilter authenticationFilter) {
		this.userWs = userWs;
		this.supervisionWs = supervisionWs;
		this.jsonFilter = jsonFilter;
		this.authenticationFilter = authenticationFilter;
	}

	public void start() {
		// angular app
		staticFileLocation("/web");
		
		// authenticated
		get("/user", authenticatedAndJsonResponse(userWs::list));
		get("/user/:login", authenticatedAndJsonResponse(userWs::get));
		post("/user", authenticatedAndJsonResponse(userWs::add));
		put("/user/:login", authenticatedAndJsonResponse(userWs::update));
		delete("user/:login", authenticationFilter.authenticate(userWs::delete));

		// public
		get("/status", jsonFilter.jsonResponse(supervisionWs::serverStatus));
	}

	private Route authenticatedAndJsonResponse(Route route) {
		return jsonFilter.jsonResponse(authenticationFilter.authenticate(route));
	}

}
