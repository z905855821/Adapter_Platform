package com.njupt.ws_cxf_spring.ws.dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.njupt.ws_cxf_spring.ws.bean.Account;
import com.njupt.ws_cxf_spring.ws.bean.State;
import com.njupt.ws_cxf_spring.ws.tools.Tools;
import com.sun.org.apache.regexp.internal.recompile;
import net.sf.json.JSONArray;

public class Dao {
	
	private static final String url = "jdbc:oracle:thin:@10.10.22.99:1521:iotdb";
	private static final String user = "scott";
	private static final String userpass = "system";
	private static ComboPooledDataSource ds;
	private ResultSet rs;
	private Connection conn;
	private PreparedStatement pstmt;
	private static final String driver = "oracle.jdbc.driver.OracleDriver";
	
	/**
     * 初始化连接池代码块,全局静态代码块
     */
	static{
		initDB();
	}
	
	/**
	 * 初始化数据库操作，建立连接池,使用的是C3PO数据源
	 * 
	 */
	private static final void initDB() {
		ds = new ComboPooledDataSource();
		try {
			ds.setDriverClass(driver);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		ds.setJdbcUrl(url);
		ds.setUser(user);
		ds.setPassword(userpass);
		ds.setMaxPoolSize(100);
		ds.setInitialPoolSize(10);
		ds.setMinPoolSize(5);
		ds.setMaxStatements(300);
		ds.setMaxIdleTime(10);
		ds.setMaxConnectionAge(100);
		//连接的最大空闲时间，如果超过这个时间，某个数据库连接还没有被使用，则会断开掉这个连接,单位秒  
		//ds.setMaxIdleTime(10);
		//配置连接的生存时间，超过这个时间的连接将由连接池自动断开丢弃掉。当然正在使用的连接不会马上断开，而是等待它close再断开。
		//ds.setMaxConnectionAge(50);
	}

	/**
	 * 向表w_athority中插入句柄，句柄生成时间以及结束时间
	 * 
	 * @param handle
	 * @return
	 * @throws Exception
	 */
	public int inserthandle(String handle, int userid) {
		
		String sql = "insert into w_authority values(?,sysdate,sysdate+1/24,?,1)";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, handle);
			pstmt.setInt(2, userid);
			flag = pstmt.executeUpdate();
			return flag;
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return flag;
	}

