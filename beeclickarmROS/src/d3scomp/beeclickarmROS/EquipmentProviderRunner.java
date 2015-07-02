package d3scomp.beeclickarmROS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;

import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

import d3scomp.beeclickarmj.Comm;
import d3scomp.beeclickarmj.CommException;
import d3scomp.beeclickarmj.JSSCComm;

public class EquipmentProviderRunner {
	public static void main(String[] args) throws URISyntaxException, CommException, IOException {
		// Check arguments
		if(args.length != 1) {
			System.err.println("Need a name of the serial port top open as a single parameter.");
			return;
		}
		final String serialName = args[0];
		
		// ROS parameters
		final String nodeHost = InetAddress.getLocalHost().getHostName();
		final URI masterURI = new URI(System.getenv("ROS_MASTER_URI"));
				
		// Setup board connection
		Comm comm = new JSSCComm(serialName);
		comm.start();
		comm.setAddr(0xBABA, 0x0103);
		comm.setChannel(0);
		
		// Common node configuration
		NodeConfiguration nodeConfig = NodeConfiguration.newPublic(nodeHost, masterURI);
		NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
			
		// Run ROS SHT1x node
		SHT1xNode sht1xNode = new SHT1xNode(comm);
		nodeMainExecutor.execute(sht1xNode, nodeConfig);
		
		// Run ROS GPS node
		GPSNode gpsNode = new GPSNode(comm);
		nodeMainExecutor.execute(gpsNode, nodeConfig);
		
		// Run MRF24J40 node
		MRF24J40Node mrf24j40Node = new MRF24J40Node(comm);
		nodeMainExecutor.execute(mrf24j40Node, nodeConfig);
		
		
		System.out.println("Press enter to exit");
		System.in.read();
		
		System.out.println("Exitting");
		comm.shutdown();
		nodeMainExecutor.shutdown();
	}
}
