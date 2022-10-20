package Bank;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

// 로그인 창.
public class BankLogin extends Frame implements ActionListener, MouseListener,KeyListener{
	
	// ArrayList
	private ArrayList<BankUserInfo> mem = new ArrayList<>();
	
	// database
	BankDateBase db_cl = new BankDateBase();
	
	// 메뉴
	BankMenu bm_cl;
	
	// 회원가입
	BankJoin join_cl;
	
	// Component
	private Label id_la;
	private Label pw_la;
	private Label loginLogo_la;
	
	private Label loginCheck_la;
	
	private Label[] null_la = new Label[18];
	
	private Panel loginLogoText_p;
	private Panel loginInputText_p;
	private Panel south_p;
	
	//private Color main_cr;
	private TextField loginId_tx;
	private TextField loginPw_tx;
	private Button loginButton_bu;
	private Button joinButton_bu;
	private Button loginISearch_bu;
	
	private Font loginLogo_f;
	private Font loginId_f;
	private Font loginPw_f;
	
	// Layout
	private FlowLayout flow;
	private GridLayout grid;
	private BorderLayout border;
	
	private int count = 0;
	
	// 생성자
	public BankLogin() {
		setFieldInit();
		setLogoText();
		setInputText();
		setLayoutInit();
		getOpenLogin();
	}
	
	
	//===================================================================
	
	private void setFieldInit() {
		
		// Frame 초기화
		this.setTitle("login");
		
		// 컴포넌트 초기화
		id_la =  new Label("아이디      ");
		pw_la = new Label("비밀번호      ");
		loginLogo_la = new Label("BK Bank");
	
		loginId_tx = new TextField(10);
		loginPw_tx = new TextField(10);
		
		loginButton_bu = new Button("로그인");
		joinButton_bu = new Button("회원가입");
		loginISearch_bu = new Button("아이디조회 / 비밀번호변경");
		
		loginLogoText_p = new Panel();
		loginInputText_p = new Panel();
		
		loginCheck_la = new Label("",Label.CENTER);
		
		// 폰트설정.
		loginLogo_f = new Font("궁서체",Font.BOLD,45);
		loginId_f = new Font("맑은고딕",Font.PLAIN,15);
		loginPw_f = new Font("맑은고딕",Font.PLAIN,15);
		
		
		// 빈공간 라벨 초기화
		for(int i = 0; i < null_la.length; i++) {
			null_la[i] = new Label(" ");
		}		
		
		
		loginPw_tx.setEchoChar('*');
		
	}
	
	
	
	private void setLayoutInit() {
		
		// 레이아웃 초기화
		flow = new FlowLayout();
		grid = new GridLayout(12,2,7,5);//8
		border = new BorderLayout();
		south_p = new Panel();
		
		
		// 레이아웃 세팅
		loginLogoText_p.setLayout(flow);
		loginInputText_p.setLayout(grid);
		south_p.setLayout(new GridLayout(2,1));
		this.setLayout(border);
		//this.setc
		
		// 로그인 메인 로고 창.
		loginLogoText_p.add(loginLogo_la);
		
		
		// 로그인 아이디, 비밀번호 라벨 및 텍스트 창.
		loginInputText_p.add(null_la[0]);loginInputText_p.add(null_la[1]);
		loginInputText_p.add(null_la[2]);loginInputText_p.add(null_la[3]);
		loginInputText_p.add(null_la[4]);loginInputText_p.add(null_la[5]);
		
		loginInputText_p.add(id_la);loginInputText_p.add(loginId_tx);
		loginInputText_p.add(pw_la);loginInputText_p.add(loginPw_tx);
		
		loginInputText_p.add(null_la[10]);loginInputText_p.add(null_la[11]);
		loginInputText_p.add(null_la[12]);loginInputText_p.add(null_la[13]);
		
		loginInputText_p.add(loginButton_bu);loginInputText_p.add(joinButton_bu);
		
		loginInputText_p.add(null_la[14]);loginInputText_p.add(null_la[15]);
		loginInputText_p.add(null_la[16]);loginInputText_p.add(null_la[17]);
		
		
		south_p.add(loginCheck_la);
		south_p.add(loginISearch_bu);
	} 
	
	
	
