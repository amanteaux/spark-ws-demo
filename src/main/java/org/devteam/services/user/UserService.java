package org.devteam.services.user;

import java.util.List;
import java.util.Optional;

public interface UserService {

	Optional<User> authenticate(String login, String password);

	List<User> list();

	Optional<User> fetch(String login);

	User create(String login, String name, String password);

	User update(String originalLogin, String login, String name, String password);

	void delete(String login);

}
