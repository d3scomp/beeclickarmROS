package d3scomp.beeclickarmROS;

import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;


public class EquipmentBoardNode extends AbstractNodeMain {

	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("STM32F4EquipmentBoard");
	}

}
