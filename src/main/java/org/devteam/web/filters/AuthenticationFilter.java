package org.devteam.web.filters;

import static spark.Spark.halt;

import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;

import org.devteam.services.user.UserService;

import spark.Request;
import spark.Route;

@Singleton
public class AuthenticationFilter {

	private final UserService userService;

	@Inject
	public AuthenticationFilter(UserService userService) {
		this.userService = userService;
	}

	public Route authenticate(Route action) {
		return ((request, response) -> {
			if(isAuthenticated(request)) {
				return action.handle(request, response);
			} else {
				return halt(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
			}
		});
	}

	private boolean isAuthenticated(Request request) {
		return Optional
				.ofNullable(request.headers("X-User-Login"))
				.flatMap(login -> Optional
									.ofNullable(request.headers("X-User-Password"))
									.map(password -> new Identifiers(login, password)))
				.flatMap(identifiers -> userService.authenticate(identifiers.login, identifiers.password))
				.isPresent();
	}

	private static class Identifiers {
		final String login;
		final String password;

		Identifiers(String login, String password) {
			this.login = login;
			this.password = password;
		}
	}

}
