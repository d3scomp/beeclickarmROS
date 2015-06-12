package d3scomp.beeclickarmROS;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import std_msgs.Float32;
import d3scomp.beeclickarmj.Comm;
import d3scomp.beeclickarmj.HumidityReadingListener;
import d3scomp.beeclickarmj.TemperatureReadingListener;


public class SHT1xNode extends AbstractNodeMain {
	private final Comm boardComm;
	
	public SHT1xNode(Comm boardComm) {
		this.boardComm = boardComm;
	}

	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("sht1x");
	}
	
	@Override
	public void onStart(ConnectedNode connectedNode) {		
		Publisher<std_msgs.Float32> tempPublisher = connectedNode.newPublisher(getDefaultNodeName() + "/temperature", std_msgs.Float32._TYPE);
		Publisher<std_msgs.Float32> humiPublisher = connectedNode.newPublisher(getDefaultNodeName() + "/humidity", std_msgs.Float32._TYPE);
		
		
		// Publish new temperature reading when received
		boardComm.setTemperatureReadingListener(new TemperatureReadingListener() {
			@Override
			public void readTemperature(float temperature) {
				Float32 temp = tempPublisher.newMessage();
				temp.setData(temperature);
				tempPublisher.publish(temp);
			}
		});
		
		// Publish new humidity reading when received
		boardComm.setHumidityReadingListener(new HumidityReadingListener() {
			@Override
			public void readHumidity(float humidity) {
				Float32 msg = humiPublisher.newMessage();
				msg.setData(humidity);
				humiPublisher.publish(msg);
			}
		});
	}
}
