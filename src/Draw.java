import java.awt.*;

public class Draw {
    private final int x;
    private final int y;
    private int diameter;
    private int value;
    private Color color;
    public Draw(Node node, int diameter, Color color) {
        this.x = node.posX - diameter/2;
        this.y = node.posY;
        this.diameter = diameter;
        this.value = node.value;
        this.color = color;
    }
    public Draw(Node node, Color color) {
        this.x = node.posX;
        this.y = node.posY;
        this.color = color;
    }
    public void drawCircle(Graphics g) {
        g.setColor(ColorManager.CANVAS_BACKGROUND);
        g.fillOval(x,y, diameter, diameter);
        g.setColor(color);
        g.drawOval(x,y, diameter, diameter);
        g.setColor(ColorManager.TEXT_YELLOW);
        Font font = new Font("Serif", Font.PLAIN, 20);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int mes_x = x + diameter /2 - metrics.stringWidth(value+"") / 2;
        int mes_y = y + diameter /2 + metrics.getAscent() / 2;
        g.drawString(value+"", mes_x, mes_y);
    }

    public void fillCircle(Graphics g) {
        g.setColor(color);
        g.fillOval(x,y, diameter, diameter);
        g.setColor(ColorManager.TEXT_YELLOW);
        Font font = new Font("Serif", Font.PLAIN, 20);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int mes_x = x + diameter /2 - metrics.stringWidth(value+"") / 2;
        int mes_y = y + diameter /2 + metrics.getAscent() / 2;
        g.drawString(value+"", mes_x, mes_y);
    }
    public void drawNumbering(Graphics g, int number) {
        g.setColor(color);
        Font font = new Font("Serif", Font.BOLD, 16);
        g.setFont(font);
        g.drawString(number+"", x-5, y - 5);
    }

    public void drawLeftLine(Graphics g, int space_width, int space_height) {
        g.setColor(color);
        g.drawLine(x+diameter/2, y+diameter, x - space_width + diameter/2, y+space_height);
    }
    public void drawRightLine(Graphics g, int space_width, int space_height) {
        g.setColor(color);
        g.drawLine(x+diameter/2, y+diameter, x + space_width + diameter/2, y+space_height);

    }

    public void drawLeftLineAbove(Graphics g, int space_width, int space_height) {
        g.setColor(ColorManager.DRAW_PATH);
        g.drawLine(x - space_width + diameter/2, y-space_height + diameter, x+diameter/2, y);
    }
    public void drawRightLineAbove(Graphics g, int space_width, int space_height) {
        g.setColor(ColorManager.DRAW_PATH);
        g.drawLine(x + space_width + diameter/2, y-space_height + diameter, x+diameter/2, y);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
