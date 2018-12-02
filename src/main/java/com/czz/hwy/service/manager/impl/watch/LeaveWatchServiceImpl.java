package com.czz.hwy.service.manager.impl.watch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.czz.hwy.bean.manager.watch.Leave;
import com.czz.hwy.bean.mission.watch.WorkPlans;
import com.czz.hwy.bean.user.watch.Users;
import com.czz.hwy.dao.manager.watch.LeaveWatchDao;
import com.czz.hwy.dao.mission.watch.DutyPlansWatchDao;
import com.czz.hwy.dao.user.watch.UsersWatchDao;
import com.czz.hwy.service.manager.watch.LeaveWatchService;
import com.czz.hwy.utils.MqttSendMessage;

/**
 * 请假管理业务接口
 * @Author 佟士儒
 * @Company chengzhongzhi
 * @createDate 2016/10/27
 */
@Service
public class LeaveWatchServiceImpl implements LeaveWatchService {

    // 请假管理dao层接口
    @Autowired
    private LeaveWatchDao leaveWatchDao;
    
    // 出勤计划dao层接口
    @Autowired
    private DutyPlansWatchDao dutyPlansWatchDao;
    
    // 用户信息dao层接口
    @Autowired
    private UsersWatchDao usersWatchDao;
    
    @Autowired
    private MqttSendMessage mqttSendMessage;
    
