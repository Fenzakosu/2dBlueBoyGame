package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import projectile.Projectile;
import utility.UtilityTool;

public class Entity {

	GamePanel gp;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1,
			attackLeft2, attackRight1, attackRight2;
	public BufferedImage image1, image2, image3;
	String dialogues[] = new String[20];
	public int solidAreaDefaultX, solidAreaDefaultY;
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public boolean collision = false;

	// STATE
	public int worldX, worldY;
	public String direction = "down";
	public int spriteNum = 1;
	int dialogueIndex = 0;
	public boolean collisionOn = false;
	public boolean isInvincible = false;
	boolean isAttacking = false;
	public boolean isAlive = true;
	public boolean isDying = false;
	boolean hpBarOn = false;
	public boolean onPath = false;
	public boolean knockbackOn = false;

	// COUNTER
	public int actionLockCounter = 0;
	public int dyingCounter = 0;
	public int spriteCounter = 0;
	public int invincibleCounter = 0;
	public int shotCounter = 0;
	int hpBarCounter;
	public int aggressiveCounter;
	public int knockbackCounter = 0;

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

	public Entity(GamePanel gp) {
		this.gp = gp;
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

	public void setAction() {

	}

	public void reactToDamage() {

	}

	public void speak() {
		if (dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;

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

		if (knockbackOn == true) {
			checkCollision();

			if (collisionOn == true) {
				knockbackCounter = 0;
				knockbackOn = false;
				speed = defaultSpeed;
			} else if (collisionOn == false) {
				switch (gp.player.direction) {
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

		} else {
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
	}

	public void damagePlayer(int attack) {
		if (gp.player.isInvincible == false) {
			// PLAYER CAN RECIEVE DAMAGE
			gp.playSE(6);

			int monsterDamage = attack - gp.player.defense;
			if (monsterDamage < 0) {
				monsterDamage = 0;
			}

			gp.player.life -= monsterDamage;

			gp.player.isInvincible = true;
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;

		int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
		int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

		if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X
				&& worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X
				&& worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y
				&& worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {

			switch (direction) {
			case "up":
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
				break;
			}

			// MONSTER HEALTH BAR
			if (type == TYPE_MONSTER && hpBarOn == true) {

				double oneScale = (double) gp.TILE_SIZE / maxLife;
				double hpBarValue = oneScale * life;

				// OUTILINE OF HEALTH BAR(SLIGHTLY LARGER)
				g2.setColor(new Color(35, 35, 35));
				g2.fillRect(screenX - 1, screenY - 16, gp.TILE_SIZE + 2, 12);
				// HEALTH BAR
				g2.setColor(new Color(255, 0, 30));
				g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

				hpBarCounter++;

				if (hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}

			if (isInvincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.4F);
			}

			if (isDying == true) {
				dyingAnimation(g2);
			}

			g2.drawImage(image, screenX, screenY, null);

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
			// IF ENTITY REACHES THE GOAL, STOP THE SEARCH
			// NPC PATHFINDING FOR TRAVELING TO A SPECIFIC POINT
			// (NOT USED FOR FOLLOWING A PLAYER!)
//			int nextCol = gp.pFinder.pathList.get(0).col;
//			int nextRow = gp.pFinder.pathList.get(0).row;
//			if (nextCol == goalCol && nextRow == goalRow) {
//			onPath = false;
//			}
		}
	}

	public int getDetected(Entity user, Entity targets[][], String targetName) {

		int index = 999;

		// CHECK OBJECT IN THE SURROUNDING AREA
		int nextWorldX = user.getLeftX();
		int nextWorldY = user.getTopY();

		switch (user.direction) {
		case "up":
			nextWorldY = user.getTopY() - 1;
			break;
		case "down":
			nextWorldY = user.getBottomY() + 1;
			break;
		case "left":
			nextWorldX = user.getLeftX() - 1;
			break;
		case "right":
			nextWorldX = user.getRightX() + 1;
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

}
