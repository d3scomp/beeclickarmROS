package beeclickarm_messages;

public interface IEEE802154Packet extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "beeclickarm_messages/IEEE802154Packet";
  static final java.lang.String _DEFINITION = "uint8[] data";
  org.jboss.netty.buffer.ChannelBuffer getData();
  void setData(org.jboss.netty.buffer.ChannelBuffer value);
}
