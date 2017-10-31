package cn.ncss.module.account.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.ncss.commom.PubCodeFactory;

/**
 * 教育背景
 * @author LiYang
 *
 */
@Entity
@Table(name = "resume_education_history")
public class EducationHistory implements Serializable {

	private static final long serialVersionUID = 5946627509450195736L;
	public static final String FOREIGN_SCHOOLID = "00000";//外国大学ID
	@Id
	@Column(name = "id", length = 22, nullable = false, updatable = false)
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.ncss.commom.Base58UuidGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	//入学时间
	@NotNull(message = "入学时间不能为空")
	//@JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd日")
	@Column(name = "start_date", length = 8)
	@Length(max = 8, message = "入学时间长度在{min}~{max}之间")
	private String startDate;
	//毕业时间
	@NotNull(message = "毕业时间不能为空")
	//@JsonFormat(timezone = "GMT+8", pattern = "yyyy年MM月dd日")
	@Column(name = "end_date", length = 8)
	@Length(max = 8, message = "毕业时间长度在{min}~{max}之间")
	private String endDate;
	//就读学校编码 外国学校默认为00000
	@NotBlank(message = "学校编码不能为空")
	@Length(max = 8, message = "就读学校编码长度应该在{min}~{max}之间")
	@Column(name = "school_id", length = 8)
	private String schoolId;
	//就读学校名称
	@NotBlank(message = "学校名称不能为空")
	@Length(max = 100, message = "就读学校名称长度应该在{min}~{max}之间")
	@Column(name = "school_name", length = 100)
	private String schoolName;
	//是否为外国大学
	@Column(name = "is_Foreign_University", length = 8)
	private String isForeignUniversity = "false";
	//学历层次
	@NotBlank(message = "学历层次不能为空")
	//@Length(max = 1, message = "学历层次长度应该在{min}~{max}之间")
	@Column(name = "eduLevel", length = 5)
	private String eduLevel;
	/*//学生类型
	@NotBlank(message = "学生类型不能为空")
	@Length(max = 1, message = "学生类型长度应该在{min}~{max}之间")
	@Column(name = "student_type", length = 1)
	private String studentType;*/
	//所在院系
	@Length(max = 32, message = "所在院系长度应该在{min}~{max}之间")
	@Column(name = "department", length = 32)
	private String department;
	/**
	 * 老系统:
	 * 专业代码 majorCode
	 * 专业名称 major
	 * 学生录入的具体专业名称 majorName
	 */
	//专业学科中类代码
	//@NotBlank
	@Length(max = 8, message = "所学专业代码长度应该在{min}~{max}之间")
	@Column(name = "middle_major_code", length = 8)
	private String middleMajorCode;
	//专业学科中类
	//@NotBlank
	@Length(max = 64, message = "所学专业代码长度应该在{min}~{max}之间")
	@Column(name = "middle_major_name", length = 64)
	private String middleMajorName;
	//专业学科小类代码
	//@NotBlank
	@Length(max = 8, message = "所学专业代码长度应该在{min}~{max}之间")
	@Column(name = "small_major_code", length = 8)
	private String smallMajorCode;
	//专业学科小类
	//@NotBlank
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
	//学号
	@Length(max = 32, message = "学号长度应该在{min}~{max}之间")
	@Column(name = "student_no", length = 32)
	private String studentNo;
	/*//说明简介
	@Length(max = 256, message = "说明简介长度应该在{min}~{max}之间")
	@Column(name = "description", length = 256)
	private String description;*/
	//学籍验证状态
	@Column(name = "is_authed", length = 8)
	private String isAuthed = "false";
	//从完善学历录入的那条数据为true、学籍验证数据标识（true：不可以删除；false：可以删除）
	@Column(name = "is_key", length = 8)
	private String isKey = "false";
	//简历
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resume_id", nullable = false)
	private Resume resume;

	@Column(name = "create_date", updatable = false)
	private Date createDate = new Date();

	@Column(name = "update_date")
	private Date updateDate = new Date();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public String getEduLevel() {
		return eduLevel;
	}

	public void setEduLevel(String eduLevel) {
		this.eduLevel = StringUtils.trimAllWhitespace(eduLevel);
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department == null ? null : department.trim();
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

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getIsAuthed() {
		return isAuthed;
	}

	public void setIsAuthed(String isAuthed) {
		this.isAuthed = isAuthed;
	}

	public String getIsKey() {
		return isKey;
	}

	public void setIsKey(String isKey) {
		this.isKey = isKey;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
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

	public void setMiddleMajorName(String middleMajorName) {
		this.middleMajorName = middleMajorName == null ? null : middleMajorName.trim();
	}

	public void setSmallMajorName(String smallMajorName) {
		this.smallMajorName = smallMajorName == null ? null : smallMajorName.trim();
	}

	public void setMiddleMinorName(String middleMinorName) {
		this.middleMinorName = middleMinorName == null ? null : middleMinorName.trim();
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

	/*@Override
	public String toString() {
		return "EducationHistory [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", schoolId="
				+ schoolId + ", schoolName=" + schoolName + ", eduLevel=" + eduLevel + ", department=" + department
				+ ", majorCode=" + majorCode + ", majorName=" + majorName + ", minorCode=" + minorCode + ", minorName="
				+ minorName + ", isAuthed=" + isAuthed + ",isKey=" + isKey + ", resume=" + resume + "]";
	}*/

}
