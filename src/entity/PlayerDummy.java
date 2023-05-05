package entity;

import main.GamePanel;

public class PlayerDummy extends Entity {

	public static final String NPC_NAME = "Dummy";

	public PlayerDummy(GamePanel gp) {
		super(gp);

		name = NPC_NAME;
		getImages();
	}

	public void getImages() {

		up1 = setup("/player/boy_up_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/player/boy_up_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/player/boy_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/player/boy_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/player/boy_left_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/player/boy_left_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/player/boy_right_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/player/boy_right_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}
}
