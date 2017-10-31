package cn.ncss.module.account.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.StringUtils;

import cn.ncss.commom.ArrayStringType;
import cn.ncss.commom.HstoreUserType;

/**
 * 基本信息
 * @author liyang
 */
@Entity
@Table(name = "PROFILE")
@TypeDefs(value = { @TypeDef(name = "array", typeClass = ArrayStringType.class),
		@TypeDef(name = "hstore", typeClass = HstoreUserType.class) })
public class Profile implements Serializable {
	private static final long serialVersionUID = 5708824763366060539L;
	/**
	 * authedStatus
	 */
	//01 当前学籍通过认证
	public static final String STUDENT_AS_PASS_01 = "01";
	//02 学籍校验失败
	public static final String STUDENT_AS_NOPASS_02 = "02";
	//0未认证
	public static final String CORP_NOTCERTIFIED_0 = "0";
	//04公司同事待认证为普通用户
	public static final String CORP_TOBE_GENERALUSER_MEMBER_04 = "04";
	//1待认证为普通用户
	public static final String CORP_TOBE_GENERALUSER_1 = "1";
	//2待认证为管理员用户
	public static final String CORP_TOBE_ADMINUSER_2 = "2";
	//3后台管理员已认证为企业普通用户
	public static final String CORP_GENERALUSER_3 = "3";
	//4公司同事认证为企业普通用户
	public static final String CORP_GENERALUSER_MEMBER_4 = "4";
	//5后台管理员已认证为企业管理员用户
	public static final String CORP_ADMINUSER_5 = "5";
	//61普通用户认证失败
	public static final String CORP_GENERALUSER_FAIL_61 = "61";
	//62管理员认证失败
	public static final String CORP_ADMINUSER_FAIL_62 = "62";
	//7已过期待认证（管理员）
	public static final String CORP_GQ_TOBE_ADMINUSER_7 = "7";
	//8过期认证失败（管理员）
	public static final String CORP_GQ_FAIL_8 = "8";
	//9普通用户申请为管理员待审核中
	public static final String CORP_GENERALUSER_ADMIN_9 = "9";

	//用户编号，使用userid
	@Id
	@Column(name = "user_id", length = 22, nullable = false, unique = true)
	private String userId;
	//用户真实姓名
	@NotNull(message = "真实姓名不能为空")
	@Length(max = 64, message = "真实姓名应该在{min}~{max}之间")
	@Column(name = "REAL_NAME")
	private String realName = "";
	//用户头像url
	@Length(max = 128)
	@Column(name = "AVATOR_URL", length = 128)
	private String avatorUrl = "";
	//性别1：男，2：女，0：未知
	@NotNull(message = "性别不能为空")
	@Column(name = "GENDER", length = 2)
	private String gender = "";

	/**
	 *  证件类型‘01’大陆身份证 ‘02’香港身份证 ‘03’军官证 ‘04’护照号 ‘05’澳门身份证 ‘06’台湾身份证
	 *  其他信息（idType：证件类型、idCard:证件号码、examId：考生号、birthday：出生日期、nation：民族、identity：政治面貌、
	 *  生源地 province city
	 *  企业其他信息（jobTitle：职务、公司名称companyName、所在部门department）
	 *  eduLevel：学历层次、school：就读学校、department：所在院系、zydm：所学专业 
	 */
	/*@Type(type = "hstore")
	@Column(name = "profile_meta", columnDefinition = "hstore")
	private Map<String, String> profileMeta = new HashMap<String, String>();*/
	//联系信息（手机mobilePhone、qq、email）
	//企业（jobphone：工作电话、jobfax：传真）
	/*@Type(type = "hstore")
	@Column(name = "contact_meta", columnDefinition = "hstore")
	private Map<String, String> contactMeta = new HashMap<String, String>();*/
	//基本信息
	@Embedded
	private Information information;

	//企业：0未认证、1后台管理员已认证为企业普通用户、2后台管理员已认证为企业管理员用户 、3认证失败、4公司同事认证为企业普通用户]
	//若管理员移交权限,降为（1：后台管理员已认证为企业普通用户）

