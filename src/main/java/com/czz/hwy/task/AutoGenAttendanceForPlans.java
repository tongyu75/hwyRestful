package com.czz.hwy.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.czz.hwy.service.usermanagement.app.AttendanceForPlansAppService;

/**
 * 每天凌晨零点一分自动生成考勤记录
 * @author 张咏雪
 * @date 2016-10-28
 *
 */
@Component("autoGenAttendanceForPlans")
public class AutoGenAttendanceForPlans {

    @Autowired
    private AttendanceForPlansAppService attendanceForPlansAppService;
	//任务调度时间
//	private final static String  cron = "0 1 0 * * ?" ; //每天凌晨零点一分自动生成考勤记录，只部署在180服务器上
	private final static String  cron = "0 1 0 * * ?" ; //每天凌晨零点一分自动生成考勤记录，只部署在218服务器，9090端口上
	
	//@Scheduled(cron=cron)
    public void autoGenAttendanceForPlans(){
	    attendanceForPlansAppService.autoGenAttendanceForPlans();
    }
}
