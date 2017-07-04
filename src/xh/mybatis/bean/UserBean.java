package xh.mybatis.bean;

import java.io.Serializable;

public class UserBean implements Serializable{
	private static final long serialVersionUID = 3574211222602302068L; 
	private Integer id;
	private String name;
	private int age;
	
	public UserBean(){
		//super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	

}
