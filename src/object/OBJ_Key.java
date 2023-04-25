package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Key extends Entity{
	
	
	public OBJ_Key(GamePanel gp) {
		
		super(gp);
		
		name = "Key";
		price = 100;
		down1 = setup("/objects/key", gp.TILE_SIZE, gp.TILE_SIZE);
		description = "[" + name + "]\nA rusty key."; 
	}
	
}
