package entity;

import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity {

	public NPC_OldMan(GamePanel gp) {
		super(gp);

		direction = "down";
		speed = 2;

		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImages();
		setDialogue();
	}

	public void getImages() {

		up1 = setup("/npc/oldman_up_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/npc/oldman_up_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/npc/oldman_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/npc/oldman_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/npc/oldman_left_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/npc/oldman_left_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/npc/oldman_right_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/npc/oldman_right_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	public void setDialogue() {
		dialogues[0] = "Greetings to you, lad.";
		dialogues[1] = "So, you've come to this island hoping \nto find some fine treasure?";
		dialogues[2] = "I used to be an accomplished mage,\nbut now ... I'm a bit too old for taking \non that kind of adventure.";
		dialogues[3] = "Well, good luck on your quest! I think \nyou're gonna need it...";
	}

	public void setAction() {

		if (onPath == true) {
			// GO TO A POINT
//			int goalCol = 12;
//			int goalRow = 9;
			// FOLLOW PLAYER
			int goalCol = (gp.player.worldX + gp.player.solidArea.x)/ gp.TILE_SIZE;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y)/ gp.TILE_SIZE;;
			
			searchPath(goalCol, goalRow);

		} else {

			actionLockCounter++;

			if (actionLockCounter == 120) {

				Random random = new Random();
				int i = random.nextInt(100) + 1; // pick a number from 1 to 100

				if (i <= 25) {
					direction = "up";
				}

				if (i > 25 && i <= 50) {
					direction = "down";
				}
				if (i > 50 && i <= 75) {
					direction = "left";
				}
				if (i > 75 && i <= 100) {
					direction = "right";
				}
				actionLockCounter = 0;
			}
		}

	}

	public void speak() {
		super.speak();

		onPath = true;
	}
}
