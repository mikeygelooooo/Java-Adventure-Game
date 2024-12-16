package main;

import entity.PlayerDummy;
import monster.MON_SkeletonLord;
import object.OBJ_BlueHeart;
import object.OBJ_Door_Iron;

import java.awt.*;

public class CutsceneManager {
    GamePanel gp;
    Graphics2D g2;

    public int sceneNum;
    public int scenePhase;

    int counter = 0;
    float alpha = 0F;
    int y;
    String endCredit;

    // Scene Number
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int ending = 2;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;

        endCredit = "IT 373A - CULMINATING ACTIVITY\n" +
                "BSIT 3B\n\n\n\n\n\n\n\n\n\n\n" +
                "PROJECT BY: GROUP 5\n\n\n\n" +
                "Leader:\n" +
                "Ochengco, Michael Angelo A.\n\n\n\n" +
                "Members:\n" +
                "Lee, Jade hart C.\n" +
                "Ladrera, Bevirlie Jane R.\n" +
                "Enverzo, Ralynn G.\n\n\n\n\n\n\n\n" +
                "THANKS FOR PLAYING!";
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch (sceneNum) {
            case skeletonLord:
                scene_skeletonLord();

                break;
            case ending:
                scene_ending();

                break;
        }
    }

    public void scene_skeletonLord() {
        if (scenePhase == 0) {
            gp.bossBattleOn = true;

            // Shut Door to Boss Room
            for (int i = 0; i < gp.obj[1].length; i++) {
                if (gp.obj[gp.currentMap][i] == null) {
                    gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
                    gp.obj[gp.currentMap][i].worldX = gp.tileSize * 25;
                    gp.obj[gp.currentMap][i].worldY = gp.tileSize * 28;
                    gp.obj[gp.currentMap][i].temp = true;
                    gp.playSE(21);

                    break;
                }
            }

            // Vacant NPC Slot for Dummy
            for (int i = 0; i < gp.npc[1].length; i++) {
                if (gp.npc[gp.currentMap][i] == null) {
                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction = gp.player.direction;

                    break;
                }
            }

            gp.player.drawing = false;

            scenePhase++;
        }

        if (scenePhase == 1) {
            gp.player.worldY -= 2;

            if (gp.player.worldY < gp.tileSize * 16) {
                scenePhase++;
            }
        }
        if (scenePhase == 2) {
            for (int i = 0; i < gp.monster[1].length; i++) {
                if (gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name.equals(MON_SkeletonLord.monName)) {
                    gp.monster[gp.currentMap][i].sleep = false;
                    gp.ui.npc = gp.monster[gp.currentMap][i];

                    scenePhase++;

                    break;
                }
            }
        }

        if (scenePhase == 3) {
            // The Boss Speaks
            gp.ui.drawDialogScreen();
        }
        if (scenePhase == 4) {
            // Return Camera to Player
            for (int i = 0; i < gp.npc[1].length; i++) {
                if (gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
                    gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
                    gp.player.worldY = gp.npc[gp.currentMap][i].worldY;

                    gp.npc[gp.currentMap][i] = null;

                    break;
                }
            }

            gp.player.drawing = true;

            sceneNum = NA;
            scenePhase = 0;

            gp.gameState = gp.playState;

            gp.stopMusic();
            gp.playMusic(22);
        }
    }

    public void scene_ending() {
        if (scenePhase == 0) {
            gp.stopMusic();
            gp.ui.npc = new OBJ_BlueHeart(gp);

            scenePhase++;
        }
        if (scenePhase == 1) {
            gp.ui.drawDialogScreen();
        }
        if (scenePhase == 2) {
            gp.playSE(4);

            scenePhase++;
        }
        if (scenePhase == 3) {
            if (counterReached(300)) {
                scenePhase++;
            }
        }
        if (scenePhase == 4) {
            alpha += 0.005F;

            if (alpha > 1F) {
                alpha = 1F;
            }

            drawBlackBackground(alpha);

            if (alpha == 1F) {
                alpha = 0F;

                scenePhase++;
            }
        }
        if (scenePhase == 5) {
            drawBlackBackground(1F);

            alpha += 0.005F;

            if (alpha > 1F) {
                alpha = 1F;
            }

            String text = "After the fierce battle with the Skeleton Lord,\n" +
                    "the brave adventurer finally found the legendary treasure.\n" +
                    "But the journey does not end here.\n" +
                    "There is still a whole world to be explored.";

            drawString(alpha, 38F, 200, text, 70);

            if (counterReached(600)) {
                gp.playMusic(0);
                scenePhase++;
            }
        }
        if (scenePhase == 6) {
            drawBlackBackground(1F);

            drawString(1F, 120F, gp.screenHeight / 2, "Pixel Odyssey", 40);

            if (counterReached(480)) {
                scenePhase++;
            }
        }
        if (scenePhase == 7) {
            drawBlackBackground(1F);

            y = gp.screenHeight / 2;

            drawString(1F, 38F, y, endCredit, 40);

            if (counterReached(480)) {
                scenePhase++;
            }
        }
        if (scenePhase == 8) {
            drawBlackBackground(1F);

            y--;

            drawString(1F, 38F, y, endCredit, 40);
        }
    }

    public boolean counterReached(int target) {
        boolean counterReached = false;

        counter++;

        if (counter > target) {
            counterReached = true;

            counter = 0;
        }

        return counterReached;
    }

    public void drawBlackBackground(float alpha) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }

    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));

        for (String line : text.split("\n")) {
            int x = gp.ui.getXforCenteredText(line);

            g2.drawString(line, x, y);

            y += lineHeight;
        }

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }
}
