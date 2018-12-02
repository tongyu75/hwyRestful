package com.czz.hwy.action.manager.watch;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.czz.hwy.service.manager.watch.LeaveWatchService;
import com.czz.hwy.utils.AccessOrigin;
import com.czz.hwy.utils.CalendarUntil;
import com.czz.hwy.utils.ConstantUtil;

/**
 * 请假管理
 * @Author 佟士儒
 * @Company chengzhongzhi
 * @createDate 2016/10/27
 */

@Controller
@RequestMapping(value = "/leaveManageWatchController")
public class LeaveManageWatchController{

    @Autowired
    private LeaveWatchService leaveWatchService;
    
    @Resource
    private AccessOrigin accessOrigin;    
    
    /**
     * 请假申请
     * @param applyId 申请者ID
     * @param applyName 申请者名字
     * @param leaveContent 请假理由
     * @param leaveFromTime 请假开始时间
     * @param leaveToTime 请假结束时间
     * @param applyRole 角色编号
     */
    @RequestMapping(value="/leaveWatch", method = RequestMethod.POST)
    @ResponseBody    
    public Map<String, Object> leaveApply(Integer applyId, String applyName, String leaveContent,
            String leaveFromTime, String leaveToTime, Integer applyRole) {

        // 初始化返回值信息
        Map<String, Object> map = new HashMap<String, Object>();
        // 判断请求信息是否为空
        if (applyId == null || StringUtils.isEmpty(applyName) || StringUtils.isEmpty(leaveContent) 
                || StringUtils.isEmpty(leaveFromTime) || StringUtils.isEmpty(leaveToTime) || applyRole == null ) {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.RESQUEST_INFO_ERR);
            return map;
        }
        
		if(leaveFromTime.compareTo(leaveToTime) > 0){//请假开始日期大于请假结束日期，给予提示
			  map.put("result", ConstantUtil.FAIL);
	          map.put("information", ConstantUtil.DATE_ERROR_FOR_LEAVE);
	          return map;
		}
        
        int leaveNumber = 0;
        int leaveTimeNumber = 0;
		if(leaveFromTime.length() > 10){
			 leaveNumber = CalendarUntil.getDays(leaveFromTime, leaveToTime);// 请假天数 请假结束时间的年月日 - 请假开始时间的年月日，与请假小时拼成完整的请假天数， 2016-09-22
		        
		        //请假小时数，（请假结束时间的时分 - 请假开始时间的时分），与请假天数拼成完整的请假天数,2016-09-22
			 leaveTimeNumber  = CalendarUntil.getHours(leaveFromTime, leaveToTime);  	
		}else{
			leaveNumber = CalendarUntil.getDates(leaveFromTime, leaveToTime);// 请假天数 请假结束时间的年月日 - 请假开始时间的年月日，与请假小时拼成完整的请假天数， 2016-09-22
			  //请假小时数，（请假结束时间的时分 - 请假开始时间的时分），与请假天数拼成完整的请假天数,2016-09-22
			leaveTimeNumber = 0;
		}
       
        // 插入请假记录并且进行相关的推送
        int result = leaveWatchService.leaveApply(applyId.intValue(), applyName, applyRole.intValue(), leaveContent, 
                leaveNumber, leaveTimeNumber, leaveFromTime, leaveToTime);
        if (result == 1) {
            map.put("result", ConstantUtil.SUCCESS);
            map.put("information", ConstantUtil.LEAVE_SUCCESS);
        } else {
            map.put("result", ConstantUtil.FAIL);
            map.put("information", ConstantUtil.LEAVE_FAIL);
        }
        
        //返回成功信息
        return map;
    }
}
