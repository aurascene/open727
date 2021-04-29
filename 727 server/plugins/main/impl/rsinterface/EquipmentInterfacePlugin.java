package main.impl.rsinterface;

import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.content.SkillCapeCustomizer;
import com.rs.net.decoders.WorldPacketsDecoder;

import main.listener.RSInterface;
import main.wrapper.RSInterfaceSignature;
import player.CombatDefinitions;

@RSInterfaceSignature(interfaceId = {387})
public class EquipmentInterfacePlugin implements RSInterface {

	@Override
	public void execute(Player player, int interfaceId, int componentId, int packetId, int slotId, int slotId2) throws Exception {
		if (player.getInterfaceManager().containsInventoryInter())
			return;
		if (componentId == 6) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
				int hatId = player.getEquipment().getHatId();
				if (hatId == 24437 || hatId == 24439 || hatId == 24440 || hatId == 24441) {
					player.getDialogueManager().startDialogue("FlamingSkull",
							player.getEquipment().getItem(Equipment.SLOT_HAT), -1);
					return;
				}
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
				sendRemove(player, Equipment.SLOT_HAT);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
				player.getEquipment().sendExamine(Equipment.SLOT_HAT);
		} else if (componentId == 9) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON5_PACKET) {
				int capeId = player.getEquipment().getCapeId();
				if (capeId == 20769 || capeId == 20771)
					SkillCapeCustomizer.startCustomizing(player, capeId);
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
				int capeId = player.getEquipment().getCapeId();
				if (capeId == 20767)
					SkillCapeCustomizer.startCustomizing(player, capeId);
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
				sendRemove(player, Equipment.SLOT_CAPE);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
				player.getEquipment().sendExamine(Equipment.SLOT_CAPE);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
				sendRemove(player, Equipment.SLOT_AMULET);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
				player.getEquipment().sendExamine(Equipment.SLOT_AMULET);
		} else if (componentId == 15) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET) {
				int weaponId = player.getEquipment().getWeaponId();
				if (weaponId == 15484)
					player.getInterfaceManager().gazeOrbOfOculus();
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON1_PACKET)
				sendRemove(player, Equipment.SLOT_WEAPON);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
				player.getEquipment().sendExamine(Equipment.SLOT_WEAPON);
		} else if (componentId == 18)
			sendRemove(player, Equipment.SLOT_CHEST);
		else if (componentId == 21)
			sendRemove(player, Equipment.SLOT_SHIELD);
		else if (componentId == 24)
			sendRemove(player, Equipment.SLOT_LEGS);
		else if (componentId == 27)
			sendRemove(player, Equipment.SLOT_HANDS);
		else if (componentId == 30)
			sendRemove(player, Equipment.SLOT_FEET);
		else if (componentId == 33)
			sendRemove(player, Equipment.SLOT_RING);
		else if (componentId == 36)
			sendRemove(player, Equipment.SLOT_ARROWS);
		else if (componentId == 45) {
			if (packetId == WorldPacketsDecoder.ACTION_BUTTON4_PACKET) {
				sendRemove(player, Equipment.SLOT_AURA);
				player.getAuraManager().removeAura();
			} else if (packetId == WorldPacketsDecoder.ACTION_BUTTON8_PACKET)
				player.getEquipment().sendExamine(Equipment.SLOT_AURA);
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON2_PACKET){
				player.getAuraManager().activate();
			}
			else if (packetId == WorldPacketsDecoder.ACTION_BUTTON3_PACKET)
				player.getAuraManager().sendAuraRemainingTime();
		} else if (componentId == 41) {
			player.stopAll();
			player.getInterfaceManager().sendInterface(17);
		} else if (componentId == 38) {
			player.getBank().openEquipmentBonuses(false);
		}
		
		if (componentId == 42) {
			player.getInterfaceManager().sendInterface(1178);
		}
		if (componentId == 43) {
			System.out.println("Customize appearance");
		}
	}

	public static void refreshEquipBonuses(Player player) {
		player.getPackets().sendIComponentText(667, 28, "Stab: +" + player.getCombatDefinitions().getBonuses()[0]);
		player.getPackets().sendIComponentText(667, 29, "Slash: +" + player.getCombatDefinitions().getBonuses()[1]);
		player.getPackets().sendIComponentText(667, 30, "Crush: +" + player.getCombatDefinitions().getBonuses()[2]);
		player.getPackets().sendIComponentText(667, 31, "Magic: +" + player.getCombatDefinitions().getBonuses()[3]);
		player.getPackets().sendIComponentText(667, 32, "Range: +" + player.getCombatDefinitions().getBonuses()[4]);
		player.getPackets().sendIComponentText(667, 33, "Stab: +" + player.getCombatDefinitions().getBonuses()[5]);
		player.getPackets().sendIComponentText(667, 34, "Slash: +" + player.getCombatDefinitions().getBonuses()[6]);
		player.getPackets().sendIComponentText(667, 35, "Crush: +" + player.getCombatDefinitions().getBonuses()[7]);
		player.getPackets().sendIComponentText(667, 36, "Magic: +" + player.getCombatDefinitions().getBonuses()[8]);
		player.getPackets().sendIComponentText(667, 37, "Range: +" + player.getCombatDefinitions().getBonuses()[9]);
		player.getPackets().sendIComponentText(667, 38,
				"Summoning: +" + player.getCombatDefinitions().getBonuses()[10]);
		player.getPackets().sendIComponentText(667, 39, "Absorb Melee: +"
				+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MELEE_BONUS] + "%");
		player.getPackets().sendIComponentText(667, 40, "Absorb Magic: +"
				+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_MAGE_BONUS] + "%");
		player.getPackets().sendIComponentText(667, 41, "Absorb Ranged: +"
				+ player.getCombatDefinitions().getBonuses()[CombatDefinitions.ABSORVE_RANGE_BONUS] + "%");
		player.getPackets().sendIComponentText(667, 42, "Strength: " + player.getCombatDefinitions().getBonuses()[14]);
		player.getPackets().sendIComponentText(667, 43,
				"Ranged Str: " + player.getCombatDefinitions().getBonuses()[15]);
		player.getPackets().sendIComponentText(667, 44, "Prayer: +" + player.getCombatDefinitions().getBonuses()[16]);
		player.getPackets().sendIComponentText(667, 45,
				"Magic Damage: +" + player.getCombatDefinitions().getBonuses()[17] + "%");
	}
	
	public static void sendRemove(Player player, int slotId) {
		if (slotId >= 15)
			return;
		player.stopAll(false, false);
		Item item = player.getEquipment().getItem(slotId);
		if (item == null || !player.getInventory().addItem(item.getId(), item.getAmount()))
			return;
		player.getEquipment().getItems().set(slotId, null);
		player.getEquipment().refresh(slotId);
		player.getAppearance().generateAppearenceData();
//		if (Runecrafting.isTiara(item.getId()))
//			player.getPackets().sendConfig(491, 0);
		if (slotId == 3)
			player.getCombatDefinitions().desecreaseSpecialAttack(0);
	}
}