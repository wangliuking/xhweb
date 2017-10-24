package xh.mybatis.service;

import org.apache.ibatis.session.SqlSession;
import xh.mybatis.bean.FaultBean;
import xh.mybatis.bean.EmergencyBean;
import xh.mybatis.mapper.FaultMapper;
import xh.mybatis.mapper.EmergencyMapper;
import xh.mybatis.tools.MoreDbTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artorius on 28/09/2017.
 */
public class EmergencyService {
    /**
     * 申请记录
     * @return
     */
    public static List<EmergencyBean> selectAll(Map<String, Object> map){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        EmergencyMapper mapper = sqlSession.getMapper(EmergencyMapper.class);
        List<EmergencyBean> list=new ArrayList<EmergencyBean>();
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
        EmergencyMapper mapper = sqlSession.getMapper(EmergencyMapper.class);
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
        EmergencyMapper mapper = sqlSession.getMapper(EmergencyMapper.class);
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
    public static int insertEmergency(EmergencyBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyMapper mapper = sqlSession.getMapper(EmergencyMapper.class);
        int result=0;
        try {
            result=mapper.insertEmergency(bean);
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
    public static int checkedOne(EmergencyBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyMapper mapper = sqlSession.getMapper(EmergencyMapper.class);
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
    public static int checkedTwo(EmergencyBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyMapper mapper = sqlSession.getMapper(EmergencyMapper.class);
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
    public static int checkedThree(EmergencyBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyMapper mapper = sqlSession.getMapper(EmergencyMapper.class);
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
    public static int checkedFour(EmergencyBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyMapper mapper = sqlSession.getMapper(EmergencyMapper.class);
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
    public static int checkedFive(EmergencyBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyMapper mapper = sqlSession.getMapper(EmergencyMapper.class);
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
    /**
    *
    * @param bean
    * @return
    */
   public static int checkedSix(EmergencyBean bean){
       SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
       EmergencyMapper mapper = sqlSession.getMapper(EmergencyMapper.class);
       int result=0;
       try {
           result=mapper.checkedSix(bean);
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
  public static int checkedSeven(EmergencyBean bean){
      SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
      EmergencyMapper mapper = sqlSession.getMapper(EmergencyMapper.class);
      int result=0;
      try {
          result=mapper.checkedSeven(bean);
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
