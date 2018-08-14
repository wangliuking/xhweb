package xh.mybatis.mapper;

import xh.mybatis.bean.EmergencyChangeBean;

import java.util.List;
import java.util.Map;


public interface EmergencyChangeMapper {
    /**
     * 查询所有申请记录
     * @return
     * @throws Exception
     */
    public List<EmergencyChangeBean> selectAll(Map<String, Object> map)throws Exception;

    /**
     * 申请进度查询
     * @param id
     * @return
     * @throws Exception
     */
    public Map<String,Object> applyProgress(int id)throws Exception;
    /**
     * 总数
     * @return
     * @throws Exception
     */
    public int dataCount(Map<String, Object> map)throws Exception;
    /**
     * 申请
     * @param bean
     * @return
     * @throws Exception
     */
    public int insertEmergencyChange(EmergencyChangeBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedOne(EmergencyChangeBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int createEmergencyGroup(Map<String,Object> map)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedTwo(EmergencyChangeBean bean)throws Exception;

    public int checkedThree(EmergencyChangeBean bean)throws Exception;

    public int checkedFour(EmergencyChangeBean bean)throws Exception;

    public int checkedFive(EmergencyChangeBean bean)throws Exception;

}
