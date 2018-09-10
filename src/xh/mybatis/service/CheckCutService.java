package xh.mybatis.service;

import org.apache.ibatis.session.SqlSession;
import xh.mybatis.bean.CheckCutBean;
import xh.mybatis.mapper.CheckCutMapper;
import xh.mybatis.tools.MoreDbTools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Artorius on 28/09/2017.
 */
public class CheckCutService {
    /**
     * 申请记录
     * @return
     */
    public static List<CheckCutBean> selectAll(Map<String, Object> map){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        List<CheckCutBean> list=new ArrayList<CheckCutBean>();
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
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
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
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
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
    public static int checkedNegTwo(CheckCutBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        int result=0;
        try {
            result=mapper.checkedNegTwo(bean);
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
    public static int createCheckCut(CheckCutBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        int result=0;
        try {
            result=mapper.createCheckCut(bean);
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
     * 申请
     * @param bean
     * @return
     */
    public static int insertCheckCut(CheckCutBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        int result=0;
        try {
            result=mapper.insertCheckCut(bean);
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
    public static int checkedOne(CheckCutBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
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
    public static int checkedTwo(CheckCutBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
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
    public static int checkedThree(CheckCutBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
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
     *
     * @param bean
     * @return
     */
    public static int checkedFour(CheckCutBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
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
     * @param param
     * @return
     */
    public static CheckCutBean sheetShow(Map<String, Object> param) {
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        CheckCutBean bean = null;
        try {
            bean = mapper.sheetShow(param);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return bean;
    }

    public static int sheetChange(CheckCutBean bean){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
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

    /**
     * 根据基站id查询详细信息，用于填充核减表
     * @param
     * @return
     */
    public static Map<String,Object> selectBsInformationById(Map<String, Object> map) {
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        Map<String,Object> result = null;
        try {
            result = mapper.selectBsInformationById(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }
    
}
