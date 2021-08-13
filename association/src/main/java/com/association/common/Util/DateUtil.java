package com.association.common.Util;


import org.apache.commons.lang3.time.DateUtils;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/*/**
 *@Description: jdk8 日期工具类
 *
 *@author  Orochi-Yzh
 *@dateTime  2018/8/21 16:46
 */
public class DateUtil {

    public static final ZoneId chinaZone = ZoneId.systemDefault();
    public static final String SIMPLE_FORMATTER = "yyyyMMddHHmmss";
    public static final String SIMPLE_FORMATTER_NONE_SS = "yyyyMMddHHmm";
    public static final String SIMPLE_FORMATTER_DAY = "yyyyMMdd";
    public static final String SIMPLE_FORMATTER_MONTH = "yyyyMM";
    public static final String FORMATTER = "yyyy-MM-dd HH:mm:ss";
    public static final String CENTURY_FORMATTER = "yyMMddHHmmss";
    public static final String FORMATTER_DAY = "yyyy-MM-dd";


    /**
     * 获取before时间
     * @param m
     * @param timeUnit
     * @return
     */
    public static Date getBeforeTime(int m, TimeUnit timeUnit) {
        return getBeforeByMinTime(new Date(), timeUnit.toMinutes(m));
    }
    /**
     * 获取before时间
     * @param m
     * @param timeUnit
     * @return
     */
    public static Date getBeforeTime(Date date, int m, TimeUnit timeUnit) {
        return getBeforeByMinTime(date, timeUnit.toMinutes(m));
    }




    /**
     * 获取当前时间，指定前面多少小时的时间
     * 返回格式Date
     * @param ihour
     * @return
     */
    public static Date getBeforeByHourTime(int ihour){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - ihour);

