package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.DeviceManageBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.OptimizeNetBean;
import xh.mybatis.bean.OptimizeNetDropBean;


public interface OptimizeNetDropMapper {
    /**
     * 查询所有申请记录
     * @return
     * @throws Exception
     */
    public List<OptimizeNetDropBean> selectAll(Map<String, Object> map)throws Exception;

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
    public int insertOptimizeNetDrop(OptimizeNetDropBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedOne(OptimizeNetDropBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */

    public int checkedTwo(OptimizeNetDropBean bean)throws Exception;

    public int checkedThree(OptimizeNetDropBean bean)throws Exception;

    public int checkedFour(OptimizeNetDropBean bean)throws Exception;

    public int checkedFive(OptimizeNetDropBean bean)throws Exception;



}
