package main;

import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    public boolean attackPressed, pausePressed, statusPressed;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used but must be implemented
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        // ================= PLAY STATE =================
        if (gp.currentState == gp.PLAY) {

            // Movement
            if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = true;
            if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = true;
            if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = true;
            if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = true;

            // Actions
            if (code == KeyEvent.VK_SPACE) attackPressed = true;
            if (code == KeyEvent.VK_H) statusPressed = true;

            // Change state
            if (code == KeyEvent.VK_P) gp.currentState = gp.PAUSE;
            if (code == KeyEvent.VK_I) gp.currentState = gp.INVENTORY;
            if (code == KeyEvent.VK_B) gp.currentState = gp.SHOP;
        }

        // ================= PAUSE STATE =================
        else if (gp.currentState == gp.PAUSE) {

            if (code == KeyEvent.VK_P) {
            	pausePressed = true;
            }
        }

        // ================= INVENTORY STATE =================
        else if (gp.currentState == gp.INVENTORY) {
            inventoryState(code);
        }

        // ================= SHOP STATE =================
        else if (gp.currentState == gp.SHOP) {
            shopState(code);
        }
    }

    private void inventoryState(int code) {

        // Exit inventory
        if (code == KeyEvent.VK_I || code == KeyEvent.VK_ESCAPE) {
            gp.currentState = gp.PLAY;
        }

        // Move cursor
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if (gp.ui.slotRow > 0) gp.ui.slotRow--;
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if (gp.ui.slotRow < 3) gp.ui.slotRow++;
        }

        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (gp.ui.slotCol > 0) gp.ui.slotCol--;
        }

        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (gp.ui.slotCol < 4) gp.ui.slotCol++;
        }

        // Use item
        if (code == KeyEvent.VK_ENTER) {
            int itemIndex = gp.ui.getItemIndexOnSlot();

            if (itemIndex < gp.player.inventory.getSize()) {
                String itemName =
                        gp.player.inventory.getItemAt(itemIndex).name;

                gp.player.useItem(itemName);
            }
        }

        // Drop item
        if (code == KeyEvent.VK_DELETE || code == KeyEvent.VK_BACK_SPACE) {

            int itemIndex = gp.ui.getItemIndexOnSlot();

            if (itemIndex < gp.player.inventory.getSize()) {
                gp.player.inventory.removeAt(itemIndex);
                gp.ui.addMessage("Item dropped!");
            }
        }
    }

    private void shopState(int code) {

        // Exit shop
        if (code == KeyEvent.VK_B || code == KeyEvent.VK_ESCAPE) {
            gp.currentState = gp.PLAY;
        }

        // Move selection
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;

            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = gp.ui.shopItems.size() - 1;
            }
        }

        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;

            if (gp.ui.commandNum >= gp.ui.shopItems.size()) {
                gp.ui.commandNum = 0;
            }
        }

        // Buy item
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {   // Heart
                gp.shop.buyHeart();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) upPressed = false;
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) downPressed = false;
        if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) leftPressed = false;
        if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) rightPressed = false;
        if (code == KeyEvent.VK_SPACE) attackPressed = false;
        if (code == KeyEvent.VK_H) statusPressed = false;
        if (code == KeyEvent.VK_P) {
        	pausePressed = false;
        }
    }
}