        return calendar.getTime();
    }


    /* 核心代码 */
    public static Date getBeforeByMinTime(Date date, Long min) {

        if (Objects.isNull(min)) {
            return null;
        }

        Calendar beforeTime = Calendar.getInstance();
        beforeTime.setTime(date);
        beforeTime.add(Calendar.MINUTE, -min.intValue());// 5分钟之前的时间
        return beforeTime.getTime();
    }

    /**
     * 获取当前时间到第二天凌晨（12点）的秒的差值
     *
     * @return Long
     */
    public static Long getSecondsNextEarlyMorning() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return (calendar.getTimeInMillis() - System.currentTimeMillis()) / (1000);
    }

    /**
     * 获取当天日期(YYYY-MM-DD格式)
     */
    public static LocalDate now() {
        return LocalDate.now();
    }

    /**
     * 按指定格式获取当天日期
     */
    public static String nowDateTime(String formatter) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(formatter));
    }

    /**
     * 按指定格式获取当天日期
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), chinaZone);
    }

    /**
     * 比较日期
     *
     * @return
     */

    public static int compareDate(Date date1, Date date2) {
        if (Objects.isNull(date1) || Objects.isNull(date2)) {
            return 0;
        }
        if (date1.getTime() > date2.getTime())
            return -1;
        else if (date1.getTime() < date2.getTime())
            return 1;
        else
            return 0;
    }

    /**
     * 日期时间对象转换为日期对象
     *
     * @param localDateTime 日期时间对象
     * @return 日期对象
     */
    public static LocalDate dateTime2Date(LocalDateTime localDateTime) {
        return localDateTime.toLocalDate();

    }

    /**
     * 日期对象转换为日期对象
     *
     * @param localDate 日期对象
     * @return 日期时间对象
     */
    public static LocalDateTime date2DateTIme(LocalDate localDate) {
        return LocalDateTime.of(localDate, LocalTime.NOON);
    }

    /**
     * 字符串转换为日期
     *
     * @param strDate 字符串日期
     * @return 日期对象 yyyy-mm-dd
     */
    public static LocalDate str2LocalDate(String strDate) {
        return LocalDate.parse(strDate, DateTimeFormatter.ISO_DATE);
    }

    /**
     * 字符串转换为日期
     *
     * @param strDate 字符串日期
     * @return 日期对象 yyyy-mm-dd
     */
    public static LocalDateTime str2LocalDateTime(String strDate, DateTimeFormatter formatter) {
        return LocalDateTime.parse(strDate, formatter);
    }

    /**
     * yyyyMMddHHmmss时间转LocalDateTime
     *
     * @param strDate
     * @return
     */
    public static LocalDateTime str2LocalDateTime(String strDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(SIMPLE_FORMATTER);
        return LocalDateTime.parse(strDate, formatter);
    }

    /**
     * LocalDateTime转换为Date
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(chinaZone).toInstant());
    }

    /**
     * LocalDateTime转换为Date
     */
    public static Date str2Date(String strDate, String formatter) {
        return localDateTime2Date(str2LocalDateTime(strDate, DateTimeFormatter.ofPattern(formatter)));
    }

    /**
     * yyyyMMddHHmmss时间转Date
     *
     * @param yyyyMMddHHmmssTime
     * @return
     */
    public static Date str2dateWithYMDHMS(String yyyyMMddHHmmssTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(SIMPLE_FORMATTER);
        LocalDateTime ldt = LocalDateTime.parse(yyyyMMddHHmmssTime, dtf);
        return localDateTime2Date(ldt);
    }

    /***
     * 将指定格式的时间字符串转为Date
     *
     * @param time
     * @param formatter
     * @return
     */
    public static Date str2date(String time, String formatter) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(formatter);
        LocalDateTime ldt = LocalDateTime.parse(time, dtf);
        return localDateTime2Date(ldt);
    }



    /**
     * 日期对象转换为字符串
     *
     * @param localDate 日期对象
     * @return 日期字符串 yyyy-mm-dd
     */
    public static String date2Str(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ISO_DATE);
    }

    /**
     * 日期时间对象转换为字符串
     *
     * @param localDateTime     日期时间对象
     * @param dateTimeFormatter 格式化字符串
     * @return 日期字符串
     */
    public static String dateTime2Str(LocalDateTime localDateTime, String dateTimeFormatter) {
        return localDateTime.format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }

    /**
     * 日期时间转字符串函数 返回ISO标准的日期字符串
     *
     * @param localDateTime 日期时间对象
     * @return 日期字符串
     */
    public static String dateTime2Str(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * 获取东八时间戳,秒级
     */
    public static long getTimeStrampMiniseconds() {
        // 获取秒数
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }

    /***
     * 获取时间戳,毫秒级
     *
     * @return
     */
    public static String getTimeStamp() {
        Date d = new Date();
        // getTime()得到的是微秒， 需要换算成秒
        long timeStamp = d.getTime() / 1000;
        return String.valueOf(timeStamp);
    }

    /**
     * 获取东八时间戳,毫秒级
     */
    public static long getTimeStrampSeconds() {
        // 获取秒数
        return LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 时间转double
     *
     * @param date
     * @return 返回值类似：43322.3770190278
     */
    public static double Date2Double(Date date) {
        @SuppressWarnings("deprecation")
        long localOffset = date.getTimezoneOffset() * 60000;
        double dd = (double) (date.getTime() - localOffset) / 24 / 3600 / 1000 + 25569.0000000;
        DecimalFormat df = new DecimalFormat("#.0000000000");// 先默认保留10位小数
        return Double.valueOf(df.format(dd));
    }

    /**
     * date 转string： yyyy-MM-dd HH:mm:ss
     */
    public static String fromDate2Str(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static String fromDate2Str(Date date, String formatter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
        return simpleDateFormat.format(date);
    }

    public static Date strToDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date strToDate(String date, String formatter) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatter);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 计算两个时间之间相差的秒数
     *
     * @param date1 起始时间
     * @param date2 结束时间
     */
    public static long secondsBetween(LocalDateTime date1, LocalDateTime date2) {
        Duration duration = Duration.between(date1, date2);
        return duration.getSeconds();
    }

    /**
     * 计算两个时间之间相差的秒数
     * 后面减前面,若结果为正数，则前面小
     * @param date1 起始时间
     * @param date2 结束时间
     */
    public static long secondsBetween(Date date1, Date date2) {
        Instant instantDateTime1 = date1.toInstant();
        Instant instantDateTime2 = date2.toInstant();
        Duration duration = Duration.between(instantDateTime1, instantDateTime2);
        return duration.getSeconds();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     */
    public static int daysBetween(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getDays();
    }

    /**
     * 计算两个日期之间相差的月数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     */
    public static int monthsBetween(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getMonths();
    }

    /**
     * 计算两个日期之间相差的年数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     */
    public static int yearsBetween(LocalDate date1, LocalDate date2) {
        Period period = Period.between(date1, date2);
        return period.getYears();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     */
    public static int daysBetween(Date date1, Date date2) {
        if (Objects.isNull(date1) || Objects.isNull(date2)) {
            return 0;
        }
        Instant instantDate1 = date1.toInstant();
        Instant instantDate2 = date2.toInstant();
        LocalDate localDate1 = instantDate1.atZone(chinaZone).toLocalDate();
        LocalDate localDate2 = instantDate2.atZone(chinaZone).toLocalDate();
        instantDate1.atZone(chinaZone);
        Period period = Period.between(localDate1, localDate2);
        return period.getDays();
    }

    /**
     * 计算两个日期之间相差的月数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     */
    public static int monthsBetween(Date date1, Date date2) {
        Instant instantDate1 = date1.toInstant();
        Instant instantDate2 = date2.toInstant();
        LocalDate localDate1 = instantDate1.atZone(chinaZone).toLocalDate();
        LocalDate localDate2 = instantDate2.atZone(chinaZone).toLocalDate();
        instantDate1.atZone(chinaZone);
        Period period = Period.between(localDate1, localDate2);
        return period.getMonths();
    }

    /**
     * 计算两个日期之间相差的年数
     *
     * @param date1 起始日期
     * @param date2 结束日期
     */
    public static int yearsBetween(Date date1, Date date2) {
        Instant instantDate1 = date1.toInstant();
        Instant instantDate2 = date2.toInstant();
        LocalDate localDate1 = instantDate1.atZone(chinaZone).toLocalDate();
        LocalDate localDate2 = instantDate2.atZone(chinaZone).toLocalDate();
        instantDate1.atZone(chinaZone);
        Period period = Period.between(localDate1, localDate2);
        return period.getYears();
    }

    /**
     * 获取指定日期对象当前月的起始日
     *
     * @param localDate 指定日期
     */
    public static int getFirstDayInMonth(LocalDate localDate) {
        LocalDate result = localDate.with(TemporalAdjusters.firstDayOfMonth());
        return result.getDayOfMonth();

    }

    /**
     * 获取指定日期对象的当前月的结束日
     *
     * @param localDate 指定日期
     */
    public static int getLastDayInMonth(LocalDate localDate) {
        LocalDate result = localDate.with(TemporalAdjusters.lastDayOfMonth());
        return result.getDayOfMonth();
    }

    /**
     * 获取指定日期对象本月的某周某天的日期
     *
     * @param localDate  日期对象
     * @param weekNumber 周
     * @param dayNumber  日
     */
    public static LocalDate getLocalDateBydayAndWeek(LocalDate localDate, int weekNumber, int dayNumber) {
        return localDate.with(TemporalAdjusters.dayOfWeekInMonth(weekNumber, DayOfWeek.of(dayNumber)));
    }

    public static String getHour(LocalDateTime localDate) {
        return dateTime2Str(localDate, SIMPLE_FORMATTER).substring(8, 10);
    }

    public static String getDay(LocalDateTime localDate) {
        return dateTime2Str(localDate, SIMPLE_FORMATTER).substring(6, 8);
    }

    public static String getDay(Date date) {
        return fromDate2Str(date, SIMPLE_FORMATTER).substring(6, 8);
    }

    public static String getWholeDay(Date date) {
        return fromDate2Str(date, SIMPLE_FORMATTER_DAY);
    }

    /**
     * 获取指定秒数后的时间戳
     *
     * @param after
     * @return
     */
    public static long getDateTimeAfterSeconds(LocalDateTime dateTime, long after) {
        return dateTime.plusSeconds(after).toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    /**
     * 获取指定秒数后的时间
     *
     * @param after
     * @return
     */
    public static LocalDateTime getLocalDateTimeAfterSeconds(LocalDateTime dateTime, long after) {
        return dateTime.plusSeconds(after);
    }

    /**
     * date2比date1多的天数
     * 后面减前面
     * @param date1
     * @param date2
     * @return
     */
    public static int daysBetweenByCalendar(Date date1,Date date2){
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2) {  //同一年
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++) {
                if(i%4==0 && i%100!=0 || i%400==0) {   //闰年
                    timeDistance += 366;
                }else {    //不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        }
        else { //不同年
            return day2-day1;
        }
    }

    /**
     * 根据出生日期计算年龄
     * @param birthday
     * @return
     */
    public static int getAgeByBirth(Date birthday) {
        int age = 0;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());// 当前时间

            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);

            if (birth.after(now)) {//如果传入的时间，在当前时间的后面，返回0岁
                age = 0;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
            }
            return age;
        } catch (Exception e) {//兼容性更强,异常后返回数据
            return 0;
        }
    }

    /**
     *
     * 两个String类型，按照日期格式对比 eg: dateOne：2015-12-26 dateTwo：2015-12-26
     * dateFormatType: yyyy-MM-dd 返回类型：-1：dateOne小于dateTwo，
     * 0：dateOne=dateTwo ，1：dateOne大于dateTwo
     * @param dateOne String
     * @param dateTwo String
     * @param dateFormatType  yyyy-MM-dd   /  yyyy-MM-dd HH:mm:ss /等
     * @return -1(小于)，0(等于) , 1(大于)
     */
    public static int compareTime(String dateOne, String dateTwo, String dateFormatType){
        int compareTo=0;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatType);
        Date nowDate_Date = null;
        Date endDate_Date= null;
        try {
            nowDate_Date = sdf.parse(dateOne);
            endDate_Date = sdf.parse(dateTwo);
            compareTo = nowDate_Date.compareTo(endDate_Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(compareTo);
        return compareTo;
    }

    // 返回日期间的每一天
    public static List<String> getBetweenDate(LocalDate startDate, LocalDate endDate,String pattern) {
        List<String> list = new ArrayList<>();
        long distance = ChronoUnit.DAYS.between(startDate, endDate);
        if (distance < 1) {
            return list;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        Stream.iterate(startDate, d -> d.plusDays(1)).limit(distance + 1).forEach(f -> list.add(f.format(formatter)));
        return list;
    }

//	public static void main(String[] args) throws Exception {
//
//
//		Date date1 = str2date("2019-12-18 23:59:59", FORMATTER);
//
//		System.out.println(DateUtil.fromDate2Str(date1,"yyyyMMdd"));
//
//
//		Date date2 = str2date("2019-12-19 00:00:00", FORMATTER);
//		System.out.println(daysBetweenByCalendar(date1, date2));
//
//
////		Date date1 = str2date("2019-12-18 22:00:00", FORMATTER);
////		Date date2 = str2date("2019-12-20 11:00:00", FORMATTER);
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date bithday = format.parse("2019-08-23 17:20:20");
//		int ageByBirth = getAgeByBirth(bithday);
//		System.out.println(ageByBirth);
//
////		System.out.println(DateUtil.fromDate2Str(new Date(1548404064262L)));
////		System.out.println(DateUtil.getDateTimeAfterSeconds(LocalDateTime.now(), 5 * 60));
////
////		System.out.println(DateUtil.str2Date("20180814140659", DateUtil.SIMPLE_FORMATTER));
////
////		System.out.println(DateUtil.nowDateTime(DateUtil.SIMPLE_FORMATTER));
////
////		LocalDateTime now = LocalDateTime.now();
////		Thread.sleep(1000);
////		LocalDateTime now2 = LocalDateTime.now();
////		System.out.println(secondsBetween(now, now2));
////
////		System.out.println(Duration.between(now, now2).toMillis() / 1000);
////
////		System.out.println(getDateTimeAfterSeconds(LocalDateTime.now(), 3600));
////		LocalDateTime date1 = dateToLocalDateTime(new Date());
////		Thread.sleep(3000);
////		System.out.println(secondsBetween(date1, LocalDateTime.now()));
//	}

    public static boolean isSameDay(Date date1, Date date2) {
        return DateUtils.isSameDay(date1, date2);
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    /**
     * 获取当天从开始到结束以15分钟为间隔的所有时间段
     * @return
     * @throws Exception
     */
    public static List<String> getTimeLag15Minute() throws  Exception{
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String s = df.format(day);
        Date date = df.parse(s);
        ArrayList<String> dates = new ArrayList<String>();
        SimpleDateFormat gs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 96; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
//            cal.add(Calendar.HOUR, 1);
            cal.add(Calendar.MINUTE, 15);

            date = cal.getTime();
            String s1 = gs.format(date);
            dates.add(s1);
        }
        String aString = DateUtil.fromDate2Str(new Date(),"yyyyMMdd");
        StringBuilder sb = new StringBuilder(aString);
        sb.insert(4, "-");
        sb.insert(7, "-");
        aString = sb.toString()+" 00:00:00";
        dates.add(0, aString);
        return dates;
    }


    /**
     * 获取某天从开始到结束以1小时为间隔的所有时间段
     * @return
     * @throws Exception
     */
    public static List<String> getTimeLag1Hour() throws  Exception{
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        //SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
        String s = df.format(day);
        Date date = df.parse(s);
        ArrayList<String> dates = new ArrayList<String>();
        SimpleDateFormat gs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 24; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
//            cal.add(Calendar.HOUR, 1);
            cal.add(Calendar.HOUR, 1);

            date = cal.getTime();
            String s1 = gs.format(date);
            dates.add(s1);
        }
        String aString = DateUtil.fromDate2Str(new Date(),"yyyyMMdd");
        StringBuilder sb = new StringBuilder(aString);
        sb.insert(4, "-");
        sb.insert(7, "-");
        aString = sb.toString()+" 00:00:00";
        dates.add(0, aString);
        return dates;
    }


    /**
     * 获取某天的时间节点（1小时一个节点）
     * @param originalDate yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static ArrayList<String> getTimeForOneHour(Date originalDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String s = df.format(originalDate);
        Date date = df.parse(s);
        ArrayList<String> dates = new ArrayList<String>();
        SimpleDateFormat gs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 24; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR, 1);

            date = cal.getTime();
            String s1 = gs.format(date);
            dates.add(s1);
        }
        String aString = DateUtil.fromDate2Str(originalDate,"yyyyMMdd");
        StringBuilder sb = new StringBuilder(aString);
        sb.insert(4, "-");
        sb.insert(7, "-");
        aString = sb.toString()+" 00:00:00";
        dates.add(0, aString);
        return dates;
    }

    /**
     * 获取某个月的每一天的24小时的时间点
     * @param year
     * @param month
     * @return
     * @throws ParseException
     */
    public static List<String> getMonthFullDay(int year , int month) throws ParseException{
        SimpleDateFormat dateFormatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
        List<String> fullDayList = new ArrayList<>(32);
        List<String> reList = new ArrayList<String>();
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        cal.clear();// 清除信息
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month-1 );
        // 当月1号
        cal.set(Calendar.DAY_OF_MONTH,1);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int j = 1; j <= count ; j++) {
            fullDayList.add(dateFormatYYYYMMDD.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_MONTH,1);
        }
        //ArrayList<String> dates = new ArrayList<String>();
        SimpleDateFormat gs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int k = 0; k < fullDayList.size(); k++) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
            Date s = df.parse((fullDayList.get(k)+" 00:00:00"));
            for (int i = 0; i < 24; i++) {
                String s1 = null;
                Calendar cal1 = Calendar.getInstance();
                cal1.setTime(s);
                cal1.add(Calendar.HOUR, 1);

                s = cal1.getTime();
                s1 = gs.format(s);
                reList.add(s1);
            }

        }
        String aString = null;
        if (String.valueOf(month).length() == 1) {
            aString= year+"-0"+month+"-01 00:00:00";
        }else {
            aString = year+"-"+month+"-01 00:00:00";
        }
        reList.add(0,aString);
        return reList;


    }


    /**
     * record与当前的前一小时时间
     * @param record 比较时间
     * @return 当 前一小时时间 小于Date2时，返回TRUE，当大于或等于时，返回false；
     */
    public static Boolean getBeforeOneHour(Date record){
        Calendar calendar = Calendar.getInstance();
        /* HOUR_OF_DAY 指示一天中的小时 */
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println("一个小时前的时间：" + df.format(calendar.getTime()));
        //System.out.println("当前的时间：" + df.format(new Date()));
        return calendar.getTime().before(record);
    }


    public static Boolean getDatePoor(Date endDate, Date nowDate) {
        Double nd = (double) (1000 * 24 * 60 * 60);
        Double nh = (double) (1000 * 60 * 60);
        Double nm = (double) (1000 * 60);
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        Double diff = (double) (endDate.getTime() - nowDate.getTime());
        // 计算差多少小时
        Double hour = diff % nd / nh;
        if(hour > 1) {
            return true;
        }else {
            return false;
        }

    }

    public static List<String> getMonthBetween(Date minDate, Date maxDate)  {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(minDate);
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(maxDate);
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }

    /**
     * @return
     * 获取年月日格式的时间
     */
    public static Date getCurrentYmd() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        int month=cal.get(Calendar.MONTH)+1;//获取月份
        int day=cal.get(Calendar.DATE);//获取日
        String cu = year+"-"+month+"-"+day;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date aDate = null;
        try {
            aDate = sdf.parse(cu);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return aDate;
    }

    /**
     * @param list
     * @param i
     */
    public static String getString(List<String> list, int i) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(list.get(i));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String cString = DateUtil.fromDate2Str(date,"yyyyMM");
        return cString;
    }

    public static List<String> getDayListOfMonth() {
        List<String> list = new ArrayList<String>();
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int year = aCalendar.get(Calendar.YEAR);//年份
        int month = aCalendar.get(Calendar.MONTH) + 1;//月份
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        for (int i = 1; i <= day; i++) {
            String aDate = String.valueOf(year)+"-"+month+"-"+i;
            Date date = DateUtil.strToDate(aDate, "yyyy-MM-dd");
            String reDate = DateUtil.fromDate2Str(date, "yyyy-MM-dd");
            String resultDate = reDate.substring(5, reDate.length());
            list.add(resultDate);
        }

        return list;
    }
