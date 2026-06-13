package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import object.OBJ_CHEST;
import object.OBJ_COIN;
import object.OBJ_HEART;
import object.SuperObject;
import object.Inventory;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    public int hasKey = 0;
    public int keysCollected = 0;
    public int monstersKilled = 0;
    public final int screenX;
    public final int screenY;
    public BufferedImage attackUp, attackDown, attackLeft, attackRight;
    public int coins = 0;

    // --- NEW: Inventory ---
    public Inventory inventory;

    public boolean invincible = false;
    public int invincibleCounter = 0;
    public BufferedImage attackImage;
    public boolean attacking = false;
    public int attackCounter = 0;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.gp = gp;
        this.keyH = keyH;

        solidArea = new java.awt.Rectangle(8, 16, 32, 32);
        solidAreaDeafultX = solidArea.x;
        solidAreaDeafultY = solidArea.y;

        screenX = gp.screenWidth / 2;
        screenY = gp.screenHeight / 2;

        // --- NEW: Initialize Inventory ---
        inventory = new Inventory(gp);

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tilesize * 23 - (gp.tilesize / 2);
        worldY = gp.tilesize * 21 - (gp.tilesize / 2);
        speed = 4;
        direction = "down";
        maxLife = 6;
        life = maxLife;
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));

            attackUp = ImageIO.read(getClass().getResourceAsStream("/player/attack_up.png"));
            attackDown = ImageIO.read(getClass().getResourceAsStream("/player/attack_down.png"));
            attackLeft = ImageIO.read(getClass().getResourceAsStream("/player/attack_left.png"));
            attackRight = ImageIO.read(getClass().getResourceAsStream("/player/attack_right.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) direction = "up";
            else if (keyH.downPressed) direction = "down";
            else if (keyH.leftPressed) direction = "left";
            else if (keyH.rightPressed) direction = "right";

            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            int monsterIndex = gp.cChecker.checkMonster(this, true);
            if(monsterIndex != 999 && !invincible){
                life -= 1;
                invincible = true;
                invincibleCounter = 0;
                gp.ui.addMessage("  Player damaged! HP: " + life);
            }
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 20) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }

        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

        if (keyH.attackPressed && !attacking) {
            attacking = true;
            attackCounter = 10;
            attack();
        }

        if (attacking) {
            attackCounter--;
            if (attackCounter <= 0) attacking = false;
        }

        // Shop and Inventory keys are now handled in KeyHandler state methods

        if (life <= 0) {
            life = 0;
            gp.currentState = gp.PAUSE;
            javax.swing.JOptionPane.showMessageDialog(gp, "💀 Game Over! Restarting the game...");
            gp.setupGame();
            gp.player.setDefaultValues();
            gp.currentState = gp.PLAY;
        }
    }

    // --- NEW: Use Item Logic ---
    public void useItem(String itemName) {
        if (inventory.contains(itemName)) {
            if (itemName.equals("Heart")) {
                if (life < maxLife) {
                    OBJ_HEART heart = new OBJ_HEART(gp);
                    heart.recoverHealth(this);
                    inventory.removeItem("Heart");
                } else {
                    gp.ui.addMessage("Health is already full!");
                }
            }
        } else {
            gp.ui.addMessage("No " + itemName + " in inventory!");
        }
    }

    public void attack() {
        for (int i = 0; i < gp.monster.length; i++) {
            if (gp.monster[i] != null) {
                java.awt.Rectangle attackArea = new java.awt.Rectangle(
                        worldX + solidArea.x - 16,
                        worldY + solidArea.y - 16,
                        solidArea.width + 32,
                        solidArea.height + 32
                );

                java.awt.Rectangle monsterRect = new java.awt.Rectangle(
                        gp.monster[i].worldX + gp.monster[i].solidArea.x,
                        gp.monster[i].worldY + gp.monster[i].solidArea.y,
                        gp.monster[i].solidArea.width,
                        gp.monster[i].solidArea.height
                );

                if (attackArea.intersects(monsterRect)) {
                    gp.monster[i].life -= 1;
                    System.out.println(gp.monster[i].name + " hit! Life: " + gp.monster[i].life);
                    gp.ui.addMessage(" Hit " + gp.monster[i].name);
                    if (gp.monster[i].life <= 0) {
                        String deadMonsterName = gp.monster[i].name;
                        gp.ui.addMessage( deadMonsterName + " defeated!");
                        gp.monster[i] = null;
                        monstersKilled++;
                    }
                }
            }
        }
    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            if ("Key".equals(objectName)) {
                hasKey++;
                keysCollected++;
                gp.obj[i] = null;
                System.out.println("Key picked! Keys: " + hasKey);
            } else if ("Door".equals(objectName) && hasKey > 0) {
                gp.obj[i] = null;
                hasKey--;
                System.out.println("Door opened! Keys left: " + hasKey);
            } else if ("Chest".equals(objectName)) {
                if (hasKey > 0) {
                    hasKey--;
                    gp.obj[i] = null;
                    coins += 500;
                    System.out.println("Chest opened! Coins: " + coins + " Keys left: " + hasKey);
                    for (int j = 0; j < gp.obj.length; j++) {
                        if (gp.obj[j] == null) {
                            gp.obj[j] = new OBJ_COIN(gp);
                            gp.obj[j].worldX = worldX;
                            gp.obj[j].worldY = worldY;
                            break;
                        }
                    }
                } else {
                    gp.ui.addMessage("You need a key!");
                }
            } else if ("Heart".equals(objectName)) {
                // Modified: Hearts go to inventory now
                inventory.addItem(gp.obj[i]);
                gp.obj[i] = null;
            } else if ("Coin".equals(objectName)) {
                coins += 100;
                gp.obj[i] = null;
                gp.ui.addMessage("Picked up a coin! Total: " + coins);
            }
        }
    }
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (attacking) {
            switch(direction) {
                case "up": image = attackUp; break;
                case "down": image = attackDown; break;
                case "left": image = attackLeft; break;
                case "right": image = attackRight; break;
            }
        } else {
            switch (direction) {
                case "up": image = (spriteNum == 1) ? up1 : up2; break;
                case "down": image = (spriteNum == 1) ? down1 : down2; break;
                case "left": image = (spriteNum == 1) ? left1 : left2; break;
                case "right": image = (spriteNum == 1) ? right1 : right2; break;
            }
        }

        g2.drawImage(image, screenX, screenY, gp.tilesize, gp.tilesize, null);
    }
}
