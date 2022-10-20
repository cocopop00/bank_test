package Bank;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

//import bank.BankCashIo;

public class BankMenu extends Frame implements ActionListener{
	
	// ArrayList
	private ArrayList<BankUserInfo> mem = new ArrayList<>();
	
	// database
	BankDateBase db_cl = new BankDateBase();
	BankLogin log_cl;
	BankCashIo bkInput_cl;
	BankUser bankUer_cl;
	BankTranfer bankT_cl;
	bankBreakDown bkbd_cl;
	
	// Filed
	private int loginCheck = 0;
	private int pageButton = 0;
	private int memIndex = 0;
	private int[] loginBuf = new int[2];
	
	
	// component
	Button main_bu;
	Button user_bu;
	Button login_bu;
	
	Button one_bu;
	Button two_bu;
	Button three_bu;
	Button four_bu;
	

	Label logo_la;
	
	// 메인 회원정보
	Label loginTime_la;
	Label userName_la;
	Label userBankNum_la;
	Label userCash_la;
	
	private Label[] null_la1 = new Label[10];
	
	// panel
	Panel south_p;
	Panel south_1_p;
	
	Panel center_p;
	Panel center_1_1_p;
	Panel center_1_2_p;
	Panel center_1_3_p;
	Panel center_1_p;
	Panel center_2_p;
	Panel center_3_p;
	
	
	public BankMenu() {
		setFieldInit();
		setLayoutInit();
		getOpenLogin();
	}
	
	

	public void setFieldInit() {
		
		setTitle("Menu");
		
		logo_la = new Label("------------ Bk Bank ------------",Label.CENTER);
		loginTime_la = new Label("");
		userName_la = new Label("");
		userBankNum_la = new Label("로그인 해주세요.",Label.CENTER);
		userCash_la = new Label("");
		
		main_bu = new Button("Home");
		user_bu = new Button("MyInfo");
		login_bu = new Button("Login");
		
		one_bu = new Button("입금");
		two_bu = new Button("출금");
		three_bu = new Button("이체");
		four_bu = new Button("내역");
		
		// 빈창 초기화
		for(int i = 0; i < null_la1.length; i++) {
			this.null_la1[i] = new Label("");
		}
		
		//======================
		logo_la.setFont(new Font("굴림체",Font.BOLD,20));
		
		// home 버튼 
		one_bu.setFont(new Font("나눔고딕",Font.BOLD,15));
		two_bu.setFont(new Font("나눔고딕",Font.BOLD,15));
		three_bu.setFont(new Font("나눔고딕",Font.BOLD,15));
		four_bu.setFont(new Font("나눔고딕",Font.BOLD,15));
		
		// home 로그인 상태
		userName_la.setFont(new Font("굴림",Font.BOLD,15));
		userBankNum_la.setFont(new Font("굴림",Font.PLAIN,11));
		userCash_la.setFont(new Font("굴림",Font.PLAIN,11));
	}
	
	
	
	public void setLayoutInit() {
	
		south_p = new Panel();
		south_1_p = new Panel();
		center_p = new Panel();
		center_1_p = new Panel();
		center_1_1_p = new Panel();
		center_1_2_p = new Panel();
		center_1_3_p = new Panel();
		
		center_2_p = new Panel();
		center_3_p = new Panel();
		
		center_1_p.setLayout(new GridLayout(4,1,1,1));
		center_1_p.add(null_la1[3]);
		center_1_p.add(userName_la);
		center_1_p.add(userBankNum_la);
		center_1_p.add(userCash_la);
		center_2_p.setLayout(new GridLayout(1,2,4,4));
		center_2_p.add(one_bu);center_2_p.add(two_bu);
		center_3_p.setLayout(new GridLayout(1,2,4,4));
		center_3_p.add(three_bu);center_3_p.add(four_bu);
		
		
		center_p.setLayout(new GridLayout(3,1,4,4));
		center_p.add(center_1_p);
		center_p.add(center_2_p);
		center_p.add(center_3_p);
		
		
		south_1_p.setLayout(new FlowLayout());
		south_1_p.add(main_bu);
		south_1_p.add(user_bu);
		south_1_p.add(login_bu);
		south_p.setLayout(new GridLayout(2,1,2,2));
		south_p.add(south_1_p);
		south_p.add(null_la1[2]);
		
		// 메인
		this.setLayout(new BorderLayout());
		
		this.add(logo_la, "North");
		this.add(null_la1[0], "East");
		this.add(center_p, "Center");
		this.add(null_la1[1], "West");
		this.add(south_p, "South");
		
		
	}
	
	
	
