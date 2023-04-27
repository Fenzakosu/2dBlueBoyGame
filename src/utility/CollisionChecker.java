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

	// PROJECTILES COLLISION
	public void checkEntity(Entity[][] targets1, Entity[][] targets2) {

		Entity target1 = null;
		Entity target2 = null;

		for (int i = 0; i < targets1[1].length; i++) {

			if (targets1[gp.currentMap][i] != null) {

				target1 = targets1[gp.currentMap][i];

				// Get entity's solid area position
				target1.solidArea.x = target1.worldX + target1.solidArea.x;
				target1.solidArea.y = target1.worldY + target1.solidArea.y;
				// Get object's solid area position
				target1.solidArea.x = target1.worldX + target1.solidArea.x;
				target1.solidArea.y = target1.worldY + target1.solidArea.y;

				switch (target1.direction) {
				case "up":
					target1.solidArea.y -= target1.speed;
					break;
				case "down":
					target1.solidArea.y += target1.speed;
					break;
				case "left":
					target1.solidArea.x -= target1.speed;
					break;
				case "right":
					target1.solidArea.x += target1.speed;
					break;
				}

				for (int j = 0; j < targets2[1].length; j++) {
					if (targets2[gp.currentMap][j] != null) {
						target2 = targets2[gp.currentMap][j];
						// Get entity's solid area position
						target2.solidArea.x = target2.worldX + target2.solidArea.x;
						target2.solidArea.y = target2.worldY + target2.solidArea.y;
						// Get object's solid area position
						target2.solidArea.x = target2.worldX + target2.solidArea.x;
						target2.solidArea.y = target2.worldY + target2.solidArea.y;

						switch (target2.direction) {
						case "up":
							target2.solidArea.y -= target1.speed;
							break;
						case "down":
							target2.solidArea.y += target1.speed;
							break;
						case "left":
							target2.solidArea.x -= target1.speed;
							break;
						case "right":
							target2.solidArea.x += target1.speed;
							break;
						}

						if (target1.solidArea.intersects(target2.solidArea)) {
							if (target2 != target1) {
								target1.isAlive = false;
								target1.generateParticle(target1, target2);
								target2.isAlive = false;
								target2.generateParticle(target2, target1);
							}
						} 
						target1.solidArea.x = target1.solidAreaDefaultX;
						target1.solidArea.y = target1.solidAreaDefaultY;
						target2.solidArea.x = target2.solidAreaDefaultX;
						target2.solidArea.y = target2.solidAreaDefaultY;
					}
				}
			}
		}

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
