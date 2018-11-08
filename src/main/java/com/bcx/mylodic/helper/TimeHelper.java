package com.bcx.mylodic.helper;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * 日期帮助类
 * @author zl
 * date: 2018-07-08 17:43
 */
public class TimeHelper {

    private static Logger logger = Logger.getLogger(TimeHelper.class.getName());

    private static final String DATE_STR = "yyyy-MM-dd";

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat(DATE_STR);

    private static final long DAY_TIME = 24*60*60*1000;

    /**
     * 得到当前时间字符串
     * @return 日期字符串
     */
    public static String getNowTime(){
        return FORMAT.format(new Date());
    }

    /**
     * 默认当前时间 精确到秒
     * @return 当前时间
     */
    public static String getNow(){
        return getNowTime("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 通过指定的格式获取当前时间字符串
     * @param format  日期字符串格式
     * @return        当前时间字符串
     */
    public static String getNowTime(String format){
        return new SimpleDateFormat(format).format(new Date());
    }

    /**
     * 获取两个时间之间的秒数
     * @param date1 开始
     * @param date2  结束
     * @param format 日期格式
     * @return       间隔秒
     */
    public static long betweenSeconds(String date1,String date2,String ...format){
        try{
            Date start = getFormat(format).parse(date1);
            Date end = getFormat(format).parse(date2);
            return (end.getTime()-start.getTime())/1000;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return 0;
    }

    /**
     * 获取两个日期之间的天数
     * @param date1 开始
     * @param date2 结束
     * @param format 日期格式
     * @return       天数
     */
    public static long betweenDays(String date1,String date2,String...format){
        long seconds = betweenSeconds(date1,date2,format);
        if(seconds==0){
            return 0;
        }
        return (seconds/60/60/24)+1;
    }


    private static boolean isNull(String ...objs){
        return objs==null || objs.length==0;
    }


    private static SimpleDateFormat getFormat(String...format){
        return isNull(format) ? FORMAT : new SimpleDateFormat(format[0]);
    }


    public static long getTime(String data,String...format){
        try {
            return getFormat(format).parse(data).getTime();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取两个日期之间的工作日
     * @param date1   开始
     * @param date2   结束
     * @param format  日期格式
     * @return        所有工作日信息
     */
    public static List<String> betweenWorkDays(String date1, String date2, String...format){
        List<String> days = getBetweenDays(date1,date2,format);
        List<String> workDays = new ArrayList<>();
        for(String day : days){
            int week = getWeek(day);
            if(week!=0 && week!=6){
                workDays.add(day);
            }
        }
        return workDays;
    }

    /**
     * 获取两个日期之间的所有日期信息
     * @param date1  开始
     * @param date2  结束
     * @param format 日期格式
     * @return       所有日期信息
     */
    public static List<String> getBetweenDays(String date1,String date2,String...format){
        List<String> dates = new ArrayList<>();
        try{
            long d1 = getFormat(format).parse(date1).getTime();
            long d2 = getFormat(format).parse(date2).getTime();
            while(d2>=d1){
                String time = getFormat(format).format(d1);
                dates.add(time);
                d1+=DAY_TIME;
            }
            return dates;
        }catch (Exception e){
            e.printStackTrace();
        }
        return dates;
    }

    /**
     * 获取指定日期为周几
     * @param date    指定日期
     * @param format  日期格式
     * @return        数字
     */
    public static int getWeek(String date,String...format){
        return searchTime(date, Calendar.DAY_OF_WEEK,format)-1;
    }


    private static int searchTime(String date,Integer search,String...format){
        try{
            Date d = getFormat(format).parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            return calendar.get(search);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return -1;
    }

    /**
     * 获取当日为周几
     * @return  数字
     */
    public static int getNowWeek(){
        return searchTime(getNowTime(),Calendar.DAY_OF_WEEK)-1;
    }

    /**
     * 获取当日为周几 中文
     * @return 中文
     */
    public static String getNowWeekSimp(){
        return new SimpleDateFormat("E").format(new Date());
    }

    /**
     * 获取当日为当前月的第几天
     * @return     数字
     */
    public static int getNowDayOfMonth(){
        return searchTime(getNowTime(),Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当月为第几月
     * @return  数字
     */
    public static int getNowMonth(){
        return searchTime(getNowTime(),Calendar.MONTH)+1;
    }

    /**
     * 获取年份
     * @return  数字
     */
    public static int getNowYear(){
        return searchTime(getNowTime(),Calendar.YEAR);
    }

}
