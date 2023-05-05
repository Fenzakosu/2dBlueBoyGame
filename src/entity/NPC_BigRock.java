package entity;

import java.util.ArrayList;

import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

public class NPC_BigRock extends Entity {

	public static final String NPC_NAME = "Big Rock";

	public NPC_BigRock(GamePanel gp) {
		super(gp);

		name = NPC_NAME;
		direction = "down";
		speed = 4;

		solidArea.x = 2;
		solidArea.y = 6;
		solidArea.width = 44;
		solidArea.height = 40;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		dialogueSet = -1;

		getImages();
		setDialogue();
	}

	public void getImages() {

		up1 = setup("/npc/bigrock", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/npc/bigrock", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/npc/bigrock", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/npc/bigrock", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/npc/bigrock", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/npc/bigrock", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/npc/bigrock", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/npc/bigrock", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	public void setDialogue() {
		dialogues[0][0] = "It's a giant rock. Duuuuh!";

	}

	// OVERRIDE UPDATE , SO THE BIG ROCK WON'T MOVE FROM IT'S
	// INTENDED POSITION
	public void update() {

	}

	public void setAction() {

	}

	public void speak() {

		facePlayer();
		startDialogue(this, dialogueSet);

		dialogueSet++;

		if (dialogues[dialogueSet][0] == null) {
			dialogueSet--;
		}

//		if (gp.player.life < gp.player.maxLife / 3) {
//			dialogueSet = 1;
//		}

//		onPath = true;
	}

	public void move(String d) {

		// ASSIGN PARAMETER VALUE TO BIG ROCK'S DIRECTION
		this.direction = d;

		// CHECK COLLISION
		checkCollision();

		if (collisionOn == false) {
			switch (direction) {
			case "up":
				worldY -= speed;
				break;
			case "down":
				worldY += speed;
				break;
			case "left":
				worldX -= speed;
				break;
			case "right":
				worldX += speed;
				break;
			}
		}
		detectPlate();
	}

	public void detectPlate() {

		// WE PUT ALL METAL PLATES AND BIG ROCKS IN ARRAYLISTS
		ArrayList<InteractiveTile> plateList = new ArrayList<>();
		ArrayList<Entity> rockList = new ArrayList<>();

		// CREATE A METAL PLATE LIST
		for (int i = 0; i < gp.iTiles[1].length; i++) {
			if (gp.iTiles[gp.currentMap][i] != null
					&& gp.iTiles[gp.currentMap][i].name
							.equals(IT_MetalPlate.IT_NAME)) {
				plateList.add(gp.iTiles[gp.currentMap][i]);
			}
		}
		// CREATE A BIG ROCK LIST
		for (int i = 0; i < gp.npcs[1].length; i++) {
			if (gp.npcs[gp.currentMap][i] != null
					&& gp.npcs[gp.currentMap][i].name.equals(NPC_BigRock.NPC_NAME)) {
				rockList.add(gp.npcs[gp.currentMap][i]);
			}
		}

		int count = 0;

		// SCAN THE PLATE LIST
		for (int i = 0; i < plateList.size(); i++) {
			int xDistance = Math.abs(worldX - plateList.get(i).worldX);
			int yDistance = Math.abs(worldY - plateList.get(i).worldY);
			int distance = Math.max(xDistance, yDistance);
			// IF DISTANCE IS WITHIN 8 PIXELS, THEN WE CONSIDER
			// THAT THIS ROCK IS ON A METAL PLATE
			if (distance < 8) {
				// WE PUT THIS IF STATEMENT , OTHERWISE UNLOCK SOUND
				// WOULD PLAY IN EVERY LOOP
				if (linkedEntity == null) {
					linkedEntity = plateList.get(i);
					gp.playSE(3);
				}
			} else {
				// IF THIS ROCK WAS LINKED TO A METAL PLATE
				// AND PUSH IT AGAIN AFTERWARDS (AWAY FROM THE PLATE),
				// THEN THIS ROCK LOSES THE LINK
				if (linkedEntity == plateList.get(i)) {
					linkedEntity = null;
				}
			}
		}

		// SCAN THE ROCK LIST
		for (int i = 0; i < rockList.size(); i++) {
			// COUNT THE ROCK ON THE PLATE
			if (rockList.get(i).linkedEntity != null) {
				count++;
			}
		}
		// IF ALL THE ROCKS ARE ON THE PLATES, THE IRON DOOR OPENS
		if (count == rockList.size()) {
			for (int i = 0; i < gp.objs[1].length; i++) {
				// SCAN THE OBJECT LIST FOR IRON DOOR
				if (gp.objs[gp.currentMap][i] != null
						&& gp.objs[gp.currentMap][i].name
								.equals(OBJ_Door_Iron.OBJ_NAME)) {
					// IRON DOORS ARE OPENED (THEY DISSAPEAR)
					gp.objs[gp.currentMap][i] = null;
					gp.playSE(21);
				}
			}
		}

	}
}
