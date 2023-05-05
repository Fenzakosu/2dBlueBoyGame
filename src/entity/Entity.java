package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import projectile.Projectile;
import utility.UtilityTool;

public class Entity {

	GamePanel gp;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1,
			attackLeft2, attackRight1, attackRight2, guardUp, guardDown, guardLeft,
			guardRight;
	public BufferedImage image1, image2, image3;
	public String dialogues[][] = new String[20][20];
	public int solidAreaDefaultX, solidAreaDefaultY;
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public boolean collision = false;
	public Entity attacker;
	public Entity linkedEntity;
	public boolean isTemp = false;

	// STATE
	public int worldX, worldY;
	public String direction = "down";
	public int spriteNum = 1;
	public int dialogueSet = 0;
	public int dialogueIndex = 0;
	public boolean collisionOn = false;
	public boolean isInvincible = false;
	public boolean isAttacking = false;
	public boolean isAlive = true;
	public boolean isDying = false;
	public boolean hpBarOn = false;
	public boolean onPath = false;
	public boolean knockbackOn = false;
	public String knockbackDIrection;
	public boolean guardingOn = false;
	public boolean isTransparent = false;
	public boolean offBalanceOn = false;
	public Entity loot;
	public boolean isOpen = false;
	public boolean isEnraged = false;
	public boolean isBoss = false;
	public boolean sleep = false;
	public boolean drawing = true;

	// COUNTER
	public int actionLockCounter = 0;
	public int dyingCounter = 0;
	public int spriteCounter = 0;
	public int invincibleCounter = 0;
	public int shotCounter = 0;
	public int hpBarCounter;
	public int knockbackCounter = 0;
	public int guardCounter = 0;
	int offBalanceCounter = 0;

	// CHARACTER ATTRIBUTES
	public String name;
	public int speed;
	public int defaultSpeed;
	public int maxLife;
	public int life;
	public int maxMana;
	public int mana;
	public int ammo;
	public int level;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int expPoints;
	public int nextLevelExp;
	public int coin;
	public int motion1Duration;
	public int motion2Duration;
	public Entity currentWeapon;
	public Entity currentShield;
	public Entity currentLight;
	public Projectile projectile;

	// ITEM ATTRIBUTES
	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int MAX_INVENTORY_SLOTS = 20;
	public int value;
	public int attackValue;
	public int defenseValue;
	public String description = "";
	public int useCost;
	public int price;
	public int knockbackPower = 0;
	public boolean isStackable = false;
	public int amount = 1;
	public int lightRadius;

	// TYPES
	public int type; // 0 = player, 1 = npc, 2 = monster
	public final int TYPE_PLAYER = 0;
	public final int TYPE_NPC = 1;
	public final int TYPE_MONSTER = 2;
	public final int TYPE_SWORD = 3;
	public final int TYPE_AXE = 4;
	public final int TYPE_SHIELD = 5;
	public final int TYPE_CONSUMABLE = 6;
	public final int TYPE_PICKUP_ONLY = 7;
	public final int TYPE_OBSTACLE = 8;
	public final int TYPE_LIGHT = 9;
	public final int TYPE_PICKAXE = 10;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public int getScreenX() {
		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
		return screenX;
	}

	public int getScreenY() {
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;
		return screenY;
	}

	public int getLeftX() {
		return worldX + solidArea.x;
	}

	public int getRightX() {
		return worldX + solidArea.x + solidArea.width;
	}

	public int getTopY() {
		return worldY + solidArea.y;
	}

	public int getBottomY() {
		return worldY + solidArea.y + solidArea.height;
	}

	public int getCol() {
		return (worldX + solidArea.x) / gp.TILE_SIZE;
	}

	public int getRow() {
		return (worldY + solidArea.y) / gp.TILE_SIZE;
	}

	// GET X COORDINATE OF ENTITY'S IMAGE
	public int getCenterX() {
		int centerX = worldX + left1.getWidth() / 2;
		return centerX;
	}

