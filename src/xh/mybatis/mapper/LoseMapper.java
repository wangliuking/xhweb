package xh.mybatis.mapper;

import java.util.List;
import java.util.Map;

import xh.mybatis.bean.AssetInfoBean;
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
	public int insertAsset(AssetInfoBean bean)throws Exception;
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
	/**
	 * 根据序列号修改备注
	 */
	public int updateByNum(Map<String,Object> map)throws Exception;
	/**
	 * 根据序列号查询是否存在此信息
	 */
	public int countByNum(String serialNumber)throws Exception;
}
