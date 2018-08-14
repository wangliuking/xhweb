package xh.mybatis.service;

import org.apache.ibatis.session.SqlSession;
import xh.mybatis.bean.FaultBean;
import xh.mybatis.bean.OptimizeChangeBean;
import xh.mybatis.bean.OptimizeChangeSheet;
import xh.mybatis.mapper.FaultMapper;
import xh.mybatis.mapper.OptimizeChangeMapper;
import xh.mybatis.tools.MoreDbTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artorius on 28/09/2017.
 */
public class OptimizeChangeService {
    /**
     * 申请记录
     * @return
     */
    public static List<OptimizeChangeBean> selectAll(Map<String, Object> map){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
        List<OptimizeChangeBean> list=new ArrayList<OptimizeChangeBean>();
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
     * 申请进度查询
     * @param id
     * @return
     */
    public static Map<String,Object> applyProgress(int id){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
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
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
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
     * 申请
     * @param bean
     * @return
     */
    public static int insertOptimizeChange(OptimizeChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
        int result=0;
        try {
            result=mapper.insertOptimizeChange(bean);
            sqlSession.commit();
            result=1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return result;
    }
    /**
     * 管理方审核
     * @param bean
     * @return
     */
    public static int checkedOne(OptimizeChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedOne(bean);
            sqlSession.commit();
            result=1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return result;
    }
    /**
     *
     * @param bean
     * @return
     */
    public static int checkedTwo(OptimizeChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedTwo(bean);
            sqlSession.commit();
            result=1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return result;
    }
    /**
     *
     * @param bean
     * @return
     */
    public static int checkedThree(OptimizeChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedThree(bean);
            sqlSession.commit();
            result=1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return result;
    }
    /**
     * 管理方入库信息
     * @param bean
     * @return
     */
    public static int checkedFour(OptimizeChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedFour(bean);
            sqlSession.commit();
            result=1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return result;
    }

    /**
     *
     * @param bean
     * @return
     */
    public static int checkedFive(OptimizeChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedFive(bean);
            sqlSession.commit();
            result=1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return result;
    }

    /**
     *
     * @param bean
     * @return
     */
    public static int checkedSix(OptimizeChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedSix(bean);
            sqlSession.commit();
            result=1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            sqlSession.close();
        }
        return result;
    }

    public static OptimizeChangeSheet sheetShow(Map<String, Object> param) {
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
        OptimizeChangeSheet bean = null;
        try {
            bean = mapper.sheetShow(param);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return bean;
    }

    public static int sheetChange(OptimizeChangeSheet bean){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeChangeMapper mapper = sqlSession.getMapper(OptimizeChangeMapper.class);
        int result = 0;
        try {
            result = mapper.sheetChange(bean);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }

}
