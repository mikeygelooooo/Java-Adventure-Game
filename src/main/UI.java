package main;

import java.awt.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;

    public String currentDialog = "";

    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics g2) {
        this.g2 = (Graphics2D) g2;

        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);

        // Play State
        if (gp.gameState == gp.playState) {

        }

        // Pause State
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
        }

        // Dialog State
        if (gp.gameState == gp.dialogState) {
            drawDialogScreen();
        }
    }

    public void drawPauseScreen() {
        g2.setFont(arial_80B);

        String text = "Paused";

        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogScreen() {
        // Dialog Window
        int x = gp.tileSize * 2;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;

        drawSubWindow(x, y, width, height);

        // Dialog Text
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28f));

        x += gp.tileSize / 2;
        y += gp.tileSize;

        for (String line : currentDialog.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = g2.getFontMetrics().stringWidth(text);
        int x = (gp.screenWidth / 2) - (length / 2);

        return x;
    }
}
