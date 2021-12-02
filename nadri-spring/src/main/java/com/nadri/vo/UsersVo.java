package com.nadri.vo;

public class UsersVo {
	
	private int usersId;
	private String usersEmail;
	private String usersName;
	private String usersPassword;
	//회원가입일
	private String usersRegDate;
	//로그인 경로
	private String usersRoute;
	//회원정보 수정일
	private String usersUpdateDate;
	//프로필 사진
	private String usersImageName;
	//sns로그인 시 이메일 정보제공 동의여부
	// 0: 동의안함   1: 동의함
	private String usersEmailAgreement;
	
	public UsersVo() {
		super();
	}

	public UsersVo(String usersEmail, String usersName, String usersPassword, String usersRegDate,
			String usersRoute, String usersUpdateDate, String usersImageName, String usersEmailAgreement) {
		super();
		this.usersEmail = usersEmail;
		this.usersName = usersName;
		this.usersPassword = usersPassword;
		this.usersRegDate = usersRegDate;
		this.usersRoute = usersRoute;
		this.usersUpdateDate = usersUpdateDate;
		this.usersImageName = usersImageName;
		this.usersEmailAgreement = usersEmailAgreement;
	}

	public int getUsersId() {
		return usersId;
	}
	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}
	public String getUsersEmail() {
		return usersEmail;
	}
	public void setUsersEmail(String usersEmail) {
		this.usersEmail = usersEmail;
	}
	public String getUsersName() {
		return usersName;
	}
	public void setUsersName(String usersName) {
		this.usersName = usersName;
	}
	public String getUsersPassword() {
		return usersPassword;
	}
	public void setUsersPassword(String usersPassword) {
		this.usersPassword = usersPassword;
	}
	public String getUsersRegDate() {
		return usersRegDate;
	}
	public void setUsersRegDate(String usersRegDate) {
		this.usersRegDate = usersRegDate;
	}
	public String getUsersRoute() {
		return usersRoute;
	}
	public void setUsersRoute(String usersRoute) {
		this.usersRoute = usersRoute;
	}
	public String getUsersUpdateDate() {
		return usersUpdateDate;
	}
	public void setUsersUpdateDate(String usersUpdateDate) {
		this.usersUpdateDate = usersUpdateDate;
	}
	public String getUsersImageName() {
		return usersImageName;
	}
	public void setUsersImageName(String usersImageName) {
		this.usersImageName = usersImageName;
	}
	public String getUsersEmailAgreement() {
		return usersEmailAgreement;
	}
	public void setUsersEmailAgreement(String usersEmailAgreement) {
		this.usersEmailAgreement = usersEmailAgreement;
	}
	
	@Override
	public String toString() {
		return "UsersVo [usersId=" + usersId + ", usersEmail=" + usersEmail + ", usersName=" + usersName
				+ ", usersPassword=" + usersPassword + ", usersRegDate=" + usersRegDate + ", usersRoute=" + usersRoute
				+ ", usersUpdateDate=" + usersUpdateDate + ", usersImageName=" + usersImageName
				+ ", usersEmailAgreement=" + usersEmailAgreement + "]";
	}

}
