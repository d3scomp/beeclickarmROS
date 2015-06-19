package beeclickarm_messages;

public interface IEEE802154ReceivedPacket extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "beeclickarm_messages/IEEE802154ReceivedPacket";
  static final java.lang.String _DEFINITION = "float64 rssi\nstring sender\nuint8[] data";
  double getRssi();
  void setRssi(double value);
  java.lang.String getSender();
  void setSender(java.lang.String value);
  org.jboss.netty.buffer.ChannelBuffer getData();
  void setData(org.jboss.netty.buffer.ChannelBuffer value);
}
