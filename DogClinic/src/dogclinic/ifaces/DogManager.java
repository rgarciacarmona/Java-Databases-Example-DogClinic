package dogclinic.ifaces;

import java.util.List;

import dogclinic.pojos.Dog;
import dogclinic.pojos.Owner;

public interface DogManager {

	public void insertDog(Dog dog);
	public List<Dog> searchDogByOwner(int id);
	public void updateDog(Dog dog);
	public void removeDog(int id);
	public void assignDogToVet(int dogId, int vetId);
	// TODO Comment in class, new method
	public Dog getDog(int id);

}
