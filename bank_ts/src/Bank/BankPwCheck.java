package Bank;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

//import bank.BankCashIo;
import java.awt.*;

// 입금, 출금, 계좌 이체 등을 마지막에 체크하고 관리
public class BankPwCheck extends Frame implements ActionListener,KeyListener,MouseListener{

	// ArrayList
	private ArrayList<BankUserInfo> mem = new ArrayList<>();
			
	
	// database
	BankDateBase db_cl = new BankDateBase();
	BankMenu bm_cl;	
	BankCashIo bci_cl;
	BankCashLastOperation bclo_cl;
	
	// component
	Label logo_la;
	private Label[] null_la1 = new Label[10];
	Label userBankNum_la;
	Label userCash_la;
	Label userPw_la;
	Label userMessage_la;

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
	
	
	//
	String pwMax;
	private int loginCheck = 0;
	private int pageButton = 0;
	private int memIndex = 0;
	private int[] loginBuf = new int[2];
	private int pwCheck = 0;
	
	public BankPwCheck() {
		setFieldInit();
		setLayoutInit();
		getOpenLogin();
	}
	
	
	public void setFieldInit() {
		
		logo_la = new Label("------------ Bk Bank ------------",Label.CENTER);
		userBankNum_la = new Label("계좌번호:");
		userCash_la = new Label("금액:");
		userPw_la = new Label("계좌 비밀번호 4자리를 입력해주세요");
		userMessage_la = new Label("",Label.CENTER);
		userPw_tx = new TextField(4);
		
		userBankNum_la = new Label("계좌번호:");
		userCash_la = new Label("금액:");
		
		next_bu = new Button("다음");
		back_bu = new Button("뒤로");
		bankPwCheck_bu = new Button("체크");
		
		// 빈창 초기화
		for(int i = 0; i < null_la1.length; i++) {
			this.null_la1[i] = new Label("");
		}
		

		logo_la.setFont(new Font("굴림체",Font.BOLD,20));
		userPw_tx.setEchoChar('*');
		bankPwCheck_bu.setPreferredSize(new Dimension(100,25));
		
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
		
			
		center_1_1_p.setLayout(new GridLayout(3,1));
		center_1_1_p.add(null_la1[2]);
		center_1_1_p.add(userBankNum_la);
		center_1_1_p.add(userCash_la);
		
		center_1_2_p.setLayout(new GridLayout(3,1,6,6));
		
		center_1_2_p.add(null_la1[3]);
		center_1_2_p.add(userPw_la);
		center_1_2_p.add(userPw_tx);
		
		center_1_2_1_p.setLayout(new FlowLayout());
		center_1_2_1_p.add(bankPwCheck_bu);
		
		center_2_p.setLayout(new FlowLayout());
		center_2_p.add(back_bu);
		center_2_p.add(next_bu);
		
		center_p.setLayout(new GridLayout(5,1,2,2));
		center_p.add(center_1_1_p);
		center_p.add(center_1_2_p);
		center_p.add(center_1_2_1_p);
		center_p.add(userMessage_la);
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
		bankPwCheck_bu.addActionListener(this);
		userPw_tx.addKeyListener(this);
		userPw_tx.addMouseListener(this);
		
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
	
	
	// 계좌번호 찾기.
	
	public int[] bankNumsearch(ArrayList<BankUserInfo> data) {
		
		int[] buf = new int[2];
		String str = "이체";
		
		// 1이면 찾은거, 0이면 못찾은거
		for(int i = 0; i < data.size(); i++) {
			if(str.equals(data.get(i).getMemory())) {
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
		
		if(mem.get(memIndex).getMemory().equals("3")) {
			if(loginCheck == 0) {
				str = "UPDATE BANKDB SET LOGIN='off', memory= '0', memory_pay= 0,";
				str += "LAST_UPDATE=NOW() WHERE ID='"+mem.get(index).getId()+"'";
			}
			else {
				str = "UPDATE BANKDB SET LOGIN='off', memory= '0', memory_pay= 0,";
				str += "LAST_UPDATE=NOW() WHERE ID='"+mem.get(index).getId()+"'";
			}
		}
		else {
			if(loginCheck == 0) {
				str = "UPDATE BANKDB SET LOGIN='off', memory= '0', memory_pay= 0,";
				str += "LAST_UPDATE=NOW() WHERE ID='"+mem.get(index).getId()+"'";
			}
		}
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBupdate(str);
		
		if(buf == 0) System.out.println("save error");
		
		return buf;
		
	}
	//================================================
	// 계좌비밀번호 비교해서 체크 메소드
	//================================================
	
	public int bankPwCheck(String pw, ArrayList<BankUserInfo> data) {
		
		// 패스워드 비교
		if(pw.equals(data.get(memIndex).getBankPw())) {
			return 1;
		}
		else return 0;
		
	}
	
	

	
	//================================================
	// 메인
	//================================================

	public static void main(String[] args) {
		new BankPwCheck();
	}

	
	//================================================
	// 이벤트
	//================================================
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		
		if(obj == userPw_tx) {
			userPw_tx.setEchoChar((char)0);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj == userPw_tx) {
			userPw_tx.setEchoChar('*');
		}
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		
		int max = 4;

		if(userPw_tx.getText().length() >= max) {
			if(userPw_tx.getText().length() == max) {
				pwMax = userPw_tx.getText();
			}else userPw_tx.setText(pwMax);
		}
		else if(userPw_tx.getText().length() > max) {
			e.consume();
		}
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		int buf;
		
		if(obj == bankPwCheck_bu) {
			
			buf = bankPwCheck(userPw_tx.getText(),mem);
			if(buf != 0) {
				userMessage_la.setForeground(new Color(0,0,255));
				userMessage_la.setText("비밀번호가 일치 합니다.");
				pwCheck = 1;
			}else {
				userMessage_la.setForeground(new Color(255,0,0));
				userMessage_la.setText("비밀번호가 틀립니다.");
				pwCheck = 0;
			}
		}
		else if(obj == back_bu) {
			
			
			if(mem.get(memIndex).getMemory().equals("1")) {
				bci_cl = new BankCashIo();
				bci_cl.setVisible(true);
				this.setVisible(false);
				this.dispose();
			}
			else if(mem.get(memIndex).getMemory().equals("2")) {
				bci_cl = new BankCashIo();
				bci_cl.setVisible(true);
				this.setVisible(false);
				this.dispose();				
			}
			else if(mem.get(memIndex).getMemory().equals("3")) {
				bci_cl = new BankCashIo();
				bci_cl.setVisible(true);
				this.setVisible(false);
				this.dispose();				
			}				
			

		}
		else if(obj == next_bu) {
			
			if(pwCheck == 1) {
				if(mem.get(memIndex).getMemory().equals("1")) {
					bclo_cl = new BankCashLastOperation();
					bclo_cl.setVisible(true);
					this.setVisible(false);
					this.dispose();
				}	
				else if(mem.get(memIndex).getMemory().equals("2")) {
					bclo_cl = new BankCashLastOperation();
					bclo_cl.setVisible(true);
					this.setVisible(false);
					this.dispose();					
				}
				else if(mem.get(memIndex).getMemory().equals("3")) {
					bclo_cl = new BankCashLastOperation();
					bclo_cl.setVisible(true);
					this.setVisible(false);
					this.dispose();						
				}
			}
			else {
				userMessage_la.setForeground(new Color(255,0,0));
				userMessage_la.setText("비밀번호 체크를 완료해주세요");
			}
			
		}
		
	}
	
	


	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {}
	

}
