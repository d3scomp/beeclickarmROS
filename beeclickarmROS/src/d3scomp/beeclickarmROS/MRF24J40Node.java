package d3scomp.beeclickarmROS;

import java.nio.ByteOrder;
import java.util.List;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.ros.exception.ServiceException;
import org.ros.internal.message.RawMessage;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceResponseBuilder;
import org.ros.node.service.ServiceServer;
import org.ros.node.topic.Publisher;

import beeclickarm_messages.IEEE802154ReceivedPacket;
import std_msgs.ByteMultiArray;
import std_msgs.Empty;
import std_msgs.MultiArrayDimension;
import std_msgs.MultiArrayLayout;
import d3scomp.beeclickarmj.Comm;
import d3scomp.beeclickarmj.RXPacket;
import d3scomp.beeclickarmj.ReceivePacketListener;

public class MRF24J40Node extends AbstractNodeMain {
	private final Comm boardComm;

	public MRF24J40Node(Comm boardComm) {
		this.boardComm = boardComm;
	}

	@Override
	public GraphName getDefaultNodeName() {
		return GraphName.of("MRF24J40");
	}

	@Override
	public void onStart(ConnectedNode connectedNode) {
		// Received packet publisher
		Publisher<IEEE802154ReceivedPacket> packetPublisher = connectedNode.newPublisher(getDefaultNodeName() + "/received",
				IEEE802154ReceivedPacket._TYPE);

		boardComm.setReceivePacketListener(new ReceivePacketListener() {
			@Override
			public void receivePacket(RXPacket packet) {
				IEEE802154ReceivedPacket packetMsg = packetPublisher.newMessage();
				packetMsg.setRssi(packet.getRSSI());
				// TODO: Represent sender as something smarter
				packetMsg.setSender(String.format("%x", packet.getSrcSAddr()));
				ChannelBuffer packetData = ChannelBuffers.wrappedBuffer(ByteOrder.LITTLE_ENDIAN, packet.getData());
				packetMsg.setData(packetData);
				packetPublisher.publish(packetMsg);
			}
		});
		
/*		// Packet sending server
		ServiceServer<Empty, Empty> server = connectedNode.newServiceServer(getDefaultNodeName(), "/send", new ServiceResponseBuilder<Empty, Empty>() {
			@Override
			public void build(Empty arg0, Empty arg1) throws ServiceException {
				// TODO Auto-generated method stub
			}
		});
	*/	
	}
}
