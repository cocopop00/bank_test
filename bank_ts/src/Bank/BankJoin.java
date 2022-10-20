package Bank;

import java.awt.BorderLayout;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;


// 회원가입창
public class BankJoin extends Frame implements ActionListener,KeyListener,MouseListener{

	// ArrayList
	private ArrayList<BankUserInfo> mem = new ArrayList<>();
	
	// Data base class
	BankDateBase db_cl = new BankDateBase();
	BankLogin login_cl;
	
	
	// Component
	private Label logo_la;
	private Label name_la;
	private Label id_la;
	private Label idCk_la;
	private Label pw_la;
	private Label pw2_la;
	private Label bankPw_la;
	private Label[] null_la1 = new Label[10];
	private Label[] null_la2 = new Label[50];
	private Label phoneNum_la;
	private Label pwcheck_la;
	private Label bankJoinCheck_la;
	private Label residentNum_la;
	
	
	private Button pw_bu;
	private Button id_bu;
	private Button join_bu;
	
	private TextField name_tx;
	private TextField id_tx;
	private TextField pw_tx;
	private TextField pw2_tx;	
	private TextField bankPw_tx;
	private TextField phoneNum_tx;
	private TextField residentNum1_tx;
	private TextField residentNum2_tx;
	
	// Variable
	private int pwButton_chack = 0;
	private int idButton_chack = 0;
	private int name_ck = 0;
	private int id_ck = 0;
	private int pw_ck = 0;
	private int pw2_ck = 0;
	private int bankPw_ck = 0;
	private int phoneNum_ck = 0;
	private int residentNum1_ck = 0;
	private int residentNum2_ck = 0;
	
	// 자동계좌 카운트
	private int count = 0;
	
	//Panel	north_p;
	Panel	leftNum1_p;
	Panel	leftNum2_p;
	Panel	center_p;
	Panel	rightNum1_p;
	Panel	rightNum2_p;
	Panel	south_p;
	Panel	southNull_p;
	Panel	residentNum_p;
	
