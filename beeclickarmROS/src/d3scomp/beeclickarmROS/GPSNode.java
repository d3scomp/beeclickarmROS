package d3scomp.beeclickarmROS;

import java.util.Date;

import org.ros.message.Time;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

import sensor_msgs.NavSatFix;
import sensor_msgs.TimeReference;
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
		Publisher<NavSatFix> positionPublisher = connectedNode.newPublisher(getDefaultNodeName() + "/position", NavSatFix._TYPE);
		Publisher<TimeReference> timePublisher = connectedNode.newPublisher(getDefaultNodeName() + "/time", TimeReference._TYPE);
		
		// Publish new humidity reading when received
		boardComm.setGPSReadingListener(new GPSReadingListener() {
			@Override
			public void readGPS(double longtitude, double lattitude, Date time) {
				NavSatFix positionfix = positionPublisher.newMessage();
				
				TimeReference timeFix = timePublisher.newMessage();
				
				positionfix.setLatitude(lattitude);
				positionfix.setLongitude(longtitude);
			
				timeFix.setSource("GPS");
				timeFix.setTimeRef(Time.fromMillis(time.getTime()));
				
				positionPublisher.publish(positionfix);
				timePublisher.publish(timeFix);
			}
		});
	}
}
