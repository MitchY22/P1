/**
 * This class model the object Car
 * @author Sean
 *
 */
public class BackendIndividual implements BackendInterface.Data{
	private String brand;
	private String model;
	private int year;
	private double price;
	private String titleStatus;
	private double mileage;
	private String color;
	private String tin;
	//Constructor
	public BackendIndividual() {
		this("none", "none", 0, -1.00, "none", -1.00, "none", "none");
	}
	
	public BackendIndividual(int year, double price, double mileage, String color, String tin) {
		this("none", "none", year, price, "none", mileage, color, tin);
	}
	
	public BackendIndividual(int year, double price, String titleStatus, 
		double mileage, String color, String tin) {
		this("none", "none", year, price, titleStatus, mileage, color, tin);
	}
	
	public BackendIndividual(String brand, String model, int year, double price, String titleStatus, 
		double mileage, String color, String tin) {
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.price = price;
		this.titleStatus = titleStatus;
		this.mileage = mileage;
		this.color = color;
		this.tin = tin;
	}
	//Accessor
	@Override
	public String getBrand() {
		return this.brand;
	}

	@Override
	public String getModel() {
		return this.model;
	}

	@Override
	public int getYear() {
		return this.year;
	}

	@Override
	public double getPrice() {
		return this.price;
	}

	@Override
	public String getTitleStatus() {
		return this.titleStatus;
	}

	@Override
	public double getMileage() {
		return this.mileage;
	}

	@Override
	public String getColor() {
		return this.color;
	}

	@Override
	public String getTin() {
		return this.tin;
	}
	//Mutator
	@Override
	public void updateBrand(String brand) {
		this.brand = brand;
	}

	@Override
	public void updateModel(String model) {
		this.model = model;
	}

	@Override
	public void updateYear(int year) {
		this.year = year;
	}

	@Override
	public void updatePrice(double price) {
		this.price = price;
	}

	@Override
	public void updateTitleStatus(String newTitle) {
		this.titleStatus = newTitle;
	}

	@Override
	public void updateMileage(double mileage) {
		this.mileage = mileage;
	}

	@Override
	public void updateColor(String newColor) {
		this.color = newColor;
	}

	@Override
	public void updateTin(String newTin) {
		this.tin = newTin;
	}
	//Comparable method
	@Override
	public int compareTo(BackendInterface.Data o) {
		if(!(o instanceof BackendIndividual)) {
			throw new IllegalArgumentException("Only object must be comparable");
		}
		BackendIndividual comparingTo = (BackendIndividual) o;
		return (int) (this.mileage - comparingTo.getMileage())*100;
	}

}
