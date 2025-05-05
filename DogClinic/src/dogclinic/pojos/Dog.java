package dogclinic.pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import dogclinic.xml.SQLDateAdapter;

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

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "breed", "dob" })
public class Dog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7553144802424898699L;
	
	@XmlTransient
	private Integer id; // Integer can be null
	@XmlAttribute
	private String name;
	@XmlElement
	@XmlJavaTypeAdapter(SQLDateAdapter.class)
	private Date dob; // Date from java.sql
	@XmlElement
	private String breed;
	@XmlTransient
	private Owner owner;
	@XmlTransient
	private List<Vet> vets;
	
	public Dog() {
		super();
		vets = new ArrayList<Vet>();
	}

	public Dog(Integer id, String name, Date dob, String breed) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.breed = breed;
		vets = new ArrayList<Vet>();

	}

	public Dog(String name, Date dob, String breed, Owner owner) {
		super();
		this.name = name;
		this.dob = dob;
		this.breed = breed;
		this.owner = owner;
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
	
	

	public List<Vet> getVets() {
		return vets;
	}



	public void setVets(List<Vet> vets) {
		this.vets = vets;
	}



	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
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
