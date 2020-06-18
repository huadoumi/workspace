package tc.platform;

import galaxy.ide.tech.cpt.Component;
import galaxy.ide.tech.cpt.ComponentGroup;
import galaxy.ide.tech.cpt.InParams;
import galaxy.ide.tech.cpt.OutParams;
import galaxy.ide.tech.cpt.Param;
import galaxy.ide.tech.cpt.Return;
import galaxy.ide.tech.cpt.Returns;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.agree.afa.jcomponent.ErrorCode;
import cn.com.agree.afa.svc.javaengine.TCResult;
import cn.com.agree.afa.svc.javaengine.context.JavaList;

/**
 * 时间判断类组件
 * 
 * @date 2015-07-01 10:22:19
 */
@ComponentGroup(level = "平台", groupName = "时间判断类组件")
public class P_Time {

	/**
	 * @param tradedate
	 *            出参|返回获取的日期|{@link java.lang.String}
	 * @param tradetime
	 *            出参|返回获取的时间|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 * 
	 */
	@OutParams(param = {
			@Param(name = "tradedate", comment = "返回获取的日期", type = java.lang.String.class),
			@Param(name = "tradetime", comment = "返回获取的时间", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "获取交易时间", style = "判断型", type = "同步组件", comment = "返回系统时间、日期。系统时间串,格式为'HHmmss'，系统日期串,格式为'yyyyMMdd'", date = "Thu Jul 09 17:19:32 CST 2015")
	public static TCResult getTime() {
		SimpleDateFormat tradetime_sdf = new SimpleDateFormat("HHmmss");
		SimpleDateFormat tradedate_sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar calendar = Calendar.getInstance();
		String tradetime = tradetime_sdf.format(calendar.getTime());
		String datetime = tradedate_sdf.format(calendar.getTime());
		return TCResult.newSuccessResult(datetime, tradetime);
	}

	/**
	 * @param sTime
	 *            入参|日期|{@link java.lang.String}
	 * @param sFmt
	 *            入参|格式化字符串|{@link java.lang.String}
	 * @param result
	 *            出参|格式化后的时间|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "sTime", comment = "日期", type = java.lang.String.class),
			@Param(name = "sFmt", comment = "格式化字符串", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "result", comment = "格式化后的时间", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "时间格式化", style = "判断型", type = "同步组件", comment = "时间格式化,sTime:日期 支持格式:115959、11:59:59、11点59分59秒、11 59 59、11/59/59、11-59-59\nsFmt:格式化字符串 支持格式:hh:mm:ss、hh-mm-ss、hh/mm/ss、ss/mm/hh", date = "Wed Jul 01 10:50:56 CST 2015")
	public static TCResult formatTime(String sTime, String sFmt) {
		if (sTime == null || sFmt == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数sTime 或者 sFmt 不能为空值！");
		}
		String hh = "00";
		String mm = "00";
		String ss = "00";
		String partern = "^([0-2]?[0-9])\\D{0,2}([0-6]?[0-9])\\D{0,2}([0-6]?[0-9])\\D{0,2}$";
		boolean ma = sTime.matches(partern);
		if (!ma) {
			return TCResult.newFailureResult(ErrorCode.TIMEFORMAT, "时间格式不合法!");
		} else {
			Pattern p = Pattern.compile(partern);
			Matcher matcher = p.matcher(sTime);

			while (matcher.find()) {
				hh = matcher.group(1);
				mm = matcher.group(2);
				ss = matcher.group(3);
				hh = String.format("%02d", Integer.valueOf(hh));
				mm = String.format("%02d", Integer.valueOf(mm));
				ss = String.format("%02d", Integer.valueOf(ss));
			}
		}
		String result = sFmt.replace("hh", hh).replace("mm", mm)
				.replace("ss", ss);
		return TCResult.newSuccessResult(result);
	}

	/**
	 * @param sDate
	 *            入参|日期|{@link java.lang.String}
	 * @param sFmt
	 *            入参|格式化字符串|{@link java.lang.String}
	 * @param result
	 *            出参|格式化后的日期|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "sDate", comment = "日期", type = java.lang.String.class),
			@Param(name = "sFmt", comment = "格式化字符串", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "result", comment = "格式化后的日期", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "日期格式化", style = "判断型", type = "同步组件", comment = "日期格式化,sDate:日期 支持格式:20090701、200971、2009年7月1日、2009年07月01日、2009/7/1、2009/07/01、2009-2-2、2009-02-02、2009.2.2、2009.02.02、2009-7-01、2009-07-1\n               sFmt:格式化字符串 支持格式:'yyyy-mm-dd'、'yyyy年mm月dd日'、'yyyy/mm/dd'、'yyyy/mm/dd'、'yyyy.mm.dd'、'dd/mm/yyyy'、'mm.dd.yyyy'", date = "Wed Jul 01 11:22:46 CST 2015")
	public static TCResult formatDate(String sDate, String sFmt) {
		if (sDate == null || sFmt == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数sDate 或者 sFmt 不能为空值！");
		}
		return formatDate0(sDate, sFmt);
	}

	private static TCResult formatDate0(String sDate, String sFmt) {
		sDate = sDate.trim();
		String yyyy = "0000";
		String mm = "00";
		String dd = "00";
		String pattern = "^([1-9][0-9][0-9][0-9])\\D{0,2}([0-1]?[0-9])\\D{0,2}([0-3]?[0-9])\\D{0,2}$";

		// 校验传入的日期是否是合法日期
		boolean ma = sDate.matches(pattern);
		if (!ma) {
			return TCResult.newFailureResult(ErrorCode.TIMEFORMAT, "日期格式不合法");
		} else {
			Pattern p = Pattern.compile(pattern);
			Matcher matcher = p.matcher(sDate);

			while (matcher.find()) {
				yyyy = matcher.group(1);
				mm = matcher.group(2);
				dd = matcher.group(3);
				yyyy = String.format("%04d", Integer.valueOf(yyyy));
				mm = String.format("%02d", Integer.valueOf(mm));
				dd = String.format("%02d", Integer.valueOf(dd));
			}
		}
		String result = sFmt.replace("yyyy", yyyy).replace("mm", mm)
				.replace("dd", dd);
		return TCResult.newSuccessResult(result);
	}

	/**
	 * @param sDate
	 *            入参|日期|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "sDate", comment = "日期", type = java.lang.String.class) })
	@OutParams(param = {})
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "校验日期是否合法", style = "判断型", type = "同步组件", comment = "判断日期是否有效,包括月份的判断,每月天数的判断,年份判断;支持格式: 20081129", date = "Wed Jul 01 10:55:20 CST 2015")
	public static TCResult formatCheckTime(String sDate) {
		if (sDate == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数 sDate 不能为空值！");
		}
		if (sDate.length() != 8) {
			return TCResult.newFailureResult(ErrorCode.AGR, "日期长度不等于8，不合法");
		}

		int yy = Integer.valueOf(sDate.substring(0, 4));
		int mm = Integer.valueOf(sDate.substring(4, 6));
		int dd = Integer.valueOf(sDate.substring(6, 8));
		if (yy < 1940 && yy > 3000) {
			return TCResult.newFailureResult(ErrorCode.DATAOUTOFRANG,
					"年取值范围 1940 ~ 2999");
		} else if (mm < 0 && mm > 13) {
			return TCResult.newFailureResult(ErrorCode.DATAOUTOFRANG,
					"月取值范围 1 ~ 12");
		} else if (mm == 1 || mm == 3 || mm == 5 || mm == 7 || mm == 8
				|| mm == 10 || mm == 12) {
			if (dd < 1 && dd > 31) {
				return TCResult.newFailureResult(ErrorCode.DATAOUTOFRANG,
						"该月天数不符合");
			}
		} else if (mm == 9 || mm == 4 || mm == 6 || mm == 11) {
			if (dd < 1 && dd > 30) {
				return TCResult.newFailureResult(ErrorCode.DATAOUTOFRANG,
						"该月天数不符合");
			}
		} else if (mm == 2) {
			if (dd < 1 && dd > 29) {
				return TCResult.newFailureResult(ErrorCode.DATAOUTOFRANG,
						"该月天数不符合");
			}
		}
		return TCResult.newSuccessResult();
	}

	/**
	 * @param sDate
	 *            入参|起始日期|{@link java.lang.String}
	 * @param sDays
	 *            入参|天数|long
	 * @param times
	 *            出参|相加后的天数|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "sDate", comment = "起始日期", type = java.lang.String.class),
			@Param(name = "sDays", comment = "天数", type = long.class) })
	@OutParams(param = { @Param(name = "times", comment = "相加后的天数", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "日期计算", style = "判断型", type = "同步组件", comment = "一个日期往后加一个天数,得到一个新日期 支持格式: 20081129", date = "Wed Jul 01 11:02:59 CST 2015")
	public static TCResult calculateTime(String sDate, long sDays) {
		if (sDate == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数sDate不能为空值！");
		}
		int yy = Integer.valueOf(sDate.substring(0, 4));
		int mm = Integer.valueOf(sDate.substring(4, 6));
		int dd = Integer.valueOf(sDate.substring(6, 8));
		Calendar calendar = Calendar.getInstance();
		calendar.set(yy, mm, dd);
		long now = calendar.getTimeInMillis();
		now += (sDays * 24 * 60 * 60 * 1000);
		calendar.setTimeInMillis(now);
		String result = String.valueOf(calendar.get(Calendar.YEAR))
				+ String.format("%02d", calendar.get(Calendar.MONTH))
				+ String.format("%02d", calendar.get(Calendar.DATE));
		return TCResult.newSuccessResult(result);
	}

	/**
	 * @param dateList
	 *            入参|日期list|
	 *            {@link cn.com.agree.afa.svc.javaengine.context.JavaList}
	 * @param sFmt
	 *            入参|格式化字符串|{@link java.lang.String}
	 * @param outlist
	 *            出参|格式化后的日期list|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "dateList", comment = "日期list", type = cn.com.agree.afa.svc.javaengine.context.JavaList.class),
			@Param(name = "sFmt", comment = "格式化字符串", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "outlist", comment = "格式化后的日期list", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "批量日期格式化", style = "判断型", type = "同步组件", comment = "批量日期格式化,Datelist:日期 支持格式:20090701、200971、2009年7月1日、2009年07月01日、2009/7/1、2009/07/01、2009-2-2、2009-02-02、2009.2.2、2009.02.02、2009-7-01、2009-07-1\n               sFmt:格式化字符串 支持格式:'yyyy-mm-dd'、'yyyy年mm月dd日'、'yyyy/mm/dd'、'yyyy/mm/dd'、'yyyy.mm.dd'、'dd/mm/yyyy'、'mm.dd.yyyy'", date = "Wed Jul 01 11:07:40 CST 2015")
	public static TCResult formatMultidate(JavaList dateList, String sFmt) {
		if (dateList == null || sFmt == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数dateList 或者 sFmt 不能为空值！");
		}
		JavaList resultList = new JavaList();
		for (int i = 0; i < dateList.size(); i++) {
			TCResult result = formatDate0((String) dateList.get(i), sFmt);
			if (result.getStatus() != 0) {
				resultList.add(result.getOutputParams());
			} else {
				return result;
			}

		}
		return TCResult.newSuccessResult(resultList);
	}

	/**
	 * @param date
	 *            入参|起始日期|{@link java.lang.String}
	 * @param months
	 *            入参|月数|int
	 * @param sEndDate
	 *            出参|计算后的日期|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "date", comment = "起始日期", type = java.lang.String.class),
			@Param(name = "months", comment = "月数", type = int.class) })
	@OutParams(param = { @Param(name = "sEndDate", comment = "计算后的日期", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "日期计算", style = "判断型", type = "同步组件", comment = "日期按月计算", date = "Wed Jul 01 11:09:59 CST 2015")
	public static TCResult calculateMonth(String date, int months) {
		if (date == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数date不能为空值！");
		}
		int[] nDayInMonth = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		int year = Integer.valueOf(date.substring(0, 4));
		int month = Integer.valueOf(date.substring(4, 6));
		int day = Integer.valueOf(date.substring(6, 8));
		int tmpY = year;
		int mm = Integer.valueOf(months);
		if (month == 12 || month + mm > 12) {
			year = year + 1;
			month = month - 12 + mm;
		} else {
			month = month + mm;
		}

		if (day > nDayInMonth[month - 1]) {
			day = nDayInMonth[month - 1];
			if (month == 2
					&& (tmpY % 400 == 0 || (tmpY % 4 == 0 && tmpY % 100 != 0))) {
				day = nDayInMonth[month - 1] + 1;
			}
		}
		String sEndDate = String.valueOf(year) + String.format("%02d", month)
				+ String.format("%02d", day);
		return TCResult.newSuccessResult(sEndDate);
	}

	/**
	 * @param format
	 *            入参|日期时间格式串|{@link java.lang.String}
	 * @param datetime
	 *            出参|获取的日期时间|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "format", comment = "日期时间格式串", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "datetime", comment = "获取的日期时间", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "参数化取当前日期时间", style = "判断型", type = "同步组件", comment = "根据输入的日期时间类型来参数化获取日期时间串,如果format为timestamp,则返回20位的时间戳,如果输入long,则返回相对于UTC时间的秒数", date = "Wed Jul 01 11:11:22 CST 2015")
	public static TCResult getFormatTime(String format) {
		if (format == null) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数 format 不能为空！");
		}
		Calendar calendar = Calendar.getInstance();
		DateFormat df3 = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS");
		String nowdatetime = null;
		if (format.equals("timestamp")) {
			nowdatetime = df3.format(calendar.getTime());
		} else if (format.equals("long")) {
			nowdatetime = String.valueOf(calendar.getTimeInMillis() / 1000);
		} else {
			DateFormat df = new SimpleDateFormat(format);
			nowdatetime = df.format(calendar.getTime());
		}
		return TCResult.newSuccessResult(nowdatetime);
	}

	/**
	 * @param srctime
	 *            入参|源时间(YYYYMMDDHHMMSS)|{@link java.lang.String}
	 * @param dsttime
	 *            入参|目标时间(YYYYMMDDHHMMSS)|{@link java.lang.String}
	 * @param timeunit
	 *            入参|时间单位(seconds/minutes/hours/days)|{@link java.lang.String}
	 * @param timediff
	 *            出参|计算出的差值|int
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "srctime", comment = "源时间(YYYYMMDDHHMMSS)", type = java.lang.String.class),
			@Param(name = "dsttime", comment = "目标时间(YYYYMMDDHHMMSS)", type = java.lang.String.class),
			@Param(name = "timeunit", comment = "时间单位(seconds/minutes/hours/days)", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "timediff", comment = "计算出的差值", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "参数化计算两个时间差值", style = "判断型", type = "同步组件", comment = "根据输入的源日期时间,目的日期时间和单位进行计算目标时间比源时间大多少,单位支持秒,分钟,小时和天", date = "Wed Jul 01 11:13:43 CST 2015")
	public static TCResult calculateTimeDiff(String srctime, String dsttime,
			String timeunit) {
		if (srctime == null || dsttime == null || timeunit == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数 srctime,dsttime,timeunit 不能为空！");
		}
		if (srctime.length() == 8) {
			srctime = srctime + "000000";
		}
		if (dsttime.length() == 8) {
			dsttime = dsttime + "000000";
		}
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date srcDate = null;
		Date destDate = null;
		try {
			srcDate = sdf.parse(srctime);
			destDate = sdf.parse(dsttime);
		} catch (ParseException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		long diff = destDate.getTime() - srcDate.getTime();
		if (timeunit.equals("seconds")) {
			diff = diff / 1000;
		} else if (timeunit.equals("minutes")) {
			diff = diff / 1000 / 60;
		} else if (timeunit.equals("hours")) {
			diff = diff / 1000 / 60 / 60;
		} else if (timeunit.equals("days")) {
			diff = diff / 1000 / 60 / 60 / 24;
		} else {
			return TCResult.newFailureResult(ErrorCode.TIMEUNIT, "不支持的时间单位["
					+ timeunit + "]");
		}
		if (diff < 1) {
			diff = 0;
		}
		return TCResult.newSuccessResult(diff);

	}

	/**
	 * @param srctime
	 *            入参|源时间(YYYYMMDDHHMMSSxxx,支持8位,14位和20位日期格式)|
	 *            {@link java.lang.String}
	 * @param addtime
	 *            入参|累加的时间|long
	 * @param timeunit
	 *            入参|时间单位(seconds/minutes/hours/days)|{@link java.lang.String}
	 * @param dsttime
	 *            出参|计算出的时间|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = {
			@Param(name = "srctime", comment = "源时间(YYYYMMDDHHMMSSxxx,支持8位,14位和20位日期格式)", type = java.lang.String.class),
			@Param(name = "addtime", comment = "累加的时间", type = long.class),
			@Param(name = "timeunit", comment = "时间单位(seconds/minutes/hours/days)", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "dsttime", comment = "计算出的时间", type = java.lang.String.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "参数化累加时间", style = "判断型", type = "同步组件", comment = "根据输入的源日期时间,要累加的时间和时间单位进行计算出目标时间,单位支持秒,分钟,小时和天,如果是减少的话则add_time为负数", date = "Wed Jul 01 11:17:47 CST 2015")
	public static TCResult formatCalculateTime(String srctime, long addtime,
			String timeunit) {
		if (srctime == null || timeunit == null) {
			return TCResult.newFailureResult(ErrorCode.AGR,
					"参数 srctime,dsttime,timeunit 不能为空！");
		}
		String calsrctime = srctime;
		if (srctime.length() == 8) {
			calsrctime = srctime + "000000";
		} else if (srctime.length() == 20) {
			calsrctime = srctime.substring(0, 14);
		} else if (srctime.length() != 14) {
			return TCResult.newFailureResult(ErrorCode.TIMEUNIT, "不支持的时间格式["
					+ srctime + "]");
		}
		DateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date srcDate;
		try {
			srcDate = sdf.parse(calsrctime);
		} catch (ParseException e) {
			return TCResult.newFailureResult(ErrorCode.HANDLING, e);
		}
		long diff = srcDate.getTime();
		long time_dif = 0;
		if (timeunit.equals("seconds")) {
			time_dif = addtime * 1000 + diff;
		} else if (timeunit.equals("minutes")) {
			time_dif = addtime * 60 * 1000 + diff;
		} else if (timeunit.equals("hours")) {
			time_dif = addtime * 60 * 60 * 1000 + diff;
		} else if (timeunit.equals("days")) {
			time_dif = addtime * 24 * 60 * 60 * 1000 + diff;
		} else {
			return TCResult.newFailureResult(ErrorCode.TIMEUNIT, "不支持的时间单位["
					+ timeunit + "]");
		}
		String result = null;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time_dif);
		if (srctime.length() == 8) {
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			result = df.format(cal.getTime());
		} else if (srctime.length() == 20) {
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			result = df.format(cal.getTime()) + srctime.substring(14);
		} else {
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			result = df.format(cal.getTime());
		}
		return TCResult.newSuccessResult(result);
	}

	/**
	 * @category 根据日期获取星期几
	 * @param date
	 *            入参|字符串形式日期|{@link java.lang.String}
	 * @param weekday
	 *            出参|星期几|{@link java.lang.String}
	 * @return 0 失败<br/>
	 *         1 成功<br/>
	 */
	@InParams(param = { @Param(name = "date", comment = "字符串形式日期", type = java.lang.String.class) })
	@OutParams(param = { @Param(name = "weekday", comment = "星期几", type = int.class) })
	@Returns(returns = { @Return(id = "0", desp = "失败"),
			@Return(id = "1", desp = "成功") })
	@Component(label = "根据日期获取星期几", style = "判断型", type = "同步组件", comment = "根据日期获取星期几，出参1到7代表周一到周日", author = "Anonymous", date = "2016-10-24 09:27:08")
	public static TCResult getWeek(String date) {
		if (date == null || date.isEmpty()) {
			return TCResult.newFailureResult(ErrorCode.AGR, "参数date不能为空值！");
		}
		
		Calendar cal = Calendar.getInstance();
		int weekday = 0;
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		sdf.setLenient(false);
		Date date0;
		try {
			date0 = sdf.parse(date);
			cal.setTime(date0);
			weekday = cal.get(Calendar.DAY_OF_WEEK)-1;
		} catch (ParseException e1) {
			return TCResult.newFailureResult("DateError", "日期有误");
		}
		if(weekday == 0){
			weekday = 7; 
		}
		
		return TCResult.newSuccessResult(weekday);
	}

}
