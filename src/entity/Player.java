package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import projectile.PR_Fireball;
import utility.KeyHandler;

public class Player extends Entity {

	KeyHandler keyH;
	int standCounter = 0;
	public final int SCREEN_X;
	public final int SCREEN_Y;
	public boolean attackIsCanceled = false;
	public boolean lightIsUpdated = false;

	public Player(GamePanel gp, KeyHandler keyH) {

		super(gp);

		this.keyH = keyH;

		SCREEN_X = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);
		SCREEN_Y = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);
		// SOLID AREA
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		// ATTACK AREA
//		attackArea.width = 36;
//		attackArea.height = 36;

		setDefaultValues();

	}

	public void setDefaultValues() {
		// START AT WORLD MAP
//		gp.currentMap = 0;
//		worldX = gp.TILE_SIZE * 23;
//		worldY = gp.TILE_SIZE * 21;
		// START IN DUNGEON02
		gp.currentMap = 3;
		worldX = gp.TILE_SIZE * 25;
		worldY = gp.TILE_SIZE * 31;
		name = "Blue Boy";
		defaultSpeed = 4;
		speed = defaultSpeed;
		direction = "down";
		// PLAYER STATS
		level = 1;
		maxLife = 6;
		life = maxLife;
		maxMana = 4;
		mana = maxMana;
		strength = 15; // MORE STRENGTH = MORE DAMAGE
		dexterity = 10; // MORE DEXTERITY = MORE DEFENSE
		exp = 0;
		nextLevelExp = 5;
		coin = 5000;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		currentLight = null;
		projectile = new PR_Fireball(gp);
		attack = getAttack(); // STRENGTH X WEAPON DAMAGE
		defense = getDefense(); // DEXTERITY X SHIELD DEFENSE VALUE
		getImages();
		getAttackImages();
		getGuardImages();
		setItems();
		setDialogue();
	}

	public void setDefaultPositions() {

		gp.currentMap = 0;
		worldX = gp.TILE_SIZE * 23;
		worldY = gp.TILE_SIZE * 21;
		direction = "down";
	}

	public void setDialogue() {
		dialogues[0][0] = "You are level " + level + " now!\n"
				+ "You feel stronger!";
	}

	public void restoreStatus() {
		life = maxLife;
		mana = maxMana;
		isInvincible = false;
		isTransparent = false;
		knockbackOn = false;
		guardingOn = false;
		lightIsUpdated = true;
		speed = defaultSpeed;
	}

	public void setItems() {
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
		inventory.add(new OBJ_Potion_Red(gp));
		inventory.add(new OBJ_Axe(gp));
		inventory.add(new OBJ_Shield_Blue(gp));
		inventory.add(new OBJ_Lantern(gp));
	}

	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		motion1Duration = currentWeapon.motion1Duration;
		motion2Duration = currentWeapon.motion2Duration;
		return attack = strength * currentWeapon.attackValue;
	}

	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}

	public int getCurrentWeaponSlot() {
		int currentWeaponSlot = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) == currentWeapon) {
				currentWeaponSlot = i;
			}
		}
		return currentWeaponSlot;
	}

	public int getCurrentShieldSlot() {
		int currentShieldSlot = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) == currentShield) {
				currentShieldSlot = i;
			}
		}
		return currentShieldSlot;
	}

	// SIZE OF IMAGES = 16 X 16
	public void getImages() {

		up1 = setup("/player/boy_up_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/player/boy_up_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/player/boy_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/player/boy_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/player/boy_left_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/player/boy_left_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/player/boy_right_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/player/boy_right_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	// TENT IMAGES (WHEN SLEEPING)
	public void getSleepingImages(BufferedImage image) {
		up1 = image;
		up2 = image;
		down1 = image;
		down2 = image;
		left1 = image;
		left2 = image;
		right1 = image;
		right2 = image;
	}

	// SIZE OF IMAGES : UP , DOWN = 16 X 32
	// IZE OF IMAGES : LEFT, RIGHT = 32 X 16
	public void getAttackImages() {

		if (currentWeapon.type == TYPE_SWORD) {
			attackUp1 = setup("/player/boy_attack_up_1", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackUp2 = setup("/player/boy_attack_up_2", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackDown1 = setup("/player/boy_attack_down_1", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackDown2 = setup("/player/boy_attack_down_2", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackLeft1 = setup("/player/boy_attack_left_1", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
			attackLeft2 = setup("/player/boy_attack_left_2", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
			attackRight1 = setup("/player/boy_attack_right_1", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
			attackRight2 = setup("/player/boy_attack_right_2", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
		}
		if (currentWeapon.type == TYPE_AXE) {
			attackUp1 = setup("/player/boy_axe_up_1", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackUp2 = setup("/player/boy_axe_up_2", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackDown1 = setup("/player/boy_axe_down_1", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackDown2 = setup("/player/boy_axe_down_2", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackLeft1 = setup("/player/boy_axe_left_1", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
			attackLeft2 = setup("/player/boy_axe_left_2", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
			attackRight1 = setup("/player/boy_axe_right_1", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
			attackRight2 = setup("/player/boy_axe_right_2", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
		}
		if (currentWeapon.type == TYPE_PICKAXE) {
			attackUp1 = setup("/player/boy_pick_up_1", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackUp2 = setup("/player/boy_pick_up_2", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackDown1 = setup("/player/boy_pick_down_1", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackDown2 = setup("/player/boy_pick_down_2", gp.TILE_SIZE,
					gp.TILE_SIZE * 2);
			attackLeft1 = setup("/player/boy_pick_left_1", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
			attackLeft2 = setup("/player/boy_pick_left_2", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
			attackRight1 = setup("/player/boy_pick_right_1", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
			attackRight2 = setup("/player/boy_pick_right_2", gp.TILE_SIZE * 2,
					gp.TILE_SIZE);
		}
	}

	public void getGuardImages() {
		guardUp = setup("/player/boy_guard_up", gp.TILE_SIZE, gp.TILE_SIZE);
		guardDown = setup("/player/boy_guard_down", gp.TILE_SIZE, gp.TILE_SIZE);
		guardLeft = setup("/player/boy_guard_left", gp.TILE_SIZE, gp.TILE_SIZE);
		guardRight = setup("/player/boy_guard_right", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	public void update() {

		if (knockbackOn == true) {

			collisionOn = false;
			gp.cChecker.checkTile(this);
			gp.cChecker.checkObject(this, true);
			gp.cChecker.checkEntity(this, gp.npcs);
			gp.cChecker.checkEntity(this, gp.monsters);
			gp.cChecker.checkEntity(this, gp.iTiles);

			if (collisionOn == true) {
				knockbackCounter = 0;
				knockbackOn = false;
				speed = defaultSpeed;
			} else if (collisionOn == false) {
				switch (knockbackDIrection) {
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
			}
			knockbackCounter++;
			if (knockbackCounter == 10) {
				knockbackCounter = 0;
				knockbackOn = false;
				speed = defaultSpeed;
			}

		}

		else if (isAttacking == true) {
			attacking();
		} else if (keyH.spacePressed == true) {
			guardingOn = true;
			guardCounter++;
		}

		else if (keyH.upPressed == true || keyH.downPressed == true
				|| keyH.leftPressed == true || keyH.rightPressed == true
				|| keyH.enterPressed == true) {
			if (keyH.upPressed == true) {
				direction = "up";
			} else if (keyH.downPressed == true) {
				direction = "down";
			} else if (keyH.leftPressed == true) {
				direction = "left";
			} else if (keyH.rightPressed == true) {
				direction = "right";
			}

			// CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);

			// CHECK OBJECT COLLISION
			int objIndex = gp.cChecker.checkObject(this, true);
			pickupObject(objIndex);

			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkEntity(this, gp.npcs);
			interactWithNPC(npcIndex);

			// CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
			contactMonster(monsterIndex);

			// CHECK INTERACTIVE TILE COLLISION
			gp.cChecker.checkEntity(this, gp.iTiles);

			// CHECK DAMAGE PIT EVENT
			gp.eHandler.checkEvent();

			// IF COLLISION IS FALSE , PLAYER CAN MOVE
			if (collisionOn == false && keyH.enterPressed == false) {
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
			}

			if (keyH.enterPressed == true && attackIsCanceled == false) {
				gp.playSE(7);
				isAttacking = true;
				spriteCounter = 0;
			}

			attackIsCanceled = false;
			gp.keyH.enterPressed = false;
			guardingOn = false;
			gp.keyH.spacePressed = false;
			// RESET GUARD COUNTER
			guardCounter = 0;
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
		if (gp.keyH.shootKeyPressed == true && projectile.hasResource(this) == false
				&& shotCounter == 30) {
			gp.ui.addMessage("Not enough mana!");
			gp.keyH.shootKeyPressed = false;
		}

		if (gp.keyH.shootKeyPressed == true && projectile.isAlive == false
				&& shotCounter == 30 && projectile.hasResource(this) == true) {
			// SET DEFAULT COORDINATES, DIRECTION AND USER
			projectile.set(worldX, worldY, direction, true, this);
			// SUBTRACT THE COST (MANA, AMMO ...)
			projectile.subtractResource(this);

			// CHECK VACANCY
			for (int i = 0; i < gp.projectiles[1].length; i++) {
				if (gp.projectiles[gp.currentMap][i] == null) {
					gp.projectiles[gp.currentMap][i] = projectile;
					break;
				}
			}

			// RESET SHOT COUNTER FOR PLAYER
			shotCounter = 0;
			// RESET GUARD COUNTER FOR PLAYER
			guardCounter = 0;
			// PLAY FIREBALL SOUND
			gp.playSE(10);
		}

		// THIS NEEDS TO BE OUTSIDE OF KEY IF STATEMENT!
		if (isInvincible == true) {
			invincibleCounter++;
			if (invincibleCounter > gp.fps) {
				isInvincible = false;
				isTransparent = false;
				invincibleCounter = 0;
			}
		}
		if (shotCounter < 30) {
			shotCounter++;
		}
		if (life > maxLife) {
			life = maxLife;
		}
		if (mana > maxMana) {
			mana = maxMana;
		}

		if (keyH.godModeOn == false) {
			if (life <= 0) {
				gp.gameState = gp.gameOverState;
				gp.ui.commandNum = -1;
				gp.stopMusic();
				gp.playSE(12);
			}
		}

	}

	public void pickupObject(int i) {
		if (i != 999) {

			// PICKUP ONLY ITEMS
			if (gp.objs[gp.currentMap][i].type == TYPE_PICKUP_ONLY) {
				gp.objs[gp.currentMap][i].use(this);
				gp.objs[gp.currentMap][i] = null;
			}
			// OBSTACLE
			else if (gp.objs[gp.currentMap][i].type == TYPE_OBSTACLE) {
				if (keyH.enterPressed == true) {
					attackIsCanceled = true;
					gp.objs[gp.currentMap][i].interact();
				}
			} else {
				// INVENTORY ITEMS

				String text;
				if (canObtainItem(gp.objs[gp.currentMap][i]) == true) {
					gp.playSE(1);
					text = "Picked up a " + gp.objs[gp.currentMap][i].name + "!";
				} else {
					text = "Inventory is full!";
				}

				gp.ui.addMessage(text);
				gp.objs[gp.currentMap][i] = null;
			}

		}
	}

	public void interactWithNPC(int i) {

		if (i != 999) {
			// DIALOGUE WITH NPC
			if (gp.keyH.enterPressed == true) {
				attackIsCanceled = true;
				gp.npcs[gp.currentMap][i].speak();
			}
			// MOVE OBJECT (NPC) DEPENDING ON PLAYER'S DIRECTION
			gp.npcs[gp.currentMap][i].move(direction);
		}
	}

	public void contactMonster(int i) {
		if (i != 999) {
			if (isInvincible == false
					&& gp.monsters[gp.currentMap][i].isDying == false) {

				gp.playSE(6);

				int monsterDamage = gp.monsters[gp.currentMap][i].attack - defense;
				if (monsterDamage < 1) {
					monsterDamage = 1;
				}

				life -= monsterDamage;
				isInvincible = true;
				isTransparent = true;
			}
		}
	}

	public void damageMonster(int i, Entity attacker, int attack,
			int knockbackPower) {
		if (i != 999) {
			if (gp.monsters[gp.currentMap][i].isInvincible == false) {

				gp.playSE(5);
				if (knockbackPower > 0) {
					knockback(gp.monsters[gp.currentMap][i], attacker,
							knockbackPower);
				}

				if (gp.monsters[gp.currentMap][i].offBalanceOn == true) {
					attack *= 5;
				}

				int damage = attack - gp.monsters[gp.currentMap][i].defense;
				if (damage < 0) {
					damage = 0;
				}

				gp.monsters[gp.currentMap][i].life -= damage;

				gp.ui.addMessage(damage + " damage!");

				gp.monsters[gp.currentMap][i].isInvincible = true;
				gp.monsters[gp.currentMap][i].reactToDamage();
				if (gp.monsters[gp.currentMap][i].life <= 0) {
					gp.monsters[gp.currentMap][i].life = 0;
					gp.monsters[gp.currentMap][i].isDying = true;
					gp.ui.addMessage("You killed the "
							+ gp.monsters[gp.currentMap][i].name + "!");
					gp.ui.addMessage(
							"EXP + " + gp.monsters[gp.currentMap][i].expPoints);
					exp += gp.monsters[gp.currentMap][i].expPoints;
					checkLevelUp();

				}
			}
		}
	}

	public void damageInteractiveTile(int i) {
		if (i != 999 && gp.iTiles[gp.currentMap][i].isDestructible == true
				&& gp.iTiles[gp.currentMap][i].isCorrectItem(this) == true
				&& gp.iTiles[gp.currentMap][i].isInvincible == false) {

			gp.iTiles[gp.currentMap][i].playSE();
			gp.iTiles[gp.currentMap][i].life--;
			gp.iTiles[gp.currentMap][i].isInvincible = true;

			// Generate particle
			generateParticle(gp.iTiles[gp.currentMap][i],
					gp.iTiles[gp.currentMap][i]);

			if (gp.iTiles[gp.currentMap][i].life <= 0) {
//				gp.iTiles[gp.currentMap][i].checkDrop();
				gp.iTiles[gp.currentMap][i] = gp.iTiles[gp.currentMap][i]
						.getDestoyedForm();
			}
		}
	}

	public void damageProjectile(int i) {

		if (i != 999) {
			Entity projectile = gp.projectiles[gp.currentMap][i];
			projectile.isAlive = false;
			generateParticle(projectile, projectile);
		}

	}

	public void checkLevelUp() {
		if (exp >= nextLevelExp) {
			level++;
			exp = exp - nextLevelExp;
			nextLevelExp = nextLevelExp * 2;
			maxLife += 2;
			maxMana++;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			setDialogue();
			gp.playSE(8);

			startDialogue(this, 0);
			// CHECK LEVEL AGAIN IF YOU GAIN MORE THAN ENOUGH
			// EXPERIENCE ABOVE LEVEL UP THRESHOLD
			if (exp >= nextLevelExp) {
				checkLevelUp();
			}
		}
	}

	public void selectItem() {
		int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol,
				gp.ui.playerSlotRow);

		if (itemIndex < inventory.size()) {

			Entity selectedItem = inventory.get(itemIndex);
			// WEAPON EQUIP
			if (selectedItem.type == TYPE_SWORD || selectedItem.type == TYPE_AXE
					|| selectedItem.type == TYPE_PICKAXE) {
				currentWeapon = selectedItem;
				attack = getAttack();
				getAttackImages();
			}
			// SHIELD EQUIP
			if (selectedItem.type == TYPE_SHIELD) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			// LIGHT SOURCE EQUIP
			if (selectedItem.type == TYPE_LIGHT) {
				if (currentLight == selectedItem) {
					currentLight = null;
				} else {
					currentLight = selectedItem;
				}
				lightIsUpdated = true;
			}
			// CONSUMABLES
			if (selectedItem.type == TYPE_CONSUMABLE) {
				if (selectedItem.use(this) == true) {
					if (selectedItem.amount > 1) {
						selectedItem.amount--;
					} else {
						inventory.remove(itemIndex);
					}
				}
			}

		}
	}

	// CHECK IF THE SAME ITEM IS ALREADY IN INVENTORY
	public int searchItemInInventory(String itemName) {

		int itemIndex = 999;

		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				break;
			}
		}
		return itemIndex;
	}

	public boolean canObtainItem(Entity item) {

		boolean canObtain = false;

		Entity newItem = gp.entGenerator.getObject(item.name);

		// CHECK IF STACKABLE
		if (newItem.isStackable == true) {
			int index = searchItemInInventory(newItem.name);
			if (index != 999) {
				inventory.get(index).amount++;
				canObtain = true;
			} else { // NEW ITEM, SO WE NEED TO CHECK VACANCY
				if (inventory.size() != MAX_INVENTORY_SLOTS) {
					inventory.add(newItem);
					canObtain = true;
				}
			}
		} else { // NOT STACKABLE, SO CHECK VACANCY
			if (inventory.size() != MAX_INVENTORY_SLOTS) {
				inventory.add(newItem);
				canObtain = true;
			}
		}

		return canObtain;

	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		int tempScreenX = SCREEN_X;
		int tempScreenY = SCREEN_Y;

		switch (direction) {
		case "up":
			if (isAttacking == false) {
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
			}
			if (isAttacking == true) {
				tempScreenY = SCREEN_Y - gp.TILE_SIZE;
				if (spriteNum == 1) {
					image = attackUp1;
				}
				if (spriteNum == 2) {
					image = attackUp2;
				}
			}
			if (guardingOn == true) {
				image = guardUp;
			}
			break;
		case "down":
			if (isAttacking == false) {
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
			}
			if (isAttacking == true) {
				if (spriteNum == 1) {
					image = attackDown1;
				}
				if (spriteNum == 2) {
					image = attackDown2;
				}
			}
			if (guardingOn == true) {
				image = guardDown;
			}
			break;
		case "left":
			if (isAttacking == false) {
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
			}
			if (isAttacking == true) {
				tempScreenX = SCREEN_X - gp.TILE_SIZE;
				if (spriteNum == 1) {
					image = attackLeft1;
				}
				if (spriteNum == 2) {
					image = attackLeft2;
				}
			}
			if (guardingOn == true) {
				image = guardLeft;

			}
			break;
		case "right":
			if (isAttacking == false) {
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
			}
			if (isAttacking == true) {
				if (spriteNum == 1) {
					image = attackRight1;
				}
				if (spriteNum == 2) {
					image = attackRight2;
				}
			}
			if (guardingOn == true) {
				image = guardRight;
			}
			break;
		}

		// MAKE PLAYER CHARACTER HALF-TRANSPARENT (70 %) WHEN HE IS INVULNERABLE
		if (isTransparent == true) {
			g2.setComposite(
					AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		if (drawing == true) {
			g2.drawImage(image, tempScreenX, tempScreenY, null);
		}

		// RESET ALPHA
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// DEBUG : PLAYER RECIEVING DAMAGE
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible: " + invincibleCounter, 10, 400);
	}

}
