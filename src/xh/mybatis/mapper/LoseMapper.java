package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.Lose;

public interface LoseMapper {
	/**
	 * 查询
	 * @return
	 * @throws Exception
	 */
	public List<Lose> assetInfo(Map<String,Object> map)throws Exception;
	/**
	 * 总数
	 * @return
	 * @throws Exception
	 */
	public int assetInfoCount(Map<String,Object> map)throws Exception;
	/**
	 * 添加
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int insertAsset(Lose bean)throws Exception;
	/**
	 *修改
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int updateAsset(Lose bean)throws Exception;
	/**
	 * 删除
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int deleteAsset(List<String> list)throws Exception;


}
