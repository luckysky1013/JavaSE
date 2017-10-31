package cn.ncss.module.account.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

/**
 * 用户操作日志
 * 
 */
@Entity
@Table(name = "user_action_log")
public class UserActionLog implements Serializable {

	private static final long serialVersionUID = 9143222812340471276L;

	@Id
	@Column(name = "action_id", length = 22, nullable = false, unique = true, updatable = false)
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.ncss.commom.Base58UuidGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	private String id;

	//操作人ID
	@Column(length = 32)
	private String userId;

	//被操作人ID
	@Column(length = 32)
	private String operateObjectId;
	//操作类型
	@Enumerated(EnumType.STRING)
	@Column(name = "action_type", length = 32)
	private ActionType actiontype;

	//操作内容描述
	@Column(name = "content", length = 256)
	private String content;

	//操作日期
	@Column(name = "create_date")
	private Date createDate = new Date();

	//ip
	@Length(min = 0, max = 32)
	@Column(name = "IP", length = 32, updatable = false)
	private String ip;

	//操作状态，true or false
	@Column(name = "status")
	private String status = "true";

	public UserActionLog() {

	}

	public UserActionLog(String userId, String operateObjectId, ActionType actiontype, String content, String ip,
			String status) {
		super();
		this.userId = userId;
		this.operateObjectId = operateObjectId;
		this.actiontype = actiontype;
		this.content = content;
		this.ip = ip;
		this.status = status;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOperateObjectId() {
		return operateObjectId;
	}

	public void setOperateObjectId(String operateObjectId) {
		this.operateObjectId = operateObjectId;
	}

	public ActionType getActiontype() {
		return actiontype;
	}

	public void setActiontype(ActionType actiontype) {
		this.actiontype = actiontype;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public enum ActionType {
		//更新用户信息，创建用户信息，删除用户 ，
		USER_UPDATE, USER_CREATE, USER_DELETE,
		//绑定第三方账号、修改、解绑，
		THIRDPARTY_CREATE, THIRDPARTY_UPDATE, THIRDPARTY_DELETE,
		//更新简历，教育背景添加、修改、删除，
		RESUME_UPDATE, EDUCATION_CREATE, EDUCATION_UPDATE, EDUCATION_DELETE,
		//求职期望添加、修改、删除，
		TARGET_CREATE, TARGET_UPDATE, TARGET_DELETE,
		//简历项添加、修改、修改顺序、删除
		ITEMMETA_CREATE, ITEMMETA_UPDATE, ITEMMETA_DELETE,
		//基本信息添加、修改、删除，上传头像
		PROFILE_CREATE, PROFILE_UPDATE, PROFILE_DELETE;

	}

}
