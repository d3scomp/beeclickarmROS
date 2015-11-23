cmake_minimum_required(VERSION 2.8.3)
project(beeclickarm_messages)

find_package(catkin REQUIRED COMPONENTS
  roscpp
  rospy
  std_msgs
  message_generation)

add_message_files(
  DIRECTORY msg
  FILES
  IEEE802154Packet.msg
  IEEE802154ReceivedPacket.msg)IRLED.srv

add_service_files(
  FILES
  IEEE802154BroadcastPacket.srv
  IRLED.srv)

#add_action_files(
#  FILES
#  GetMap.action)

generate_messages(
  DEPENDENCIES
  std_msgs)

catkin_package(CATKIN_DEPENDS std_msgs)
