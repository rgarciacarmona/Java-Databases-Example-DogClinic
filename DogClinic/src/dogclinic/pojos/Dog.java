package dogclinic.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

// THIS COMMENT IS A TEST FOR GITHUB
// CHANGE CHANGE CHANGE

// POJO
// Plain Old Java Object
// MANDATORY
// - Empty Constructor (without parameters)
// - Getters and setters
// - Hashcode and equals
//			"equals()" != "=="
// - implements Serializable
// OPTIONAL
// - toString
// - Add and Remove

public class Dog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7553144802424898699L;
	
	private Integer id; // Integer can be null
	private String name;
	private Date dob; // Date from java.sql
	private String breed;
	private Owner owner;
	
	public Dog() {
		super();
	}

	

	public Dog(Integer id, String name, Date dob, String breed) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.breed = breed;
	}

	

	@Override
	public String toString() {
		return "Dog [id=" + id + ", name=" + name + ", dob=" + dob + ", breed=" + breed + "]";
	}



	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
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

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) { // toby.equals(thor)
		if (this == obj) // Checks if both objects have the same memory reference (the same piece of paper)
			return true;
		if (obj == null) // If not, checks if the other object is null
			return false;
		if (getClass() != obj.getClass()) // If not, check if both objects are of the same class
			return false;
		Dog other = (Dog) obj; // If they are, cast the other object to this class
		return Objects.equals(id, other.id); // Compare the appropriate attributes
	}
	
	
	
}
