package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity {

	GamePanel gp;

	public static final String OBJ_NAME = "Chest";

	public OBJ_Chest(GamePanel gp) {

		super(gp);
		this.gp = gp;

		type = TYPE_OBSTACLE;
		name = OBJ_NAME;
		image1 = setup("/objects/chest", gp.TILE_SIZE, gp.TILE_SIZE);
		image2 = setup("/objects/chest_opened", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = image1;
		collision = true;

		solidArea.x = 4;
		solidArea.y = 16;
		solidArea.width = 40;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}

	public void setDialogue() {
		dialogues[0][0] = "You open a chest and find " + loot.name + "!"
				+ "\n...But you cannot carry any more!";
		dialogues[1][0] = "You open a chest and find " + loot.name + "!"
				+ "\nYou obtain the " + loot.name + "!";
		dialogues[2][0] = "Chest is empty!";
	}

	public void setLoot(Entity loot) {
		this.loot = loot;
		// WE NEED LOOT INFO BEFORE WE INVOKE SET DIALOGUE METHOD
		setDialogue();
	}

	public void interact() {
		// IF CHEST IS NOT OPENED ALREADY , YOU CAN GET ITEM(S) INSIDE IT
		if (isOpen == false) {
			gp.playSE(3);
			// IF YOUR INVENTORY IS FULL , YOU CANNOT TAKE THE ITEM(S)
			if (gp.player.canObtainItem(loot) == false) {
				startDialogue(this, 0);
			} else {
				// YOU TAKE ITEM(S) FROM THE CHEST
				startDialogue(this, 1);
				down1 = image2;
				isOpen = true;
			}
		} else {
			// CHEST HAS BEEN OPENED ALREADY, HENCE , IT IS EMPTY
			startDialogue(this, 2);
		}
	}
}