	public void getOpenLogin() {
		
		// 데이터베이스 테이블 중 LOGIN에서 "on"을 불러온다.
		memIndex = dbLoard(mem);
		loginBuf = loginSearching(mem);
		loginCheck = loginBuf[0];
		memIndex = loginBuf[1];
		
		// 데이터를 정상정으로 불러오면 이 조건문에 들어가 자료정보를 입력한다.
		if(loginCheck == 1) {
			login_bu.setLabel("logout");
			userName_la.setText(mem.get(memIndex).getName() + "님 환영합니다.");
			userBankNum_la.setAlignment(Label.LEFT);
			userBankNum_la.setText("계좌번호 : "+ mem.get(memIndex).getBankNum());
			userCash_la.setText("금액 : " + mem.get(memIndex).getCash() + "원");
		}
		
		
		login_bu.addActionListener(this);
		main_bu.addActionListener(this);
		user_bu.addActionListener(this);
		
		one_bu.addActionListener(this);
		two_bu.addActionListener(this);
		three_bu.addActionListener(this);
		four_bu.addActionListener(this);
		
		this.setSize(300, 450);
		this.setResizable(false);
		this.setVisible(true);
		
		//========== 프레임 위치 가운데로 고정
		this.setLocationRelativeTo(null);
		
		// 창 닫는 메소드
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				//창을 닫을때, 강제 로그아웃.
				loginCheck = 0;
				dbSave(mem,memIndex,"0");
				System.exit(0);
			}
		});	 
	}
	
	
	// 다음페이지 넘길 때, 사용되는 메소드
	//=======================================================
	
	// 두번째 페이지
	public void setMyinfo() {
		
		pageButton = 1;
		
		one_bu.setLabel("회원정보");
		two_bu.setLabel("고객센터");
		three_bu.setLabel("공사중");
		four_bu.setLabel("공사중");
		
	}
	
	// 첫번쨰 페이지
	public void setHome() {
		
		pageButton = 0;
		
		one_bu.setLabel("입금");
		two_bu.setLabel("출금");
		three_bu.setLabel("이체");
		four_bu.setLabel("내역");
		
	}
	
	//=======================================================
	
	// 로그인 모드
	public int setLogin(ArrayList<BankUserInfo> data) {
		
		login_bu.setLabel("logout");
		userName_la.setText(mem.get(memIndex).getName() + "님 환영합니다.");
		userBankNum_la.setAlignment(Label.LEFT);
		userBankNum_la.setText("계좌번호 : "+ mem.get(memIndex).getBankNum());
		userCash_la.setText("금액 : " + mem.get(memIndex).getCash() + "원");		
		
		return 0;
	}
	
	// 로그아웃 모드
	public int setLogout() {
		
		login_bu.setLabel("login");
		userName_la.setText("");
		userBankNum_la.setAlignment(Label.CENTER);
		userBankNum_la.setText("로그인 해주세요");
		userCash_la.setText("");		
		
		return 0;
	}
	
	
	//=======================================================
	
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
				//System.out.print(data.get(i).getName() + "님");
				break;
			}
		}
		
		return buf;
	}
	
	
	//=======================================================

	// 데이터베이스에서 정보 읽어오기
	public int dbLoard(ArrayList<BankUserInfo> data) {
		
		// 카운터 및 오류 검사 버퍼.
		int buf = 0;
		
		// 전체 데이터를 불러온다.
		String sql = "select * from BANKDB";
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBselect(sql,data);
		
		if(buf == 0) System.out.println("dataBase error");
		
		return buf; 
		
	}
	
	
	// 데이터베이스로 정보 저장하기
	public int dbSave(ArrayList<BankUserInfo> data, int index, String choice) {
		
		// 세이브 유무 버퍼
		int buf = 0;
		String str;	
		
		// 로그인 상태에서 다음 클래스로 이동시, 입금 : 1, 출금 : 2, 이체 : 3으로 메모리 공간에 값을 저장시킨다.
		if(loginCheck == 1) {
			str = "UPDATE BANKDB SET MEMORY='"+choice+"', MEMORY_PAY=0 WHERE ID='"+mem.get(index).getId()+"'";
		}
		else {
			str = "UPDATE BANKDB SET LOGIN='off', CASH='"+mem.get(index).getCash()+"',";
			str += "memory='0', memory_pay=0, LAST_UPDATE=NOW() WHERE ID='"+mem.get(index).getId()+"'";
		}
		
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBupdate(str);
		
		if(buf == 0) System.out.println("save error");
		
		return buf;
		
	}
	
	
	//========================================================
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == login_bu) {
			// 로그인 상태.
			if(loginCheck == 1) {
				loginCheck = 0;
				dbSave(mem,memIndex,"0");
				int buf = setLogout();
				memIndex = 0;
			}
			else {
				log_cl = new BankLogin();
				log_cl.setVisible(true);
				setVisible(false);
				dispose();
			}
		}
		else if(obj == user_bu) {
				setMyinfo();
		}
		else if(obj == main_bu) {
				setHome();
		}
		else if(obj == one_bu) {
			// home
			if(pageButton == 0) {
				if(loginCheck == 1) {
					dbSave(mem,memIndex,"1");
					bkInput_cl = new BankCashIo();
					bkInput_cl.setVisible(true);
					setVisible(false);
					dispose();
				} 
				else {
					System.out.println("login..");
				}
			}// myinfo // 회원정보 버튼
			else {
				if(loginCheck == 1) {
					bankUer_cl = new BankUser(); 
					bankUer_cl.setVisible(true);
					this.setVisible(false);
					this.dispose();
				}
				else {
					System.out.println("login..");
				}
			}
		}
		else if(obj == two_bu) {
			if(pageButton == 0) {
				if(loginCheck == 1) {
					dbSave(mem,memIndex,"2");
					bkInput_cl = new BankCashIo();
					bkInput_cl.setVisible(true);
					setVisible(false);
					dispose();
				}
				else {
					System.out.println("login..");
				}
			}
		}
		else if(obj == three_bu) {
			if(pageButton == 0) {
				if(loginCheck == 1) {
					dbSave(mem,memIndex,"3");
					 bankT_cl = new BankTranfer();
					 bankT_cl.setVisible(true);
					 setVisible(false);
					 dispose();
				}
			}
		}
		else if(obj == four_bu) {
			if(pageButton == 0) {
				if(loginCheck == 1) {
					 bkbd_cl = new bankBreakDown();
					 bkbd_cl.setVisible(true);
					 setVisible(false);
					 dispose();
				}
			}
			
		}
		
	}
	
	
	
	
	//=======================================================
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BankMenu();
	}




}
