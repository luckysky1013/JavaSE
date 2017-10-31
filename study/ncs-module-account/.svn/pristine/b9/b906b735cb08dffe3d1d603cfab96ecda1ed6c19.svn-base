package cn.ncss.module.account.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import cn.ncss.commom.PubCodeFactory;

/**
 * 基本信息
 * @author liyang
 *
 */
@Embeddable
public class Information {
	/*
	 * 学生用户
	 */
	/*//城市
	@Column(length = 10)
	private String city = "";
	//省
	@Column(length = 10)
	private String province = "";*/

	// 地区代码，6位数字代码---省市
	//@Size(max = 6)
	@Column(length = 6)
	private String divisionCode = "";
	@Transient
	private String divisionName;
	//生日
	@Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "生日格式为YYYY-MM-DD")
	@Column(length = 10)
	@Length(max = 10, message = "生日应该在{min}~{max}之间")
	private String birth = "";
	//身份证号
	@Column(length = 32)
	@Length(max = 32, message = "身份证号在{min}~{max}之间")
	private String idCard = "";
	//证件类型:01大陆身份证 02香港身份证  03军官证 04护照 05澳门身份证 06台湾身份证
	@Column(length = 8)
	private String idType = "";
	//民族
	@Column(length = 20)
	@Length(max = 20, message = "民族长度在{min}~{max}之间")
	private String nation = "";
	@Transient
	private String nationName;
	//学历层次
	@Column(length = 16)
	@Length(max = 16)
	private String eduLevel = "";
	//政治面貌
	@Column(length = 16)
	@Length(max = 16)
	private String identity = "";
	@Transient
	private String identityName;
	//学校ID 外国学校默认为00000
	@Column(length = 10)
	@Length(max = 10)
	private String schoolId = "";
	//学校名字
	@Column(length = 100)
	@Length(max = 100, message = "学校名字长度在{min}~{max}之间")
	private String schoolName = "";
	//是否为外国大学
	@Column(name = "is_Foreign_University", length = 8)
	private String isForeignUniversity = "false";
	//入学时间
	@Column(length = 8)
	@Length(max = 8, message = "入学时间长度在{min}~{max}之间")
	private String startDate = "";
	//毕业时间
	@Length(max = 8, message = "毕业时间长度在{min}~{max}之间")
	@Column(length = 8)
	private String endDate = "";

	//专业学科中类代码
	@Length(max = 8, message = "所学专业代码长度应该在{min}~{max}之间")
	@Column(name = "middle_major_code", length = 8)
	private String middleMajorCode;
	//专业学科中类
	@Length(max = 64, message = "所学专业代码长度应该在{min}~{max}之间")
	@Column(name = "middle_major_name", length = 64)
	private String middleMajorName;
	//专业学科小类代码
	@Length(max = 8, message = "所学专业代码长度应该在{min}~{max}之间")
	@Column(name = "small_major_code", length = 8)
	private String smallMajorCode;
	//专业学科小类
	@Length(max = 64, message = "所学专业代码长度应该在{min}~{max}之间")
	@Column(name = "small_major_name", length = 64)
	private String smallMajorName;
	//辅修专业中类代码
	@Length(max = 8, message = "辅修专业代码长度应该在{min}~{max}之间")
	@Column(name = "middle_minor_code", length = 8)
	private String middleMinorCode;
	//辅修专业中类名称
	@Length(max = 64, message = "辅修专业名称长度应该在{min}~{max}之间")
	@Column(name = "middle_minor_name", length = 64)
	private String middleMinorName;
	//辅修专业小类代码
	@Length(max = 8, message = "辅修专业代码长度应该在{min}~{max}之间")
	@Column(name = "small_minor_code", length = 8)
	private String smallMinorCode;
	//辅修专业小类名称
	@Length(max = 64, message = "辅修专业名称长度应该在{min}~{max}之间")
	@Column(name = "small_minor_name", length = 64)
	private String smallMinorName;

	/*
	 * 企业用户
	 */
	//职务
	@Length(max = 32, message = "职务长度应该在{min}~{max}之间")
	@Column(length = 32)
	private String jobTitle = "";
	//公司名称
	@Length(max = 128)
	@Column(length = 128)
	private String companyName = "";

	//contact 
	//传真
	@Pattern(regexp = "^(((0\\d{2,3})-)?(\\d{7,8})(-(\\d{1,5}))?)?$", message = "传真格式不正确")
	@Column(length = 20)
	@Length(max = 20)
	private String jobfax = "";
	//工作电话
	@Pattern(regexp = "^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[2-9]{9})|(15[0-9]{9})$|^(((0\\d{2,3})-)?(\\d{7,8})(-(\\d{5,}))?)?$?", message = "工作电话格式不正确")
	@Column(length = 20)
	@Length(max = 20, message = "工作电话长度应该在{min}~{max}之间")
	private String jobphone = "";
	/*
	 * 公用字段
	 */
	//所在院系/部门
	@Column(length = 32)
	@Length(max = 32, message = "院系或部门长度应该在{min}~{max}之间")
	private String department = "";

	@Column(length = 11)
	@Length(max = 11, message = "手机号长度应该在{min}~{max}之间")
	@Pattern(regexp = "^(((1[3|4|5|6|8|9][0-9]{1})|(17[2-9]{1}))+\\d{8})$", message = "手机号格式不正确")
	private String mobilePhone = "";

	//@Pattern(regexp = "^(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)?")
	@Length(max = 32, message = "邮箱长度应该在{min}~{max}之间")
	@Email(message = "邮箱格式不正确")
	private String email = "";

	public Information() {
	}

	public Information(String divisionCode, String birth, String idCard, String idType, String nation, String eduLevel,
			String identity, String schoolId, String schoolName, String startDate, String endDate,
			String middleMajorCode, String middleMajorName, String smallMajorCode, String smallMajorName,
			String middleMinorCode, String middleMinorName, String smallMinorCode, String smallMinorName,
			String jobTitle, String companyName, String jobfax, String jobphone, String department, String mobilePhone,
			String email) {
		super();
		this.divisionCode = divisionCode;
		this.birth = birth;
		this.idCard = idCard;
		this.idType = idType;
		this.nation = nation;
		this.eduLevel = eduLevel;
		this.identity = identity;
		this.schoolId = schoolId;
		this.schoolName = schoolName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.middleMajorCode = middleMajorCode;
		this.middleMajorName = middleMajorName;
		this.smallMajorCode = smallMajorCode;
		this.smallMajorName = smallMajorName;
		this.middleMinorCode = middleMinorCode;
		this.middleMinorName = middleMinorName;
		this.smallMinorCode = smallMinorCode;
		this.smallMinorName = smallMinorName;
		this.jobTitle = jobTitle;
		this.companyName = companyName;
		this.jobfax = jobfax;
		this.jobphone = jobphone;
		this.department = department;
		this.mobilePhone = mobilePhone;
		this.email = email;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = StringUtils.trimAllWhitespace(birth);
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = StringUtils.trimAllWhitespace(idCard);
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = StringUtils.trimAllWhitespace(idType);
	}

	public String getNation() {
		return nation;

	}

	public String getNationName() {
		if (StringUtils.hasText(nation)) {
			if (PubCodeFactory.getInstance().getNation().containsKey(nation)) {
				return PubCodeFactory.getInstance().getNation().get(nation).toString();
			}
			return nation;
		} else {
			return "";
		}

	}

	public void setNation(String nation) {
		if (StringUtils.hasText(nation)) {
			if (PubCodeFactory.getInstance().getNation().containsKey(nation)) {
				this.nation = nation;
			}
		} else {
			this.nation = nation;
		}
	}

	public String getEduLevel() {
		return eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = StringUtils.trimAllWhitespace(eduLevel);
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = StringUtils.trimAllWhitespace(identity);
	}

	public String getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = StringUtils.trimAllWhitespace(schoolId);
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName == null ? null : schoolName.trim();
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = StringUtils.trimAllWhitespace(startDate);
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = StringUtils.trimAllWhitespace(endDate);
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle == null ? null : jobTitle.trim();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName == null ? null : companyName.trim();
	}

	public String getJobfax() {
		return jobfax;
	}

	public void setJobfax(String jobfax) {
		this.jobfax = StringUtils.trimAllWhitespace(jobfax);
	}

	public String getJobphone() {
		return jobphone;
	}

	public void setJobphone(String jobphone) {
		this.jobphone = StringUtils.trimAllWhitespace(jobphone);
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department == null ? null : department.trim();
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

	public String getDivisionCode() {
		return divisionCode;
	}

	public String getDivisionName() {
		if (StringUtils.hasText(divisionCode)) {
			return PubCodeFactory.getInstance().getAreaCodeSyd().getProperty(this.divisionCode);
		}
		return divisionCode;

	}

	public void setDivisionCode(String divisionCode) {
		if (StringUtils.hasText(divisionCode)) {
			if (PubCodeFactory.getInstance().getAreaCodeSyd().keySet().contains(divisionCode)) {
				this.divisionCode = divisionCode;
			}
		} else {
			this.divisionCode = divisionCode;
		}

	}

	////政治面貌中文
	public String getIdentityName() {
		if (StringUtils.hasText(identity)) {
			return PubCodeFactory.getInstance().getIdentityCode().getProperty(this.identity);
		}
		return identity;

	}

	public String getMiddleMajorCode() {
		return middleMajorCode;
	}

	public void setMiddleMajorCode(String middleMajorCode) {
		this.middleMajorCode = setCode(middleMajorCode);
		//this.middleMajorName = getNameByCode(middleMajorCode);
	}

	public String getMiddleMajorName() {
		return middleMajorName;
	}

	public void setMiddleMajorName(String middleMajorName) {
		this.middleMajorName = middleMajorName == null ? null : middleMajorName.trim();
	}

	public String getSmallMajorCode() {
		return smallMajorCode;
	}

	public void setSmallMajorCode(String smallMajorCode) {
		this.smallMajorCode = setCode(smallMajorCode);
		//this.smallMajorName = getNameByCode(smallMajorCode);
	}

	public String getSmallMajorName() {
		return smallMajorName;
	}

	public void setSmallMajorName(String smallMajorName) {
		this.smallMajorName = smallMajorName == null ? null : smallMajorName.trim();
	}

	public String getMiddleMinorCode() {
		return middleMinorCode;
	}

	public void setMiddleMinorCode(String middleMinorCode) {
		this.middleMinorCode = setCode(middleMinorCode);
		//this.middleMinorName = getNameByCode(middleMinorCode);
	}

	public String getMiddleMinorName() {
		return middleMinorName;
	}

	public void setMiddleMinorName(String middleMinorName) {
		this.middleMinorName = middleMinorName == null ? null : middleMinorName.trim();
	}

	public String getSmallMinorCode() {
		return smallMinorCode;
	}

	public void setSmallMinorCode(String smallMinorCode) {
		this.smallMinorCode = setCode(smallMinorCode);
		//this.smallMinorName = getNameByCode(smallMinorCode);
	}

	public String getSmallMinorName() {
		return smallMinorName;
	}

	public void setSmallMinorName(String smallMinorName) {
		this.smallMinorName = smallMinorName == null ? null : smallMinorName.trim();
	}

	public String getIsForeignUniversity() {
		return isForeignUniversity;
	}

	public void setIsForeignUniversity(String isForeignUniversity) {
		this.isForeignUniversity = isForeignUniversity;
	}

	public String setCode(String code) {
		if (StringUtils.hasText(code)) {
			if ("01".equals(eduLevel) || "11".equals(eduLevel)) {
				if (PubCodeFactory.getInstance().getMajorCodeYJS().keySet().contains(code)) {
					return StringUtils.trimAllWhitespace(code);
				}
			} else if ("25".equals(eduLevel) || "31".equals(eduLevel)) {
				if (PubCodeFactory.getInstance().getMajorCodeBK().keySet().contains(code)) {
					return StringUtils.trimAllWhitespace(code);
				}
			} else if ("41".equals(eduLevel)) {
				if (PubCodeFactory.getInstance().getMajorCodeZK().keySet().contains(code)) {
					return StringUtils.trimAllWhitespace(code);
				}
			}
		}
		return StringUtils.trimAllWhitespace(code);
	}

	public String getNameByCode(String code) {
		if (StringUtils.hasText(code)) {
			if ("01".equals(eduLevel) || "11".equals(eduLevel)) {
				return PubCodeFactory.getInstance().getMajorCodeYJS().getProperty(code);
			} else if ("25".equals(eduLevel) || "31".equals(eduLevel)) {
				return PubCodeFactory.getInstance().getMajorCodeBK().getProperty(code);
			} else if ("41".equals(eduLevel)) {
				return PubCodeFactory.getInstance().getMajorCodeZK().getProperty(code);
			}

		}
		return code;
	}

}
