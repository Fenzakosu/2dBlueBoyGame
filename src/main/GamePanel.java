package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ai.PathFinder;
import configuration.Config;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import environment.EnvironmentManager;
import map.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;
import utility.AssetSetter;
import utility.CollisionChecker;
import utility.KeyHandler;

public class GamePanel extends JPanel implements Runnable {

	// SCREEN SETTINGS
	final int ORIGINAL_TILE_SIZE = 16; // 16x16 sized tile
	final int SCALE = 3;
	public final int TILE_SIZE = ORIGINAL_TILE_SIZE * SCALE; // 48 * 48 tile
	public final int MAX_SCREEN_COL = 20;
	public final int MAX_SCREEN_ROW = 12;
	public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;// 760 pixels
	public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;// 576 pixels

	// FOR FULLSCREEN
	int screenWidth2 = SCREEN_WIDTH;
	int screenHeight2 = SCREEN_HEIGHT;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullscreenOn = false;

	// WORLD SETTINGS
	public int maxWorldCol;
	public int maxWorldRow;
	public final int MAX_MAP = 20;
	public int currentMap = 0;
	// FPS
	public int fps = 60;

	// SYSTEM
	public TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);
	public Sound music = new Sound();
	public Sound se = new Sound();
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);
	Config config = new Config(this);
	public PathFinder pFinder = new PathFinder(this);
	EnvironmentManager envManager = new EnvironmentManager(this);
	public Map map = new Map(this);
	public SaveLoad saveLoad = new SaveLoad(this);
	Thread gameThread;

	// ENTITIES
	public Player player = new Player(this, keyH);
	public Entity objs[][] = new Entity[MAX_MAP][20];
	public Entity npcs[][] = new Entity[MAX_MAP][10];
	public Entity monsters[][] = new Entity[MAX_MAP][20];
	public InteractiveTile iTiles[][] = new InteractiveTile[MAX_MAP][50];
	public Entity projectiles[][] = new Entity[MAX_MAP][20];
