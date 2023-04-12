package dogclinic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dogclinic.ifaces.VetManager;
import dogclinic.pojos.Owner;
import dogclinic.pojos.Vet;

public class JDBCVetManager implements VetManager {

	private Connection c;
	
	public JDBCVetManager(Connection c) {
		this.c = c;

	}
	
	@Override
	public void insertVet(Vet vet) {
		try {
			String sql = "INSERT INTO vets (name, phone, email, speciality) VALUES (?, ?, ?, ?)";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, vet.getName());
			p.setInt(2, vet.getPhone());
			p.setString(3, vet.getEmail());
			p.setString(4, vet.getSpeciality());
			p.executeUpdate();
			p.close();
		} catch (SQLException e) {
			System.out.println("Database exception.");
			e.printStackTrace();
		}

	}

	@Override
	public List<Vet> searchVetByName(String name) {
		List<Vet> list = new ArrayList<Vet>();
		try {
			String sql = "SELECT * FROM vets WHERE name LIKE ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, "%" + name + "%");
			ResultSet rs = p.executeQuery();
			while(rs.next()) {
				// Create a new Vet
				Integer id = rs.getInt("id");
				String n = rs.getString("name");
				Integer phone = rs.getInt("phone");
				String email = rs.getString("email");
				String speciality = rs.getString("speciality");
				Vet v = new Vet(id, n, phone, email, speciality);
				// IMPORTANT: I don't have the dogs
				// Add the Vet to the list
				list.add(v);
			}
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
		return list;
	}

}
