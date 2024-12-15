package main;


import entity.Entity;

public class EventHandler {
    GamePanel gp;
    EventRect[][][] eventRect;
    Entity eventMaster;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;

    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;
        eventMaster = new Entity(gp);
        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        int map = 0;
        int col = 0;
        int row = 0;

        while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
            eventRect[map][col][row] = new EventRect();
            eventRect[map][col][row].x = 23;
            eventRect[map][col][row].y = 23;
            eventRect[map][col][row].width = 2;
            eventRect[map][col][row].height = 2;
            eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
            eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;

            col++;

            if (col == gp.maxWorldCol) {
                col = 0;

                row++;

                if (row == gp.maxWorldRow) {
                    row = 0;

                    map++;
                }
            }
        }

        setDialog();
    }

    public void setDialog() {
        eventMaster.dialogs[0][0] = "You fall into a pit!";

        eventMaster.dialogs[1][0] = "You drank from the Lake of Vitality!\nYou have recovered your health and mana!\n\n[Your progress has been saved!]";
    }

    public void checkEvent() {
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if (distance > gp.tileSize) {
            canTouchEvent = true;
        }

        if (canTouchEvent) {
            if (hit(0, 27, 16, "right")) {
                damagePit(gp.dialogState);
            } else if (hit(0, 23, 12, "up")) {
                healingPool(gp.dialogState);
            } else if (hit(0, 10, 39, "any")) {
                teleport(1, 12, 13, gp.indoor);
            } else if (hit(1, 12, 13, "any")) {
                teleport(0, 10, 39, gp.outside);
            } else if (hit(1, 12, 9, "up")) {
                speak(gp.npc[1][0]);
            } else if (hit(0, 12, 9, "any")) {
                teleport(2, 9, 41, gp.dungeon);
            } else if (hit(2, 9, 41, "any")) {
                teleport(0, 12, 9, gp.outside);
            } else if (hit(2, 8, 7, "any")) {
                teleport(3, 26, 41, gp.outside);
            } else if (hit(3, 26, 41, "any")) {
                teleport(2, 8, 7, gp.dungeon);
            }
        }
    }

    public boolean hit(int map, int col, int row, String reqDirection) {
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

            eventRect[map][col][row].x = col * gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row * gp.tileSize + eventRect[map][col][row].y;

            if (gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if (gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;
                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;

            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }



        return hit;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.playSE(6);
        eventMaster.startDialog(eventMaster, 0);
        gp.player.life -= 1;

        canTouchEvent = false;
    }

    public void healingPool(int gameState) {
        if (gp.keyH.enterPressed) {
            gp.gameState = gameState;
            gp.player.attackCanceled = true;
            gp.playSE(2);

            eventMaster.startDialog(eventMaster, 1);
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;

            // Monsters Respawn After Healing
            gp.aSetter.setMonster();

            // Save Game
            gp.saveLoad.save();
        }
    }

    public void teleport(int map, int col, int row, int area) {
        gp.gameState = gp.transitionState;
        gp.nextArea = area;

        tempMap = map;
        tempCol = col;
        tempRow = row;

        canTouchEvent = false;

        gp.playSE(13);
    }

    public void speak(Entity entity) {
        if (gp.keyH.enterPressed) {
            gp.gameState = gp.dialogState;
            gp.player.attackCanceled = true;

            entity.speak();
        }
    }
}
