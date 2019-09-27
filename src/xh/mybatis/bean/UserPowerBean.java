package xh.mybatis.bean;
 
public class UserPowerBean {
	
	
	/*用户ID*/
	private int roleId;
	private int userId;
	private String user;
	
	/*运维管理权限*/
	private String o_check_report="off"; //'报告审核',
	private String o_check_service="off"; //'服务抽检审核',
	private String o_check_netoptimize="off"; //'网络优化审核',
	private String o_check_emergency="off"; //'应急处置演练审核',
	private String o_check_work="off"; //'工作记录审核',
	private String o_check_change="off"; //'变更管理审核',
	private String o_check_add_asset="off"; //'新增资产审核',
	private String o_check_scrap_asset="off";//报废资产审核
	private String o_add="off"; //'运维模块-添加数据',
	private String o_update="off"; //'运维模块-修改数据',
	private String o_delete="off"; //'运维模块-删除数据',
	private String o_check_fault="off"; //'运维管理-故障处理审核',
	private String o_check_duty="off"; //'运维管理-运维值班文件审核',
	private String o_check_inspection="off"; //'运维管理-运维巡检文件审核',
	private String o_order="off"; //'运维管理-运维派单',
	private String o_check_cut="off";//审核核减流程
	private String o_check_system_up="off";//审核系统升级
	private String o_check_asset_update_status="off";//审核资产状态变更
	private String o_check_operations_check="off";//审核运维考核
	private String o_task="off";//确认工作联系单
	private String o_gpsset="off";//gps设置
	private String o_radio_open="off";//遥启
	public String getO_radio_open() {
		return o_radio_open;
	}
	public String getO_radio_close() {
		return o_radio_close;
	}
	public void setO_radio_open(String o_radio_open) {
		this.o_radio_open = o_radio_open;
	}
	public void setO_radio_close(String o_radio_close) {
		this.o_radio_close = o_radio_close;
	}
	private String o_radio_close="off";//摇晕

	
	
	/*平台管理权限*/
	
	private String p_add="off"; //'平台管理-添加',
	private String p_update="off"; //'平台管理-修改',
	private String p_delete="off";// '平台管理-删除',
	private String p_lock="off"; //'平台管理-账号加锁',
	private String p_power="off"; //'平台管理-权限分配',
	
	
	/*业务管理权限*/
	
	private String b_add="off"; //'业务管理-添加',
	private String b_update="off"; //'业务管理-修改',
	private String b_delete="off"; //'业务管理-删除',
	private String b_check_joinnet="off"; //'业务管理-审核入网申请',
	private String b_check_quitnet="off" ;//'审核退网',
	private String b_check_businesschange="off";//'审核业务变更',
	private String b_check_fault="off";//'审核故障申报',
	private String b_check_communicationsupport="off" ;//'审核通信保障',
	private String b_check_lend="off";//'审核租借',
	
	
	
