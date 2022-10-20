package Bank;

public class BankUserInfo {

	// membership info
	private String name;				// 이름
	private String id;					// 아이디
	private String pw;					// 비밀번호
	private String bankNum;				// 계좌번호
	private String bankPw;				// 계좌비밀번호
	private String phoneNum;			// 전화번호
	private String residentNum;			// 주민번호
	private String login;				// 로그인 on,off 상태.
	private String membership;
	private String memory;
	private String joinDate;
	
	private int cash;							// 돈
	private int check;							// 회원 정보를 찾을 때 사용.
	private int number;
	private int memory_pay;
	
	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public int getNumber() {
		return number;
	}


	public void setNumber(int number) {
		this.number = number;
	}


	public BankUserInfo(){
		this.name = "";
		this.id = "";
		this.pw = "";
		this.bankNum = "";
		this.bankPw = "";
		this.phoneNum = "";
		this.cash = 0;
		this.check = 0;	
		this.number = 0;
		this.login = "";
	}
	
	
	public BankUserInfo(String name, String id, String pw, String bankNum, String bankPw,String phoneNum,String login,
					int cash, int check, int number , String residentNum, String membership, String memory, int memory_pay, String joinDate) {
		
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.bankNum = bankNum;
		this.bankPw = bankPw;
		this.phoneNum = phoneNum;
		this.login = login;
		this.cash = cash;
		this.check = check;
		this.number = number;
		this.residentNum = residentNum;
		this.membership = membership;
		this.memory = "";
		this.memory_pay = 0;
		this.joinDate = joinDate;
		
	}
	





	public void setMemInit() {
		this.name = "";
		this.id = "";
		this.pw = "";
		this.bankNum = "";
		this.bankPw = "";
		this.login = "";
		this.cash = 0;
		this.check = 0;	
		this.number = 0;
		this.residentNum = "";
		this.membership = "";
	}
	
	
	public String getMemory() {
		return memory;
	}


	public void setMemory(String memory) {
		this.memory = memory;
	}


	public int getMemory_pay() {
		return memory_pay;
	}


	public void setMemory_pay(int memory_pay) {
		this.memory_pay = memory_pay;
	}

	
	public String getMembership() {
		return membership;
	}


	public void setMembership(String membership) {
		this.membership = membership;
	}


	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPw() {
		return pw;
	}
	
	public void setPw(String pw) {
		this.pw = pw;
	}
	
	public String getBankNum() {
		return bankNum;
	}
	
	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}
	
	public int getCash() {
		return cash;
	}
	
	public void setCash(int cash) {
		this.cash = cash;
	}

	public String getBankPw() {
		return bankPw;
	}

	public void setBankPw(String bankPw) {
		this.bankPw = bankPw;
	}

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	public String getPhoneNum() {
		return phoneNum;
	}


	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	public String getResidentNum() {
		return residentNum;
	}


	public void setResidentNum(String residentNum) {
		this.residentNum = residentNum;
	}

	public String getJoinDate() {
		return joinDate;
	}


	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}


}
