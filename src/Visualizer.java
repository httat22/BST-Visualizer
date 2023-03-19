import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Visualizer {
    private static final int PADDING = 20;
    private static final int SPACE = 80;
    private static final int DIAMETER = 50;
    private static int traversalNumbering;
    private final int SPEED = 1000;
    private BinarySearchTree tree;
    private boolean hasTree;

    private ArrayList<Node> arrayOfNode = new ArrayList<Node>();

    // statistic
    private final BufferStrategy bs;
    private Graphics g;
    private final ButtonListener listener;


    public Visualizer(ButtonListener listener) {
        this.listener = listener;
        bs = listener.getBufferStrategy();
    }

    //    Create Tree
    public void createTreeEmpty(int canvasWidth, int canvasHeight) {
        hasTree = true;
        tree = new BinarySearchTree();
        // get graphics
        g = bs.getDrawGraphics();
        // clear screen
        g.setColor(ColorManager.CANVAS_BACKGROUND);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        bs.show();
        g.dispose();
    }

    public void createTreeRandom(int canvasWidth, int canvasHeight) {
        hasTree = true;
        tree = new BinarySearchTree();
        Random rand = new Random();

        int num_node = rand.nextInt(4) + 7;
        for (int i = 0; i < num_node; i++) {
            int val = rand.nextInt(100);
            tree.insert(val, canvasWidth / 2, PADDING, canvasWidth / 2);
        }
        // get graphics
        g = bs.getDrawGraphics();
        // clear screen
        g.setColor(ColorManager.CANVAS_BACKGROUND);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        if (tree.root != null) {
            drawNode(tree.root);
        }
        bs.show();
        g.dispose();
    }
    public void createTreeFromFile(int canvasWidth, int canvasHeight) {
        hasTree = true;
        tree = new BinarySearchTree();
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextInt()) {
                    int number = scanner.nextInt();
                    tree.insert(number, canvasWidth / 2, PADDING, canvasWidth / 2);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // get graphics
        g = bs.getDrawGraphics();
        // clear screen
        g.setColor(ColorManager.CANVAS_BACKGROUND);
        g.fillRect(0, 0, canvasWidth, canvasHeight);

        if (tree.root != null) {
            drawNode(tree.root);
        }
        bs.show();
        g.dispose();
    }
    private void drawNode(Node node) {
        Draw draw;
        draw = new Draw(node, DIAMETER, ColorManager.DRAW_CIRCLE);
        draw.drawCircle(g);
        if (tree.root == null) {
            return;
        }
        if (node.left != null) {
            draw.drawLeftLine(g, node.spacing / 2, SPACE);
            drawNode(node.left);
        }
        if (node.right != null) {
            draw.drawRightLine(g, node.spacing / 2, SPACE);
            drawNode(node.right);
        }
    }

    //     insert node
    public void insert(JTextField jTextField, int canvasWidth, int canvasHeight) {
        isCreated();
        if (!hasTree) return;
        if (jTextField.getText().isEmpty()) {
            isInputEmpty();
            return;
        }
        if (arrayOfNode.size() == 0) update(canvasWidth, canvasHeight);
        g = bs.getDrawGraphics();
        int value = Integer.parseInt(jTextField.getText());
        boolean existNode = tree.contains(value);
        if (existNode)
            isValueSimilar();
        else {
            if (tree.root == null) {
                tree.insert(value, canvasWidth / 2, PADDING, canvasWidth / 2);
                drawNode(tree.root);
            } else {
                tree.insert(value, canvasWidth / 2, PADDING, canvasWidth / 2);
                tree.getPath(tree.root, value);
                arrayOfNode = tree.getArrayOfNode();
                drawPath(tree.getArrayOfNode());
                tree.clearArrayOfNode();
            }
        }
        bs.show();
        g.dispose();
    }

    //search
    public void search(JTextField jTextField, int canvasWidth, int canvasHeight) {
        isCreated();
        if (!hasTree) return;
        if (tree.root == null) {
            isTreeEmpty();
            return;
        }
        if (jTextField.getText().isEmpty()) {
            isInputEmpty();
            return;
        }
        if (arrayOfNode.size() == 0) update(canvasWidth, canvasHeight);
        g = bs.getDrawGraphics();
        int value = Integer.parseInt(jTextField.getText());
        boolean existNode = tree.contains(value);
        if (!existNode)
            isValueExit();
        else {
            tree.getPath(tree.root, value);
            arrayOfNode = tree.getArrayOfNode();
            drawPath(tree.getArrayOfNode());
            tree.clearArrayOfNode();
        }
        bs.show();
        g.dispose();
    }

    public void searchMin(int canvasWidth, int canvasHeight) {
        isCreated();
        if (!hasTree) return;
        if (tree.root == null) {
            isTreeEmpty();
            return;
        }
        if (arrayOfNode.size() == 0) update(canvasWidth, canvasHeight);
        g = bs.getDrawGraphics();
        if (tree.root == null) return;
        int value = tree.findMinNode();
        tree.getPath(tree.root, value);
        arrayOfNode = tree.getArrayOfNode();
        drawPath(tree.getArrayOfNode());
        tree.clearArrayOfNode();
        bs.show();
        g.dispose();
    }

    public void searchMax(int canvasWidth, int canvasHeight) {
        isCreated();
        if (!hasTree) return;
        if (tree.root == null) {
            isTreeEmpty();
            return;
        }
        if (arrayOfNode.size() == 0) update(canvasWidth, canvasHeight);
        g = bs.getDrawGraphics();
        if (tree.root == null) return;
        int value = tree.findMaxValue();
        tree.getPath(tree.root, value);
        arrayOfNode = tree.getArrayOfNode();
        drawPath(tree.getArrayOfNode());
        tree.clearArrayOfNode();
        bs.show();
        g.dispose();
    }

    public void remove(JTextField jTextField, int canvasWidth, int canvasHeight) {
        isCreated();
        if (!hasTree) return;
        if (tree.root == null) {
            isTreeEmpty();
            return;
        }
        if (jTextField.getText().isEmpty()) {
            isInputEmpty();
            return;
        }
        update(canvasWidth, canvasHeight);
        int value = Integer.parseInt(jTextField.getText());
        Node nodeRemove;
        if (!tree.contains(value)) {
            isValueExit();
            return;
        }
        nodeRemove = tree.getNode(tree.root, value);
        Node nodeAlternate;
        highlightNode(nodeRemove, ColorManager.FILL_CIRCLE_DELETE);
        if (nodeRemove.right != null && nodeRemove.left != null) {
            nodeAlternate = tree.findMinNode(nodeRemove.right);
            System.out.println(nodeAlternate.value);
            highlightNode(nodeAlternate, ColorManager.FILL_CIRCLE_ALTERNATE);
        } else if (nodeRemove.right != null || nodeRemove.left != null) {
            if (nodeRemove.right == null) {
                nodeAlternate = nodeRemove.left;
                highlightNode(nodeAlternate, ColorManager.FILL_CIRCLE_ALTERNATE);
            } else {
                nodeAlternate = nodeRemove.right;
                highlightNode(nodeAlternate, ColorManager.FILL_CIRCLE_ALTERNATE);
            }
        } else {
            nodeAlternate = null;
        }
        timeDelay(2 * SPEED);
        tree.deleteNode(value);
        // clear screen
        g = bs.getDrawGraphics();
        update(canvasWidth, canvasHeight);
        if (nodeAlternate != null) {
            Node nodeChange = tree.getNode(tree.root, nodeAlternate.value);
            highlightNode(nodeChange, ColorManager.FILL_CIRCLE_ALTERNATE);
        }
        bs.show();
        g.dispose();
    }

    private void highlightNode(Node node, Color color) {
        g = bs.getDrawGraphics();
        Draw draw = new Draw(node, DIAMETER, color);
        draw.fillCircle(g);
        bs.show();
        g.dispose();
    }
    private void traverseNumbering(Node node, Color color) {
        g = bs.getDrawGraphics();
        Draw draw = new Draw(node, color);
        draw.drawNumbering(g, ++traversalNumbering);
        bs.show();
        g.dispose();
    }

    public void traversePreOrder(int canvasWidth, int canvasHeight) {
        isCreated();
        if (!hasTree) return;
        if (tree.root == null) {
            isTreeEmpty();
            return;
        }
        traversalNumbering = 0;
        update(canvasWidth, canvasHeight);
        traversePreOrder(tree.root);
    }

    private void traversePreOrder(Node node) {
        if (node != null) {
            highlightNode(node, Color.BLACK);
            traverseNumbering(node, Color.YELLOW);
            timeDelay(1000);
            traversePreOrder(node.left);
            traversePreOrder(node.right);
        }
    }
    public void traverseInOrder(int canvasWidth, int canvasHeight) {
        isCreated();
        if (!hasTree) return;
        if (tree.root == null) {
            isTreeEmpty();
            return;
        }
        traversalNumbering = 0;
        update(canvasWidth, canvasHeight);
        traverseInOrder(tree.root);
    }

    private void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            highlightNode(node, Color.BLACK);
            traverseNumbering(node, Color.CYAN);
            timeDelay(1000);
            traverseInOrder(node.right);
        }
    }
    public void traversePostOrder(int canvasWidth, int canvasHeight) {
        isCreated();
        if (!hasTree) return;
        if (tree.root == null) {
            isTreeEmpty();
            return;
        }
        traversalNumbering = 0;
        update(canvasWidth, canvasHeight);
        traversePostOrder(tree.root);
    }

    private void traversePostOrder(Node node) {
        if (node != null) {
            traversePostOrder(node.left);
            traversePostOrder(node.right);
            highlightNode(node, Color.BLACK);
            traverseNumbering(node, Color.GREEN);
            timeDelay(1000);
        }
    }

    private void drawPath(ArrayList<Node> nodes) {
        Draw draw;
        if (nodes.size() == 0) return;
        timeDelay(SPEED);
        draw = new Draw(nodes.get(0), DIAMETER, ColorManager.DRAW_PATH);
        draw.fillCircle(g);
        bs.show();
        for (int i = 1; i < nodes.size(); i++) {
            timeDelay(SPEED);
            draw = new Draw(nodes.get(i), DIAMETER, ColorManager.DRAW_PATH);
            draw.fillCircle(g);
            if (nodes.get(i).isLeft)
                draw.drawRightLineAbove(g, nodes.get(i).spacing, SPACE);
            else
                draw.drawLeftLineAbove(g, nodes.get(i).spacing, SPACE);
            bs.show();
        }
    }

    private void timeDelay(int speed) {
        try {
            TimeUnit.MILLISECONDS.sleep(speed);
        } catch (Exception ignored) {
        }
    }

    //    for restore purpose
    public void update(int canvasWidth, int canvasHeight) {
        if (!hasTree)
            return;
        g = bs.getDrawGraphics();
        g.setColor(ColorManager.CANVAS_BACKGROUND);
        g.fillRect(0, 0, canvasWidth, canvasHeight);
        if (tree.root != null) {
            drawNode(tree.root);
        }
        bs.show();
        g.dispose();
    }

    //check if array is created
    private void isCreated() {
        if (!hasTree)
            JOptionPane.showMessageDialog(null, "Bạn cần tạo cây!", "Lỗi cây chưa được tạo", JOptionPane.ERROR_MESSAGE);
    }

    private void isValueSimilar() {
        JOptionPane.showMessageDialog(null, "Giá trị này đã tồn tại. Mời bạn nhập giá trị khác!", "Lỗi trùng giá trị", JOptionPane.ERROR_MESSAGE);
    }
    private void isValueExit() {
        JOptionPane.showMessageDialog(null, "Giá trị này không có trong cây.", "Không tồn tại giá trị", JOptionPane.ERROR_MESSAGE);
    }
    private void isInputEmpty() {
        JOptionPane.showMessageDialog(null, "Bạn chưa nhập giá trị số vào trường văn bản", "Chưa nhập giá trị", JOptionPane.ERROR_MESSAGE);
    }

    private void isTreeEmpty() {
        JOptionPane.showMessageDialog(null, "Cây chưa có giá trị nào!", "Cây rỗng", JOptionPane.ERROR_MESSAGE);
    }

    public interface ButtonListener {
        BufferStrategy getBufferStrategy();
    }
}
