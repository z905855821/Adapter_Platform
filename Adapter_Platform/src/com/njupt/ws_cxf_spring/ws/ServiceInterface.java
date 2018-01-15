package com.njupt.ws_cxf_spring.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface ServiceInterface {

	/**
	 * 验证用户是否合法，合法则给句柄
	 * @param username
	 * @param passwords
	 * @return
	 */
	@WebMethod
	String getPermission(String username,String passwords)throws Exception;
	

	/**
	 * 执行注销操作
	 * @param handle
	 * @return
	 * @throws Exception
	 */
	@WebMethod
	String logout(String handle) throws Exception;
	
	/**
	 * 在信息卡号表中将卡号信息与插座绑定
	 * @param handle
	 * @param name
	 * @param seatid
	 * @param serialnumber
	 * @return
	 */
	@WebMethod
	String insertCardInfo(String handle,String name, String seatid,String serialnumber);
	/**
	 * 学生信息注册
	 * @param handle
	 * @param username
	 * @param password
	 * @param telnum
	 * @param seatid
	 * @return
	 */
	@WebMethod
	String studentRegister(String handle,String username, String password,String telnum,String seatid);
	/**
	 * 学生登录接口
	 * @param handle
	 * @param username
	 * @param password
	 * @return
	 */
	@WebMethod
	String studentLogin(String handle,String username,String password);
	/**
	 * 根据名字查找所有的出勤状态
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getAllHisStateInfoByName(String handle,String username);
	/**
	 * 根据名字查找当天的出勤记录
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getCurStateInfoByName(String handle,String username);
	/**
	 * 根据姓名查找昨天的出勤记录
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getYesterdayStateInfoByName(String handle,String username);
	/**
	 * 获取学生的最新一周的出勤记录
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getLastWeekStateInfoByName(String handle,String username);
	/**
	 * 获取学生的最近一月的出勤记录
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getLastMounthStateInfoByName(String handle,String username);
	
	/**
	 * 获取学生的最近三个月出勤记录
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getLastThreeMounthStateInfoByName(String handle,String username);
	/**
	 * 获取当天的用电情况
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getCurPowerByName(String handle,String username);
	/**
	 * 获取昨天的用电情况
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getYesterdayPowerByName(String handle,String username);
	/**
	 * 获取上周的用电情况
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getLastWeekPowerByName(String handle,String username);
	/**
	 * 获取本月的用电情况
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getLastMounthPowerByName(String handle,String username);
	/**
	 * 获取近三月的用电情况
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getLastThreeMounthPowerByName(String handle,String username);
	/**
	 * 获取用户的当前余额
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String selectBalanceByName(String handle,String username);
	/**
	 * 获取学生的当天消费记录
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getCurConsumeByName(String handle,String username);
	/**
	 * 获取用户最近一周的消费记录
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getLastWeekConsumeByName(String handle,String username);
	/**
	 * 获取最忌一个月的消费记录
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getLastMounthConsumeByName(String handle,String username);
	/**
	 * 获取最近三个月的消费记录
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String getLastThreeMounthConsumeByName(String handle,String username);
	
	/**
	 * 教师注册
	 * @param handle
	 * @param username
	 * @param password
	 * @param telnum
	 * @return
	 */
	@WebMethod
	String teacherRegister(String handle,String username, String password,String telnum);
	/**
	 * 教师登录
	 * @param handle
	 * @param username
	 * @param password
	 * @return
	 */
	@WebMethod
	String teacherLogin(String handle,String username,String password);
	
	/**
	 * 远程控制断电
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String remotecontrol(String handle,String username);
	/**
	 * 充值
	 * @param handle
	 * @param username
	 * @param value
	 * @return
	 */
	@WebMethod
	String charge(String handle,String username,String value);
	/**
	 * 获取学生实时状态
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String selectRealStateByName(String handle,String username);
	/**
	 * 获取所有学生姓名信息
	 * @param handle
	 * @return
	 */
	@WebMethod
	String selectStudentName(String handle);
	/**
	 * 根据姓名查找手机号
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String selectPhoneByName(String handle,String username);
	
	/**
	 * 插入学生项目信息
	 * @param name
	 * @param xuehao
	 * @param projectname
	 * @param teachername
	 * @param durality
	 * @return
	 */
	@WebMethod
	String insertStuProj(String handle,String name,String xuehao,String projectname,String teachername,String durality);
	
	/**
	 * 根据用户名查找项目信息
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String selectStuProjectInfo(String handle,String username);
	
	/**
	 * 获取所有插座的实时状态
	 * @param handle
	 * @return
	 */
	@WebMethod
	String getAllStateInfo(String handle);
	
	/**
	 * 获取出勤状态学生信息
	 * @param handle
	 * @return
	 */
	@WebMethod
	String getAllOpenStateInfo(String handle);
	
	/**
	 * 根据座位号查找学生姓名
	 * @param seatid
	 * @return
	 */
	@WebMethod
	String selectnamedBySeatid(String handle,String seatid);
	
	/**
	 * 查找最近一周出勤次数
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String selectLastWeekCount(String handle,String username);
	
	/**
	 * 查询最近一个月的出勤次数
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String selectLastMounthCount(String handle,String username);
	
	/**
	 * 查询最近三个月的出勤次数
	 * @param handle
	 * @param username
	 * @return
	 */
	@WebMethod
	String selectLastThreeMounthCount(String handle,String username);
}
