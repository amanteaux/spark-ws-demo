package org.devteam.web.filters;

import org.devteam.services.user.UserService;
import spark.Request;
import spark.Route;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import static spark.Spark.halt;

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
				// TODO refactor when https://github.com/perwendel/spark/pull/270 is accepted
				halt(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
				return null;
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
