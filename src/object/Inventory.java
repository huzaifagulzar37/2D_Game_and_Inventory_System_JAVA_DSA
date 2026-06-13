package object;

import main.GamePanel;

public class Inventory {
    private InventoryNode head;
    private int size;
    private GamePanel gp;

    public Inventory(GamePanel gp) {
        this.gp = gp;
        this.head = null;
        this.size = 0;
    }

    public void addItem(SuperObject item) {

        InventoryNode newNode = new InventoryNode(item);

        // Insert at beginning (O(1))
        newNode.next = head;
        head = newNode;

        size++;

        gp.ui.addMessage("Added " + item.name + " to inventory!");
    }

    public boolean removeItem(String itemName) {
        if (head == null) return false;

        if (head.item.name.equals(itemName)) {
            head = head.next;
            size--;
            return true;
        }

        InventoryNode current = head;
        while (current.next != null) {
            if (current.next.item.name.equals(itemName)) {
                current.next = current.next.next;
                size--;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean contains(String itemName) {
        InventoryNode current = head;
        while (current != null) {
            if (current.item.name.equals(itemName)) return true;
            current = current.next;
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    // Get item at specific index for UI selection
    public SuperObject getItemAt(int index) {
        if (index < 0 || index >= size) return null;
        InventoryNode current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.item;
    }

    // Remove item at specific index
    public void removeAt(int index) {
        if (index < 0 || index >= size) return;
        if (index == 0) {
            head = head.next;
        } else {
            InventoryNode current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }
}
