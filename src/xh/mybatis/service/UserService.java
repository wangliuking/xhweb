package xh.mybatis.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.UserBean;
import xh.mybatis.mapper.UserMapper;
import xh.mybatis.tools.DbTools;

public class UserService {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//insert();
		selectAllUser();
	}
    public static void insert(){
    	SqlSession session=DbTools.getSession();
    	UserMapper mapper=session.getMapper(UserMapper.class);
    	UserBean userBean=new UserBean();
		userBean.setName("猴子22");
		userBean.setAge(56);
		try {
			mapper.insertUser(userBean);
			session.commit();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();session.rollback();
		}
    }
	private static void selectAllUser(){
		SqlSession session=DbTools.getSession();
		UserMapper mapper=session.getMapper(UserMapper.class);
		
		
		try {
			Long time1=new Date().getTime();
			System.out.println("开始时间："+time1);
	        List<UserBean> user=mapper.selectAllUser();
	        /*System.out.println(user.toString());*/
	        session.commit();
	        
	        for (int i = 0; i < user.size(); i++) {
				System.out.println(user.get(i).getName()+"--"+user.get(i).getAge());
			}
	        System.out.println("结束时间："+(new Date().getTime()-time1));
	        
	        
	        } catch (Exception e) {
	            //e.printStackTrace();
	        	System.out.println("数据库连接失败");
	            session.rollback();
	        }
	}

}
