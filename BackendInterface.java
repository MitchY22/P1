import java.util.List;
/**
 * This interface specifies how a carsAccessor should behave
 * @author Sean
 *
 */
public interface BackendInterface{
	/**
	 * This interface specifies how a class Car should behave
	 * @author Sean
	 *
	 */
	public interface Data extends Comparable<Data>{
		/*
		 * Constructor methods for Car class
		public Car() {
			this("none", "none", 0, -1.00, "none", -1.00, "none");
		}

		public Car(int year, double price, double mileage, String color, String tin) {
			this("none", "none", year, price, "none", mileage, color);
		}
		
		public Car(int year, double price, String titleStatus, 
			double mileage, String color, String tin) {
			this("none", "none", year, price, titleStatus, mileage, color);
		}
		
		public Car(String brand, String model, int year, double price, String titleStatus, 
			double mileage, String color, String tin) {
			#TODO
		}
		*/
		
		
		// We will have accessors
		/**
		 * This method returns the brand
		 * @return brand
		 */
		public String getBrand();
		/**
		 * This method returns the model
		 * @return model
		 */
		public String getModel();
		/**
		 * This method returns the year of this car
		 * @return year
		 */
		public int getYear();
		/**
		 * This method returns the price
		 * @return price
		 */
		public double getPrice();
		/**
		 * This method returns the title status
		 * @return
		 */
		public String getTitleStatus();
		/**
		 * This method returns the mileage
		 * @return mileage
		 */
		public double getMileage();
		/**
		 * This method returns the color
		 * @return
		 */
		public String getColor();
		/**
		 * This method returns the tin
		 * @return
		 */
		public String getTin();
		//We will then have mutator
		/**
		 * This method changes the current brand of the car to another brand
		 * @param brand
		 * @throws IllegalArgumentException if given brand is not a String type
		 */
		public void updateBrand(String brand);
		
		/**
		 * This method changes the current model of the car to another model
		 * @param model
		 * @throws IllegalArgumentException if given model is not a String type
		 */
		public void updateModel(String model);
		
		/**
		 * This method changes the current year of the car to another year
		 * @param year
		 * @throws IllegalArgumentException if given year is not a int type
		 */
		public void updateYear(int year);
		
		/**
		 * This method changes the current price of the car to another price
		 * @param price
		 * @throws IllegalArgumentException if given price is not a double type
		 */
		public void updatePrice(double price);
		
		/**
		 * This method changes the current title status of the car to another title status
		 * @param newTitle
		 * @throws IllegalArgumentException if given title is not a String type
		 */
		public void updateTitleStatus(String newTitle);
		
		/**
		 * This method changes the current mileage of the car to another mileage
		 * @param mileage
		 * @throws IllegalArgumentException if given mileage is not a double type
		 */
		public void updateMileage(double mileage);
		
		/**
		 * This method changes the current color of the car to another color
		 * @param newColor
		 * @throws IllegalArgumentException if given color is not a String type
		 */
		public void updateColor(String newColor);
		
		/**
		 * This method changes the current tin of the car to another tin
		 * @param newTin
		 * @throws IllegalArgumentException if given tin is not a String type
		 */
		public void updateTin(String newTin);
	}
	
	//Accessor
	/**
	 * Return the current list of cars
	 * @return List<Car>
	 */
	public IterableMultiKeySortedCollectionInterface<Data> getCars();
	/**
	 * Clear the list of cars
	 */
	public void clearCars();
	/**
	 * This method reads a file and add it to the list of cars field
	 * @param filepath the relative file path to the file
	 * @throws IllegalArgumentException if given argument is not a file name or String object or file is not readable
	 */
	public void readFile(String filepath) throws IllegalArgumentException;
	
	/**
	 * This methods returns a list of Car object with mileage at least of the given argument
	 * @param mileage the threshold of car mileage to filter
	 * @return Car[] with at least given mileage
	 * @throws IllegalArgument if given mileage is not a double type or less than 0.
	 */
	public List<Data> getCarsAtAboveMileage(double mileage);
	
	/**
	 * This methods returns a list of Car object with the minimum mileage
	 * @return Car[] with least mileage
	 * @throws Exception
	 * @throws IllegalArgument if given mileage is not a double type or less than 0.
	 */
	public List<Data> getCarsWithMinumumMileage() throws Exception;
	
}
