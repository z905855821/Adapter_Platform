package com.njupt.ws_cxf_spring.ws;

import javax.jws.WebService;

import com.njupt.ws_cxf_spring.ws.dao.Dao;
import com.njupt.ws_cxf_spring.ws.tools.Tools;

@WebService
public class ServiceInterfaceImpl implements ServiceInterface {

	Dao db;

	public ServiceInterfaceImpl()throws Exception {
		db = new Dao();
	}

	/*
	 * 判断用户是否合法
	 * 
	 */
	public String getPermission(String username, String passwords)
			throws Exception {
		System.out.println("********************************begin*******************************");
		int a = db.islegalUser(username, passwords);
		System.out.println("用户id:"+a);
		if (a > 0) {
			String b;
			b = Tools.GetRandomNumber();
			System.out.println("生成的句柄："+b);
			db.inserthandle(b, a);
			System.out.println("句柄插入成功");
			System.out.println("*********************************end*******************************");
			return b;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";			
		}
	}
	
	/*
	 * 执行注销操作 (non-Javadoc)
	 * 
	 */
	@Override
	public String logout(String handle) throws Exception {
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			db.updatehandle(handle);
			System.out.println("*********************************end*******************************");
			return "成功注销！";
		} else{
				System.out.println("*********************************end*******************************");
				return "未能成功注销！";
			}
	}

	@Override
	public String insertCardInfo(String handle, String name, String seatid, String serialnumber) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			int num=db.insertCardInfo(name, seatid, serialnumber);
			System.out.println("*********************************end*******************************");
			if (num>0) {
				return "绑定成功！";
			}else {
				return "绑定失败！";
			}
			
		} else{
				System.out.println("*********************************end*******************************");
				return "你输入的用户不合法";
		}
	}

	@Override
	public String studentRegister(String handle, String username, String password, String telnum, String seatid) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.studentRegister(username, password, telnum, seatid);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
				System.out.println("*********************************end*******************************");
				return "你输入的用户不合法";
		}
	}

	@Override
	public String studentLogin(String handle, String username, String password) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.studentLogin(username, password);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getAllHisStateInfoByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getAllHisStateInfoByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getCurStateInfoByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getCurStateInfoByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getYesterdayStateInfoByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getYesterdayStateInfoByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getLastWeekStateInfoByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getLastWeekStateInfoByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getLastMounthStateInfoByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getLastMounthStateInfoByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getLastThreeMounthStateInfoByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getLastThreeMounthStateInfoByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getCurPowerByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getCurPowerByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getYesterdayPowerByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getYesterdayPowerByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getLastWeekPowerByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getLastWeekPowerByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getLastMounthPowerByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getLastMounthPowerByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getLastThreeMounthPowerByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getLastThreeMounthPowerByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String selectBalanceByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.selectBalanceByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getCurConsumeByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getCurConsumeByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getLastWeekConsumeByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getLastWeekConsumeByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getLastMounthConsumeByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getLastMounthConsumeByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getLastThreeMounthConsumeByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getLastThreeMounthConsumeByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String teacherRegister(String handle, String username, String password, String telnum) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.teacherRegister(username, password, telnum);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String teacherLogin(String handle, String username, String password) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.teacherLogin(username, password);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String remotecontrol(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.remotecontrol(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String charge(String handle, String username, String value) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.charge(username, value);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String selectRealStateByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.selectRealStateByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String selectStudentName(String handle) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.selectStudentName();
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String selectPhoneByName(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.selectPhoneByName(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String insertStuProj(String handle, String name, String xuehao, String projectname, String teachername,
			String durality) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.insertStuProj(name, xuehao, projectname, teachername, durality);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String selectStuProjectInfo(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.selectStuProjectInfo(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getAllStateInfo(String handle) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getAllStateInfo();
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String getAllOpenStateInfo(String handle) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.getAllOpenStateInfo();
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String selectnamedBySeatid(String handle, String seatid) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.selectnamedBySeatid(seatid);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String selectLastWeekCount(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.selectLastWeekCount(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String selectLastMounthCount(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.selectLastMounthCount(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}

	@Override
	public String selectLastThreeMounthCount(String handle, String username) {
		// TODO Auto-generated method stub
		System.out.println("********************************begin*******************************");
		if (db.islegalhandle(handle)) {
			String result=db.selectLastThreeMounthCount(username);
			System.out.println("*********************************end*******************************");
			return result;
		} else{
			System.out.println("*********************************end*******************************");
			return "你输入的用户不合法";
		}
	}
	
}
