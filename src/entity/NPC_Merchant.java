package entity;

import main.GamePanel;
import object.OBJ_Axe;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import object.OBJ_Tent;

public class NPC_Merchant extends Entity {
	public NPC_Merchant(GamePanel gp) {
		super(gp);

		direction = "down";
		speed = 1;

		solidArea.x = 8;
		solidArea.y = 16;
		solidArea.width = 32;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImages();
		setDialogue();
		setItems();
	}

	public void getImages() {

		up1 = setup("/npc/merchant_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		up2 = setup("/npc/merchant_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		down1 = setup("/npc/merchant_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		down2 = setup("/npc/merchant_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		left1 = setup("/npc/merchant_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		left2 = setup("/npc/merchant_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
		right1 = setup("/npc/merchant_down_1", gp.TILE_SIZE, gp.TILE_SIZE);
		right2 = setup("/npc/merchant_down_2", gp.TILE_SIZE, gp.TILE_SIZE);
	}

	public void setDialogue() {
		dialogues[0][0] = "I see that someone managed to find me!"
				+ "\nNow I'll be able to present you some "
				+ "\npristine equipment! For a reasonable \nprice, of course!";
		dialogues[1][0] = "Come again, hehe!";
		dialogues[2][0] =  "You need more coins to buy that!";
		dialogues[3][0] =  "You cannot carry any more items!";
		dialogues[4][0] =  "You cannot sell an equipped item!";
		
	}

	public void setItems() {
		inventory.add(new OBJ_Potion_Red(gp));
		inventory.add(new OBJ_Potion_Red(gp));
		inventory.add(new OBJ_Potion_Red(gp));
		inventory.add(new OBJ_Sword_Normal(gp));
		inventory.add(new OBJ_Axe(gp));
		inventory.add(new OBJ_Shield_Wood(gp));
		inventory.add(new OBJ_Shield_Blue(gp));
		inventory.add(new OBJ_Tent(gp));

	}

	public void speak() {
		facePlayer();
		gp.gameState = gp.tradeState;
		gp.ui.npc = this;
	}

}
