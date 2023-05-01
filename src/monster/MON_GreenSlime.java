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
		defaultSpeed = 1;
		speed = defaultSpeed;
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

	public void setAction() {

//		int xDistance = Math.abs(worldX - gp.player.worldX);
//		int yDistance = Math.abs(worldY - gp.player.worldY);
//		int tileDistance = (xDistance + yDistance) / gp.TILE_SIZE;

		if (onPath == true) {

			// CHECK IF IT STOPS CHASING (TARGET, DISTANCE, RATE)
			checkStopChasingOrNot(gp.player, 15, 100);
			
			// SEARCH THE DIRECTION TO GO
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
			// CHECK IF IT SHOOTS A PROJECTILE (RATE, SHOT INTERVAL)
			checkShootingOrNot(200, 30);
		} else {
			// CHECK IF STARTS CHASING (TARGET, DISTANCE, RATE)
			checkStartChasingOrNot(gp.player, 5, 100);
			// GET A RANDOM DIRECTION
			getRandomDirection();
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
