package org.devteam.services.user;

import java.util.List;
import java.util.Optional;

public interface UserService {

	Optional<User> authenticate(String login, String password);

	List<User> list();

	Optional<User> fetch(String login);

	User save(String name, String login, String password);

	void delete(String login);
	
}
