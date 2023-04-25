package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import projectile.PR_Rock;

public class MON_GreenSlime extends Entity {

	GamePanel gp;

	public MON_GreenSlime(GamePanel gp) {
		super(gp);

		this.gp = gp;

		// STATS
		// MONSTER TYPE IS 2
		type = TYPE_MONSTER;
		name = "Green Slime";
		speed = 1;
		maxLife = 6;
		life = maxLife;
		attack = 5;
		defense = 0;
		expPoints = 4;
		projectile = new PR_Rock(gp);
		// GREEN SLIME SOLID AREA
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		// LOADING GREEN SLIME IMAGES
		getImages();
	}

	public void getImages() {
		up1 = setup("/monsters/greenslime_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/monsters/greenslime_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/monsters/greenslime_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/monsters/greenslime_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/monsters/greenslime_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/monsters/greenslime_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/monsters/greenslime_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/monsters/greenslime_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	public void update() {
		super.update();

		int xDistance = Math.abs(worldX - gp.player.worldX);
		int yDistance = Math.abs(worldY - gp.player.worldY);
		int tileDistance = (xDistance + yDistance) / gp.TILE_SIZE;

		if (onPath == false && tileDistance < 5) {
			int i = new Random().nextInt(100) + 1;
			if (i > 50) {
				onPath = true;
			}
		}
		if (onPath == true && tileDistance > 20) {
			onPath = false;
		}
	}

	public void setAction() {

		if (onPath == true) {
			// GO TO A POINT
//			int goalCol = 12;
//			int goalRow = 9;
			// FOLLOW PLAYER
			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.TILE_SIZE;
			int goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.TILE_SIZE;
			;

			searchPath(goalCol, goalRow);
			int i = new Random().nextInt(200) + 1;
			if (i > 197 && projectile.isAlive == false && shotCounter == 30) {
				projectile.set(worldX, worldY, direction, true, this);
				gp.projectileList.add(projectile);
				shotCounter = 0;
			}
		} else {

			actionLockCounter++;

			if (actionLockCounter == 120) {

				Random random = new Random();
				int i = random.nextInt(100) + 1; // pick a number from 1 to 100

				if (i <= 25) {
					direction = "up";
				}

				if (i > 25 && i <= 50) {
					direction = "down";
				}
				if (i > 50 && i <= 75) {
					direction = "left";
				}
				if (i > 75 && i <= 100) {
					direction = "right";
				}
				actionLockCounter = 0;
			}

		}
	}

	public void reactToDamage() {
		actionLockCounter = 0;
		onPath = true;

	}

	public void checkDrop() {
		// CAST A DIE
		int i = new Random().nextInt(100) + 1;

		// SET THE MONSTER DROP
		if (i < 50) {
			dropItem(new OBJ_Coin_Bronze(gp));
		}
		if (i >= 50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if (i >= 75 && i < 100) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}

}
