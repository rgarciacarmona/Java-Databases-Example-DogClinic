package dogclinic.pojos;

public class Test {

	public static void main(String[] args) {
//		Dog toby = new Dog();
//		Dog thor = new Dog();
//		toby.setId(1);
//		toby.setName("Toby");
//		thor.setId(1);
//		thor.setName("Thor");
//		System.out.println(toby == thor); // false
//		System.out.println(toby.equals(thor)); // true
		
		Dog bellota = new Dog(1, "Bellota", null, "Bichón Maltés");
		Owner belen = new Owner(1, "Belén", 666666666, "belen@email.com");
		bellota.setOwner(belen);
		belen.addDog(bellota);
		System.out.println("Welcome to the Dog Clinic.");
		System.out.println(belen);
	}

}
