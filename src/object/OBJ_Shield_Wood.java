package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity {

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);

		type = TYPE_SHIELD;
		name = "Wood Shield";
		price = 50;
		down1 = setup("/objects/shield_wood", gp.TILE_SIZE, gp.TILE_SIZE);
		defenseValue = 1;
		description = "[" + name + "]\nA wodden, worm-ridden \nshield."; 

	}

}
