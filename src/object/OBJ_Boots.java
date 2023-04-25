package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity {

	public OBJ_Boots(GamePanel gp) {
		super(gp);

		name = "Boots";
		down1 = setup("/objects/boots", gp.TILE_SIZE, gp.TILE_SIZE);

	}
}
