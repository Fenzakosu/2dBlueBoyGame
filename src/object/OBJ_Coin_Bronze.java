package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity{

	GamePanel gp;
	
	public static final String OBJ_NAME = "Bronze Coin";
	
	public OBJ_Coin_Bronze(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		type = TYPE_PICKUP_ONLY;
		name = OBJ_NAME;
		value = 1;
		down1 = setup("/objects/coin_bronze", gp.TILE_SIZE, gp.TILE_SIZE);
	}
	
	public boolean use(Entity entity) {
		gp.playSE(1);
		gp.ui.addMessage("You picked up " + value + " coin." );
		gp.player.coin += value;
		return true;
	}

}
