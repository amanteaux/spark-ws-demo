package org.devteam.services;

import javax.inject.Singleton;

import org.devteam.services.user.UserService;
import org.devteam.services.user.UserServiceInMemory;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {

	@Provides
	@Singleton
	static UserService provideUserService(UserServiceInMemory userService) {
		return userService;
	}

}
