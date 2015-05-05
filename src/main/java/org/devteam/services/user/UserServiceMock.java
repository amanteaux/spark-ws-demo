package org.devteam.services.user;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.common.collect.ImmutableList;

@Singleton
public class UserServiceMock implements UserService {
	
	@Inject
	public UserServiceMock() {
	}

	public List<User> list() {
		return ImmutableList.of(
			new User("Uta", "Willis"),
			new User("Jermaine", "Mason")
		);
	}

}
