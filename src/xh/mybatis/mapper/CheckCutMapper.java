package xh.mybatis.mapper;

import xh.mybatis.bean.CheckCutBean;
import java.util.List;
import java.util.Map;


public interface CheckCutMapper {
    /**
     * 查询所有申请记录
     * @return
     * @throws Exception
     */
    public List<CheckCutBean> selectAll(Map<String, Object> map)throws Exception;

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
     * 申请
     * @param bean
     * @return
     * @throws Exception
     */
    public int checkedNegTwo(CheckCutBean bean)throws Exception;
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
    public int checkedThree(CheckCutBean bean)throws Exception;

    public int checkedFour(CheckCutBean bean)throws Exception;

    public CheckCutBean sheetShow(Map<String,Object> param)throws Exception;

    public int sheetChange(CheckCutBean bean)throws  Exception;

    /**
     * 根据基站id查询详细信息，用于填充核减表
     */
    public Map<String,Object> selectBsInformationById(Map<String,Object> map)throws Exception;

}
