package main;

import data.Progress;
import entity.Entity;
import rectangle.EventRect;

public class EventHandler {

	GamePanel gp;
	EventRect eventRect[][][];
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	Entity eventMaster;

	public EventHandler(GamePanel gp) {
		this.gp = gp;

		eventMaster = new Entity(gp);

		eventRect = new EventRect[gp.MAX_MAP][gp.maxWorldCol][gp.maxWorldRow];

		int map = 0;
		int col = 0;
		int row = 0;

		while (map < gp.MAX_MAP && col < gp.maxWorldCol && row < gp.maxWorldRow) {

			eventRect[map][col][row] = new EventRect();

			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].width = 2;
			eventRect[map][col][row].height = 2;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;

				if (row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
			}
		}
		setDialogue();
	}

	public void setDialogue() {
		eventMaster.dialogues[0][0] = "You fall into a pit!";
		eventMaster.dialogues[1][0] = "You drink water from the pool.\nYour vitality and mana energy \nhave been restored!"
				+ "\n(The progress has been saved.)";
		eventMaster.dialogues[1][1] = "Damn , this water tastes good!";
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
			// TRANSITION TO INTERIOR MAP (MERCHANT'S HOUSE)
			else if (hit(0, 10, 39, "any") == true) {
				teleport(1, 12, 13, gp.AREA_INDOOR);
			}
			// TRANSITION FROM INTERIOR MAP (FROM MERCHANT'S HOUSE TO THE OUTSIDE)
			else if (hit(1, 12, 13, "any") == true) {
				teleport(0, 10, 39, gp.AREA_OUTSIDE);
			}
			// TALK TO A MERCHANT
			else if (hit(1, 12, 9, "up") == true) {
				speak(gp.npcs[1][0]);
			}
			// TRANSITION TO DUNGEON01
			else if (hit(0, 12, 9, "any") == true) {
				teleport(2, 9, 41, gp.AREA_DUNGEON);
			}
			// TRANSITION FROM DUNGEON01 TO EXTERIOR MAP
			else if (hit(2, 9, 41, "any") == true) {
				teleport(0, 12, 9, gp.AREA_OUTSIDE);
			}
			// TRANSITION TO DUNGEON02 FROM DUNGEON01
			else if (hit(2, 8, 7, "any") == true) {
				teleport(3, 26, 41, gp.AREA_DUNGEON);
			}
			// TRANSITION TO DUNGEON01 FROM DUNGEON02
			else if (hit(3, 26, 41, "any") == true) {
				teleport(2, 8, 7, gp.AREA_DUNGEON);
			}
			// SKELETON LORD CUTSCENE (BOSS ENCOUNTER)
			else if (hit(3, 25, 27, "any") == true) {
				skeletonLord();
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
		eventMaster.startDialogue(eventMaster, 0);
		gp.player.life -= 1;
//		eventRect[col][row].eventDone = true;
		canTouchEvent = false;
	}

	public void invokeHealingPool(int gameState) {
		if (gp.keyH.enterPressed == true) {
			gp.gameState = gameState;
			gp.player.attackIsCanceled = true;
			gp.playSE(2);
			eventMaster.startDialogue(eventMaster, 1);
			gp.player.life = gp.player.maxLife;
			gp.player.mana = gp.player.maxMana;

			// MONSTERS RESPAWN AFTER DRINKING WATER FROM HEALING POOL
			gp.aSetter.setMonsters();

			// SAVE GAME
			gp.saveLoad.save();
		}
	}

	public void teleport(int map, int col, int row, int area) {

		gp.gameState = gp.transitionState;
		gp.nextArea = area;
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

	// SKELETON LORD CUTSCENE
	public void skeletonLord() {
		if (gp.bossBattleOn == false && Progress.skeletonLordDefeated == false) {
			gp.gameState = gp.cutsceneState;
			gp.csManager.sceneNum = gp.csManager.SKELETON_LORD;
		}
	}

}
