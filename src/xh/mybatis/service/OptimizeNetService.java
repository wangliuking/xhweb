package xh.mybatis.service;

import org.apache.ibatis.session.SqlSession;
import xh.mybatis.bean.FaultBean;
import xh.mybatis.bean.OptimizeNetBean;
import xh.mybatis.mapper.FaultMapper;
import xh.mybatis.mapper.OptimizeNetMapper;
import xh.mybatis.tools.MoreDbTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artorius on 28/09/2017.
 */
public class OptimizeNetService {
    /**
     * 申请记录
     * @return
     */
    public static List<OptimizeNetBean> selectAll(Map<String, Object> map){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        OptimizeNetMapper mapper = sqlSession.getMapper(OptimizeNetMapper.class);
        List<OptimizeNetBean> list=new ArrayList<OptimizeNetBean>();
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
        OptimizeNetMapper mapper = sqlSession.getMapper(OptimizeNetMapper.class);
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
        OptimizeNetMapper mapper = sqlSession.getMapper(OptimizeNetMapper.class);
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
    public static int insertOptimizeNet(OptimizeNetBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeNetMapper mapper = sqlSession.getMapper(OptimizeNetMapper.class);
        int result=0;
        try {
            result=mapper.insertOptimizeNet(bean);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 管理方审核
     * @param bean
     * @return
     */
    public static int checkedOne(OptimizeNetBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeNetMapper mapper = sqlSession.getMapper(OptimizeNetMapper.class);
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
    public static int checkedTwo(OptimizeNetBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeNetMapper mapper = sqlSession.getMapper(OptimizeNetMapper.class);
        int result=0;
        try {
            result=mapper.checkedTwo(bean);
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
    public static int checkedThree(OptimizeNetBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeNetMapper mapper = sqlSession.getMapper(OptimizeNetMapper.class);
        int result=0;
        try {
            result=mapper.checkedThree(bean);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 管理方入库信息
     * @param bean
     * @return
     */
    public static int checkedFour(OptimizeNetBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeNetMapper mapper = sqlSession.getMapper(OptimizeNetMapper.class);
        int result=0;
        try {
            result=mapper.checkedFour(bean);
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
    public static int checkedFive(OptimizeNetBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeNetMapper mapper = sqlSession.getMapper(OptimizeNetMapper.class);
        int result=0;
        try {
            result=mapper.checkedFive(bean);
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
    public static int checkedSix(OptimizeNetBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeNetMapper mapper = sqlSession.getMapper(OptimizeNetMapper.class);
        int result=0;
        try {
            result=mapper.checkedSix(bean);
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
    public static int checkedSeven(OptimizeNetBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        OptimizeNetMapper mapper = sqlSession.getMapper(OptimizeNetMapper.class);
        int result=0;
        try {
            result=mapper.checkedSeven(bean);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
}
