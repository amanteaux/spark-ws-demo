package org.devteam.ws.user;

import spark.Request;
import spark.Response;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by amanteaux on 18/04/15.
 */
@Singleton
public class UserWs {

    @Inject
    public UserWs() {
    }

    public User hello(Request request, Response response) {
        return new User(request.params(":name"), "Hello " + request.params(":name"));
    }

}
