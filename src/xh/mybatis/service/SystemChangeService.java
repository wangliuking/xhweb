package xh.mybatis.service;

import org.apache.ibatis.session.SqlSession;
import xh.mybatis.bean.SystemChangeBean;
import xh.mybatis.bean.SystemChangeSheet;
import xh.mybatis.mapper.SystemChangeMapper;
import xh.mybatis.tools.MoreDbTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemChangeService {

    public static List<SystemChangeBean> selectAll(Map<String, Object> map){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        List<SystemChangeBean> list=new ArrayList<SystemChangeBean>();
        try {
            list = mapper.selectAll(map);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    public static Map<String,Object> applyProgress(int id){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
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

    public static int dataCount(Map<String, Object> map){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
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

    public static int insertSystemChange(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        int result=0;
        try {
            result=mapper.insertSystemChange(bean);
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

    public static int checkedOne(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
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

    public static int createSystemGroup(Map<String,Object> map){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        int result=0;
        try {
            result=mapper.createSystemGroup(map);
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

    public static int checkedTwo(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
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

    public static int checkedThree(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
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

    public static int checkedFour(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
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

    public static int checkedFive(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
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

    public static int checkedSix(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
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

    public static int checkedSenven(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedSenven(bean);
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

    public static int checkedEight(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedEight(bean);
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

    public static int checkedNine(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedNine(bean);
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

    public static int checkedNegOne(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedNegOne(bean);
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

    public static int checkedNegThree(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedNegThree(bean);
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

    public static int checkedNegFour(SystemChangeBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        int result=0;
        try {
            result=mapper.checkedNegFour(bean);
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

    public static SystemChangeSheet sheetShow(Map<String, Object> param) {
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        SystemChangeSheet bean = null;
        try {
            bean = mapper.sheetShow(param);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return bean;
    }

    public static Map<String,Object> selectSystemChangeById(Map<String, Object> param) {
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        Map<String,Object> res = null;
        try {
            res = mapper.selectSystemChangeById(param);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return res;
    }

    public static int sheetChange(SystemChangeSheet bean){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
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

    public static int insertDefaultPower(Map<String,Object> param){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        int result = 0;
        try {
            result = mapper.insertDefaultPower(param);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }

    public static int updateTypeAndQuestionById(SystemChangeBean bean){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        SystemChangeMapper mapper = sqlSession.getMapper(SystemChangeMapper.class);
        int result = 0;
        try {
            result = mapper.updateTypeAndQuestionById(bean);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }

}
