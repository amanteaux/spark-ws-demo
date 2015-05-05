package org.devteam.web.filters;

import static spark.Spark.halt;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import spark.Route;

@Singleton
public class AuthenticationFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	
	@Inject
    public AuthenticationFilter() {
	}

	public Route authenticate(Route action) {
        return ((request, response) -> {
        	if(isAuthenticated()) {
        		return action.handle(request, response);
        	} else {
        		halt(401, "Unauthorized");
        		return null;
        	}
        });
    }
    
    private boolean isAuthenticated() {
    	logger.debug("Checking authentication here...");
    	return true;
    }
	
}
