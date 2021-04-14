package com.rs.net.decoders.handlers;

import com.rs.Settings;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.event.EventListener.ClickOption;
import com.rs.game.event.EventManager;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.route.strategy.RouteEvent;
import com.rs.io.InputStream;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

import skills.runecrafting.SihponActionNodes;

public final class ObjectHandler {

	public static void handleOption(final Player player, InputStream stream, int option) {
		if (!player.hasStarted() || !player.clientHasLoadedMapRegion() || player.isDead())
			return;
		long currentTime = Utils.currentTimeMillis();
		if (player.getLockDelay() >= currentTime || player.getEmotesManager().getNextEmoteEnd() >= currentTime)
			return;
		
//		boolean forceRun = stream.readUnsignedByte128() == 1;
//		final int id = stream.readIntLE();
//		int x = stream.readUnsignedShortLE();
//		int y = stream.readUnsignedShortLE128();
		int y = stream.readUnsignedShort();
		int x = stream.readUnsignedShort();
		final int id = stream.readInt();
		boolean forceRun = stream.readUnsignedByte128() == 1;
		
		if (Settings.DEBUG)
			System.out.println(x+", "+ y +", "+id +", "+forceRun);
		
		int rotation = 0;
		if (player.isAtDynamicRegion()) {
			rotation = World.getRotation(player.getPlane(), x, y);
			if (rotation == 1) {
				ObjectDefinitions defs = ObjectDefinitions.getObjectDefinitions(id);
				y += defs.getSizeY() - 1;
			} else if (rotation == 2) {
				ObjectDefinitions defs = ObjectDefinitions.getObjectDefinitions(id);
				x += defs.getSizeY() - 1;
			}
		}
		final WorldTile tile = new WorldTile(x, y, player.getPlane());
		final int regionId = tile.getRegionId();
		if (!player.getMapRegionsIds().contains(regionId))
			return;
		WorldObject mapObject = World.getRegion(regionId).getObject(id, tile);
		if (mapObject == null || mapObject.getId() != id)
			return;
		if (player.isAtDynamicRegion() && World.getRotation(player.getPlane(), x, y) != 0) { // temp fix
			ObjectDefinitions defs = ObjectDefinitions.getObjectDefinitions(id);
			if (defs.getSizeX() > 1 || defs.getSizeY() > 1) {
				for (int xs = 0; xs < defs.getSizeX() + 1 && (mapObject == null || mapObject.getId() != id); xs++) {
					for (int ys = 0; ys < defs.getSizeY() + 1 && (mapObject == null || mapObject.getId() != id); ys++) {
						tile.setLocation(x + xs, y + ys, tile.getPlane());
						mapObject = World.getRegion(regionId).getObject(id, tile);
					}
				}
			}
			if (mapObject == null || mapObject.getId() != id)
				return;
		}
		final WorldObject object = !player.isAtDynamicRegion() ? mapObject
				: new WorldObject(id, mapObject.getType(), (mapObject.getRotation() + rotation % 4), x, y,
						player.getPlane());
		player.stopAll(false);
		if (forceRun)
			player.setRun(forceRun);
		switch (option) {
		case 1:
			handleOption1(player, object);
			break;
		case 2:
			handleOption2(player, object);
			break;
		case 3:
			handleOption3(player, object);
			break;
		case 4:
			handleOption4(player, object);
			break;
		case 5:
			handleOption5(player, object);
			break;
		case -1:
			handleOptionExamine(player, object);
			break;
		}
	}

	@SuppressWarnings("unused")
	private static void handleOption1(final Player player, final WorldObject object) {
		final ObjectDefinitions objectDef = object.getDefinitions();
		final int id = object.getId();
		final int x = object.getX();
		final int y = object.getY();
		final WorldTile tile = object;
		if (SihponActionNodes.siphon(player, object))
			return;
		player.setRouteEvent(new RouteEvent(object, new Runnable() {
			@Override
			public void run() {
				player.stopAll();
				player.faceObject(object);
				if (!player.getControlerManager().processObjectClick1(object))
					return;
				if (EventManager.get().handleObjectClick(player, object.getId(), object, tile, ClickOption.FIRST)) {
					return;
				}
				if (Settings.DEBUG)
					Logger.log("ObjectHandler",
							"clicked 1 at object id : " + id + ", " + object.getX() + ", " + object.getY() + ", "
									+ object.getPlane() + ", " + object.getType() + ", " + object.getRotation() + ", "
									+ object.getDefinitions().name);
			}
		}, false));//objectDef.getSizeX(), Wilderness.isDitch(id) ? 4 : objectDef.getSizeY(), object.getRotation()));
	}

