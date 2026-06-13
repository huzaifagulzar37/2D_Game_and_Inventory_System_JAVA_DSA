package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;
import object.SuperObject;
import object.OBJ_HEART;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40, arial_80B;
    public ArrayList<String> message = new ArrayList<>();
    public ArrayList<Integer> messageCounter = new ArrayList<>();
    
    // UI State
    public int slotCol = 0;
    public int slotRow = 0;
    public int commandNum = 0;
    
    // Shop Items (Static list for now)
    public ArrayList<SuperObject> shopItems = new ArrayList<>();

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        
        // Initialize Shop Items
        shopItems.add(new OBJ_HEART(gp));
    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.white);

        if (gp.currentState == gp.PLAY) {
            drawMessages();
        }
        if (gp.currentState == gp.PAUSE) {
            drawPauseScreen();
        }
        if (gp.currentState == gp.INVENTORY) {
            drawInventory();
        }
        if (gp.currentState == gp.SHOP) {
            drawShop();
        }
    }

    public void drawMessages() {
        int messageX = gp.tilesize / 2;
        int messageY = gp.tilesize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);
                messageY += 30;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawPauseScreen() {
        g2.setFont(arial_80B);
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight / 2;
        g2.drawString(text, x, y);
    }

    public void drawInventory() {
        // Window
        int frameX = gp.tilesize * 9;
        int frameY = gp.tilesize;
        int frameWidth = gp.tilesize * 6;
        int frameHeight = gp.tilesize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Slots
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tilesize + 3;

        // Draw Items
        for (int i = 0; i < gp.player.inventory.getSize(); i++) {
            SuperObject item = gp.player.inventory.getItemAt(i);
            if (item != null) {
                g2.drawImage(item.image, slotX, slotY, null);
            }
            slotX += slotSize;
            //if (i == 4 || i == 9 || i == 14) {
            if((i +1) % 3 == 0) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        // Cursor
        int cursorX = slotXstart + (slotSize * slotCol);
        int cursorY = slotYstart + (slotSize * slotRow);
        int cursorWidth = gp.tilesize;
        int cursorHeight = gp.tilesize;

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // Description Window
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tilesize * 3;
        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

        int textX = dFrameX + 20;
        int textY = dFrameY + 40;
        g2.setFont(g2.getFont().deriveFont(20F));

        int itemIndex = getItemIndexOnSlot();
        if (itemIndex < gp.player.inventory.getSize()) {
            SuperObject selectedItem = gp.player.inventory.getItemAt(itemIndex);
            g2.drawString("[" + selectedItem.name + "]", textX, textY);
            g2.drawString("Press ENTER to use", textX, textY + 40);
            g2.drawString("Press DEL to drop", textX, textY + 80);
        }
    }

    public void drawShop() {
        int frameX = gp.tilesize * 2;
        int frameY = gp.tilesize;
        int frameWidth = gp.tilesize * 12;
        int frameHeight = gp.tilesize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        g2.drawString("SHOP", frameX + 40, frameY + 60);
        g2.setFont(g2.getFont().deriveFont(24F));
        g2.drawString("Coins: " + gp.player.coins, frameX + 400, frameY + 60);

        int itemX = frameX + 40;
        int itemY = frameY + 120;

        for (int i = 0; i < shopItems.size(); i++) {
            if (i == commandNum) {
                g2.drawString(">", itemX - 30, itemY);
            }
            g2.drawImage(shopItems.get(i).image, itemX, itemY - 25, null);
            g2.drawString(shopItems.get(i).name, itemX + 60, itemY);
            g2.drawString("Price: 500", itemX + 300, itemY);
            itemY += 60;
        }
        
        g2.drawString("Press ENTER to buy", frameX + 40, frameY + frameHeight - 40);
        g2.drawString("Press ESC to leave", frameX + 350, frameY + frameHeight - 40);
    }

    public int getItemIndexOnSlot() {
        return slotCol + (slotRow * 3);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXforCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}
