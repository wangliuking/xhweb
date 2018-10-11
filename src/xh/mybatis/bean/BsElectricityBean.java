package xh.mybatis.bean;

public class BsElectricityBean {

	  private int bsId;
	  private String bs_supply_electricity_type;
	  private String bs_power_down_type;
	  private String transfer_supply_electricity_type;
	  private String transfer_power_down_type;
	  private String emh_supply_electricity_type;
	  private String emh_power_down_type;
	  private String has_spare_power;
	  private String spare_power_info;
	  private String bs_xh_require_time;
	  private String bs_xh_fact_time;
	  private String transfer_require_time;
	  private String transfer_fact_time;
	  private String emh_require_time;
	  private String emh_fact_time;
	  private String generation_to_bs_date;
	  private String is_allow_generation;
	  private String generation_date;
	  private String to_bs_date;
	  private String generatorConfig;
	  private String not_config_generator;
	  private String rectification_measures;
	  private String is_require;
	  private String not_require_reason;
	  private String rectification_measures2;
	public int getBsId() {
		return bsId;
	}
	public void setBsId(int bsId) {
		this.bsId = bsId;
	}

	public String getBs_supply_electricity_type() {
		return bs_supply_electricity_type;
	}
	public void setBs_supply_electricity_type(String bs_supply_electricity_type) {
		this.bs_supply_electricity_type = bs_supply_electricity_type;
	}
	public String getBs_power_down_type() {
		return bs_power_down_type;
	}
	public void setBs_power_down_type(String bs_power_down_type) {
		this.bs_power_down_type = bs_power_down_type;
	}
	public String getTransfer_supply_electricity_type() {
		return transfer_supply_electricity_type;
	}
	public void setTransfer_supply_electricity_type(
			String transfer_supply_electricity_type) {
		this.transfer_supply_electricity_type = transfer_supply_electricity_type;
	}
	public String getTransfer_power_down_type() {
		return transfer_power_down_type;
	}
	public void setTransfer_power_down_type(String transfer_power_down_type) {
		this.transfer_power_down_type = transfer_power_down_type;
	}
	public String getEmh_supply_electricity_type() {
		return emh_supply_electricity_type;
	}
	public void setEmh_supply_electricity_type(String emh_supply_electricity_type) {
		this.emh_supply_electricity_type = emh_supply_electricity_type;
	}
	public String getEmh_power_down_type() {
		return emh_power_down_type;
	}
	public void setEmh_power_down_type(String emh_power_down_type) {
		this.emh_power_down_type = emh_power_down_type;
	}
	public String getHas_spare_power() {
		return has_spare_power;
	}
	public void setHas_spare_power(String has_spare_power) {
		this.has_spare_power = has_spare_power;
	}
	public String getSpare_power_info() {
		return spare_power_info;
	}
	public void setSpare_power_info(String spare_power_info) {
		this.spare_power_info = spare_power_info;
	}
	public String getBs_xh_require_time() {
		return bs_xh_require_time;
	}
	public void setBs_xh_require_time(String bs_xh_require_time) {
		this.bs_xh_require_time = bs_xh_require_time;
	}
	public String getBs_xh_fact_time() {
		return bs_xh_fact_time;
	}
	public void setBs_xh_fact_time(String bs_xh_fact_time) {
		this.bs_xh_fact_time = bs_xh_fact_time;
	}
	public String getTransfer_require_time() {
		return transfer_require_time;
	}
	public void setTransfer_require_time(String transfer_require_time) {
		this.transfer_require_time = transfer_require_time;
	}
	public String getTransfer_fact_time() {
		return transfer_fact_time;
	}
	public void setTransfer_fact_time(String transfer_fact_time) {
		this.transfer_fact_time = transfer_fact_time;
	}
	public String getEmh_require_time() {
		return emh_require_time;
	}
	public void setEmh_require_time(String emh_require_time) {
		this.emh_require_time = emh_require_time;
	}
	public String getEmh_fact_time() {
		return emh_fact_time;
	}
	public void setEmh_fact_time(String emh_fact_time) {
		this.emh_fact_time = emh_fact_time;
	}
	public String getGeneration_to_bs_date() {
		return generation_to_bs_date;
	}
	public void setGeneration_to_bs_date(String generation_to_bs_date) {
		this.generation_to_bs_date = generation_to_bs_date;
	}
	public String getIs_allow_generation() {
		return is_allow_generation;
	}
	public void setIs_allow_generation(String is_allow_generation) {
		this.is_allow_generation = is_allow_generation;
	}
	public String getGeneration_date() {
		return generation_date;
	}
	public void setGeneration_date(String generation_date) {
		this.generation_date = generation_date;
	}
	public String getTo_bs_date() {
		return to_bs_date;
	}
	public void setTo_bs_date(String to_bs_date) {
		this.to_bs_date = to_bs_date;
	}
	public String getGeneratorConfig() {
		return generatorConfig;
	}
	public void setGeneratorConfig(String generatorConfig) {
		this.generatorConfig = generatorConfig;
	}
	public String getNot_config_generator() {
		return not_config_generator;
	}
	public void setNot_config_generator(String not_config_generator) {
		this.not_config_generator = not_config_generator;
	}
	public String getRectification_measures() {
		return rectification_measures;
	}
	public void setRectification_measures(String rectification_measures) {
		this.rectification_measures = rectification_measures;
	}
	public String getIs_require() {
		return is_require;
	}
	public void setIs_require(String is_require) {
		this.is_require = is_require;
	}
	public String getNot_require_reason() {
		return not_require_reason;
	}
	public void setNot_require_reason(String not_require_reason) {
		this.not_require_reason = not_require_reason;
	}
	public String getRectification_measures2() {
		return rectification_measures2;
	}
	public void setRectification_measures2(String rectification_measures2) {
		this.rectification_measures2 = rectification_measures2;
	}
	@Override
	public String toString() {
		return "BsElectricityBean [bsId=" + bsId
				+ ", bs_supply_electricity_type=" + bs_supply_electricity_type
				+ ", bs_power_down_type=" + bs_power_down_type
				+ ", transfer_supply_electricity_type="
				+ transfer_supply_electricity_type
				+ ", transfer_power_down_type=" + transfer_power_down_type
				+ ", emh_supply_electricity_type="
				+ emh_supply_electricity_type + ", emh_power_down_type="
				+ emh_power_down_type + ", has_spare_power=" + has_spare_power
				+ ", spare_power_info=" + spare_power_info
				+ ", bs_xh_require_time=" + bs_xh_require_time
				+ ", bs_xh_fact_time=" + bs_xh_fact_time
				+ ", transfer_require_time=" + transfer_require_time
				+ ", transfer_fact_time=" + transfer_fact_time
				+ ", emh_require_time=" + emh_require_time + ", emh_fact_time="
				+ emh_fact_time + ", generation_to_bs_date="
				+ generation_to_bs_date + ", is_allow_generation="
				+ is_allow_generation + ", generation_date=" + generation_date
				+ ", to_bs_date=" + to_bs_date + ", generatorConfig="
				+ generatorConfig + ", not_config_generator="
				+ not_config_generator + ", rectification_measures="
				+ rectification_measures + ", is_require=" + is_require
				+ ", not_require_reason=" + not_require_reason
				+ ", rectification_measures2=" + rectification_measures2 + "]";
	}
	  
	  



}
