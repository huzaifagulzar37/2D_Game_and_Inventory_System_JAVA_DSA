package main;

import monster.MON_SKEL;
import object.OBJ_DOOR;
import object.OBJ_HEART;
import object.OBJ_KEY;
import object.OBJ_CHEST;
import object.OBJ_COIN;

public class AssetSet {
    GamePanel gp;

    public AssetSet(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {
        gp.obj[0] = new OBJ_KEY();
        gp.obj[0].worldX = 23 * gp.tilesize;
        gp.obj[0].worldY = 7 * gp.tilesize;

        gp.obj[1] = new OBJ_KEY();
        gp.obj[1].worldX = 23 * gp.tilesize;
        gp.obj[1].worldY = 40 * gp.tilesize;

        gp.obj[2] = new OBJ_DOOR();
        gp.obj[2].worldX = 10 * gp.tilesize;
        gp.obj[2].worldY = 11 * gp.tilesize;

        gp.obj[3] = new OBJ_CHEST();
        gp.obj[3].worldX = 10 * gp.tilesize;
        gp.obj[3].worldY = 7 * gp.tilesize;
        
        gp.obj[4] = new OBJ_COIN(gp);
		gp.obj[4].worldX=10*gp.tilesize;
		gp.obj[4].worldY=10*gp.tilesize;
		
		gp.obj[5] = new OBJ_HEART(gp);
		gp.obj[5].worldX = 23 * gp.tilesize;
		gp.obj[5].worldY = 41 * gp.tilesize;
    }

    public void setMonster() {
        gp.monster[0] = new MON_SKEL(gp);
        gp.monster[0].worldX = gp.tilesize * 23;
        gp.monster[0].worldY = gp.tilesize * 36;

        gp.monster[1] = new MON_SKEL(gp);
        gp.monster[1].worldX = gp.tilesize * 23;
        gp.monster[1].worldY = gp.tilesize * 37;
    }
}