	/**
	 * 判断用户是否合法
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @return
	 */
	public int islegalUser(String username, String password) {
		String sql = "select * from w_user where username=?";
		int c = 0;
		String b = Tools.getMD5Str(password);
		System.out.println("用户输入的密码："+b);
		String a = "";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				a = Tools.getMD5Str(rs.getString(3));
				System.out.println("用户注册在数据库中的密码："+a);
				if (a.equals(b)) {
					c = rs.getInt(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return c;
	}

	/**
	 * 判断句柄是否合法以及是否有效
	 * 
	 * @param handle
	 * @return
	 */
	public boolean islegalhandle(String handle) {
		String sql = "select * from w_authority where handle=?";
		try {
			conn = ds.getConnection();
			// ---------------------------------------------------
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, handle);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String a = rs.getString(3);

				String b = Tools.getCurrentTime();
				int c = Tools.timeCompare(b, a);
				if (c < 0) {
					return true;
				} else
					return false;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 更新句柄对应表的数据
	 * 
	 * @param handle
	 * @return
	 * @throws Exception
	 */
	public int updatehandle(String handle) {
		String sql = "update w_authority set endtime=sysdate,survivaltime=(sysdate-begintime)*24 where handle=?";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, handle);
			flag = pstmt.executeUpdate();
			return flag;
		} catch(Exception e){
			e.printStackTrace();
		}finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	/**
	 * 学生账户注册
	 * @param username
	 * @param password
	 * @param telnum
	 * @param seatid
	 * @return
	 */
	public String studentRegister(String username, String password,String telnum,String seatid) {
			String result="failed";
			String sql = "insert into W_SEAT_INFO values(SEATID_INCREASE.nextval,?,?,?,5000,?,'student',sysdate)";
			try {
				conn = ds.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, username);
				pstmt.setString(2, password);
				pstmt.setString(3, telnum);
				pstmt.setString(4, seatid);
				int flag = pstmt.executeUpdate();
				if(flag==1){
					result="success";
					System.out.println("studentRegister: "+result);
					return result;			
				}
			}catch(Exception e){			
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			System.out.println("studentRegister: "+result);
		    return result;
	}
	
	/**
	 * 教师注册
	 * @param username
	 * @param password
	 * @param telnum
	 * @return
	 */
	public String teacherRegister(String username, String password,String telnum) {
		String result="failed";
		String sql = "insert into W_SEAT_INFO values(SEATID_INCREASE.nextval,?,?,?,5000,?,'teacher',sysdate)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			pstmt.setString(3, telnum);
			pstmt.setString(4, "");
			int flag = pstmt.executeUpdate();
			if(flag==1){
				result="success";
				System.out.println("teacherRegister: "+result);
				return result;			
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("teacherRegister: "+result);
	    return result;
}
	
	/**
	 * 学生登录接口
	 * @param username
	 * @param password
	 * @return
	 */
	public String studentLogin(String username,String password) {
		String result="登录失败";
		String sql="select password,identity from W_SEAT_INFO where name=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, username);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          String passwordget=rs.getString("password");
	          String identity=rs.getString("identity");
	          if (passwordget==null||"".equals(passwordget)) {
					result= "没有该用户";
				}else if (passwordget.equals(password)&&"student".equals(identity)) {
					result= "登录成功";
				}
	        }  
	        System.out.println("studentLogin:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("studentLogin:"+result);
		return result;			
	}
	/**
	 * 教师登录
	 * @param username
	 * @param password
	 * @return
	 */
	public String teacherLogin(String username,String password) {
		String result="登录失败";
		String sql="select password,identity from W_SEAT_INFO where name=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, username);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          String passwordget=rs.getString("password");
	          String identity=rs.getString("identity");
	          if (passwordget==null||"".equals(passwordget)) {
					result= "没有该用户";
				}else if (passwordget.equals(password)&&"teacher".equals(identity)) {
					result= "登录成功";
				}
	        }  
	        System.out.println("teacherLogin:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("teacherLogin:"+result);
		return result;			
	}
	/**
	 * 根据用户名字查找座位号
	 * @param username
	 * @return
	 */
	public String selectseatIdByName(String username) {
		String result="";
		String sql="select seatid from W_SEAT_INFO where name=? order by savetime desc";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, username);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          result=rs.getString("seatid");
	        }  
	        System.out.println("selectseatIdByName:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("selectseatIdByName:"+result);
		return result;			
	}
	
	/**
	 * 根据名字查询所有的出勤记录
	 * @param username
	 * @return
	 */
	public String getAllHisStateInfoByName(String username) {
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			State state=null;
			List<State> list=new ArrayList<>();
			String sql="select * from W_ADAPTER_HISTORY where relationid=? order by savetime desc";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           state=new State(rs.getString("relationid"), rs.getString("openstate"), rs.getString("savetime"));
		           list.add(state);
		        }  
		        JSONArray jsonArray = JSONArray.fromObject(list);
		        result=jsonArray.toString();
		        System.out.println("getAllHisStateInfoByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getAllHisStateInfoByName: "+result);
		return result;			
	}
	/**
	 * 根据名字查询当天的出勤情况
	 * @param username
	 * @return
	 */
	public String getCurStateInfoByName(String username) {
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			State state=null;
			List<State> list=new ArrayList<>();
			String sql="select * from w_adapter_history where trunc(savetime) = trunc(sysdate) and relationid=? order by savetime desc";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           state=new State(rs.getString("relationid"), rs.getString("openstate"), rs.getString("savetime"));
		           list.add(state);
		        }  
		        JSONArray jsonArray = JSONArray.fromObject(list);
		        result=jsonArray.toString();
		        System.out.println("getCurStateInfoByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getCurStateInfoByName: "+result);
		return result;			
	}
	/**
	 * 查询昨天的出勤记录
	 * @param username
	 * @return
	 */
	public String getYesterdayStateInfoByName(String username) {
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			State state=null;
			List<State> list=new ArrayList<>();
			String sql="select * from w_adapter_history where to_char(savetime,'yyyy/MM/dd')=to_char(sysdate-1,'yyyy/MM/dd') and relationid=? order by savetime desc";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           state=new State(rs.getString("relationid"), rs.getString("openstate"), rs.getString("savetime"));
		           list.add(state);
		        }  
		        JSONArray jsonArray = JSONArray.fromObject(list);
		        result=jsonArray.toString();
		        System.out.println("getYesterdayStateInfoByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getYesterdayStateInfoByName: "+result);
		return result;			
	}
	/**
	 * 获取近一周的学生出勤记录
	 * @param username
	 * @return
	 */
	public String getLastWeekStateInfoByName(String username) {
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			State state=null;
			List<State> list=new ArrayList<>();
			String sql="select * from w_adapter_history where to_char(savetime,'yyyy/MM/dd')>to_char(sysdate-8,'yyyy/MM/dd') and relationid=? order by savetime desc";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           state=new State(rs.getString("relationid"), rs.getString("openstate"), rs.getString("savetime"));
		           list.add(state);
		        }  
		        JSONArray jsonArray = JSONArray.fromObject(list);
		        result=jsonArray.toString();
		        System.out.println("getLastWeekStateInfoByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getLastWeekStateInfoByName: "+result);
		return result;			
	}
	/**
	 * 查询最近一个月的出勤记录
	 * @param username
	 * @return
	 */
	public String getLastMounthStateInfoByName(String username) {
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			State state=null;
			List<State> list=new ArrayList<>();
			String sql="select * from W_ADAPTER_HISTORY where savetime >= add_months(sysdate,-1) and savetime <= sysdate and relationid=? order by savetime desc";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           state=new State(rs.getString("relationid"), rs.getString("openstate"), rs.getString("savetime"));
		           list.add(state);
		        }  
		        JSONArray jsonArray = JSONArray.fromObject(list);
		        result=jsonArray.toString();
		        System.out.println("getLastMounthStateInfoByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getLastMounthStateInfoByName: "+result);
		return result;			
	}
	/**
	 * 获取学生的最近三个月的出勤记录
	 * @param username
	 * @return
	 */
	public String getLastThreeMounthStateInfoByName(String username) {
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			State state=null;
			List<State> list=new ArrayList<>();
			String sql="select * from W_ADAPTER_HISTORY where savetime >= add_months(sysdate,-3) and savetime <= sysdate and relationid=? order by savetime desc";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           state=new State(rs.getString("relationid"), rs.getString("openstate"), rs.getString("savetime"));
		           list.add(state);
		        }  
		        JSONArray jsonArray = JSONArray.fromObject(list);
		        result=jsonArray.toString();
		        System.out.println("getLastMounthStateInfoByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getLastMounthStateInfoByName: "+result);
		return result;			
	}
	/**
	 * 查找当天的用电量
	 * @param username
	 * @return
	 */
	public String getCurPowerByName(String username){
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			String sql="select (max(savetime)-min(savetime))*24*3600 from w_adapter_history where trunc(savetime) = trunc(sysdate) and relationid=?";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           result=rs.getString(1);
		        }  
		        if (result==null||"".equals(result)) {
					result="not";
				}
		        System.out.println("getCurPowerByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getCurPowerByName: "+result);
		return result;			
	}
	/**
	 * 查找昨天的用电量
	 * @param username
	 * @return
	 */
	public String getYesterdayPowerByName(String username){
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			String sql="select (max(savetime)-min(savetime))*24*3600 from w_adapter_history where trunc(savetime) = trunc(sysdate-1) and relationid=?";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           result=rs.getString(1);
		        }  
		        if (result==null||"".equals(result)) {
					result="not";
				}
		        System.out.println("getYesterdayPowerByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getYesterdayPowerByName: "+result);
		return result;			
	}
	/**
	 * 查找最近一周的用电量	
	 * @param username
	 * @return
	 */
	public String getLastWeekPowerByName(String username){
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			String sql="select sum(a.cha)from (select to_char(t.savetime,'YYYY-MM-DD') as daytime,(max(t.savetime)-min(savetime))*24*3600 as cha from W_ADAPTER_HISTORY t WHERE to_char(t.savetime,'yyyy/MM/dd')>to_char(sysdate-8,'yyyy/MM/dd')and relationid=? group by to_char(t.savetime,'YYYY-MM-DD') ORDER BY daytime) a";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           result=rs.getString(1);
		        }
		        if (result==null||"".equals(result)) {
					result="not";
				}
		        System.out.println("getLastWeekPowerByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getLastWeekPowerByName: "+result);
		return result;			
	}
	/**
	 * 获取最近一个月的学生用电量
	 * @param username
	 * @return
	 */
	public String getLastMounthPowerByName(String username){
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			String sql="select sum(a.cha)from (select to_char(t.savetime,'YYYY-MM-DD') as daytime,(max(t.savetime)-min(savetime))*24*3600 as cha from W_ADAPTER_HISTORY t WHERE savetime >= add_months(sysdate,-1) and savetime <= sysdate and relationid=? group by to_char(t.savetime,'YYYY-MM-DD') ORDER BY daytime) a";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           result=rs.getString(1);
		        }  
		        if (result==null||"".equals(result)) {
					result="not";
				}
		        System.out.println("getLastMounthPowerByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getLastMounthPowerByName: "+result);
		return result;			
	}
	/**
	 * 查询最近三个月的学生用电量
	 * @param username
	 * @return
	 */
	public String getLastThreeMounthPowerByName(String username){
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			String sql="select sum(a.cha)from (select to_char(t.savetime,'YYYY-MM-DD') as daytime,(max(t.savetime)-min(savetime))*24*3600 as cha from W_ADAPTER_HISTORY t WHERE savetime >= add_months(sysdate,-3) and savetime <= sysdate and relationid=? group by to_char(t.savetime,'YYYY-MM-DD') ORDER BY daytime) a";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           result=rs.getString(1);
		        }  
		        if (result==null||"".equals(result)) {
					result="not";
				}
		        System.out.println("getLastThreeMounthPowerByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getLastThreeMounthPowerByName: "+result);
		return result;			
	}
	/**
	 * 根据名字查找用户余额
	 * @param username
	 * @return
	 */
	public String selectBalanceByName(String username) {
		String result="";
		String sql="select balance from W_SEAT_INFO where name=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, username);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          result=rs.getString("balance");
	        }  
	        System.out.println("selectBalanceByName:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("selectBalanceByName:"+result);
		return result;			
	}
	/**
	 * 获取当天的消费记录
	 * @param username
	 * @return
	 */
	public String getCurConsumeByName(String username) {
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			Account account=null;
			List<Account> list=new ArrayList<>();
			String sql="select * from W_BALANCE where trunc(savetime) = trunc(sysdate) and relationid=? order by savetime desc";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           account=new Account(rs.getString("consume"), rs.getString("savetime"));
		           list.add(account);
		        }  
		        JSONArray jsonArray = JSONArray.fromObject(list);
		        result=jsonArray.toString();
		        System.out.println("getCurConsumeByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getCurConsumeByName: "+result);
		return result;			
	}
	
	/**
	 * 获取近一周消费记录
	 * @param username
	 * @return
	 */
	public String getLastWeekConsumeByName(String username) {
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			Account account=null;
			List<Account> list=new ArrayList<>();
			String sql="select * from W_BALANCE where to_char(savetime,'yyyy/MM/dd')>to_char(sysdate-8,'yyyy/MM/dd') and relationid=? order by savetime desc";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           account=new Account(rs.getString("consume"), rs.getString("savetime"));
		           list.add(account);
		        }  
		        JSONArray jsonArray = JSONArray.fromObject(list);
		        result=jsonArray.toString();
		        System.out.println("getLastWeekConsumeByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getLastWeekConsumeByName: "+result);
		return result;			
	}
	/**
	 * 获取最近一个月的消费记录
	 * @param username
	 * @return
	 */
	public String getLastMounthConsumeByName(String username) {
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			Account account=null;
			List<Account> list=new ArrayList<>();
			String sql="select * from W_BALANCE where savetime >= add_months(sysdate,-1) and savetime <= sysdate and relationid=? order by savetime desc";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           account=new Account(rs.getString("consume"), rs.getString("savetime"));
		           list.add(account);
		        }  
		        JSONArray jsonArray = JSONArray.fromObject(list);
		        result=jsonArray.toString();
		        System.out.println("getLastMounthConsumeByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getLastMounthConsumeByName: "+result);
		return result;			
	}
	
	/**
	 * 查看最近三个月的消费记录
	 * @param username
	 * @return
	 */
	public String getLastThreeMounthConsumeByName(String username) {
		String result="";
		String relationid=selectseatIdByName(username);
		if (relationid==null||"".equals(relationid)) {
			return result;
		}else {
			Account account=null;
			List<Account> list=new ArrayList<>();
			String sql="select * from W_BALANCE where savetime >= add_months(sysdate,-3) and savetime <= sysdate and relationid=? order by savetime desc";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	pstmt.setString(1, relationid);
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           account=new Account(rs.getString("consume"), rs.getString("savetime"));
		           list.add(account);
		        }  
		        JSONArray jsonArray = JSONArray.fromObject(list);
		        result=jsonArray.toString();
		        System.out.println("getLastThreeMounthConsumeByName: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		}
		 System.out.println("getLastThreeMounthConsumeByName: "+result);
		return result;			
	}
	
	/**
	 * 根据用户名字查找插座实时状态
	 * @param username
	 * @return
	 */
	public String selectRealStateByName(String username) {
		String result="";
		String id=selectseatIdByName(username);
		String sql="select openstate from W_ADAPTER_REALTIME where relationid=? order by savetime desc";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, id);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          result=rs.getString("openstate");
	        }  
	        System.out.println("selectRealStateByName:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("selectRealStateByName:"+result);
		return result;			
	}
	
	/**
	 * 根据用户名远程关闭电源
	 * @param username
	 * @return
	 */
	public String remotecontrol(String username) {
		String result="success";
		 try {
		 WebSocketContainer container = ContainerProvider.getWebSocketContainer(); 
		Session session=null;
		 String uri ="ws://10.10.24.74:8080/AdapterWebSocketServer/websocket.ws/relationId/1234567"; 
		 System.out.println("Connecting to:"+ uri); 
		 try { 
			  session = container.connectToServer(MyClient.class, URI.create(uri)); 
		 } catch (DeploymentException e) { 
			 e.printStackTrace();
			 result= "failed";
		 } catch (IOException e) { 
		e.printStackTrace();
		result= "failed";
		}
	        //session.getBasicRemote().sendText("123456_"+"S:N=801002@Relay=1E;");//开灯
	       // session.getBasicRemote().sendText("123456_"+"S:N=801002@Relay=0E;");//关灯
	     //   session.getBasicRemote().sendText("123456_"+"S:N=801002@Find=1E;");//开蜂鸣
		 String relationid=selectseatIdByName(username);
		 String state=selectRealStateByName(username);
		 if ("open".equals(state)) {
			 session.getBasicRemote().sendText(relationid+"_#NCTR@Relay=0E;");//关蜂鸣
		}else {
			return "isclosed";
		}
			
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result="failed";
		}//关蜂鸣器
		return result;
	}
	
	
	
	/**
	 * 插入消费记录
	 * @param relationid
	 * @return
	 */
	public String insertConsumeBalance(String relationid,String value) {
		String sql = "insert into w_balance values(BALANCEID_INCREASE.nextval,?,?,sysdate)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,relationid );
			pstmt.setString(2, value);
			int flag = pstmt.executeUpdate();
			if(flag==1){
				System.out.println("insertConsumeBalance:success");
				return "success";			
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("insertConsumeBalance:failed");
		return "failed";
	}
	
	/**
	 * 充值
	 * @param username
	 * @param value
	 * @return
	 */
	public String charge(String username,String value){
		String balance=selectBalanceByName(username);
		String relationid=selectseatIdByName(username);
		String sql = "update W_SEAT_INFO set balance=? where seatid=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,(Double.parseDouble(balance)+Double.parseDouble(value))+"");
			pstmt.setString(2,relationid );
			int flag = pstmt.executeUpdate();
			if(flag==1){
				System.out.println("charge:success");
				insertConsumeBalance(relationid, value);
				return "success";			
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("charge:failed");
		return "failed";
	}
	/**
	 * 获取所有学生姓名
	 * @return
	 */
	public String selectStudentName() {
		String result="";
		String sql="select name from w_seat_info where identity='student'";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	rs=pstmt.executeQuery();       
	        while(rs.next())
	        {
	          result=result+rs.getString("name")+":";
	        } 
	        if (result.length()>1) {
				result=result.substring(0, result.length()-1);
			}
	        System.out.println("selectStudentName:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("selectStudentName:"+result);
		return result;			
	}
	/**
	 * 根据姓名查找号码
	 * @param username
	 * @return
	 */
	public String selectPhoneByName(String username) {
		String result="";
		String sql="select telnum from w_seat_info where name=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	
        	pstmt.setString(1, username);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          result=rs.getString("telnum");
	        } 
	        System.out.println("selectPhoneByName:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("selectPhoneByName:"+result);
		return result;			
	}
	
	
	/**
	 * 根据学生名字查看学生项目信息
	 * @param username
	 * @return
	 */
	public String selectStuProjectInfo(String username) {
		String result="";
		String sql="select xuehao,projectname,teachername,durality from w_stuproj where name=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	
        	pstmt.setString(1, username);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          result=result+rs.getString("xuehao")+":";
	          result=result+rs.getString("projectname")+":";
	          result=result+rs.getString("teachername")+":";
	          result=result+rs.getString("durality");
	        } 
	        System.out.println("selectStuProjectInfo:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("selectStuProjectInfo:"+result);
		return result;			
	}
	
	/**
	 * 获取所有的实时状态信息
	 * @return
	 */
	public String getAllStateInfo() {
		String result="";
			State state=null;
			List<State> list=new ArrayList<>();
			String sql="select * from w_adapter_realtime";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           state=new State(rs.getString("relationid"), rs.getString("openstate"),null);
		           list.add(state);
		        }  
		        JSONArray jsonArray = JSONArray.fromObject(list);
		        result=jsonArray.toString();
		        System.out.println("getAllStateInfo: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		 System.out.println("getAllStateInfo: "+result);
		return result;			
	}
	
	public String getAllOpenStateInfo() {
		String result="";
			String sql="select relationid from w_adapter_realtime where openstate='open'";		
	        try{
	        	conn=ds.getConnection();
	        	pstmt=conn.prepareStatement(sql);  	         
	        	rs=pstmt.executeQuery();       
		        while(rs.next())
		        {
		           result=result+rs.getString("relationid")+":";
		        } 
		        if (result.length()>1) {
					result=result.substring(0, result.length()-1);
				}
		        System.out.println("getAllOpenStateInfo: "+result);
				return result;
	        }catch(Exception e){
	        	e.printStackTrace();
	        }finally{
	        	try{
		        	if(rs!=null)
		        		rs.close();
		        	if(pstmt!=null)
		        		pstmt.close();
		        	if(conn!=null)
		        		conn.close();
	        	}catch(Exception e){
	        		e.printStackTrace();
	        	}
	        }
		 System.out.println("getAllOpenStateInfo: "+result);
		return result;			
	}
	
	public String selectnamedBySeatid(String seatid) {
		String result="";
		String sql="select name from W_SEAT_INFO where seatid=? order by savetime desc";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, seatid);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          result=rs.getString("name");
	        }  
	        System.out.println("selectnamedBySeatid:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("selectnamedBySeatid:"+result);
		return result;			
	}
	/**
	 * 查找最近一周的出勤次数
	 * @param username
	 * @return
	 */
	public String selectLastWeekCount(String username) {
		String seatid=selectseatIdByName(username);
		String result="";
		String sql="select count(*) from (select * from(select to_char(savetime,'yyyy/MM/dd') aa from w_adapter_history where to_char(savetime,'yyyy/MM/dd')>to_char(sysdate-8,'yyyy/MM/dd') and relationid=? and openstate='open' group by savetime order by savetime desc)  dd group by dd.aa order by dd.aa desc)";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, seatid);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          result=rs.getString(1);
	        }  
	        System.out.println("selectLastWeekCount:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("selectLastWeekCount:"+result);
		return result;			
	}
	
	/**
	 * 查找最近一个月的出勤记录
	 * @param username
	 * @return
	 */
	public String selectLastMounthCount(String username) {
		String seatid=selectseatIdByName(username);
		String result="";
		String sql="select count(*) from (select * from(select to_char(savetime,'yyyy/MM/dd') aa from w_adapter_history where savetime >= add_months(sysdate,-1) and savetime <= sysdate and relationid=? and openstate='open' group by savetime order by savetime desc) dd group by dd.aa order by dd.aa desc)";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, seatid);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          result=rs.getString(1);
	        }  
	        System.out.println("selectLastMounthCount:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("selectLastMounthCount:"+result);
		return result;			
	}
	
	public String selectLastThreeMounthCount(String username) {
		String seatid=selectseatIdByName(username);
		String result="";
		String sql="select count(*) from (select * from(select to_char(savetime,'yyyy/MM/dd') aa from w_adapter_history where savetime >= add_months(sysdate,-3) and savetime <= sysdate and relationid=? and openstate='open' group by savetime order by savetime desc) dd group by dd.aa order by dd.aa desc)";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, seatid);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          result=rs.getString(1);
	        }  
	        System.out.println("selectLastThreeMounthCount:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("selectLastThreeMounthCount:"+result);
		return result;			
	}
	
	/**
	 * 信息卡号表中用于绑定用户卡号和座位号(插座)
	 * @param name
	 * @param seatid
	 * @param serialnumber
	 * @return
	 */
	public int insertCardInfo(String name, String seatid,String serialnumber) {
		
		String sql = "insert into W_CARD_INFO values(CARDID_INCREASE.nextval,?,?,?,sysdate)";
		int flag=0;
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, seatid);
			pstmt.setString(3, serialnumber);
			flag = pstmt.executeUpdate();
			System.out.println("insertCardInfo"+flag);
			return flag;
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try{
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("insertCardInfo"+flag);
		return flag;
	}
	
	/**
	 * 根据用户名查找卡号表格中的信息条数
	 * @param name
	 * @return
	 */
	public String queryCardInfoCount(String seatid) {
		String result="";
		String sql="select count(*) from w_card_info where seatid=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, seatid);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          result=rs.getString(1);
	        }  
	        System.out.println("queryCardInfoCount:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("queryCardInfoCount:"+result);
		return result;			
	}
	
	/**
	 * 更改座位信息表
	 * @param username
	 * @param seatid
	 * @param serialnumber
	 * @return
	 */
	public String updateSeatCardInfo(String username,String seatid,String serialnumber){
		String sql = "update w_card_info set username=? ,serialnumber=? ,savetime=sysdate where seatid=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,username);
			pstmt.setString(2, serialnumber);
			pstmt.setString(3, seatid);
			int flag = pstmt.executeUpdate();
			if(flag==1){
				System.out.println("updateSeatCardInfo:success");
				return "success";			
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("updateSeatCardInfo:failed");
		return "failed";
	}
	/**
	 * 实际插入或修改接口
	 * @param username
	 * @param seatid
	 * @param serialnumber
	 * @return
	 */
	public String addOrupdateSeatCardInfo(String username,String seatid,String serialnumber){
		String count=queryCardInfoCount(seatid);
		if (Integer.parseInt(count)>0) {
			return updateSeatCardInfo(username, seatid, serialnumber);
		}else {
			return insertCardInfo(username, seatid, serialnumber)+"";
		}
	}
	
	/**
	 * 添加学生的项目信息
	 * @param name
	 * @param xuehao
	 * @param projectname
	 * @param teachername
	 * @param durality
	 * @return
	 */
	public String insertStuProj(String name,String xuehao,String projectname,String teachername,String durality) {
		String sql = "insert into w_stuproj values(W_STUPROJID_INCREASE.nextval,?,?,?,?,?,sysdate)";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,name );
			pstmt.setString(2, xuehao);
			pstmt.setString(3, projectname);
			pstmt.setString(4, teachername);
			pstmt.setString(5, durality);
			int flag = pstmt.executeUpdate();
			if(flag==1){
				System.out.println("insertStuProj:success");
				return "success";			
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("insertStuProj:failed");
		return "failed";
	}
	
	/**
	 * 根据用户名查找是否有项目
	 * @param name
	 * @return
	 */
	public String queryStuProjInfoCount(String username) {
		String result="";
		String sql="select count(*) from w_stuproj where name=?";		
        try{
        	conn=ds.getConnection();
        	pstmt=conn.prepareStatement(sql);  	         
        	pstmt.setString(1, username);
        	rs=pstmt.executeQuery();       
	        if(rs.next())
	        {
	          result=rs.getString(1);
	        }  
	        System.out.println("queryStuProjInfoCount:"+result);
			return result;
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	try{
	        	if(rs!=null)
	        		rs.close();
	        	if(pstmt!=null)
	        		pstmt.close();
	        	if(conn!=null)
	        		conn.close();
        	}catch(Exception e){
        		e.printStackTrace();
        	}
        }
        System.out.println("queryStuProjInfoCount:"+result);
		return result;			
	}
	
	
	public String updateStuProjInfo(String username,String xuehao,String projectname,String teachername,String durality){
		String sql = "update w_stuproj set xuehao=? ,projectname=?,teachername=?,durality=?,savetime=sysdate where name=?";
		try {
			conn = ds.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,xuehao);
			pstmt.setString(2, projectname);
			pstmt.setString(3, teachername);
			pstmt.setString(4, durality);
			pstmt.setString(5, username);
			int flag = pstmt.executeUpdate();
			if(flag==1){
				System.out.println("updateStuProjInfo:success");
				return "success";			
			}
		}catch(Exception e){			
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("updateStuProjInfo:failed");
		return "failed";
	}
	/**
	 * 修改项目信息
	 * @param username
	 * @param xuehao
	 * @param projectname
	 * @param teachername
	 * @param durality
	 * @return
	 */
	public String addOrupdateStuProjInfo(String username,String xuehao,String projectname,String teachername,String durality){
		String count=queryStuProjInfoCount(username);
		if (Integer.parseInt(count)>0) {
			return updateStuProjInfo(username, xuehao, projectname, teachername, durality);
		}else {
			return insertStuProj(username, xuehao, projectname, teachername, durality);
		}
	}
}
