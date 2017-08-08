package xh.org.socket;

public class RtDataStruct {
	private String uuid;
	private String dnt;
	private SV sv;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getDnt() {
		return dnt;
	}
	public void setDnt(String dnt) {
		this.dnt = dnt;
	}
	public SV getSv() {
		return sv;
	}
	public void setSv(SV sv) {
		this.sv = sv;
	}
	@Override
	public String toString() {
		return "RtDataStruct [uuid=" + uuid + ", dnt=" + dnt + ", sv=" + sv.toString()
				+ "]";
	}
	
	
}

class SV{
	private String valueTitle;
	private int valueType;
	private String valueData;
	public String getValueTitle() {
		return valueTitle;
	}
	public void setValueTitle(String valueTitle) {
		this.valueTitle = valueTitle;
	}
	public int getValueType() {
		return valueType;
	}
	public void setValueType(int valueType) {
		this.valueType = valueType;
	}
	public String getValueData() {
		return valueData;
	}
	public void setValueData(String valueData) {
		this.valueData = valueData;
	}
	@Override
	public String toString() {
		return "SV [valueTitle=" + valueTitle + ", valueType=" + valueType
				+ ", valueData=" + valueData + "]";
	}
	
	
}
