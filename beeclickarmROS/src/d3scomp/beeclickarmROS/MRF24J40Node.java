package d3scomp.beeclickarmROS;

import java.nio.ByteOrder;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.ros.exception.ServiceException;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.service.ServiceResponseBuilder;
import org.ros.node.topic.Publisher;

import beeclickarm_messages.IEEE802154BroadcastPacket;
import beeclickarm_messages.IEEE802154BroadcastPacketRequest;
import beeclickarm_messages.IEEE802154BroadcastPacketResponse;
import beeclickarm_messages.IEEE802154ReceivedPacket;
import d3scomp.beeclickarmj.Comm;
import d3scomp.beeclickarmj.CommException;
import d3scomp.beeclickarmj.RXPacket;
import d3scomp.beeclickarmj.ReceivePacketListener;
import d3scomp.beeclickarmj.TXPacket;

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
		Publisher<IEEE802154ReceivedPacket> packetPublisher = connectedNode.newPublisher(getDefaultNodeName()
				+ "/received_packets", IEEE802154ReceivedPacket._TYPE);

		boardComm.setReceivePacketListener(new ReceivePacketListener() {
			@Override
			public void receivePacket(RXPacket packet) {
				IEEE802154ReceivedPacket packetMsg = packetPublisher.newMessage();
				packetMsg.setRssi(packet.getRSSI());
				packetMsg.setFcs(packet.getFCS());
				packetMsg.setLqi(packet.getLQI());
				packetMsg.setSrcPanId(packet.getSrcPanId());
				packetMsg.setSrcSAddr(packet.getSrcSAddr());
				ChannelBuffer packetData = ChannelBuffers.wrappedBuffer(ByteOrder.LITTLE_ENDIAN, packet.getData());
				packetMsg.setData(packetData);
				packetPublisher.publish(packetMsg);
			}
		});

		// Packet sending server
		connectedNode.newServiceServer(getDefaultNodeName() + "/broadcast_packet", IEEE802154BroadcastPacket._TYPE,
				new ServiceResponseBuilder<IEEE802154BroadcastPacketRequest, IEEE802154BroadcastPacketResponse>() {
					@Override
					public void build(IEEE802154BroadcastPacketRequest packetData, IEEE802154BroadcastPacketResponse response)
							throws ServiceException {
						TXPacket txPacket = new TXPacket(packetData.getData().array());
						try {
							boardComm.broadcastPacket(txPacket);
							response.setSuccess(true);
						} catch (CommException e) {
							e.printStackTrace();
							response.setSuccess(false);
						}
					}
				});
	}
}
