package d3scomp.beeclickarmROS;

import java.io.IOException;
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
		// Setup board connection
		Comm comm = new JSSCComm("COM7");

		comm.start();

		comm.setAddr(0xBABA, 0x0103);
		comm.setChannel(0);
		
		// Common node configuration
		NodeConfiguration nodeConfig = NodeConfiguration.newPublic("192.168.56.1", new URI("http://192.168.56.101:11311"));
		NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
			
		// Run ROS SHT1x node
		SHT1xNode sht1xNode = new SHT1xNode(comm);
		nodeMainExecutor.execute(sht1xNode, nodeConfig);
		
		// Run ROS GPS node
		GPSNode gpsNode = new GPSNode(comm);
		nodeMainExecutor.execute(gpsNode, nodeConfig);
		
		
		System.out.println("Press enter to exit");
		System.in.read();
		
		System.out.println("Exitting");
		comm.shutdown();
		nodeMainExecutor.shutdown();
	}
}
