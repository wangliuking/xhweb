package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

public interface WebAppMapper {
	int app_bs_gd_count() throws Exception;
	
	int app_verticalbs_count() throws Exception;
	
	Map<String,Object> app_room() throws Exception;
	
	Map<String,Object> app_portable() throws Exception;
	
	Map<String,Object> app_movebus() throws Exception;
	
    List<Map<String,Object>> app_subway() throws Exception;
	
	int app_dispatch_count() throws Exception;
	
	int app_access_count() throws Exception;
	
	int app_vpn_count() throws Exception;

}
