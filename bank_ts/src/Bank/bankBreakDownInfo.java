package Bank;


public class bankBreakDownInfo {

	// 계좌 내역 필요 필드
	private String interalBankNum;
	private String exteralBankNum;
	private String dateTime;
	private String usingName;
	private String Transaction;
	
	private int usingCash;
	private int totalCash;
	
	
	public bankBreakDownInfo() {
		
		interalBankNum = "";
		exteralBankNum = "";
		dateTime = "";
		usingName = "";
		Transaction = "";
		
		usingCash = 0;
		totalCash = 0;
		
	}

	

	public String getInteralBankNum() {
		return interalBankNum;
	}


	public void setInteralBankNum(String interalBankNum) {
		this.interalBankNum = interalBankNum;
	}


	public String getExteralBankNum() {
		return exteralBankNum;
	}


	public void setExteralBankNum(String exteralBankNum) {
		this.exteralBankNum = exteralBankNum;
	}


	public String getDateTime() {
		return dateTime;
	}


	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}


	public String getUsingName() {
		return usingName;
	}


	public void setUsingName(String usingName) {
		this.usingName = usingName;
	}


	public String getTransaction() {
		return Transaction;
	}


	public void setTransaction(String transaction) {
		Transaction = transaction;
	}


	public int getUsingCash() {
		return usingCash;
	}


	public void setUsingCash(int usingCash) {
		this.usingCash = usingCash;
	}


	public int getTotalCash() {
		return totalCash;
	}


	public void setTotalCash(int totalCash) {
		this.totalCash = totalCash;
	}



	
	
	

}

