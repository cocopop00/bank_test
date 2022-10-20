package Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


// 데이터 베이스 넣는 구간.
public class BankDateBase{

	// 기본 설정
	
	
	// 정보를 담는 객체
	ResultSet rs = null;
	Connection conn = null;
	Statement stmt = null;
	
	// 드라이버 주소.
	String driver = "com.mysql.cj.jdbc.Driver";
	// sql 호스트와 포트 주소. 주의 : (나중에 sql를 바꾸면 이 부분도 바뀜) 
	String url = "jdbc:mysql://localhost:3306/app";
	static BankDateBase bm = new BankDateBase();
	
	/**
	 * 데이터 베이스 드라이버 연결 메소드
	 */
	public void setDBdriveInit() {
	
		try {
			Class.forName(driver);
			System.out.println("DataBase loading success");
		
			conn = DriverManager.getConnection(url,"root","java");
			System.out.println("DataBase server success");
			
		}catch(Exception e) {
			System.out.println("DataBase server fail");
			e.printStackTrace();
		}
	
	}
	
	
	/**
	 * 데이터베이스 테이블 설정.
	 * @param sql
	 */
	public void setDBtable(String sql) {
		
		try {
			stmt = conn.createStatement();
			int rsult = stmt.executeUpdate(sql);
			
			if(rsult < 0)System.out.println("server fail");
			else System.out.println("server success");
			
		} catch (Exception e) {
			System.out.println("DataBase loading fail");
			e.printStackTrace();
		}finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {}
		}

	}	
	
	
	/**
	 * 데이터 베이스 업데이트 
	 * @param sql
	 */
	public int setDBupdate(String sql) {
		
		try {
			stmt = conn.createStatement();
			int rsult = stmt.executeUpdate(sql);
			
			if(rsult < 0)System.out.println("server fail");
			else System.out.println("server success");
			
		} catch (Exception e) {
			System.out.println("DataBase loading fail");
			e.printStackTrace();
			return 0;
		}finally {
			try {
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {return 0;}
		}
		
		
		
		return 1;
	}
	
	
	
	/**
	 * 데이터베이스의 데이터 추출
	 * @param sql
	 * @return
	 */
	public int setDBselect(String sql, ArrayList<BankUserInfo> data) {
		
		int count = 0;
		int memNum = 0;
		
		try {
			// query로 Resultset를 선언.
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				BankUserInfo bkuser = new BankUserInfo();
				data.add(bkuser);
				
				data.get(count).setNumber(rs.getInt("num"));
				data.get(count).setName(rs.getString("name"));
				data.get(count).setId(rs.getString("id"));
				data.get(count).setPw(rs.getString("pw"));
				data.get(count).setPhoneNum(rs.getString("PHONNUMBER"));
				data.get(count).setBankPw(rs.getString("bankpw"));
				data.get(count).setBankNum(rs.getString("BANKNUMBER"));
				data.get(count).setCash(rs.getInt("cash"));
				data.get(count).setJoinDate(rs.getString("JOIN_DATE"));
				data.get(count).setLogin(rs.getString("login"));
				data.get(count).setMembership(rs.getString("membership"));
				data.get(count).setResidentNum(rs.getString("RESIDENT_NUM"));
				data.get(count).setMemory(rs.getString("memory"));
				data.get(count).setMemory_pay(rs.getInt("memory_pay"));
				
				
				count++;
			}
			
		} catch (Exception e) {
			System.out.println("DataBase loading fail");
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {}
		}
		
		if(count <= 0) memNum = 0;
		else memNum = (data.get(count-1).getNumber()) + 1;
		
		
		return memNum;
	}	
	
	
	
	public int setBreakDouwnselect(String sql, ArrayList<bankBreakDownInfo> data) {
		
		int count = 0;
		int memNum = 0;
		
		try {
			// query로 Resultset를 선언.
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			
			while(rs.next()) {
				
				bankBreakDownInfo bkbd = new bankBreakDownInfo();
				data.add(bkbd);
				
				data.get(count).setDateTime(rs.getString("date"));
				data.get(count).setExteralBankNum(rs.getString("output_banknumber"));
				data.get(count).setInteralBankNum(rs.getString("input_banknumber"));
				data.get(count).setTotalCash(rs.getInt("total_cash"));
				data.get(count).setTransaction(rs.getString("transaction"));
				data.get(count).setUsingCash(rs.getInt("cash"));
				data.get(count).setUsingName(rs.getString("usingname"));

				count++;
				
			}
			
		} catch (Exception e) {
			System.out.println("DataBase loading fail");
			e.printStackTrace();
			return 0;
		}finally {
			try {
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(Exception e) {}
		}
		
		
		return 1;	
		
		
	}
	
	
}
