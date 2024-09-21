import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BackendDeveloperTests {
	private String filepath = "/Users/user/Documents/cs400/p1_backend/src/cars.csv";
	/**
	 * This method tests the accessor methods in Car class
	 */
	@Test
	public void checkCarAccessor() {
		//Create a car object
		BackendIndividual car = new BackendIndividual("Toyota", "Camry", 2023, 10000.99, "Status", 399.50, "black", "A199");
		//Make sure car accessor returns the right value
		Assertions.assertEquals(car.getBrand(), "Toyota");
		Assertions.assertEquals(car.getModel(), "Camry");
		Assertions.assertEquals(car.getYear(), 2023);
		Assertions.assertEquals(car.getPrice(), 10000.99);
		Assertions.assertEquals(car.getTitleStatus(), "Status");
		Assertions.assertEquals(car.getMileage(), 399.50);
		Assertions.assertEquals(car.getColor(), "black");
		Assertions.assertEquals(car.getTin(), "A199");
	}
	
	/**
	 * This method tests the mutator methods in Car class
	 */
	@Test
	public void checkCarMutator() {
		//Create a car object
		BackendIndividual car = new BackendIndividual("Toyota", "Camry", 2023, 10000.99, "Status", 399.50, "black", "A199");
		//Update it with new value
		car.updateBrand("Tesla");
		car.updateModel("Model 3");
		car.updateYear(2000);
		car.updatePrice(5000.10);
		car.updateTitleStatus("New Status");
		car.updateMileage(49.99);
		car.updateColor("White");
		car.updateTin("B10");
		//Make sure the accessor returns the new value
		Assertions.assertEquals(car.getBrand(), "Tesla");
		Assertions.assertEquals(car.getModel(), "Model 3");
		Assertions.assertEquals(car.getYear(), 2000);
		Assertions.assertEquals(car.getPrice(), 5000.10);
		Assertions.assertEquals(car.getTitleStatus(), "New Status");
		Assertions.assertEquals(car.getMileage(), 49.99);
		Assertions.assertEquals(car.getColor(), "White");
		Assertions.assertEquals(car.getTin(), "B10");
	}
	
	/**
	 * This method tests on the method readFile(filename)
	 */
	@Test
	public void checkReadFile() {
		//This should be changed depending what is the name of the class implementing the interface
		Backend carAccessor = new Backend();
		//Read the file
		carAccessor.readFile(this.filepath);
		IterableMultiKeySortedCollectionInterface<BackendInterface.Data> cars =  carAccessor.getCars();
		//Loop through all the cars that it returns
		for(BackendInterface.Data car:cars) {
			//make sure all the object returned are class Car
			Assertions.assertTrue(car instanceof BackendIndividual);
		}
	}
	
	/**
	 * This test the method getCarsAtAboveMileage()
	 */
	@Test
	public void checkGetCarsAtAboveMileage() {
		//This should be changed depending what is the name of the class implementing the interface
		Backend carAccessor = new Backend();
		//Read the file
		carAccessor.readFile(this.filepath);
		List<BackendInterface.Data> cars =  carAccessor.getCarsAtAboveMileage(900000);
		
		for(BackendInterface.Data car:cars) {
			//Make sure all the cars returned has atleast mileage 300.00
			Assertions.assertTrue(car.getMileage() >= 900000);
		}
	}
	
	/**
	 * This test the method getCarsWithMinumumMileage()
	 */
	@Test
	public void checkGetCarsWithMinumumMileage() {
		//This should be changed depending what is the name of the class implementing the interface
		Backend backend = new Backend();
		//Read the file
		backend.readFile(this.filepath);
		try{
			List<BackendInterface.Data> cars =  backend.getCarsWithMinumumMileage();
			List<String> tins =  new ArrayList<String>();
			for(BackendInterface.Data car:cars) {
				//Make sure all the cars returned has atleast mileage 300.00
				Assertions.assertTrue(car.getMileage() == 0.00);
				tins.add(car.getTin());
			}
			Assertions.assertTrue(tins.contains("3gnek12t74g240524"));
			Assertions.assertTrue(tins.contains("1fdee14n7rha47894"));
			Assertions.assertTrue(tins.contains("1fadp3k21hl268441"));
		}catch(Exception e){
		    Assertions.assertEquals("false", e);
		}
	}
	/* 
	*Tests below are for integration test
	*/

	/**
	 * This test is to check if the app starts properly and loaded file as expected
	 */
	@Test
	public void checkStartAppAndLoadFile() {
		Backend backend = new Backend();
		String userCommandSimulation = "1\ncars.csv\n4\n";
		TextUITester uiTester = new TextUITester(userCommandSimulation);
		Scanner scanner = new Scanner(System.in);
		FrontendPlaceholder frontend = new FrontendPlaceholder(backend, scanner);
		frontend.startApp();
		String programOutput = uiTester.checkOutput();
		//This part will be modified as we merge but this below should be checking what is expected for the output
		Assertions.assertTrue(programOutput.contains("App started"));
		Assertions.assertTrue(programOutput.contains("File loaded"));
		Assertions.assertTrue(programOutput.contains("App exited"));
	}
	/**
	 * This method is to check if the app filtering is working as expected
	 */
	@Test
	public void testFiltering() {
		Backend backend = new Backend();
		TextUITester uiTester = new TextUITester("1\nsrc/cars.csv\n2\n3\n200000\n4\n");
		Scanner scanner = new Scanner(System.in);
		FrontendPlaceholder frontend = new FrontendPlaceholder(backend, scanner);
		frontend.startApp();
		String programOutput = uiTester.checkOutput();
		//This part will be modified as we merge but this below should be checking what is expected for the output
		Assertions.assertTrue(programOutput.contains("App started"));
		Assertions.assertTrue(programOutput.contains("File loaded"));
		Assertions.assertTrue(programOutput.contains("1fdee14n7rha47894"));
		Assertions.assertTrue(programOutput.contains("jtezu11f88k007763")&&programOutput.contains("1fdfe4fs7eda23699")&&!programOutput.contains("2fmdk3gc4bbb02217"));
		Assertions.assertTrue(programOutput.contains("App exited."));
	}

	/**
	 * This method is to check if the app filtering is working as expected
	 */
	public static void main(String[] args) {
		Backend backend = new Backend();
		Scanner scanner = new Scanner(System.in);
		Frontend frontend = new Frontend(backend, scanner);
		frontend.startApp();
	}
}
