package dogclinic.ifaces;

import java.util.List;

import dogclinic.pojos.*;

public interface OwnerManager {
	
	public void insertOwner(Owner owner);
	public List<Owner> searchOwnerByName(String name);
	public Owner getOwnerByEmail(String email);
	public Owner getOwner(int id);

}
