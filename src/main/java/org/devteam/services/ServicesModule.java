package org.devteam.services;

import org.devteam.services.user.UserService;
import org.devteam.services.user.UserServiceMock;

import dagger.Module;
import dagger.Provides;

@Module
public class ServicesModule {

	@Provides UserService provideUserService(UserServiceMock userService) {
		return userService;
	}
	
}