	private void setLogoText() {
	
		// 로그인 메인 글 글꼴 설정
		loginLogo_la.setFont(loginLogo_f);
		
	}
	
	
	private void setInputText() {
		
		// 아이디, 패스워드 라벨 위치 설정.
		id_la.setAlignment(Label.RIGHT);
		pw_la.setAlignment(Label.RIGHT);
		
		
		// 아이디, 패스워드 텍스트필드 폰트 설정.
		loginId_tx.setFont(loginId_f);
		loginPw_tx.setFont(loginPw_f);

		
	}
	
	
	
	// 레이아웃 출력
	public void getOpenLogin() {
		
		// 데이터 베이스 자료 arrayList에 로드.
		int buf = 0;
		
		buf = dbLoard(mem);
		count = buf;		// 계좌번호에 카운팅을 위해서 회원자료 얻음.
		
		this.add(loginLogoText_p, "North");
		this.add(loginInputText_p, "Center");
		this.add(null_la[10], "West");
		this.add(null_la[11], "East");
		this.add(south_p, "South");
		
		loginId_tx.addActionListener(this);
		loginPw_tx.addActionListener(this);
		loginButton_bu.addActionListener(this);
		joinButton_bu.addActionListener(this);
		loginISearch_bu.addActionListener(this);
		

		loginPw_tx.addMouseListener(this);
		loginId_tx.addKeyListener(this);
		loginPw_tx.addKeyListener(this);
		
		
		this.setSize(300, 450);
		this.setResizable(false);
		this.setVisible(true);
	
		//========== 프레임 위치 가운데로 고정
		this.setLocationRelativeTo(null);
		
		// 창 닫는 메소드
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});	 
		
	}
	
	/**
	 * DataBase save,loard
	 */
	//============================================================================
	
//	// 데이터베이스에서 정보 읽어오기
	public int dbLoard(ArrayList<BankUserInfo> data) {
		
		// 카운터 및 오류 검사 버퍼.
		int buf = 0;
		
		String sql = "select * from BANKDB";
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBselect(sql,data);
		
		if(buf == 0) System.out.println("loading..error");
		
		return buf; 
		
	}
	

//	// 데이터베이스로 정보 저장하기
	public int dbSave(String id, String pw) {
		
		// 세이브 유무 버퍼
		int buf = 0;
		
		String str = "UPDATE BANKDB SET login='on', LAST_UPDATE=NOW()  WHERE ID='"+id+"' ";		
		
		db_cl.setDBdriveInit();
		buf = db_cl.setDBupdate(str);
		
		if(buf == 0) System.out.println("save error");
		
		return buf;
		
	}
	
	//============================================================================
	
	// 회원 아이디 찾는 메서드
	public int idSearching(String id, String pw, ArrayList<BankUserInfo> data) {
		
		int buf = 0;
		
		
		// 1이면 찾은거, 0이면 못찾은거
		for(int i = 0; i < data.size(); i++) {
			if(id.equals(data.get(i).getId()) && pw.equals(data.get(i).getPw())) {
				buf = 1;
				System.out.print("login success");
				break;
			}
		}
		
		return buf;
	}
	
	
	
	//============================================================================

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == loginButton_bu) {
			
			int buf = idSearching(loginId_tx.getText(),loginPw_tx.getText(),mem);
			
			if(buf != 1) {
				System.out.println("ID and passwoard error");
				loginCheck_la.setForeground(new Color(255,0,0));
				loginCheck_la.setText("아이디와 비밀번호가 틀립니다");
			}
			else {
				System.out.println("login success");
				int save_ck = dbSave(loginId_tx.getText(),loginPw_tx.getText());
				if(save_ck != 1)System.out.println("save error");
				else {
					bm_cl = new BankMenu();
					setVisible(false);
					dispose();
					bm_cl.setVisible(true);
					System.out.println("save success");
				}
			}
			
		}
		else if(obj == joinButton_bu) {
			join_cl = new BankJoin();
			setVisible(false);
			dispose();
			join_cl.setVisible(true);
			//System.out.println("회원가입");
		}
		else if(obj == loginISearch_bu) {
			//System.out.println("아이디찾기");
		}
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BankLogin();

	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj == loginPw_tx) {
			loginPw_tx.setEchoChar((char)0);
		}
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj == loginPw_tx) {
			loginPw_tx.setEchoChar('*');
		}
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
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		
		if(obj == loginId_tx) {
			
			String str = loginId_tx.getText();
			
			if(str.equals("")) {
				loginCheck_la.setText("");
			}
			
		}
		else if(obj == loginPw_tx) {
			
			String str = loginPw_tx.getText();
			
			if(str.equals("")) {
				loginCheck_la.setText("");
			}			
			
		}
		
		
	}



}
