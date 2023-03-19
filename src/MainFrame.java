import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class MainFrame extends JFrame implements
        MyCanvas.VisualizerProvider, ButtonPanel.CreateButtonListener
        , Visualizer.ButtonListener {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }

    private static final int WIDTH = 1280, HEIGHT = 720;
    private MyCanvas canvas;
    private ButtonPanel buttonPanel;
    private Visualizer visualizer;

    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMaximumSize(new Dimension(WIDTH, HEIGHT + 200));
        setMinimumSize(new Dimension(WIDTH, HEIGHT + 20));
        setPreferredSize(new Dimension(WIDTH, HEIGHT + 20));
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(ColorManager.BACKGROUND);
        setTitle("Binary Search Tree");
        initialize();
    }

    // initialize components
    private void initialize() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(ColorManager.BACKGROUND);
        add(mainPanel);

        // add buttons panel
        buttonPanel = new ButtonPanel(this);
        buttonPanel.setBounds(0, 100, 250, HEIGHT);
        buttonPanel.setBackground(ColorManager.BACKGROUND);
        mainPanel.add(buttonPanel);

        // add canvas
        canvas = new MyCanvas(this);
        int cWidth = WIDTH - 250 - 10;
        int cHeight = HEIGHT - 80;
        canvas.setFocusable(false);
        canvas.setMaximumSize(new Dimension(cWidth, cHeight));
        canvas.setMinimumSize(new Dimension(cWidth, cHeight));
        canvas.setPreferredSize(new Dimension(cWidth, cHeight));
        canvas.setBounds(250, 60, cWidth, cHeight);
        mainPanel.add(canvas);
        pack();

        visualizer = new Visualizer(this);
    }

    @Override
    public void onDrawArray() {
        if (visualizer != null)
            visualizer.update(canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void createButtonBST(int id) {
        switch (id) {
            case 0 -> visualizer.createTreeEmpty(canvas.getWidth(), canvas.getHeight());
            case 1 -> visualizer.createTreeRandom(canvas.getWidth(), canvas.getHeight());
            case 2 -> visualizer.search(buttonPanel.getJTextField(), canvas.getWidth(), canvas.getHeight());
            case 3 -> visualizer.searchMin(canvas.getWidth(), canvas.getHeight());
            case 4 -> visualizer.searchMax(canvas.getWidth(), canvas.getHeight());
            case 5 -> visualizer.insert(buttonPanel.getJTextField(), canvas.getWidth(), canvas.getHeight());
            case 6 -> visualizer.remove(buttonPanel.getJTextField(), canvas.getWidth(), canvas.getHeight());
            case 7 -> visualizer.traversePreOrder(canvas.getWidth(), canvas.getHeight());
            case 8 -> visualizer.traverseInOrder(canvas.getWidth(), canvas.getHeight());
            case 9 -> visualizer.traversePostOrder(canvas.getWidth(), canvas.getHeight());
            case 10 -> visualizer.createTreeFromFile(canvas.getWidth(), canvas.getHeight());
        }
    }

    public BufferStrategy getBufferStrategy() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(2);
            bs = canvas.getBufferStrategy();
        }
        return bs;
    }
}
