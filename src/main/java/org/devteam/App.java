package org.devteam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.devteam.ws.UserWs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.function.BiFunction;

import static spark.Spark.halt;
import static spark.Spark.get;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private final ObjectMapper mapper;

    public App() {
        this.mapper = new ObjectMapper();
    }

    public static void main( String[] args ) {
        new App().router();
    }

    private void router() {
        UserWs userWs = new UserWs();

        get("/hello/:name", json(authenticate(userWs::hello)));
    }

    private <T> Route json(BiFunction<Request, Response, T> action) {
        return ((request, response) -> {
            try {
                response.type("application/json; charset=utf-8");
                return mapper.writeValueAsString(action.apply(request, response));
            } catch (JsonProcessingException e) {
                logger.error("", e);
                halt(500, "Shit happened :/");
                return "";
            }
        });
    }

    private <T> BiFunction<Request, Response, T> authenticate(BiFunction<Request, Response, T> action) {
        return ((request, response) -> {
            logger.info("Checking authentication here...");
            return action.apply(request, response);
        });
    }

}
