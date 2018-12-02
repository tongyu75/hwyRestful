/**
 * 
 */
package com.czz.hwy.service.manager;

import java.util.List;
import java.util.Map;

import com.czz.hwy.bean.manager.Message;


/**
 * 消息Service接口
 * @Author 佟士儒
 * @Company chengzhongzhi
 * @createDate 2016/11/02
 */
public interface MessageService {

   /**
     * 根据条件查询消息列表(带分页)
     * @param message
     * @return
     */
    List<Message> getMessageByBean(Message message);

    /**
     * 根据条件查询消息个数
     * @param message
     * @return
     */
    int getMessageByBeanCount(Message message);
    
    /**
     * 推送通知消息到个人
     * @param topInfo     推送的消息
     * @param topicsList  推送接收者（多人）
     * @param msg         
     * @param userss
     * 首先做消息推送到mqtt服务器端操作，然后是推送的消息入库记录操作，防止消息没有推送成功，但是消息已经入库行为
     */
    public void sendMessage(Map<String, Object> topInfo,List<String> topicsList,Message msg);
}
