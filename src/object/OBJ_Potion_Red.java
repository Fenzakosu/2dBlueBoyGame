package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {

	GamePanel gp;

	public static final String OBJ_NAME = "Red Potion";
	
	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);

		this.gp = gp;

		type = TYPE_CONSUMABLE;
		name = OBJ_NAME;
		value = 5;
		price = 150;
		down1 = setup("/objects/potion_red", gp.TILE_SIZE, gp.TILE_SIZE);
		description = "[" + name + "]" + "\nRestores your health by " + value + ".";
		isStackable = true;
		setDialogue();
	}
	
	public void setDialogue() {
		dialogues[0][0] = "You drink the " + name + "!\n"
				+ "Your life has been restored by " + value + " \nhit points!";
	}

	public boolean use(Entity entity) {
		startDialogue(this, 0);
		entity.life += value;
		if (gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		gp.playSE(2);
		
		return true;
	}
}
