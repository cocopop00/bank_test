package Bank;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class BankTranfer extends Frame implements ActionListener,KeyListener{

	// ArrayList
	private ArrayList<BankUserInfo> mem = new ArrayList<>();
	
	// database
	BankDateBase db_cl = new BankDateBase();
	BankMenu bm_cl;
	BankCashIo bci_cl;
	
	// component
	Label logo_la;
	private Label[] null_la1 = new Label[10];
	Label userBankNum_la;
	Label userCash_la;
	Label userBanknum_la;
	Label userMessage_la;
	Label userMessage2_la;
	
	TextField userBanknum_tx;
	
	Button cashSum1_bu;
	Button cashSum2_bu;
	Button cashSum3_bu;
	Button cashSum4_bu;
	Button next_bu;
	Button back_bu;
	Button bankNumCheck_bu;
	
	// Panel
	Panel center_p;
	Panel center_1_1_p;
	Panel center_1_2_p;
	Panel center_1_2_1_p;
	Panel center_2_p;
	Panel center_4_p;
	Panel south_p;
	
	String pwMax;
	private int loginCheck = 0;
	private int pageButton = 0;
	private int memIndex = 0;
	private int[] loginBuf = new int[2];
	private int bankNumCheck = 0;
	
	public BankTranfer() {
		setFieldInit();
		setLayoutInit();
		getOpenLogin();
	}
	
	
	
	public void setFieldInit() {
		
		logo_la = new Label("------------ Bk Bank ------------",Label.CENTER);
		userBankNum_la = new Label("계좌번호:");
		userCash_la = new Label("금액:");
		userBanknum_la = new Label("이체 하실 계좌번호를 입력해주세요.");
		userMessage_la = new Label("",Label.CENTER);
		userMessage2_la = new Label("",Label.CENTER);
		userBanknum_tx = new TextField(15);
		
		userBankNum_la = new Label("계좌번호:");
		userCash_la = new Label("금액:");
		
		next_bu = new Button("다음");
		back_bu = new Button("뒤로");
		bankNumCheck_bu = new Button("계좌번호 체크");
		
		// 빈창 초기화
		for(int i = 0; i < null_la1.length; i++) {
			this.null_la1[i] = new Label("");
		}
		

		logo_la.setFont(new Font("굴림체",Font.BOLD,20));
		bankNumCheck_bu.setPreferredSize(new Dimension(100,25));		
		
		
	}
	
	public void setLayoutInit() {
		
		center_p = new Panel();
		center_1_1_p = new Panel();
		center_1_2_p = new Panel();
		center_1_2_1_p = new Panel();
		center_2_p =  new Panel();
		center_4_p = new Panel();
		south_p = new Panel();
		
		// 메인
		setLayout(new BorderLayout());
		
			
		center_1_1_p.setLayout(new GridLayout(3,1));
		center_1_1_p.add(null_la1[2]);
		center_1_1_p.add(userBankNum_la);
		center_1_1_p.add(userCash_la);
		
		center_1_2_p.setLayout(new GridLayout(3,1,6,6));
		
		center_1_2_p.add(null_la1[3]);
		center_1_2_p.add(userBanknum_la);
		center_1_2_p.add(userBanknum_tx);
		
		center_1_2_1_p.setLayout(new FlowLayout());
		center_1_2_1_p.add(bankNumCheck_bu);
		
		center_2_p.setLayout(new FlowLayout());
		center_2_p.add(back_bu);
		center_2_p.add(next_bu);
		
		center_4_p.setLayout(new GridLayout(2,1));
		center_4_p.add(userMessage_la);
		center_4_p.add(userMessage2_la);
		
		center_p.setLayout(new GridLayout(5,1,2,2));
		center_p.add(center_1_1_p);
		center_p.add(center_1_2_p);
		center_p.add(center_1_2_1_p);
		center_p.add(center_4_p);
		center_p.add(center_2_p);
		
		
		south_p.setLayout(new GridLayout(2,1));
		south_p.add(null_la1[8]);
		south_p.add(null_la1[9]);
		
		// 메인 add
		add(logo_la, "North");
		add(null_la1[0], "West");
		add(null_la1[1], "East");
		add(center_p, "Center");
		
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
		}
		
		
		next_bu.addActionListener(this);
		back_bu.addActionListener(this);
		bankNumCheck_bu.addActionListener(this);
		
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
				dbSave(mem,memIndex);
				System.exit(0);
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
	
	
	
	// 이체 시킬 데이터 값의 메모리를 저장한다.
	public int setNextdbSave(ArrayList<BankUserInfo> data, int index, String var) {
		
		int buf = 0;
		String str = "";
		
		str = "UPDATE BANKDB SET memory= '"+ var +"' WHERE ID='"+mem.get(index).getId()+"'";		
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBupdate(str);	
		
		
		return buf;
		
	}
	
	//================================================
	// 계좌비밀번호 비교해서 체크 메소드
	//================================================
	
	// 사용자가 적은 계좌번호를 입력해서 List에 있는지 비교
	public int[] bankNumCheck(String num, ArrayList<BankUserInfo> data) {
		
		int[] buf = new int[2];
		
		// 1이면 찾은거, 0이면 못찾은거
		for(int i = 0; i < data.size(); i++) {
			if(num.equals(data.get(i).getBankNum())) {
				buf[0] = 1;
				buf[1] = i;
				break;
			}
		}
		
		return buf;
		
	}

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BankTranfer();
	}

	
	//===================================
	// 이벤트
	//===================================
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		String str;
		int buf;
		
		if(obj == bankNumCheck_bu) {
			
			str = userBanknum_tx.getText();
			loginBuf = bankNumCheck(str,mem);
			
			if(loginBuf[0] == 0) {
				bankNumCheck = 0;
				userMessage_la.setForeground(new Color(255,0,0));
				userMessage_la.setText("존재하지 않는 번호입니다.");
				userMessage2_la.setText("");
			}
			else {
				bankNumCheck = 1;
				userMessage_la.setForeground(new Color(0,0,255));
				userMessage_la.setText(mem.get(loginBuf[1]).getName() + "님" + mem.get(loginBuf[1]).getBankNum());
				
			}
			
		}
		else if(obj == next_bu) {
			buf = setNextdbSave(mem,loginBuf[1],"이체");
			if(buf == 0) userMessage_la.setText("저장오류 발생");
			else {
				bci_cl = new BankCashIo();
				bci_cl.setVisible(true);
				setVisible(false);
				dispose();				
			}
		}
		else if(obj == back_bu) {
			bm_cl = new BankMenu();
			bm_cl.setVisible(true);
			setVisible(false);
			dispose();
		}
		
	}
	
	

}
