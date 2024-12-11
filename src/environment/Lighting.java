package environment;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage darknessFilter;

    public Lighting(GamePanel gp, int circleSize) {
        // Create Buffered Image
        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();

        // Create Screen-Sized Rectangle Area
        Area screenArea = new Area(new Rectangle2D.Double(0, 0, gp.screenWidth, gp.screenHeight));


        // Get the Center Coordinates of the Light Circle
        int centerX = gp.player.screenX + (gp.tileSize / 2);
        int centerY = gp.player.screenY + (gp.tileSize / 2);

        // Get the Top-Left Coordinates pf the Light Circle
        double x = centerX - ((double) circleSize / 2);
        double y = centerY - ((double) circleSize / 2);

        // Create the Light Circle Shape
        Shape circleShape = new Ellipse2D.Double(x, y, circleSize, circleSize);

        // Create the Light Circle Area
        Area lightArea = new Area(circleShape);

        // Subtract Circle Area from Screen Area
        screenArea.subtract(lightArea);

        // Create Gradation Effect
        Color[] color = new Color[12];
        float[] fraction = new float[12];

        color[0] = new Color(0, 0, 0, 0.1F);
        color[1] = new Color(0, 0, 0, 0.42F);
        color[2] = new Color(0, 0, 0, 0.52F);
        color[3] = new Color(0, 0, 0, 0.61F);
        color[4] = new Color(0, 0, 0, 0.69F);
        color[5] = new Color(0, 0, 0, 0.76F);
        color[6] = new Color(0, 0, 0, 0.82F);
        color[7] = new Color(0, 0, 0, 0.87F);
        color[8] = new Color(0, 0, 0, 0.91F);
        color[9] = new Color(0, 0, 0, 0.94F);
        color[10] = new Color(0, 0, 0, 0.96F);
        color[11] = new Color(0, 0, 0, 0.98F);

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
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, (circleSize / 2), fraction, color);

        // Set the Gradient Data on g2
        g2.setPaint(gPaint);

        // Draw the Light Circle
        g2.fill(lightArea);

        // Draw the Screen Rectangle without the Light Circle Area
        g2.fill(screenArea);

        g2.dispose();
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(darknessFilter, 0, 0, null);
    }
}
