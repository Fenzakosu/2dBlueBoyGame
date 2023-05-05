package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Pickaxe extends Entity {

	public static final String OBJ_NAME = "Pickaxe";

	public OBJ_Pickaxe(GamePanel gp) {
		super(gp);

		type = TYPE_PICKAXE;
		name = OBJ_NAME;
		down1 = setup("/objects/pickaxe", gp.TILE_SIZE, gp.TILE_SIZE);
		attackValue = 2;
		price = 75;
		knockbackPower = 10;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[" + name + "]" + "\nYou will dig it!";
		motion1Duration = 10;
		motion2Duration = 20;
	}

}
