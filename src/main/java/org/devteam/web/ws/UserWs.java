package org.devteam.web.ws;

import static spark.Spark.halt;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;

import org.devteam.services.user.User;
import org.devteam.services.user.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;

import spark.Request;
import spark.Response;

@Singleton
public class UserWs {

	private final UserService userService;
	private final ObjectMapper jsonMapper;

	@Inject
	public UserWs(UserService userService, ObjectMapper jsonMapper) {
		this.userService = userService;
		this.jsonMapper = jsonMapper;
	}

	public List<User> list(Request request, Response response) {
		return userService.list();
	}
	
	public User add(Request request, Response response) {
		UserData userData = parseUser(request);

		userService.fetch(userData.login).ifPresent(user ->
				halt(HttpServletResponse.SC_BAD_REQUEST, "A user already exists with this login")
		);

		return userService.create(
				userData.login,
				userData.name,
				userData.password
		);
	}

	public User get(Request request, Response response) {
		return userFromPathLogin(request)
				.orElseGet(this::userNotFound);
	}

	public User update(Request request, Response response) {
		return userFromPathLogin(request)
				.map(userToUpdate -> {
					UserData userData = parseUser(request);
					return userService.update(
						userToUpdate.getLogin(),
						userData.login,
						userData.name,
						userData.password
					);
				})
				.orElseGet(this::userNotFound);
	}

	public String delete(Request request, Response response) {
		Optional<User> userToDelete = userFromPathLogin(request);
		if(userToDelete.isPresent()) {
			userService.delete(userToDelete.get().getLogin());
			return "User deleted";
		}
		userNotFound();
		return null;
	}

	// internal
	
	private Optional<User> userFromPathLogin(Request request) {
		return userService.fetch(request.params(":login"));
	}

	private User userNotFound() {
		// TODO refactor when https://github.com/perwendel/spark/pull/270 is accepted
		halt(HttpServletResponse.SC_NOT_FOUND, "User not found");
		return null;
	}

	private UserData parseUser(Request request) {
		try {
			UserData userData = jsonMapper.readValue(request.body(), UserData.class);
			
			checkRequiredParam(userData.login, "login");
			checkRequiredParam(userData.name, "name");
			checkRequiredParam(userData.password, "password");
			
			return userData;
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}
	
	private void checkRequiredParam(String value, String name) {
		if(Strings.isNullOrEmpty(value)) {
			// TODO refactor when https://github.com/perwendel/spark/pull/270 is accepted
			halt(HttpServletResponse.SC_BAD_REQUEST, "'"+name+"' is required");
		}
	}
	
	@SuppressWarnings("unused") // Jackson needs unused setter
	private static class UserData {
		private String login;
		private String name;
		private String password;
		
		public void setLogin(String login) {
			this.login = login;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}

}
