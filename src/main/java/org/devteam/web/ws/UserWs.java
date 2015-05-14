package org.devteam.web.ws;

import com.google.common.base.Strings;
import org.devteam.services.user.User;
import org.devteam.services.user.UserService;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static spark.Spark.halt;

@Singleton
public class UserWs {

	private final UserService userService;

	@Inject
	public UserWs(UserService userService) {
		this.userService = userService;
	}

	public List<User> list(Request request, Response response) {
		return userService.list();
	}

	public User get(Request request, Response response) {
		return userService
				.fetch(request.params(":login"))
				.orElseGet(this::userNotFound);
	}

	public User add(Request request, Response response) {
		checkContentTypeFormUrlEncoded(request);

		String login = requiredParam(request, "login");
		userService.fetch(login).ifPresent(user ->
				halt(HttpServletResponse.SC_BAD_REQUEST, "A user already exists with this login")
		);

		return userService.save(
				requiredParam(request, "name"),
				login,
				requiredParam(request, "password")
		);
	}

	public User update(Request request, Response response) {
		checkContentTypeFormUrlEncoded(request);

		return userService
				.fetch(request.params(":login"))
				.map(userToUpdate -> userService.save(
						requiredParam(request, "name"),
						userToUpdate.getLogin(),
						requiredParam(request, "password")
				))
				.orElseGet(this::userNotFound);
	}

	public String delete(Request request, Response response) {
		Optional<User> userToDelete = userService.fetch(request.params(":login"));
		if(userToDelete.isPresent()) {
			userService.delete(userToDelete.get().getLogin());
			return "User deleted";
		}
		userNotFound();
		return null;
	}

	// internal

	private User userNotFound() {
		// TODO refactor when https://github.com/perwendel/spark/pull/270 is accepted
		halt(HttpServletResponse.SC_NOT_FOUND, "User not found");
		return null;
	}

	private String requiredParam(Request request, String name) {
		return Optional
				.ofNullable(request.queryParams(name))
				.orElseGet(() -> {
					// TODO refactor when https://github.com/perwendel/spark/pull/270 is accepted
					halt(HttpServletResponse.SC_BAD_REQUEST, "'"+name+"' is required");
					return null;
				});
	}

	private void checkContentTypeFormUrlEncoded(Request request) {
		String contentType = request.contentType();
		if(Strings.isNullOrEmpty(contentType) || !contentType.contains("application/x-www-form-urlencoded")) {
			halt(HttpServletResponse.SC_BAD_REQUEST, "Content-Type header must be 'application/x-www-form-urlencoded'");
		}
	}

}
