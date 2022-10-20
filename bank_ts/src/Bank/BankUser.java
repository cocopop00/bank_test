package Bank;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class BankUser extends Frame implements ActionListener{

	
	
	// ArrayList
	private ArrayList<BankUserInfo> mem = new ArrayList<>();	
	
	// database
	BankDateBase db_cl = new BankDateBase();
	BankMenu bm_cl;
	
	// component
	Label logo_la;
	private Label[] null_la1 = new Label[10];
	Label userMessage_la;
	Label userInfoLogo_la;
	
	Label userName_la;
	Label userId_la;
	Label userPw_la;
	Label userBankNum_la;
	Label userPhoneNum_la;
	Label userCash_la;
	Label userJoin_la;
	Label userMembership_la;
	
	
	TextField userPw_tx;
	
	
	Button cashSum1_bu;
	Button cashSum2_bu;
	Button cashSum3_bu;
	Button cashSum4_bu;
	Button next_bu;
	Button back_bu;
	Button bankPwCheck_bu;
	
	// Panel
	Panel center_p;
	Panel center_1_1_p;
	Panel center_1_2_p;
	Panel center_1_2_1_p;
	Panel center_2_p;
	Panel south_p;
	
	String pwMax;
	private int loginCheck = 0;
	private int pageButton = 0;
	private int memIndex = 0;
	private int[] loginBuf = new int[2];
	private int pwCheck = 0;
	
	
	public BankUser() {
		setFieldInit();
		setLayoutInit();
		getOpenLogin();
	}
	
	
	
	public void setFieldInit() {
		
		logo_la = new Label("------------ Bk Bank ------------",Label.CENTER);
		userInfoLogo_la = new Label("회원 정보",Label.CENTER);
		userMessage_la = new Label("",Label.CENTER);
		
		
		userName_la = new Label("",Label.LEFT);
		userId_la = new Label("",Label.LEFT);
		userBankNum_la = new Label("",Label.LEFT);
		userPhoneNum_la = new Label("",Label.LEFT);
		userCash_la = new Label("",Label.LEFT);
		userJoin_la = new Label("",Label.LEFT);
		userMembership_la = new Label("",Label.LEFT);
		
		
		next_bu = new Button("닫기");
		back_bu = new Button("회원정보변경");
		bankPwCheck_bu = new Button("체크");
		// 빈창 초기화
		for(int i = 0; i < null_la1.length; i++) {
			this.null_la1[i] = new Label("");
		}
	
		
		logo_la.setFont(new Font("굴림체",Font.BOLD,20));
		userInfoLogo_la.setFont(new Font("궁서체",Font.BOLD,15));
	}
	
	public void setLayoutInit() {
		
		center_p = new Panel();
		center_1_1_p = new Panel();
		center_1_2_p = new Panel();
		center_1_2_1_p = new Panel();
		center_2_p =  new Panel();
		south_p = new Panel();
		
		// 메인
		setLayout(new BorderLayout());
		
		
		center_2_p.setLayout(new FlowLayout());
		center_2_p.add(back_bu);
		center_2_p.add(next_bu);
		
		center_p.setLayout(new GridLayout(11,1,2,2));
		center_p.add(userInfoLogo_la);
		center_p.add(null_la1[0]);
		
		center_p.add(userName_la);
		center_p.add(userId_la);
		center_p.add(userPhoneNum_la);
		center_p.add(userBankNum_la);
		center_p.add(userCash_la);
		center_p.add(userJoin_la);
		center_p.add(userMembership_la);
		
		center_p.add(null_la1[1]);
		center_p.add(center_2_p);

		
		
		south_p.setLayout(new GridLayout(2,1));
		//south_p.add(null_la1[8]);
		south_p.add(null_la1[3]);
		
		// 메인 add
		add(logo_la, "North");
		add(null_la1[4], "West");
		add(null_la1[5], "East");
		add(center_p, "Center");
		add(south_p, "South");	
		
	}
	
	public void getOpenLogin() {
		
		
		memIndex = dbLoard(mem);
		loginBuf = loginSearching(mem);
		loginCheck = loginBuf[0];
		memIndex = loginBuf[1];
		
		if(loginCheck == 1) {
			userBankNum_la.setAlignment(Label.LEFT);
			userBankNum_la.setText("계좌번호 : "+ mem.get(memIndex).getBankNum());
			userCash_la.setText("금액 : " + mem.get(memIndex).getCash() + "원");
			
			
			userName_la.setText("성함 : " + mem.get(memIndex).getName());
			userId_la.setText("ID : " + mem.get(memIndex).getId());
			userPhoneNum_la.setText("전화번호 : " + mem.get(memIndex).getPhoneNum());
			userBankNum_la.setText("계좌번호 : " + mem.get(memIndex).getBankNum());
			
			
			String userCash = String.valueOf(mem.get(memIndex).getCash());
			
			userCash_la.setText("잔액 : " + userCash + "원");
			userJoin_la.setText("가입날짜 : " + mem.get(memIndex).getJoinDate());
			userMembership_la.setText("등급 : " + mem.get(memIndex).getMembership());
		}
		
		
		next_bu.addActionListener(this);
		
		this.setSize(300, 450);
		this.setResizable(false);
		this.setVisible(true);
		
		//========== 프레임 위치 가운데로 고정
		this.setLocationRelativeTo(null);
		
		// 창 닫는 메소드
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				//창을 닫을때, 강제 로그아웃.
				//loginCheck = 0;
				//dbSave(mem,memIndex);
				//System.exit(0);
			}
		});	 
		
	}
	
	
	//================================================
	// 데이터베이스 정보 읽어오기
	//================================================
	
	// 데이터베이스에서 정보 읽어오기
	public int dbLoard(ArrayList<BankUserInfo> data) {
		
		// 카운터 및 오류 검사 버퍼.
		int buf = 0;
		
		String sql = "select * from BANKDB";
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBselect(sql,data);
		
		if(buf == 0) System.out.println("loading..error");
		
		return buf; 
		
	}
	
	
	//================================================
	// 로그인을 찾는다.
	//================================================
	
	// 로그인 상태를 찾는다.
	// 로그인에서 아이디와 비밀번호가 맞으면 로그인 변수에 "on"이 찍힘.
	public int[] loginSearching(ArrayList<BankUserInfo> data) {
		
		int[] buf = new int[2];
		String str = "on";
		
		// 1이면 찾은거, 0이면 못찾은거
		for(int i = 0; i < data.size(); i++) {
			if(str.equals(data.get(i).getLogin())) {
				buf[0] = 1;
				buf[1] = i;
				break;
			}
		}
		
		return buf;
	}
	
	
	// 데이터베이스로 정보 저장하기
	public int dbSave(ArrayList<BankUserInfo> data, int index) {
		
		// 세이브 유무 버퍼
		int buf = 0;
		String str = "";

		if(loginCheck == 0) {
			str = "UPDATE BANKDB SET LOGIN='off', memory= '0', memory_pay= 0,";
			str += "LAST_UPDATE=NOW() WHERE ID='"+mem.get(index).getId()+"'";
		}
		
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBupdate(str);
		
		if(buf == 0) System.out.println("save error");
		
		return buf;
		
	}
	
	//======================================================
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BankUser();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj == next_bu) {
			bm_cl = new BankMenu();
			bm_cl.setVisible(true);
			this.setVisible(false);
			this.dispose();
		}
		
	}

}