/*    *//**
     * 根据当前时间获取本周的第一条和最后一天
     * @param date
     * @return
     *//*
    public static OutCountInput getTimeInterval(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = sdf.format(cal.getTime());
        Date stDate = cal.getTime();
        // System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        Date enDate = cal.getTime();
        // System.out.println("所在周星期日的日期：" + imptimeEnd);
        //System.out.println(st + "," + enDate );
        OutCountInput outCountInput = new OutCountInput();
        outCountInput.setStartTime(stDate);
        outCountInput.setEndTime(enDate);
        return outCountInput;
    }*/
    /**
     * 根据起始时间获得中间的所有时间(包含起始时间)(无年份)
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<String> findDates(Date dBegin, Date dEnd)  {
        List<Date> lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        List<String> week=new ArrayList<>();
        for(int i=0;i<lDate.size();i++) {
            String data = fromDate2Str(lDate.get(i), "yyyy-MM-dd");
            week.add(data);
        }
        List<String> weekList = new ArrayList<String>();
        for(int i =0;i<week.size();i++) {
            Date date = DateUtil.strToDate(week.get(i), "yyyy-MM-dd");
            String reDate = DateUtil.fromDate2Str(date, "yyyy-MM-dd");
            String resultDate = reDate.substring(5, reDate.length());
            weekList.add(resultDate);
        }

        return weekList;
    }


    /**
     * 根据起始时间获得中间的所有时间(包含起始时间)(有年份)
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<String> findDateList(Date dBegin, Date dEnd)  {
        List<Date> lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        List<String> week=new ArrayList<>();
        for(int i=0;i<lDate.size();i++) {
            String data = fromDate2Str(lDate.get(i), "yyyy-MM-dd");
            week.add(data);
        }
        List<String> weekList = new ArrayList<String>();
        for(int i =0;i<week.size();i++) {
            Date date = DateUtil.strToDate(week.get(i), "yyyy-MM-dd");
            String reDate = DateUtil.fromDate2Str(date, "yyyy-MM-dd");
            weekList.add(reDate);
        }

        return weekList;
    }

    // 有用的
    public static Long getMonthBegin(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // 设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        // 将小时至0
        c.set(Calendar.HOUR_OF_DAY, 0);
        // 将分钟至0
        c.set(Calendar.MINUTE, 0);
        // 将秒至0
        c.set(Calendar.SECOND, 0);
        // 将毫秒至0
        c.set(Calendar.MILLISECOND, 0);
        // 获取本月第一天的时间戳
        return c.getTimeInMillis();
    }

    // 有用的
    public static Long getMonthEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        // 设置为当月最后一天
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        // 将小时至23
        c.set(Calendar.HOUR_OF_DAY, 23);
        // 将分钟至59
        c.set(Calendar.MINUTE, 59);
        // 将秒至59
        c.set(Calendar.SECOND, 59);
        // 将毫秒至999
        c.set(Calendar.MILLISECOND, 999);
        // 获取本月最后一天的时间戳
        return c.getTimeInMillis();
    }

    // 有用的
    public static String getMonthBeginAndEnd(String dateString) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");// 注意月份是MM
        Date date = simpleDateFormat.parse(dateString);
        getMonthBegin(date);
        getMonthEnd(date);
        Long startTime = getMonthBegin(date);
        Long endTime = getMonthEnd(date);
        DateTimeFormatter ftf = DateTimeFormatter.ofPattern("yyyyMMdd");
        String startTimeStr = ftf
                .format(LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneId.systemDefault()));
        String endTimeStr = ftf.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(endTime), ZoneId.systemDefault()));
        return startTimeStr + "," + endTimeStr;
    }


    public static void main(String[] args) throws ParseException {
//    	SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = ft.parse("2020-09-02 14:50:43");
//        Date day=new Date();
//        String s = ft.format(day);
//        Date date2 = ft.parse(s);
//    	Boolean flag = getDatePoor(date2,date);
//    	System.out.println(date2.getTime());
//    	System.out.println(date.getTime());
//    	System.out.println(flag);
        String st = "2021-02-28";
        String en = "2021-03-06";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse(en);
        Date date2 = formatter2.parse(st);
        String cString = DateUtil.fromDate2Str(date,"yyyyMMdd");
        String bString = DateUtil.fromDate2Str(date2,"yyyyMMdd");
        List<String> list = getMonthBetween(date2,date);
//	    	for(int i = 0;i<list.size();i++) {
//	    		String paramStr = DateUtil.getString(list, i);
//	    		System.out.println(paramStr);
//	    	}
//        System.out.println(list.toString());
//        System.out.println(list.size());
        System.out.println(cString);
        System.out.println(bString);
    }

    /**
     * 获取startDate日期后month月的日期
     * @param startDate 开始日期
     * @param month  几个月后
     * @return
     */
    public static Date getMonthDate(Date startDate,int month){
        LocalDateTime localDateTime = startDate.toInstant()
                .atZone(ZoneId.systemDefault() )
                .toLocalDateTime().plusMonths(month);
        Date date = Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant());
        return date;
    }

    /**
     * 获取startDate日期后day月的日期
     * @param startDate 开始日期
     * @param day  几天后
     * @return
     */
    public static Date getDayDate(Date startDate,int day){
        LocalDateTime localDateTime = startDate.toInstant()
                .atZone(ZoneId.systemDefault() )
                .toLocalDateTime().plusDays(day);
        Date date = Date.from(localDateTime.atZone( ZoneId.systemDefault()).toInstant());
        return date;
    }

    /**
     * 获取某年某月的开始时间和结束时间(带时分秒的)
     * @param year
     * @param month
     * @return
     */
    public static String getBeginTime(int year, int month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate localDate = yearMonth.atDay(1);
        LocalDateTime startOfDay = localDate.atStartOfDay();
        ZonedDateTime zonedDateTime = startOfDay.atZone(ZoneId.of("Asia/Shanghai"));

        String aString = sdf.format(Date.from(zonedDateTime.toInstant()));
        return aString;
    }
    public static String getEndTime(int year, int month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate endOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime localDateTime = endOfMonth.atTime(23, 59, 59, 999);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Shanghai"));
        String bString = sdf.format(Date.from(zonedDateTime.toInstant()));
        return bString;
    }

    /**
     * 获取上个月
     * @return
     */
    public static String getLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date); // 设置为当前时间
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1); // 设置为上一个月
        date = calendar.getTime();
        String accDate = format.format(date);
        return accDate;
    }
}