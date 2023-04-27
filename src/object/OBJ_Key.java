package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

	GamePanel gp;

	public OBJ_Key(GamePanel gp) {

		super(gp);
		this.gp = gp;

		type = TYPE_CONSUMABLE;
		name = "Key";
		price = 100;
		down1 = setup("/objects/key", gp.TILE_SIZE, gp.TILE_SIZE);
		description = "[" + name + "]\nA rusty key.";
		isStackable = true;
	}

	public boolean use(Entity entity) {
		gp.gameState = gp.dialogueState;

		int objIndex = getDetected(entity, gp.objs, "Door");

		if (objIndex != 999) {
			gp.ui.currentDialogue = "You use the " + name + " and open the door!";
			gp.playSE(3);
			gp.objs[gp.currentMap][objIndex] = null;
			return true;
		} else {
			gp.ui.currentDialogue = "What are you doing?!?!";
			return false;
		}

	}
}
