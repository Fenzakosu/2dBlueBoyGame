package main;

import entity.Entity;
import rectangle.EventRect;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][][];
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventRect = new EventRect[gp.MAX_MAP][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

		int map = 0;
		int col = 0;
		int row = 0;

		while (map < gp.MAX_MAP && col < gp.MAX_WORLD_COL
				&& row < gp.MAX_WORLD_ROW) {

			eventRect[map][col][row] = new EventRect();

			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

			col++;
			if (col == gp.MAX_WORLD_COL) {
				col = 0;
				row++;

				if (row == gp.MAX_WORLD_ROW) {
					row = 0;
					map++;
				}
			}
		}

	}

	public void checkEvent() {

		// CHECK IF THE PLAYER IS MORE THAN 1 TILE AWAY FROM LAST EVENT LOCATION
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);

		if (distance > gp.TILE_SIZE) {
			canTouchEvent = true;
		}

		if (canTouchEvent == true) {
			// DAMAGE PIT EVENT
			if (hit(0, 27, 16, "right") == true) {
				invokeDamagePit(gp.dialogueState);
			}
			// HEALING POOL EVENT
			else if (hit(0, 23, 12, "up") == true) {
				invokeHealingPool(gp.dialogueState);
			}
			// TRANSITION TO INTERIOR MAP (MAP 1)
			else if (hit(0, 10, 39, "any") == true) {
				teleport(1, 12, 13);
			}
			// TRANSITION FROM INTERIOR MAP (MAP 1 TO MAP 0)
			else if (hit(1, 12, 13, "any") == true) {
				teleport(0, 10, 39);
			}
			// TALK TO A MERCHANT
			else if (hit(1, 12, 9, "up") == true) {
				speak(gp.npcs[1][0]);
			}
		}

	}

	public boolean hit(int map, int col, int row, String reqDirection) {

		boolean hit = false;

		if (map == gp.currentMap) {

			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			eventRect[map][col][row].x = col * gp.TILE_SIZE
					+ eventRect[map][col][row].x;
			eventRect[map][col][row].y = row * gp.TILE_SIZE
					+ eventRect[map][col][row].y;

			if (gp.player.solidArea.intersects(eventRect[map][col][row])
					&& eventRect[map][col][row].eventDone == false) {
				if (gp.player.direction.contentEquals(reqDirection)
						|| reqDirection.contentEquals("any")) {
					hit = true;

					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}

			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}

		return hit;
	}

	public void invokeDamagePit(int gameState) {
		gp.gameState = gameState;
		gp.playSE(6);
		gp.ui.currentDialogue = "You fall into a pit!";
		gp.player.life -= 1;
//		eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}

	public void invokeHealingPool(int gameState) {
		if (gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.player.attackIsCanceled = true;
			gp.playSE(2);
			gp.ui.currentDialogue = "You drink water from the pool.\nYour vitality and mana energy \nhave been restored!";
			gp.player.life = gp.player.maxLife;
			gp.player.mana = gp.player.maxMana;

			// MONSTERS RESPAWN AFTER DRINKING WATER FROM HEALING POOL
			gp.aSetter.setMonsters();
		}
	}

	public void teleport(int map, int col, int row) {

		gp.gameState = gp.transitionState;

		tempMap = map;
		tempCol = col;
		tempRow = row;

		canTouchEvent = false;
		gp.playSE(13);
	}

	public void speak(Entity entity) {
		if (gp.keyH.enterPressed == true) {
			gp.gameState = gp.dialogueState;
			gp.player.attackIsCanceled = true;
			entity.speak();
		}
	}

}
