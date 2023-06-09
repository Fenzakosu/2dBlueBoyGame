package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Lantern extends Entity {

	public static final String OBJ_NAME = "Lantern";

	public OBJ_Lantern(GamePanel gp) {
		super(gp);

		type = TYPE_LIGHT;
		name = OBJ_NAME;
		down1 = setup("/objects/lantern", gp.TILE_SIZE, gp.TILE_SIZE);
		description = "[Lantern] \nIlluminates \nyour surroundings.";
		price = 100;
		lightRadius = 350;
	}

}
