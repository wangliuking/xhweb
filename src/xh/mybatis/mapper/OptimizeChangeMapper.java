package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;
import xh.mybatis.bean.OptimizeChangeBean;


public interface OptimizeChangeMapper {
    /**
     * 查询所有申请记录
     * @return
     * @throws Exception
     */
    public List<OptimizeChangeBean> selectAll(Map<String, Object> map)throws Exception;

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
    public int insertOptimizeChange(OptimizeChangeBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedOne(OptimizeChangeBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedTwo(OptimizeChangeBean bean)throws Exception;

    public int checkedThree(OptimizeChangeBean bean)throws Exception;

    public int checkedFour(OptimizeChangeBean bean)throws Exception;

    public int checkedFive(OptimizeChangeBean bean)throws Exception;

    public int checkedSix(OptimizeChangeBean bean)throws Exception;
}