	private String r_order="off";//派单
	private String recv_work_contact="off"; //工作联系单
	private String r_cut="off";   //发起核检
	private String r_system_up="off";//发起系统升级
	private String r_netoptimize="off"; //'网络优化',
	private String r_emergency="off"; //'应急处置演练',
			
	
	
	
	public String getO_check_operations_check() {
		return o_check_operations_check;
	}
	public void setO_check_operations_check(String o_check_operations_check) {
		this.o_check_operations_check = o_check_operations_check;
	}
	public String getO_check_add_asset() {
		return o_check_add_asset;
	}
	public void setO_check_add_asset(String o_check_add_asset) {
		this.o_check_add_asset = o_check_add_asset;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getP_add() {
		return p_add;
	}
	public void setP_add(String p_add) {
		this.p_add = p_add;
	}
	public String getP_update() {
		return p_update;
	}
	public void setP_update(String p_update) {
		this.p_update = p_update;
	}
	public String getP_delete() {
		return p_delete;
	}
	public void setP_delete(String p_delete) {
		this.p_delete = p_delete;
	}
	public String getP_lock() {
		return p_lock;
	}
	public void setP_lock(String p_lock) {
		this.p_lock = p_lock;
	}
	public String getB_add() {
		return b_add;
	}
	public void setB_add(String b_add) {
		this.b_add = b_add;
	}
	public String getB_update() {
		return b_update;
	}
	public void setB_update(String b_update) {
		this.b_update = b_update;
	}
	public String getB_delete() {
		return b_delete;
	}
	public void setB_delete(String b_delete) {
		this.b_delete = b_delete;
	}
	
	public String getO_check_report() {
		return o_check_report;
	}
	public void setO_check_report(String o_check_report) {
		this.o_check_report = o_check_report;
	}
	public String getO_check_service() {
		return o_check_service;
	}
	public void setO_check_service(String o_check_service) {
		this.o_check_service = o_check_service;
	}
	public String getO_check_netoptimize() {
		return o_check_netoptimize;
	}
	public void setO_check_netoptimize(String o_check_netoptimize) {
		this.o_check_netoptimize = o_check_netoptimize;
	}
	public String getO_check_emergency() {
		return o_check_emergency;
	}
	public void setO_check_emergency(String o_check_emergency) {
		this.o_check_emergency = o_check_emergency;
	}
	public String getO_check_work() {
		return o_check_work;
	}
	public void setO_check_work(String o_check_work) {
		this.o_check_work = o_check_work;
	}
	public String getO_check_change() {
		return o_check_change;
	}
	public void setO_check_change(String o_check_change) {
		this.o_check_change = o_check_change;
	}
	public String getO_add() {
		return o_add;
	}
	public void setO_add(String o_add) {
		this.o_add = o_add;
	}
	public String getO_update() {
		return o_update;
	}
	public void setO_update(String o_update) {
		this.o_update = o_update;
	}
	public String getO_delete() {
		return o_delete;
	}
	public void setO_delete(String o_delete) {
		this.o_delete = o_delete;
	}
	public String getB_check_joinnet() {
		return b_check_joinnet;
	}
	public void setB_check_joinnet(String b_check_joinnet) {
		this.b_check_joinnet = b_check_joinnet;
	}
	public String getB_check_quitnet() {
		return b_check_quitnet;
	}
	public void setB_check_quitnet(String b_check_quitnet) {
		this.b_check_quitnet = b_check_quitnet;
	}
	public String getB_check_businesschange() {
		return b_check_businesschange;
	}
	public void setB_check_businesschange(String b_check_businesschange) {
		this.b_check_businesschange = b_check_businesschange;
	}
	public String getB_check_fault() {
		return b_check_fault;
	}
	public void setB_check_fault(String b_check_fault) {
		this.b_check_fault = b_check_fault;
	}
	public String getB_check_communicationsupport() {
		return b_check_communicationsupport;
	}
	public void setB_check_communicationsupport(String b_check_communicationsupport) {
		this.b_check_communicationsupport = b_check_communicationsupport;
	}
	public String getB_check_lend() {
		return b_check_lend;
	}
	public void setB_check_lend(String b_check_lend) {
		this.b_check_lend = b_check_lend;
	}
	
	public String getP_power() {
		return p_power;
	}
	public void setP_power(String p_power) {
		this.p_power = p_power;
	}
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public String getO_check_fault() {
		return o_check_fault;
	}
	public void setO_check_fault(String o_check_fault) {
		this.o_check_fault = o_check_fault;
	}
	public String getO_check_duty() {
		return o_check_duty;
	}
	public void setO_check_duty(String o_check_duty) {
		this.o_check_duty = o_check_duty;
	}
	public String getO_check_inspection() {
		return o_check_inspection;
	}
	public void setO_check_inspection(String o_check_inspection) {
		this.o_check_inspection = o_check_inspection;
	}
	
	public String getO_order() {
		return o_order;
	}
	public void setO_order(String o_order) {
		this.o_order = o_order;
	}
	
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public String getO_check_scrap_asset() {
		return o_check_scrap_asset;
	}
	public void setO_check_scrap_asset(String o_check_scrap_asset) {
		this.o_check_scrap_asset = o_check_scrap_asset;
	}
	
	public String getRecv_work_contact() {
		return recv_work_contact;
	}
	public void setRecv_work_contact(String recv_work_contact) {
		this.recv_work_contact = recv_work_contact;
	}


	
	public String getO_check_cut() {
		return o_check_cut;
	}
	public void setO_check_cut(String o_check_cut) {
		this.o_check_cut = o_check_cut;
	}
	
	public String getO_check_system_up() {
		return o_check_system_up;
	}
	public void setO_check_system_up(String o_check_system_up) {
		this.o_check_system_up = o_check_system_up;
	}

	public String getR_order() {
		return r_order;
	}
	public void setR_order(String r_order) {
		this.r_order = r_order;
	}
	public String getR_cut() {
		return r_cut;
	}
	public void setR_cut(String r_cut) {
		this.r_cut = r_cut;
	}
	public String getR_system_up() {
		return r_system_up;
	}
	public void setR_system_up(String r_system_up) {
		this.r_system_up = r_system_up;
	}
	public String getR_netoptimize() {
		return r_netoptimize;
	}
	public void setR_netoptimize(String r_netoptimize) {
		this.r_netoptimize = r_netoptimize;
	}
	public String getR_emergency() {
		return r_emergency;
	}
	public void setR_emergency(String r_emergency) {
		this.r_emergency = r_emergency;
	}
	public String getO_check_asset_update_status() {
		return o_check_asset_update_status;
	}
	public void setO_check_asset_update_status(String o_check_asset_update_status) {
		this.o_check_asset_update_status = o_check_asset_update_status;
	}
	
	public String getO_gpsset() {
		return o_gpsset;
	}
	public void setO_gpsset(String o_gpsset) {
		this.o_gpsset = o_gpsset;
	}
	public String getO_task() {
		return o_task;
	}
	public void setO_task(String o_task) {
		this.o_task = o_task;
	}
	@Override
	public String toString() {
		return "UserPowerBean [roleId=" + roleId + ", userId=" + userId
				+ ", user=" + user + ", o_check_report=" + o_check_report
				+ ", o_check_service=" + o_check_service
				+ ", o_check_netoptimize=" + o_check_netoptimize
				+ ", o_check_emergency=" + o_check_emergency
				+ ", o_check_work=" + o_check_work + ", o_check_change="
				+ o_check_change + ", o_check_add_asset=" + o_check_add_asset
				+ ", o_check_scrap_asset=" + o_check_scrap_asset + ", o_add="
				+ o_add + ", o_update=" + o_update + ", o_delete=" + o_delete
				+ ", o_check_fault=" + o_check_fault + ", o_check_duty="
				+ o_check_duty + ", o_check_inspection=" + o_check_inspection
				+ ", o_order=" + o_order + ", o_check_cut=" + o_check_cut
				+ ", o_check_system_up=" + o_check_system_up
				+ ", o_check_asset_update_status="
				+ o_check_asset_update_status + ", p_add=" + p_add
				+ ", p_update=" + p_update + ", p_delete=" + p_delete
				+ ", p_lock=" + p_lock + ", p_power=" + p_power + ", b_add="
				+ b_add + ", b_update=" + b_update + ", b_delete=" + b_delete
				+ ", b_check_joinnet=" + b_check_joinnet + ", b_check_quitnet="
				+ b_check_quitnet + ", b_check_businesschange="
				+ b_check_businesschange + ", b_check_fault=" + b_check_fault
				+ ", b_check_communicationsupport="
				+ b_check_communicationsupport + ", b_check_lend="
				+ b_check_lend + ", r_order=" + r_order
				+ ", recv_work_contact=" + recv_work_contact + ", r_cut="
				+ r_cut + ", r_system_up=" + r_system_up + ", r_netoptimize="
				+ r_netoptimize + ", r_emergency=" + r_emergency + "]";
	}
	
	
	

}
