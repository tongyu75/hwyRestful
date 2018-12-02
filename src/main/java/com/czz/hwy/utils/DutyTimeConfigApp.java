package com.czz.hwy.utils;

 
public class DutyTimeConfigApp {

    // 上班时间前推时间，用于设置判定上班状态开始时间
    public int DUTY_ONTIME_BEFORE;
    
    // 上班时间推后时间，用于判定上班状态 结束时间
    public int DUTY_ON_TIME_AFTER;
    
    // 下班时间前推时间，用于设置判定下班状态结束时间
    public int DUTY_OFF_TIME_BEFORE;
    
    // 下班时间推后时间,用于判定下班状态结束时间
    public int DUTY_OFF_TIME_AFTER;
    
    // 判定上班活动距离，用于判定是否正常上班，大于200米认为正常上班
    public int DUTY_ON_DISTANCE;
    
    // 判定怠工活动距离，用于判定是否怠工，小于20米认为怠工
    public int DUTY_OFF_DISTANCE;
    
    // 用于设置判定上班状态时，查询当前时间往前推？分钟的轨迹,查询当前时间到前推多少分钟的轨迹
    public int CURRENT_TIME_BEFORE;
    
    // 用于设置开始查询怠工时间，为上班时间后的？分钟，目前设为上班30分钟后
    public int SLOW_DOWN_TIME_AFTER;
    
    // 用于设置判定是否怠工时，查询当前时间往前推？分钟的轨迹，目前设置为【当前时间-30，当前时间】之间的轨迹
    public int SLOW_DOWN_TIME_BEFORE;
    
    // 查询上班地理位置异常，查询【上班时间，上班后？分钟】的轨迹中是否有位置异常
    public int ON_LOCATION_EXCEPTION_AFTER;
    
    // 查询下班地理位置异常，查询【下班前？分钟，下班时间】的轨迹中是否有位置异常
    public int OFF_LOCATION_EXCEPTION_BEFORE;
    
    // 查询考勤记录的日期是当前日期，显示是否早退，把判断是否早退时间往下班时间后退10分钟
    public int ZAO_TUI_AFTER_MINUTE;

    // 判断上班前？分钟内，是否有在责任点的轨迹，判断逻辑是【上班时间前60分钟，上班时间】内是否有轨迹是在责任点内的
    public int AT_POINT_TIME_FOR_SB;
    
    public int getDUTY_ONTIME_BEFORE() {
        return DUTY_ONTIME_BEFORE;
    }

    public void setDUTY_ONTIME_BEFORE(int dUTY_ONTIME_BEFORE) {
        DUTY_ONTIME_BEFORE = dUTY_ONTIME_BEFORE;
    }

    public int getDUTY_ON_TIME_AFTER() {
        return DUTY_ON_TIME_AFTER;
    }

    public void setDUTY_ON_TIME_AFTER(int dUTY_ON_TIME_AFTER) {
        DUTY_ON_TIME_AFTER = dUTY_ON_TIME_AFTER;
    }

    public int getDUTY_OFF_TIME_BEFORE() {
        return DUTY_OFF_TIME_BEFORE;
    }

    public void setDUTY_OFF_TIME_BEFORE(int dUTY_OFF_TIME_BEFORE) {
        DUTY_OFF_TIME_BEFORE = dUTY_OFF_TIME_BEFORE;
    }

    public int getDUTY_OFF_TIME_AFTER() {
        return DUTY_OFF_TIME_AFTER;
    }

    public void setDUTY_OFF_TIME_AFTER(int dUTY_OFF_TIME_AFTER) {
        DUTY_OFF_TIME_AFTER = dUTY_OFF_TIME_AFTER;
    }

    public int getDUTY_ON_DISTANCE() {
        return DUTY_ON_DISTANCE;
    }

    public void setDUTY_ON_DISTANCE(int dUTY_ON_DISTANCE) {
        DUTY_ON_DISTANCE = dUTY_ON_DISTANCE;
    }

    public int getDUTY_OFF_DISTANCE() {
        return DUTY_OFF_DISTANCE;
    }

    public void setDUTY_OFF_DISTANCE(int dUTY_OFF_DISTANCE) {
        DUTY_OFF_DISTANCE = dUTY_OFF_DISTANCE;
    }

    public int getCURRENT_TIME_BEFORE() {
        return CURRENT_TIME_BEFORE;
    }

    public void setCURRENT_TIME_BEFORE(int cURRENT_TIME_BEFORE) {
        CURRENT_TIME_BEFORE = cURRENT_TIME_BEFORE;
    }

    public int getSLOW_DOWN_TIME_AFTER() {
        return SLOW_DOWN_TIME_AFTER;
    }

    public void setSLOW_DOWN_TIME_AFTER(int sLOW_DOWN_TIME_AFTER) {
        SLOW_DOWN_TIME_AFTER = sLOW_DOWN_TIME_AFTER;
    }

    public int getSLOW_DOWN_TIME_BEFORE() {
        return SLOW_DOWN_TIME_BEFORE;
    }

    public void setSLOW_DOWN_TIME_BEFORE(int sLOW_DOWN_TIME_BEFORE) {
        SLOW_DOWN_TIME_BEFORE = sLOW_DOWN_TIME_BEFORE;
    }

    public int getON_LOCATION_EXCEPTION_AFTER() {
        return ON_LOCATION_EXCEPTION_AFTER;
    }

    public void setON_LOCATION_EXCEPTION_AFTER(int oN_LOCATION_EXCEPTION_AFTER) {
        ON_LOCATION_EXCEPTION_AFTER = oN_LOCATION_EXCEPTION_AFTER;
    }

    public int getOFF_LOCATION_EXCEPTION_BEFORE() {
        return OFF_LOCATION_EXCEPTION_BEFORE;
    }

    public void setOFF_LOCATION_EXCEPTION_BEFORE(
            int oFF_LOCATION_EXCEPTION_BEFORE) {
        OFF_LOCATION_EXCEPTION_BEFORE = oFF_LOCATION_EXCEPTION_BEFORE;
    }

    public int getZAO_TUI_AFTER_MINUTE() {
        return ZAO_TUI_AFTER_MINUTE;
    }

    public void setZAO_TUI_AFTER_MINUTE(int zAO_TUI_AFTER_MINUTE) {
        ZAO_TUI_AFTER_MINUTE = zAO_TUI_AFTER_MINUTE;
    }

    public int getAT_POINT_TIME_FOR_SB() {
        return AT_POINT_TIME_FOR_SB;
    }

    public void setAT_POINT_TIME_FOR_SB(int aT_POINT_TIME_FOR_SB) {
        AT_POINT_TIME_FOR_SB = aT_POINT_TIME_FOR_SB;
    }

}
