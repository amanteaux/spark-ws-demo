package org.devteam.services;

import org.devteam.services.user.UserService;
import org.devteam.services.user.UserServiceInMemory;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {

	@Provides UserService provideUserService(UserServiceInMemory userService) {
		return userService;
	}
	
}
