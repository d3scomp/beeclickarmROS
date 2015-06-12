package d3scomp.beeclickarmROS;

import java.net.URI;
import java.net.URISyntaxException;

import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMainExecutor;

public class EquipmentProviderRunner {
	public static void main(String[] args) throws URISyntaxException {
		EquipmentBoardNode node = new EquipmentBoardNode();
		
		NodeConfiguration config = NodeConfiguration.newPublic("192.168.56.1", new URI("http://192.168.56.101:11311"));
		
		NodeMainExecutor nodeMainExecutor = DefaultNodeMainExecutor.newDefault();
		nodeMainExecutor.execute(node, config);
	}
}
