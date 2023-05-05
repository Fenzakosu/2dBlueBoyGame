package entity;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Door_Iron;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_ManaCrystal;
import object.OBJ_Pickaxe;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import object.OBJ_Tent;

public class EntityGenerator {

	GamePanel gp;

	public EntityGenerator(GamePanel gp) {
		this.gp = gp;
	}

	public Entity getObject(String itemName) {
		Entity obj = null;

		switch (itemName) {
		case OBJ_Axe.OBJ_NAME:
			obj = new OBJ_Axe(gp);
			break;
		case OBJ_Pickaxe.OBJ_NAME:
			obj = new OBJ_Pickaxe(gp);
			break;
		case OBJ_Boots.OBJ_NAME:
			obj = new OBJ_Boots(gp);
			break;
		case OBJ_Key.OBJ_NAME:
			obj = new OBJ_Key(gp);
			break;
		case OBJ_Lantern.OBJ_NAME:
			obj = new OBJ_Lantern(gp);
			break;
		case OBJ_Potion_Red.OBJ_NAME:
			obj = new OBJ_Potion_Red(gp);
			break;
		case OBJ_Shield_Blue.OBJ_NAME:
			obj = new OBJ_Shield_Blue(gp);
			break;
		case OBJ_Shield_Wood.OBJ_NAME:
			obj = new OBJ_Shield_Wood(gp);
			break;
		case OBJ_Sword_Normal.OBJ_NAME:
			obj = new OBJ_Sword_Normal(gp);
			break;
		case OBJ_Tent.OBJ_NAME:
			obj = new OBJ_Tent(gp);
			break;
		case OBJ_Door.OBJ_NAME:
			obj = new OBJ_Door(gp);
			break;
		case OBJ_Door_Iron.OBJ_NAME:
			obj = new OBJ_Door_Iron(gp);
			break;
		case OBJ_Chest.OBJ_NAME:
			obj = new OBJ_Chest(gp);
			break;
		case OBJ_Coin_Bronze.OBJ_NAME:
			obj = new OBJ_Coin_Bronze(gp);
			break;
		case OBJ_Heart.OBJ_NAME:
			obj = new OBJ_Heart(gp);
			break;
		case OBJ_ManaCrystal.OBJ_NAME:
			obj = new OBJ_ManaCrystal(gp);
			break;
		}
		return obj;
	}

}
