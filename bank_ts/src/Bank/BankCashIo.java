package Bank;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
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
import java.util.regex.Pattern;

public class BankCashIo extends Frame implements ActionListener,KeyListener{

	// ArrayList
	private ArrayList<BankUserInfo> mem = new ArrayList<>();
	
	// database
	BankDateBase db_cl = new BankDateBase();
	BankPwCheck bpc_cl;
	BankMenu bm;	
	
	// Filed
	private int loginCheck = 0;
	private int pageButton = 0;
	private int memIndex = 0;
	private int[] loginBuf = new int[2];
	
	// component
	Label logo_la;
	private Label[] null_la1 = new Label[10];
	Label userBankNum_la;
	Label userCash_la;
	Label userCashIO_la;
	Label userMessage_la;
	
	TextField cash_tx;
	
	Button cashSum1_bu;
	Button cashSum2_bu;
	Button cashSum3_bu;
	Button cashSum4_bu;
	Button next_bu;
	Button close_bu;
	
	
	// Panel
	Panel center_p;
	Panel center_1_1_p;
	Panel center_1_2_p;
	Panel center_1_2_1_p;
	Panel center_2_p;
	Panel south_p;
	
	
	private int next_ck = 0;
	private int cashvar = 0;
	
	public BankCashIo() {
		setFieldInit();
		setLayoutInit();
		getOpenLogin();
	}
	
	
	
	public void setFieldInit() {
		
		logo_la = new Label("------------ Bk Bank ------------",Label.CENTER);
		userBankNum_la = new Label("계좌번호:");
		userCash_la = new Label("금액:");
		userCashIO_la = new Label("");
		userMessage_la = new Label("",Label.CENTER);
		cash_tx = new TextField("0",20);
		
		cashSum1_bu = new Button("+1만원");
		cashSum2_bu = new Button("+5만원");
		cashSum3_bu = new Button("+10만원");
		cashSum4_bu = new Button("+100만원");
		
		next_bu = new Button("다음");
		close_bu = new Button("취소");
		
		// 빈창 초기화
		for(int i = 0; i < null_la1.length; i++) {
			this.null_la1[i] = new Label("");
		}
		
		
		logo_la.setFont(new Font("굴림체",Font.BOLD,20));
		//cash_tx.set
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
		center_1_2_p.add(userCashIO_la);
		center_1_2_p.add(cash_tx);
		
		center_1_2_1_p.setLayout(new FlowLayout());
		center_1_2_1_p.add(cashSum1_bu);
		center_1_2_1_p.add(cashSum2_bu);
		center_1_2_1_p.add(cashSum3_bu);
		center_1_2_1_p.add(cashSum4_bu);
		
		center_2_p.setLayout(new FlowLayout());
		center_2_p.add(close_bu);
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
		
			if(mem.get(memIndex).getMemory().equals("1")) lastInput();
			else if(mem.get(memIndex).getMemory().equals("2")) lastOutput();
			else if(mem.get(memIndex).getMemory().equals("3")) lastTransfer();
		
		}
		
		cash_tx.addKeyListener(this);
		
		cashSum1_bu.addActionListener(this);
		cashSum2_bu.addActionListener(this);
		cashSum3_bu.addActionListener(this);
		cashSum4_bu.addActionListener(this);

