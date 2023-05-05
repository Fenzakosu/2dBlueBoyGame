package utility;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;

import boss_monster.BMON_SkeletonLord;
import entity.Entity;
import entity.PlayerDummy;
import main.GamePanel;
import object.OBJ_BlueHeart;
import object.OBJ_Door_Iron;

public class CutsceneManager {

	GamePanel gp;
	Graphics2D g2;
	public int sceneNum;
	public int scenePhase;
	// COUNTER FOR DARKENING THE SCREEN (WAITING PERIOD)
	int counter = 0;
	float alpha = 0F;
	int y;
	String endCredits;

	// SCENE NUMBER
	public final int NA = 0;
	public final int SKELETON_LORD = 1;
	public final int ENDING = 2;

	public CutsceneManager(GamePanel gp) {
		this.gp = gp;

		endCredits = "Program/Music/Art\n" + "Fenzakosu"
				+ "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + "Special Thanks\n"
				+ "Someone\n" + "Someone\n" + "Someone\n" + "Someone\n\n\n\n\n\n\n\n"
				+ "Thank you for playing!";
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;

		switch (sceneNum) {
		case SKELETON_LORD:
			scene_skeletonLord();
			break;
		case ENDING:
			scene_ending();
			break;
		default:
			break;
		}

	}

	public void scene_skeletonLord() {

		// FIRST PHASE
		if (scenePhase == 0) {
			gp.bossBattleOn = true;

			// SHUT THE IRON DOORS
			for (int i = 0; i < gp.objs[1].length; i++) {

				if (gp.objs[gp.currentMap][i] == null) {
					gp.objs[gp.currentMap][i] = new OBJ_Door_Iron(gp);
					gp.objs[gp.currentMap][i].worldX = gp.TILE_SIZE * 25;
					gp.objs[gp.currentMap][i].worldY = gp.TILE_SIZE * 28;
					// THESE IRON DOORS ARE ONLY AVAILABLE DURING BOSS FIGHT,
					// HENCE TEMP BOOLEAN VARIABLE
					gp.objs[gp.currentMap][i].isTemp = true;
					gp.playSE(21);
					break;
				}
			}
			// SEARCH A VACANT SLOT FOR THE PLAYER DUMMY
			for (int i = 0; i < gp.npcs[1].length; i++) {

				if (gp.npcs[gp.currentMap][i] == null) {
					gp.npcs[gp.currentMap][i] = new PlayerDummy(gp);
					gp.npcs[gp.currentMap][i].worldX = gp.player.worldX;
					gp.npcs[gp.currentMap][i].worldY = gp.player.worldY;
					gp.npcs[gp.currentMap][i].direction = gp.player.direction;
					break;
				}
			}
			// STOP DRAWING PLAYER IMAGES
			gp.player.drawing = false;
			// MOVE TO NEXT PHASE
			scenePhase++;
		}
		if (scenePhase == 1) {
			gp.player.worldY -= 2;
			// STOP THE CAMER WHEN IT REACHES SECOND IRON DOOR
			// AND MOVE TO NEXT PHASE
			if (gp.player.worldY < gp.TILE_SIZE * 16) {
				scenePhase++;
			}
		}
		// WAKE UP THE BOSS
		if (scenePhase == 2) {

			// SEARCH THE MONSTER ARRAY FOR BOSS MONSTER
			for (int i = 0; i < gp.monsters[1].length; i++) {

				Entity bossMonster = gp.monsters[gp.currentMap][i];

				if (bossMonster != null
						&& bossMonster.name.equals(BMON_SkeletonLord.BOSS_NAME)) {
					// CHANGE BOSS' SLEEP STATE
					bossMonster.sleep = false;
					// PASS BOSS OBJECT INTO NPC ENTITY FOR HIM TO SPEAK
					gp.ui.npc = bossMonster;
					scenePhase++;
					break;
				}
			}
		}
		// LETTING THE BOSS MONSTER SPEAK
		if (scenePhase == 3) {
			// DRAW THE DIALOGUE SCREEN
			gp.ui.drawDialogueScreen();
		}
		if (scenePhase == 4) {
			// RETURN CAMERA VIEW TO THE PLAYER

			// SEARCH FOR THE DUMMY
			for (int i = 0; i < gp.npcs[1].length; i++) {

				if (gp.npcs[gp.currentMap][i] != null
						&& gp.npcs[gp.currentMap][i].name
								.equals(PlayerDummy.NPC_NAME)) {
					// RESTORE THE PLAYER POSITION
					gp.player.worldX = gp.npcs[gp.currentMap][i].worldX;
					gp.player.worldY = gp.npcs[gp.currentMap][i].worldY;
					// DELETE THE DUMMY
					gp.npcs[gp.currentMap][i] = null;
					break;
				}
			}
			// START DRAWING THE PLAYER AGAIN
			gp.player.drawing = true;

			// RESET
			sceneNum = 0;
			scenePhase = 0;
			gp.gameState = gp.playState;

			// BOSS BATTLE MUSIC
			gp.stopMusic();
			gp.playMusic(22);
		}
	}

