package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity {

	GamePanel gp;

	public static final String OBJ_NAME = "Door";
	
	public OBJ_Door(GamePanel gp) {

		super(gp);
		this.gp = gp;

		type = TYPE_OBSTACLE;
		name = OBJ_NAME;
		down1 = setup("/objects/door", gp.TILE_SIZE, gp.TILE_SIZE);
		collision = true;
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		setDialogue();
	}

	public void setDialogue() {
		dialogues[0][0] = "You need a key to open the door.";
	}

	public void interact() {

		startDialogue(this, 0);
	}
}