	KeyListener listener;
	
	
	public BankJoin() {
		setJoinInit();
		setJoinLayout();
		getJoinLayout();
	}
	
	
	private void setJoinInit() {
		
		// component
		this.logo_la = new Label("회원가입",Label.CENTER);
		this.name_la = new Label("성함",Label.LEFT);
		this.id_la =  new Label("아이디",Label.LEFT);
		this.idCk_la = new Label("[ID 체크 해주세요]");
		this.pw_la = new Label("비밀번호",Label.LEFT);
		this.pw2_la = new Label("비밀번호 재확인",Label.LEFT);
		this.bankPw_la = new Label("계좌비밀번호(4자리)",Label.LEFT);
		this.phoneNum_la = new Label("전화번호",Label.LEFT);
		this.pwcheck_la = new Label("[비밀번호 확인버튼 누르세요]");
		this.bankJoinCheck_la = new Label("",Label.CENTER);
		this.residentNum_la = new Label("주민등록번호",Label.LEFT);
		
		this.name_tx = new TextField(10);
		this.id_tx = new TextField(10);
		this.pw_tx = new TextField(10);
		this.pw2_tx = new TextField(10);
		this.bankPw_tx = new TextField(10);
		this.phoneNum_tx = new TextField("010-",10);
		
		this.residentNum1_tx = new TextField(10);
		this.residentNum2_tx = new TextField(10);
		
		this.pw_bu = new Button("PW확인");
		this.id_bu = new Button("ID확인");
		this.join_bu = new Button("가입하기");
		
		// 빈창 초기화
		for(int i = 0; i < null_la1.length; i++) {
			this.null_la1[i] = new Label("");
		}
		
		// 빈창 초기화
		for(int i = 0; i < null_la2.length; i++) {
			this.null_la2[i] = new Label("");
		}		
		
		//=================================
		// 비밀번호 확인 버튼과 로고 폰트 변경.
		logo_la.setFont(new Font("맑은고딕",Font.PLAIN,20));
		
		// 가입버튼 눌렀을때, 문제 있을 시 나오는 문구 글씨 색상 변경(빨강)
		bankJoinCheck_la.setForeground(new Color(255,0,0));
		
		// 비밀번호 가리기
		pw_tx.setEchoChar('*');
		pw2_tx.setEchoChar('*');
		residentNum2_tx.setEchoChar('*');
		bankPw_tx.setEchoChar('*');
		
	}
	
	
	private void setJoinLayout() {
		
		// Panel 초기화
		leftNum1_p = new Panel();
		leftNum2_p = new Panel();
		rightNum1_p = new Panel();
		rightNum2_p = new Panel();	
		center_p = new Panel();
		south_p = new Panel();
		southNull_p = new Panel();
		residentNum_p = new Panel();
		
		// SetLayout 초기화
		center_p.setLayout(new GridLayout(1,2));
		leftNum1_p.setLayout(new BorderLayout());
		leftNum2_p.setLayout(new GridLayout(16,1,3,3));
		rightNum1_p.setLayout(new BorderLayout());
		rightNum2_p.setLayout(new GridLayout(16,3,3,3));
		south_p.setLayout(new FlowLayout());
		southNull_p.setLayout(new GridLayout(3,1));
		residentNum_p.setLayout(new GridLayout(1,2,7,7));
		
		// 하단 가입 버튼
		south_p.add(join_bu);
		
		// 하단 그리드 생성 1층 확인체크 라벨, 2층 가입버튼, 3층 빈공간
		southNull_p.add(bankJoinCheck_la);
		southNull_p.add(south_p);
		southNull_p.add(null_la1[5]);
		
		// 중간 회원 목록
		leftNum1_p.add(leftNum2_p, "Center");
		leftNum1_p.add(null_la1[0], "West");
		
		rightNum1_p.add(rightNum2_p, "Center");

		// 주민등록번호 
		residentNum_p.add(residentNum1_tx);
		residentNum_p.add(residentNum2_tx);
		
		// left
		//leftNum2_p.add(null_la1[1]);
		leftNum2_p.add(name_la);
		leftNum2_p.add(name_tx);
		leftNum2_p.add(id_la);
		leftNum2_p.add(id_tx);
		leftNum2_p.add(idCk_la);
		leftNum2_p.add(pw_la);
		leftNum2_p.add(pw_tx);
		leftNum2_p.add(pw2_la);
		leftNum2_p.add(pw2_tx);
		leftNum2_p.add(pwcheck_la);
		
		leftNum2_p.add(residentNum_la);
		leftNum2_p.add(residentNum_p);
		leftNum2_p.add(phoneNum_la);
		leftNum2_p.add(phoneNum_tx);
		leftNum2_p.add(bankPw_la);
		leftNum2_p.add(bankPw_tx);
		
		// right
		for(int i = 0; i < (15*3); i++) {
			if(i == 10) rightNum2_p.add(id_bu);
			else if(i == 25) rightNum2_p.add(pw_bu);
			else rightNum2_p.add(null_la2[i]);
		}
		
		center_p.add(leftNum1_p);
		center_p.add(rightNum1_p);
		
		// 메인
		this.setLayout(new BorderLayout());
		this.add(center_p, "Center");
		this.add(null_la1[5], "West");
		this.add(null_la1[6], "East");
		this.add(logo_la, "North");
		this.add(southNull_p, "South");
		
	}
	
