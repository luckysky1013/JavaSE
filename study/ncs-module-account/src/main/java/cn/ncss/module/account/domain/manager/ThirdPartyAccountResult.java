package cn.ncss.module.account.domain.manager;

import java.io.Serializable;
import java.util.List;

import cn.ncss.module.account.domain.ThirdPartyAccount;

public class ThirdPartyAccountResult implements Serializable {

	private static final long serialVersionUID = -5109468163477949970L;

	private List<ThirdPartyAccount> thirdPartyAccounts;

	public List<ThirdPartyAccount> getThirdPartyAccounts() {
		return thirdPartyAccounts;
	}

	public void setThirdPartyAccounts(List<ThirdPartyAccount> thirdPartyAccounts) {
		this.thirdPartyAccounts = thirdPartyAccounts;
	}

}
