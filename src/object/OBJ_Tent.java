package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent extends Entity {

	GamePanel gp;

	public OBJ_Tent(GamePanel gp) {
		super(gp);
		this.gp = gp;

		type = TYPE_CONSUMABLE;
		name = "Tent";
		down1 = setup("/objects/tent", gp.TILE_SIZE, gp.TILE_SIZE);
		description = "[Tent]\nYou can sleep \nuntil next \nmorning";
		price = 250;
		isStackable = true;
	}

	public boolean use(Entity entity) {
		gp.player.currentLight = null;
		gp.gameState = gp.sleepState;
		gp.playSE(14);
		gp.player.life = gp.player.maxLife;
		gp.player.mana = gp.player.maxMana;
		gp.player.getSleepingImages(down1);
		return true;
	}

}
