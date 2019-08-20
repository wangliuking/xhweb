package xh.mybatis.bean;

public class MoveBusBean {
	private int id;
	private String carNumber;
	private String model;
	private String location;
	private int state;
	private String frequency;
	public int getId() {
		return id;
	}
	public String getCarNumber() {
		return carNumber;
	}
	public String getModel() {
		return model;
	}
	public String getLocation() {
		return location;
	}
	public int getState() {
		return state;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setState(int state) {
		this.state = state;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	@Override
	public String toString() {
		return "MoveBusBean [id=" + id + ", carNumber=" + carNumber
				+ ", model=" + model + ", location=" + location + ", state="
				+ state + ", frequency=" + frequency + "]";
	}
	
	

}
