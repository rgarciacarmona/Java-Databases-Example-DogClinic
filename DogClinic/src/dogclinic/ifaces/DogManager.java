package dogclinic.ifaces;

import java.util.List;

import dogclinic.pojos.Dog;

public interface DogManager {

	public void insertDog(Dog dog);
	public List<Dog> searchDogByOwner(int id);
	public void updateDog(Dog dog);
	public void removeDog(int id);
	public void assignDogToVet(int dogId, int vetId);
}
