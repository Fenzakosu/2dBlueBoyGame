package tile_interactive;

import main.GamePanel;

public class IT_MetalPlate extends InteractiveTile {

	GamePanel gp;
	public static final String IT_NAME = "Metal Plate";

	public IT_MetalPlate(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;

		this.worldX = gp.TILE_SIZE * col;
		this.worldY = gp.TILE_SIZE * row;

		name = IT_NAME;
		down1 = setup("/tiles_interactive/metalplate", gp.TILE_SIZE, gp.TILE_SIZE);

		// NO COLLISION
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 0;
		solidArea.height = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}

}
