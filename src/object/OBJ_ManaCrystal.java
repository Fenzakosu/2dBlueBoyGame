package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity {

	GamePanel gp;
	
	public static final String OBJ_NAME = "Mana Crystal";

	public OBJ_ManaCrystal(GamePanel gp) {
		super(gp);

		this.gp = gp;
		type = TYPE_PICKUP_ONLY;
		name = OBJ_NAME;
		value = 1;
		down1 = setup("/objects/manacrystal_full", gp.TILE_SIZE, gp.TILE_SIZE);
		image1 = setup("/objects/manacrystal_full", gp.TILE_SIZE, gp.TILE_SIZE);
		image2 = setup("/objects/manacrystal_blank", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	public boolean use(Entity entity) {
		gp.playSE(2);
		gp.ui.addMessage("Mana + " + value);
		entity.mana += value;
		return true;
	}
}
