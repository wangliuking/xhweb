package xh.mybatis.bean;

public class EastBsCallDataBean {
	
	  private int  TotalActiveCalls ;
	  private Long  TotalActiveCallDuration=(long) 0 ;
	  private Long  AverageCallDuration=(long) 0 ;
	  private int  PTTCount ;
	  private int  QueueCount ;
	  private int  QueueDuration ;
	  private int  MaxUserRegCount ;
	  private int MaxGroupRegCount ;
	  private int MaxControlChanUpRate ;
	  private int MinControlChanUpRate ;
	  private int AvgControlChanUpRate ;
	  private int MaxControlChanDownRate ;
	  private int AvgTrafficChanRate ;
	  private int MinControlChanDownRate ;
	  private int AvgControlChanDownRate ;
	  private int bsid;
	  private String starttime ;
	  private String  endtime ;
	  private int mscid ;
	  private String name;
	  private String level;
	  private String area;
	  private String zone;
	  private int bsTotals;
	  private float percent;
	public int getTotalActiveCalls() {
		return TotalActiveCalls;
	}
	public void setTotalActiveCalls(int totalActiveCalls) {
		TotalActiveCalls = totalActiveCalls;
	}

	public Long getTotalActiveCallDuration() {
		return TotalActiveCallDuration;
	}
	public void setTotalActiveCallDuration(Long totalActiveCallDuration) {
		TotalActiveCallDuration = totalActiveCallDuration;
	}
	public Long getAverageCallDuration() {
		return AverageCallDuration;
	}
	public void setAverageCallDuration(Long averageCallDuration) {
		AverageCallDuration = averageCallDuration;
	}
	public int getPTTCount() {
		return PTTCount;
	}
	public void setPTTCount(int pTTCount) {
		PTTCount = pTTCount;
	}
	public int getQueueCount() {
		return QueueCount;
	}
	public void setQueueCount(int queueCount) {
		QueueCount = queueCount;
	}
	public int getQueueDuration() {
		return QueueDuration;
	}
	public void setQueueDuration(int queueDuration) {
		QueueDuration = queueDuration;
	}
	public int getMaxUserRegCount() {
		return MaxUserRegCount;
	}
	public void setMaxUserRegCount(int maxUserRegCount) {
		MaxUserRegCount = maxUserRegCount;
	}
	public int getMaxGroupRegCount() {
		return MaxGroupRegCount;
	}
	public void setMaxGroupRegCount(int maxGroupRegCount) {
		MaxGroupRegCount = maxGroupRegCount;
	}
	public int getMaxControlChanUpRate() {
		return MaxControlChanUpRate;
	}
	public void setMaxControlChanUpRate(int maxControlChanUpRate) {
		MaxControlChanUpRate = maxControlChanUpRate;
	}
	public int getMinControlChanUpRate() {
		return MinControlChanUpRate;
	}
	public void setMinControlChanUpRate(int minControlChanUpRate) {
		MinControlChanUpRate = minControlChanUpRate;
	}
	public int getAvgControlChanUpRate() {
		return AvgControlChanUpRate;
	}
	public void setAvgControlChanUpRate(int avgControlChanUpRate) {
		AvgControlChanUpRate = avgControlChanUpRate;
	}
	public int getMaxControlChanDownRate() {
		return MaxControlChanDownRate;
	}
	public void setMaxControlChanDownRate(int maxControlChanDownRate) {
		MaxControlChanDownRate = maxControlChanDownRate;
	}
	public int getAvgTrafficChanRate() {
		return AvgTrafficChanRate;
	}
	public void setAvgTrafficChanRate(int avgTrafficChanRate) {
		AvgTrafficChanRate = avgTrafficChanRate;
	}
	public int getMinControlChanDownRate() {
		return MinControlChanDownRate;
	}
	public void setMinControlChanDownRate(int minControlChanDownRate) {
		MinControlChanDownRate = minControlChanDownRate;
	}
	public int getAvgControlChanDownRate() {
		return AvgControlChanDownRate;
	}
	public void setAvgControlChanDownRate(int avgControlChanDownRate) {
		AvgControlChanDownRate = avgControlChanDownRate;
	}
	public int getBsid() {
		return bsid;
	}
	public void setBsid(int bsid) {
		this.bsid = bsid;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public int getMscid() {
		return mscid;
	}
	public void setMscid(int mscid) {
		this.mscid = mscid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public int getBsTotals() {
		return bsTotals;
	}
	public void setBsTotals(int bsTotals) {
		this.bsTotals = bsTotals;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	
	  
	  

}