	// CUTSCENE WHEN PICKING UP BLUE HEART
	public void scene_ending() {
		if (scenePhase == 0) {

			gp.stopMusic();
			// SET BLUE HEART AS NPC
			gp.ui.npc = new OBJ_BlueHeart(gp);
			// NEXT PHASE
			scenePhase++;
		}
		if (scenePhase == 1) {

			// DISPLAY DIALOGUES
			gp.ui.drawDialogueScreen();
		}
		if (scenePhase == 2) {

			// PLAY THE FANFARE SOUND
			gp.playSE(4);
			scenePhase++;
		}
		if (scenePhase == 3) {

			// WAIT UNTIL THE SOUND EFFECT ENDS (5 seconds)
			if (counterReached(300) == true) {
				scenePhase++;
			}
		}
		if (scenePhase == 4) {

			// SCREEN GETS DARKER
			alpha += 0.005F;
			if (alpha > 1f) {
				alpha = 1f;
			}
			// DRAW BLACK BACKGROUND GRADUALLY
			drawBlackBackground(alpha);

			if (alpha == 1f) {
				alpha = 0;
				scenePhase++;
			}

		}
		if (scenePhase == 5) {
			// DRAW BLACK BACKGROUND INSTANTLY
			drawBlackBackground(1F);
			alpha += 0.005F;
			if (alpha > 1f) {
				alpha = 1f;
			}
			// NARRATIVE MESSAGE DISPLAY
			String text = "After the fierce battle with the Skeleton Lord,\n"
					+ "the Blue boy finally found legendary treasure. \n"
					+ "But this is not the end of his journey. \n"
					+ "His adventure has only just begun.";
			drawString(alpha, 38F, 200, text, 70);
			if (counterReached(600) == true) {
				gp.playMusic(0);
				scenePhase++;
			}
		}
		if (scenePhase == 6) {
			drawBlackBackground(1F);

			drawString(1F, 120F, gp.SCREEN_HEIGHT / 2, "Blue Boy Adventure", 40);
			if (counterReached(480) == true) {
				scenePhase++;
			}
		}
		if (scenePhase == 7) {
			drawBlackBackground(1F);
			y = gp.SCREEN_HEIGHT / 2;
			drawString(1F, 38F, y, endCredits, 40);
			
			if (counterReached(480) == true) {
				scenePhase++;
			}
		}
		if (scenePhase == 8) {
			drawBlackBackground(1F);
			
			// Scrolling the credits
			y--;
			drawString(1F, 38F, y, endCredits, 40);
		}
	}

	public boolean counterReached(int target) {

		boolean counterReached = false;

		counter++;
		if (counter > target) {
			counterReached = true;
			counter = 0;
		}

		return counterReached;

	}

	public void drawBlackBackground(float alpha) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

	}

	public void drawString(float alpha, float fontSize, int y, String text,
			int lineHeight) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(fontSize));

		for (String line : text.split("\n")) {
			int x = gp.ui.getXforCenteredText(line);
			g2.drawString(line, x, y);
			y += lineHeight;
		}
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

	}

}
