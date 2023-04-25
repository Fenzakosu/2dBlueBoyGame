package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity {
	public OBJ_Shield_Blue(GamePanel gp) {
		super(gp);

		type = TYPE_SHIELD;
		name = "Blue Shield";
		down1 = setup("/objects/shield_blue", gp.TILE_SIZE, gp.TILE_SIZE);
		defenseValue = 2;
		price = 100;
		description = "[" + name + "]\nA rather ordinary shield, \nused by brigands";

	}
}
