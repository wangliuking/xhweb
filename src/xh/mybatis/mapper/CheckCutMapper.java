package xh.mybatis.mapper;

import xh.mybatis.bean.CheckCutBean;
import xh.mybatis.bean.CheckCutElecBean;

import java.util.List;
import java.util.Map;


public interface CheckCutMapper {
    /**
     * 查询所有申请记录
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> selectAll(Map<String, Object> map)throws Exception;

    /**
     * 申请进度查询
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
     * 删除
     * @param
     * @return
     * @throws Exception
     */
    public int deleteCheckCutById(int id)throws Exception;
    /**
     * 删除核减时重置故障表的核减状态
     * @param
     * @return
     * @throws Exception
     */
    public int updateFaultWhenDel(int id)throws Exception;
    /**
     * 申请
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedNegTwo(CheckCutBean bean)throws Exception;
    /**
     * 修改核减依据
     * @param bean
     * @return
     * @throws Exception
     */
    public int updateCheckContent(CheckCutBean bean)throws Exception;
    /**
     * 申请
     * @param bean
     * @return
     * @throws Exception
     */
    public int insertCheckCut(CheckCutBean bean)throws Exception;
    /**
     * 申请
     * @param bean
     * @return
     * @throws Exception
     */
    public int createCheckCut(CheckCutBean bean)throws Exception;

    /**
     * @param bean
     * @return
     * @throws Exception
     */
    public int synCheckCut(CheckCutBean bean)throws Exception;

    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedOne(CheckCutBean bean)throws Exception;

    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedTwo(CheckCutBean bean)throws Exception;

    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int appCheckedTwo(CheckCutBean bean)throws Exception;

    /**
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedThree(CheckCutBean bean)throws Exception;

    public int checkedFour(CheckCutBean bean)throws Exception;

    public CheckCutBean sheetShow(Map<String,Object> param)throws Exception;

    public int sheetChange(CheckCutBean bean)throws  Exception;

    /**
     * 根据基站id查询详细信息，用于填充核减表
     */
    public List<Map<String,Object>> selectBsInformationById(Map<String,Object> map)throws Exception;

    /**
     * 根据调度台d查询详细信息，用于填充核减表
     */
    public List<Map<String,Object>> selectDispathInformationById(Map<String,Object> map)throws Exception;

    public int updatePrintStatusById(int id)throws  Exception;

    /**
     * 查询list用于导出word
     * @return
     * @throws Exception
     */
    public List<Map<String,Object>> exportWordByTime(Map<String, Object> map)throws Exception;

    /**
     * 查询考核发起时间，用于决定哪些核减无法修改
     */
    public String selectCheckTimeForStatus()throws Exception;

    /**
     * 根据基站id查询发电说明
     */
    public Map<String,Object> selectCheckCutElecById(int bsId)throws Exception;

    /**
     * 更新发电说明
     * @return
     * @throws Exception
     */
    public int replaceCheckContent(CheckCutElecBean bean)throws  Exception;

    /**
     * 根据基站id删除发电记录
     */
    public int delElec(String bsId)throws Exception;

    public int changeRequestTime(Map<String,Object> param)throws Exception;

    public List<Map<String,Object>> selectBreakTime(Map<String,Object> param)throws Exception;

    public int updateApplyTime(Map<String,Object> param)throws Exception;

}
