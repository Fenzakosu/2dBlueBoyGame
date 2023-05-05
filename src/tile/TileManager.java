package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;

import main.GamePanel;
import utility.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tiles;
	public int mapTileNums[][][];
	boolean drawPath = true;
	ArrayList<String> fileNames = new ArrayList<>();
	ArrayList<String> collisionStatuses = new ArrayList<>();

	public TileManager(GamePanel gp) {
		this.gp = gp;

		// READ TILE DATA FILE
		InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		// GETTING TILE NAMES AND COLLISION INFO FROM THE FILE
		String line;

		try {
			while ((line = br.readLine()) != null) {
				fileNames.add(line);
				collisionStatuses.add(br.readLine());
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// INITIALIZE THE TILE ARRAY BASED ON THE fileNames SIZE
		tiles = new Tile[fileNames.size()];

		getTileImage();
		
		// GET THE MAX_WORLD_COL & MAX_WORLD_ROW
		is = getClass().getResourceAsStream("/maps/worldmap.txt");
		br = new BufferedReader(new InputStreamReader(is));
		
		try {
			 String line2= br.readLine();
			 String maxTile[] = line2.split(" ");
			 
			 gp.maxWorldCol = maxTile.length;
			 gp.maxWorldRow = maxTile.length;
			 mapTileNums = new int[gp.MAX_MAP][gp.maxWorldCol][gp.maxWorldRow];
			 br.close();
		} catch (IOException e) {
			System.out.println("Exception!");
		}
		loadMap("/maps/worldmap.txt", 0);
		loadMap("/maps/indoor01.txt", 1);
		loadMap("/maps/dungeon01.txt", 2);
		loadMap("/maps/dungeon02.txt", 3);


	}

	public void getTileImage() {

		// ASSIGNMENT OF TILES BY USING THE EDITOR
		for (int i = 0; i < fileNames.size(); i++) {
			String fileName;
			boolean collision;

			// GET A FILE NAME
			fileName = fileNames.get(i);

			// GET A COLLISION STATUS
			if (collisionStatuses.get(i).equals("true")) {
				collision = true;
			} else {
				collision = false;
			}

			setup(i, fileName, collision);

		}

	}

	public void setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();

		try {
			tiles[index] = new Tile();
			tiles[index].image = ImageIO.read(getClass()
					.getResourceAsStream("/tiles_editor/" + imageName));
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

			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();

				while (col < gp.maxWorldCol) {
					String nums[] = line.split(" ");
					int num = Integer.parseInt(nums[col]);
					mapTileNums[map][col][row] = num;
					col++;
				}
				if (col == gp.maxWorldCol) {
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

		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {

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

			if (worldCol == gp.maxWorldCol) {
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
