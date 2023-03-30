package dogclinic.ifaces;

import java.util.List;

import dogclinic.pojos.Vet;

public interface VetManager {

	public void insertVet(Vet vet);
	public List<Vet> searchVetByName(String name);
}
