public class Car {
	private long license;
	private String model;
	private String brand;

	public Car(long license, String model, String brand) {
		this.license = license;
		this.model = model;
		this.brand = brand;
	}
	
	public long getLicense() {
		return license;
	}
	public void setLicense(long license) {
		this.license = license;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}

}
