package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class Entity {
    public int type;
    public final int type_player = 0;
    public final int type_npc = 1;
    public final int type_monster = 2;
    public int screenX;
    public int screenY;
    GamePanel gp;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public int solidAreaDeafultX, solidAreaDeafultY;
    public boolean collisionOn = false;
    public int maxLife;
    public int life;
    public String name;
    public int actionLockCounter = 0;
    public UtilityTool uTool = new UtilityTool();

    public BufferedImage setup(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
            image = uTool.scaleImage(image, gp.tilesize, gp.tilesize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {}

    public void draw(Graphics2D g2) {
        screenX = worldX - gp.player.worldX + gp.player.screenX;
        screenY = worldY - gp.player.worldY + gp.player.screenY;
        BufferedImage imageToDraw = down1;

        if (direction != null) {
            switch (direction) {
                case "up": imageToDraw = (spriteNum == 1) ? up1 : up2; break;
                case "down": imageToDraw = (spriteNum == 1) ? down1 : down2; break;
                case "left": imageToDraw = (spriteNum == 1) ? left1 : left2; break;
                case "right": imageToDraw = (spriteNum == 1) ? right1 : right2; break;
            }
        }
        g2.drawImage(imageToDraw, screenX, screenY, gp.tilesize, gp.tilesize, null);
    }
}