	//认证状态[学生： 01 当前学籍通过认证、 02 学籍校验失败；
	//企业：0未认证、04公司同事待认证为普通用户、1待认证为普通用户、2待认证为管理员用户、3后台管理员已认证为企业普通用户、4公司同事认证为企业普通用户、
	//5后台管理员已认证为企业管理员用户 、61普通用户认证失败、62管理员认证失败、7已过期待认证（管理员）、8过期认证失败（管理员）、9普通用户申请为管理员待审核中]

	//04状态只能由公司同事认证为4普通用户、1只能由后台认证为3普通用户、2只能由后台认证为5管理员用户
	//若管理员移交权限,降为（3：后台管理员已认证为企业普通用户）
	@Column(name = "authed_status", length = 4)
	private String authedStatus = CORP_NOTCERTIFIED_0;

	//学生用户：schooId; 企业用户：comId
	@Column(name = "related_id")
	private String relatedId = "";

	//上传认证材料的状态(跳转到认证页面【上传认证材料由后台认证管理员（2）、上传认证材料由认证普通用户标识（1）】);
	//若是当前用户注册的公司uploadTag设置为（0）
	//若是公司审核过期，当前用户所在重新申请审核设置为（4）
	@Column(name = "upload_tag", length = 4)
	private String uploadTag = "";

	//创建时间
	@Column(name = "create_date", updatable = false)
	private Date createDate = new Date();

	//修改时间
	@Column(name = "update_date")
	private Date updateDate = new Date();

	/**
	 * 企业用户审核 
	 */
	//审核时间
	@Column(name = "check_date")
	private Date checkDate;

	//审核有效期
	@Column(name = "check_valid_date")
	private Date checkValidDate;

	//审核不通过原因
	@Column(name = "check_reason", length = 16)
	@Length(max = 16)
	private String checkReason = "";

	//审核不通过描述
	@Length(max = 200)
	@Column(name = "description", length = 200)
	private String description = "";

	//注册公司时待审核提交时间（工作台统计  按天数查询统计待审核企业数）
	@Column(name = "wait_check_date")
	private Date waitCheckDate;

	//其他(判断是否上传这个图片)
	@Column(name = "commore")
	private String commore = "false";

	public Date getWaitCheckDate() {
		return waitCheckDate;
	}

	public void setWaitCheckDate(Date waitCheckDate) {
		this.waitCheckDate = waitCheckDate;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public Date getCheckValidDate() {
		return checkValidDate;
	}

	public void setCheckValidDate(Date checkValidDate) {
		this.checkValidDate = checkValidDate;
	}

	public String getCheckReason() {
		return checkReason;
	}

	public void setCheckReason(String checkReason) {
		this.checkReason = StringUtils.trimAllWhitespace(checkReason);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = StringUtils.trimAllWhitespace(description);
	}

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
		this.realName = StringUtils.trimAllWhitespace(realName);
	}

	public String getAvatorUrl() {
		return avatorUrl;
	}

	public void setAvatorUrl(String avatorUrl) {
		this.avatorUrl = StringUtils.trimAllWhitespace(avatorUrl);
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = StringUtils.trimAllWhitespace(gender);
	}

	public String getAuthedStatus() {
		return authedStatus;
	}

	public void setAuthedStatus(String authedStatus) {
		this.authedStatus = StringUtils.trimAllWhitespace(authedStatus);
	}

	public String getRelatedId() {
		return relatedId;
	}

	public void setRelatedId(String relatedId) {
		this.relatedId = StringUtils.trimAllWhitespace(relatedId);
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

	public String getUploadTag() {
		return uploadTag;
	}

	public void setUploadTag(String uploadTag) {
		this.uploadTag = StringUtils.trimAllWhitespace(uploadTag);
	}

	public Information getInformation() {
		return information;
	}

	public void setInformation(Information information) {
		this.information = information;
	}

	public String getCommore() {
		return commore;
	}

	public void setCommore(String commore) {
		this.commore = commore;
	}

}
