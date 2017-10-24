package xh.mybatis.service;

import org.apache.ibatis.session.SqlSession;
import xh.mybatis.bean.FaultBean;
import xh.mybatis.bean.QualityCheckBean;
import xh.mybatis.mapper.FaultMapper;
import xh.mybatis.mapper.QualityCheckMapper;
import xh.mybatis.tools.MoreDbTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artorius on 28/09/2017.
 */
public class QualityCheckService {
    /**
     * 申请记录
     * @return
     */
    public static List<QualityCheckBean> selectAll(Map<String, Object> map){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        QualityCheckMapper mapper = sqlSession.getMapper(QualityCheckMapper.class);
        List<QualityCheckBean> list=new ArrayList<QualityCheckBean>();
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
        QualityCheckMapper mapper = sqlSession.getMapper(QualityCheckMapper.class);
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
        QualityCheckMapper mapper = sqlSession.getMapper(QualityCheckMapper.class);
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
    public static int insertQualityCheck(QualityCheckBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        QualityCheckMapper mapper = sqlSession.getMapper(QualityCheckMapper.class);
        int result=0;
        try {
            result=mapper.insertQualityCheck(bean);
            sqlSession.commit();
            result = 1 ;
        } catch (Exception e) {
        	sqlSession.close();
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
    public static int checkedOne(QualityCheckBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        QualityCheckMapper mapper = sqlSession.getMapper(QualityCheckMapper.class);
        int result=0;
        try {
            result=mapper.checkedOne(bean);
            sqlSession.commit();
            result = 1 ;
        } catch (Exception e) {
        	sqlSession.close();
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
    public static int checkedTwo(QualityCheckBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        QualityCheckMapper mapper = sqlSession.getMapper(QualityCheckMapper.class);
        int result=0;
        try {
            result=mapper.checkedTwo(bean);
            sqlSession.commit();
            result = 1 ;
        } catch (Exception e) {
        	sqlSession.close();
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
    public static int checkedThree(QualityCheckBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        QualityCheckMapper mapper = sqlSession.getMapper(QualityCheckMapper.class);
        int result=0;
        try {
            result=mapper.checkedThree(bean);
            sqlSession.commit();
            result = 1 ;
        } catch (Exception e) {
        	sqlSession.close();
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
    public static int checkedFour(QualityCheckBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        QualityCheckMapper mapper = sqlSession.getMapper(QualityCheckMapper.class);
        int result=0;
        try {
            result=mapper.checkedFour(bean);
            sqlSession.commit();
            result = 1 ;
        } catch (Exception e) {
        	sqlSession.close();
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
    public static int checkedFive(QualityCheckBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        QualityCheckMapper mapper = sqlSession.getMapper(QualityCheckMapper.class);
        int result=0;
        try {
            result=mapper.checkedFive(bean);
            sqlSession.commit();
            result = 1 ;
        } catch (Exception e) {
        	sqlSession.close();
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

}
