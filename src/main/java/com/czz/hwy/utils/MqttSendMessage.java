package com.czz.hwy.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.czz.hwy.service.manager.SubTopicsService;
/**
 * 定时发送通知消息
 * @author Administrator
 *
 */
@Component
public class MqttSendMessage {
	
    @Autowired
    private SubTopicsService subTopicsService;
    
	/**
	 * 通过员工ID推送消息到个人
	 * @param topInfo 推送的消息
	 * @param employeeId 推送接收者（个人）
	 */
	/*public void sendMessages(Map<String, Object> topInfo, String employeeId) {
		// 获取消息信息
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(topInfo,jsonConfig);
		// 将消息发送给第一责任人
		ClientPub clientPub = ClientPub.getInterface();
		// 获取mqtt配置信息 
		Properties properties = ReadFile.getProperties();
		String newIpStr = properties.getProperty("subIp");
		MqttClient mqttClient = clientPub.createConn(newIpStr);
		
        String topics = StringUtils.EMPTY;
        SubTopics bean = subTopicsService.getSubTopicsByBean(Integer.parseInt(employeeId));
        if (bean != null) {
            topics = bean.getTopics();
            clientPub.publishMessage(topics, json.toString(), 2, mqttClient);
        }
	}

	*//**
     * 通过员工ID推送消息到个人
     * @param topInfo 推送的消息
     * @param firstUser 推送接收者（多人）
     *//*
    public void sendListMessages(Map<String, Object> topInfo, List<String> topicsList) {
        // 获取消息信息
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class,
                new JsonDateValueProcessor());
        JSONObject json = JSONObject.fromObject(topInfo,jsonConfig);
        // 将消息发送给第一责任人
        ClientPub clientPub = ClientPub.getInterface();
        // 获取mqtt配置信息 
        Properties properties = ReadFile.getProperties();
        String newIpStr = properties.getProperty("subIp");
        MqttClient mqttClient = clientPub.createConn(newIpStr);
        for (String employeeId : topicsList) {
            SubTopics bean = subTopicsService.getSubTopicsByBean(Integer.parseInt(employeeId));
            if (bean != null) {
                String topics = bean.getTopics();
                clientPub.publishMessage(topics, json.toString(), 2, mqttClient);
            }
        }
    }

     *//**
      * 通过取得的topics直接推送
      * @param topInfo 推送的消息
      * @param firstUser 推送接收者（个人）
      *//*
     public void sendTopicsMessages(Map<String, Object> topInfo, List<String> topicsList) {
         // 获取消息信息
         JsonConfig jsonConfig = new JsonConfig();
         jsonConfig.registerJsonValueProcessor(Date.class,
                 new JsonDateValueProcessor());
         JSONObject json = JSONObject.fromObject(topInfo,jsonConfig);
         // 将消息发送给第一责任人
         ClientPub clientPub = ClientPub.getInterface();
         // 获取mqtt配置信息 
         Properties properties = ReadFile.getProperties();
         String newIpStr = properties.getProperty("subIp");
         MqttClient mqttClient = clientPub.createConn(newIpStr);
         for (String user : topicsList) {
             clientPub.publishMessage(user, json.toString(), 2, mqttClient);
         }
     }*/
}