	// GET Y COORDINATE OF ENTITY'S IMAGE
	public int getCenterY() {
		int centerY = worldY + up1.getHeight() / 2;
		return centerY;
	}

	public int getXdistance(Entity target) {
		int xDistance = Math.abs(getCenterX() - target.getCenterX());
		return xDistance;
	}

	public int getYdistance(Entity target) {
		int yDistance = Math.abs(getCenterY() - target.getCenterY());
		return yDistance;
	}

	public int getTileDistance(Entity target) {
		int tileDistance = (getXdistance(target) + getYdistance(target))
				/ gp.TILE_SIZE;
		return tileDistance;
	}

	public int getGoalCol(Entity target) {
		int goalCol = (gp.player.worldX + target.solidArea.x) / gp.TILE_SIZE;
		return goalCol;
	}

	public int getGoalRow(Entity target) {
		int goalRow = (gp.player.worldY + target.solidArea.y) / gp.TILE_SIZE;
		return goalRow;
	}

	public void resetCounters() {
		actionLockCounter = 0;
		dyingCounter = 0;
		spriteCounter = 0;
		invincibleCounter = 0;
		shotCounter = 0;
		hpBarCounter = 0;
		knockbackCounter = 0;
		guardCounter = 0;
		offBalanceCounter = 0;
	}

	public void setLoot(Entity loot) {

	}

	public void setAction() {

	}

	public void move(String direction) {

	}

	public void reactToDamage() {

	}

	public void speak() {

	}

	public void facePlayer() {
		switch (gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}
	}

	public void startDialogue(Entity entity, int setNum) {
		gp.gameState = gp.dialogueState;
		gp.ui.npc = entity;
		dialogueSet = setNum;
	}

	public void checkDrop() {

	}

	public void dropItem(Entity droppedItem) {

		for (int i = 0; i < gp.objs[1].length; i++) {
			if (gp.objs[gp.currentMap][i] == null) {
				gp.objs[gp.currentMap][i] = droppedItem;
				gp.objs[gp.currentMap][i].worldX = worldX; // THE DEAD MONSTER'S
															// WORLDX
				gp.objs[gp.currentMap][i].worldY = worldY;// THE DEAD MONSTER'S
															// WORLDY
				break;
			}
		}

	}

	public Color getParticleColor() {
		Color color = null;
		return color;
	}