//	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();

	// GAME STATE
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int charSheetState = 4;
	public final int optionsState = 5;
	public final int gameOverState = 6;
	public final int transitionState = 7;
	public final int tradeState = 8;
	public final int sleepState = 9;
	public final int mapState = 10;

	public GamePanel() {
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	// Setting up items (objects)
	public void setupGame() {
		aSetter.setObjects();
		aSetter.setNPC();
		aSetter.setMonsters();
		aSetter.setInteractiveTiles();
		envManager.setup();
//		playMusic(0);
		gameState = titleState;
		// FULLSCREEN
		tempScreen = new BufferedImage(SCREEN_WIDTH, SCREEN_HEIGHT,
				BufferedImage.TYPE_INT_ARGB);
		g2 = (Graphics2D) tempScreen.getGraphics();

		if (fullscreenOn == true) {
			setFullscreen();
		}

	}

	public void resetGame(boolean restart) {
		player.setDefaultPositions();
		player.restoreStatus();
		aSetter.setNPC();
		aSetter.setMonsters();
		player.resetCounters();

		if (restart == true) {
			player.setDefaultValues();
			aSetter.setInteractiveTiles();
			aSetter.setObjects();
			envManager.lighting.resetDay();
		}
	}

	public void setFullscreen() {

		// GET LOCAL SCREEN DEVICE
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);

		// GET FULL SCREEN WIDTH AND HEIGHT
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void run() {

		double drawInterval = 1000000000 / fps;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		long drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);

			lastTime = currentTime;

			if (delta >= 1) {
				update();
				drawToTempScreen(); // DRAW EVERYTHING TO BUFFERED IMAGE
				drawToScreen();// DRAW THE BUFFERED IMAGE TO THE SCREEN
				delta--;
				drawCount++;
			}

			if (timer >= 1000000000) {
//				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}

		}
	}

	public void update() {
		if (gameState == playState) {
			// PLAYER
			player.update();

			// NPC
			for (int i = 0; i < npcs[1].length; i++) {
				if (npcs[currentMap][i] != null) {
					npcs[currentMap][i].update();
				}
			}

			// MONSTERS
			for (int i = 0; i < monsters[1].length; i++) {
				if (monsters[currentMap][i] != null) {
					if (monsters[currentMap][i].isAlive == true
							&& monsters[currentMap][i].isDying == false) {
						monsters[currentMap][i].update();
					}
					if (monsters[currentMap][i].isAlive == false) {
						monsters[currentMap][i].checkDrop();
						monsters[currentMap][i] = null;
					}
				}
			}
			// PROJECTILES
			for (int i = 0; i < projectiles[1].length; i++) {
				if (projectiles[currentMap][i] != null) {
					if (projectiles[currentMap][i].isAlive == true) {
						projectiles[currentMap][i].update();
					}
					if (projectiles[currentMap][i].isAlive == false) {
						projectiles[currentMap][i] = null;
					}
				}
			}
			// PARTICLES
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					if (particleList.get(i).isAlive == true) {
						particleList.get(i).update();
					}
					if (particleList.get(i).isAlive == false) {
						particleList.remove(i);
					}
				}
			}
			// INTERACTIVE TILES
			for (int i = 0; i < iTiles[1].length; i++) {
				if (iTiles[currentMap][i] != null) {
					iTiles[currentMap][i].update();
				}
			}
			// LIGHTING
			envManager.update();
		}
		if (gameState == pauseState) {
			// TODO:NOTHING FOR NOW
		}
	}

	// FULLSCREEN
	public void drawToTempScreen() {
		// DEBUG
		long drawStart = 0;
		if (keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}

		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);
		}

		// MAP SCREEN
		else if (gameState == mapState) {
			map.drawFullMapScreen(g2);
		}
		// OTHERS
		else {

			// TILES
			tileM.draw(g2);
			// INTERACTIVE TILES
			for (int i = 0; i < iTiles[1].length; i++) {
				if (iTiles[currentMap][i] != null) {
					iTiles[currentMap][i].draw(g2);
				}
			}
			// ADD PLAYER TO ENTITY LIST
			entityList.add(player);
			// ADD NPCS TO ENTITY LIST
			for (int i = 0; i < npcs[1].length; i++) {
				if (npcs[currentMap][i] != null) {
					entityList.add(npcs[currentMap][i]);
				}
			}
			// ADD OBJECTS TO ENTITY LIST
			for (int i = 0; i < objs[1].length; i++) {
				if (objs[currentMap][i] != null) {
					entityList.add(objs[currentMap][i]);
				}
			}

			// ADD MONSTERS TO ENTITY LIST
			for (int i = 0; i < monsters[1].length; i++) {
				if (monsters[currentMap][i] != null) {
					entityList.add(monsters[currentMap][i]);
				}
			}
			// ADD PROJECTILES TO ENTITY LIST
			for (int i = 0; i < projectiles[1].length; i++) {
				if (projectiles[currentMap][i] != null) {
					entityList.add(projectiles[currentMap][i]);
				}
			}
			// ADD PARTICLES TO ENTITY LIST
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					entityList.add(particleList.get(i));
				}
			}

			// SORT
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
			});

			// DRAW ENTITIES
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			// EMPTY THE ENTITY LIST
			entityList.clear();

			// ENVIRONMENT EFFECTS (LIGHTNING ...)
			envManager.draw(g2);

			// MINIMAP
			map.drawMiniMap(g2);
			// UI
			ui.draw(g2);
		}

		// DEBUG
		if (keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setFont(new Font("Arial", Font.PLAIN, 20));
			g2.setColor(Color.white);
			int x = 10;
			int y = 400;
			int lineHeight = 20;
			y += lineHeight;
			g2.drawString("WorldX: " + player.worldX, x, y);
			y += lineHeight;
			g2.drawString("WorldY: " + player.worldY, x, y);
			y += lineHeight;
			g2.drawString("Col: " + (player.worldX + player.solidArea.x) / TILE_SIZE,
					x, y);
			y += lineHeight;
			g2.drawString("Row: " + (player.worldY + player.solidArea.y) / TILE_SIZE,
					x, y);
			y += lineHeight;
			g2.drawString("Draw Time: " + passed, 10, 400);

		}
	}

	public void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
		g.dispose();
	}

	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}

	public void stopMusic() {
		music.stop();
	}

	// PLAY SOUND EFFECT
	public void playSE(int i) {
		se.setFile(i);
		se.play();
	}
}
