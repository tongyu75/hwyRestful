/**
 * 
 */
package com.czz.hwy.service.manager.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.czz.hwy.bean.manager.Message;
import com.czz.hwy.bean.manager.Topics;
import com.czz.hwy.dao.manager.MessageDao;
import com.czz.hwy.dao.manager.SubTopicsDao;
import com.czz.hwy.dao.manager.TopicsDao;
import com.czz.hwy.service.manager.MessageService;
import com.czz.hwy.service.manager.SubTopicsService;
import com.czz.hwy.utils.MqttSendMessage;

/**
 * 消息ServiceImpl接口
 * @Author 佟士儒
 * @Company chengzhongzhi
 * @createDate 2016/11/02
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {
    
    @Autowired
    private MessageDao messageDao;
    
    @Autowired
    private TopicsDao topicsDao;
    
    @Autowired
    private SubTopicsDao subTopics;
    
    @Autowired
    private SubTopicsService subTopicsService;
    
    @Autowired
    private MqttSendMessage mqttSendMessage;
    
    /**
     * 根据条件查询消息列表(带分页)
     * @param message
     * @return
     */
    public List<Message> getMessageByBean(Message message) {
        return messageDao.getInfoListByBean("getMessageByBean", message);
    }

    /**
     * 根据条件查询消息个数
     * @param message
     * @return
     */
    public int getMessageByBeanCount(Message message) {
        return messageDao.getInfoCount("getMessageByBeanCount", message);
    }
    
    /**
     * 推送通知消息到个人
     * @param topInfo     推送的消息
     * @param topicsList  推送接收者（多人）
     * @param msg         
     * @param userss
     * 首先做消息推送到mqtt服务器端操作，然后是推送的消息入库记录操作，防止消息没有推送成功，但是消息已经入库行为
     */
    public void sendMessage(Map<String, Object> topInfo,List<String> topicsList,Message msg) {
        // 1.推送消息
        // mqttSendMessage.sendListMessages(topInfo, topicsList);
        
        // 2.发送消息入库及推送都应在一个事物中，如果调用mqtt失败也应该回滚事物
        messageDao.insertInfo("insertMessage", msg);
        
        // 3.推送消息入库
        for(String employeeId : topicsList){
            Topics topics = new Topics();
            topics.setEmployeeId(Integer.parseInt(employeeId));
            topics.setPublishTime(new Date());
            topics.setContent(msg.getContent());
            topics.setTitle(msg.getTitle());
            topicsDao.insertInfo("insertTopics", topics);
        }
    }
}
