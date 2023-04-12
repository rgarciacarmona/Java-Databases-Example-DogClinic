package dogclinic.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dogclinic.ifaces.OwnerManager;
import dogclinic.pojos.Dog;
import dogclinic.pojos.Owner;

public class JDBCOwnerManager implements OwnerManager {

	private Connection c;

	public JDBCOwnerManager(Connection c) {
		this.c = c;

	}

	@Override
	public void insertOwner(Owner owner) {
		try {
			Statement s = c.createStatement();
			String sql = "INSERT INTO owners (name, phone, email) VALUES ('" + owner.getName() + "', "
					+ owner.getPhone() + ", '" + owner.getEmail() + "')";
			s.executeUpdate(sql);
			s.close();
		} catch (SQLException e) {
			System.out.println("Database exception.");
			e.printStackTrace();
		}

	}

	@Override
	public List<Owner> searchOwnerByName(String name) {
		List<Owner> list = new ArrayList<Owner>();
		try {
			String sql = "SELECT * FROM owners WHERE name LIKE ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setString(1, "%" + name + "%");
			ResultSet rs = p.executeQuery();
			while (rs.next()) {
				// Create a new Owner
				Integer id = rs.getInt("id");
				String n = rs.getString("name");
				String email = rs.getString("email");
				Integer phone = rs.getInt("phone");
				Owner o = new Owner(id, n, phone, email);
				// IMPORTANT: I don't have the dogs
				// Add the Owner to the list
				list.add(o);
			}
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Owner getOwner(int id) {
		try {
			String sql = "SELECT * FROM owners WHERE id = ?";
			PreparedStatement p = c.prepareStatement(sql);
			p.setInt(1, id);
			ResultSet rs = p.executeQuery();
			rs.next();
			String name = rs.getString("name");
			Integer phone = rs.getInt("phone");
			String email = rs.getString("email");
			Owner o = new Owner(id, name, phone, email);
			rs.close();
			p.close();
			return o;
		} catch (SQLException e) {
			System.out.println("Database error.");
			e.printStackTrace();
		}
		return null;
	}

}
