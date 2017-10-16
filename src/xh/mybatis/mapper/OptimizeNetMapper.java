package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.DeviceManageBean;
import xh.mybatis.bean.JoinNetBean;
import xh.mybatis.bean.OptimizeNetBean;


public interface OptimizeNetMapper {
    /**
     * 查询所有申请记录
     * @return
     * @throws Exception
     */
    public List<OptimizeNetBean> selectAll(Map<String, Object> map)throws Exception;

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
    public int insertOptimizeNet(OptimizeNetBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedOne(OptimizeNetBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedTwo(OptimizeNetBean bean)throws Exception;

    public int checkedThree(OptimizeNetBean bean)throws Exception;

    public int checkedFour(OptimizeNetBean bean)throws Exception;

    public int checkedFive(OptimizeNetBean bean)throws Exception;


    public int checkedSix(OptimizeNetBean bean)throws Exception;

    public int checkedSeven(OptimizeNetBean bean)throws Exception;

}
