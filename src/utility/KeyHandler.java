package utility;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyHandler implements KeyListener {

	GamePanel gp;

	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed,
			shootKeyPressed, spacePressed;
	// DEBUG
	public boolean checkDrawTime = false;
	public boolean godModeOn = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			useTitleState(code);
		}

		// PLAY STATE
		else if (gp.gameState == gp.playState) {
			usePlayState(code);
		}
		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			usePauseState(code);
		}
		// DIALOGUE OR CUTSCENE STATE
		else if (gp.gameState == gp.dialogueState
				|| gp.gameState == gp.cutsceneState) {
			useDialogueState(code);
		}
		// CHARACTER SHEET STATE
		else if (gp.gameState == gp.charSheetState) {
			useCharSheetState(code);
		}
		// OPTIONS STATE
		else if (gp.gameState == gp.optionsState) {
			useOptionsState(code);
		}
		// GAME OVER STATE
		else if (gp.gameState == gp.gameOverState) {
			useGameOverState(code);
		}
		// TRADE STATE
		else if (gp.gameState == gp.tradeState) {
			useTradeState(code);
		}
		// MAP STATE (MINIMAP)
		else if (gp.gameState == gp.mapState) {
			useMapState(code);
		}
	}

	public void useMapState(int code) {
		if (code == KeyEvent.VK_M) {
			gp.gameState = gp.playState;
		}
	}

	public void useTradeState(int code) {
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (gp.ui.subState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
				gp.playSE(9);
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
				gp.playSE(9);
			}
		}
		if (gp.ui.subState == 1) {
			useNpcInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
		if (gp.ui.subState == 2) {
			usePlayerInventory(code);
			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.subState = 0;
			}
		}
	}

	public void usePlayerInventory(int code) {
		if (code == KeyEvent.VK_W) {
			if (gp.ui.playerSlotRow != 0) {
				gp.ui.playerSlotRow--;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_S) {
			if (gp.ui.playerSlotRow != gp.ui.numOfRows - 1) {
				gp.ui.playerSlotRow++;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.playerSlotCol != 0) {
				gp.ui.playerSlotCol--;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_D) {
			if (gp.ui.playerSlotCol != gp.ui.numOfCols - 1) {
				gp.ui.playerSlotCol++;
				gp.playSE(9);
			}
		}
	}

	public void useNpcInventory(int code) {
		if (code == KeyEvent.VK_W) {
			if (gp.ui.npcSlotRow != 0) {
				gp.ui.npcSlotRow--;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_S) {
			if (gp.ui.npcSlotRow != gp.ui.numOfRows - 1) {
				gp.ui.npcSlotRow++;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.npcSlotCol != 0) {
				gp.ui.npcSlotCol--;
				gp.playSE(9);
			}
		}
		if (code == KeyEvent.VK_D) {
			if (gp.ui.npcSlotCol != gp.ui.numOfCols - 1) {
				gp.ui.npcSlotCol++;
				gp.playSE(9);
			}
		}
	}

	public void useGameOverState(int code) {
		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = 1;
			}
			gp.playSE(9);
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			if (gp.ui.commandNum > 1) {
				gp.ui.commandNum = 0;
			}
			gp.playSE(9);
		}
		if (code == KeyEvent.VK_ENTER) {
			if (gp.ui.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.resetGame(false);

			} else if (gp.ui.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.ui.titleScreenState = 0;
				gp.stopMusic();
				gp.resetGame(true);
			}
		}
	}

	public void useOptionsState(int code) {
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}

		int maxCommandNum = 0;
		switch (gp.ui.subState) {
		case 0:
			maxCommandNum = 5;
			break;
		case 3:
			maxCommandNum = 1;
			break;
		}

		if (code == KeyEvent.VK_W) {
			gp.ui.commandNum--;
			gp.playSE(9);
			if (gp.ui.commandNum < 0) {
				gp.ui.commandNum = maxCommandNum;
			}
		}
		if (code == KeyEvent.VK_S) {
			gp.ui.commandNum++;
			gp.playSE(9);
			if (gp.ui.commandNum > maxCommandNum) {
				gp.ui.commandNum = 0;
			}
		}
		if (code == KeyEvent.VK_A) {
			if (gp.ui.subState == 0) {
				// MUSIC
				if (gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(9);
				}
				// SE
				if (gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
					gp.se.volumeScale--;
					gp.playSE(9);
				}
			}
		}
		if (code == KeyEvent.VK_D) {
			if (gp.ui.subState == 0) {
				// MUSIC
				if (gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(9);
				}
				if (gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
					gp.se.volumeScale++;
					gp.playSE(9);
				}
			}
		}

	}

	public void useTitleState(int code) {
		if (gp.ui.titleScreenState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
				}
				if (gp.ui.commandNum == 1) {
					gp.saveLoad.load();
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
		} else if (gp.ui.titleScreenState == 1) {
			gp.playMusic(0);
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 3;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				if (gp.ui.commandNum > 3) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					System.out.println("You choose fighter class!");
					gp.gameState = gp.playState;
//					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 1) {
					System.out.println("You choose thief class!");
					gp.gameState = gp.playState;
//					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 2) {
					System.out.println("You choose mage class!");
					gp.gameState = gp.playState;
//					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 3) {
					gp.ui.titleScreenState = 0;
					gp.ui.commandNum = 0;
					gp.stopMusic();
				}
			}
		}
	}

	public void usePlayState(int code) {
		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.pauseState;
		}
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.charSheetState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (code == KeyEvent.VK_F) {
			shootKeyPressed = true;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.optionsState;
		}
		if (code == KeyEvent.VK_M) {
			gp.gameState = gp.mapState;
		}
		if (code == KeyEvent.VK_X) {
			if (gp.map.miniMapOn == false) {
				gp.map.miniMapOn = true;
			} else {
				gp.map.miniMapOn = false;
			}
		}
		if (code == KeyEvent.VK_SPACE) {
			if (spacePressed == false) {
				spacePressed = true;
			} else {
				spacePressed = false;
			}
		}
		// DEBUG
		if (code == KeyEvent.VK_T) {
			if (checkDrawTime == false) {
				checkDrawTime = true;
			} else if (checkDrawTime == true) {
				checkDrawTime = false;
			}
		}
		if (code == KeyEvent.VK_R) {
			switch (gp.currentMap) {
			case 0:
				gp.tileM.loadMap("/maps/worldV3.txt", 0);
				break;
			case 1:
				gp.tileM.loadMap("/maps/interior01.txt", 1);
				break;
			}
		}
		// DEBUG
		if (code == KeyEvent.VK_G) {
			if (godModeOn == false) {
				godModeOn = true;
			} else if (godModeOn == true) {
				godModeOn = false;
			}
		}
	}

	public void usePauseState(int code) {
		if (code == KeyEvent.VK_P) {
			gp.gameState = gp.playState;
		}
	}

	public void useDialogueState(int code) {
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
	}

	public void useCharSheetState(int code) {
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}

		if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}
		usePlayerInventory(code);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_F) {
			shootKeyPressed = false;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = false;
		}
		if (code == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}

	}

}
