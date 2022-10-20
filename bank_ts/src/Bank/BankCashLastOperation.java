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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class BankCashLastOperation extends Frame implements ActionListener{

	
	// ArrayList
	private ArrayList<BankUserInfo> mem = new ArrayList<>();
		
	// database
	BankDateBase db_cl = new BankDateBase();
	BankPwCheck bpc_cl;
	BankMenu bm_cl;	
	
	// Filed
	private int loginCheck = 0;
	private int pageButton = 0;
	private int memIndex = 0;
	private int[] loginBuf = new int[2];
	private String number = "";
	
	// component
	Label logo_la;
	private Label[] null_la1 = new Label[10];
	Label userBankNum_la;
	Label userCash_la;
	Label userCashInput_la;
	Label lastCheck_la;
	
	Label[] text_la = new Label[4]; 
	
	Button check_bu;
	Button close_bu;
	
	// Panel
	Panel center_p;
	Panel center_1_1_p;
	Panel center_1_2_p;
	Panel center_1_2_1_p;
	Panel center_2_p;
	Panel south_p;
	
	
	// 계좌이체를 위해.
	int subeIndex = 0;  
	int subeCash = 0;
	int memoryCash = 0;
	
	public BankCashLastOperation() {
		setFieldInit();
		setLayoutInit();
		getOpenLogin();
	}
	
	
	public void setFieldInit() {
		
		logo_la = new Label("------------ Bk Bank ------------",Label.CENTER);
		check_bu = new Button("보내기");
		close_bu = new Button("취소");
		userBankNum_la = new Label("계좌번호:");
		userCash_la = new Label("금액:");
		lastCheck_la = new Label("이대로 진행 하시겠습니까?",Label.CENTER);
		
		// 빈창 초기화
		for(int i = 0; i < null_la1.length; i++) {
			this.null_la1[i] = new Label("");
		}
		
		
		for(int i = 0; i < text_la.length; i++) {
			this.text_la[i] = new Label("");
			this.text_la[i].setBackground(new Color(215,215,215));
		}
		
		
		logo_la.setFont(new Font("굴림체",Font.BOLD,20));

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
		
		center_1_2_p.setLayout(new GridLayout(3,1));
		center_1_2_p.add(text_la[0]);
		center_1_2_p.add(text_la[1]);
		center_1_2_p.add(text_la[2]);
		
		center_2_p.setLayout(new FlowLayout());
		center_2_p.add(close_bu);
		center_2_p.add(check_bu);
		
		center_p.setLayout(new GridLayout(5,1,2,2));
		center_p.add(center_1_1_p);
		center_p.add(center_1_2_p);
		center_p.add(lastCheck_la);
		center_p.add(null_la1[4]);
		center_p.add(center_2_p);
		
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
			
			loginBuf = bankNumsearch(mem);
			
			if(mem.get(memIndex).getMemory().equals("1")) lastInput(mem);
			else if(mem.get(memIndex).getMemory().equals("2")) lastOutput(mem);
			else if(mem.get(memIndex).getMemory().equals("3")) lastTranfer(mem,loginBuf[1]);
		}
		
		
		check_bu.addActionListener(this);
		
		
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
				//dbSave(mem,memIndex,0);
				//System.exit(0);
			}
		});	 
	}
	
	//================================================================
	// 상황에 따라 라벨 수정.
	//================================================================
	
	public void lastInput(ArrayList<BankUserInfo> data) {
		text_la[0].setText("입금 금액 : " + data.get(memIndex).getMemory_pay() + "원");
		text_la[1].setText("입금 계좌 : " + data.get(memIndex).getBankNum());
		text_la[2].setText("성    함 : " + data.get(memIndex).getName());
	}
	
	public void lastOutput(ArrayList<BankUserInfo> data) {
		text_la[0].setText("출금 금액 : " + data.get(memIndex).getMemory_pay() + "원");
		text_la[1].setText("출금 계좌 : " + data.get(memIndex).getBankNum());
		text_la[2].setText("성    함 : " + data.get(memIndex).getName());
	}
	
	public void lastTranfer(ArrayList<BankUserInfo> data, int index) {
		text_la[0].setText("이체 금액 : " + data.get(memIndex).getMemory_pay() + "원");
		text_la[1].setText("이체 계좌 : " + data.get(index).getBankNum());
		text_la[2].setText("성    함 : " + data.get(index).getName());
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
	
	public int[] bankNumsearch(ArrayList<BankUserInfo> data) {
		
		int[] buf = new int[2];
		String str = "이체";
		
		// 1이면 찾은거, 0이면 못찾은거
		for(int i = 0; i < data.size(); i++) {
			if(str.equals(data.get(i).getMemory())) {
				buf[0] = 1;
				buf[1] = i;
				System.out.print("success");
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
		String str = "";
		//String str = "UPDATE BANKDB SET login='on'  WHERE ID='"+id+"'";	
		
		if(loginCheck == 1) {
			
			if(mem.get(memIndex).getMemory().equals("1")) {
				str = "INSERT INTO BANKBREAKD(";
				str += "INPUT_BANKNUMBER, OUTPUT_BANKNUMBER, Date, cash, Total_cash, Transaction, usingname)";
				str += "VALUE('"+ mem.get(index).getBankNum() +"','"+ mem.get(index).getBankNum() +"',NOW(),'"+ mem.get(index).getMemory_pay() +"', ";
				str += "'"+ cash +"','입금', '"+ mem.get(index).getName() +"')";		
			}
			else if(mem.get(memIndex).getMemory().equals("2")) {
				str = "INSERT INTO BANKBREAKD(";
				str += "INPUT_BANKNUMBER, OUTPUT_BANKNUMBER, Date, cash, Total_cash, Transaction, usingname)";
				str += "VALUE('"+ mem.get(index).getBankNum() +"','"+ mem.get(index).getBankNum() +"',NOW(),'"+ mem.get(index).getMemory_pay() +"', ";
				str += "'"+ cash +"','출금', '"+ mem.get(index).getName() +"')";							
			}
		
			db_cl.setDBdriveInit();
			buf = db_cl.setDBupdate(str);
			
			str = "UPDATE BANKDB SET CASH='"+cash+"',";
			str += "LAST_UPDATE=NOW(), memory= '0', memory_pay= 0 WHERE ID='"+mem.get(index).getId()+"'";
		}
		else{
			
			str = "UPDATE BANKDB SET LOGIN='off', memory= '0', memory_pay= 0,";
			str += "LAST_UPDATE=NOW() WHERE ID='"+mem.get(index).getId()+"'";
			
		}
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBupdate(str);
		

		if(buf == 0) System.out.println("save error");
		
		return buf;
		
	}
	
	//transferDbSave(mem,memIndex,loginBuf[1],cashbuf,cash);
	
	
	public int transferDbSave(ArrayList<BankUserInfo> data, int index, int subIndex, int cash, int subcash) {
		
		int buf = 0;
		String str = "";
		
		if(loginCheck == 1) {	
		
			str = "UPDATE BANKDB SET CASH='"+cash+"',";
			str += "LAST_UPDATE=NOW(), memory= '0', memory_pay= 0 WHERE ID='"+mem.get(index).getId()+"'";
			
			db_cl.setDBdriveInit();
			buf = db_cl.setDBupdate(str);
			
			str = "UPDATE BANKDB SET CASH='"+subcash+"',";
			str += "LAST_UPDATE=NOW(), memory= '0', memory_pay= 0 WHERE ID='"+mem.get(subIndex).getId()+"'";		

			db_cl.setDBdriveInit();
			buf = db_cl.setDBupdate(str);
			
		}
		else {
			
			str = "UPDATE BANKDB SET LAST_UPDATE=NOW(), memory= '0', memory_pay= 0 WHERE ID='"+mem.get(index).getId()+"'";
			
			db_cl.setDBdriveInit();
			buf = db_cl.setDBupdate(str);
			
			str = "UPDATE BANKDB SET LAST_UPDATE=NOW(), memory= '0', memory_pay= 0 WHERE ID='"+mem.get(subIndex).getId()+"'";		

			db_cl.setDBdriveInit();
			buf = db_cl.setDBupdate(str);
			
		}

		
		return buf;
	}
	
	
	public int breakdownSave(ArrayList<BankUserInfo> data, int index, int subinex, int cash, int memoryCash, int subeCash) {
		
		// 세이브 유무 버퍼
		int buf = 0;
		String str = "";
		
		str = "INSERT INTO BANKBREAKD(";
		str += "INPUT_BANKNUMBER, OUTPUT_BANKNUMBER, Date, cash, Total_cash, Transaction, usingname)";
		str += "VALUE('"+ mem.get(index).getBankNum() +"','"+ mem.get(subinex).getBankNum() +"',NOW(),'"+ memoryCash +"', ";
		str += "'"+ cash +"','출금', '"+ mem.get(subinex).getName() +"')";	
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBupdate(str);
		
		str = "INSERT INTO BANKBREAKD(";
		str += "INPUT_BANKNUMBER, OUTPUT_BANKNUMBER, Date, cash, Total_cash, Transaction, usingname)";
		str += "VALUE('"+ mem.get(subinex).getBankNum() +"','"+ mem.get(index).getBankNum() +"',NOW(),'"+ memoryCash +"', ";
		str += "'"+ subeCash +"','입금', '"+ mem.get(index).getName() +"')";	
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBupdate(str);	
		
		return buf;
	}
	
	
	//=====================================================================
	
	public static void main(String[] args) {
		new BankCashLastOperation();
	
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		int cash = 0;
		
		if(obj == check_bu) {
			if(mem.get(memIndex).getMemory().equals("1")) {
				cash = mem.get(memIndex).getCash();
				cash += mem.get(memIndex).getMemory_pay();
				dbSave(mem,memIndex,cash);
				bm_cl = new BankMenu();
				bm_cl.setVisible(true);
				this.setVisible(false);
				this.dispose();
			}
			else if(mem.get(memIndex).getMemory().equals("2")){
				cash = mem.get(memIndex).getCash();
				cash -= mem.get(memIndex).getMemory_pay();
				dbSave(mem,memIndex,cash);
				bm_cl = new BankMenu();
				bm_cl.setVisible(true);
				this.setVisible(false);
				this.dispose();				
			}
			else if(mem.get(memIndex).getMemory().equals("3")){
				cash = mem.get(memIndex).getCash();
				cash -= mem.get(memIndex).getMemory_pay();
				int cashbuf = mem.get(loginBuf[1]).getCash();
				cashbuf += mem.get(memIndex).getMemory_pay();
				
				memoryCash = mem.get(memIndex).getMemory_pay();
				subeCash = cashbuf;
				subeIndex = loginBuf[1];
				
				transferDbSave(mem,memIndex,loginBuf[1],cash,cashbuf);
				
				breakdownSave(mem,memIndex,subeIndex,cash,memoryCash,subeCash);
				
				bm_cl = new BankMenu();
				bm_cl.setVisible(true);
				this.setVisible(false);
				this.dispose();					
			}
		}
		
	}

}
