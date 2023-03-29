package dogclinic.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
		// TODO Do this thing first, if not, the DB doesn't work. I'd have loved to do this today, but Ivana is coming.
	}

	@Override
	public void insertOwner(Owner owner) {
		try {
			Statement s = c.createStatement();
			String sql = "INSERT INTO owners (name, phone, email) VALUES ('" + owner.getName() + "', " + owner.getPhone() + ", '" + owner.getEmail() + "')";
			s.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("Database exception.");
			e.printStackTrace();
		}
		

	}

	@Override
	public List<Owner> searchOwnerByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
