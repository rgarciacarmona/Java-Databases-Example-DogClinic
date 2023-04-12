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
import dogclinic.pojos.*;

public class Menu {

	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static OwnerManager ownerMan;
	private static DogManager dogMan;
	private static VetManager vetMan;

	public static void main(String[] args) {
		// TODO Comment in class, while(true), close connection
		ConnectionManager conMan = new ConnectionManager();
		ownerMan = new JDBCOwnerManager(conMan.getConnection());
		dogMan = new JDBCDogManager(conMan.getConnection());
		vetMan = new JDBCVetManager(conMan.getConnection());
		while (true) {
			try {
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
					selectOwner();
					break;
				}
				case 3: {
					registerVet();
					break;
				}
				case 0: {
					conMan.closeConnection();
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

	public static void selectOwner() throws IOException {
		System.out.println("Let's search by name:");
		String name = r.readLine();
		List<Owner> listOwn = ownerMan.searchOwnerByName(name);
		System.out.println(listOwn);
		System.out.println("Please choose an owner, type its Id:");
		Integer id = Integer.parseInt(r.readLine());
		// Go to the owner's menu
		ownerMenu(id);
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

	public static void ownerMenu(int id) {
		while (true) {
			try {

				System.out.println("Welcome to the DogClinic, dear owner!");
				System.out.println("Choose an option, please:");
				System.out.println("1. Register a new dog");
				System.out.println("2. Check my dogs");
				System.out.println("0. Back to main menu");

				int choice = Integer.parseInt(r.readLine());

				switch (choice) {
				case 1: {
					registerDog(id);
					break;
				}
				case 2: {
					checkDogs(id);
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

	public static void registerDog(int id) throws IOException {
		// TODO Comment in class, search by ID and LocalDate
		System.out.println("Please, input the dog's data:");
		System.out.println("Name:");
		String name = r.readLine();
		System.out.println("Date of birth (yyyy-MM-dd):");
		String dob = r.readLine();
		LocalDate dobLocalDate = LocalDate.parse(dob, formatter);
		Date dobDate = Date.valueOf(dobLocalDate);
		System.out.println("Breed:");
		String breed = r.readLine();
		// Get the owner from the database
		Owner o = ownerMan.getOwner(id);
		Dog d = new Dog(name, dobDate, breed, o);
		d.setOwner(o);
		dogMan.insertDog(d);
	}

	public static void checkDogs(int id) throws IOException {
		System.out.println("Your dogs are:");
		List<Dog> listDog = dogMan.searchDogByOwner(id);
		System.out.println(listDog);
		System.out.println("Please choose a dog, type its Id:");
		Integer dogId = Integer.parseInt(r.readLine());
		// Go to the owner's menu
		dogMenu(dogId);
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
		// TODO Comment in class, many to many relationship
		System.out.println("Let's search a vet by its name:");
		String name = r.readLine();
		List<Vet> listVet = vetMan.searchVetByName(name);
		System.out.println(listVet);
		System.out.println("Please choose a vet, type its Id:");
		Integer vetId = Integer.parseInt(r.readLine());
		// Go to the owner's menu
		dogMan.assignDogToVet(dogId, vetId);
	}

	public static void updateDog(int id) throws IOException {
		// TODO Comment in class, how to do an update
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
	}

	public static void removeDog(int id) throws IOException {
		dogMan.removeDog(id);
	}

}
