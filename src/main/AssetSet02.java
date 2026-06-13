package main;
import monster.MON_SKEL;
import object.OBJ_DOOR;
import object.OBJ_HEART;
import object.OBJ_KEY;
import object.OBJ_CHEST;
import object.OBJ_COIN;
public class AssetSet02 {
    GamePanel gp;
    public AssetSet02(GamePanel gp) {
        this.gp = gp;
    }
    public void setObject() {
        // Keys
        gp.obj[0] = new OBJ_KEY(); gp.obj[0].worldX = 25 * gp.tilesize; gp.obj[0].worldY = 28 * gp.tilesize; // Near the door
        gp.obj[1] = new OBJ_KEY(); gp.obj[1].worldX = 23 * gp.tilesize; gp.obj[1].worldY = 25 * gp.tilesize; // Inside central structure
        gp.obj[2] = new OBJ_KEY(); gp.obj[2].worldX = 15 * gp.tilesize; gp.obj[2].worldY = 15 * gp.tilesize; // On grass
        gp.obj[3] = new OBJ_KEY(); gp.obj[3].worldX = 35 * gp.tilesize; gp.obj[3].worldY = 35 * gp.tilesize; // On sand
        
        // Doors
        gp.obj[4] = new OBJ_DOOR(); gp.obj[4].worldX = 25 * gp.tilesize; gp.obj[4].worldY = 29 * gp.tilesize; // Entrance of central structure
        
        // Chests
        gp.obj[5] = new OBJ_CHEST(); gp.obj[5].worldX = 26 * gp.tilesize; gp.obj[5].worldY = 25 * gp.tilesize; // Inside central structure
        
        // Coins
        gp.obj[6] = new OBJ_COIN(gp); gp.obj[6].worldX = 20 * gp.tilesize; gp.obj[6].worldY = 20 * gp.tilesize;
        gp.obj[7] = new OBJ_COIN(gp); gp.obj[7].worldX = 30 * gp.tilesize; gp.obj[7].worldY = 20 * gp.tilesize;
        gp.obj[8] = new OBJ_COIN(gp); gp.obj[8].worldX = 20 * gp.tilesize; gp.obj[8].worldY = 30 * gp.tilesize;
        gp.obj[9] = new OBJ_COIN(gp); gp.obj[9].worldX = 30 * gp.tilesize; gp.obj[9].worldY = 30 * gp.tilesize;
        gp.obj[10] = new OBJ_COIN(gp); gp.obj[10].worldX = 10 * gp.tilesize; gp.obj[10].worldY = 10 * gp.tilesize;
        gp.obj[11] = new OBJ_COIN(gp); gp.obj[11].worldX = 40 * gp.tilesize; gp.obj[11].worldY = 40 * gp.tilesize;
        gp.obj[12] = new OBJ_COIN(gp); gp.obj[12].worldX = 5 * gp.tilesize; gp.obj[12].worldY = 25 * gp.tilesize;
        gp.obj[13] = new OBJ_COIN(gp); gp.obj[13].worldX = 45 * gp.tilesize; gp.obj[13].worldY = 25 * gp.tilesize;

        // Hearts
        gp.obj[14] = new OBJ_HEART(gp); gp.obj[14].worldX = 18 * gp.tilesize; gp.obj[14].worldY = 18 * gp.tilesize;
        gp.obj[15] = new OBJ_HEART(gp); gp.obj[15].worldX = 32 * gp.tilesize; gp.obj[15].worldY = 32 * gp.tilesize;
        gp.obj[16] = new OBJ_HEART(gp); gp.obj[16].worldX = 25 * gp.tilesize; gp.obj[16].worldY = 10 * gp.tilesize;
    }
    public void setMonster() {
        // Skeletons
        gp.monster[0] = new MON_SKEL(gp); gp.monster[0].worldX = gp.tilesize * 25; gp.monster[0].worldY = gp.tilesize * 20; // Guarding central structure
        gp.monster[1] = new MON_SKEL(gp); gp.monster[1].worldX = gp.tilesize * 10; gp.monster[1].worldY = gp.tilesize * 15; // On grass
        gp.monster[2] = new MON_SKEL(gp); gp.monster[2].worldX = gp.tilesize * 40; gp.monster[2].worldY = gp.tilesize * 35; // On grass
        gp.monster[3] = new MON_SKEL(gp); gp.monster[3].worldX = gp.tilesize * 15; gp.monster[3].worldY = gp.tilesize * 30; // On sand
        gp.monster[4] = new MON_SKEL(gp); gp.monster[4].worldX = gp.tilesize * 35; gp.monster[4].worldY = gp.tilesize * 10; // On sand
        gp.monster[5] = new MON_SKEL(gp); gp.monster[5].worldX = gp.tilesize * 20; gp.monster[5].worldY = gp.tilesize * 5; // On sand
        gp.monster[6] = new MON_SKEL(gp); gp.monster[6].worldX = gp.tilesize * 30; gp.monster[6].worldY = gp.tilesize * 45; // On grass
    }
}
