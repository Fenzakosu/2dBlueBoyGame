package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity {

	GamePanel gp;

	public static final String OBJ_NAME = "Key";

	public OBJ_Key(GamePanel gp) {

		super(gp);
		this.gp = gp;

		type = TYPE_CONSUMABLE;
		name = OBJ_NAME;
		price = 100;
		down1 = setup("/objects/key", gp.TILE_SIZE, gp.TILE_SIZE);
		description = "[" + name + "]\nA rusty key.";
		isStackable = true;
		setDialogue();
	}

	public void setDialogue() {
		dialogues[0][0] = "You use the " + name + " and open the door!";
		dialogues[1][0] = "What are you doing?!?!";
	}

	public boolean use(Entity entity) {

		int objIndex = getDetected(entity, gp.objs, "Door");

		if (objIndex != 999) {
			startDialogue(this, 0);
			gp.playSE(3);
			gp.objs[gp.currentMap][objIndex] = null;
			return true;
		} else {
			startDialogue(this, 1);
			return false;
		}

	}
}
