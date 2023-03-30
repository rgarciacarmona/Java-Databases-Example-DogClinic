package dogclinic.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import dogclinic.ifaces.DogManager;
import dogclinic.pojos.Dog;

public class JDBCDogManager implements DogManager {

	Connection c;

	@Override
	public void insertDog(Dog dog) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Dog> searchDogByOwner(int id) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub

	}

}
