package xh.org.socket;

public class statusSetStruct {
	private int operation;
	
	private int id;       //4
	private String name;  //16
	
	public statusSetStruct(){}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "statusSetStruct [operation=" + operation + ", id=" + id
				+ ", name=" + name + "]";
	}
	
	
	

}
