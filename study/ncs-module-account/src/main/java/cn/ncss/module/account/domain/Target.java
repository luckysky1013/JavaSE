package cn.ncss.module.account.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cn.ncss.commom.PubCodeFactory;

/**
 * 求职期望
 * @author LiYang
 */
@Entity
@Table(name = "resume_target")
public class Target implements Serializable {

	private static final long serialVersionUID = 8948671290153827894L;
	/**
	 * jobType
	 */
	//全职
	public static final String JOBTYPE_QZ = "全职";
	//兼职
	public static final String JOBTYPE_JZ = "兼职";
	//实习
	public static final String JOBTYPE_SX = "实习";

	@Id
	@Column(name = "target_id", length = 22, nullable = false, updatable = false)
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.ncss.commom.Base58UuidGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String targrtId;
	//简历
	@JsonIgnore
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resume_id", nullable = false)
	private Resume resume;
	//期望地区列表
	/*@NotEmpty
	@Type(type = "array")
	@Column(name = "areas", columnDefinition = "varchar[10]")*/
	@NotBlank(message = "期望地区不能为空")
	private String areas;
	//期望行业列表
	/*@Type(type = "array")
	@Column(name = "industries", columnDefinition = "varchar[20]")*/
	@NotBlank(message = "期望行业不能为空")
	private String industries;

	//期望薪酬范围
	/*@NotBlank
	@Length(max = 16, message = "期望薪酬范围长度应该在{min}~{max}之间")
	@Column(name = "salary_scope", length = 16)
	private String salaryScope;*/
	@NotBlank(message = "期望薪资不能为空")
	@Length(max = 16, message = "期望薪酬范围应该在{min}~{max}之间")
	@Pattern(regexp = "(^[0-9]*$)?", message = "请填写数字")
	@Column(name = "low_money", length = 10)
	private String lowMoney;
	@NotBlank(message = "期望薪资不能为空")
	@Length(max = 16, message = "期望薪酬范围应该在{min}~{max}之间")
	@Pattern(regexp = "(^[0-9]*$)?", message = "请填写数字")
	@Column(name = "high_money", length = 10)
	private String highMoney;

	//期望工作类型
	//@Enumerated(EnumType.STRING)
	@NotNull(message = "期望工作类型不能为空")
	@Column(name = "job_type")
	@Length(max = 16)
	private String jobType = JOBTYPE_QZ;

	//期望岗位类别期望职位类别
	/*@Type(type = "array")
	@Column(name = "jobposition", columnDefinition = "varchar[20]")*/
	@NotBlank(message = "期望岗位类别不能为空")
	@Length(max = 64, message = "期望岗位长度应该在{min}~{max}之间")
	@Column(name = "job_position", length = 64)
	private String jobPosition;

	@Column(name = "create_date", updatable = false)
	private Date createDate = new Date();

	@Column(name = "update_date")
	private Date updateDate = new Date();

	public String getAreas() {
		return areas;
	}

	public String getIndustries() {
		return industries;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setAreas(String areas) {
		String[] is = areas.split(",");
		List<String> areasList = Arrays.asList(is);
		if (PubCodeFactory.getInstance().getAreaCode().keySet().containsAll(areasList)) {
			this.areas = areas;
		}

	}

	public void setIndustries(String industries) {
		String[] is = industries.split(",");
		List<String> industriesList = Arrays.asList(is);
		if (PubCodeFactory.getInstance().getIndustryCode().keySet().containsAll(industriesList)) {
			this.industries = industries;
		}
	}

	public void setJobPosition(String jobPosition) {
		String[] is = jobPosition.split(",");
		List<String> jobPositionList = Arrays.asList(is);
		if (PubCodeFactory.getInstance().getJobcategoryCode().keySet().containsAll(jobPositionList)) {
			this.jobPosition = jobPosition;
		}

	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = StringUtils.trimAllWhitespace(jobType);
	}

	public String getLowMoney() {
		return lowMoney;
	}

	public void setLowMoney(String lowMoney) {
		this.lowMoney = StringUtils.trimAllWhitespace(lowMoney);
	}

	public String getHighMoney() {
		return highMoney;
	}

	public void setHighMoney(String highMoney) {
		this.highMoney = StringUtils.trimAllWhitespace(highMoney);
	}

	public String getTargrtId() {
		return targrtId;
	}

	public void setTargrtId(String targrtId) {
		this.targrtId = targrtId;
	}

	public Resume getResume() {
		return resume;
	}

	public void setResume(Resume resume) {
		this.resume = resume;
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

	public Target() {
	}
	/*//返回代码和汉字名称的对应map
	public Map<String, String> getAreasMap() {
		Map<String, String> map = new HashMap<String, String>();
		for (String str : areas) {
			Object val = PubCodeFactory.getInstance().getAreaCode().get(str);
			map.put(str, val == null ? "" : val.toString());
		}
		return map;
	}
	
	
	public void setIndustries(List<String> industries) {
		List<String> list = new ArrayList<String>();
		if (industries != null) {
			for (String str : industries) {
				if (StringUtils.hasText(str)) {
					if (PubCodeFactory.getInstance().getIndustryCode().keySet().contains(str)) {
						list.add(str);
					}
				}
			}
		}
		this.industries = list;
	}
	*/
}
