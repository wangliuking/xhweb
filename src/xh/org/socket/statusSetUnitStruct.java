package xh.org.socket;

public class statusSetUnitStruct {
	private int operation;
	
	private int ssId;  //2
	private int number;  //4
	private String text;  //80
	
	public statusSetUnitStruct(){}
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
	public int getSsId() {
		return ssId;
	}
	public void setSsId(int ssId) {
		this.ssId = ssId;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "statusSetUnitStruct [operation=" + operation + ", ssId=" + ssId
				+ ", number=" + number + ", text=" + text + "]";
	}
	
	

}
