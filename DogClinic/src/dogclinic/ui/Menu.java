package dogclinic.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import dogclinic.ifaces.OwnerManager;
import dogclinic.jdbc.JDBCOwnerManager;
import dogclinic.pojos.Owner;

public class Menu {
	
	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	private static OwnerManager ownerMan;

	public static void main(String[] args) {
		try {
			ownerMan = new JDBCOwnerManager();
			
			System.out.println("Welcome to the DogClinic, dear pet lover!");
			System.out.println("Choose an option, please:");
			System.out.println("1. Register a new owner");
			System.out.println("2. Select an existing owner");
			System.out.println("3. Register a new vet");
			System.out.println("0. Exit");

			int choice = Integer.parseInt(r.readLine());
			
			switch (choice) {
			case 1: {
				registerOwner();
				break;
			}
			case 2: {
				// TODO Select owner
				break;
			}
			case 3: {
				// TODO register vet
				break;
			}
			case 0: {
				return;
			}
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void registerOwner() throws IOException {
		System.out.println("Please, input the owner's data:");
		System.out.println("Name:");
		String name = r.readLine();
		System.out.println("Phone:");
		Integer phone = Integer.parseInt(r.readLine());
		System.out.println("Email:");
		String email = r.readLine();
		Owner o = new Owner(name, phone, email);
		ownerMan.insertOwner(o);
	}
}
