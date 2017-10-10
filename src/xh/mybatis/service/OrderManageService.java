package xh.mybatis.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.OrderManageBean;
import xh.mybatis.mapper.OrderManageMapper;
import xh.mybatis.tools.MoreDbTools;
import xh.mybatis.tools.MoreDbTools.DataSourceEnvironment;

public class OrderManageService {
    /**
     * 故障记录
     * @return
     */
    public static List<OrderManageBean> selectAll(Map<String, Object> map){
        SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
        OrderManageMapper mapper = sqlSession.getMapper(OrderManageMapper.class);
        List<OrderManageBean> list=new ArrayList<OrderManageBean>();
        try {
            list = mapper.selectAll(map);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 故障处理进度查询
     * @param id
     * @return
     */
    public static Map<String,Object> applyProgress(int id){
        SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
        OrderManageMapper mapper = sqlSession.getMapper(OrderManageMapper.class);
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map = mapper.applyProgress(id);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }
    /**
     * 总数
     * @return
     */
    public static int dataCount(Map<String, Object> map){
        SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.slave);
        OrderManageMapper mapper = sqlSession.getMapper(OrderManageMapper.class);
        int count=0;
        try {
            count=mapper.dataCount(map);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count;
    }
    /**
     * 故障记录入库
     * @param bean
     * @return
     */
    public static int insertOrderManage(OrderManageBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
        OrderManageMapper mapper = sqlSession.getMapper(OrderManageMapper.class);
        int result=0;
        try {
            result=mapper.insertOrderManage(bean);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    /**
     *
     * @param bean
     * @return
     */
    public static int checkedOne(OrderManageBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
        OrderManageMapper mapper = sqlSession.getMapper(OrderManageMapper.class);
        int result=0;
        try {
            result=mapper.checkedOne(bean);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    /**
     *
     * @param bean
     * @return
     */
    public static int checkedTwo(OrderManageBean bean) {
        SqlSession sqlSession = MoreDbTools.getSession(DataSourceEnvironment.master);
        OrderManageMapper mapper = sqlSession.getMapper(OrderManageMapper.class);
        int result = 0;
        try {
            result = mapper.checkedTwo(bean);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param bean
     * @return
     */
    public static int checkedThree(OrderManageBean bean) {
        SqlSession sqlSession = MoreDbTools.getSession(DataSourceEnvironment.master);
        OrderManageMapper mapper = sqlSession.getMapper(OrderManageMapper.class);
        int result = 0;
        try {
            result = mapper.checkedThree(bean);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    /**
     *
     * @param bean
     * @return
     */
    public static int sureFile(OrderManageBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(DataSourceEnvironment.master);
        OrderManageMapper mapper = sqlSession.getMapper(OrderManageMapper.class);
        int result=0;
        try {
            result=mapper.sureFile(bean);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
