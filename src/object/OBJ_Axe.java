package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		
		type = TYPE_AXE;
		name = "Woodcutter's Axe";
		down1 = setup("/objects/axe", gp.TILE_SIZE, gp.TILE_SIZE);
		attackValue = 2;
		price = 75;
		knockbackPower = 10;
		attackArea.width = 30;
		attackArea.height = 30;
		description="[" + name + "]" + "\nA simple axe, \nused most commonly \nfor chopping wood.";
		motion1Duration = 20;
		motion2Duration = 40;
	}

}
