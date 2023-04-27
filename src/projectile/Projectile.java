package projectile;

import entity.Entity;
import main.GamePanel;

public class Projectile extends Entity {

	Entity user;

	GamePanel gp;

	public Projectile(GamePanel gp) {
		super(gp);
		this.gp = gp;
	}

	public void set(int worldX, int worldY, String direction, boolean isAlive,
			Entity user) {
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.isAlive = isAlive;
		this.user = user;
		this.life = this.maxLife;
	}

	public void update() {

		// check collision if player is using a projectile
		if (user == gp.player) {
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
			if (monsterIndex != 999) {
				gp.player.damageMonster(monsterIndex, attack,knockbackPower);
				generateParticle(user.projectile,
						gp.monsters[gp.currentMap][monsterIndex]);
				isAlive = false;
			}
		}
		// check collision if monster(or npc) is using a projectile
		if (user != gp.player) {
			boolean isTouchingPlayer = gp.cChecker.checkPlayer(this);
			if (gp.player.isInvincible == false && isTouchingPlayer == true) {
				damagePlayer(attack);
				generateParticle(user.projectile, gp.player);
				isAlive = false;
			}
		}
		// check collision between projectiles
		gp.cChecker.checkEntity(gp.projectiles, gp.projectiles);

		switch (direction) {
		case "up":
			worldY -= speed;
			break;
		case "down":
			worldY += speed;
			break;
		case "left":
			worldX -= speed;
			break;
		case "right":
			worldX += speed;
			break;
		}

		life--;
		if (life <= 0) {
			isAlive = false;
		}

		spriteCounter++;
		if (spriteCounter > 12) {
			if (spriteNum == 1) {
				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}

	}

	public boolean hasResource(Entity user) {
		boolean hasResource = false;
		return hasResource;
	}

	public void subtractResource(Entity user) {
	}

	public void damageProjectile(int i) {

		if (i != 999) {
			Entity projectile = gp.projectiles[gp.currentMap][i];
			projectile.isAlive = false;
			generateParticle(projectile, projectile);
		}

	}
}
