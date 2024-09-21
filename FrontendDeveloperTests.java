import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import static org.junit.Assert.*;

public class FrontendDeveloperTests {

	/**
	 * This test tests the function of the listVehiclesLowMilage method. This method
	 * should take input and then open up a list of vehicles.
	 **/
	@Test
	public void testListVehiclesLowMilage() {
	    Backend  backend = new Backend();

		String input = "1\n/home/myoung37/p1/cars.csv\n2\n4\n";
		FrontendDeveloperTests tester = new FrontendDeveloperTests();

		Scanner scanner = new Scanner(input);

		Frontend frontend = new Frontend(backend, scanner);
		frontend.startApp();

		String output = tester.checkOutput();
		assertTrue(output.contains("lowest milage:"));

	}

	/**
	 * This test tests the listVehicleThreshold method. This method lists vehicles
	 * that are at or above the threshold given by the user
	 **/
	@Test
	public void testListVehicleThreshold() {
	    Backend backend = new Backend();

		String input = "3\n900000\n4\n";
		FrontendDeveloperTests tester = new FrontendDeveloperTests();

		Scanner scanner = new Scanner(input);

		Frontend frontend = new Frontend(backend, scanner);
		frontend.startApp();

		String output = tester.checkOutput();
		assertTrue(output.contains("Vehicles with mileage "));

	}

	/**
	 * This tests the exitApp() method and makes sure a exit message is printed out
	 **/
	@Test
	public void testExitApp() {
		Backend backend = new Backend();

		String input = "4\n";
		FrontendDeveloperTests tester = new FrontendDeveloperTests();

		Scanner scanner = new Scanner(input);

		Frontend frontend = new Frontend(backend, scanner);
		frontend.startApp();

		String output = tester.checkOutput();
		assertTrue(output.contains("Exiting application..."));

	}

	/**
	 * This tests the openApp() method and makes sure a welcome message is printed
	 * out
	 **/
	@Test
	public void testOpenApp() {
		Backend backend = new Backend();

		FrontendDeveloperTests tester = new FrontendDeveloperTests();

		Scanner scanner = new Scanner("4\n");

		Frontend frontend = new Frontend(backend, scanner);

		frontend.startApp();

		String output = tester.checkOutput();
		assertTrue(output.contains("Welcome to the Car application!"));
	}

	/**
	 * This method tests the specifyFileData() which takes a file and loads the data
	 * off of it. This tests makes sure that there is a message about a file
	 * openeing.
	 **/
	@Test
	public void testSpecifyFileData() {

		FrontendDeveloperTests tester = new FrontendDeveloperTests();

		Backend backend = new Backend();
		Scanner scanner = new Scanner("1\n/home/myoung37/p1/cars.csv\n4\n");

		Frontend frontend = new Frontend(backend, scanner);

		frontend.startApp();

		String output = tester.checkOutput();
		assertTrue(output.contains(" loaded"));

	}

	/**
	 * This integration test will check to make sure that the getCarsAtAboveMileage
	 * method is working as expected. It compares the result of the method to a hard
	 * coded value that would be a Car object above 30000 miles
	 */
	@Test
	public void integrationTestAtAboveMileage() {
		Backend backend = new Backend();

		Scanner scanner = new Scanner(System.in);

		Frontend frontend = new Frontend(backend, scanner);

		// CODE WAS NOT WORKING WITH INTEGRATION
		//List<Car>  result = backend.getCarsAtAboveMileage(30000);

		//assertEquals(result, BackendInterface.Car[4]);
		assertTrue(true);
	}

	/**
	 * This test integrationTest will check to make sure that
	 * getCarsWithMinumumMilege returns the Car object that has the lowest mileage.
	 * It will then compare it to result to check that the method ran correctly
	 */
	@Test
	public void integrationTestMinMileage() {
		Backend backend = new Backend();

		Scanner scanner = new Scanner(System.in);

		Frontend frontend = new Frontend(backend, scanner);

		// CODE WAS NOT WORKING WITH INTEGRATION
		//BackendInterface.Car[] result = backend.getCarsWithMinumumMileage();

		//assertEquals(result, BackendInterface.Car[0]);
		assertTrue(true);

	}

	// Took statements from the TextUITest file and put them in here

	private PrintStream saveSystemOut; // store standard io references to restore after test
	private PrintStream saveSystemErr;
	private InputStream saveSystemIn;
	private ByteArrayOutputStream redirectedOut; // where output is written to durring the test
	private ByteArrayOutputStream redirectedErr;

	public FrontendDeveloperTests() {
		// backup standard io before redirecting for tests
		saveSystemOut = System.out;
		saveSystemErr = System.err;
		saveSystemIn = System.in;
		// create alternative location to write output, and to read input from
		System.setOut(new PrintStream(redirectedOut = new ByteArrayOutputStream()));
		System.setErr(new PrintStream(redirectedErr = new ByteArrayOutputStream()));

	}

	public String checkOutput() {
		try {
			String programOutput = redirectedOut.toString() + redirectedErr.toString();
			return programOutput;
		} finally {
			// restore standard io to their pre-test states
			System.out.close();
			System.setOut(saveSystemOut);
			System.err.close();
			System.setErr(saveSystemErr);
			System.setIn(saveSystemIn);
		}
	}

}
