package xh.mybatis.mapper;


public interface DgnaMapper {
	
	/**
	 * 根据组ID查找组名
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public String groupNameById(int id)throws Exception;


}
