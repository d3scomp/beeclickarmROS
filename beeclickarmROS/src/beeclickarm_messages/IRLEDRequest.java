package beeclickarm_messages;

public interface IRLEDRequest extends org.ros.internal.message.Message {
  static final java.lang.String _TYPE = "beeclickarm_messages/IRLEDRequest";
  static final java.lang.String _DEFINITION = "bool powered\n";
  boolean getPowered();
  void setPowered(boolean value);
}