	public int getParticleSize() {
		int size = 0;
		return size;
	}

	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}

	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}

	public void generateParticle(Entity generator, Entity target) {
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		// TOP LEFT
		Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
		// TOP RIGHT
		Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
		// DOWN LEFT
		Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
		// DOWN RIGHT
		Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);

	}

	public void interact() {

	}

	public boolean use(Entity entity) {
		return false;
	}

	public void checkCollision() {
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npcs);
		gp.cChecker.checkEntity(this, gp.monsters);
		gp.cChecker.checkEntity(this, gp.iTiles);

		// CHECK IF ENTITY IS A MONSTER (TYPE = 2) AND TOUCHING THE PLAYER
		boolean isTouchingPl = gp.cChecker.checkPlayer(this);
		if (this.type == TYPE_MONSTER && isTouchingPl == true) {
			damagePlayer(attack);
		}
	}

	public void update() {

		if (sleep == false) {
			if (knockbackOn == true) {
				checkCollision();

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

			} else if (isAttacking == true) {
				attacking();
			}

			else {
				setAction();
				checkCollision();

				// IF COLLISION IS FALSE , PLAYER CAN MOVE
				if (collisionOn == false) {
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
				spriteCounter++;

				if (spriteCounter > 24) {
					if (spriteNum == 1) {
						spriteNum = 2;
					} else if (spriteNum == 2) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
			}

			if (isInvincible == true) {
				invincibleCounter++;
				// GENERAL ENTITY INVULNERABILITY TIMER IS SHORTER THAN PLAYER'S
				if (invincibleCounter > (gp.fps * 2) / 3) {
					isInvincible = false;
					invincibleCounter = 0;
				}
			}

			if (shotCounter < 30) {
				shotCounter++;
			}
			// OFF BALANCE COUNTER
			if (offBalanceOn == true) {
				offBalanceCounter++;
				if (offBalanceCounter > 60) {
					offBalanceOn = false;
					offBalanceCounter = 0;
				}
			}
		}

	}

	public void moveTowardPlayer(int interval) {
		actionLockCounter++;

		if (actionLockCounter > interval) {
			if (getXdistance(gp.player) > getYdistance(gp.player)) {
				if (gp.player.getCenterX() < getCenterX()) {
					direction = "left";
				} else {
					direction = "right";
				}
			} else if (getXdistance(gp.player) < getYdistance(gp.player)) {
				if (gp.player.getCenterY() < getCenterY()) {
					direction = "up";
				} else {
					direction = "down";
				}
			}
			actionLockCounter = 0;
		}
	}

	public String getOppositeDirection(String direction) {

		String oppositeDirection = "";

		switch (direction) {
		case "up":
			oppositeDirection = "down";
			break;
		case "down":
			oppositeDirection = "up";
			break;
		case "left":
			oppositeDirection = "right";
			break;
		case "right":
			oppositeDirection = "left";
			break;
		}

		return oppositeDirection;
	}

	public void attacking() {

		spriteCounter++;
		// START OF ATTACK ANIMATION
		if (spriteCounter <= motion1Duration) {
			spriteNum = 1;
		} // ATTACK SWING ANIMATION
		if (spriteCounter > motion1Duration && spriteCounter < motion2Duration) {
			spriteNum = 2;

			// CHECK IF ATTACK LANDED ON A MONSTER
			// SAVE THE CURRENT WORLDX, WORLDY,SOLIDAREA
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;

			// ADJUST PLAYER'S WORLDX/Y FOR THE ATTACKAREA
			switch (direction) {
			case "up":
				worldY -= attackArea.height;
				break;
			case "down":
				worldY += attackArea.height;
				break;
			case "left":
				worldX -= attackArea.width;
				break;
			case "right":
				worldX += attackArea.width;
				break;
			}
			// ATTACKAREA BECOMES SOLIDAREA
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;

			if (type == TYPE_MONSTER) {
				if (gp.cChecker.checkPlayer(this) == true) {
					damagePlayer(attack);
				}
			} else {
				// PLAYER
				// CHECK MONSTER COLLISION WITH THE UPDATED WORLDX, WORLDY AND
				// SOLIDAREA
				int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
				gp.player.damageMonster(monsterIndex, this, attack,
						currentWeapon.knockbackPower);

				int iTileIndex = gp.cChecker.checkEntity(this, gp.iTiles);
				gp.player.damageInteractiveTile(iTileIndex);

				int projectileIndex = gp.cChecker.checkEntity(this, gp.projectiles);
				gp.player.damageProjectile(projectileIndex);
			}

			// RESTORE ORIGINAL POSITION AND SOLIDAREA
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if (spriteCounter > motion2Duration) {
			spriteNum = 1;
			spriteCounter = 0;
			isAttacking = false;
		}
	}

	public void checkStartChasingOrNot(Entity target, int distance, int rate) {
		if (getTileDistance(target) < distance) {
			int i = new Random().nextInt(rate);
			if (i == 0) {
				onPath = true;
			}
		}
	}

	public void checkStopChasingOrNot(Entity target, int distance, int rate) {
		if (getTileDistance(target) > distance) {
			int i = new Random().nextInt(rate);
			if (i == 0) {
				onPath = false;
			}
		}
	}

	public void checkAttackingOrNot(int rate, int straight, int horizontal) {

		boolean targetInRange = false;
		int xDis = getXdistance(gp.player);
		int yDis = getYdistance(gp.player);

		switch (direction) {
		case "up":
			if (gp.player.getCenterY() < getCenterY() && yDis < straight
					&& xDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "down":
			if (gp.player.getCenterY() > getCenterY() && yDis < straight
					&& xDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "left":
			if (gp.player.getCenterX() < getCenterX() && xDis < straight
					&& yDis < horizontal) {
				targetInRange = true;
			}
			break;
		case "right":
			if (gp.player.getCenterX() > getCenterX() && xDis < straight
					&& yDis < horizontal) {
				targetInRange = true;
			}
			break;
		}

		if (targetInRange == true) {
			// CHECK IF IT INITIATES AN ATTACK
			int i = new Random().nextInt(rate);
			if (i == 0) {
				isAttacking = true;
				spriteNum = 1;
				spriteCounter = 0;
				shotCounter = 0;
			}
		}
	}

	public void checkShootingOrNot(int rate, int shotInterval) {
		int i = new Random().nextInt(rate);
		if (i == 0 && projectile.isAlive == false && shotCounter == shotInterval) {
			projectile.set(worldX, worldY, direction, true, this);

			// CHECK VACANCY
			for (int j = 0; j < gp.projectiles[1].length; j++) {
				if (gp.projectiles[gp.currentMap][j] == null) {
					gp.projectiles[gp.currentMap][j] = projectile;
					break;
				}
			}
			shotCounter = 0;
		}
	}

	public void getRandomDirection(int interval) {
		actionLockCounter++;

		if (actionLockCounter > interval) {

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

	public void damagePlayer(int attack) {
		if (gp.player.isInvincible == false) {
			int damage = attack - gp.player.defense;
			// PLAYER CAN RECIEVE DAMAGE

			// GET AN OPPOSITE DIRECTION OF THIS ATTACKER
			String canGuardDirection = getOppositeDirection(direction);

			if (gp.player.guardingOn == true
					&& gp.player.direction.equals(canGuardDirection)) {

				// PARRY (CHECK GUARD COUNTER)
				if (gp.player.guardCounter < 10) {
					damage = 0;
					gp.playSE(16);
					// KNOCKBACK THE ATTACKER
					knockback(this, gp.player, knockbackPower);
					offBalanceOn = true;
					spriteCounter = -60;
				} else {
					damage /= 3;
					gp.playSE(15);
				}
			} else {
				gp.playSE(6);
				if (damage < 1) {
					damage = 1;
				}
			}
			if (damage != 0) {
				if (damage < 0) {
					damage = 0;
				}
				isTransparent = true;
				knockback(gp.player, this, knockbackPower);
			}

			gp.player.life -= damage;

			gp.player.isInvincible = true;
		}
	}

	public boolean inCamera() {
		boolean isInCamera = false;
		if (worldX + gp.TILE_SIZE * 5 > gp.player.worldX - gp.player.SCREEN_X
				&& worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X
				&& worldY + gp.TILE_SIZE * 5 > gp.player.worldY - gp.player.SCREEN_Y
				&& worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
			isInCamera = true;
		}
		return isInCamera;
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		if (inCamera() == true) {

			int tempScreenX = getScreenX();
			int tempScreenY = getScreenY();

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
					tempScreenY = getScreenY() - up1.getHeight();
					if (spriteNum == 1) {
						image = attackUp1;
					}
					if (spriteNum == 2) {
						image = attackUp2;
					}
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
					tempScreenX = getScreenX() - left1.getWidth();
					if (spriteNum == 1) {
						image = attackLeft1;
					}
					if (spriteNum == 2) {
						image = attackLeft2;
					}
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
				break;
			}

			if (isInvincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.4F);
			}

			if (isDying == true) {
				dyingAnimation(g2);
			}

			g2.drawImage(image, tempScreenX, tempScreenY, null);

			changeAlpha(g2, 1F);
		}
	}

	public void dyingAnimation(Graphics2D g2) {
		dyingCounter++;

		int i = 5;

		if (dyingCounter <= i) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i && dyingCounter <= i * 2) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 8) {
			isAlive = false;
		}

	}

	public void changeAlpha(Graphics2D g2, float alphaValue) {
		g2.setComposite(
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}

	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image, scaledImage = null;
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			scaledImage = uTool.scaleImage(image, width, height);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scaledImage;
	}

	public void searchPath(int goalCol, int goalRow) {

		int startCol = (worldX + solidArea.x) / gp.TILE_SIZE;
		int startRow = (worldY + solidArea.y) / gp.TILE_SIZE;

		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);

		if (gp.pFinder.search() == true) {

			// NEXT worldX & worldY
			int nextX = gp.pFinder.pathList.get(0).col * gp.TILE_SIZE;
			int nextY = gp.pFinder.pathList.get(0).row * gp.TILE_SIZE;
			// Entity's solidArea POSITION
			int enLeftX = worldX + solidArea.x;
			int enRightX = worldX + solidArea.x + solidArea.width;
			int enTopY = worldY + solidArea.y;
			int enBottomY = worldY + solidArea.y + solidArea.height;

			if (enTopY > nextY && enLeftX >= nextX
					&& enRightX < nextX + gp.TILE_SIZE) {
				direction = "up";
			} else if (enTopY < nextY && enLeftX >= nextX
					&& enRightX < nextX + gp.TILE_SIZE) {
				direction = "down";
			} else if (enTopY >= nextY && enBottomY < nextY + gp.TILE_SIZE) {
				// LEFT OR RIGHT
				if (enLeftX > nextX) {
					direction = "left";
				}
				if (enLeftX < nextX) {
					direction = "right";
				}
			}
			// STUCK EXCEPTION (UPPER RIGHT)
			else if (enTopY > nextY && enLeftX > nextX) {
				// UP OR LEFT
				direction = "up";
				checkCollision();
				if (collisionOn == true) {
					direction = "left";
				}
			}
			// STUCK EXCEPTION (UPPER LEFT)
			else if (enTopY > nextY && enLeftX < nextX) {
				// UP OR RIGHT
				direction = "up";
				checkCollision();
				if (collisionOn == true) {
					direction = "right";
				}
			}
			// STUCK EXCEPTION (LOWER RIGHT)
			else if (enTopY < nextY && enLeftX > nextX) {
				// DOWN OR LEFT
				direction = "down";
				checkCollision();
				if (collisionOn == true) {
					direction = "left";
				}
			} // STUCK EXCEPTION (LOWER LEFT)
			else if (enTopY < nextY && enLeftX < nextX) {
				// DOWN OR RIGHT
				direction = "down";
				checkCollision();
				if (collisionOn == true) {
					direction = "right";
				}
			}

		}
	}

	public int getDetected(Entity user, Entity targets[][], String targetName) {

		int index = 999;

		// CHECK OBJECT IN THE SURROUNDING AREA
		int nextWorldX = user.getLeftX();
		int nextWorldY = user.getTopY();

		switch (user.direction) {
		case "up":
			nextWorldY = user.getTopY() - gp.player.speed;
			break;
		case "down":
			nextWorldY = user.getBottomY() + gp.player.speed;
			break;
		case "left":
			nextWorldX = user.getLeftX() - gp.player.speed;
			break;
		case "right":
			nextWorldX = user.getRightX() + gp.player.speed;
			break;
		}

		int col = nextWorldX / gp.TILE_SIZE;
		int row = nextWorldY / gp.TILE_SIZE;

		for (int i = 0; i < targets[1].length; i++) {
			if (targets[gp.currentMap][i] != null) {
				if (targets[gp.currentMap][i].getCol() == col
						&& targets[gp.currentMap][i].getRow() == row
						&& targets[gp.currentMap][i].name.equals(targetName)) {
					index = i;
					break;
				}
			}
		}
		return index;

	}

	public void knockback(Entity target, Entity attacker, int knockbackPower) {

		this.attacker = attacker;
		target.knockbackDIrection = attacker.direction;
		target.speed += knockbackPower;
		target.knockbackOn = true;
	}
}
