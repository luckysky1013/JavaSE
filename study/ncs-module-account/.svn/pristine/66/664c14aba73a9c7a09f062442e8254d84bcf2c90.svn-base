package cn.ncss.module.account.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 第三方登录账号信息
 * @author kyrin
 *
 */
@Entity
@Table(name = "third_party_account")
public class ThirdPartyAccount {

	@Id
	@GenericGenerator(name = "uuidGenerator", strategy = "cn.ncss.commom.Base58UuidGenerator")
	@GeneratedValue(generator = "uuidGenerator")
	public String id;

	//关联用户ID
	//@JsonIgnore
	@ManyToOne(optional = true)
	@JoinColumn(name = "user_id")
	public UserAccount userAccount;

	//标示唯一的第三方用户，从第三方获取
	@Column(name = "openid", nullable = false, unique = true)
	public String openId;

	@Column(name = "nickname", nullable = false)
	public String nickname;

	//CHSI, QQ, WEIXIN, WEIBO
	@Column(name = "type", nullable = false)
	//@Enumerated(EnumType.STRING)
	public String type;

	@Column(name = "access_token")
	public String accessToken;

	@Column(name = "refresh_token")
	public String refreshToken;

	@Column(name = "expire_in")
	public Date expireIn;

	@Column(name = "bind_date", updatable = false)
	public Date bindDate;

	@Column(name = "last_login_date")
	public Date lastLoginDate;

	public ThirdPartyAccount() {
		super();
	}

	public ThirdPartyAccount(String id, UserAccount userAccount, String openId, String nickname, String type,
			String accessToken, String refreshToken, Date expireIn, Date bindDate, Date lastLoginDate) {
		super();
		this.id = id;
		this.userAccount = userAccount;
		this.openId = openId;
		this.nickname = nickname;
		this.type = type;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expireIn = expireIn;
		this.bindDate = bindDate;
		this.lastLoginDate = lastLoginDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Date getExpireIn() {
		return expireIn;
	}

	public void setExpireIn(Date expireIn) {
		this.expireIn = expireIn;
	}

	public Date getBindDate() {
		return bindDate;
	}

	public void setBindDate(Date bindDate) {
		this.bindDate = bindDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/*	public enum ThirdPartyAccountType {
			*//**
				* 学信
				*/
	/*
	CHSI, QQ, WEIXIN, WEIBO
	}*/

}
