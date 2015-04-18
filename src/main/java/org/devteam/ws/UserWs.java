package org.devteam.ws;

import spark.Request;
import spark.Response;

/**
 * Created by amanteaux on 18/04/15.
 */
public class UserWs {

    public User hello(Request request, Response response) {
        return new User(request.params(":name"), "Hello " + request.params(":name"));
    }

}
