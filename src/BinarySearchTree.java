import java.util.ArrayList;

public class BinarySearchTree {
    Node root;
    private final int SPACING_HEIGHT = 80;
    private ArrayList<Node> arrayOfNode;

    BinarySearchTree() {
        root = null;
    }

    public void insert(int value, int posX, int posY, int spacing_width) {
        root = insert(root, value, posX, posY, spacing_width, true);
    }

    private Node insert(Node node, int value, int posX, int posY, int spacing_width, boolean isLeft) {
        if (node == null) {
            node = new Node(value, posX, posY, spacing_width, isLeft);
        } else if (value < node.value) {
            node.left = insert(node.left, value, posX - spacing_width / 2, posY + SPACING_HEIGHT, spacing_width / 2, true);
        } else if (value > node.value) {
            node.right = insert(node.right, value, posX + spacing_width / 2, posY + SPACING_HEIGHT, spacing_width / 2, false);
        }
        return node;
    }

    public Node getNode(Node node, int value) {
        if (value == node.value) {
            return node;
        } else if (value < node.value) {
            return getNode(node.left, value);
        } else {
            return getNode(node.right, value);
        }
    }

    public void deleteNode(int value) {
        root = deleteNode(root, value);
    }

    private Node deleteNode(Node current, int value) {
        if (current == null)
            return null;
        if (value < current.value) {
            current.left = deleteNode(current.left, value);
        } else if (value > current.value) {
            current.right = deleteNode(current.right, value);
        } else {
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            }
            Node minNode = findMinNode(current.right);
            current.value = minNode.value;
            current.right = deleteNode(current.right, current.value);
        }
        updatePositive(current);
        return current;
    }

    private void updatePositive(Node node) {
        if (node.left != null) {
            node.left.posX = node.posX - node.spacing/2;
            node.left.posY = node.posY + SPACING_HEIGHT;
//            System.out.println(node.left.posX + " " + node.left.posY + " " + node.left.spacing);
            node.left.spacing = node.spacing/2;
            node.left.isLeft = true;
            updatePositive(node.left);
        }
        if (node.right != null) {
            node.right.posX = node.posX + node.spacing/2;
            node.right.posY = node.posY + SPACING_HEIGHT;
//            System.out.println(node.right.posX + " " + node.right.posY + " " + node.right.spacing);
            node.right.spacing = node.spacing/2;
            node.right.isLeft = false;
            updatePositive(node.right);
        }
    }

    public void traversePreOrder (Node node) {
        // Node -> Left -> Right
        if (node != null) {
            System.out.println(node.value + " ");
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }
    public void traverseInOrder(Node node) {
        // Left -> Node -> Right
        if (node != null) {
            traverseInOrder(node.left);
            System.out.print(node.value + " ");
            traverseInOrder(node.right);
        }
    }
    public void traversePostOrder(Node node) {
        // Left -> Right -> Node
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            System.out.print(node.value + " ");
        }
    }

    public void getPath(Node node, int value) {
        arrayOfNode = new ArrayList<Node>();
        arrayOfNode.add(node);
        getPathNode(node, value);
    }

    public Node findMinNode(Node root) {
        Node nodeMin = root;
        while (root.left != null) {
            nodeMin = root.left;
            root = root.left;
        }
        return nodeMin;
    }


    public int findMinNode() {
        Node current = root;
        while (current.left != null)
            current = current.left;
        return current.value;
    }

    public int findMaxValue() {
        Node current = root;
        while (current.right != null)
            current = current.right;
        return current.value;
    }

    public void getPathNode(Node node, int value) {
        if (value == node.value) {
            this.arrayOfNode.add(node);
        } else if (value < node.value) {
            this.arrayOfNode.add(node.left);
            getPathNode(node.left, value);
        } else {
            this.arrayOfNode.add(node.right);
            getPathNode(node.right, value);
        }
    }

    public ArrayList<Node> getArrayOfNode() {
        return this.arrayOfNode;
    }

    public void clearArrayOfNode() {
        if (this.arrayOfNode.size() != 0)
            this.arrayOfNode.clear();
    }

    public boolean contains(int value) {
        return contains(root, value);
    }

    private boolean contains(Node node, int value) {
        if (node == null) {
            return false;
        } else if (value == node.value) {
            return true;
        } else if (value < node.value) {
            return contains(node.left, value);
        } else {
            return contains(node.right, value);
        }
    }
}
