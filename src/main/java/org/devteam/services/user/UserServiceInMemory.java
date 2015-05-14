package org.devteam.services.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.common.collect.ImmutableList;

@Singleton
public class UserServiceInMemory implements UserService {

	private final Map<String, User> users;
	
	@Inject
	public UserServiceInMemory() {
		users = new ConcurrentHashMap<>();
		save("Uta Willis", "uwillis", "secret");
	}

	@Override
	public Optional<User> authenticate(String login, String password) {
		return fetch(login).filter(user -> user.getPassword().equals(password));
	}

	public List<User> list() {
		return ImmutableList.copyOf(users.values());
	}

	@Override
	public Optional<User> fetch(String login) {
		return Optional.ofNullable(users.get(login));
	}

	@Override
	public User save(String name, String login, String password) {
		User user = new User(name, login, password);
		users.put(login, user);

		return user;
	}

	@Override
	public void delete(String login) {
		users.remove(login);
	}

}
