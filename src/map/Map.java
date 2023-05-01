package map;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import main.GamePanel;
import tile.TileManager;

public class Map extends TileManager {

	GamePanel gp;
	BufferedImage worldMap[];
	public boolean miniMapOn = false;

	public Map(GamePanel gp) {
		super(gp);
		this.gp = gp;
		createWorldMap();
	}

	public void createWorldMap() {
		worldMap = new BufferedImage[gp.MAX_MAP];
		int worldMapWidth = gp.TILE_SIZE * gp.maxWorldCol;
		int worldMapHeight = gp.TILE_SIZE * gp.maxWorldRow;

		for (int i = 0; i < gp.MAX_MAP; i++) {
			worldMap[i] = new BufferedImage(worldMapWidth, worldMapHeight,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = (Graphics2D) worldMap[i].createGraphics();

			int col = 0;
			int row = 0;

			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				int tileNum = mapTileNums[i][col][row];
				int x = gp.TILE_SIZE * col;
				int y = gp.TILE_SIZE * row;
				g2.drawImage(tiles[tileNum].image, x, y, null);
				col++;
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			g2.dispose();
		}
	}

	public void drawFullMapScreen(Graphics2D g2) {
		// BACKGROUND COLOR
		g2.setColor(Color.black);
		g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

		// DRAW MAP
		int width = 500;
		int height = 500;
		int x = gp.SCREEN_WIDTH / 2 - width / 2;
		int y = gp.SCREEN_HEIGHT / 2 - height / 2;

		g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

		// DRAW PLAYER
		double scale = (double) (gp.TILE_SIZE * gp.maxWorldCol) / width;
		int playerX = (int) (x + gp.player.worldX / scale);
		int playerY = (int) (y + gp.player.worldY / scale);
		int playerSize = (int) (gp.TILE_SIZE / scale);
		g2.drawImage(gp.player.down1, playerX, playerY, playerSize, playerSize,
				null);

		// HINT
		g2.setFont(gp.ui.maruMonica.deriveFont(32F));
		g2.setColor(Color.white);
		g2.drawString("Press M to close", 750, 550);
	}

	public void drawMiniMap(Graphics2D g2) {
		if (miniMapOn == true) {
			// DRAW MAP
			int width = 200;
			int height = 200;
			int x = gp.SCREEN_WIDTH - width - 50;
			int y = 50;

			g2.setComposite(
					AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2.drawImage(worldMap[gp.currentMap], x, y, width, height, null);

			// DRAW PLAYER
			double scale = (double) (gp.TILE_SIZE * gp.maxWorldCol) / width;
			int playerX = (int) (x + gp.player.worldX / scale);
			int playerY = (int) (y + gp.player.worldY / scale);
			int playerSize = (int) (gp.TILE_SIZE / 5);
			g2.drawImage(gp.player.down1, playerX-2, playerY-2, playerSize, playerSize,
					null);
			// RESET ALPHA
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		}
	}

}
