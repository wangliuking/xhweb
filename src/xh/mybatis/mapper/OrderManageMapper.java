package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.OrderManageBean;


public interface OrderManageMapper {
    /**
     * 查询所有故障处理记录
     * @return
     * @throws Exception
     */
    public List<OrderManageBean> selectAll(Map<String, Object> map)throws Exception;

    /**
     * 处理进度查询
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
     * 故障处理入库
     * @param bean
     * @return
     * @throws Exception
     */
    public int insertOrderManage(OrderManageBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedOne(OrderManageBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedTwo(OrderManageBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedThree(OrderManageBean bean)throws Exception;
    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int sureFile(OrderManageBean bean)throws Exception;

}
