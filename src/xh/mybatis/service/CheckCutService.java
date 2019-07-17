package xh.mybatis.service;

import org.apache.ibatis.session.SqlSession;
import xh.mybatis.bean.CheckCutBean;
import xh.mybatis.bean.CheckCutElecBean;
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
    public static List<Map<String,Object>> selectAll(Map<String, Object> map){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
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
     *删除
     * @param
     * @return
     */
    public static int deleteCheckCutById(int id){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        int result=0;
        try {
            result=mapper.deleteCheckCutById(id);
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
     *删除核减时重置故障表的核减状态
     * @param
     * @return
     */
    public static int updateFaultWhenDel(int id){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        int result=0;
        try {
            result=mapper.updateFaultWhenDel(id);
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
     * 修改核减依据
     * @param bean
     * @return
     */
    public static int updateCheckContent(CheckCutBean bean){
        SqlSession sqlSession =MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        int result=0;
        try {
            result=mapper.updateCheckContent(bean);
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
    public static List<Map<String,Object>> selectBsInformationById(Map<String, Object> map) {
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        List<Map<String,Object>> result = null;
        try {
            result = mapper.selectBsInformationById(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }

    /**
     * 根据调度台id查询详细信息，用于填充核减表
     * @param
     * @return
     */
    public static List<Map<String,Object>> selectDispathInformationById(Map<String, Object> map) {
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        List<Map<String,Object>> result = null;
        try {
            result = mapper.selectDispathInformationById(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }

    public static int updatePrintStatusById(int id){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        int result = 0;
        try {
            result = mapper.updatePrintStatusById(id);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }

    /**
     * 查询list用于导出word
     * @return
     */
    public static List<Map<String,Object>> exportWordByTime(Map<String, Object> map){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        try {
            list = mapper.exportWordByTime(map);
            sqlSession.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 查询考核发起时间，用于决定哪些核减无法修改
     * @param
     * @return
     */
    public static String selectCheckTimeForStatus() {
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        String result = "";
        try {
            result = mapper.selectCheckTimeForStatus();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }

    /**
     * 根据基站id查询发电说明
     * @param
     * @return
     */
    public static Map<String,Object> selectCheckCutElecById(int bsId) {
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        Map<String,Object> result = null;
        try {
            result = mapper.selectCheckCutElecById(bsId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }

    /**
     *更新发电说明
     * @return
     */
    public static int replaceCheckContent(CheckCutElecBean bean){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        int result = 0;
        try {
            result = mapper.replaceCheckContent(bean);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }

    /**
     *根据基站id删除发电记录
     * @return
     */
    public static int delElec(String bsId){
        SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.master);
        CheckCutMapper mapper = sqlSession.getMapper(CheckCutMapper.class);
        int result = 0;
        try {
            result = mapper.delElec(bsId);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
        return result;
    }
    
}
