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
		//System.out.println(insertCardInfoTest("³ÂÏşÑô", "74", "123456789"));
		//System.out.println(studentRegisterTest("³ÂÏşÑô", "123456", "13770525127", "74"));
		//System.out.println(studentLoginTest("³ÂÏşÑô", "123456"));
		//System.out.println(selectseatIdByNameTest("³ÂÏşÑô"));
		//System.out.println(getAllHisStateInfoByNameTest("³ÂÏşÑô"));
		//System.out.println(getCurStateInfoByNameTest("³ÂÏşÑô"));
		//System.out.println(getYesterdayStateInfoByNameTest("³ÂÏşÑô"));
	    //getLastWeekStateInfoByNameTest("³ÂÏşÑô");
		//getLastMounthStateInfoByNameTest("³ÂÏşÑô");
		//getLastThreeMounthStateInfoByNameTest("³ÂÏşÑô");
		//getCurPowerByNameTest("³ÂÏşÑô");
		//getYesterdayPowerByNameTest("³ÂÏşÑô");
//		getLastWeekPowerByNameTest("³ÂÏşÑô");
//		getLastMounthPowerByNameTest("³ÂÏşÑô");
//		getLastThreeMounthPowerByNameTest("³ÂÏşÑô");
		//selectBalanceByNameTest("³ÂÏşÑô");
		//getCurConsumeByNameTest("³ÂÏşÑô");
		//getLastWeekConsumeByNameTest("³ÂÏşÑô");
		//getLastMounthConsumeByNameTest("³ÂÏşÑô");
		//getLastThreeMounthConsumeByNameTest("³ÂÏşÑô");
		//selectRealStateByNameTest("³ÂÏşÑô");
		//remotecontrolTest("³ÂÏşÑô");
		//chargeTest("³ÂÏşÑô", "1000");
		//teacherRegisterTest("½­ÁèÔÆ", "123456", "13770525127");
		//teacherLoginTest("½­ÁèÔÆ", "123456");
		//selectStudentNameTest();
		//selectPhoneByNameTest("³ÂÏşÑô");
		//insertStuProjTest("³ÂÏşÑô", "1015010128", "Í£³µ³¡³µÎ»¼ì²âÓë¿ØÖÆÏµÍ³", "½­ÁèÔÆ", "2016.06-2017.06");
		//selectStuProjectInfoTest("³ÂÏşÑô");
		//getAllStateInfoTest();
		getAllOpenStateInfoTest();
		//selectnamedBySeatidTest("74");
		//selectLastWeekCountTest("³ÂÏşÑô");
		//selectLastMounthCountTest("³ÂÏşÑô");
		//selectLastThreeMounthCountTest("³ÂÏşÑô");
		//queryCardInfoCountTest("74");
		//addOrupdateSeatCardInfoTest("³ÂÏşÑô", "74", "123456789");
		//queryStuProjInfoCountTest("³ÂÏşÑô");
//		addOrupdateStuProjInfo("³ÂÏşÑô", "1015010128", "Í£³µ³¡³µÎ»¼ì²âÓë¿ØÖÆÏµÍ³", "½­ÁèÔÆ", "2016.06-2017.06");
	}
}
