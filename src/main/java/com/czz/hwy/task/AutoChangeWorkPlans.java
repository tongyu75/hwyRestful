package com.czz.hwy.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.czz.hwy.bean.manager.AdminLogs;
import com.czz.hwy.bean.mission.WorkPlans;
import com.czz.hwy.service.manager.AdminLogsService;
import com.czz.hwy.service.mission.WorkPlansService;
import com.czz.hwy.utils.CalendarUntil;

/**
 * 自动执行换班计划
 * @author 张咏雪
 *
 */
@Component("autoChangeWorkPlans")
public class AutoChangeWorkPlans {

	@Autowired
	private WorkPlansService workPlansService;//新系统排班计划业务层
	
	@Autowired
	private AdminLogsService adminLogsService;
	
	private static final int minute = 30;//每30分钟循环一次
	
	//任务调度时间 
//	private final static String  cron = "0 0/30 * * * ?"; //每30分钟，判断一次是否有需要换班的，若是有，则根据具体的换班规则，执行换班。只部署在180服务器上
	private final static String  cron = "0 0/30 * * * ?"; //每30分钟，判断一次是否有需要换班的，若是有，则根据具体的换班规则，执行换班。只部署在218服务器，9090端口上
	
	public static void main(String[] args) throws ParseException {
		
		int day = CalendarUntil.day(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = sdf.parse("2016-11-05 22:30:00");
		int week = CalendarUntil.week(date);//week(new Date());
		System.out.println(day);
		System.out.println(week);
	}
	
	//@Scheduled(cron=cron)
	public void changeWorkPlans(){
		int createId = 2015101501;
		Date nowDate = new Date();//当前日期
		nowDate.setSeconds(0);
		int week = CalendarUntil.week(nowDate);//获取当前日期是周几,1是周一 ，7是周天
		int day = CalendarUntil.day(nowDate);//获取当前日期是几号， 1是1号
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nowDate", nowDate);
		map.put("minute", minute);
		map.put("week", week);
		map.put("day", day);
		
		//1. 判定当前时间是否有需要换班的排班计划，若无，则退出该方法，若有，则进行执行换班
		List<WorkPlans> workPlansList = workPlansService.getWorkPlansListByMap(map); // 查询出【当前日期前30分钟，当前日期】需要换班的排班计划，2016-10-11
		if(workPlansList.size() == 0){//如果没有需要换班的排班计划，则退出该方法
			return;
		}
		
		List<Integer> changePlansEmployeeIds = new ArrayList<Integer>();//执行换班计划，且生成新的排班计划的员工ID集合，需要生成新的排班计划的员工集合
		List<Integer> deletePlansEmployeeIds = new ArrayList<Integer>();//执行换班计划，需要删除旧的排班计划的员工ID集合，用于删除旧的排班计划的员工集合
		List<WorkPlans> newWorkPlansList = new ArrayList<WorkPlans>();//保存换班后的排班计划，用于批量生成新的排班计划。
		
		//2. 循环需要换班的人员，并组合换班后的排班集合，
		//情况有两种：一种是换班人员有排班计划，将其排班计划换成该员工的排班计划。
		//第二种是换班人员无排班计划，将其排班计划换给换班人员，该员工无排班计划。
		int employeeId;
		int tradeEmployeeId;
		for(WorkPlans workPlans : workPlansList){
			employeeId = workPlans.getEmployeeId();
			tradeEmployeeId = workPlans.getTradeEmployeeId();
			deletePlansEmployeeIds.add(employeeId);//保存要删除排版计划的人员ID
			
			List<WorkPlans> tradeList = workPlansService.getWorkPlansListByEmployeeId(tradeEmployeeId + "");//获取某人员的排班计划集合，2016-10-12
			if(tradeList.size() == 0){//若换班人员无排班计划，将其排班计划换给换班人员，该员工本次轮空
				changePlansEmployeeIds.add(tradeEmployeeId);//保存应该执行新排班计划的人员ID
				//查询当前员工的排班计划
				List<WorkPlans> curList = workPlansService.getWorkPlansListByEmployeeId(employeeId + "");//获某人员的排班计划集合，2016-10-12
				newWorkPlansList.addAll(getChangedWorkPlans(curList, tradeEmployeeId, employeeId,nowDate, createId));//保存换班之后的排班计划
			}else{
				changePlansEmployeeIds.add(employeeId);//保存应该执行新排班计划的人员ID
				newWorkPlansList.addAll(getChangedWorkPlans(tradeList, employeeId, tradeEmployeeId,nowDate, createId));//保存换班之后的排班计划
			}
		}
		
		String changeEmployeeIds = changePlansEmployeeIds.toString().substring(1, changePlansEmployeeIds.toString().length() - 1);
		String deleteEmployeeIds = deletePlansEmployeeIds.toString().substring(1, deletePlansEmployeeIds.toString().length() - 1);
		
		//3. 删除新系统旧的排班计划
		workPlansService.deleteWorPlansByEmployeeIds(deleteEmployeeIds);//根据员工ID集合，删除新系统排班计划对应的数据，2016-10-12
		
		//4. 新增新系统新的排班计划
		int result = workPlansService.batchAddWorkPlans(newWorkPlansList);//批量新增排班计划
		
		//5. 删除旧系统的排班计划
		//TODO 根据员工ID集合，删除旧系统排班计划对应的数据，2016-10-12,目前设置为逻辑删除, 以后要改成物理删除
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("deleteEmployeeIds", deleteEmployeeIds);
		deleteMap.put("updateId", createId);
		deleteMap.put("updateAt", nowDate);
		workPlansService.deleteDutyForBanciByIds(deleteMap);//根据员工ID集合，删除旧系统排班计划对应的数据，2016-10-12,目前设置为逻辑删除,以后要改成物理删除,现在已经改成物理删除
		
		workPlansService.deleteDutyPlansByIds(deleteMap);//根据员工ID集合，删除旧系统排班计划对应的数据，2016-10-12,目前设置为逻辑删除,以后要改成物理删除,现在已经改成物理删除
		
		//6. 新增旧系统的排班计划
		workPlansService.batchAddDutyForBanciByIds(changeEmployeeIds);//根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
		
		workPlansService.batchAddDutyPlansByIds(changeEmployeeIds);//根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
		
		
		//7.记录系统日志
		AdminLogs adminlogs = new AdminLogs();
		adminlogs.setCreateAt(new Date());
		adminlogs.setEmployeeId(createId);
		adminlogs.setStatus("1");
		String content = "";
		
		if (result > 0) {//换班成功
			content = "自动换班成功！";
		}else {//失败
			content = "自动换班失败！";
		}
		adminlogs.setContent(content);
		adminLogsService.insertAdminLogs(adminlogs);//插入系统日志
	}
	
	/**
	 * 获取换班后的排班计划
	 * @param list       //换班之前的排班计划
	 * @param employeeId  //值班人
	 * @param tradeEmployeeId  //换班人
	 * @return
	 */
	public List<WorkPlans> getChangedWorkPlans(List<WorkPlans> list, int employeeId, int tradeEmployeeId,Date nowDate, int createId){
		//循环排班计划，保存换班后的值班人和换班人
		for(WorkPlans workPlans : list){
			workPlans.setEmployeeId(employeeId);
			workPlans.setTradeEmployeeId(tradeEmployeeId);
			workPlans.setCreateId(createId);
			workPlans.setCreateAt(nowDate);
		}
		return list;
	}
	
	
	/*@Scheduled(cron=cron)
	public void changeWorkPlans(){
		int createId = 2015101501;
		Date nowDate = new Date();//当前日期
		int week = CalendarUntil.week(nowDate);//获取当前日期是周几,1是周一 ，7是周天
		int day = CalendarUntil.day(nowDate);//获取当前日期是几号， 1是1号
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nowDate", nowDate);
		map.put("minute", minute);
		map.put("week", week);
		map.put("day", day);
		
		//1. 判定当前时间是否有需要换班的排班计划，若无，则退出该方法，若有，则进行执行换班
		List<WorkPlans> workPlansList = workPlansService.getWorkPlansListByMap(map); // 查询出【当前日期前30分钟，当前日期】需要换班的排班计划，2016-10-11
		if(workPlansList.size() == 0){//如果没有需要换班的排班计划，则退出该方法
			return;
		}
		
		List<Integer> changePlansEmployeeIds = new ArrayList<Integer>();//执行换班计划，且生成新的排班计划的员工ID集合，需要生成新的排班计划的员工集合
		List<Integer> deletePlansEmployeeIds = new ArrayList<Integer>();//执行换班计划，需要删除旧的排班计划的员工ID集合，用于删除旧的排班计划的员工集合
		List<WorkPlans> newWorkPlansList = new ArrayList<WorkPlans>();//保存换班后的排班计划，用于批量生成新的排班计划。
		
		//2. 循环需要换班的人员，并组合换班后的排班集合，
		//情况有两种：一种是换班人员有排班计划，将其排班计划换成该员工的排班计划。
		//第二种是换班人员无排班计划，将其排班计划换给换班人员，该员工无排班计划。
		int employeeId;
		int tradeEmployeeId;
		for(WorkPlans workPlans : workPlansList){
			employeeId = workPlans.getEmployeeId();
			tradeEmployeeId = workPlans.getTradeEmployeeId();
			deletePlansEmployeeIds.add(employeeId);//保存要删除排版计划的人员ID
			
			List<WorkPlans> tradeList = workPlansService.getWorkPlansListByEmployeeId(tradeEmployeeId + "");//获取某人员的排班计划集合，2016-10-12
			if(tradeList.size() == 0){//若换班人员无排班计划，将其排班计划换给换班人员，该员工本次轮空
				changePlansEmployeeIds.add(tradeEmployeeId);//保存应该执行新排班计划的人员ID
				//查询当前员工的排班计划
				List<WorkPlans> curList = workPlansService.getWorkPlansListByEmployeeId(employeeId + "");//获某人员的排班计划集合，2016-10-12
				newWorkPlansList.addAll(getChangedWorkPlans(curList, tradeEmployeeId, employeeId,nowDate, createId));//保存换班之后的排班计划
			}else{
				changePlansEmployeeIds.add(employeeId);//保存应该执行新排班计划的人员ID
				newWorkPlansList.addAll(getChangedWorkPlans(tradeList, employeeId, tradeEmployeeId,nowDate, createId));//保存换班之后的排班计划
			}
		}
		
		String changeEmployeeIds = changePlansEmployeeIds.toString().substring(1, changePlansEmployeeIds.toString().length() - 1);
		String deleteEmployeeIds = deletePlansEmployeeIds.toString().substring(1, deletePlansEmployeeIds.toString().length() - 1);
		
		//3. 删除新系统旧的排班计划
		workPlansService.deleteWorPlansByEmployeeIds(deleteEmployeeIds);//根据员工ID集合，删除新系统排班计划对应的数据，2016-10-12
		
		//4. 新增新系统新的排班计划
		int result = workPlansService.batchAddWorkPlans(newWorkPlansList);//批量新增排班计划
		
		//5. 删除旧系统的排班计划
		//TODO 根据员工ID集合，删除旧系统排班计划对应的数据，2016-10-12,目前设置为逻辑删除, 以后要改成物理删除
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("deleteEmployeeIds", deleteEmployeeIds);
		deleteMap.put("updateId", createId);
		deleteMap.put("updateAt", nowDate);
		workPlansService.deleteDutyForBanciByIds(deleteMap);//根据员工ID集合，删除旧系统排班计划对应的数据，2016-10-12,目前设置为逻辑删除,以后要改成物理删除,现在已经改成物理删除
		
		workPlansService.deleteDutyPlansByIds(deleteMap);//根据员工ID集合，删除旧系统排班计划对应的数据，2016-10-12,目前设置为逻辑删除,以后要改成物理删除,现在已经改成物理删除
		
		//6. 新增旧系统的排班计划
		workPlansService.batchAddDutyForBanciByIds(changeEmployeeIds);//根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
		
		workPlansService.batchAddDutyPlansByIds(changeEmployeeIds);//根据员工ID集合，查询出新系统对应的排班计划集合，并将这些排班计划新增到旧系统排班计划表中去。2016-10-12
		
		
		//7.记录系统日志
		AdminLogs adminlogs = new AdminLogs();
		adminlogs.setCreateAt(new Date());
		adminlogs.setEmployeeId(createId);
		adminlogs.setStatus("1");
		String content = "";
		
		if (result > 0) {//换班成功
			content = "自动换班成功！";
		}else {//失败
			content = "自动换班失败！";
		}
		adminlogs.setContent(content);
		adminLogsService.insertAdminLogs(adminlogs);//插入系统日志
	}*/
}
