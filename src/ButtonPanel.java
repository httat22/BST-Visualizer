import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class ButtonPanel extends JPanel {
    private static final int BUTTON_WIDTH = 200, BUTTON_HEIGHT = 40;
    private final JButton[] buttons;
    private JTextField jTextField;
    private final int number = 11;
    private final CreateButtonListener listener;

    public JTextField getJTextField() {
        return jTextField;
    }

    public void setJTextField(JTextField jTextField) {
        this.jTextField = jTextField;
    }

    public ButtonPanel(CreateButtonListener listener) {
        super();
        this.listener = listener;
        buttons = new JButton[number];
        for (int i = 0; i < number; i++)
            buttons[i] = new JButton();

        initButtons(buttons[0], "create tree empty", 0);
        initButtons(buttons[1], "create tree random", 1);
        initButtons(buttons[2], "search", 2);
        initButtons(buttons[3], "search min", 3);
        initButtons(buttons[4], "search max", 4);
        initButtons(buttons[5], "insert", 5);
        initButtons(buttons[6], "remove", 6);
        initButtons(buttons[7], "traversePreOrder", 7);
        initButtons(buttons[8], "traverseInOrder", 8);
        initButtons(buttons[9], "traversePostOrder", 9);
        initButtons(buttons[10], "Choose File", 10);
        // add button to the panel
        setLayout(null);
        jTextField = new JTextField();
        jTextField.setBounds(20, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        AbstractDocument doc = (AbstractDocument) jTextField.getDocument();
        doc.setDocumentFilter(new IntegerFilter());

        add(jTextField);
        for (int i = 1; i <= number; i++) {
            buttons[i-1].setBounds(20, (BUTTON_HEIGHT + 5) * i, BUTTON_WIDTH, BUTTON_HEIGHT);
            add(buttons[i-1]);
        }
    }

    private void initButtons(JButton button, String name, int id) {
        button.setText(name);
        button.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                listener.createButtonBST(id);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    public interface CreateButtonListener {
        void createButtonBST(int id);
    }
}
