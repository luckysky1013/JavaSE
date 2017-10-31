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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 简历其他项
 * @author LiYang
 *
 */
@Entity
@Table(name = "resume_item_meta")
public class ItemMeta implements Serializable {

	private static final long serialVersionUID = 9039476725849886680L;
	@Id
	@Column(name = "id", length = 22, nullable = false, updatable = false)
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.ncss.commom.Base58UuidGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;
	//标题
	@NotBlank(message = "标题不能为空")
	@Length(max = 64, message = "标题范围应该在{min}~{max}之间")
	@Column(name = "label", length = 64)
	private String label;
	@Transient
	private String contentHtml;
	//内容
	@NotBlank(message = "内容不能为空")
	@Length(max = 5000, message = "内容范围应该在{min}~{max}之间")
	@Column(name = "content", length = 5000)
	private String content;
	//排序
	@Column(name = "sn")
	private Integer sn;
	//简历
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resume_id", nullable = false)
	private Resume resume;

	@Column(name = "create_date", updatable = false)
	private Date createDate = new Date();

	@Column(name = "update_date")
	private Date updateDate = new Date();

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = StringUtils.trimAllWhitespace(label);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentHtml() {
		String html = this.content.replaceAll("\r\n", "<br />").replaceAll("\n", "<br />").replaceAll("\r", "<br />")
				.replaceAll("  ", "&nbsp;&nbsp");
		return html;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	/*@Override
	public int compareTo(ItemMeta arg0) {
		return this.getSn().compareTo(arg0.getSn());
	}
	*/
}
