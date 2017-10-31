package cn.ncss.module.account.domain.manager;

import java.io.Serializable;
import java.util.Date;

/**
 * 后台企业用户list展示
 * @author liyang
 *
 */
public class CorpUsers implements Serializable {

	private static final long serialVersionUID = 2911137587822820353L;
	//用户编号，使用userid
	private String userId;
	//用户真实姓名
	private String realName;
	//公司名称
	private String companyName;
	//所在部门
	private String department;
	//职务
	private String jobTitle;
	//工作电话
	private String jobphone;
	//传真
	private String jobfax;
	//电子邮箱
	private String email;
	//手机号
	private String mobilePhone;
	//提交时间 （当authedStatus="无"时，为注册时间）
	private Date updateDate;
	//认证状态
	private String authedStatus;
	//审核时间
	private Date checkDate;
	//之前被举报次数总和
	private String reportNumBefore;
	//举报次数
	private String reportNum;

	//解锁日期
	private Date unlockDate;
	//公司ID
	private String companyId;
	//公司审核状态
	private String aduitStatus;
	private String uploadTag;

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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getJobphone() {
		return jobphone;
	}

	public void setJobphone(String jobphone) {
		this.jobphone = jobphone;
	}

	public String getJobfax() {
		return jobfax;
	}

	public void setJobfax(String jobfax) {
		this.jobfax = jobfax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getAuthedStatus() {
		return authedStatus;
	}

	public void setAuthedStatus(String authedStatus) {
		this.authedStatus = authedStatus;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getReportNum() {
		return reportNum;
	}

	public void setReportNum(String reportNum) {
		this.reportNum = reportNum;
	}

	public Date getUnlockDate() {
		return unlockDate;
	}

	public void setUnlockDate(Date unlockDate) {
		this.unlockDate = unlockDate;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getAduitStatus() {
		return aduitStatus;
	}

	public void setAduitStatus(String aduitStatus) {
		this.aduitStatus = aduitStatus;
	}

	public String getUploadTag() {
		return uploadTag;
	}

	public void setUploadTag(String uploadTag) {
		this.uploadTag = uploadTag;
	}

	public String getReportNumBefore() {
		return reportNumBefore;
	}

	public void setReportNumBefore(String reportNumBefore) {
		this.reportNumBefore = reportNumBefore;
	}

}
