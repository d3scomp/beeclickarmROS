package d3scomp.beeclickarmROS;

import java.util.Date;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import std_msgs.Float64;
import d3scomp.beeclickarmj.Comm;
import d3scomp.beeclickarmj.GPSReadingListener;


public class GPSNode extends AbstractNodeMain {
	private final Comm boardComm;
	
	public GPSNode(Comm boardComm) {
		this.boardComm = boardComm;
	}

	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("gps");
	}
	
	@Override
	public void onStart(ConnectedNode connectedNode) {		
		Publisher<std_msgs.Float64> latPublisher = connectedNode.newPublisher(getDefaultNodeName() + "/lat", std_msgs.Float64._TYPE);
		Publisher<std_msgs.Float64> lonPublisher = connectedNode.newPublisher(getDefaultNodeName() + "/lon", std_msgs.Float64._TYPE);
		
		// Publish new humidity reading when received
		boardComm.setGPSReadingListener(new GPSReadingListener() {
			@Override
			public void readGPS(double longtitude, double lattitude, Date time) {
				Float64 latMsg = latPublisher.newMessage();
				Float64 lonMsg = lonPublisher.newMessage();
				
				lonMsg.setData(lattitude);
				latMsg.setData(longtitude);
				
				latPublisher.publish(latMsg);
				lonPublisher.publish(lonMsg);
			}
		});
	}
}
