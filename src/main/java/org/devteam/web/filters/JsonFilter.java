package org.devteam.web.filters;

import static spark.Spark.halt;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Route;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Singleton
public class JsonFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(JsonFilter.class);
	
	private final ObjectMapper mapper;
	
	@Inject
    public JsonFilter() {
		this.mapper = new ObjectMapper();
	}

	public Route jsonResponse(Route action) {
        return ((request, response) -> {
            try {
            	Object result = action.handle(request, response);
                response.type("application/json; charset=utf-8");
                return mapper.writeValueAsString(result);
            } catch (JsonProcessingException e) {
                logger.error("", e);
                halt(500, "Shit happened :/");
                return null;
            }
        });
    }

}
