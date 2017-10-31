package cn.ncss.module.account.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.TypeDef;

import cn.ncss.commom.HstoreUserType;

/**
 * 简历
 * @author LiYang
 */
@Entity
@Table(name = "resume")
@TypeDef(name = "hstore", typeClass = HstoreUserType.class)
public class Resume implements Serializable {
	private static final long serialVersionUID = -7867988736420456875L;

	@Id
	@Column(name = "id", length = 22, nullable = false, updatable = false)
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.ncss.commom.Base58UuidGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	//用户信息
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Profile.class)
	@JoinColumn(name = "user_id")
	private Profile profile;
	//求职期望
	@OneToOne(targetEntity = Target.class, mappedBy = "resume", cascade = CascadeType.ALL)
	private Target target;
	//教育背景
	@OrderBy(value = "startDate ASC,endDate ASC")
	@OneToMany(targetEntity = EducationHistory.class, mappedBy = "resume", cascade = CascadeType.ALL)
	private List<EducationHistory> educations;
	//简历项
	@OneToMany(targetEntity = ItemMeta.class, mappedBy = "resume", cascade = CascadeType.ALL)
	private List<ItemMeta> itemMeta;
	/*//其他简历项
	@Lazy
	@Type(type = "hstore")
	@Column(name = "other_items", columnDefinition = "hstore")
	private Map<String, String> otherItems = new HashMap<String, String>();*/
	// 简历的浏览次数
	@Column(name = "view_count")
	private int viewCount = 0;
	//简历得到的评分
	@Column(name = "score")
	private int score = 0;

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

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public List<EducationHistory> getEducations() {
		return educations;
	}

	public void setEducations(List<EducationHistory> educations) {
		this.educations = educations;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<ItemMeta> getItemMeta() {
		return itemMeta;
	}

	public void setItemMeta(List<ItemMeta> itemMeta) {
		this.itemMeta = itemMeta;
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

	/**
	 * photo的base64字符串
	 * @return
	 */
	/*@Transient
	public String getPhotoBase64() {
		return Base64.encodeBase64String(this.photo);
	}*/

}