	private static void handleOption2(final Player player, final WorldObject object) {
		@SuppressWarnings("unused")
		final ObjectDefinitions objectDef = object.getDefinitions();
		final int id = object.getId();
		final WorldTile tile = object;
		player.setRouteEvent(new RouteEvent(object, new Runnable() {
			@Override
			public void run() {
				player.stopAll();
				player.faceObject(object);
				if (!player.getControlerManager().processObjectClick2(object))
					return;
				if (EventManager.get().handleObjectClick(player, object.getId(), object, tile, ClickOption.SECOND)) {
					return;
				}
				if (Settings.DEBUG)
					Logger.log("ObjectHandler", "clicked 2 at object id : " + id + ", " + object.getX() + ", "
							+ object.getY() + ", " + object.getPlane());
			}
		}, false));//objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
	}

	@SuppressWarnings("unused")
	private static void handleOption3(final Player player, final WorldObject object) {
		final ObjectDefinitions objectDef = object.getDefinitions();
		final int id = object.getId();
		final WorldTile tile = object;
		player.setRouteEvent(new RouteEvent(object, new Runnable() {
			@Override
			public void run() {
				player.stopAll();
				player.faceObject(object);
				if (!player.getControlerManager().processObjectClick3(object))
					return;
				if (EventManager.get().handleObjectClick(player, object.getId(), object, tile, ClickOption.THIRD)) {
					return;
				}
				if (Settings.DEBUG)
					Logger.log("ObjectHandler", "cliked 3 at object id : " + id + ", " + object.getX() + ", "
							+ object.getY() + ", " + object.getPlane() + ", ");
			}
		}, false));//objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
	}

	@SuppressWarnings("unused")
	private static void handleOption4(final Player player, final WorldObject object) {
		final ObjectDefinitions objectDef = object.getDefinitions();
		final int id = object.getId();
		final WorldTile tile = object;
		player.setRouteEvent(new RouteEvent(object, new Runnable() {
			@Override
			public void run() {
				player.stopAll();
				player.faceObject(object);

				if (EventManager.get().handleObjectClick(player, object.getId(), object, tile, ClickOption.FOURTH)) {
					return;
				}
				
				if (Settings.DEBUG)
					Logger.log("ObjectHandler", "cliked 4 at object id : " + id + ", " + object.getX() + ", "
							+ object.getY() + ", " + object.getPlane() + ", ");
			}
		}, false));//objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
	}

	@SuppressWarnings("unused")
	private static void handleOption5(final Player player, final WorldObject object) {
		final ObjectDefinitions objectDef = object.getDefinitions();
		final int id = object.getId();
		final WorldTile tile = object;
		player.setRouteEvent(new RouteEvent(object, new Runnable() {
			@Override
			public void run() {
				player.stopAll();
				player.faceObject(object);

				if (EventManager.get().handleObjectClick(player, object.getId(), object, tile, ClickOption.FIFTH)) {
					return;
				}
				
				if (Settings.DEBUG)
					Logger.log("ObjectHandler", "cliked 5 at object id : " + id + ", " + object.getX() + ", "
							+ object.getY() + ", " + object.getPlane() + ", ");
			}
		}, false));//objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
	}

	private static void handleOptionExamine(final Player player, final WorldObject object) {
		if (Settings.DEBUG) {
			int offsetX = object.getX() - player.getX();
			int offsetY = object.getY() - player.getY();
			System.out.println("Offsets" + offsetX + " , " + offsetY);
		}
		player.getPackets().sendGameMessage("It's an " + object.getDefinitions().name + ".");
		if (Settings.DEBUG)
			if (Settings.DEBUG)

				Logger.log("ObjectHandler",
						"examined object id : " + object.getId() + ", " + object.getX() + ", " + object.getY() + ", "
								+ object.getPlane() + ", " + object.getType() + ", " + object.getRotation() + ", "
								+ object.getDefinitions().name);
	}
	
	@SuppressWarnings("unused")
	public static void handleItemOnObject(final Player player, final WorldObject object, final int interfaceId,
			final Item item) {
		final int itemId = item.getId();
		final ObjectDefinitions objectDef = object.getDefinitions();
		player.setRouteEvent(new RouteEvent(object, new Runnable() {
			@Override
			public void run() {
				player.faceObject(object);
				
					if (Settings.DEBUG)
						System.out.println("Item on object: " + object.getId());
				}
			
		}, false));//objectDef.getSizeX(), objectDef.getSizeY(), object.getRotation()));
	}
}
