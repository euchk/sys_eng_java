package my_ui_elements;

import javax.swing.JLabel;

import ui_elements.UIElement;

// This class wraps JLabel as a UIElement in order to add text to the dashboard
public class TextLabelUIElement extends UIElement {
    private JLabel label;

    public TextLabelUIElement(String id, JLabel label, int posX, int posY) {
        super(id, posX, posY, label.getWidth(), label.getHeight(), label);
        this.label = label;
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
