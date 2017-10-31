package cn.ncss.module.account.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

/**
 * 用户实体
 * @author LiYang
 *
 */
@Entity
@Table(name = "user_account")
public class UserAccount implements Serializable {

	private static final long serialVersionUID = 7605211566992766124L;

	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.ncss.commom.Base58UuidGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String userId;

	//密码
	@Column(length = 16, nullable = false, name = "password")
	@Length(min = 6, max = 32)
	private String password;

	//加密盐
	@Column(length = 4, nullable = true, name = "salt")
	private String salt;

	//手机号
	@Pattern(regexp = "^(((1[3|4|5|6|8|9][0-9]{1})|(17[2-9]{1}))+\\d{8})$", message = "手机号格式不正确")
	@Column(length = 11, nullable = true, name = "mobile_phone", unique = true)
	@Length(max = 11, message = "手机号长度应该在{min}~{max}之间")
	private String mobilePhone;

	//邮箱
	@Email(regexp = "(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)?", message = "邮箱格式不正确")
	@Column(name = "email", nullable = true, unique = true)
	@Length(max = 32, message = "邮箱长度应该在{min}~{max}之间")
	private String email;

	//是否验证邮箱
	@Column(name = "is_valid_email", nullable = false)
	private String isValidEmail = "false";

	//是否验证手机
	@Column(name = "is_valid_phone", nullable = false)
	private String isValidPhone = "false";

	//登录失败次数
	@Column(name = "login_failed_count")
	private int loginFailedCount;

	//解锁日期
	@Column(name = "unlock_date")
	private Date unlockDate;

	//账户是否被锁住
	/*@Column(name = "is_lock", nullable = false)
	private boolean isLock = false;
	*/
	//用户类型：学生用户，企业用户 STUDENT, COMPANY
	@Column(name = "user_type", nullable = false)
	//@Enumerated(EnumType.STRING)
	@Length(max = 8)
	private String userType;

	//注册来源站点code （部级中心：00，省：4位 ， 学校：5位）
	@Column(name = "reg_website_code")
	@Length(max = 6)
	private String regWebsiteCode;

	//注册时间
	@Column(name = "reg_date", updatable = false)
	private Date regDate;

	//注册IP
	@Column(name = "reg_ip", updatable = false)
	private String regIP;

	//最后登录时间
	@Column(name = "last_login_date")
	private Date lastLoginDate;

	//展示上次最后登录时间
	@Column(name = "last_login_date_show")
	private Date lastLoginDateShow;

	//最后登录IP
	@Column(name = "last_login_ip")
	private String lastLoginIP;

	//密码强度 LOW MIDDLE HIGH
	@Column(name = "password_level")
	@Length(max = 8)
	private String passwordLevel;

	//通知类型【学生用户：企业通知COM、系统通知SYS;企业用户：系统通知SYS;】
	//学生（已被查看（OK）、待沟通（LEAD）、邀请面试（INTERVIEW）、通过面试（OFFERED）、不合适（REJECTED））
	//企业（职位发布的进展、现状反馈（ZWFB）；加入的团队的消息（TDXX）；账户通知、系统反馈（ZHTZ））
	@Column(name = "nfn_Type")
	@Length(max = 100)
	private String nfnType = "";

	//弹窗标识：企业管理员删除普通用户（解绑）(popUps:del)企业管理员审核不通过 (popUps:nopass)
	@Column(name = "pop_Tag")
	@Length(max = 8)
	private String popTag = "";
	//累计职位被举报数（不包括在当前公司被举报的次数）
	@Column(name = "be_Reported_Count_before")
	private int beReportedCountBefore = 0;
	//在当前公司职位被举报的次数
	@Column(name = "be_Reported_Count")
	private int beReportedCount = 0;

	public String getPopTag() {
		return popTag;
	}

	public void setPopTag(String popTag) {
		this.popTag = popTag;
	}

