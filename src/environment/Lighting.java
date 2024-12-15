package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;

    BufferedImage darknessFilter;

    public int dayCounter;
    public float filterAlpha = 0f;

    // Day State
    public final int day = 0;
    public final int dusk = 1;
    public final int night = 2;
    public final int dawn = 3;
    public int dayState = day;

    public Lighting(GamePanel gp) {
        this.gp = gp;

        setLightSource();
    }

    public void setLightSource() {
        // Create Buffered Image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        if (gp.player.currentLight == null) {
            g2.setColor(new Color(0, 0, 0.1F, 0.97F));
        } else {
            // Get the Center Coordinates of the Light Circle
            int centerX = gp.player.screenX + (gp.tileSize / 2);
            int centerY = gp.player.screenY + (gp.tileSize / 2);

            // Create Gradation Effect
            Color[] color = new Color[12];
            float[] fraction = new float[12];

            color[0] = new Color(0, 0, 0.1F, 0.1F);
            color[1] = new Color(0, 0, 0.1F, 0.42F);
            color[2] = new Color(0, 0, 0.1F, 0.52F);
            color[3] = new Color(0, 0, 0.1F, 0.61F);
            color[4] = new Color(0, 0, 0.1F, 0.69F);
            color[5] = new Color(0, 0, 0.1F, 0.76F);
            color[6] = new Color(0, 0, 0.1F, 0.82F);
            color[7] = new Color(0, 0, 0.1F, 0.87F);
            color[8] = new Color(0, 0, 0.1F, 0.91F);
            color[9] = new Color(0, 0, 0.1F, 0.92F);
            color[10] = new Color(0, 0, 0.1F, 0.93F);
            color[11] = new Color(0, 0, 0.1F, 0.94F);

            fraction[0] = 0f;
            fraction[1] = 0.4f;
            fraction[2] = 0.5f;
            fraction[3] = 0.6f;
            fraction[4] = 0.65f;
            fraction[5] = 0.7f;
            fraction[6] = 0.75f;
            fraction[7] = 0.8f;
            fraction[8] = 0.85f;
            fraction[9] = 0.9f;
            fraction[10] = 0.95f;
            fraction[11] = 1f;

            // Create Gradation Paint Settings
            RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);

            // Set the Gradient Data on g2
            g2.setPaint(gPaint);
        }

        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.dispose();
    }

    public void resetDay() {
        dayState = day;
        filterAlpha = 0f;
    }

    public void update() {
        if (gp.player.lightUpdated) {
            setLightSource();

            gp.player.lightUpdated = false;
        }

        // Check the state of the day
        if (dayState == day) {
            dayCounter++;

            if (dayCounter > 3600) {
                dayState = dusk;

                dayCounter = 0;
            }
        }
        if (dayState == dusk) {
            filterAlpha += 0.001f;

            if (filterAlpha > 1F) {
                filterAlpha = 1F;

                dayState = night;
            }
        }
        if (dayState == night) {
            dayCounter++;

            if (dayCounter > 3600) {
                dayState = dawn;

                dayCounter = 0;
            }
        }
        if (dayState == dawn) {
            filterAlpha -= 0.001f;

            if (filterAlpha <= 0F) {
                filterAlpha = 0F;

                dayState = day;
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (gp.currentArea == gp.outside) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));

        }
        if (gp.currentArea == gp.outside || gp.currentArea == gp.dungeon) {
            g2.drawImage(darknessFilter, 0, 0, null);
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }
}
