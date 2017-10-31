package cn.ncss.module.account;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * Objects and their properties are defined here, Such as the securityManager, Realms and anything
 * else needed to build the SecurityManager
 * @author liujun
 */
@ConfigurationProperties(prefix = "shiro.main")
public class ShiroMainProperties {
	private String loginUrl = "/login.html";
	private String successUrl = "/";
	private String unauthorizedUrl = "/unauthorized.html";
	private Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	protected Map<String, String> getFilterChainDefinitionMap() {
		return filterChainDefinitionMap;
	}

	protected void setFilterChainDefinitionMap(Map<String, String> filterChainDefinitionMap) {
		this.filterChainDefinitionMap = filterChainDefinitionMap;
	}

	public void setFilters(String filters) {
		if (StringUtils.isEmpty(filters)) {
			return;
		}
		String[] chainDefinitionsArray = filters.split(";");
		for (String filter : chainDefinitionsArray) {
			String[] o = filter.split("=");
			filterChainDefinitionMap.put(o[0], o[1]);
		}
	}
}
