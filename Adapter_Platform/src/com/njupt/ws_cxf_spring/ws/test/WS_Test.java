package com.njupt.ws_cxf_spring.ws.test;

import com.njupt.ws_cxf_spring.ws.dao.Dao;

public class WS_Test {
    static	Dao dao=new Dao();

	public static int insertCardInfoTest(String name, String seatid,String serialnumber) {
		return dao.insertCardInfo(name, seatid, serialnumber);
	}
	public static String studentRegisterTest(String username, String password,String telnum,String seatid) {
		return dao.studentRegister(username, password, telnum, seatid);
	}
	public static String studentLoginTest(String username,String password) {
		return dao.studentLogin(username, password);
	}
	public static String selectseatIdByNameTest(String username) {
		return dao.selectseatIdByName(username);
	}
	public static String getAllHisStateInfoByNameTest(String username) {
		return dao.getAllHisStateInfoByName(username);
	}
	public static String getCurStateInfoByNameTest(String username) {
		return dao.getCurStateInfoByName(username);
	}
	public static String getYesterdayStateInfoByNameTest(String username){
		return dao.getYesterdayStateInfoByName(username);
	}
	public static String getLastWeekStateInfoByNameTest(String username){
		return dao.getLastWeekStateInfoByName(username);
	}
	public static String getLastMounthStateInfoByNameTest(String username){
		return dao.getLastMounthStateInfoByName(username);
	}
	public static String getLastThreeMounthStateInfoByNameTest(String username){
		return dao.getLastThreeMounthStateInfoByName(username);
	}
	public static String getCurPowerByNameTest(String username){
		return dao.getCurPowerByName(username);
	}
	public static String getYesterdayPowerByNameTest(String username){
		return dao.getYesterdayPowerByName(username);
	}
	public static String getLastWeekPowerByNameTest(String username){
		return dao.getLastWeekPowerByName(username);
	}
	public static String getLastMounthPowerByNameTest(String username){
		return dao.getLastMounthPowerByName(username);
	}
	public static String getLastThreeMounthPowerByNameTest(String username){
		return dao.getLastThreeMounthPowerByName(username);
	}
	public static String selectBalanceByNameTest(String username){
		return dao.selectBalanceByName(username);
	}
	public static String getCurConsumeByNameTest(String username){
		return dao.getCurConsumeByName(username);
	}
	public static String getLastWeekConsumeByNameTest(String username){
		return dao.getLastWeekConsumeByName(username);
	}
	public static String getLastMounthConsumeByNameTest(String username){
		return dao.getLastMounthConsumeByName(username);
	}
	public static String getLastThreeMounthConsumeByNameTest(String username){
		return dao.getLastThreeMounthConsumeByName(username);
	}
	public static String selectRealStateByNameTest(String username){
		return dao.selectRealStateByName(username);
	}
	public static String remotecontrolTest(String username){
		return dao.remotecontrol(username);
	}
	public static String chargeTest(String username,String value){
		return dao.charge(username, value);
	}
	
	public static String teacherRegisterTest(String username, String password,String telnum) {
		return dao.teacherRegister(username, password, telnum);
	}
	public static String teacherLoginTest(String username, String password) {
		return dao.teacherLogin(username, password);
	}
	public static String selectStudentNameTest() {
		return dao.selectStudentName();
	}
	public static String selectPhoneByNameTest(String username) {
		return dao.selectPhoneByName(username);
	}
	public static String insertStuProjTest(String name,String xuehao,String projectname,String teachername,String durality) {
		return dao.insertStuProj(name, xuehao, projectname, teachername, durality);
	}
	public static String selectStuProjectInfoTest(String username) {
		return dao.selectStuProjectInfo(username);
	}
	public static String getAllStateInfoTest() {
		return dao.getAllStateInfo();
	}
	public static String getAllOpenStateInfoTest() {
		return dao.getAllOpenStateInfo();
	}
	public static String selectnamedBySeatidTest(String seatid) {
		return dao.selectnamedBySeatid(seatid);
	}
	public static String selectLastWeekCountTest(String username) {
		return dao.selectLastWeekCount(username);
	}
	public static String selectLastMounthCountTest(String username) {
		return dao.selectLastMounthCount(username);
	}
	public static String selectLastThreeMounthCountTest(String username) {
		return dao.selectLastThreeMounthCount(username);
	}
	public static String queryCardInfoCountTest(String username) {
		return dao.queryCardInfoCount(username);
	}
	public static String  addOrupdateSeatCardInfoTest(String username,String seatid,String serialnumber) {
		return dao.addOrupdateSeatCardInfo(username, seatid, serialnumber);
	}
	public static String queryStuProjInfoCountTest(String username) {
		return dao.queryStuProjInfoCount(username);
	}
	public static String addOrupdateStuProjInfo(String username,String xuehao,String projectname,String teachername,String durality) {
		return dao.addOrupdateStuProjInfo(username, xuehao, projectname, teachername, durality);
	}
	public static void main(String[] args) {
		//System.out.println(insertCardInfoTest("������", "74", "123456789"));
		//System.out.println(studentRegisterTest("������", "123456", "13770525127", "74"));
		//System.out.println(studentLoginTest("������", "123456"));
		//System.out.println(selectseatIdByNameTest("������"));
		//System.out.println(getAllHisStateInfoByNameTest("������"));
		//System.out.println(getCurStateInfoByNameTest("������"));
		//System.out.println(getYesterdayStateInfoByNameTest("������"));
	    //getLastWeekStateInfoByNameTest("������");
		//getLastMounthStateInfoByNameTest("������");
		//getLastThreeMounthStateInfoByNameTest("������");
		//getCurPowerByNameTest("������");
		//getYesterdayPowerByNameTest("������");
//		getLastWeekPowerByNameTest("������");
//		getLastMounthPowerByNameTest("������");
//		getLastThreeMounthPowerByNameTest("������");
		//selectBalanceByNameTest("������");
		//getCurConsumeByNameTest("������");
		//getLastWeekConsumeByNameTest("������");
		//getLastMounthConsumeByNameTest("������");
		//getLastThreeMounthConsumeByNameTest("������");
		//selectRealStateByNameTest("������");
		//remotecontrolTest("������");
		//chargeTest("������", "1000");
		//teacherRegisterTest("������", "123456", "13770525127");
		//teacherLoginTest("������", "123456");
		//selectStudentNameTest();
		//selectPhoneByNameTest("������");
		//insertStuProjTest("������", "1015010128", "ͣ������λ��������ϵͳ", "������", "2016.06-2017.06");
		//selectStuProjectInfoTest("������");
		//getAllStateInfoTest();
		getAllOpenStateInfoTest();
		//selectnamedBySeatidTest("74");
		//selectLastWeekCountTest("������");
		//selectLastMounthCountTest("������");
		//selectLastThreeMounthCountTest("������");
		//queryCardInfoCountTest("74");
		//addOrupdateSeatCardInfoTest("������", "74", "123456789");
		//queryStuProjInfoCountTest("������");
//		addOrupdateStuProjInfo("������", "1015010128", "ͣ������λ��������ϵͳ", "������", "2016.06-2017.06");
	}
}
