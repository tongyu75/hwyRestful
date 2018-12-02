package com.czz.hwy.service.manager.watch;

import java.util.List;

import com.czz.hwy.bean.manager.watch.OneClickAlarmWatch;
import com.czz.hwy.bean.user.watch.Attendances;
import com.czz.hwy.bean.user.watch.Users;

/**
 * 一键报警的service接口
 * @author 张咏雪 2016-5-17
 */
public interface OneClickAlarmWatchService {

    /**
     * 根据员工Id，查询员工信息
     */
    Users getEmployeeById(String employeeId);

    /**
     * 根据员工ID，查询出与其在同一责任区内的其他员工的topics（主题）
     */
    List<String> getTopicsListById(String employeeId);

    /**
     * 插入一键报警信息
     */
    int insertAlarm(OneClickAlarmWatch oneClickAlarm);

    /**
     * 根据员工ID，查询员工最后一次位置
     */
    Attendances selectAttendancesById(String employeeId);
}
