package cn.ncss.module.account.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ncss.module.account.repository.AccountUsersQueryRepository;
import cn.ncss.module.account.repository.ProfileRepository;

/**
 * 后台manager工作台
 * @author liyang
 *
 */
@Transactional(readOnly = true)
@RestController
public class ManagerWorkController {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private AccountUsersQueryRepository userAccountRepository;

	/**
	 * 用户注册数量统计(按天数)
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	@GetMapping(value = "/registerlogByDay")
	public ResponseEntity<List<Object>> findListByDay(Date startDate, Date endDate) throws ParseException {
		endDate = getDate(endDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Object> log = userAccountRepository.findListByDay(sdf.format(startDate), sdf.format(endDate), 15);
		return ResponseEntity.ok(log);
	}

	/**
	 * 用户注册数量统计（按月统计）
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	@GetMapping(value = "/registerlogByMonth")
	public ResponseEntity<List<Object>> findListByMonth(Date startDate, Date endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Object> log = userAccountRepository.findListByMonth(sdf.format(startDate), sdf.format(endDate), 12);
		return ResponseEntity.ok(log);
	}

	@GetMapping(value = "/regcount")
	public ResponseEntity<Integer> findcount() throws ParseException {
		int count = userAccountRepository.findcount(LocalDate.now().toString());
		return ResponseEntity.ok(count);
	}

	/**
	 * 用户待审核企业数量统计(按天数)
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	@GetMapping(value = "/corplogByDay")
	public ResponseEntity<List<Object>> findListCorpByDay(Date startDate, Date endDate) throws ParseException {
		endDate = getDate(endDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Object> log = profileRepository.findListCorpByDay(sdf.format(startDate), sdf.format(endDate), 15);
		return ResponseEntity.ok(log);
	}

	/**
	 * 用户待审核企业注册数量统计（按月统计）
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	@GetMapping(value = "/corplogByMonth")
	public ResponseEntity<List<Object>> findListCorpByMonth(Date startDate, Date endDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Object> log = profileRepository.findListCorpByMonth(sdf.format(startDate), sdf.format(endDate), 12);
		return ResponseEntity.ok(log);
	}

	/**
	 * 待审核企业个数
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	@GetMapping(value = "/corpconut")
	public ResponseEntity<Integer> corpConut() throws ParseException {
		int count = profileRepository.findcount(LocalDate.now().toString());
		return ResponseEntity.ok(count);
	}

	private Date getDate(Date endDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
		Date date = calendar.getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.SECOND, -1);
		date = c.getTime();
		return date;

	}

}