	public void getJoinLayout() {
		
		// 데이터 베이스 자료 arrayList에 로드.
		int buf = 0;
		
		buf = dbLoard(mem);
		count = buf;		// 계좌번호에 카운팅을 위해서 회원자료 얻음.
		
		// 키보드 액션
		name_tx.addKeyListener(this);
		id_tx.addKeyListener(this);
		pw_tx.addKeyListener(this);
		pw2_tx.addKeyListener(this);
		bankPw_tx.addKeyListener(this);
		phoneNum_tx.addKeyListener(this);
		
		residentNum1_tx.addKeyListener(this);
		residentNum2_tx.addKeyListener(this);
		
		
		id_bu.addActionListener(this);
		pw_bu.addActionListener(this);
		join_bu.addActionListener(this);
		
		pw_tx.addMouseListener(this);
		pw2_tx.addMouseListener(this);
		residentNum2_tx.addMouseListener(this);
		bankPw_tx.addMouseListener(this);
		
		this.setSize(400, 500);
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
	
	
	// 각 회원 정보 체크 부분.
	//=======================================================================================

	public int setNameCheck(String data) {
		
		boolean nameCheck = Pattern.matches("^[가-힣]{1,10}$|^[a-z]{1,10}$|^[A-Z]{1,10}$", data);
		
		if(nameCheck) {
			return 1;
		}else {
			if(data.length() == 0) return 1;
			return 0;
		}
		
	} 
	
	
	public int setIdCheck(String data) {
		
		char[] check;
		char[] str = {'0','1','2','3','4','5','6','7','8','9'};
		
		boolean idCheck = Pattern.matches("^[a-zA-Z]{1}[a-zA-Z0-9_]{3,12}$", data);
		System.out.println(idCheck);
		if(idCheck) {
			// toCharArray로 문자열을 배열로 변환
			check = data.toCharArray();		
			
			// 배열중에 0번째 문자가 숫자면 필터.
			for(int i = 0; i < str.length; i++) {
				if(check[0] == str[i]) {
					System.out.println("First number");
					return 2;
				}
			}
			return 1;
		}else {
			
			// 첫째자리가 특수키가 있는지 다시 확인
			boolean  specialKey = Pattern.matches("^[a-zA-Z]{1}$|^[a-zA-Z]{1}[a-zA-Z0-9_]{1,3}$|^[a-zA-Z]{1}[a-zA-Z0-9_]{13,30}$", data);
			System.out.println(specialKey);
			// 정상키
			if(specialKey) return 0;
			else {
				if(data.length() == 0) return 1;
				else return 2;			
			} 
				
			
		}	
		
	} 
	
	
	public int setPwCheck(String data) {
		
		boolean pwCheck = Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,12}$", data);
		
		if(pwCheck) {
			return 1;
		}else {
			if(data.length() == 0) return 1;
			return 0;
		}
	
	} 
	
	
	public int setPhoneCheck(String data) {
		
		boolean phoneCheck = Pattern.matches("(0\\d{1,2})-(\\d{3,4})-(\\d{4})", data);
		
		if(phoneCheck) {
			return 1;
		}else {
			if(data.length() == 0) return 1;
			return 0;
		}
			
	} 
	
	
	public int setBankPwCheck(String data) {
			
		boolean bkpwCheck = Pattern.matches("^[0-9]{4}$", data);
		
		System.out.println(bkpwCheck);
		
		if(bkpwCheck) {
			return 1;
		}else {
			if(data.length() == 0) return 1;
			return 0;
		}
		
	} 
	
	
	public int setBankResidentNum(String data, int num) {
		
		int buf = 0;
		char[] check;
		char[] str = {'0','1','2','3','4','5','6','7','8','9'};
		boolean bkRdCheck;
		
		// 앞에 6자리
		if(num == 1) {
			bkRdCheck = Pattern.matches("^[0-9]{6}$", data);
	
			if(bkRdCheck) {
				return 1;
			}else {
				if(data.length() == 0) return 1;
				return 0;
			} 
			
			
		}// 뒤에 7자리
		else {
			bkRdCheck = Pattern.matches("^[1-4]\\d{6}$", data);
			
			check = data.toCharArray();
			
			if(bkRdCheck) {
				if(check[0] == str[1] || check[0] == str[2]) {
					return 1;
				}else return 2;
			}else {
				if(data.length() == 0) return 1;
				return 0;
			} 
			
		}
		
	}
	

	
	// 데이터 베이스 파트
	//=======================================================================================

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
	public int dbSave(ArrayList<BankUserInfo> data, String[] mem) {
		
		// 세이브 유무 버퍼
		int buf = 0;
		
		String str = "INSERT INTO BANKDB(";
		str += "NAME, ID, PW, BANKNUMBER, BANKPW, PHONNUMBER, CASH, JOIN_DATE,LOGIN, RESIDENT_NUM, MEMBERSHIP)";
		str += "VALUE('"+ mem[0] +"','"+ mem[1] +"','"+ mem[2] +"','"+ mem[6] +"', ";
		str += "'"+ mem[4] +"','"+ mem[5] +"',0, NOW(),'off', '"+mem[10]+"', '일반')";
		//str[10]
		db_cl.setDBdriveInit();
		buf = db_cl.setDBupdate(str);
		
		if(buf == 0) System.out.println("save error");
		
		return buf;
		
	}
	
	
	//=======================================================================================
	
