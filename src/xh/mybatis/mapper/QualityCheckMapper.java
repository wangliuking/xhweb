package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.DeviceManageBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.QualityCheckBean;


public interface QualityCheckMapper {
    /**
     * 查询所有申请记录
     * @return
     * @throws Exception
     */
    public List<QualityCheckBean> selectAll(Map<String, Object> map)throws Exception;

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
    public int insertQualityCheck(QualityCheckBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedOne(QualityCheckBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedTwo(QualityCheckBean bean)throws Exception;

    public int checkedThree(QualityCheckBean bean)throws Exception;

    public int checkedFour(QualityCheckBean bean)throws Exception;

    public int checkedFive(QualityCheckBean bean)throws Exception;


}
