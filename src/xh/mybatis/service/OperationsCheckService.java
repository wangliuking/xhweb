package xh.mybatis.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import xh.mybatis.bean.CheckMoneyBean;
import xh.mybatis.bean.CheckMonthBsBreakBean;
import xh.mybatis.bean.CheckRoomEquBean;
import xh.mybatis.bean.MoneyBean;
import xh.mybatis.bean.OperationsCheckBean;
import xh.mybatis.bean.OperationsCheckDetailBean;
import xh.mybatis.bean.OperationsCheckScoreBean;
import xh.mybatis.bean.ScoreBean;
import xh.mybatis.bean.WorkContactBean;
import xh.mybatis.mapper.OperationsCheckMapper;
import xh.mybatis.mapper.WorkContactMapper;
import xh.mybatis.tools.MoreDbTools;

public class OperationsCheckService {

	public static List<OperationsCheckBean> dataList(Map<String, Object> map) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<OperationsCheckBean> list = new ArrayList<OperationsCheckBean>();
		try {
			list = mapper.dataList(map);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					OperationsCheckBean bean=new OperationsCheckBean();
					bean=list.get(i);
					List<Map<String,Object>> l=new ArrayList<Map<String,Object>>();
					l=searchFile(bean.getApplyId());
					bean.setFiles(l);
					list.set(i, bean);
					
				}
			}
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<OperationsCheckScoreBean> searchScore(Map<String, Object> map) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<OperationsCheckScoreBean> list = new ArrayList<OperationsCheckScoreBean>();
		try {
			list = mapper.searchScore(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<OperationsCheckScoreBean> show_score_detail(String time,int period) {
		SqlSession session = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session.getMapper(OperationsCheckMapper.class);
		List<OperationsCheckScoreBean> list = new ArrayList<OperationsCheckScoreBean>();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("time", time);
		map.put("period", period);
		try {
			list = mapper.show_score_detail(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static int  addFile(List<Map<String,Object>> list) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count=0;
		try {
			count = mapper.addFile(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static List<Map<String,Object>> readFileTree(String path){
		File dir = new File(path);
		File[] files=dir.listFiles();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(files!=null){
			for(int i=0;i<files.length;i++){
				Map<String,Object> map=new HashMap<String, Object>();
				if(files[i].isDirectory()){
					map.put("isDir", true);
					map.put("file", files[i].getName());				
					map.put("children", readFileTree(files[i].getPath()));
				}else{
					map.put("isDir", false);
					map.put("file", files[i].getName());
					map.put("path", files[i].getPath());
				}
				list.add(map);
			}
		}else{
			System.out.println("当前目录为空");
		}
		return list;
		
	}
	public static int  sealFile(int id) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count=0;
		try {
			count = mapper.sealFile(id);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public static int count(Map<String, Object> map) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.count(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public static int add(OperationsCheckBean checkBean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		// detailBean.setApplyId(checkBean.getApplyId());
		int count = 0;
		int r = 0;
		try {
			count = mapper.add(checkBean);
			session.commit();
			/*
			 * if(count>=1){ if(detailExists(detailBean.getTime())>0){
			 * r=updateDetail(detailBean); }else{ r=addDetail(detailBean); }
			 * if(r==0){ session.rollback(); } }else{ session.rollback(); }
			 */
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static int addScore(ScoreBean bean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		System.out.println(bean.toString());
		List<OperationsCheckScoreBean> list=new ArrayList<OperationsCheckScoreBean>();
		OperationsCheckScoreBean score1=new OperationsCheckScoreBean();
		score1.setPeriod(bean.getPeriod());
		score1.setCheck_month(bean.getTime());
		score1.setCheck_tag("a1");
		score1.setCheck_note(bean.getN_a1());
		if(!bean.getS_a1().equals("")){
			score1.setScore(Float.parseFloat(bean.getS_a1()));
		}else{
			score1.setScore(0);
		}
		list.add(score1);
		OperationsCheckScoreBean score2=new OperationsCheckScoreBean();
		score2.setCheck_month(bean.getTime());
		score2.setCheck_tag("b1");
		score2.setCheck_note(bean.getN_b1());
		score2.setPeriod(bean.getPeriod());
		if(!bean.getS_b1().equals("")){
			score2.setScore(Float.parseFloat(bean.getS_b1()));
		}else{
			score2.setScore(0);
		}
		list.add(score2);
		OperationsCheckScoreBean score3=new OperationsCheckScoreBean();
		score3.setCheck_month(bean.getTime());
		score3.setCheck_tag("b2");
		score3.setCheck_note(bean.getN_b2());
		score3.setPeriod(bean.getPeriod());
		if(!bean.getS_b2().equals("")){
			score3.setScore(Float.parseFloat(bean.getS_b2()));
		}else{
			score3.setScore(0);
		}
		list.add(score3);
		OperationsCheckScoreBean score4=new OperationsCheckScoreBean();
		score4.setCheck_month(bean.getTime());
		score4.setCheck_tag("b3");
		score4.setCheck_note(bean.getN_b3());
		score4.setPeriod(bean.getPeriod());
		if(!bean.getS_b3().equals("")){
			score4.setScore(Float.parseFloat(bean.getS_b3()));
		}else{
			score4.setScore(0);
		}
		list.add(score4);
		OperationsCheckScoreBean score5=new OperationsCheckScoreBean();
		score5.setCheck_month(bean.getTime());
		score5.setCheck_tag("b4");
		score5.setCheck_note(bean.getN_b4());
		score5.setPeriod(bean.getPeriod());
		if(!bean.getS_b4().equals("")){
			score5.setScore(Float.parseFloat(bean.getS_b4()));
		}else{
			score5.setScore(0);
		}
		list.add(score5);
		OperationsCheckScoreBean score6=new OperationsCheckScoreBean();
		score6.setCheck_month(bean.getTime());
		score6.setCheck_tag("c1");
		score6.setCheck_note(bean.getN_c1());
		score6.setPeriod(bean.getPeriod());
		if(!bean.getS_c1().equals("")){
			score6.setScore(Float.parseFloat(bean.getS_c1()));
		}else{
			score6.setScore(0);
		}
		list.add(score6);
		
		OperationsCheckScoreBean score7=new OperationsCheckScoreBean();
		score7.setCheck_month(bean.getTime());
		score7.setCheck_tag("c2");
		score7.setCheck_note(bean.getN_c2());
		score7.setPeriod(bean.getPeriod());
		if(!bean.getS_c2().equals("")){
			score7.setScore(Float.parseFloat(bean.getS_c2()));
		}else{
			score7.setScore(0);
		}
		list.add(score7);
		OperationsCheckScoreBean score8=new OperationsCheckScoreBean();
		score8.setCheck_month(bean.getTime());
		score8.setCheck_tag("d1");
		score8.setCheck_note(bean.getN_d1());
		score8.setPeriod(bean.getPeriod());
		if(!bean.getS_d1().equals("")){
			score8.setScore(Float.parseFloat(bean.getS_d1()));
		}else{
			score8.setScore(0);
		}
		list.add(score8);
		OperationsCheckScoreBean score9=new OperationsCheckScoreBean();
		score9.setCheck_month(bean.getTime());
		score9.setCheck_tag("d2");
		score9.setCheck_note(bean.getN_d2());
		score9.setPeriod(bean.getPeriod());
		if(!bean.getS_d2().equals("")){
			score9.setScore(Float.parseFloat(bean.getS_d2()));
		}else{
			score9.setScore(0);
		}
		list.add(score9);
		OperationsCheckScoreBean score10=new OperationsCheckScoreBean();
		score10.setCheck_month(bean.getTime());
		score10.setCheck_tag("e1");
		score10.setCheck_note(bean.getN_e1());
		score10.setPeriod(bean.getPeriod());
		if(!bean.getS_e1().equals("")){
			score10.setScore(Float.parseFloat(bean.getS_e1()));
		}else{
			score10.setScore(0);
		}
		list.add(score10);
		OperationsCheckScoreBean score11=new OperationsCheckScoreBean();
		score11.setCheck_month(bean.getTime());
		score11.setCheck_tag("f1");
		score11.setCheck_note(bean.getN_f1());
		score11.setPeriod(bean.getPeriod());
		if(!bean.getS_f1().equals("")){
			score11.setScore(Float.parseFloat(bean.getS_f1()));
		}else{
			score11.setScore(0);
		}
		list.add(score11);
		OperationsCheckScoreBean score12=new OperationsCheckScoreBean();
		score12.setCheck_month(bean.getTime());
		score12.setCheck_tag("f2");
		score12.setCheck_note(bean.getN_f2());
		score12.setPeriod(bean.getPeriod());
		if(!bean.getS_f2().equals("")){
			score12.setScore(Float.parseFloat(bean.getS_f2()));
		}else{
			score12.setScore(0);
		}
		list.add(score12);
		OperationsCheckScoreBean score13=new OperationsCheckScoreBean();
		score13.setCheck_month(bean.getTime());
		score13.setCheck_tag("g1");
		score13.setCheck_note(bean.getN_g1());
		score13.setPeriod(bean.getPeriod());
		if(!bean.getS_g1().equals("")){
			score13.setScore(Float.parseFloat(bean.getS_g1()));
		}else{
			score13.setScore(0);
		}
		list.add(score13);
		
		OperationsCheckScoreBean score14=new OperationsCheckScoreBean();
		score14.setCheck_month(bean.getTime());
		score14.setCheck_tag("h1");
		score14.setCheck_note(bean.getN_h1());
		score14.setPeriod(bean.getPeriod());
		if(!bean.getS_h1().equals("")){
			score14.setScore(Float.parseFloat(bean.getS_h1()));
		}else{
			score14.setScore(0);
		}
		list.add(score14);
		/*OperationsCheckScoreBean score14=new OperationsCheckScoreBean();
		score14.setCheck_month(bean.getTime());
		score14.setCheck_tag("sum");
		score14.setCheck_note(bean.getN_sum());
		if(!bean.getS_sum().equals("")){
			score14.setScore(Float.parseFloat(bean.getS_sum()));
		}else{
			score14.setScore(0);
		}
		list.add(score14);	*/
		int count = 0;
		try {
			count = mapper.addScore(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public static List<CheckMoneyBean> searchDetail(Map<String, Object> map) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<CheckMoneyBean> list= new ArrayList<CheckMoneyBean>();
		try {
			list = mapper.searchDetail(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<CheckMoneyBean> show_money_detail(String time,int period) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<CheckMoneyBean> list= new ArrayList<CheckMoneyBean>();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("time", time);
		map.put("period", period);
		try {
			list = mapper.show_money_detail(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public static int detailExists(String time) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.detailExists(time);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public static int addDetail(MoneyBean bean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<CheckMoneyBean> list=new ArrayList<CheckMoneyBean>();
		
		System.out.println(bean.toString());
		CheckMoneyBean score1=new CheckMoneyBean();
		score1.setCheck_date(bean.getTime());
		score1.setCheck_tag("a1");
		score1.setCheck_note(bean.getN_a1());
		score1.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_a1().equals("")){
			score1.setMoney(Float.parseFloat(bean.getM_a1()));
		}else{
			score1.setMoney(0);
		}
		list.add(score1);
		
		CheckMoneyBean score2=new CheckMoneyBean();
		score2.setCheck_date(bean.getTime());
		score2.setCheck_tag("a2");
		score2.setCheck_note(bean.getN_a2());
		score2.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_a2().equals("")){
			score2.setMoney(Float.parseFloat(bean.getM_a2()));
		}else{
			score2.setMoney(0);
		}
		list.add(score2);
		
		CheckMoneyBean score3=new CheckMoneyBean();
		score3.setCheck_date(bean.getTime());
		score3.setCheck_tag("a3");
		score3.setCheck_note(bean.getN_a3());
		score3.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_a3().equals("")){
			score3.setMoney(Float.parseFloat(bean.getM_a3()));
		}else{
			score3.setMoney(0);
		}
		list.add(score3);
		
		CheckMoneyBean score4=new CheckMoneyBean();
		score4.setCheck_date(bean.getTime());
		score4.setCheck_tag("a4");
		score4.setCheck_note(bean.getN_a4());
		score4.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_a4().equals("")){
			score4.setMoney(Float.parseFloat(bean.getM_a4()));
		}else{
			score4.setMoney(0);
		}
		list.add(score4);
		
		CheckMoneyBean score5=new CheckMoneyBean();
		score5.setCheck_date(bean.getTime());
		score5.setCheck_tag("b1");
		score5.setCheck_note(bean.getN_b1());
		score5.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_b1().equals("")){
			score5.setMoney(Float.parseFloat(bean.getM_b1()));
		}else{
			score5.setMoney(0);
		}
		list.add(score5);
		
		CheckMoneyBean score6=new CheckMoneyBean();
		score6.setCheck_date(bean.getTime());
		score6.setCheck_tag("b2");
		score6.setCheck_note(bean.getN_b2());
		score6.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_b2().equals("")){
			score6.setMoney(Float.parseFloat(bean.getM_b2()));
		}else{
			score6.setMoney(0);
		}
		list.add(score6);
		
		CheckMoneyBean score7=new CheckMoneyBean();
		score7.setCheck_date(bean.getTime());
		score7.setCheck_tag("c1");
		score7.setCheck_note(bean.getN_c1());
		score7.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_c1().equals("")){
			score7.setMoney(Float.parseFloat(bean.getM_c1()));
		}else{
			score7.setMoney(0);
		}
		list.add(score7);
		
		CheckMoneyBean score8=new CheckMoneyBean();
		score8.setCheck_date(bean.getTime());
		score8.setCheck_tag("c2");
		score8.setCheck_note(bean.getN_c2());
		score8.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_c2().equals("")){
			score8.setMoney(Float.parseFloat(bean.getM_c2()));
		}else{
			score8.setMoney(0);
		}
		list.add(score8);
		
		CheckMoneyBean score9=new CheckMoneyBean();
		score9.setCheck_date(bean.getTime());
		score9.setCheck_tag("c3");
		score9.setCheck_note(bean.getN_c3());
		score9.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_c3().equals("")){
			score9.setMoney(Float.parseFloat(bean.getM_c3()));
		}else{
			score9.setMoney(0);
		}
		list.add(score9);
		
		CheckMoneyBean score10=new CheckMoneyBean();
		score10.setCheck_date(bean.getTime());
		score10.setCheck_tag("d1");
		score10.setCheck_note(bean.getN_d1());
		score10.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_d1().equals("")){
			score10.setMoney(Float.parseFloat(bean.getM_d1()));
		}else{
			score10.setMoney(0);
		}
		list.add(score10);
		
		CheckMoneyBean score11=new CheckMoneyBean();
		score11.setCheck_date(bean.getTime());
		score11.setCheck_tag("e1");
		score11.setCheck_note(bean.getN_e1());
		score11.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_e1().equals("")){
			score11.setMoney(Float.parseFloat(bean.getM_e1()));
		}else{
			score11.setMoney(0);
		}
		list.add(score11);
		
		CheckMoneyBean score12=new CheckMoneyBean();
		score12.setCheck_date(bean.getTime());
		score12.setCheck_tag("f1");
		score12.setCheck_note(bean.getN_f1());
		score12.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_f1().equals("")){
			score12.setMoney(Float.parseFloat(bean.getM_f1()));
		}else{
			score12.setMoney(0);
		}
		list.add(score12);
		
		CheckMoneyBean score13=new CheckMoneyBean();
		score13.setCheck_date(bean.getTime());
		score13.setCheck_tag("g1");
		score13.setCheck_note(bean.getN_g1());
		score13.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_g1().equals("")){
			score13.setMoney(Float.parseFloat(bean.getM_g1()));
		}else{
			score13.setMoney(0);
		}
		list.add(score13);
		
		CheckMoneyBean score14=new CheckMoneyBean();
		score14.setCheck_date(bean.getTime());
		score14.setCheck_tag("h1");
		score14.setCheck_note(bean.getN_h1());
		score14.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_h1().equals("")){
			score14.setMoney(Float.parseFloat(bean.getM_h1()));
		}else{
			score14.setMoney(0);
		}
		list.add(score14);
		
		CheckMoneyBean score15=new CheckMoneyBean();
		score15.setCheck_date(bean.getTime());
		score15.setCheck_tag("i1");
		score15.setCheck_note(bean.getN_i1());
		score15.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_i1().equals("")){
			score15.setMoney(Float.parseFloat(bean.getM_i1()));
		}else{
			score15.setMoney(0);
		}
		list.add(score15);
		
		CheckMoneyBean score16=new CheckMoneyBean();
		score16.setCheck_date(bean.getTime());
		score16.setCheck_tag("j1");
		score16.setCheck_note(bean.getN_j1());
		score16.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_j1().equals("")){
			score16.setMoney(Float.parseFloat(bean.getM_j1()));
		}else{
			score16.setMoney(0);
		}
		list.add(score16);
		
		CheckMoneyBean score17=new CheckMoneyBean();
		score17.setCheck_date(bean.getTime());
		score17.setCheck_tag("k1");
		score17.setCheck_note(bean.getN_k1());
		score17.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_k1().equals("")){
			score17.setMoney(Float.parseFloat(bean.getM_k1()));
		}else{
			score17.setMoney(0);
		}
		list.add(score17);
		
		CheckMoneyBean score18=new CheckMoneyBean();
		score18.setCheck_date(bean.getTime());
		score18.setCheck_tag("l1");
		score18.setCheck_note(bean.getN_l1());
		score18.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_l1().equals("")){
			score18.setMoney(Float.parseFloat(bean.getM_l1()));
		}else{
			score18.setMoney(0);
		}
		list.add(score18);
		
		CheckMoneyBean score19=new CheckMoneyBean();
		score19.setCheck_date(bean.getTime());
		score19.setCheck_tag("m1");
		score19.setCheck_note(bean.getN_m1());
		score19.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_m1().equals("")){
			score19.setMoney(Float.parseFloat(bean.getM_m1()));
		}else{
			score19.setMoney(0);
		}
		list.add(score19);
		
		CheckMoneyBean score20=new CheckMoneyBean();
		score20.setCheck_date(bean.getTime());
		score20.setCheck_tag("n1");
		score20.setCheck_note(bean.getN_n1());
		score20.setPeriod(String.valueOf(bean.getPeriod()));
		if(!bean.getM_n1().equals("")){
			score20.setMoney(Float.parseFloat(bean.getM_n1()));
		}else{
			score20.setMoney(0);
		}
		list.add(score20);
		int count = 0;
		try {
			count = mapper.addDetail(list);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public static int updateDetail(OperationsCheckDetailBean bean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.updateDetail(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public static int check2(OperationsCheckBean checkBean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.check2(checkBean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public static int check3(OperationsCheckBean checkBean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		//detailBean.setApplyId(checkBean.getApplyId());
		int count = 0;
		int r = 0;
		try {
			count = mapper.check3(checkBean);
			session.commit();
		
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	public static int check4(OperationsCheckBean checkBean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.check4(checkBean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int check5(OperationsCheckBean checkBean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.check5(checkBean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 机房配套设备检查
	 * */
	public static List<Map<String, Object>> check_room_equ() {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.check_room_equ();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	/*<!-- 考核运维人员是否达到20人 -->*/
	public static int check_phone_book() {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count=0;
		try {
			count=mapper.check_phone_book();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/*<!-- 考核运办公场所 ,考核仪器仪表 ,考核运维车辆不足3辆 -->*/
	public static Map<String,Object> check_officeaddress(){
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		Map<String, Object> map=new HashMap<String, Object>();
		try {
			map=mapper.check_officeaddress();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	/*考核备品备件完好率低于80%*/
	public static List<Map<String, Object>> check_attachment() {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.check_attachment();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	public static int insert_check_month_detail(CheckRoomEquBean bean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.insert_check_month_detail(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	
	public static int insert_check_month_money_detail(CheckMoneyBean bean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.insert_check_month_money_detail(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static int del_score(String time) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.del_score(time);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int del_money(String time) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.del_money(time);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/*	<!-- 基站故障 -->*/
	public static List<Map<String,Object>> bs_error(String time)  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.bs_error(time);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/*	<!-- 基站故障 -->*/
	public static List<Map<String,Object>> bs_error_money(String time)  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.bs_error_money(time);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<Map<String,Object>> error_money_total(String time)  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.error_money_total(time);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	public static List<Map<String,Object>> searchFile(String applyId) {
		SqlSession sqlSession = MoreDbTools.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = sqlSession.getMapper(OperationsCheckMapper.class);
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> list2= new ArrayList<Map<String,Object>>();
		String[] files={
				"本月计划维护作业完成情况",
				"下月计划维护作业",
				"系统运行维护服务月报",
				"基站信息表",
				"运维资源配置表",
				"运维人员通讯录",
				"运维故障统计",
				/*"故障核减申请书",*/
				/*"应急通信保障报告",*/
				"故障处理报告",
				"备品备件表",
				"定期维护报告-交换中心月维护",
				"定期维护报告-基站月维护",
				"巡检汇总表",
				/*"基站月度巡检表",*/
				"年度健康检查报告"};
		
				
				
				
				/*"'运维服务团队通讯录','运维资源配置表',"
				+ "'本月计划维护作业完成情况','下月计划维护作业','系统运行维护服务月报',"
				+ "'基站信息表','运维故障统计','通信保障报告','备品备件表','定期维护报告-交换中心月维护',"
				+ "'定期维护报告-基站月维护','系统日常维护表','巡检记录汇总表','话务统计'";*/
		try {
			list=mapper.searchFile(applyId);
			for (String string : files) {
				Map<String,Object> xx=new HashMap<String, Object>();
				xx.put("fileName", string);
				xx.put("ishas", false);
				boolean is=false;
				for (Map<String,Object> map : list) {
					if(map.get("fileName").toString().contains(string)){
						is=true;
						map.put("ishas", true);
						xx=map;
					}
				}
				list2.add(xx);
			}
			
			
			
			sqlSession.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list2;
	}
	public static int update_file_info(Map<String,Object> map) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.update_file_info(map);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int up_score_money(OperationsCheckBean checkBean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.up_score_money(checkBean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int sure_score_money(OperationsCheckBean checkBean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.sure_score_money(checkBean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static int sealScoreMoneyComplete(OperationsCheckBean checkBean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.sealScoreMoneyComplete(checkBean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int signMeet(OperationsCheckBean checkBean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count = 0;
		try {
			count = mapper.signMeet(checkBean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	
	/*	<!-- (特别)重大故障 -->*/
	public static List<Map<String,Object>> tb_zd_fault(String time,int period)  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("time", time);
		map.put("period", period);
		try {
			list=mapper.tb_zd_fault(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	/*<!-- 考核一级基站 -->*/
	public static List<Map<String,Object>> check_onelevel_bs()  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.check_onelevel_bs();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/*<!-- 考核二级基站 -->*/
	public static List<Map<String,Object>> check_twolevel_bs()  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.check_twolevel_bs();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/*<!-- 考核三级基站 -->*/
	public static List<Map<String,Object>> check_threelevel_bs()  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.check_onelevel_bs();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/*	<!-- 考核特别重大故障 -->*/
	public static List<Map<String,Object>> check_onelevel_fault()   {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.check_onelevel_fault();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/*	<!-- 考核重大故障 -->*/
	public static List<Map<String,Object>> check_twolevel_fault()  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.check_twolevel_fault();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/*	<!-- 查询考核扣分明细-->*/
	public static List<Map<String,Object>> search_score_detail(Map<String,Object> map)  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.search_score_detail(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/*	<!-- 查询考核扣分明细数量-->*/
	public static int search_score_detail_count(Map<String,Object> map)  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count=0;
		try {
			count=mapper.search_score_detail_count(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	/*	<!-- 查询考核款分明细-->*/
	public static List<Map<String,Object>> search_money_detail(Map<String,Object> map)  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.search_money_detail(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/*	<!-- 查询考核扣款明细数量-->*/
	public static int search_money_detail_count(Map<String,Object> map)  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count=0;
		try {
			count=mapper.search_money_detail_count(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	public static int search_checkcut_count(Map<String,Object> map)  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count=0;
		try {
			count=mapper.search_checkcut_count(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	/*写入每月基站断站时长*/
	public static int insert_check_month_bs_break(CheckMonthBsBreakBean bean) {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.master);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		int count=0;
		try {
			count=mapper.insert_check_month_bs_break(bean);
			session.commit();
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	public static List<Map<String,Object>> search_year_bs_break(Map<String,Object> map)  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.search_year_bs_break(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<Map<String,Object>> search_month_bs_break(Map<String,Object> map)  {
		SqlSession session = MoreDbTools
				.getSession(MoreDbTools.DataSourceEnvironment.slave);
		OperationsCheckMapper mapper = session
				.getMapper(OperationsCheckMapper.class);
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		try {
			list=mapper.search_month_bs_break(map);
			session.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	

}
