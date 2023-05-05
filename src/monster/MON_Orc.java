package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class MON_Orc extends Entity {

	GamePanel gp;

	public MON_Orc(GamePanel gp) {
		super(gp);

		this.gp = gp;

		// STATS
		// MONSTER TYPE IS 2
		type = TYPE_MONSTER;
		name = "Orc";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 10;
		life = maxLife;
		attack = 8;
		defense = 2;
		expPoints = 30;
		knockbackPower = 5;
		// ORC SOLID AREA
		solidArea.x = 4;
		solidArea.y = 4;
		solidArea.width = 40;
		solidArea.height = 44;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 48;
		attackArea.height = 48;
		motion1Duration = 40;
		motion2Duration = 85;
		
		// LOADING ORC IMAGES
		getImages();
		getAttackImages();
	}

	public void getImages() {
		up1 = setup("/monsters/orc_up_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/monsters/orc_up_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/monsters/orc_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/monsters/orc_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/monsters/orc_left_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/monsters/orc_left_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/monsters/orc_right_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/monsters/orc_right_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	public void getAttackImages() {
		attackUp1 = setup("/monsters/orc_attack_up_1", gp.TILE_SIZE,
				gp.TILE_SIZE * 2);
		attackUp2 = setup("/monsters/orc_attack_up_2", gp.TILE_SIZE,
				gp.TILE_SIZE * 2);
		attackDown1 = setup("/monsters/orc_attack_down_1", gp.TILE_SIZE,
				gp.TILE_SIZE * 2);
		attackDown2 = setup("/monsters/orc_attack_down_2", gp.TILE_SIZE,
				gp.TILE_SIZE * 2);
		attackLeft1 = setup("/monsters/orc_attack_left_1", gp.TILE_SIZE * 2,
				gp.TILE_SIZE);
		attackLeft2 = setup("/monsters/orc_attack_left_2", gp.TILE_SIZE * 2,
				gp.TILE_SIZE);
		attackRight1 = setup("/monsters/orc_attack_right_1", gp.TILE_SIZE * 2,
				gp.TILE_SIZE);
		attackRight2 = setup("/monsters/orc_attack_right_2", gp.TILE_SIZE * 2,
				gp.TILE_SIZE);
	}

	public void setAction() {

//		

		if (onPath == true) {

			// CHECK IF IT STOPS CHASING (TARGET, DISTANCE, RATE)
			checkStopChasingOrNot(gp.player, 15, 100);

			// SEARCH THE DIRECTION TO GO
			searchPath(getGoalCol(gp.player), getGoalRow(gp.player));
		} else {
			// CHECK IF STARTS CHASING (TARGET, DISTANCE, RATE)
			checkStartChasingOrNot(gp.player, 5, 100);
			// GET A RANDOM DIRECTION
			getRandomDirection(120);

		}
		// CHECK IF IT ATTACKS OR NOT
		if (isAttacking == false) {
			checkAttackingOrNot(30, gp.TILE_SIZE * 4, gp.TILE_SIZE);
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
