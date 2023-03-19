import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SimpleAnimation extends JPanel implements ActionListener {

    private int x = 0;
    private final int y = 0;
    private int dx = 5;

    public SimpleAnimation() {
        Timer timer = new Timer(50, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += dx;
        if (x + 50 > getWidth() || x < 0) {
            dx *= -1;
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLUE);
        g.fillOval(x, y, 50, 50);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simple Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 250));
        frame.getContentPane().add(new SimpleAnimation());
        frame.pack();
        frame.setVisible(true);
    }
}

