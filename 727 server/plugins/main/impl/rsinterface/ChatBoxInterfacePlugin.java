package main.impl.rsinterface;

import com.rs.game.player.Player;
import com.rs.net.decoders.WorldPacketsDecoder;

import main.listener.RSInterface;
import main.wrapper.RSInterfaceSignature;

@RSInterfaceSignature(interfaceId = {751})
public class ChatBoxInterfacePlugin implements RSInterface {

	@Override
	public void execute(Player player, int interfaceId, int componentId, int packetId, int slotId, int slotId2) throws Exception {
		if (componentId == 14 && packetId == 96) {
			//report function 
			//TODO: SQL reports
		}
		if (componentId == 26) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
				player.getFriendsIgnores().setPrivateStatus(0);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
				player.getFriendsIgnores().setPrivateStatus(1);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
				player.getFriendsIgnores().setPrivateStatus(2);
		} else if (componentId == 32) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
				player.setFilterGame(false);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
				player.setFilterGame(true);
		} else if (componentId == 29) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
				player.setPublicStatus(0);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
				player.setPublicStatus(1);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
				player.setPublicStatus(2);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET)
				player.setPublicStatus(3);
		} else if (componentId == 0) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
				player.getFriendsIgnores().setFriendsChatStatus(0);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
				player.getFriendsIgnores().setFriendsChatStatus(1);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
				player.getFriendsIgnores().setFriendsChatStatus(2);
		} else if (componentId == 23) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
				player.setClanStatus(0);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
				player.setClanStatus(1);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
				player.setClanStatus(2);
		} else if (componentId == 20) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
				player.setTradeStatus(0);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
				player.setTradeStatus(1);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
				player.setTradeStatus(2);
		} 
//		else if (componentId == 17) {
//			if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET)
//				player.setAssistStatus(0);
//			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
//				player.setAssistStatus(1);
//			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET)
//				player.setAssistStatus(2);
//			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON9_PACKET) {
//				// ASSIST XP Earned/Time
//			}
//		}
	}
}