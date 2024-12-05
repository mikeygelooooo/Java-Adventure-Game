package main;

import entity.Entity;
import object.OBJ_Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;

    BufferedImage heart_full, heart_half, heart_blank;

    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

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
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
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
            drawMessage();
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

        // Character State
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
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

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.BLACK);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.WHITE);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;

                messageCounter.set(i, counter);

                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
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

    public void drawCharacterScreen() {
        // Frame
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32f));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // Names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);

        // Values
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 14, null);
        textY += gp.tileSize;

        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 14, null);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 220);
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

    public int getXforAlignToRightText(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - length;

        return x;
    }
}