	public UserAccount() {
		super();
	}

	public UserAccount(String userId, String password, String salt, String mobilePhone, String email,
			String isValidEmail, String isValidPhone, int loginFailedCount, Date unlockDate, String userType,
			String regWebsiteCode, Date regDate, String regIP, Date lastLoginDate, String lastLoginIP) {
		super();
		this.userId = userId;
		this.password = password;
		this.salt = salt;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.isValidEmail = isValidEmail;
		this.isValidPhone = isValidPhone;
		this.loginFailedCount = loginFailedCount;
		this.unlockDate = unlockDate;
		//this.isLock = isLock;
		this.userType = userType;
		this.regWebsiteCode = regWebsiteCode;
		this.regDate = regDate;
		this.regIP = regIP;
		this.lastLoginDate = lastLoginDate;
		this.lastLoginIP = lastLoginIP;
	}

	public String getSalt() {
		return salt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	/*public void setPassword(String password) {
		this.passwordLevel = CommonUtils.getLevel(password);
		this.salt = CommonUtils.randomNumeric(4);
		if (!StringUtils.hasText(this.salt)) {
			salt = new SecureRandomNumberGenerator().nextBytes().toHex();
		}
		this.password = new Md5Hash(password, salt).toString();
	}*/

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = StringUtils.trimAllWhitespace(mobilePhone);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = StringUtils.trimAllWhitespace(email);
	}

	public String getIsValidEmail() {
		return isValidEmail;
	}

	public void setIsValidEmail(String isValidEmail) {
		this.isValidEmail = isValidEmail;
	}

	public String getIsValidPhone() {
		return isValidPhone;
	}

	public void setIsValidPhone(String isValidPhone) {
		this.isValidPhone = isValidPhone;
	}

	public int getLoginFailedCount() {
		return loginFailedCount;
	}

	public void setLoginFailedCount(int loginFailedCount) {
		this.loginFailedCount = loginFailedCount;
	}

	public Date getUnlockDate() {
		return unlockDate;
	}

	public void setUnlockDate(Date unlockDate) {
		this.unlockDate = unlockDate;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = StringUtils.trimAllWhitespace(userType);
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getRegIP() {
		return regIP;
	}

	public void setRegIP(String regIP) {
		this.regIP = StringUtils.trimAllWhitespace(regIP);
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = StringUtils.trimAllWhitespace(lastLoginIP);
	}

	public String getRegWebsiteCode() {
		return regWebsiteCode;
	}

	public void setRegWebsiteCode(String regWebsiteCode) {
		this.regWebsiteCode = StringUtils.trimAllWhitespace(regWebsiteCode);
	}

	public String getPasswordLevel() {
		return passwordLevel;
	}

	public void setPasswordLevel(String passwordLevel) {
		this.passwordLevel = StringUtils.trimAllWhitespace(passwordLevel);
	}

	public String getNfnType() {
		return nfnType;
	}

	public void setNfnType(String nfnType) {
		this.nfnType = StringUtils.trimAllWhitespace(nfnType);
	}

	public int getBeReportedCount() {
		return beReportedCount;
	}

	public void setBeReportedCount(int beReportedCount) {
		this.beReportedCount = beReportedCount;
	}

	public Date getLastLoginDateShow() {
		return lastLoginDateShow;
	}

	public void setLastLoginDateShow(Date lastLoginDateShow) {
		this.lastLoginDateShow = lastLoginDateShow;
	}

	public int getBeReportedCountBefore() {
		return beReportedCountBefore;
	}

	public void setBeReportedCountBefore(int beReportedCountBefore) {
		this.beReportedCountBefore = beReportedCountBefore;
	}

	/*public enum LoginType {
		*//**
			* 新职业登录
			*/
	/*
	NCSS,
	*//**
		* 学信登录
		*/
	/*
	CHSI, QQ, WEIXIN, WEIBO
	}*/

}
