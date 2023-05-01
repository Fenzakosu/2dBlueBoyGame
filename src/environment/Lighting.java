package environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Lighting {
	GamePanel gp;
	BufferedImage darknessFilter;
	public int dayCounter;
	public float filterAlpha = 0F;

	public final int DAY = 0;
	public final int DUSK = 1;
	public final int NIGHT = 2;
	public final int DAWN = 3;
	public int dayState = DAY;

	public Lighting(GamePanel gp) {

		this.gp = gp;
		setLightSource();

	}

	public void setLightSource() {

		// CREATE A BUFFERED IMAGE
		darknessFilter = new BufferedImage(gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

		if (gp.player.currentLight == null) {
			g2.setColor(new Color(0, 0, 0.1F, 0.9F));
		} else {
			// GET THE CENTER X AND Y OF THE LIGHT CIRCLE
			int centerX = gp.player.SCREEN_X + gp.TILE_SIZE / 2;
			int centerY = gp.player.SCREEN_Y + gp.TILE_SIZE / 2;

			// CREATE A GRADATION EFFECT WITHIN THE LIGHT CIRCLE
			Color color[] = new Color[12];
			float fraction[] = new float[12];

			color[0] = new Color(0, 0, 0.1F, 0.1F);
			color[1] = new Color(0, 0, 0.1F, 0.42F);
			color[2] = new Color(0, 0, 0.1F, 0.52F);
			color[3] = new Color(0, 0, 0.1F, 0.61F);
			color[4] = new Color(0, 0, 0.1F, 0.69F);
			color[5] = new Color(0, 0, 0.1F, 0.76F);
			color[6] = new Color(0, 0, 0.1F, 0.82F);
			color[7] = new Color(0, 0, 0.1F, 0.87F);
			color[8] = new Color(0, 0, 0.1F, 0.91F);
			color[9] = new Color(0, 0, 0.1F, 0.94F);
			color[10] = new Color(0, 0, 0.1F, 0.96F);
			color[11] = new Color(0, 0, 0.1F, 0.97F);

			fraction[0] = 0F;
			fraction[1] = 0.4F;
			fraction[2] = 0.5F;
			fraction[3] = 0.6F;
			fraction[4] = 0.65F;
			fraction[5] = 0.7F;
			fraction[6] = 0.75F;
			fraction[7] = 0.8F;
			fraction[8] = 0.85F;
			fraction[9] = 0.9F;
			fraction[10] = 0.95F;
			fraction[11] = 1F;

			// CREATE A GRADATION PAINT SETTINGS FOR THE LIGHT CIRCLE
			RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY,
					gp.player.currentLight.lightRadius, fraction, color);

			// SET THE GRADIENT DATA ON G2
			g2.setPaint(gPaint);
		}

		g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

		g2.dispose();

	}

	public void resetDay() {
		dayState = DAY;
		filterAlpha = 0f;
	}
	
	public void update() {

		if (gp.player.lightIsUpdated == true) {
			setLightSource();
			gp.player.lightIsUpdated = false;
		}

		// CHECK THE STATE OF DAYTIME
		if (dayState == DAY) {
			dayCounter++;
			if (dayCounter > 1200) {
				dayState = DUSK;
				dayCounter = 0;
			}
		}
		if (dayState == DUSK) {
			filterAlpha += 0.0005F;
			if (filterAlpha > 1F) {
				filterAlpha = 1F;
				dayState = NIGHT;
			}
		}
		if (dayState == NIGHT) {
			dayCounter++;
			if (dayCounter > 1200) {
				dayState = DAWN;
				dayCounter = 0;
			}
		}
		if (dayState == DAWN) {
			filterAlpha -= 0.0005F;
			if (filterAlpha < 0F) {
				filterAlpha = 0F;
				dayState = DAY;
			}
		}
	}

	public void draw(Graphics2D g2) {
		g2.setComposite(
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
		g2.drawImage(darknessFilter, 0, 0, null);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));

		// DEBUG
		String situation = "";

		switch (dayState) {
		case DAY:
			situation = "Day";
			break;
		case DUSK:
			situation = "Dusk";
			break;
		case NIGHT:
			situation = "Night";
			break;
		case DAWN:
			situation = "Dawn";
			break;
		}
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(50F));
		g2.drawString(situation, 800, 500);

	}

}