		close_bu.addActionListener(this);
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
				loginCheck = 0;
				dbSave(mem,memIndex,cashvar);
				System.exit(0);
			}
		});	 
	}
	
	
	
	//=================================================================
	
	// 돈 입력창에서 천만원 이상 적지 못하게 검사.
	public int setCashCheck(String data) {
		
		boolean bkpwCheck = Pattern.matches("^[0-9]{1,9}$", data);
				
		if(bkpwCheck) {
			return 1;
		}else {
			
			if(data.length() == 0) return 1;
			else {
				if(data.length() > 9) {
					return 2;
				}
				
			} return 0;
			
		}
	} 
	
	
	// 메뉴얼
	//=================================================================
	
	public void lastInput() {
		userCashIO_la.setText("얼마를 입금하시겠습니까?");
	}
	
	public void lastOutput() {
		userCashIO_la.setText("얼마를 출금하시겠습니까?");
	} 
	
	public void lastTransfer() {
		userCashIO_la.setText("얼마를 이체하시겠습니까?");
	} 
	
	//=================================================================
	
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
				System.out.print("login success");
				break;
			}
		}
		
		return buf;
	}
	
	//=================================================================
	
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
	
	
	// 데이터베이스로 정보 저장하기
	public int dbSave(ArrayList<BankUserInfo> data, int index, int cash) {
		
		// 세이브 유무 버퍼
		int buf = 0;
		String str;	
		
		if(loginCheck == 1) {// 입금은 1, 출금은 2, 이체는 3으로 지정.
			str = "UPDATE BANKDB SET memory_pay ='"+cash+"' WHERE ID='"+mem.get(index).getId()+"'";
		}
		else {// 중도 창을 듣으면 전부 지워진다.
			str = "UPDATE BANKDB SET LOGIN='off', memory='0', memory_pay=0,";
			str += "LAST_UPDATE=NOW() WHERE ID='"+mem.get(index).getId()+"'";
		}
		
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBupdate(str);
		
		if(buf == 0) System.out.println("save error");
		
		return buf;
		
	}
	
	//=====================================
	// 이벤트
	//=====================================
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		String str;
		
		
		if(obj == cashSum1_bu) {
			userMessage_la.setText("");
			str = cash_tx.getText();
			if(str.equals("")) {
				cashvar = 0;
			}else cashvar = Integer.parseInt(str);	
			cashvar += 10000;
			str = String.valueOf(cashvar);
			cash_tx.setText(str);
		}
		else if(obj == cashSum2_bu) {
			userMessage_la.setText("");
			str = cash_tx.getText();
			if(str.equals("")) {
				cashvar = 0;
			}else cashvar = Integer.parseInt(str);	
			cashvar += 50000;
			str = String.valueOf(cashvar);
			cash_tx.setText(str);			
		}
		else if(obj == cashSum3_bu) {
			userMessage_la.setText("");
			str = cash_tx.getText();
			if(str.equals("")) {
				cashvar = 0;
			}else cashvar = Integer.parseInt(str);	
			cashvar += 100000;
			str = String.valueOf(cashvar);
			cash_tx.setText(str);				
		}
		else if(obj == cashSum4_bu) {
			userMessage_la.setText("");
			str = cash_tx.getText();
			if(str.equals("")) {
				cashvar = 0;
			}else cashvar = Integer.parseInt(str);	
			cashvar += 1000000;
			str = String.valueOf(cashvar);
			cash_tx.setText(str);				
		}
		else if(obj == close_bu) {	// 뒤로 갈때 1,2는 메뉴로, 3은 계좌번호조회쪽으로 이동 수정.
//			bm = new BankMenu();
//			bm.setVisible(true);
//			setVisible(false);
//			dispose();
		}
		else if(obj == next_bu) {
			 dbSave(mem,memIndex,cashvar);
			 bpc_cl = new BankPwCheck();
			 bpc_cl.setVisible(true);
			 this.setVisible(false);
			 this.dispose();
		}
		
		
		
	}
	
	
	
	//=================================================================
	
	public static void main(String[] args) {
		new BankCashIo();

	}



	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void keyReleased(KeyEvent e) {
		
		Object obj = e.getSource();
		String str = "";
		int buf = 0;
		int cashbuf = 0;
		
		if(obj == cash_tx) {
			
			// 입금
			if(mem.get(memIndex).getMemory().equals("1")) {
				str = cash_tx.getText();
				
				buf = setCashCheck(str);
				
				if(buf == 0) {
					userMessage_la.setForeground(new Color(255,0,0));
					userMessage_la.setText("숫자로 작성해주십시오");
					cash_tx.setText("");
				}
				else {
					if(buf == 1) {
						userMessage_la.setText("");	
						if(str.equals("")) {
							cashvar = 0;
						}else cashvar = Integer.parseInt(str);						
					}
					else if(buf == 2) {
						userMessage_la.setForeground(new Color(255,0,0));
						userMessage_la.setText("입금은 하루 천만원 미만까지만 가능합니다.");
						cash_tx.setText("");
					}
				}					
				
			}// 출금
			else if(mem.get(memIndex).getMemory().equals("2")) {
				str = cash_tx.getText();
				
				buf = setCashCheck(str);
				
				if(buf == 0) {
					userMessage_la.setForeground(new Color(255,0,0));
					userMessage_la.setText("숫자로 작성해주십시오");
					cash_tx.setText("");
				}
				else {
					userMessage_la.setForeground(new Color(255,0,0));
					userMessage_la.setText("");
					
					if(str.equals("")) {
						cashvar = 0;
					}else cashvar = Integer.parseInt(str);
					
					cashbuf = mem.get(memIndex).getCash();
					
					if(cashbuf-cashvar < 0) {
						userMessage_la.setForeground(new Color(255,0,0));
						userMessage_la.setText("잔액보다 출금 금액이 큽니다.");
						cash_tx.setText("");
					}else {
						userMessage_la.setText("");
					}
					
				}

			}// 이체
			else if(mem.get(memIndex).getMemory().equals("3")) {
				str = cash_tx.getText();
				
				buf = setCashCheck(str);
				
				if(buf == 0) {
					userMessage_la.setForeground(new Color(255,0,0));
					userMessage_la.setText("숫자로 작성해주십시오");
					cash_tx.setText("");
				}
				else {
					if(buf == 1) {
						userMessage_la.setText("");	
						if(str.equals("")) {
							cashvar = 0;
						}else cashvar = Integer.parseInt(str);						
					}
					else if(buf == 2) {
						userMessage_la.setForeground(new Color(255,0,0));
						userMessage_la.setText("이체은 하루 천만원 미만까지만 가능합니다.");
						cash_tx.setText("");
					}
				}
			}
			
			
			
			
		}
		
		
		
		
		
		
	}





}
