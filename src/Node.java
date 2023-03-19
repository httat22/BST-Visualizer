public class Node {
    public int value;
    public int posX, posY, spacing;
    public Node left, right;
    public boolean isLeft; // check node in left or right
    public Node(int value, int posX, int posY, int spacing, boolean isLeft) {
        this.value = value;
        this.posX = posX;
        this.posY = posY;
        this.spacing = spacing;
        left = right = null;
        this.isLeft = isLeft;
    }
}
