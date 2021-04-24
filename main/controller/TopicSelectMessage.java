package main.controller;

public class TopicSelectMessage implements Message{
	private String topic;
	
	public TopicSelectMessage(String topic){
		this.topic = topic;
	}
	
	public String getTopic() {
		return topic;
	}
}
