package main;

import object.OBJ_Heart;
import object.SuperObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;

    BufferedImage heart_full, heart_half, heart_blank;

    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;

    public boolean gameFinished = false;

    public String currentDialog = "";

    public int commandNum = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);

        } catch (FontFormatException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

        // Creat HUD Object
        SuperObject heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics g2) {
        this.g2 = (Graphics2D) g2;

        g2.setFont(maruMonica);
        g2.setColor(Color.WHITE);

        // Title State
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // Play State
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
        }

        // Pause State
        if (gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        // Dialog State
        if (gp.gameState == gp.dialogState) {
            drawPlayerLife();
            drawDialogScreen();
        }
    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;

        int i = 0;

        // Draw Max Life
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);

            i++;

            x += gp.tileSize;
        }

        x = gp.tileSize / 2;
        y = gp.tileSize / 2;

        i = 0;

        // Draw Current Life
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, null);

            i++;

            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }

            i++;

            x += gp.tileSize;
        }
    }

    public void drawTitleScreen() {
        // Background
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Title Text
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));

        String text = "Pixel Odyssey";

        int x = getXforCenteredText(text);
        int y = gp.tileSize * 3;

        // Text Shadow
        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 5, y + 5);

        // Text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // Character Image
        x = (gp.screenWidth / 2) - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;

        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "New Game";
        x = getXforCenteredText(text);
        y += (int) (gp.tileSize * 3.5);
        g2.drawString(text, x, y);

        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "Load Game";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        text = "Quit";
        x = getXforCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80f));

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
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32f));

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
