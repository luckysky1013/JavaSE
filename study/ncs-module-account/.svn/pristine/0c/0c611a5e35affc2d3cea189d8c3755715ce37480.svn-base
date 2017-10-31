package cn.ncss.module.account;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * shiro 相关配置以及session共享
 * @author kyrin,liuyang
 *
 */
@Configuration
@EnableConfigurationProperties({ ShiroMainProperties.class })
public class ShiroConfig {
	//shiro配置
	@Autowired
	private ShiroMainProperties shiroMainProperties;

	/**
	 * redis 共享session , 修改session的共享域 
	 * @return
	 */
	@Bean
	public ServletContainerSessionManager servletContainerSessionManager() {
		ServletContainerSessionManager d = new ServletContainerSessionManager();
		//d.getSessionIdCookie().setDomain(cookie_domain);
		return d;
	}

	@Bean(name = "securityManager")
	public DefaultWebSecurityManager defaultWebSecurityManager() {
		DefaultWebSecurityManager dsm = new DefaultWebSecurityManager();
		//List<Realm> realms = new ArrayList<>();
		//realms.add(ncssRealm());
		//realms.add(chsiRealm());
		//realms.add(qqRealm());
		//realms.add(weiboRealm());
		//dsm.setRealms(realms);
		//dsm.setSubjectFactory(defaultWebSubjectFactory());
		//dsm.setSessionManager(defaultWebSessionManager());
		//设置 自动登录
		dsm.setRememberMeManager(rememberMeManager());
		dsm.setSessionManager(servletContainerSessionManager());
		return dsm;
	}

	@Bean(name = "shiroFilterFactoryBean")
	public ShiroFilterFactoryBean shiroFilterFactoryBean() {
		ShiroFilterFactoryBean sf = new ShiroFilterFactoryBean();
		sf.setSecurityManager(defaultWebSecurityManager());
		//sf.setSuccessUrl(shiroMainProperties.getSuccessUrl());
		//sf.setLoginUrl(shiroMainProperties.getLoginUrl());
		//sf.setUnauthorizedUrl(shiroMainProperties.getUnauthorizedUrl());
		//Map<String, Filter> filters = new HashMap<>();
		//添加过滤器
		//filters.put("CHSI", chsiFilter());
		//filters.put("QQ", qqFilter());
		//filters.put("WEIXIN", weixinFilter());
		//filters.put("WEIBO", weiboFilter());
		//sf.setFilters(filters);
		//sf.setFilterChainDefinitionMap(shiroMainProperties.getFilterChainDefinitionMap());
		return sf;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor auth = new AuthorizationAttributeSourceAdvisor();
		auth.setSecurityManager(defaultWebSecurityManager());
		return auth;
	}

	/*@Bean
	public Realm ncssRealm() {
		return new NcssRealm();
	}*/

	//shiro“记住密码”配置
	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie rememberMeCookie = new SimpleCookie("rememberMeCookie");
		rememberMeCookie.setHttpOnly(true);
		rememberMeCookie.setMaxAge(10 * 24 * 3600);
		return rememberMeCookie;
	}

	@Bean
	public CookieRememberMeManager rememberMeManager() {
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		rememberMeManager.setCipherKey(Base64.decode("FjbNm1avvGmWE9CY2HqV75=="));
		rememberMeManager.setCookie(rememberMeCookie());
		return rememberMeManager;
	}
}
