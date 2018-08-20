package xh.mybatis.mapper;

import xh.mybatis.bean.SystemChangeBean;
import xh.mybatis.bean.SystemChangeSheet;

import java.util.List;
import java.util.Map;


public interface SystemChangeMapper {

    public List<SystemChangeBean> selectAll(Map<String, Object> map)throws Exception;

    public Map<String,Object> applyProgress(int id)throws Exception;

    public int dataCount(Map<String, Object> map)throws Exception;

    public int insertSystemChange(SystemChangeBean bean)throws Exception;

    public int checkedOne(SystemChangeBean bean)throws Exception;

    public int createSystemGroup(Map<String, Object> map)throws Exception;

    public int checkedTwo(SystemChangeBean bean)throws Exception;

    public int checkedThree(SystemChangeBean bean)throws Exception;

    public int checkedFour(SystemChangeBean bean)throws Exception;

    public int checkedFive(SystemChangeBean bean)throws Exception;

    public int checkedSix(SystemChangeBean bean)throws Exception;

    public int checkedSenven(SystemChangeBean bean)throws Exception;

    public int checkedEight(SystemChangeBean bean)throws Exception;

    public int checkedNine(SystemChangeBean bean)throws Exception;

    public int checkedNegOne(SystemChangeBean bean)throws Exception;

    public int checkedNegThree(SystemChangeBean bean)throws Exception;

    public int checkedNegFour(SystemChangeBean bean)throws Exception;

    public SystemChangeSheet sheetShow(Map<String,Object> param)throws Exception;

    public int sheetChange(SystemChangeSheet bean)throws  Exception;

}
