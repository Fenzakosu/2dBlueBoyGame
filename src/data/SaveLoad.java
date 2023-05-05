package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import object.OBJ_Tent;

public class SaveLoad {

	GamePanel gp;

	public SaveLoad(GamePanel gp) {
		this.gp = gp;
	}

	public void save() {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File("save.dat")));

			DataStorage ds = new DataStorage();
			// SAVE PLAYER'S STATS
			ds.level = gp.player.level;
			ds.maxLife = gp.player.maxLife;
			ds.life = gp.player.life;
			ds.maxMana = gp.player.maxMana;
			ds.mana = gp.player.mana;
			ds.strength = gp.player.strength;
			ds.dexterity = gp.player.dexterity;
			ds.exp = gp.player.exp;
			ds.nextLevelExp = gp.player.nextLevelExp;
			ds.coin = gp.player.coin;

			// SAVE PLAYER'S INVENTORY
			for (int i = 0; i < gp.player.inventory.size(); i++) {
				ds.itemNames.add(gp.player.inventory.get(i).name);
				ds.itemAmounts.add(gp.player.inventory.get(i).amount);
			}

			// SAVE PLAYER'S EQUIPPED ITEMS (WEAPON AND SHIELD)
			ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
			ds.currentShieldSlot = gp.player.getCurrentShieldSlot();

			// OBJECTS ON MAP
			ds.mapObjectNames = new String[gp.MAX_MAP][gp.objs[1].length];
			ds.mapObjectWorldX = new int[gp.MAX_MAP][gp.objs[1].length];
			ds.mapObjectWorldY = new int[gp.MAX_MAP][gp.objs[1].length];
			// OBJECTS IN CHESTS
			ds.mapObjectLootNames = new String[gp.MAX_MAP][gp.objs[1].length];
			ds.mapObjectsOpened = new boolean[gp.MAX_MAP][gp.objs[1].length];

			for (int mapNum = 0; mapNum < gp.MAX_MAP; mapNum++) {
				for (int i = 0; i < gp.objs[1].length; i++) {
					if (gp.objs[mapNum][i] == null) {
						ds.mapObjectNames[mapNum][i] = "NA";
					} else {
						ds.mapObjectNames[mapNum][i] = gp.objs[mapNum][i].name;
						ds.mapObjectWorldX[mapNum][i] = gp.objs[mapNum][i].worldX;
						ds.mapObjectWorldY[mapNum][i] = gp.objs[mapNum][i].worldY;
						if (gp.objs[mapNum][i].loot != null) {
							ds.mapObjectLootNames[mapNum][i] = gp.objs[mapNum][i].loot.name;
						}
						ds.mapObjectsOpened[mapNum][i] = gp.objs[mapNum][i].isOpen;
					}
				}
			}

			// WRITE THE DATASTORAGE OBJECT
			oos.writeObject(ds);

		} catch (FileNotFoundException e) {
			System.out.println("FILE NOT FOUND");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("INPUT / OUTPUT EXCEPTION");
			e.printStackTrace();
		}
	}

	public void load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(
					new FileInputStream(new File("save.dat")));

			// READ THE DATASTORAGE OBJECT
			DataStorage ds = (DataStorage) ois.readObject();

			// LOAD PLAYER'S STATS
			gp.player.level = ds.level;
			gp.player.maxLife = ds.maxLife;
			gp.player.life = ds.life;
			gp.player.maxMana = ds.maxMana;
			gp.player.mana = ds.mana;
			gp.player.strength = ds.strength;
			gp.player.dexterity = ds.dexterity;
			gp.player.exp = ds.exp;
			gp.player.nextLevelExp = ds.nextLevelExp;
			gp.player.coin = ds.coin;

			// LOAD PLAYER'S INVENTORY
			gp.player.inventory.clear();

			for (int i = 0; i < ds.itemNames.size(); i++) {
				gp.player.inventory
						.add(gp.entGenerator.getObject(ds.itemNames.get(i)));
				gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
			}

			// LOAD PLAYER'S EQUIPPED ITEMS (WEAPON AND SHIELD)
			gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
			gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
			// GET PLAYER'S ATTACK , DEFENSE AND ATTACK IMAGES
			gp.player.getAttack();
			gp.player.getDefense();
			gp.player.getAttackImages();

			// LOAD OBJECTS ON MAP
			for (int mapNum = 0; mapNum < gp.MAX_MAP; mapNum++) {
				for (int i = 0; i < gp.objs[1].length; i++) {
					if (ds.mapObjectNames[mapNum][i].equals("NA")) {
						// NOT AVAILABLE
						gp.objs[mapNum][i] = null;
					} else {
						gp.objs[mapNum][i] = gp.entGenerator
								.getObject(ds.mapObjectNames[mapNum][i]);
						gp.objs[mapNum][i].worldX = ds.mapObjectWorldX[mapNum][i];
						gp.objs[mapNum][i].worldY = ds.mapObjectWorldY[mapNum][i];
						if (ds.mapObjectLootNames[mapNum][i] != null) {
							// ITEMS IN CHEST
							gp.objs[mapNum][i].setLoot(gp.entGenerator
									.getObject(ds.mapObjectLootNames[mapNum][i]));
						}
						gp.objs[mapNum][i].isOpen = ds.mapObjectsOpened[mapNum][i];
						// IF CHEST IS OPENED , CHANGE IT'S IMAGE
						if (gp.objs[mapNum][i].isOpen == true) {
							gp.objs[mapNum][i].down1 = gp.objs[mapNum][i].image2;
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("LOAD EXCEPTION");
			e.printStackTrace();
		}
	}

}
