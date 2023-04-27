package utility;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import main.GamePanel;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Tent;
import tile_interactive.IT_DryTree;

public class AssetSetter {
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObjects() {

		int mapNum = 0;

		int i = 0;
		gp.objs[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 25;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 23;
		i++;
		gp.objs[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 21;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 19;
		i++;
		gp.objs[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 26;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 21;
		i++;
		gp.objs[mapNum][i] = new OBJ_Axe(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 33;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 21;
		i++;
		gp.objs[mapNum][i] = new OBJ_Shield_Blue(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 35;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 21;
		i++;
		gp.objs[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 22;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 27;
		i++;
		gp.objs[mapNum][i] = new OBJ_Heart(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 22;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 29;
		i++;
		gp.objs[mapNum][i] = new OBJ_ManaCrystal(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 22;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 31;
		i++;
		gp.objs[mapNum][i] = new OBJ_Door(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 14;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 28;
		i++;
		gp.objs[mapNum][i] = new OBJ_Door(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 12;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 12;
		i++;
		gp.objs[mapNum][i] = new OBJ_Chest(gp, new OBJ_Key(gp));
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 37;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 7;
		i++;
		gp.objs[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 21;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 20;
		i++;
		gp.objs[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 20;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 20;
		i++;
		gp.objs[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 17;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 21;
		i++;
		gp.objs[mapNum][i] = new OBJ_Lantern(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 18;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 20;
		i++;
		gp.objs[mapNum][i] = new OBJ_Tent(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 19;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 20;
	}

	public void setNPC() {

		int i = 0;
		// FIRST MAP
		int mapNum = 0;
		gp.npcs[mapNum][i] = new NPC_OldMan(gp);
		gp.npcs[mapNum][i].worldX = gp.TILE_SIZE * 21;
		gp.npcs[mapNum][i].worldY = gp.TILE_SIZE * 21;

		// SECOND MAP (INTERIOR)
		mapNum = 1;
		i = 0;
		gp.npcs[mapNum][i] = new NPC_Merchant(gp);
		gp.npcs[mapNum][i].worldX = gp.TILE_SIZE * 12;
		gp.npcs[mapNum][i].worldY = gp.TILE_SIZE * 7;
	}

	public void setMonsters() {
		int mapNum = 0;
		int i = 0;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 21;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 38;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 25;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 37;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 20;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 37;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 23;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 42;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 25;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 40;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 24;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 37;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 34;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 42;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 38;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 42;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 39;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 42;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 38;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 41;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 36;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 33;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 35;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 38;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 19;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 38;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 36;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 7;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 38;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 7;
		i++;
		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 37;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 8;

		mapNum = 1;

		gp.monsters[mapNum][i] = new MON_GreenSlime(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 19;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 38;
		i++;
	}

	public void setInteractiveTiles() {
		int mapNum = 0;
		int i = 0;
		gp.iTiles[mapNum][i] = new IT_DryTree(gp, 27, 12);
		i++;
		gp.iTiles[mapNum][i] = new IT_DryTree(gp, 28, 12);
		i++;
		gp.iTiles[mapNum][i] = new IT_DryTree(gp, 29, 12);
		i++;
		gp.iTiles[mapNum][i] = new IT_DryTree(gp, 30, 12);
		i++;
		gp.iTiles[mapNum][i] = new IT_DryTree(gp, 31, 12);
		i++;
		gp.iTiles[mapNum][i] = new IT_DryTree(gp, 32, 12);
		i++;
		gp.iTiles[mapNum][i] = new IT_DryTree(gp, 33, 12);

	}
}
