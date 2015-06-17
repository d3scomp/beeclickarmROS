package d3scomp.beeclickarmROS;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import sensor_msgs.RelativeHumidity;
import sensor_msgs.Temperature;
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
		Publisher<Temperature> tempPublisher = connectedNode.newPublisher(getDefaultNodeName() + "/temperature", Temperature._TYPE);
		Publisher<RelativeHumidity> humiPublisher = connectedNode.newPublisher(getDefaultNodeName() + "/humidity", RelativeHumidity._TYPE);
		
		
		// Publish new temperature reading when received
		boardComm.setTemperatureReadingListener(new TemperatureReadingListener() {
			@Override
			public void readTemperature(float temperature) {
				Temperature temp = tempPublisher.newMessage();
				temp.setTemperature(temperature);
				tempPublisher.publish(temp);
			}
		});
		
		// Publish new humidity reading when received
		boardComm.setHumidityReadingListener(new HumidityReadingListener() {
			@Override
			public void readHumidity(float humidity) {
				RelativeHumidity msg = humiPublisher.newMessage();
				msg.setRelativeHumidity(humidity);
				humiPublisher.publish(msg);
			}
		});
	}
}
