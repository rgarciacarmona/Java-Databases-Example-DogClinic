package dogclinic.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dogclinic.ifaces.*;
import dogclinic.jdbc.*;
import dogclinic.jpa.JPAUserManager;
import dogclinic.pojos.*;
import dogclinic.xml.XMLManagerImpl;

public class Menu {

	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static OwnerManager ownerMan;
	private static DogManager dogMan;
	private static VetManager vetMan;
	private static UserManager userMan;
	private static XMLManager xmlMan = new XMLManagerImpl();

	public static void main(String[] args) {
		ConnectionManager conMan = new ConnectionManager();
		ownerMan = new JDBCOwnerManager(conMan.getConnection());
		dogMan = new JDBCDogManager(conMan.getConnection());
		vetMan = new JDBCVetManager(conMan.getConnection());
		userMan = new JPAUserManager();
		while (true) {
			try {
				System.out.println("Welcome to the DogClinic, dear pet lover!");
				System.out.println("Choose an option, please:");
				System.out.println("1. Register as an owner");
				System.out.println("2. Register as a vet");
				System.out.println("3. Login");
				System.out.println("0. Exit");

				int choice = Integer.parseInt(r.readLine());

				switch (choice) {
				case 1: {
					registerOwner();
					break;
				}
				case 2: {
					registerVet();
					break;
				}
				case 3: {
					login();
					break;
				}
				case 0: {
					conMan.closeConnection();
					userMan.close();
					return;
				}
				}

			} catch (NumberFormatException e) {
				System.out.println("You didn't type a number!");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("I/O Exception.");
				e.printStackTrace();
			}
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
		System.out.println("Username:");
		String username = r.readLine();
		System.out.println("Password:");
		String password = r.readLine();
		Owner o = new Owner(name, phone, email);
		ownerMan.insertOwner(o);
		User u = new User(username, password, email);
		userMan.register(u);
		Role r = userMan.getRole("owner");
		userMan.assignRole(u, r);
	}

	public static void registerVet() throws IOException {
		System.out.println("Please, input the vet's data:");
		System.out.println("Name:");
		String name = r.readLine();
		System.out.println("Phone:");
		Integer phone = Integer.parseInt(r.readLine());
		System.out.println("Email:");
		String email = r.readLine();
		System.out.println("Speciality:");
		String speciality = r.readLine();
		Vet v = new Vet(name, phone, email, speciality);
		vetMan.insertVet(v);
	}
	

	public static void login() throws IOException {
		while (true) {
			// Ask for the username and password
			System.out.println("Username:");
			String username = r.readLine();
			System.out.println("Password:");
			String password = r.readLine();
			// If they match, go to the owner screen
			User user = userMan.login(username, password);
			if (user != null) {
				if (user.getRole().getName().equals("owner")) {
					ownerMenu(user.getEmail());
				}
				else if (user.getRole().getName().equals("vet")) {
					vetMenu(user.getEmail());
				}
			}
			// It not, ask again
			else {
				System.out.println("Wrong username/password combination.");
			}
		}
	}

	public static void ownerMenu(String email) {
		Owner owner = ownerMan.getOwnerByEmail(email);
		while (true) {
			try {

				System.out.println("Welcome to the DogClinic, dear owner!");
				System.out.println("Choose an option, please:");
				System.out.println("1. Register a new dog");
				System.out.println("2. Check my dogs");
				System.out.println("3. Export my dogs to XML");
				System.out.println("0. Back to main menu");

				int choice = Integer.parseInt(r.readLine());

				switch (choice) {
				case 1: {
					registerDog(owner.getId());
					break;
				}
				case 2: {
					checkDogs(owner.getId());
					break;
				}
				case 3: {
					dogs2Xml(owner.getId());
					break;
				}
				case 0: {
					return;
				}
				}

			} catch (NumberFormatException e) {
				System.out.println("You didn't type a number!");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("I/O Exception.");
				e.printStackTrace();
			}
		}

	}

	public static void registerDog(int id) throws IOException {
		// Register a new Dog and link it with its Owner
		System.out.println("Please, input the dog's data:");
		System.out.println("Name:");
		String name = r.readLine();
		System.out.println("Date of birth (yyyy-MM-dd):");
		String dob = r.readLine();
		LocalDate dobLocalDate = LocalDate.parse(dob, formatter); // java.time.LocalDate
		Date dobDate = Date.valueOf(dobLocalDate); // java.sql.Date
		System.out.println("Breed:");
		String breed = r.readLine();
		// Get the owner from the database
		Owner o = ownerMan.getOwner(id);
		Dog d = new Dog(name, dobDate, breed, o);
		dogMan.insertDog(d);
		System.out.println("The dog has been saved to the database.");
	}

	public static void checkDogs(int id) throws IOException {
		System.out.println("Your dogs are:");
		List<Dog> listDog = dogMan.searchDogByOwner(id);
		System.out.println(listDog);
		System.out.println("Please choose a dog, type its Id:");
		Integer dogId = Integer.parseInt(r.readLine());
		// Go to the dog's menu
		dogMenu(dogId);
	}
	
	public static void dogs2Xml(int id) throws IOException {
		List<Dog> listDog = dogMan.searchDogByOwner(id);
		Owner own = ownerMan.getOwner(id);
		own.setDogs(listDog);
		xmlMan.owner2Xml(own);
		System.out.println("Your dogs are in ./xmls/Dogs.xml");
	}

	public static void dogMenu(int id) {
		while (true) {
			try {

				System.out.println("What do you want to do to the dog?:");
				System.out.println("1. Assign to a vet");
				System.out.println("2. Change the data");
				System.out.println("3. Remove the dog :(");
				System.out.println("0. Back to owner menu");

				int choice = Integer.parseInt(r.readLine());

				switch (choice) {
				case 1: {
					assignVet(id);
					break;
				}
				case 2: {
					updateDog(id);
					break;
				}
				case 3: {
					removeDog(id);
					break;
				}
				case 0: {
					return;
				}
				}

			} catch (NumberFormatException e) {
				System.out.println("You didn't type a number, idiot!");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("I/O Exception.");
				e.printStackTrace();
			}
		}
	}

	public static void assignVet(int dogId) throws IOException {
		System.out.println("Let's search a vet by its name:");
		String name = r.readLine();
		List<Vet> listVet = vetMan.searchVetByName(name);
		System.out.println(listVet);
		System.out.println("Please choose a vet, type its Id:");
		Integer vetId = Integer.parseInt(r.readLine());
		dogMan.assignDogToVet(dogId, vetId);
		System.out.println("The dog has been assigned to the vet.");
	}

	public static void updateDog(int id) throws IOException {
		Dog d = dogMan.getDog(id);
		System.out.println("Type the new data, or press enter to keep actual data");
		System.out.println("Name (" + d.getName() + "):");
		String name = r.readLine();
		if (!name.equals("")) {
			d.setName(name);
		}
		System.out.println("Date of birth (" + d.getDob().toLocalDate() + "):");
		String dob = r.readLine();
		if (!dob.equals("")) {
			LocalDate dobLocalDate = LocalDate.parse(dob, formatter);
			Date dobDate = Date.valueOf(dobLocalDate);
			d.setDob(dobDate);
		}
		System.out.println("Breed (" + d.getBreed() + "):");
		String breed = r.readLine();
		if (!breed.equals("")) {
			d.setBreed(breed);
		}
		dogMan.updateDog(d);
		System.out.println("The changes to the dog have been saved.");
	}

	public static void removeDog(int id) throws IOException {
		dogMan.removeDog(id);
		System.out.println("The dog has been removed. :(");
	}

	public static void vetMenu(String email) throws IOException {
		// TODO Complete vet's menu
	}


}
