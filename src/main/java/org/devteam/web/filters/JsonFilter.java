package org.devteam.web.filters;

import static spark.Spark.halt;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;

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

	/**
	 * Convert the response to a JSON string and change the response content-type to application/json
	 */
	public Route jsonResponse(Route action) {
		return ((request, response) -> {
			try {
				// the result has to be evaluated before doing anything, this way, if an error occured, the response won't changed
				Object result = action.handle(request, response);
				response.type("application/json; charset=utf-8");
				return mapper.writeValueAsString(result);
			} catch (JsonProcessingException e) {
				logger.error("", e);
				// TODO refactor when https://github.com/perwendel/spark/pull/270 is accepted
				halt(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Shit happened :/");
				return null;
			}
		});
	}

}
