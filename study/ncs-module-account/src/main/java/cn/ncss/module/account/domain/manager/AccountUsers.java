package cn.ncss.module.account.domain.manager;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import cn.ncss.commom.PubCodeFactory;

/**
 * 后台学生用户list展示实体
 * @author liyang
 *
 */
public class AccountUsers implements Serializable {

	private static final long serialVersionUID = -8458032309289755510L;

	//用户编号，使用userid
	private String userId;
	private String mobilePhone;
	private String email;
	//用户真实姓名
	private String realName;
	//学历层次 
	private String eduLevel;
	//就读学校编码
	private String schoolId;
	//学校 
	private String schoolName;
	//专业 类别
	private String majorCode;
	//专业类别名称
	private String majorName;
	//毕业时间	
	private String endDate;
	//学生类型 
	//private String studentType;
	//生源地 
	private String province;

	private String city;

	//学籍验证
	private String authedStatus;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEduLevel() {
		return eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = eduLevel;
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getMajorCode() {
		return majorCode;
	}

	public void setMajorCode(String majorCode) {
		this.majorCode = majorCode;
	}

	public String getMajorName() {
		return majorName;
	}

	public void setMajorName(String majorName) {
		this.majorName = majorName;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getProvince() {
		if (StringUtils.hasText(province)) {
			return PubCodeFactory.getInstance().getAreaCodeSyd().getProperty(this.province.substring(0, 2));
		}
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAuthedStatus() {
		return authedStatus;
	}

	public void setAuthedStatus(String authedStatus) {
		this.authedStatus = authedStatus;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public AccountUsers() {
		super();
	}

	public AccountUsers(String userId, String mobilePhone, String email, String realName, String eduLevel,
			String schoolId, String schoolName, String majorCode, String majorName, String endDate, String province,
			String city, String authedStatus) {
		super();
		this.userId = userId;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.realName = realName;
		this.eduLevel = eduLevel;
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.majorCode = majorCode;
		this.majorName = majorName;
		this.endDate = endDate;
		this.province = province;
		this.city = city;
		this.authedStatus = authedStatus;
	}

}
