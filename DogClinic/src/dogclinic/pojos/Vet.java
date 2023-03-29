package dogclinic.pojos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3034589358817537492L;

	private Integer id;
	private String name;
	private Integer phone;
	private String email;
	private String speciality;
	private List<Dog> dogs;

	public Vet() {
		super();
		dogs = new ArrayList<Dog>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;

	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPhone() {
		return phone;
	}

	public void setPhone(Integer phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public List<Dog> getDogs() {
		return dogs;
	}

	public void setDogs(List<Dog> dogs) {
		this.dogs = dogs;
	}
	
	public void addDog(Dog dog) {
		if (!dogs.contains(dog))
			dogs.add(dog);
	}
	
	public void removeDog(Dog dog) {
		if (dogs.contains(dog))
			dogs.remove(dog);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vet other = (Vet) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Vet [id=" + id + ", name=" + name + ", phone=" + phone + ", email=" + email + ", speciality="
				+ speciality + ", dogs=" + dogs + "]";
	}

}
