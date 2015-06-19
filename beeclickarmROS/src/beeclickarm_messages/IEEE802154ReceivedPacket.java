package beeclickarm_messages;

public interface IEEE802154ReceivedPacket extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "beeclickarm_messages/IEEE802154ReceivedPacket";
  static final java.lang.String _DEFINITION = "uint32 rssi\nuint32 lqi\nuint32 fcs\nuint32 srcPanId\nuint32 srcSAddr\nuint8[] data";
  int getRssi();
  void setRssi(int value);
  int getLqi();
  void setLqi(int value);
  int getFcs();
  void setFcs(int value);
  int getSrcPanId();
  void setSrcPanId(int value);
  int getSrcSAddr();
  void setSrcSAddr(int value);
  org.jboss.netty.buffer.ChannelBuffer getData();
  void setData(org.jboss.netty.buffer.ChannelBuffer value);
}
