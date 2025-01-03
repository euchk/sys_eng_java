package my_ui_elements;

import java.awt.Color;
import javax.swing.JLabel;

import ui_elements.UIElement;

// This class wraps JLabel as a UIElement in order to add text to the dashboard
public class TextLabelUIElement extends UIElement {
    private JLabel label;

    public TextLabelUIElement(String id, String text, int height, int width, int posX, int posY) {
        super(id, posX, posY, width, height, new JLabel(text));
        this.label = (JLabel) super.getJComponent();;
        this.label.setBounds(posX, posY, width, height);
        this.label.setForeground(Color.WHITE); 
    }

    @Override
    public JLabel getJComponent() {
        return label;
    }

    @Override
    public void action() {
        ;
    }
}
