package xh.mybatis.mapper;

import java.util.List;

import xh.mybatis.bean.OfficeAddressBean;
import xh.mybatis.bean.OperationsBusBean;
import xh.mybatis.bean.OperationsInstrumentBean;

public interface OperationsConfigMapper {
	
	List<OfficeAddressBean> office_list() throws Exception;
	
	List<OperationsBusBean> bus_list() throws Exception;
	
	List<OperationsInstrumentBean> instrument_list() throws Exception;

}
