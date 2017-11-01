package org.devteam.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import dagger.Module;
import dagger.Provides;

@Module
public class WebModule {

	@Provides ObjectMapper provideObjectMapper() {
		return new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

}
