package xh.constant;

public enum ConstantLog {
	ADD("1"),UPDATE("2"),DELETE("3"),OTHER("4"),CKECK("5");
	private String tag;	
	ConstantLog(String tag){
		this.tag=tag;
	}
	 @Override
     public String toString()
     {
         return this.tag;
     }
}
