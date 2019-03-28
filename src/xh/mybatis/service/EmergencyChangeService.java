package xh.mybatis.service;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.EmergencyChangeBean;
import xh.mybatis.mapper.EmergencyChangeMapper;
import xh.mybatis.tools.MoreDbTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artorius on 28/09/2017.
 */
public class EmergencyChangeService {
    /**
     * 申请记录
     * @return
     */
    public static List<EmergencyChangeBean> selectAll(Map<String, Object> map){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        EmergencyChangeMapper mapper = sqlSession.getMapper(EmergencyChangeMapper.class);
        List<EmergencyChangeBean> list=new ArrayList<EmergencyChangeBean>();
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
        EmergencyChangeMapper mapper = sqlSession.getMapper(EmergencyChangeMapper.class);
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
        EmergencyChangeMapper mapper = sqlSession.getMapper(EmergencyChangeMapper.class);
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
    public static int insertemergencyChange(EmergencyChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyChangeMapper mapper = sqlSession.getMapper(EmergencyChangeMapper.class);
        int result=0;
        try {
            result=mapper.insertEmergencyChange(bean);
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
     * 为演练组增加默认权限
     */
    public static int insertDefaultPower(Map<String,Object> param){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyChangeMapper mapper = sqlSession.getMapper(EmergencyChangeMapper.class);
        int result=0;
        try {
            result=mapper.insertDefaultPower(param);
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
    public static int checkedOne(EmergencyChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyChangeMapper mapper = sqlSession.getMapper(EmergencyChangeMapper.class);
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
     * @param
     * @return
     */
    public static int createEmergencyGroup(Map<String,Object> map){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyChangeMapper mapper = sqlSession.getMapper(EmergencyChangeMapper.class);
        int result=0;
        try {
            result=mapper.createEmergencyGroup(map);
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
    public static int checkedTwo(EmergencyChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyChangeMapper mapper = sqlSession.getMapper(EmergencyChangeMapper.class);
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
    public static int checkedThree(EmergencyChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyChangeMapper mapper = sqlSession.getMapper(EmergencyChangeMapper.class);
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
    public static int checkedFour(EmergencyChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyChangeMapper mapper = sqlSession.getMapper(EmergencyChangeMapper.class);
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
    public static int checkedFive(EmergencyChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        EmergencyChangeMapper mapper = sqlSession.getMapper(EmergencyChangeMapper.class);
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
}
