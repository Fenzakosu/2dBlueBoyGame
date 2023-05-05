package boss_monster;

import java.util.Random;

import data.Progress;
import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class BMON_SkeletonLord extends Entity {

	GamePanel gp;
	public static final String BOSS_NAME = "Skeleton Lord";

	public BMON_SkeletonLord(GamePanel gp) {
		super(gp);

		this.gp = gp;

		// STATS
		type = TYPE_MONSTER;
		isBoss = true;
		sleep = true;
		name = BOSS_NAME;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 50;
		life = maxLife;
		attack = 10;
		defense = 5;
		expPoints = 150;
		knockbackPower = 5;
		// SKELETON LORD SOLID AREA

		int size = gp.TILE_SIZE * 5;

		solidArea.x = 40;
		solidArea.y = 48;
		solidArea.width = size - 48 * 2;
		solidArea.height = size - 48;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 170;
		attackArea.height = 170;
		motion1Duration = 25;
		motion2Duration = 50;

		// LOADING SKELETON LORD IMAGES
		getImages();
		getAttackImages();
		// LOADING SKELETON LORD DIALOGUE
		setDialogues();
	}

	public void getImages() {

		// INCREASE IMAGE SIZE OF THE BOSS
		int i = 5;

		if (isEnraged == false) {
			up1 = setup("/boss_monsters/skeletonlord_up_1", gp.TILE_SIZE * i,
					gp.TILE_SIZE * i);
			up2 = setup("/boss_monsters/skeletonlord_up_2", gp.TILE_SIZE * i,
					gp.TILE_SIZE * i);
			down1 = setup("/boss_monsters/skeletonlord_down_1", gp.TILE_SIZE * i,
					gp.TILE_SIZE * i);
			down2 = setup("/boss_monsters/skeletonlord_down_2", gp.TILE_SIZE * i,
					gp.TILE_SIZE * i);
			left1 = setup("/boss_monsters/skeletonlord_left_1", gp.TILE_SIZE * i,
					gp.TILE_SIZE * i);
			left2 = setup("/boss_monsters/skeletonlord_left_2", gp.TILE_SIZE * i,
					gp.TILE_SIZE * i);
			right1 = setup("/boss_monsters/skeletonlord_right_1", gp.TILE_SIZE * i,
					gp.TILE_SIZE * i);
			right2 = setup("/boss_monsters/skeletonlord_right_2", gp.TILE_SIZE * i,
					gp.TILE_SIZE * i);
		} else {
			up1 = setup("/boss_monsters/skeletonlord_phase2_up_1", gp.TILE_SIZE * i,
					gp.TILE_SIZE * i);
			up2 = setup("/boss_monsters/skeletonlord_phase2_up_2", gp.TILE_SIZE * i,
					gp.TILE_SIZE * i);
			down1 = setup("/boss_monsters/skeletonlord_phase2_down_1",
					gp.TILE_SIZE * i, gp.TILE_SIZE * i);
			down2 = setup("/boss_monsters/skeletonlord_phase2_down_2",
					gp.TILE_SIZE * i, gp.TILE_SIZE * i);
			left1 = setup("/boss_monsters/skeletonlord_phase2_left_1",
					gp.TILE_SIZE * i, gp.TILE_SIZE * i);
			left2 = setup("/boss_monsters/skeletonlord_phase2_left_2",
					gp.TILE_SIZE * i, gp.TILE_SIZE * i);
			right1 = setup("/boss_monsters/skeletonlord_phase2_right_1",
					gp.TILE_SIZE * i, gp.TILE_SIZE * i);
			right2 = setup("/boss_monsters/skeletonlord_phase2_right_2",
					gp.TILE_SIZE * i, gp.TILE_SIZE * i);
		}

	}

	public void getAttackImages() {
		// INCREASE IMAGE SIZE OF THE BOSS
		int i = 5;

		if (isEnraged == false) {
			attackUp1 = setup("/boss_monsters/skeletonlord_attack_up_1",
					gp.TILE_SIZE * i, gp.TILE_SIZE * 2 * i);
			attackUp2 = setup("/boss_monsters/skeletonlord_attack_up_2",
					gp.TILE_SIZE * i, gp.TILE_SIZE * 2 * i);
			attackDown1 = setup("/boss_monsters/skeletonlord_attack_down_1",
					gp.TILE_SIZE * i, gp.TILE_SIZE * 2 * i);
			attackDown2 = setup("/boss_monsters/skeletonlord_attack_down_2",
					gp.TILE_SIZE * i, gp.TILE_SIZE * 2 * i);
			attackLeft1 = setup("/boss_monsters/skeletonlord_attack_left_1",
					gp.TILE_SIZE * 2 * i, gp.TILE_SIZE * i);
			attackLeft2 = setup("/boss_monsters/skeletonlord_attack_left_2",
					gp.TILE_SIZE * 2 * i, gp.TILE_SIZE * i);
			attackRight1 = setup("/boss_monsters/skeletonlord_attack_right_1",
					gp.TILE_SIZE * 2 * i, gp.TILE_SIZE * i);
			attackRight2 = setup("/boss_monsters/skeletonlord_attack_right_2",
					gp.TILE_SIZE * 2 * i, gp.TILE_SIZE * i);
		} else {
			attackUp1 = setup("/boss_monsters/skeletonlord_phase2_attack_up_1",
					gp.TILE_SIZE * i, gp.TILE_SIZE * 2 * i);
			attackUp2 = setup("/boss_monsters/skeletonlord_phase2_attack_up_2",
					gp.TILE_SIZE * i, gp.TILE_SIZE * 2 * i);
			attackDown1 = setup("/boss_monsters/skeletonlord_phase2_attack_down_1",
					gp.TILE_SIZE * i, gp.TILE_SIZE * 2 * i);
			attackDown2 = setup("/boss_monsters/skeletonlord_phase2_attack_down_2",
					gp.TILE_SIZE * i, gp.TILE_SIZE * 2 * i);
			attackLeft1 = setup("/boss_monsters/skeletonlord_phase2_attack_left_1",
					gp.TILE_SIZE * 2 * i, gp.TILE_SIZE * i);
			attackLeft2 = setup("/boss_monsters/skeletonlord_phase2_attack_left_2",
					gp.TILE_SIZE * 2 * i, gp.TILE_SIZE * i);
			attackRight1 = setup("/boss_monsters/skeletonlord_phase2_attack_right_1",
					gp.TILE_SIZE * 2 * i, gp.TILE_SIZE * i);
			attackRight2 = setup("/boss_monsters/skeletonlord_phase2_attack_right_2",
					gp.TILE_SIZE * 2 * i, gp.TILE_SIZE * i);
		}

	}

	public void setDialogues() {
		dialogues[0][0] = "No one can steal MY treasure!";
		dialogues[0][1] = "Only death awaits you here!";
		dialogues[0][2] = "THE HOUR OF YOUR DOOM APPROACHES!";
	}

	public void setAction() {

		// RAGE STATE
		if (isEnraged == false && life < maxLife / 2) {
			isEnraged = true;
			getImages();
			getAttackImages();
			// INCREASE SPEED AND ATTACK
			defaultSpeed++;
			speed = defaultSpeed;
			attack *= 2;
		}

		if (getTileDistance(gp.player) < 10) {
			// MOVE TOWARDS THE PLAYER
			moveTowardPlayer(60);
		} else {
			// GET A RANDOM DIRECTION
			getRandomDirection(120);

		}
		// CHECK IF IT ATTACKS OR NOT
		if (isAttacking == false) {
			checkAttackingOrNot(30, gp.TILE_SIZE * 7, gp.TILE_SIZE * 5);
		}
	}

	public void reactToDamage() {
		actionLockCounter = 0;

	}

	// DROP ONE RANDOM ITEM & END BOSS BATTLE MODE
	public void checkDrop() {

		gp.bossBattleOn = false;
		Progress.skeletonLordDefeated = true;
		
		// RESTORE THE PREVIOUS MUSIC
		gp.stopMusic();
		gp.playMusic(19);

		// REMOVE THE TEMPORARY IRON DOORS
		for (int i = 0; i < gp.objs[1].length; i++) {
			if (gp.objs[gp.currentMap][i] != null
					&& gp.objs[gp.currentMap][i].name.equals(OBJ_Door_Iron.OBJ_NAME)) {
				gp.playSE(21);
				gp.objs[gp.currentMap][i] = null;
			}
		}

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
