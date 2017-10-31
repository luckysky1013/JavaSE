package cn.ncss.module.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {

	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;

	/*	@Bean
		public JedisConnectionFactory connectionFactory() {
			return new JedisConnectionFactory();
		}
	*/
	@Bean
	public HttpSessionStrategy httpSessionStrategy() {
		return new HeaderHttpSessionStrategy();
	}

	//对缓存设置过期时间
	@Bean
	public RedisCacheManager redisCacheManager() {
		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);

		// Number of seconds before expiration. Defaults to unlimited (0)  
		cacheManager.setDefaultExpiration(10); // Sets the default expire time (in seconds)  
		/*	Map<String, Long> expires = new HashMap<>();
			expires.put("schools", 10l);
			cacheManager.setExpires(expires);*/
		return cacheManager;
	}
}