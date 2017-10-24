package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.DeviceManageBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.EmergencyBean;


public interface EmergencyMapper {
    /**
     * 查询所有申请记录
     * @return
     * @throws Exception
     */
    public List<EmergencyBean> selectAll(Map<String, Object> map)throws Exception;

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
    public int insertEmergency(EmergencyBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedOne(EmergencyBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedTwo(EmergencyBean bean)throws Exception;

    public int checkedThree(EmergencyBean bean)throws Exception;

    public int checkedFour(EmergencyBean bean)throws Exception;

    public int checkedFive(EmergencyBean bean)throws Exception;
    
    public int checkedSix(EmergencyBean bean)throws Exception;

    public int checkedSeven(EmergencyBean bean)throws Exception;


}
