package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import utility.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tiles;
	public int mapTileNums[][][];
	boolean drawPath = true;

	public TileManager(GamePanel gp) {
		this.gp = gp;

		tiles = new Tile[50];

		mapTileNums = new int[gp.MAX_MAP][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

		getTileImage();

		loadMap("/maps/worldV3.txt", 0);
		loadMap("/maps/interior01.txt", 1);
	}

	public void getTileImage() {

		// PLACEHOLDER
		setup(0, "grass00", false);
		setup(1, "grass00", false);
		setup(2, "grass00", false);
		setup(3, "grass00", false);
		setup(4, "grass00", false);
		setup(5, "grass00", false);
		setup(6, "grass00", false);
		setup(7, "grass00", false);
		setup(8, "grass00", false);
		setup(9, "grass00", false);
		setup(10, "grass00", false);
		// PLACEHOLDER

		setup(11, "grass01", false);
		setup(12, "water00", true);
		setup(13, "water01", true);
		setup(14, "water02", true);
		setup(15, "water03", true);
		setup(16, "water04", true);
		setup(17, "water05", true);
		setup(18, "water06", true);
		setup(19, "water07", true);
		setup(20, "water08", true);
		setup(21, "water09", true);
		setup(22, "water10", true);
		setup(23, "water11", true);
		setup(24, "water12", true);
		setup(25, "water13", true);
		setup(26, "road00", false);
		setup(27, "road01", false);
		setup(28, "road02", false);
		setup(29, "road03", false);
		setup(30, "road04", false);
		setup(31, "road05", false);
		setup(32, "road06", false);
		setup(33, "road07", false);
		setup(34, "road08", false);
		setup(35, "road09", false);
		setup(36, "road10", false);
		setup(37, "road11", false);
		setup(38, "road12", false);
		setup(39, "earth", false);
		setup(40, "wall", true);
		setup(41, "tree", true);
		setup(42, "hut", false);
		setup(43, "floor01", false);
		setup(44, "table01", true);

	}

	public void setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();

		try {
			tiles[index] = new Tile();
			tiles[index].image = ImageIO.read(getClass()
					.getResourceAsStream("/new_tiles/" + imageName + ".png"));
			tiles[index].image = uTool.scaleImage(tiles[index].image, gp.TILE_SIZE,
					gp.TILE_SIZE);
			tiles[index].collision = collision;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadMap(String filePath, int map) {
		try {

			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			int col = 0;
			int row = 0;

			while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
				String line = br.readLine();

				while (col < gp.MAX_WORLD_COL) {
					String nums[] = line.split(" ");
					int num = Integer.parseInt(nums[col]);
					mapTileNums[map][col][row] = num;
					col++;
				}
				if (col == gp.MAX_WORLD_COL) {
					col = 0;
					row++;
				}
			}

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;

		while (worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW) {

			int tileNum = mapTileNums[gp.currentMap][worldCol][worldRow];

			int worldX = worldCol * gp.TILE_SIZE;
			int worldY = worldRow * gp.TILE_SIZE;
			int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
			int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

			if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X
					&& worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X
					&& worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y
					&& worldY - gp.TILE_SIZE < gp.player.worldY
							+ gp.player.SCREEN_Y) {

				g2.drawImage(tiles[tileNum].image, screenX, screenY, null);
			}

			worldCol++;

			if (worldCol == gp.MAX_WORLD_COL) {
				worldCol = 0;
				worldRow++;
			}

		}

		// SHOW PATHFINDING OF THE NPC
		if (drawPath == true) {
			g2.setColor(new Color(255, 0, 0, 70));

			for (int i = 0; i < gp.pFinder.pathList.size(); i++) {
				int worldX = gp.pFinder.pathList.get(i).col * gp.TILE_SIZE;
				int worldY = gp.pFinder.pathList.get(i).row * gp.TILE_SIZE;
				int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
				int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

				g2.fillRect(screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE);
			}

		}

	}

}
