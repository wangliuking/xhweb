package xh.mybatis.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.OfficeAddressBean;
import xh.mybatis.bean.OperationsBusBean;
import xh.mybatis.bean.OperationsInstrumentBean;
import xh.mybatis.mapper.OperationsConfigMapper;
import xh.mybatis.tools.MoreDbTools;

public class OfficeAddressService {
	public static List<OfficeAddressBean> office_list(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsConfigMapper mapper=sqlSession.getMapper(OperationsConfigMapper.class);
		List<OfficeAddressBean> list=new ArrayList<OfficeAddressBean>();
		try{
			list=mapper.office_list();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<OperationsBusBean> bus_list() {
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsConfigMapper mapper=sqlSession.getMapper(OperationsConfigMapper.class);
		List<OperationsBusBean> list=new ArrayList<OperationsBusBean>();
		try{
			list=mapper.bus_list();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<OperationsInstrumentBean> instrument_list(){
		SqlSession sqlSession=MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsConfigMapper mapper=sqlSession.getMapper(OperationsConfigMapper.class);
		List<OperationsInstrumentBean> list=new ArrayList<OperationsInstrumentBean>();
		try{
			list=mapper.instrument_list();
			sqlSession.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
