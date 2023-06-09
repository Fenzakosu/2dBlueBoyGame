package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class Particle extends Entity {

	Entity generator;
	Color color;
	int size;
	int xd;
	int yd;

	public Particle(GamePanel gp, Entity generator, Color color, int size, int speed,
			int maxLife, int xd, int yd) {
		super(gp);

		this.generator = generator;
		this.color = color;
		this.size = size;
		this.speed = speed;
		this.maxLife = maxLife;
		this.xd = xd;
		this.yd = yd;

		life = maxLife;
		int offset = (gp.TILE_SIZE / 2) - (size / 2);
		worldX = generator.worldX + offset;
		worldY = generator.worldY + offset;
	}

	public void update() {
		life--;
		// PARTICLE GRAVITY
		if (life < maxLife/3) {
			yd++;
		}
		
		worldX += xd * speed;
		worldY += yd * speed;
		if (life == 0) {
			isAlive = false;
		}
	}

	public void draw(Graphics2D g2) {
		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

		g2.setColor(color);
		g2.fillRect(screenX, screenY, size, size);
	}

}
