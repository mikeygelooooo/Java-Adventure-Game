package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.*;
import tile_interactive.IT_DryTree;
import tile_interactive.InteractiveTile;

public class AssetSetter {
    GamePanel gp;

    public AssetSetter(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        int i = 0;

        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize * 33;
        gp.obj[i].worldY = gp.tileSize * 7;
        i++;
    }

    public void setNPC() {
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;
    }

    public void setMonster() {
        int i = 0;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 21;
        gp.monster[i].worldY = gp.tileSize * 38;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 23;
        gp.monster[i].worldY = gp.tileSize * 42;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 24;
        gp.monster[i].worldY = gp.tileSize * 37;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 34;
        gp.monster[i].worldY = gp.tileSize * 42;
        i++;

        gp.monster[i] = new MON_GreenSlime(gp);
        gp.monster[i].worldX = gp.tileSize * 38;
        gp.monster[i].worldY = gp.tileSize * 42;
        i++;
    }

    public void setInteractiveTile() {
        int i = 0;

        gp.iTile[i] = new IT_DryTree(gp, 27, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 28, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 29, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 30, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 31, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 32, 12);
        i++;
        gp.iTile[i] = new IT_DryTree(gp, 33, 12);
        i++;

        gp.iTile[i] = new IT_DryTree(gp, 31, 21);
    }
}
