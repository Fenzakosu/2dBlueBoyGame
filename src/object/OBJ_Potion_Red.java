package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity {

	GamePanel gp;


	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);

		this.gp = gp;

		type = TYPE_CONSUMABLE;
		name = "Red Potion";
		value = 5;
		price = 150;
		down1 = setup("/objects/potion_red", gp.TILE_SIZE, gp.TILE_SIZE);
		description = "[" + name + "]" + "\nRestores your health by " + value + ".";
	}

	public void use(Entity entity) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drink the " + name + "!\n"
				+ "Your life has been restored by " + value + " \nhit points!";
		entity.life += value;
		if (gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		gp.playSE(2);
	}
}
