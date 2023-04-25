package utility;

import entity.Entity;
import main.GamePanel;

public class CollisionChecker {

	GamePanel gp;

	public CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	// We are checking collision for ALL entities(monsters , NPCs ...)
	// , not just player
	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x
				+ entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y
				+ entity.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.TILE_SIZE;
		int entityRightCol = entityRightWorldX / gp.TILE_SIZE;
		int entityTopRow = entityTopWorldY / gp.TILE_SIZE;
		int entityBottomRow = entityBottomWorldY / gp.TILE_SIZE;

		int tileNum1, tileNum2;

		switch (entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.TILE_SIZE;
			tileNum1 = gp.tileM.mapTileNums[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNums[gp.currentMap][entityRightCol][entityTopRow];
			if (gp.tileM.tiles[tileNum1].collision == true
					|| gp.tileM.tiles[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.TILE_SIZE;
			tileNum1 = gp.tileM.mapTileNums[gp.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNums[gp.currentMap][entityRightCol][entityBottomRow];
			if (gp.tileM.tiles[tileNum1].collision == true
					|| gp.tileM.tiles[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.TILE_SIZE;
			tileNum1 = gp.tileM.mapTileNums[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNums[gp.currentMap][entityLeftCol][entityBottomRow];
			if (gp.tileM.tiles[tileNum1].collision == true
					|| gp.tileM.tiles[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.TILE_SIZE;
			tileNum1 = gp.tileM.mapTileNums[gp.currentMap][entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNums[gp.currentMap][entityRightCol][entityBottomRow];
			if (gp.tileM.tiles[tileNum1].collision == true
					|| gp.tileM.tiles[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}

	public int checkObject(Entity entity, boolean isPlayer) {

		int index = 999;

		for (int i = 0; i < gp.objs[1].length; i++) {
			if (gp.objs[gp.currentMap][i] != null) {

				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				// Get object's solid area position
				gp.objs[gp.currentMap][i].solidArea.x = gp.objs[gp.currentMap][i].worldX
						+ gp.objs[gp.currentMap][i].solidArea.x;
				gp.objs[gp.currentMap][i].solidArea.y = gp.objs[gp.currentMap][i].worldY
						+ gp.objs[gp.currentMap][i].solidArea.y;

				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					break;
				}
				if (entity.solidArea
						.intersects(gp.objs[gp.currentMap][i].solidArea)) {
					if (gp.objs[gp.currentMap][i].collision == true) {
						entity.collisionOn = true;
					}
					if (isPlayer == true) {
						index = i;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.objs[gp.currentMap][i].solidArea.x = gp.objs[gp.currentMap][i].solidAreaDefaultX;
				gp.objs[gp.currentMap][i].solidArea.y = gp.objs[gp.currentMap][i].solidAreaDefaultY;
			}
		}

		return index;
	}

	// NPC OR MONSTER COLLISION
	public int checkEntity(Entity entity, Entity[][] targets) {
		int index = 999;

		for (int i = 0; i < targets[1].length; i++) {
			if (targets[gp.currentMap][i] != null) {

				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				// Get object's solid area position
				targets[gp.currentMap][i].solidArea.x = targets[gp.currentMap][i].worldX
						+ targets[gp.currentMap][i].solidArea.x;
				targets[gp.currentMap][i].solidArea.y = targets[gp.currentMap][i].worldY
						+ targets[gp.currentMap][i].solidArea.y;

				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					break;
				}
				if (entity.solidArea
						.intersects(targets[gp.currentMap][i].solidArea)) {
					if (targets[gp.currentMap][i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				targets[gp.currentMap][i].solidArea.x = targets[gp.currentMap][i].solidAreaDefaultX;
				targets[gp.currentMap][i].solidArea.y = targets[gp.currentMap][i].solidAreaDefaultY;
			}
		}

		return index;
	}

	public boolean checkPlayer(Entity entity) {

		boolean isTouchingPl = false;

		// Get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		// Get object's solid area position
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

		switch (entity.direction) {
		case "up":
			entity.solidArea.y -= entity.speed;
			break;
		case "down":
			entity.solidArea.y += entity.speed;
			break;
		case "left":
			entity.solidArea.x -= entity.speed;
			break;
		case "right":
			entity.solidArea.x += entity.speed;
			break;
		}
		if (entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			isTouchingPl = true;
		}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;

		return isTouchingPl;
	}
}
