package utility;

import boss_monster.BMON_SkeletonLord;
import data.Progress;
import entity.NPC_BigRock;
import entity.NPC_Merchant;
import entity.NPC_OldMan;
import main.GamePanel;
import monster.MON_Bat;
import monster.MON_GreenSlime;
import monster.MON_Orc;
import object.OBJ_Axe;
import object.OBJ_BlueHeart;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Pickaxe;
import object.OBJ_Potion_Red;
import object.OBJ_Tent;
import tile_interactive.IT_DestructibleWall;
import tile_interactive.IT_DryTree;
import tile_interactive.IT_MetalPlate;

public class AssetSetter {
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		this.gp = gp;
	}

	public void setObjects() {

		int mapNum = 0;

		int i = 0;
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
		gp.objs[mapNum][i] = new OBJ_Chest(gp);
		gp.objs[mapNum][i].setLoot(new OBJ_Key(gp));
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 37;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 7;
		i++;
		gp.objs[mapNum][i] = new OBJ_Chest(gp);
		gp.objs[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 22;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 12;
		i++;
		gp.objs[mapNum][i] = new OBJ_Chest(gp);
		gp.objs[mapNum][i].setLoot(new OBJ_Axe(gp));
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 13;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 20;
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

		// DUNGEON01 MAP OBJECTS
		mapNum = 2;
		i = 0;
		gp.objs[mapNum][i] = new OBJ_Chest(gp);
		gp.objs[mapNum][i].setLoot(new OBJ_Pickaxe(gp));
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 40;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 41;
		i++;
		gp.objs[mapNum][i] = new OBJ_Chest(gp);
		gp.objs[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 13;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 16;
		i++;
		gp.objs[mapNum][i] = new OBJ_Chest(gp);
		gp.objs[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 26;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 34;
		i++;
		gp.objs[mapNum][i] = new OBJ_Chest(gp);
		gp.objs[mapNum][i].setLoot(new OBJ_Potion_Red(gp));
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 27;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 15;
		i++;
		gp.objs[mapNum][i] = new OBJ_Door_Iron(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 18;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 23;

		mapNum = 3;
		i = 0;
		gp.objs[mapNum][i] = new OBJ_Door_Iron(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 25;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 15;
		i++;
		gp.objs[mapNum][i] = new OBJ_BlueHeart(gp);
		gp.objs[mapNum][i].worldX = gp.TILE_SIZE * 25;
		gp.objs[mapNum][i].worldY = gp.TILE_SIZE * 8;

	}

	public void setNPC() {

		int i = 0;
		// FIRST MAP
		int mapNum = 0;
		gp.npcs[mapNum][i] = new NPC_OldMan(gp);
		gp.npcs[mapNum][i].worldX = gp.TILE_SIZE * 21;
		gp.npcs[mapNum][i].worldY = gp.TILE_SIZE * 20;

		// SECOND MAP (INTERIOR)
		mapNum = 1;
		i = 0;
		gp.npcs[mapNum][i] = new NPC_Merchant(gp);
		gp.npcs[mapNum][i].worldX = gp.TILE_SIZE * 12;
		gp.npcs[mapNum][i].worldY = gp.TILE_SIZE * 7;

		// THIRD MAP (DUNGEON01)
		mapNum = 2;
		i = 0;
		gp.npcs[mapNum][i] = new NPC_BigRock(gp);
		gp.npcs[mapNum][i].worldX = gp.TILE_SIZE * 20;
		gp.npcs[mapNum][i].worldY = gp.TILE_SIZE * 25;
		i++;
		gp.npcs[mapNum][i] = new NPC_BigRock(gp);
		gp.npcs[mapNum][i].worldX = gp.TILE_SIZE * 11;
		gp.npcs[mapNum][i].worldY = gp.TILE_SIZE * 18;
		i++;
		gp.npcs[mapNum][i] = new NPC_BigRock(gp);
		gp.npcs[mapNum][i].worldX = gp.TILE_SIZE * 23;
		gp.npcs[mapNum][i].worldY = gp.TILE_SIZE * 14;
		i++;
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
		i++;
		gp.monsters[mapNum][i] = new MON_Orc(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 12;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 33;

		// SET MONSTERS IN DUNGEON01
		mapNum = 2;
		i = 0;
		gp.monsters[mapNum][i] = new MON_Bat(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 34;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 39;
		i++;
		gp.monsters[mapNum][i] = new MON_Bat(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 36;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 25;
		i++;
		gp.monsters[mapNum][i] = new MON_Bat(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 39;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 26;
		i++;
		gp.monsters[mapNum][i] = new MON_Bat(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 28;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 11;
		i++;
		gp.monsters[mapNum][i] = new MON_Bat(gp);
		gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 10;
		gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 19;
		i++;

		// DUNGEON02 (BOSS ENCOUNTER)
		mapNum = 3;
		i = 0;
		// CHECK IF THE BOSS IS ALREADY DEFEATED
		if (Progress.skeletonLordDefeated == false) {
			gp.monsters[mapNum][i] = new BMON_SkeletonLord(gp);
			gp.monsters[mapNum][i].worldX = gp.TILE_SIZE * 23;
			gp.monsters[mapNum][i].worldY = gp.TILE_SIZE * 16;
		}

	}

	public void setInteractiveTiles() {

		// OUTSIDE
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

		// DUNGEON01
		mapNum = 2;
		i = 0;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 18, 30);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 17, 31);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 17, 32);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 17, 34);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 18, 34);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 18, 33);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 10, 24);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 38, 18);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 38, 19);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 38, 20);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 38, 21);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 18, 13);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 18, 14);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 22, 28);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 30, 28);
		i++;
		gp.iTiles[mapNum][i] = new IT_DestructibleWall(gp, 32, 28);

		i++;
		gp.iTiles[mapNum][i] = new IT_MetalPlate(gp, 20, 22);
		i++;
		gp.iTiles[mapNum][i] = new IT_MetalPlate(gp, 8, 17);
		i++;
		gp.iTiles[mapNum][i] = new IT_MetalPlate(gp, 39, 31);
	}
}
