package tile_interactive;

import java.awt.Graphics2D;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {

	GamePanel gp;
	public boolean isDestructible = false;

	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}

	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		return isCorrectItem;
	}

	public void update() {
		if (isInvincible == true) {
			invincibleCounter++;
			// GENERAL ENTITY INVULNERABILITY TIMER IS SHORTER THAN PLAYER'S
			if (invincibleCounter > gp.fps / 2) {
				isInvincible = false;
				invincibleCounter = 0;
			}
		}
	}

	public void playSE() {

	}

	public InteractiveTile getDestoyedForm() {
		InteractiveTile tile = null;
		return tile;
	}
	
	public void draw(Graphics2D g2) {

		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

		if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X
				&& worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X
				&& worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y
				&& worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {


			g2.drawImage(down1, screenX, screenY, null);

			changeAlpha(g2, 1F);
		}
	}
}
