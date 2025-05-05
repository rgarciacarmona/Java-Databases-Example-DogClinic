package dogclinic.ifaces;

import java.util.List;

import dogclinic.pojos.*;

public interface UserManager {

	public void register(User user);
	public void createRole(Role role);
	public Role getRole(String name);
	public List<Role> getRoles();
	public void assignRole(User user, Role role);
	// If user doesn't exist return null
	public User login(String name, String password);
	public void close();
}
