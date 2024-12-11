package main;

import entity.Entity;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;

    BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank, coin;

    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished = false;

    public String currentDialog = "";

    public int commandNum = 0;

    public int playerSlotCol = 0;
    public int playerSlotRow = 0;

    public int npcSlotCol = 0;
    public int npcSlotRow = 0;

    int subState = 0;

    int counter = 0;

    public Entity npc;

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
        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
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
            drawDialogScreen();
        }

        // Character State
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory(gp.player, true);
        }

        // Options State
        if (gp.gameState == gp.optionsState) {
            drawOptionsScreen();
        }

        // Game Over State
        if (gp.gameState == gp.gameOverState) {
            drawGameOverScreen();
        }

        // Transition State
        if (gp.gameState == gp.transitionState) {
            drawTransition();
        }

        // Trade State
        if (gp.gameState == gp.tradeState) {
            drawTradeScreen();
        }

        // Sleep State
        if (gp.gameState == gp.sleepState) {
            drawSleepScreen();
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

        // Draw Max Mana
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);

        i = 0;

        while (i < gp.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, null);

            i++;

            x += 35;
        }

        // Draw Current Mana
        x = (gp.tileSize / 2) - 5;
        y = (int) (gp.tileSize * 1.5);

        i = 0;

        while (i < gp.player.mana) {
            g2.drawImage(crystal_full, x, y, null);

            i++;

            x += 35;
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
        int x = gp.tileSize * 3;
        int y = gp.tileSize / 2;
        int width = gp.screenWidth - (gp.tileSize * 6);
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
        final int frameX = gp.tileSize * 2;
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
        g2.drawString("Mana", textX, textY);
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
        textY += lineHeight + 10;
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

        value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
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

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 24, null);
        textY += gp.tileSize;

        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 24, null);
    }

    public void drawInventory(Entity entity, boolean cursor) {
        int frameX = 0;
        int frameY = 0;
        int frameWidth = 0;
        int frameHeight = 0;
        int slotCol = 0;
        int slotRow = 0;

        if (entity == gp.player) {
            frameX = gp.tileSize * 12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        } else {
            frameX = gp.tileSize * 2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize * 6;
            frameHeight = gp.tileSize * 5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        // Frame
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXstart = frameX + 19;
        final int slotYstart = frameY + 19;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize + 3;

        // Draw Player Items
        for (int i = 0; i < entity.inventory.size(); i++) {
            // Equip Cursor
            if (entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentShield || entity.inventory.get(i) == entity.currentLight) {
                g2.setColor(new Color(240, 190, 90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }

            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            // Display Amount
            if (entity == gp.player && entity.inventory.get(i).amount > 1) {
                g2.setFont(g2.getFont().deriveFont(32F));

                int amountX;
                int amountY;

                String s = "" + entity.inventory.get(i).amount;
                amountX = getXforAlignToRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;

                // Shadow
                g2.setColor(new Color(60, 60, 60));
                g2.drawString(s, amountX, amountY);

                // Number
                g2.setColor(Color.WHITE);
                g2.drawString(s, amountX - 3, amountY - 3);
            }

            slotX += slotSize;

            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;

                slotY += slotSize;
            }
        }

        // Cursor
        if (cursor) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            // Draw Cursor
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

            // Description Frame
            int dframeX = frameX;
            int dframeY = frameY + frameHeight;
            int dframeWidth = frameWidth;
            int dframeHeight = gp.tileSize * 3;

            // Description Text
            int textX = dframeX + 20;
            int textY = dframeY + gp.tileSize;

            g2.setFont(g2.getFont().deriveFont(28F));

            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);

            if (itemIndex < entity.inventory.size()) {
                drawSubWindow(dframeX, dframeY, dframeWidth, dframeHeight);

                for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
            }
        }
    }

    public void drawGameOverScreen() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;

        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));

        text = "GAME OVER";

        // Shadow
        g2.setColor(Color.BLACK);
        x = getXforCenteredText(text);
        y = gp.tileSize * 4;
        g2.drawString(text, x, y);

        // Text
        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 4, y - 4);

        // Retry
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));
        text = "RETRY";
        x = getXforCenteredText(text);
        y += gp.tileSize * 4;
        g2.drawString(text, x, y);

        if (commandNum == 0) {
            g2.drawString(">", x - 40, y);
        }

        // Return to Title Screen
        text = "QUIT";
        x = getXforCenteredText(text);
        y += 55;
        g2.drawString(text, x, y);

        if (commandNum == 1) {
            g2.drawString(">", x - 40, y);
        }
    }

    public void drawOptionsScreen() {
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32F));

        // Sub-Window
        int frameX = gp.tileSize * 6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 8;
        int frameHeight = gp.tileSize * 10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                options_top(frameX, frameY);

                break;
            case 1:
                options_fullScreenNotification(frameX, frameY);

                break;
            case 2:
                options_control(frameX, frameY);

                break;
            case 3:
                options_endGameConfirmation(frameX, frameY);

                break;
        }

        gp.keyH.enterPressed = false;
    }

    public void options_top(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "OPTIONS";

        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;

        g2.drawString(text, textX, textY);

        // Fullscreen Option
        textX = frameX + gp.tileSize;
        textY += gp.tileSize * 2;
        g2.drawString("Full Screen", textX, textY);

        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                if (!gp.fullScreenOn) {
                    gp.fullScreenOn = true;
                } else {
                    gp.fullScreenOn = false;
                }

                subState = 1;
            }
        }

        // Music Option
        textY += gp.tileSize;
        g2.drawString("Music", textX, textY);

        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);
        }

        // Sound Effect Option
        textY += gp.tileSize;
        g2.drawString("Sounds", textX, textY);

        if (commandNum == 2) {
            g2.drawString(">", textX - 25, textY);
        }

        // Control Option
        textY += gp.tileSize;
        g2.drawString("Controls", textX, textY);

        if (commandNum == 3) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                subState = 2;

                commandNum = 0;
            }
        }

        // Quit Option
        textY += gp.tileSize;
        g2.drawString("Quit Game", textX, textY);

        if (commandNum == 4) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                subState = 3;

                commandNum = 0;
            }
        }

        // Continue Option
        textY += gp.tileSize * 2;
        g2.drawString("CONTINUE", textX, textY);

        if (commandNum == 5) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                gp.gameState = gp.playState;

                commandNum = 0;
            }
        }

        // Fullscreen Check Box
        textX = frameX + (int) (gp.tileSize * 4.5);
        textY = frameY + (gp.tileSize * 2) + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);

        if (gp.fullScreenOn) {
            g2.fillRect(textX, textY, 24, 24);
        }

        // Music Volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        // Sound Effects Volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        volumeWidth = 24 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }

    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialog = "Change will take\neffect after restarting\nthe game";

        for (String line : currentDialog.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Return
        textY = frameY + gp.tileSize * 9;
        g2.drawString("RETURN", textX, textY);

        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                subState = 0;
            }
        }
    }

    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        // Title
        String text = "CONTROLS";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;

        g2.drawString("Move", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Interact", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Shoot", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Inventory", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Pause", textX, textY);
        textY += gp.tileSize;
        g2.drawString("Options", textX, textY);
        textY += gp.tileSize;

        textX = frameX + gp.tileSize * 5;
        textY = frameY + gp.tileSize * 2;
        g2.drawString("W-A-S-D", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY);
        textY += gp.tileSize;
        g2.drawString("F", textX, textY);
        textY += gp.tileSize;
        g2.drawString("C", textX, textY);
        textY += gp.tileSize;
        g2.drawString("P", textX, textY);
        textY += gp.tileSize;
        g2.drawString("ESCAPE", textX, textY);
        textY += gp.tileSize;

        // Return
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize * 9;
        g2.drawString("RETURN", textX, textY);

        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                subState = 0;

                commandNum = 3;
            }
        }
    }

    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize * 3;

        currentDialog = "Quit the Game and\nReturn to Title Screen?";

        for (String line : currentDialog.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        // Yes
        String text = "YES";
        textX = getXforCenteredText(text);
        textY += gp.tileSize * 3;
        g2.drawString(text, textX, textY);

        if (commandNum == 0) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                subState = 0;

                gp.stopMusic();

                gp.gameState = gp.titleState;
            }
        }

        // No
        text = "NO";
        textX = getXforCenteredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);

        if (commandNum == 1) {
            g2.drawString(">", textX - 25, textY);

            if (gp.keyH.enterPressed) {
                subState = 0;

                commandNum = 4;
            }
        }
    }

    public void drawTransition() {
        counter++;

        g2.setColor(new Color(0, 0, 0, counter * 5));

        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        if (counter == 50) {
            counter = 0;

            gp.gameState = gp.playState;

            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;

            gp.eHandler.previousEventX = gp.player.worldX;
            gp.eHandler.previousEventY = gp.player.worldY;
        }
    }

    public void drawTradeScreen() {
        switch (subState) {
            case 0:
                trade_select();

                break;
            case 1:
                trade_buy();

                break;
            case 2:
                trade_sell();

                break;
        }

        gp.keyH.enterPressed = false;
    }

    public void trade_select() {
        drawDialogScreen();

        // Draw Window
        int x = gp.tileSize * 15;
        int y = gp.tileSize * 4;
        int width = gp.tileSize * 3;
        int height = (int) (gp.tileSize * 3.5);

        drawSubWindow(x, y, width, height);

        // Draw Text
        x += gp.tileSize;
        y += gp.tileSize;

        g2.drawString("BUY", x, y);
        if (commandNum == 0) {
            g2.drawString(">", x - 24, y);

            if (gp.keyH.enterPressed) {
                subState = 1;
            }
        }
        y += gp.tileSize;

        g2.drawString("SELL", x, y);
        if (commandNum == 1) {
            g2.drawString(">", x - 24, y);

            if (gp.keyH.enterPressed) {
                subState = 2;
            }
        }
        y += gp.tileSize;

        g2.drawString("LEAVE", x, y);
        if (commandNum == 2) {
            g2.drawString(">", x - 24, y);

            if (gp.keyH.enterPressed) {
                commandNum = 0;

                gp.gameState = gp.dialogState;

                currentDialog = "Safe travels! And don’t forget who gave you that deal.";
            }
        }
    }

    public void trade_buy() {
        // Draw Player Inventory
        drawInventory(gp.player, false);

        // Draw NPC Inventory
        drawInventory(npc, true);

        // Draw Hint Window
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Return", x + 24, y + 60);

        // Draw Coins Window
        x = gp.tileSize * 12;

        drawSubWindow(x, y, width, height);
        g2.drawString("COINS: " + gp.player.coin, x + 24, y + 60);

        // Draw Price Window
        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);

        if (itemIndex < npc.inventory.size()) {
            x = (int) (gp.tileSize * 5.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;

            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text = "" + price;

            x = getXforAlignToRightText(text, (gp.tileSize * 8) - 20);
            g2.drawString(text, x, y + 34);

            // Buy an Item
            if (gp.keyH.enterPressed) {
                if (npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    gp.gameState = gp.dialogState;
                    currentDialog = "You don't have enough coins!";
                    drawDialogScreen();
                } else {
                    if (gp.player.canObtainItem(npc.inventory.get(itemIndex))) {
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    } else {
                        subState = 0;
                        gp.gameState = gp.dialogState;
                        currentDialog = "Your inventory is full!";
                    }
                }
            }
        }
    }

    public void trade_sell() {
        // Draw Player Inventory
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;

        // Draw Hint Window
        x = gp.tileSize * 2;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;

        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] Return", x + 24, y + 60);

        // Draw Coins Window
        x = gp.tileSize * 12;

        drawSubWindow(x, y, width, height);
        g2.drawString("COINS: " + gp.player.coin, x + 24, y + 60);

        // Draw Price Window
        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);

        if (itemIndex < gp.player.inventory.size()) {
            x = (int) (gp.tileSize * 15.5);
            y = (int) (gp.tileSize * 5.5);
            width = (int) (gp.tileSize * 2.5);
            height = gp.tileSize;

            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x + 10, y + 8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price / 2;
            String text = "" + price;

            x = getXforAlignToRightText(text, (gp.tileSize * 18) - 20);
            g2.drawString(text, x, y + 34);

            // Sell an Item
            if (gp.keyH.enterPressed) {
                if (gp.player.inventory.get(itemIndex) == gp.player.currentWeapon || gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                    commandNum = 0;
                    subState = 0;
                    gp.gameState = gp.dialogState;
                    currentDialog = "You cannot sell an equipped item!";
                } else {
                    if (gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount --;
                    } else {
                        gp.player.inventory.remove(itemIndex);
                    }

                    gp.player.coin += price;
                }
            }
        }
    }

    public void drawSleepScreen() {
        counter++;

        if (counter < 120) {
            gp.eManager.lighting.filterAlpha += 0.01F;

            if (gp.eManager.lighting.filterAlpha > 1F) {
                gp.eManager.lighting.filterAlpha = 1F;
            }
        }
        if (counter >= 120) {
            gp.eManager.lighting.filterAlpha -= 0.01F;

            if (gp.eManager.lighting.filterAlpha <= 0F) {
                gp.eManager.lighting.filterAlpha = 0F;

                counter = 0;

                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                gp.eManager.lighting.dayCounter = 0;

                gp.gameState = gp.playState;

                gp.player.getPlayerImage();
            }
        }
    }

    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        int itemIndex = slotCol + (slotRow * 5);

        return itemIndex;
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
