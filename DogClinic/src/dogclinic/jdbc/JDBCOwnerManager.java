package dogclinic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dogclinic.ifaces.OwnerManager;
import dogclinic.pojos.Owner;

public class JDBCOwnerManager implements OwnerManager {

	Connection c;

	public JDBCOwnerManager() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/dogclinic.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			System.out.println("Database connection opened.");
			createTables();
		} catch (Exception e) {
			System.out.println("Database access error");
			e.printStackTrace();
		}

	}

	private void createTables() {
		try {
			Statement s = c.createStatement();
			String table = "CREATE TABLE owners (id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT NOT NULL,"
					+ " phone INTEGER," + " email TEXT NOT NULL)";
			s.executeUpdate(table);
			String table2 = "CREATE TABLE dogs (id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT NOT NULL,"
					+ " dob DATE NOT NULL," + " breed TEXT," + " ownerId INTEGER REFERENCES owners(id))";
			s.executeUpdate(table2);
			String table3 = "CREATE TABLE vets (id INTEGER PRIMARY KEY AUTOINCREMENT," + " name TEXT NOT NULL,"
					+ " phone INTEGER," + " email TEXT NOT NULL," + " speciality TEXT)";
			s.executeUpdate(table3);
			String table4 = "CREATE TABLE dogsVets (" + " dogId INTEGER REFERENCES dogs(id),"
					+ " vetId INTEGER REFERENCES vets(id))";
			s.executeUpdate(table4);
			s.close();
		} catch (SQLException e) {
			// Check if the exception is because the tables already exist
			if (e.getMessage().contains("already exist")) {
				return;
			}
			System.out.println("Database error.");
			e.printStackTrace();
		}
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
			while(rs.next()) {
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

}
