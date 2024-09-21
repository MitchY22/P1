import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FrontendPlaceholder implements FrontendInterface{

    private Backend backend;
    private Scanner scanner;

    public FrontendPlaceholder(Backend backend, Scanner scanner) {
		this.backend = backend;
        this.scanner = scanner;
	}


    @Override
    public void startApp(){
        System.out.println("App started");
        boolean running = true;
        while (running) {
			// Prints menu
			System.out.println("Menu:");
			System.out.println("1. Load a data file");
			System.out.println("2. List vehicles with the lowest mileage");
			System.out.println("3. List vehicles at or above a mileage threshold");
			System.out.println("4. Exit");
			System.out.print("Enter your choice (1, 2, etc): ");

			// Checks to make sure the command is an integer
			if (this.scanner.hasNextInt()) {
				// If is is, coninue with loop
				int userCommand = this.scanner.nextInt();

				switch (userCommand) {
				
				case 1: // Accessing the file
					try{
						System.out.println("Please enter your filepath (relative or absolute)");
						String filename = this.scanner.nextLine();
						specifyDataFile(filename);
					}catch(Exception e){
						System.out.println("File not loaded. Please try again. Error: "+e);
					}
					break;
				case 2: // Listing the vehicles with the lowest mileage
					listVehiclesLowMilage();
					break;
					
				case 3: // Listing the vehicles at or above a threshold
					System.out.println("Please specify the threshold you wish to search at and above: ");
					double threshold = this.scanner.nextDouble();
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
				this.scanner.next(); 

			}
		}
    }

    @Override
    public void specifyDataFile(String filepath) throws FileNotFoundException {
        try{
			this.backend.readFile(filepath);
			System.out.println("File loaded by backend and ready");
		}catch(Exception e){
			throw new FileNotFoundException("Error: "+e);
		}
    }

    @Override
    public void listVehiclesLowMilage() {
        System.out.println("A list of minimum mileage vehicals is returned.");
    }

    @Override
    public void listVehiclesThreshold(double threshold) {
        System.out.println("A list of vehicals specified by minimum threshold is returned.");
    }

    @Override
    public void exit() {
        System.out.println("App exited.");
    }
    
}
