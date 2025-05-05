package dogclinic.ifaces;

import java.io.File;

import dogclinic.pojos.Owner;

public interface XMLManager {

	public void owner2Xml(Owner o);
	public Owner xml2Owner(File xml);
}
