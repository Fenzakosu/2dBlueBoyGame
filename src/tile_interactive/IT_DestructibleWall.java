package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_DestructibleWall extends InteractiveTile {

	GamePanel gp;
	public static final String IT_NAME = "Destructible Wall";

	public IT_DestructibleWall(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;

		this.worldX = gp.TILE_SIZE * col;
		this.worldY = gp.TILE_SIZE * row;
		life = 3;
		down1 = setup("/tiles_interactive/destructiblewall", gp.TILE_SIZE,
				gp.TILE_SIZE);
		name = IT_NAME;
		isDestructible = true;
	}

	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;

		if (entity.currentWeapon.type == TYPE_PICKAXE) {
			isCorrectItem = true;
		}

		return isCorrectItem;
	}

	public void playSE() {
		gp.playSE(20);
	}

	public InteractiveTile getDestoyedForm() {
		InteractiveTile tile = null;
		return tile;
	}

	public Color getParticleColor() {
		Color color = new Color(65, 65, 65);
		return color;
	}

	public int getParticleSize() {
		int size = 6; // 6 pixels
		return size;
	}

	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}

	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}

//	public void checkDrop() {
//		// CAST A DIE
//		int i = new Random().nextInt(100) + 1;
//
//		// SET THE DESTRUCTIBLE DROP
//		if (i < 50) {
//			dropItem(new OBJ_Coin_Bronze(gp));
//		}
//		if (i >= 50 && i < 75) {
//			dropItem(new OBJ_Heart(gp));
//		}
//		if (i >= 75 && i < 100) {
//			dropItem(new OBJ_ManaCrystal(gp));
//		}
//	}

}