    // 日期format
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    // 日期format
    SimpleDateFormat hms = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    /**
     * 请假申请，2016-09-18：若是上级是考核员或督察员，则推送请假申请给所有的考核员或督察员
     * 2016-10-19:（1）环卫工填写请假申请单，请假天数小于等于1天，由检测员审批完成结束，大于1天的请假单由检测员、考核员审批完成结束
     *               （2）检测员填写请假申请单，由考核员审批完成结束
     *              （3） 考核员填写申请单，由督查员审批完成结束
     *            
     * @param bean
     * @return 请假申请
     */
    public int leaveApply(int applyId, String applyName, int applyRole, String leaveContent, int leaveNumber,int leaveTimeNumber,
            String fromTime, String toTime) {
        // 当前时间
        Date date = new Date();
        
        // 请假开始时间
        Date leaveFromTime = null;
        // 请假结束时间
        Date leaveToTime = null;
        try {
            leaveFromTime = hms.parse(fromTime);
            leaveToTime = hms.parse(toTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        int result = 0;//返回结果标识
        // 责任区ID
        int dutyAreaId = 0;
        String areasId = "";//记录请假人所有的责任区ID集合
        // 责任点ID
        int dutyPointId = 0;
        WorkPlans bean = new WorkPlans();
        // 员工ID
        bean.setEmployeeId(applyId);
        bean.setRoleId(applyRole);
        List<WorkPlans> plansList = dutyPlansWatchDao.getWorkPlansListByLeaveWatch(bean);//根据员工ID获取其所负责的责任区，2016-11-04
        if (plansList != null && plansList.size() > 0) {
            // 员工对应的责任区和责任点
            WorkPlans p = plansList.get(0);
            // 责任区ID
            dutyAreaId = p.getAreaId();
            areasId +=  p.getAreaId() + ",";
            // 责任点ID
            dutyPointId = p.getPointId();
        }else{
            result = 2;
            return result;
        }
        areasId = areasId.substring(0, areasId.length() - 1);
        
        // 上级员工ID
        int employeeId = 0;
        // 推送接收者角色
        int pushRoleId = 0;
        // 上级信息
       /* DutyPlans plans = new DutyPlans();
        // 责任区ID
        plans.setDutyAreaId(dutyAreaId);
        if (applyRole != 3) {
            // 责任点ID
            plans.setDutyPointId(dutyPointId);
        }*/
        
        WorkPlans workPlans = new WorkPlans();//用于查询上级信息条件
        workPlans.setAreaId(dutyAreaId);
        workPlans.setAreasId(areasId);
        List<WorkPlans> workPlansList = null;//用于保存直接上级列表
        
        Users users = new Users();
        // 环卫工
        if (applyRole == 1) {
            workPlans.setRoleId(applyRole + 1);//设置角色为检测员
            workPlansList = dutyPlansWatchDao.getWorkPlansListByLeaveRoles(workPlans);//根据角色ID和责任区ID获取排班计划，2016-11-03
            if(workPlansList.size() > 0){//如果有检测员上级，则将消息推送给直接上级（检测员）
                // 设置接收角色类型为检测员
                pushRoleId = 2;
                users.setRoleId(2);
            }else{//若是无直接上级，则将详细推送给直接上级（考核员）
                workPlans.setRoleId(applyRole + 2);//设置角色为考核员
                workPlansList = dutyPlansWatchDao.getWorkPlansListByLeaveRoles(workPlans);//根据角色ID和责任区ID获取排班计划，2016-11-03
                if(workPlansList.size() > 0){
                    // 设置接收角色类型为考核员
                    pushRoleId = 3;
                    users.setRoleId(3);
                }
            }
        // 检测员
        } else if (applyRole == 2) {
            workPlans.setRoleId(applyRole + 1);//设置角色为考核员
            workPlansList = dutyPlansWatchDao.getWorkPlansListByLeaveRoles(workPlans);//根据角色ID和责任区ID获取排班计划，2016-11-03
            if(workPlansList.size() > 0){//如果有考核员上级，则将消息推送给直接上级（考核员）
                // 设置接收角色类型为考核员
                 pushRoleId = 3;
                 users.setRoleId(3);
            }
        }
        else if (applyRole == 3) {
            // 接收角色类型
            pushRoleId = 4;
            users.setRoleId(4);
        }
        
        // 请假记录Bean
        Leave leave = new Leave();
        // 申请者ID
        leave.setApplyId(applyId);
        // 申请者名字
        leave.setApplyName(applyName);
        // 角色编号
        leave.setApplyRole(applyRole);
        // 请假理由
        leave.setLeaveContent(leaveContent);
        // 请假天数
        leave.setLeaveNumber(leaveNumber);
        //请假小时
        leave.setLeaveTimeNumber(leaveTimeNumber);
        //第一步审批人角色ID
        leave.setFirstApprRoleId(pushRoleId);
        
        double leavedays = Double.parseDouble(leaveNumber + "." + leaveTimeNumber);
        
        if(applyRole == 1){//如果请假人是 环卫工
             if(leavedays <= 1){//请假天数小于一天
                if(pushRoleId == 3){//如果请假人是环卫工，而推送是考核员，则将最后一步审批人角色设置为考核员
                    leave.setApprNumber(3);
                }else{//如果请假人是环卫工，而推送是检测员，则将最后一步审批人角色设置为检测员
                    leave.setApprNumber(2);
                }
             }else{//请假天数大于一天，最后一步审批人角色为考核员
                leave.setApprNumber(3);
             }
        }else if(applyRole == 2){//如果请假人是检测员
            leave.setApprNumber(3);//最后一步审批人角色为考核员
        }else if(applyRole == 3){//如果请假人是考核员
            leave.setApprNumber(4);//最后一步审批人角色为督察员
        }
        
        // 请假开始时间
        leave.setLeaveFromTime(leaveFromTime);
        // 请假结束时间
        leave.setLeaveToTime(leaveToTime);
        // 请假提交时间
        leave.setLeaveApplTime(date);
        // 审核状态(2.审核中)
        leave.setApprStatus(2);
        // 创建时间
        leave.setCreateAt(date);
        // 更新时间
        leave.setUpdateAt(date);
        // 责任区ID        
        leave.setDutyAreaId(dutyAreaId);
        // 责任点ID
        leave.setDutyPointId(dutyPointId);
        // 审核人
        leave.setLeaveApprId(employeeId);
        // 审核人角色
        leave.setApprRole(pushRoleId);
        // 检测员审核状态(0.未操作)
        leave.setApprTwoStatus(0);
        // 考核员审核状态(0.未操作)
        leave.setApprThreeStatus(0);
        // 督查员审核状态(0.未操作)
        leave.setApprFourStatus(0);
       
        List<Users> lstUser = null;
        if(workPlansList != null && workPlansList.size() == 0 && applyRole != 3){//请假审批人是环卫工或检测员的没有直接上级，不能进行请假
            result = 2;
            return result;
        }else if(applyRole == 3){//请假审批人是考核员的没有直接上级，不能进行请假
            lstUser = usersWatchDao.getInfoListByBean("getAllInfoByBeanWatch", users);
            if(lstUser.size() == 0){
                result = 2;
                return result;
            }
        }
        
        // 插入请假记录
        result = leaveWatchDao.insertInfo("insertLeaveByBeanWatch", leave);
        if (result == 1) {
            List<String> userList = new ArrayList<String>();
            if(applyRole == 1 || applyRole == 2){//如果请假人是环卫工或检测员
                 for(WorkPlans plans : workPlansList){
                     userList.add(plans.getEmployeeId() + "");
                 }
            }else if(applyRole == 3){//如果请假人是考核员
                 for(Users u : lstUser){
                     userList.add(u.getEmployeeId() + "");
                 }
            }
            this.sendLeaveMessage(userList);
        }
        return result;
    }
    
    /**
     * 推送请假消息,推送给多人
     * @param bean
     * @return 
     */    
    public void sendLeaveMessage(List<String> userList) {
        // 向上一级推送请假信息
        Map<String, Object> topicsInfo = new HashMap<String, Object>();
        topicsInfo.put("titleName", "您有新的请假信息要处理！");
        topicsInfo.put("checkTime", "");
        topicsInfo.put("checkAddress", "");
        topicsInfo.put("lat", "");
        topicsInfo.put("lng", "");
        topicsInfo.put("checkId", "");
        topicsInfo.put("evalType", "");
        topicsInfo.put("status", "");
        topicsInfo.put("type", 5);
        
        // mqttSendMessage.sendListMessages(topicsInfo, userList);
    }
}
