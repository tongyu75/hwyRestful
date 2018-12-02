package com.czz.hwy.utils;
 
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
 
/**
 * 日期工具类
 * @author ZYX
 *
 */
public class CalendarUntil {
 
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 
    static SimpleDateFormat hms = new SimpleDateFormat("HH:mm:ss");
    // Print the date with the format.
    public void printDate(String p_msg, Date p_date) {
        System.out.println(p_msg + sdf.format(p_date));
    }
 
    /**
     * Get the Dates between Start Date and End Date.
     * @param p_start   Start Date
     * @param p_end     End Date
     * @return Dates List
     * 
     * 
     * 
     */
    public static List<Date> getDates(Date p_start, Date p_end) { 
     	Calendar pc_start = Calendar.getInstance();
     	pc_start.setTime(p_start);  	
     	Calendar pe_end = Calendar.getInstance();
     	//张纪才 屏蔽
     	pe_end.setTime(new Date());  
     	List<Date> result = new ArrayList<Date>();
        Calendar temp = (Calendar) pc_start.clone();
        result.add(temp.getTime());
        temp.add(Calendar.DAY_OF_YEAR, 1);
        while (temp.before(pe_end)) {
            result.add(temp.getTime());
            temp.add(Calendar.DAY_OF_YEAR, 1);
        }

        
        return result;
    }
    
    /**
     * 获取两个日期相差的天数
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return 天数
     */
    public static int getDates(String fromDate , String toDate) {
        int days = 0;
        try {
            Date date1 = sdf.parse(fromDate);
            Date date2 = sdf.parse(toDate);
            Calendar cal = Calendar.getInstance();    
            cal.setTime(date1);    
            long time1 = cal.getTimeInMillis();                 
            cal.setTime(date2);    
            long time2 = cal.getTimeInMillis();         
            long between_days=(time2-time1)/(1000*3600*24);  
            days = Integer.parseInt(String.valueOf(between_days)) + 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
    
    /**
     * 获取两个日期相差的天数,2016-09-23
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return 天数
     */
    public static int getDays(String fromDate , String toDate) {
        int days = 0;
        try {
            Date date1 = sdf.parse(fromDate);
            Date date2 = sdf.parse(toDate);
            Calendar cal = Calendar.getInstance();    
            cal.setTime(date1);    
            long time1 = cal.getTimeInMillis();                 
            cal.setTime(date2);    
            long time2 = cal.getTimeInMillis();         
            long between_days=(time2-time1)/(1000*3600*24);  
            days = Integer.parseInt(String.valueOf(between_days));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }
    
    /**
	 * 获取两个时间相差的小时数，2016-09-23
	 * @param leaveFromTime 开始时间
	 * @param leaveToTime   结束时间
	 * @return 小时数
	 */
	public static int getHours(String leaveFromTime, String leaveToTime) {
		SimpleDateFormat sdfh = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdft = new SimpleDateFormat("HH:mm:ss");
		int hours = 0;
		try {
			Date date1 = sdfh.parse(leaveFromTime);
			Date date2 = sdfh.parse(leaveToTime);
			leaveFromTime = date1.getHours() + ":" + date1.getMinutes() + ":"  + date1.getSeconds() ;
			leaveToTime =  date2.getHours() + ":" + date2.getMinutes() + ":"  + date2.getSeconds() ;

			Date d1 = sdft.parse(leaveFromTime);
			Date d2 = sdft.parse(leaveToTime);
			Calendar c1 = Calendar.getInstance();    
            c1.setTime(d1);    
            long time1 = c1.getTimeInMillis();                 
            c1.setTime(d2);    
            long time2 = c1.getTimeInMillis();  
            
            
            BigDecimal b1 = new BigDecimal(time2 - time1);  
            BigDecimal b2 = new BigDecimal(1000*3600);  
            hours = b1.divide(b2, 0, BigDecimal.ROUND_HALF_UP).intValue();
            
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return hours;
	}
    
	public static void main(String[] args) {
		String str1 = "2016-09-22 14:15:20";
		String str2 = "2016-09-22 16:45:59";
		
		System.out.println(getHours(str1,str2));
	}
	
	
    /**
	 * 将时间往前推？分钟
	 * @param d 要前推的时间
	 * @param minute 要前推的分钟
	 * @return
	 */
	public static Date beforeHowManyMin(Date d, int minute) {

		if (d != null) {

			Calendar calendar = Calendar.getInstance();

			calendar.setTime(d);

			calendar.add(Calendar.MINUTE, -minute);

			return calendar.getTime();
		}

		return d;
	}
	
	/**
	 * 将时间往后推？分钟
	 * @param d  要推后的时间
	 * @param minute 推后的分钟
	 * @return
	 */
	public static Date afterHowManyMin(Date d, int minute) {

		if (d != null) {

			Calendar calendar = Calendar.getInstance();

			calendar.setTime(d);

			calendar.add(Calendar.MINUTE, minute);

			return calendar.getTime();
		}

		return d;
	}

    /**
     * 将时间往后推？分钟
     * @param d  要推后的时间
     * @param minute 推后的分钟
     * @return
     */
    public static Date afterHowManyMin(String dateStr, int minute) {
        Date time = null;
        try {
            Date d = hms.parse(dateStr);
            if (d != null) {
                
                Calendar calendar = Calendar.getInstance();
                
                calendar.setTime(d);
                
                calendar.add(Calendar.MINUTE, minute);
                time = calendar.getTime();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;

    }	
	
	/**
	 *  时间格式化
	 */	
	public static Date timeFormat(Date currDate) {
		String d = currDate.getHours() + ":" + currDate.getMinutes() + ":" + currDate.getSeconds();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		if (d != null) {
			Date date = null;
			try {
				date = sdf.parse(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}
		return null;
	}
	
	/**
	 * 获取某个日期是周几
	 * 1是星期一，7是星期天
	 * @return
	 */
	public static int week(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		if(week == 1){//如果是周天
			week = 7;
		}else{
			week -= 1;
		}
		return week;
	}
	
	/**
	 * 获取某个日期是几号？
	 * 1 表示1号
	 */
	public static int day(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return day;
	}
	
	/**
	 * 将两个时间格式化，用第一日期的年月日拼上第二个日期的时分秒，
	 * 返回Date 类型
	 */
	public static Date dateTimeFormat(Date fristDate, Date secondDate) {
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(fristDate);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(secondDate);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH),calendar1.get(Calendar.DAY_OF_MONTH),
				calendar2.get(Calendar.HOUR_OF_DAY), calendar2.get(Calendar.MINUTE), calendar2.get(Calendar.SECOND));
//		String timeStr = secondDate.getHours() + ":" + secondDate.getMinutes() + ":" + secondDate.getSeconds();
//		SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String dateStr = d.format(fristDate);
		
		Date date = calendar.getTime();
		return date;
		
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(dateStr + " " + timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;*/
	}
	
    /**
     * 获取明日日期
     * @param fromDate 开始日期
     * @param toDate 结束日期
     * @return 天数
     */
    public static Date getTomorrowDate() {
        //通过日历获取下一天日期  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(new Date());  
        cal.add(Calendar.DAY_OF_YEAR, 1);  
        String nextDate = sdf.format(cal.getTime());  
        Date tomorrow = null;
        try {
            tomorrow = sdf.parse(nextDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return tomorrow;
    }
}