	// 회원가입 아이디 중복체크 메소드
	public int idCheck(String id, ArrayList<BankUserInfo> data) {
		
		
		for(int i = 0; i < data.size(); i++) {
			if(id.equals(data.get(i).getId())) {
				return 0;
			}
		}
		
		return 1;
	}
	
	//=======================================================================================

	// 계좌번호 자동생성 
	public String setBankNumer(int count) {
		// xxx-xxx-xxxxxx 앞에 세자리는 130으로 통합. 가운데는 1씩 카운터, 마지막 6자리는 랜덤함수.
		
		// 객체 문자열을 하나로 붙일 클래스
		StringBuffer sb = new StringBuffer();
		// 뒤에 6자리 랜덤
		Random ran = new Random();
		int[] num = new int[6];
		
		
		// 랜덤함수로 뒤에 6자리 숫자 담기.
		for(int i = 0; i < num.length; i++) {
			num[i] = (ran.nextInt(9)+1);
			for(int j = 0; j < i; j++) {
				if(num[i] == num[j]) {
					num[i] = (ran.nextInt(9)+1);
					j -= 1;
				}
			}
		}		
		
		// 랜덤 6자리를 String으로 형변환 및 숫자들을 붙이기 위한 과정
		String str = Arrays.toString(num);
		str = str.replace(",", "");
		str = str.replace("[", "");
		str = str.replace("]", "");
		str = str.replace(" ", "");
		
		// 130 고유번호, 000 회원가입순위, 000000 랜덤수
		sb.append("130");
		sb.append("-");
		if(count < 10)sb.append("00"+count);
		else if(count > 10 && count < 100) sb.append("0"+count);
		else sb.append(count);
		sb.append("-");	
		sb.append(str);	
	
		String banknumer = sb.toString();
		
		return banknumer;
		
	}
	
	
	public String residentNumSum(String num1, String num2) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append(num1);
		sb.append("-");
		sb.append(num2);
		
		String totalNum = sb.toString();
		
