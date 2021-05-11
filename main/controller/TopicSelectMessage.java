package main.controller;

public class TopicSelectMessage implements Message{
	private String topic;

	/**
     	* ctor
     	* @param topic given topic as a string
     	*/
	public TopicSelectMessage(String topic){
		this.topic = topic;
	}

	/**
     	* @return topic as a string
     	*/
	public String getTopic() {
		return topic;
	}
}
