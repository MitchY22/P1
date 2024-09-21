import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public interface FrontendInterface {

//    /**
//     * This is the constructor that takes input from the backend as well as fron the scanner
//     **/
//    public FrontendInterface(BackendInterface backend, Scanner scanner) {
//    }

    
    /**
     * This method starts the main command loop for the program
     **/
    public void startApp();
    

    /**
     * This method will be used to load the file that contains the data for all of the different
     * cars that this program will be dealing with
     *
     * @param filename The file that the data is contained on
     * @throws FileNotFoundException when the file trying to be loaded cannot be found
     **/
    public void specifyDataFile(String filename) throws FileNotFoundException;

    /**
     * This method will list out the vehicles that have the lowest milage
     **/
    public void listVehiclesLowMilage();

    /**
     * This method will list out the vehicles that are at or above the specified threshold.
     * This threshold will be the lowest milage (inclusive) that the user wants to search for
     *
     * @param threshold the milage that the user wants to search at and above
     **/
    public void listVehiclesThreshold(double threshold);

    /**
     * This method will exit the loop that the user uses to select their options
     **/
    public void exit();
}
