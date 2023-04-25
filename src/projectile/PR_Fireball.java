package projectile;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class PR_Fireball extends Projectile {

	GamePanel gp;

	public PR_Fireball(GamePanel gp) {
		super(gp);

		this.gp = gp;

		name = "Fireball";
		speed = 6;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		isAlive = false;
		getImages();
	}

	public void getImages() {
		up1 = setup("/projectiles/fireball_up_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/projectiles/fireball_up_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/projectiles/fireball_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/projectiles/fireball_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/projectiles/fireball_left_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/projectiles/fireball_left_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/projectiles/fireball_right_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/projectiles/fireball_right_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	public boolean hasResource(Entity user) {
		boolean hasResource = false;
		if (user.mana >= useCost) {
			hasResource = true;
		}
		return hasResource;
	}
	
	public void subtractResource(Entity user) {
		user.mana -= useCost;
	}
	public Color getParticleColor() {
		Color color = new Color(210, 100, 0);
		return color;
	}

	public int getParticleSize() {
		int size = 10; // 10 pixels
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
}
