package cn.ncss.module.account.domain.manager;

import java.io.Serializable;
import java.util.List;

/**
 * 企业用户result
 * @author liyang
 *
 */
public class CorpUsersSearchResult implements Serializable {

	private static final long serialVersionUID = -8644739478388788371L;
	private List<CorpUsers> corpUsers;
	private long total;
	private int limit;
	private int offset;

	public List<CorpUsers> getCorpUsers() {
		return corpUsers;
	}

	public void setCorpUsers(List<CorpUsers> corpUsers) {
		this.corpUsers = corpUsers;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
