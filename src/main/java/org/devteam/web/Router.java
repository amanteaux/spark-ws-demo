package org.devteam.web;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.devteam.web.filters.AuthenticationFilter;
import org.devteam.web.filters.JsonFilter;
import org.devteam.web.ws.SupervisionWs;
import org.devteam.web.ws.UserWs;

import spark.Route;
import spark.Service;

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
		Service sparkServer = Service.ignite();

		// angular app
		sparkServer.staticFileLocation("/web");

		// TODO the routes declaration should be fluent
		// authenticated
		sparkServer.get("/user", authenticatedAndJsonResponse(userWs::list));
		sparkServer.get("/user/:login", authenticatedAndJsonResponse(userWs::get));
		sparkServer.post("/user", authenticatedAndJsonResponse(userWs::add));
		sparkServer.put("/user/:login", authenticatedAndJsonResponse(userWs::update));
		sparkServer.delete("user/:login", authenticationFilter.authenticate(userWs::delete));

		// public
		sparkServer.get("/status", jsonFilter.jsonResponse(supervisionWs::serverStatus));
	}

	private Route authenticatedAndJsonResponse(Route route) {
		return jsonFilter.jsonResponse(authenticationFilter.authenticate(route));
	}

}
