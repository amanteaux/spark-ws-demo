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
		save("uwillis", "Uta Willis", "secret");
		save("swaller", "Scarlett Waller", "abcd");
		save("nklein", "Nissim Klein", "azerty");
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
	public User create(String login, String name, String password) {
		return save(login, name, password);
	}

	@Override
	public User update(String login, String name, String password) {
		return save(login, name, password);
	}

	@Override
	public void delete(String login) {
		users.remove(login);
	}

	private User save(String login, String name, String password) {
		User user = new User(name, login, password);
		users.put(login, user);

		return user;
	}

}
