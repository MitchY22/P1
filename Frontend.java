import java.util.List;
import java.util.Scanner;

/**
 * This class contains the code that allows the frontend to work properly/
 * This class also implements the FrontendInterface
 *
 * @author Mitchell Young
 **/
public class Frontend implements FrontendInterface {

	private Scanner input = new Scanner(System.in);
	private Backend backend = new Backend();

    // Constructor for the frontend
	public Frontend(Backend backend, Scanner scanner) {
		this.backend = backend;
		this.input = scanner;
	}
    /**
     * This method contains the code for the menu loop that runs the application
     **/
	@Override
	public void startApp() {
		System.out.println("Welcome to the Car application!");

		// Sets the varaible so it keeps running
		boolean running = true;

		// Starts the menu loop
		while (running) {
			// Prints menu
			System.out.println("\nMenu:");
			System.out.println("1. Load a data file");
			System.out.println("2. List vehicles with the lowest mileage");
			System.out.println("3. List vehicles at or above a mileage threshold");
			System.out.println("4. Exit");
			System.out.print("Enter your choice (1, 2, etc): ");

			// Checks to make sure the command is an integer
			if (input.hasNextInt()) {
				// If is is, coninue with loop
				int userCommand = this.input.nextInt();

				switch (userCommand) {
				
				case 1: // Accessing the file
					System.out.println("Please input your filename (relative or absolute path)");
					this.input.nextLine();
					String filename = this.input.nextLine();
					specifyDataFile(filename);
					break;
				case 2: // Listing the vehicles with the lowest mileage
					listVehiclesLowMilage();
					break;
				case 3: // Listing the vehicles at or above a threshold
					System.out.println("Please specify the threshold you wish to search at and above: ");
					this.input.nextLine();
					double threshold = input.nextDouble();
					listVehiclesThreshold(threshold);
					break;
				case 4: // Exit app
					exit();
					running = false;
					break;
				default:
					System.out.println("Invalid command. Please try again!");
				}
				// Happens when the command isn't an integer
			} else {
				System.out.println("Please enter a Integer!");
				input.next();
			}
		}
	}

    /**
     * This method allows the user to give a data file and then it calls backend to load it
     **/
	@Override
	public void specifyDataFile(String filename) {
		// Loading the file
		try{
			this.backend.readFile(filename);
			System.out.println("File loaded successfully");
		}catch(IllegalArgumentException e){
		    System.out.println("Error occured: Please try again!");
		}
	}

    /**
     * this method lists the vehicles with the lowest mileage
     **/
	@Override
	public void listVehiclesLowMilage() {

	    // Goes through the list of min mileage cars and prints them out with all of their details
		try{
		    List<BackendInterface.Data> minimum_mileage_vehicals = this.backend.getCarsWithMinumumMileage();
		    System.out.println("Vehicles with the lowest milage:");
			for(int i=0;i<minimum_mileage_vehicals.size();i++){
				BackendInterface.Data car = minimum_mileage_vehicals.get(i);
				System.out.println("Car "+i+": "+car.getBrand()+" "+car.getModel()+" "+car.getColor()+" "+
				car.getPrice()+" "+car.getMileage()+" "+car.getYear()+" "+car.getTin()+" "+car.getTitleStatus());
			}
		}catch(Exception e){
			System.out.println("Error occured: Please try again!");
		}
	}

    /**
     * This method lists the vehicles at or above the threshold given by the user
     **/
	@Override
	public void listVehiclesThreshold(double threshold) {

	    // Goes through the list of cars at or above a threshold and prints them out one by one with all their details
		try{
		    List<BackendInterface.Data> above_threshold_vehicals = this.backend.getCarsAtAboveMileage(threshold);
		  
			System.out.println("Vehicles with mileage " + threshold + " and above:");
			for(int i=0;i<above_threshold_vehicals.size();i++){
				BackendInterface.Data car = above_threshold_vehicals.get(i);
				System.out.println("Car "+i+": "+car.getBrand()+" "+car.getModel()+" "+car.getColor()+" "+
				car.getPrice()+" "+car.getMileage()+" "+car.getYear()+" "+car.getTin()+" "+car.getTitleStatus());
			}
		}catch(Exception e){
			System.out.println("Error occured: Please try again!");
		}

	}

    /**
     * This method exits the app
     **/
	@Override
	public void exit() {
		System.out.println("Thank you for using the app!");
		System.out.println("Exiting application...");
	}

    /**
     * This is the main method that is used to run the app
     **/
    public static void main(String[] args) {
	Scanner input = new Scanner(System.in);
	Backend backend = new Backend();
	Frontend frontend = new Frontend(backend, input);
	frontend.startApp();
    }
}
