package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class UI {
	GamePanel gp;
	public Font maruMonica, purisaB;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank,
			coin;
	Graphics2D g2;
	public boolean messageOn = false;
	public boolean gameFinished = false;
	ArrayList<String> messages = new ArrayList<>();
	ArrayList<Integer> messagesCounter = new ArrayList<>();
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0; // 0 : the first screen, 1: the second screen
	public int subState = 0;
	public int trCounter = 0; // FOR TRANSITION
	public Entity npc;

	// ROWS AND COLUMNS
	public int playerSlotCol = 0;
	public int playerSlotRow = 0;
	public int npcSlotCol = 0;
	public int npcSlotRow = 0;
	public int numOfCols = 5;
	public int numOfRows = 4;

	public UI(GamePanel gp) {

		this.gp = gp;

		try {
			InputStream is = getClass()
					.getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
			purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// CREATE HUD OBJECT
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image1;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image1;
		crystal_blank = crystal.image2;
		Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
		coin = bronzeCoin.down1;
	}

	public void addMessage(String text) {
		messages.add(text);
		messagesCounter.add(0);
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;
		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		// PLAY STATE
		if (gp.gameState == gp.playState) {
			drawPlayerLife();
			drawMessages();
		}

		// PAUSE STATE
		if (gp.gameState == gp.pauseState) {
			drawPlayerLife();
			drawPauseScreen();
		}
		// DIALOGUE STATE
		if (gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		// CHARACTER SHEET STATE
		if (gp.gameState == gp.charSheetState) {
			drawCharSheet();
			drawInventory(gp.player, true);
		}

		// OPTIONS STATE
		if (gp.gameState == gp.optionsState) {
			drawOptionsScreen();
		}
		// GAME OVER STATE
		if (gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}
		// TRANSITION STATE
		if (gp.gameState == gp.transitionState) {
			drawTransition();
		}
		// TRADE STATE
		if (gp.gameState == gp.tradeState) {
			drawTradeScreen();
		}
		// SLEEP STATE
		if (gp.gameState == gp.sleepState) {
			drawSleepScreen();
		}
	}

	public void drawPlayerLife() {

		int x = gp.TILE_SIZE / 2;
		int y = gp.TILE_SIZE / 2;
		int i = 0;

		// DRAW MAX LIFE
		while (i < gp.player.maxLife / 2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.TILE_SIZE;
		}
		// RESET
		x = gp.TILE_SIZE / 2;
		y = gp.TILE_SIZE / 2;
		i = 0;

		// DRAW CURRENT LIFE
		while (i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if (i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.TILE_SIZE;
		}

		// DRAW MAX MANA
		x = (gp.TILE_SIZE / 2) - 5;
		y = (int) (gp.TILE_SIZE * 1.5);
		i = 0;
		while (i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x += 35;
		}
		// RESET
		x = (gp.TILE_SIZE / 2) - 5;
		y = (int) (gp.TILE_SIZE * 1.5);
		i = 0;
		while (i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x += 35;
		}
	}

	public void drawMessages() {
		int messageX = gp.TILE_SIZE;
		int messageY = gp.TILE_SIZE * 4;

		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 26F));

		for (int i = 0; i < messages.size(); i++) {
			if (messages.get(i) != null) {

				g2.setColor(Color.black);
				g2.drawString(messages.get(i), messageX + 2, messageY + 2);

				g2.setColor(Color.white);
				g2.drawString(messages.get(i), messageX, messageY);

				int counter = messagesCounter.get(i) + 1;// messagesCounter++
				messagesCounter.set(i, counter);// set the counter of integer
												// arraylist
				messageY += gp.TILE_SIZE / 2;

				if (messagesCounter.get(i) > 180) {
					messages.remove(i);
					messagesCounter.remove(i);
				}

			}
		}
	}

	public void drawTitleScreen() {

		if (titleScreenState == 0) {
			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
			String text = "Blue Boy Adventure";
			int x = getXforCenteredText(text);
			int y = gp.TILE_SIZE * 3;

			// TITLE SHADOW
			g2.setColor(Color.gray);
			g2.drawString(text, x + 3, y + 3);

			// TITLE MAIN COLOR
			g2.setColor(Color.blue);
			g2.drawString(text, x, y);

			// BLUE BOY IMAGE
			x = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE * 2) / 2;
			y += gp.TILE_SIZE * 2;
			g2.drawImage(gp.player.down1, x, y, gp.TILE_SIZE * 2, gp.TILE_SIZE * 2,
					null);

			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
			g2.setColor(Color.white);

			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE * 3.5;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}

			text = "LOAD GAME";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}

			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE;
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}
		} else if (titleScreenState == 1) {

			g2.setColor(new Color(0, 0, 0));
			g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

			// CLASS SELECTION SCREEN
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			String text = "Select your class!";
			int x = getXforCenteredText(text);
			int y = gp.TILE_SIZE * 3;
			g2.drawString(text, x, y);

			// FIGHTER
			text = "Fighter";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE * 3;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}

			// THIEF
			text = "Thief";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}
			// MAGE
			text = "Mage";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE;
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}
			// OPTION FOR BACK
			text = "Back";
			x = getXforCenteredText(text);
			y += gp.TILE_SIZE * 2;
			g2.drawString(text, x, y);
			if (commandNum == 3) {
				g2.drawString(">", x - gp.TILE_SIZE, y);
			}
		}

	}

	public void drawPauseScreen() {

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));

		String text = "PAUSED";

		int x = getXforCenteredText(text);

		int y = gp.SCREEN_HEIGHT / 2;

		g2.drawString(text, x, y);

	}

	public void drawDialogueScreen() {

		// DIALOGUE WINDOW
		int x = gp.TILE_SIZE * 3;
		int y = gp.TILE_SIZE / 2;
		int width = gp.SCREEN_WIDTH - (gp.TILE_SIZE * 6);
		int height = gp.TILE_SIZE * 4;

		drawSubWindow(x, y, width, height);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 40F));
		x += gp.TILE_SIZE;
		y += gp.TILE_SIZE;

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}

	}

	public void drawCharSheet() {

		// CREATE A FRAME
		final int FRAME_X = gp.TILE_SIZE;
		final int FRAME_Y = gp.TILE_SIZE;
		final int FRAME_WIDTH = gp.TILE_SIZE * 5;
		final int FRAME_HEIGHT = gp.TILE_SIZE * 10;
		drawSubWindow(FRAME_X, FRAME_Y, FRAME_WIDTH, FRAME_HEIGHT);

		// TEXT
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));

		int textX = FRAME_X + 20;
		int textY = FRAME_Y + gp.TILE_SIZE;
		final int LINE_HEIGHT = 35;

		// CHARACTER STAT NAMES
		g2.drawString("Level", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Life", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Mana", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Strength", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Dexterity", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Attack", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Defense", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Exp", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Next Level", textX, textY);
		textY += LINE_HEIGHT + 10;
		g2.drawString("Coin", textX, textY);
		textY += LINE_HEIGHT + 15;
		g2.drawString("Weapon", textX, textY);
		textY += LINE_HEIGHT;
		g2.drawString("Shield", textX, textY);

		// VALUES
		int tailX = (FRAME_X + FRAME_WIDTH) - 30;
		// RESET textY
		textY = FRAME_Y + gp.TILE_SIZE;
		String value;

		value = String.valueOf(gp.player.level);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;

		value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;

		value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;

		value = String.valueOf(gp.player.strength);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;

		value = String.valueOf(gp.player.dexterity);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;

		value = String.valueOf(gp.player.attack);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;

		value = String.valueOf(gp.player.defense);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;

		value = String.valueOf(gp.player.exp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;

		value = String.valueOf(gp.player.nextLevelExp);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;

		value = String.valueOf(gp.player.coin);
		textX = getXforAlignToRightText(value, tailX);
		g2.drawString(value, textX, textY);
		textY += LINE_HEIGHT;

		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.TILE_SIZE, textY - 24,
				null);
		textY += gp.TILE_SIZE;
		g2.drawImage(gp.player.currentShield.down1, tailX - gp.TILE_SIZE, textY - 24,
				null);
	}

	public void drawInventory(Entity entity, boolean cursor) {

		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotCol = 0;
		int slotRow = 0;

		// PLAYER INVENTORY FRAME
		if (entity == gp.player) {
			frameX = gp.TILE_SIZE * 13;
			frameY = gp.TILE_SIZE;
			frameWidth = gp.TILE_SIZE * (numOfCols + 1);
			frameHeight = gp.TILE_SIZE * (numOfRows + 1);
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		} else {
			// NPC INVENTORY FRAME
			frameX = gp.TILE_SIZE * 2;
			frameY = gp.TILE_SIZE;
			frameWidth = gp.TILE_SIZE * (numOfCols + 1);
			frameHeight = gp.TILE_SIZE * (numOfRows + 1);
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}

		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		// SLOT
		final int SLOT_X_START = frameX + 20;
		final int SLOT_Y_START = frameY + 20;
		int slotX = SLOT_X_START;
		int slotY = SLOT_Y_START;
		int slotSize = gp.TILE_SIZE + 3;

		// DRAW PLAYER'S ITEMS
		for (int i = 0; i < entity.inventory.size(); i++) {

			// EQUIP CURSOR
			if (entity.currentWeapon == entity.inventory.get(i)
					|| entity.currentShield == entity.inventory.get(i)
					|| entity.currentLight == entity.inventory.get(i)) {

				g2.setColor(new Color(240, 190, 90));
				g2.fillRoundRect(slotX, slotY, gp.TILE_SIZE, gp.TILE_SIZE, 10, 10);
			}

			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

			// DISPLAY AMOUNT
			if (entity == gp.player && entity.inventory.get(i).amount > 1) {

				g2.setFont(g2.getFont().deriveFont(32F));
				int amountX;
				int amountY;

				String s = "" + entity.inventory.get(i).amount;
				amountX = getXforAlignToRightText(s, slotX + 44);
				amountY = slotY + gp.TILE_SIZE;

				// SHADOW
				g2.setColor(new Color(60, 60, 60));
				g2.drawString(s, amountX, amountY);
				// NUMBER
				g2.setColor(Color.white);
				g2.drawString(s, amountX - 3, amountY - 3);
			}

			slotX += slotSize;

			if (i == 4 || i == 9 || i == 14) {
				slotX = SLOT_X_START;
				slotY += slotSize;
			}
		}

		// CURSOR
		if (cursor == true) {

			int cursorX = SLOT_X_START + (slotSize * slotCol);
			int cursorY = SLOT_Y_START + (slotSize * slotRow);
			int cursorWidth = gp.TILE_SIZE;
			int cursorHeight = gp.TILE_SIZE;
			// DRAW CURSOR
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

			// ITEMS DESCRIPTION FRAME
			int desFrameX = frameX;
			int desFrameY = frameY + frameHeight;
			int desFrameWidth = frameWidth;
			int desFrameHeight = gp.TILE_SIZE * 3;
			// DRAW DESCRIPTION TEXT
			int textX = desFrameX + 20;
			int textY = desFrameY + gp.TILE_SIZE;
			g2.setFont(g2.getFont().deriveFont(24F));

			int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

			if (itemIndex < entity.inventory.size()) {

				drawSubWindow(desFrameX, desFrameY, desFrameWidth, desFrameHeight);

				for (String line : entity.inventory.get(itemIndex).description
						.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += gp.TILE_SIZE / 2;
				}
			}
		}

	}

	public void drawGameOverScreen() {

		// SCREEN IS BECOMING DARKER
		g2.setColor(new Color(0, 0, 0, 150));
		g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

		text = "Game Over";
		// TEXT SHADOW
		g2.setColor(Color.black);
		x = getXforCenteredText(text);
		y = gp.TILE_SIZE * 4;
		g2.drawString(text, x, y);
		// TEXT
		g2.setColor(Color.white);
		g2.drawString(text, x - 4, y - 4);

		// RETRY
		g2.setFont(g2.getFont().deriveFont(50f));
		text = "Retry";
		x = getXforCenteredText(text);
		y += gp.TILE_SIZE * 4;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.TILE_SIZE, y);
		}

		// BACK TO THE TITLE SCREEN
		text = "Quit to Main Menu";
		x = getXforCenteredText(text);
		y += gp.TILE_SIZE;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.TILE_SIZE, y);
		}
	}

	public void drawOptionsScreen() {
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));

		// SUBWINDOW
		int frameX = gp.TILE_SIZE * 6;
		int frameY = gp.TILE_SIZE;
		int frameWidth = gp.TILE_SIZE * 8;
		int frameHeight = gp.TILE_SIZE * 10;

		drawSubWindow(frameX, frameY, frameWidth, frameHeight);

		switch (subState) {
		case 0:
			optionsTop(frameX, frameY);
			break;
		case 1:
			optionsFullscreenNotification(frameX, frameY);
			break;
		case 2:
			optionsControl(frameX, frameY);
			break;
		case 3:
			optionsEndGameConfirmation(frameX, frameY);
			break;
		}
		gp.keyH.enterPressed = false;
	}

	public void optionsTop(int frameX, int frameY) {
		int textX;
		int textY;

		// TITLE
		String text = "Options";
		textX = getXforCenteredText(text);
		textY = frameY + gp.TILE_SIZE;
		g2.drawString(text, textX, textY);

		// FULL SCREEN ON/OFF
		textX = frameX + gp.TILE_SIZE;
		textY += gp.TILE_SIZE * 2;
		g2.drawString("Full Screen", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - gp.TILE_SIZE / 2, textY);
			if (gp.keyH.enterPressed == true) {
				if (gp.fullscreenOn == false) {
					gp.fullscreenOn = true;
				} else if (gp.fullscreenOn == true) {
					gp.fullscreenOn = false;
				}
				subState = 1;
			}
		}

		// MUSIC
		textY += gp.TILE_SIZE;
		g2.drawString("Music", textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - gp.TILE_SIZE / 2, textY);
		}

		// SOUND EFFECTS
		textY += gp.TILE_SIZE;
		g2.drawString("SE", textX, textY);
		if (commandNum == 2) {
			g2.drawString(">", textX - gp.TILE_SIZE / 2, textY);
		}
		// CONTROLS
		textY += gp.TILE_SIZE;
		g2.drawString("Controls", textX, textY);
		if (commandNum == 3) {
			g2.drawString(">", textX - gp.TILE_SIZE / 2, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
				commandNum = 0;
			}
		}
		// END GAME
		textY += gp.TILE_SIZE;
		g2.drawString("End Game", textX, textY);
		if (commandNum == 4) {
			g2.drawString(">", textX - gp.TILE_SIZE / 2, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 3;
				commandNum = 0;
			}
		}
		// BACK
		textY += gp.TILE_SIZE * 2;
		g2.drawString("Back", textX, textY);
		if (commandNum == 5) {
			g2.drawString(">", textX - gp.TILE_SIZE / 2, textY);
			if (gp.keyH.enterPressed == true) {
				gp.gameState = gp.playState;
				commandNum = 0;
			}
		}
		// FULLSCREEN CHECKBOX
		textX = frameX + (int) (gp.TILE_SIZE * 4.5);
		textY = frameY + gp.TILE_SIZE * 2 + gp.TILE_SIZE / 2;
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, gp.TILE_SIZE / 2, gp.TILE_SIZE / 2);
		if (gp.fullscreenOn == true) {
			g2.fillRect(textX, textY, gp.TILE_SIZE / 2, gp.TILE_SIZE / 2);
		}

		// MUSIC VOLUME
		textY += gp.TILE_SIZE;
		g2.drawRect(textX, textY, 120, 24); // 120 / 5 = 24
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);

		// SE VOLUME
		textY += gp.TILE_SIZE;
		g2.drawRect(textX, textY, 120, 24);
		volumeWidth = 24 * gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		// SAVE SETTING TO CONFIG FILE
		gp.config.saveConfig();
	}

	public void optionsFullscreenNotification(int frameX, int frameY) {
		int textX = frameX + gp.TILE_SIZE;
		int textY = frameY + gp.TILE_SIZE * 3;

		currentDialogue = "The change will take \neffect after restarting \nthe game.";

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}
		// BACK
		textY = frameY + gp.TILE_SIZE * 9;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
			}

		}

	}

	public void optionsControl(int frameX, int frameY) {

		int textX;
		int textY;

		// TITLE
		String text = "Control";
		textX = getXforCenteredText(text);
		textY = frameY + gp.TILE_SIZE;
		g2.drawString(text, textX, textY);
		// CONTROL LABELS
		textX = frameX + gp.TILE_SIZE;
		textY += gp.TILE_SIZE;
		g2.drawString("Move", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("Confirm/Attack", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("Shoot/Cast", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("Character Screen", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("Pause", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("Options", textX, textY);
		// CONTROL KEYS
		textX = frameX + gp.TILE_SIZE * 6;
		textY = frameY + gp.TILE_SIZE * 2;
		g2.drawString("WASD", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("ENTER", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("F", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("C", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("P", textX, textY);
		textY += gp.TILE_SIZE;
		g2.drawString("ESC", textX, textY);
		textY += gp.TILE_SIZE;

		// BACK
		textX = frameX + gp.TILE_SIZE;
		textY = frameY + gp.TILE_SIZE * 9;
		g2.drawString("Back", textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 3;
			}
		}
	}

	public void optionsEndGameConfirmation(int frameX, int frameY) {
		int textX = frameX + gp.TILE_SIZE;
		int textY = frameY + gp.TILE_SIZE * 2;

		currentDialogue = "Quit the game and \nreturn to the title screen?";

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}

		// YES
		String text = "Yes";
		textX = getXforCenteredText(text);
		textY += gp.TILE_SIZE * 2;
		g2.drawString(text, textX, textY);
		if (commandNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				gp.gameState = gp.titleState;
				titleScreenState = 0;
				gp.stopMusic();
				gp.currentMap = 0;
				gp.resetGame(true);
			}

		}

		// NO
		text = "No";
		textX = getXforCenteredText(text);
		textY += gp.TILE_SIZE;
		g2.drawString(text, textX, textY);
		if (commandNum == 1) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyH.enterPressed == true) {
				subState = 0;
				commandNum = 4;
			}

		}
	}

	public void drawTransition() {
		// DARKENING THE SCREEN
		trCounter++;
		g2.setColor(new Color(0, 0, 0, trCounter * 5));
		g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
		// RESET COUNTER
		if (trCounter == 50) {
			trCounter = 0;
			// UPDATE PLAYER'S POSITION
			gp.gameState = gp.playState;
			gp.currentMap = gp.eHandler.tempMap;
			gp.player.worldX = gp.TILE_SIZE * gp.eHandler.tempCol;
			gp.player.worldY = gp.TILE_SIZE * gp.eHandler.tempRow;
			gp.eHandler.previousEventX = gp.player.worldX;
			gp.eHandler.previousEventY = gp.player.worldY;
		}

	}

	public void drawTradeScreen() {
		switch (subState) {
		case 0:
			tradeSelect();
			break;
		case 1:
			tradeBuy();
			break;
		case 2:
			tradeSell();
			break;
		}
		gp.keyH.enterPressed = false;
	}

	public void tradeSelect() {
		drawDialogueScreen();

		// DRAW SUBWINDOW
		int x = gp.TILE_SIZE * 15;
		int y = gp.TILE_SIZE * 4;
		int width = gp.TILE_SIZE * 3;
		int height = (int) (gp.TILE_SIZE * 3.5);
		drawSubWindow(x, y, width, height);

		// DRAW TEXTS
		x += gp.TILE_SIZE;
		y += gp.TILE_SIZE;
		g2.drawString("Buy", x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.TILE_SIZE / 2, y);
			if (gp.keyH.enterPressed == true) {
				subState = 1;
			}
		}
		y += gp.TILE_SIZE;
		g2.drawString("Sell", x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.TILE_SIZE / 2, y);
			if (gp.keyH.enterPressed == true) {
				subState = 2;
			}
		}
		y += gp.TILE_SIZE;
		g2.drawString("Leave", x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.TILE_SIZE / 2, y);
			if (gp.keyH.enterPressed == true) {
				commandNum = 0;
				gp.gameState = gp.dialogueState;
				currentDialogue = "Come again, hehe!";
			}
		}

	}

	public void tradeBuy() {

		// DRAW PLAYER'S INVENTORY IN TRADE STATE
		drawInventory(gp.player, false);
		// DRAW MERCHANT'S INVENTORY IN TRADE STATE
		drawInventory(npc, true);

		// DRAW HINT WINDOW
		int x = gp.TILE_SIZE * 2;
		int y = gp.TILE_SIZE * 9;
		int width = gp.TILE_SIZE * 6;
		int height = gp.TILE_SIZE * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x + 24, y + 56);

		// DRAW PLAYER'S COIN WINDOW
		x = gp.TILE_SIZE * 13;
		y = gp.TILE_SIZE * 9;
		width = gp.TILE_SIZE * 6;
		height = gp.TILE_SIZE * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your Coins:" + gp.player.coin, x + 24, y + 56);

		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
		if (itemIndex < npc.inventory.size()) {
			x = (int) (gp.TILE_SIZE * 5.5);
			y = (int) (gp.TILE_SIZE * 5.5);
			width = (int) (gp.TILE_SIZE * 2.5);
			height = gp.TILE_SIZE;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

			int price = npc.inventory.get(itemIndex).price;
			String text = "" + price;
			x = getXforAlignToRightText(text, gp.TILE_SIZE * 8 - gp.TILE_SIZE / 2);
			g2.drawString(text, x, y + 34);

			// BUY AN ITEM
			if (gp.keyH.enterPressed == true) {
				if (npc.inventory.get(itemIndex).price > gp.player.coin) {
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "You need more coins to buy that!";
					drawDialogueScreen();
				} else {
					if (gp.player
							.canObtainItem(npc.inventory.get(itemIndex)) == true) {
						gp.player.coin -= npc.inventory.get(itemIndex).price;
						npc.inventory.remove(itemIndex);
					} else {
						subState = 0;
						gp.gameState = gp.dialogueState;
						currentDialogue = "You cannot carry any more items!";
					}
				}
			}
		}
	}

	public void tradeSell() {
		// DRAW PLAYER'S INVENTORY
		drawInventory(gp.player, true);

		// DRAW HINT WINDOW
		int x = gp.TILE_SIZE * 2;
		int y = gp.TILE_SIZE * 9;
		int width = gp.TILE_SIZE * 6;
		int height = gp.TILE_SIZE * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("[ESC] Back", x + 24, y + 56);

		// DRAW PLAYER'S COIN WINDOW
		x = gp.TILE_SIZE * 13;
		y = gp.TILE_SIZE * 9;
		width = gp.TILE_SIZE * 6;
		height = gp.TILE_SIZE * 2;
		drawSubWindow(x, y, width, height);
		g2.drawString("Your Coins:" + gp.player.coin, x + 24, y + 56);

		// DRAW PRICE WINDOW
		int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
		if (itemIndex < gp.player.inventory.size()) {
			x = (int) (gp.TILE_SIZE * 16.5);
			y = (int) (gp.TILE_SIZE * 5.5);
			width = (int) (gp.TILE_SIZE * 2.5);
			height = gp.TILE_SIZE;
			drawSubWindow(x, y, width, height);
			g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

			int price = (int) gp.player.inventory.get(itemIndex).price / 2;
			String text = "" + price;
			x = getXforAlignToRightText(text, gp.TILE_SIZE * 19 - gp.TILE_SIZE / 2);
			g2.drawString(text, x, y + 34);

			// SELL AN ITEM
			if (gp.keyH.enterPressed == true) {
				if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon
						|| gp.player.inventory
								.get(itemIndex) == gp.player.currentShield) {
					commandNum = 0;
					subState = 0;
					gp.gameState = gp.dialogueState;
					currentDialogue = "You cannot sell an equipped item!";
					drawDialogueScreen();
				} else {
					if (gp.player.inventory.get(itemIndex).amount > 1) {
						gp.player.inventory.get(itemIndex).amount--;
					} else {
						gp.player.inventory.remove(itemIndex);
					}
					gp.player.coin += price;
				}
			}
		}
	}

	public void drawSleepScreen() {
		trCounter++;
		if (trCounter < 120) {
			gp.envManager.lighting.filterAlpha += 0.01F;
			if (gp.envManager.lighting.filterAlpha > 1F) {
				gp.envManager.lighting.filterAlpha = 1F;
			}
		}
		if (trCounter >= 120) {
			gp.envManager.lighting.filterAlpha -= 0.01F;
			if (gp.envManager.lighting.filterAlpha <= 0F) {
				gp.envManager.lighting.filterAlpha = 0F;
				trCounter = 0;
				gp.envManager.lighting.dayState = gp.envManager.lighting.DAY;
				gp.gameState = gp.playState;
				gp.player.getImages();
				gp.envManager.lighting.dayCounter = 0;
			}
		}
	}

	public int getItemIndexOnSlot(int slotCol, int slotRow) {
		int itemIndex = slotCol + (slotRow * numOfCols);
		return itemIndex;
	}

	public void drawSubWindow(int x, int y, int width, int height) {
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

	}

	public int getXforCenteredText(String text) {
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2)
				.getWidth();
		int x = gp.SCREEN_WIDTH / 2 - textLength / 2;
		return x;
	}

	public int getXforAlignToRightText(String text, int tailX) {
		int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2)
				.getWidth();
		int x = tailX - textLength;
		return x;
	}
}
