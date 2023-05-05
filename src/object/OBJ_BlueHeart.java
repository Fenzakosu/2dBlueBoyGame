package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity {

	GamePanel gp;
	public static final String OBJ_NAME = "Blue Heart";

	public OBJ_BlueHeart(GamePanel gp) {
		super(gp);

		this.gp = gp;

		type = TYPE_PICKUP_ONLY;
		name = OBJ_NAME;
		down1 = setup("/objects/blueheart", gp.TILE_SIZE, gp.TILE_SIZE);
		setDialogues();
	}

	public void setDialogues() {
		dialogues[0][0] = "You pick up a beautiful blue gem.";
		dialogues[0][1] = "You find the Blue Heart, legendary treasure!";
	}

	public boolean use(Entity entity) {
		gp.gameState = gp.cutsceneState;
		gp.csManager.sceneNum = gp.csManager.ENDING;
		return true;
	}

}