		return totalNum;
	}
	
	
	//=======================================================================================
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BankJoin();
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//BankLogin login = new BankLogin();
		Object obj = e.getSource();
		
		// 비밀번호 확인 버튼
		if(obj == pw_bu) {
			String pw = pw_tx.getText();
			String pw2 = pw2_tx.getText();
			//login.setVisible(false);
			
			if(pw.equals(pw2)) {
				
				if(pw_ck == 1) {
					if(pw.length() == 0 || pw2.length() == 0) {
						pwcheck_la.setText("[빈칸을 채워주세요]");
						pwcheck_la.setForeground(new Color(255,0,0));
						pwButton_chack = 0;
					}
					else {
						pwcheck_la.setText("[비밀번호 체크 확인]");
						pwcheck_la.setForeground(new Color(0,0,255));
						pwButton_chack = 1;
					}					
				}
				else {
					if(pw.length() == 0 || pw2.length() == 0) {
						pwcheck_la.setText("[빈칸을 채워주세요]");
						pwcheck_la.setForeground(new Color(255,0,0));
						pwButton_chack = 0;
					}
					else {
						pwcheck_la.setText("[조건에 맞게 입력해주세요]");
						pwcheck_la.setForeground(new Color(255,0,0));
						pwButton_chack = 0;
					}

				} 
				
			}
			else {
				if(pw.length() ==  0 || pw2.length() == 0) {
					pwcheck_la.setText("[빈칸을 채워주세요]");
					pwcheck_la.setForeground(new Color(255,0,0));
					pwButton_chack = 0;
				}
				else {
					pwcheck_la.setText("[비밀번호가 서로 틀립니다]");
					pwcheck_la.setForeground(new Color(255,0,0));
					pwButton_chack = 0;	
				}
			} 
		}// id확인 검사
		else if(obj == id_bu) {
			
			String id = id_tx.getText();
			
			if(id_ck == 1) {
				if(id.length() == 0) {
					idCk_la.setText("[빈공간을 채워주세요]");
					idCk_la.setForeground(new Color(255,0,0));
					idButton_chack = 0;
				}
				else {
					int idck = idCheck(id_tx.getText(),mem);
					if(idck != 1) {
						idCk_la.setText("[중복 ID가 있습니다]");
						idCk_la.setForeground(new Color(255,0,0));
						idButton_chack = 0;
					}
					else {
						idCk_la.setText("[사용할 수 있는 ID 입니다]");
						idCk_la.setForeground(new Color(0,0,255));
						idButton_chack = 1;
					}
				}
			}
			else {
				// 5자리 이상인지 확인
				if(id.length() == 0) {
					idCk_la.setText("[빈공간을 채워주세요]");
					idCk_la.setForeground(new Color(255,0,0));
					idButton_chack = 0;
				}
				else if(id.length() <= 4) {
					idCk_la.setText("[4이상 자리로 늘려주세요]");
					idCk_la.setForeground(new Color(255,0,0));
					idButton_chack = 0;
				}
				else if(id.length() > 10) {
					idCk_la.setText("[12이하 자리로 줄여주세요]");
					idCk_la.setForeground(new Color(255,0,0));
					idButton_chack = 0;
				}
			}
		}// 회원가입 버튼
		else if(obj == join_bu) {
			// 유효성검사로 인해 오류 발생 여부 확인 변수 (0 : 문제발생)
			int buf = 0;
			String str[] = new String[11];
			
	
			// 전체 텍스트 불러오기
			str[0] = name_tx.getText();
			str[1] = id_tx.getText();
			str[2] = pw_tx.getText();
			str[3] = pw2_tx.getText();
			str[4] = bankPw_tx.getText();
			str[5] = phoneNum_tx.getText();
			// 주민등록번호
			str[7] = residentNum1_tx.getText();
			str[8] = residentNum2_tx.getText();
			
			// 5항목 중에 한개라도 작성하지 않으면 에러처리
			if(str[0].length() == 0 || str[1].length() == 0 ||  str[2].length() == 0 ||  str[3].length() == 0 ||  str[4].length() == 0 ||  str[5].length() == 0) {
				bankJoinCheck_la.setText("회원 입력 빈칸이 남아 있습니다.");
			}
			else {// 전부 작성 했을 시 유효성 검사 실시
				// 비밀번호 확인 체크 여부.			
				if(pwButton_chack == 1 && idButton_chack == 1) {
					
					if(name_ck == 0|id_ck == 0|pw_ck == 0|pw2_ck == 0|bankPw_ck == 0|phoneNum_ck == 0 | residentNum1_ck == 0 | residentNum2_ck == 0) {
						bankJoinCheck_la.setText("회원 입력에 \"오류발생\" 작성 완료해주세요");
						
					}
					else {
						bankJoinCheck_la.setText("가입완료");
						str[6] = setBankNumer(count);
						str[10] = residentNumSum(str[7],str[8]);
						
						int buf1 = dbSave(mem,str);
						if(buf1 == 1) {
							login_cl = new BankLogin();
							login_cl.setVisible(true);
							this.setVisible(false);
							this.dispose();
						}
						else {
							System.out.println("save error");
							bankJoinCheck_la.setText("회원가입 오류!(코드:save_err");
						}

					}
				}
				else {
					bankJoinCheck_la.setText("아이디 및 비밀번호 확인 버튼 눌러 체크 해주십시오");
				}
			}
		}

	}
	
	
	
	@Override // 키를 떼었을때 호출
	public void keyReleased(KeyEvent e) {
		
		Object obj = e.getSource();
		String str = "";
		int buf = 0;
		
		if(obj == name_tx) {
			str = name_tx.getText();
			buf = setNameCheck(str);
			name_ck = buf;
			if(buf == 0) bankJoinCheck_la.setText("한글,소문자,대문자끼리만 허용됩니다.");
			else bankJoinCheck_la.setText("");
		}
		else if(obj == id_tx) {
			str = id_tx.getText();
			buf = setIdCheck(str);			
			id_ck = buf;
			if(buf == 0) {	// 0이면 자리수가 4 이하, 12 이상일 경우. 
				if(str.length() < 4) {
					bankJoinCheck_la.setText("길이가 짧습니다.");
				}else if(str.length() > 12) bankJoinCheck_la.setText("길이가 깁니다.");
			}else if(buf == 2){// 첫자리 숫자
				bankJoinCheck_la.setText("첫자리 숫자와 특수문자는 사용 못합니다.");
			}else bankJoinCheck_la.setText("");			
		}
		else if(obj == pw_tx) {
			str = pw_tx.getText();
			buf = setPwCheck(str);
			pw_ck = buf;
			if(buf == 0) bankJoinCheck_la.setText("영문 대*소문자,숫자,특수문자 8~12자리");
			else bankJoinCheck_la.setText("");			
		}
		else if(obj == pw2_tx) {
			str = pw2_tx.getText();
			buf = setPwCheck(str);
			pw2_ck = buf;
			if(buf == 0) bankJoinCheck_la.setText("영문 대*소문자,숫자,특수문자 8~12자리");
			else bankJoinCheck_la.setText("");			
		}
		else if(obj == phoneNum_tx) {
			str = phoneNum_tx.getText();
			buf = setPhoneCheck(str);
			bankPw_ck = buf;
			if(buf == 0) bankJoinCheck_la.setText("\"-\" 도 같이 작성해주세요.");
			else bankJoinCheck_la.setText("");			
		}
		else if(obj == bankPw_tx) {
			str = bankPw_tx.getText();
			//System.out.println(str);
			buf = setBankPwCheck(str);
			phoneNum_ck = buf;
			if(buf == 0) bankJoinCheck_la.setText("숫자로만 4자리 입력해주세요");
			else bankJoinCheck_la.setText("");			
		}
		else if(obj == residentNum1_tx) {
			str = residentNum1_tx.getText();
			buf = setBankResidentNum(str,1);
			residentNum1_ck = buf;
			if(buf == 0)bankJoinCheck_la.setText("숫자로만 6자리 입력해주세요");
			else bankJoinCheck_la.setText("");
		}
		else if(obj == residentNum2_tx) {
			str = residentNum2_tx.getText();
			buf = setBankResidentNum(str,0);
			residentNum2_ck = buf;
			if(buf == 0)bankJoinCheck_la.setText("숫자로만 7자리 입력해주세요");
			else if(buf == 2) bankJoinCheck_la.setText("앞자리를 (1 or 2)로 입력해주세요");
			else bankJoinCheck_la.setText("");
			
		}
		
	}
	
	
	// 마우스가 해당 텍스트에 들어갈 때 이벤트 발생.
	@Override
	public void mouseEntered(MouseEvent e) {
		Object obj = e.getSource();
		String str;
		
		if(obj == pw_tx) {
			pw_tx.setEchoChar((char)0);
		}
		else if(obj == pw2_tx) {
			pw2_tx.setEchoChar((char)0);
		}
		else if(obj == residentNum2_tx) {
			residentNum2_tx.setEchoChar((char)0);
		}
		else if(obj == bankPw_tx) {
			bankPw_tx.setEchoChar((char)0);
		}
		
	}
	
	// 마우스를 땔때 나오는 이벤트
	@Override
	public void mouseExited(MouseEvent e) {
		
		Object obj = e.getSource();
		
		if(obj == pw_tx) {
			pw_tx.setEchoChar('*');
		}
		else if(obj == pw2_tx) {
			pw2_tx.setEchoChar('*');
		}
		else if(obj == residentNum2_tx) {
			residentNum2_tx.setEchoChar('*');
		}
		else if(obj == bankPw_tx) {
			bankPw_tx.setEchoChar('*');
		}
		
	}
	
	
	
	public void keyTyped(KeyEvent e) {}
	@Override // 키를 눌렀을때 호출
	public void keyPressed(KeyEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}

	
	
}


