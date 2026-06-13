package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_SKEL extends Entity {
	GamePanel gp;

    public MON_SKEL(GamePanel gp){
        super(gp);
        this.gp=gp;

        type=type_monster;
        name = "Skeleton";
        speed= 1;
        maxLife = 4;
        life=maxLife;
        solidArea = new java.awt.Rectangle();
        solidArea.x = 3;
        solidArea.y=10;
        solidArea.width =42;
        solidArea.height=30;
        solidAreaDeafultX = solidArea.x;
        solidAreaDeafultY = solidArea.y;
        direction = "down";


        getImage();
    }
    public void getImage(){
        up1 =setup("/monster/skeletonlord_up_1");
        up2 =setup("/monster/skeletonlord_up_2");
        down1 =setup("/monster/skeletonlord_down_1");
        down2 =setup("/monster/skeletonlord_down_2");
        left1 =setup("/monster/skeletonlord_left_1");
        left2 =setup("/monster/skeletonlord_left_2");
        right1 =setup("/monster/skeletonlord_right_1");
        right2 =setup("/monster/skeletonlord_right_2");
        
    }
    public void setAction(){
        actionLockCounter++;

    if (actionLockCounter == 120) {

    Random random = new Random();
   int i = random.nextInt(100) + 1; 

    if (i <= 25) {
        direction = "up";
    }

    if (i > 25 && i <= 50) {
        direction = "down";
    }

    if (i > 50 && i <= 75) {
        direction = "left";
    }
    if (i > 75 && i <= 100) {
        direction = "right";
    }
    actionLockCounter = 0;
        }

    }
    @Override
    public void update() {
        setAction(); // choose random direction

        collisionOn = false;
        gp.cChecker.checkTile(this); // check walls/trees
        gp.cChecker.checkObject(this, false); // optional: collide with objects

        if(!collisionOn){
            switch(direction){
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        // damage player if touching
        java.awt.Rectangle monsterRect = new java.awt.Rectangle(
        	    worldX + solidArea.x,
        	    worldY + solidArea.y,
        	    solidArea.width,
        	    solidArea.height
        	);

        	// Create player rectangle
        	java.awt.Rectangle playerRect = new java.awt.Rectangle(
        	    gp.player.worldX + gp.player.solidArea.x,
        	    gp.player.worldY + gp.player.solidArea.y,
        	    gp.player.solidArea.width,
        	    gp.player.solidArea.height
        	);

        	// Check collision with player
        	if(monsterRect.intersects(playerRect) && !gp.player.invincible){
        	    gp.player.life -= 1;
        	    gp.player.invincible = true;
        	    gp.player.invincibleCounter = 0;
        	    gp.ui.addMessage(" Skeleton hit you!");

        	}
        	spriteCounter++;
        	if(spriteCounter > 20){ // change every 20 frames
        	    spriteNum = (spriteNum == 1) ? 2 : 1;
        	    spriteCounter = 0;
        	}
    }
}
