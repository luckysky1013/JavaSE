package cn.ncss.module.account.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import cn.ncss.module.account.domain.UserActionLog;
import cn.ncss.module.account.repository.ActionLogRepository;

/**
 * account接口日志
 * @author liyang
 *
 */
@Component
public class UserActionLogHandler {
	@Autowired
	private ActionLogRepository actionLogRepository;

	//存日志
	@Async
	public void saveLog(UserActionLog userActionLog) {
		actionLogRepository.save(userActionLog);
	}

}
