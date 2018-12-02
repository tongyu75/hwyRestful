package com.czz.hwy.service.manager.watch;


/**
 * 请假管理业务实现
 * @Author 佟士儒
 * @Company chengzhongzhi
 * @createDate 2016/05/07
 */
public interface LeaveWatchService {

    public int leaveApply(int applyId, String applyName, int applyRole, String leaveContent, 
            int leaveNumber, int leaveTimeNumber, String leaveFromTime, String leaveToTime);
}
