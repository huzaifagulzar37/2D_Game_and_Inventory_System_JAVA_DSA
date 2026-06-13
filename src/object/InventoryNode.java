package object;

public class InventoryNode {
    public SuperObject item;
    public InventoryNode next;

    public InventoryNode(SuperObject item) {
        this.item = item;
        this.next = null;
    }
}
