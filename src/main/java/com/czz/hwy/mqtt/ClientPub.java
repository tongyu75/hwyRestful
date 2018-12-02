package com.czz.hwy.mqtt;


/**
 * 消息发布
 * @author Administrator
 *
 */
public class ClientPub {
	private final static boolean CLEAN_START = true;
	// 低耗网络，但是又需要及时获取数据，心跳30s
	private final static short KEEP_ALIVE = 30;
	private final static String CLIENT_ID = "clientPub";
	public static ClientPub clientPub = new ClientPub();
	
	/*private static Map<String, MqttClient> map = new HashMap<String, MqttClient>();

	private ClientPub() {
	}
	public static ClientPub getInterface(){
		if(clientPub !=null){
			clientPub = new ClientPub();
		}
		return clientPub;
	}
	public MqttClient createConn(String ip) {
		MqttClient mqttClient = null;
		if(map.isEmpty() || !map.containsKey(CLIENT_ID + ip)) {
			try {
				mqttClient = new MqttClient("tcp://" + ip + ":1883");
				map.put(CLIENT_ID + ip, mqttClient);
			} catch (MqttException e) {
				e.printStackTrace();
			}
		} else {
			mqttClient = map.get(CLIENT_ID + ip);
		}
		if(mqttClient == null || !mqttClient.isConnected()) {
			try {
				mqttClient.connect(CLIENT_ID + ip, CLEAN_START, KEEP_ALIVE);
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
		return mqttClient;
	}
	
	public void publishMessage(String topic, String content, int qos, MqttClient mqttClient) {
		try {
			mqttClient.publish(topic, content.getBytes(), qos, false);
			
		} catch (MqttNotConnectedException e) {
			e.printStackTrace();
		} catch (MqttPersistenceException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (MqttException e) {
			e.printStackTrace();
		}}
		
	public void closeConn() {
		for(Entry<String, MqttClient> entry : map.entrySet()) {
			MqttClient mqttClient = entry.getValue();
			mqttClient.terminate();
		}
	}*/
	
}
