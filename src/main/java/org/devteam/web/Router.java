package org.devteam.web;

import static spark.Spark.get;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.devteam.web.filters.AuthenticationFilter;
import org.devteam.web.filters.JsonFilter;
import org.devteam.web.ws.user.UserWs;

import spark.Route;

@Singleton
public class Router {
	
	private final UserWs userWs;
	
	private final JsonFilter jsonFilter;
	private final AuthenticationFilter authenticationFilter;
	
	@Inject
	public Router(UserWs userWs, JsonFilter jsonFilter, AuthenticationFilter authenticationFilter) {
		this.userWs = userWs;
		this.jsonFilter = jsonFilter;
		this.authenticationFilter = authenticationFilter;
	}

	public void configureRoutes() {
		get("/user/", authenticatedAndJsonResponse(userWs::list));
	}

	private Route authenticatedAndJsonResponse(Route route) {
		return jsonFilter.jsonResponse(authenticationFilter.authenticate(route));
	}
	
}
