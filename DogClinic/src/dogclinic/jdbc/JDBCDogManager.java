package dogclinic.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dogclinic.ifaces.DogManager;
import dogclinic.pojos.*;

public class JDBCDogManager implements DogManager {

	private Connection c;

	public JDBCDogManager(Connection c) {
		this.c = c;

	}

	@Override
	public void insertDog(Dog dog) {
		// IMPORTANT: Dog must have an owner
		try {
			String sql = "INSERT INTO dogs (name, dob, breed, ownerId) VALUES (?, ?, ?, ?)";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, dog.getName());
			p.setDate(2, dog.getDob());
			p.setString(3, dog.getBreed());
			p.setInt(4, dog.getOwner().getId());
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("Database exception.");
			e.printStackTrace();
		}
	}

	@Override
	public List<Dog> searchDogByOwner(int id) {
		List<Dog> list = new ArrayList<Dog>();
		try {
			String sql = "SELECT * FROM dogs WHERE ownerId = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				// Create a new Dog
				Integer dogId = rs.getInt("id");
				String name = rs.getString("name");
				Date dob = rs.getDate("dob");
				String breed = rs.getString("breed");
				Dog d = new Dog(dogId, name, dob, breed);
				// IMPORTANT: I don't have the owner nor the vets
				// Add the Dog to the list
				list.add(d);
			}
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void updateDog(Dog dog) {
		try {
			String sql = "UPDATE dogs SET" + " name = ?, " + " breed = ?, " + " dob = ? " + " WHERE id = ?";
			PreparedStatement p;
			p = c.prepareStatement(sql);
			p.setString(1, dog.getName());
			p.setString(2, dog.getBreed());
			p.setDate(3, dog.getDob());
			p.setInt(4, dog.getId());
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
	}

	@Override
	public void removeDog(int id) {
		try {
			String sql = "DELETE FROM dogs WHERE id = ?";
			PreparedStatement p;
			p = c.prepareStatement(sql);
			p.setInt(1, id);
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
	}

	@Override
	public void assignDogToVet(int dogId, int vetId) {
		// TODO Write this method
	}

	@Override
	public Dog getDog(int id) {
		// TODO Write this method
		return null;
	}

}
