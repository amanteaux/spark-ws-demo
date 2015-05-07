package org.devteam.web.ws.user;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.devteam.services.user.User;
import org.devteam.services.user.UserService;

import spark.Request;
import spark.Response;

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

}
