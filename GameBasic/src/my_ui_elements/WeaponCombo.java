package my_ui_elements;

import java.awt.Color;
import javax.swing.JLabel;

import base.Game;
import base.GameDashboard;
import my_base.MyContent;
import ui_elements.GameComboBox;

public class WeaponCombo extends GameComboBox{
    private TextLabelUIElement textLabel;
    MyContent myContent;

    public WeaponCombo(int posX, int posY) {
        super("weaponCombo", "Weapon", posX, posY, 160, 30, new String[] {"Yes", "No"});
        
        // Create label for the combo box
        JLabel label = new JLabel("Weapon:");
        label.setBounds(posX, posY - 25, 200, 30); // Position the label above the combo box
        label.setForeground(Color.WHITE); 
        textLabel = new TextLabelUIElement("weaponLabel", label, posX, posY - 25);
        
        myContent = (MyContent) Game.Content();
        this.comboBox.setSelectedItem("No");
    }

    public void setWeapon(String weapon) {
        this.comboBox.setSelectedItem(weapon);
    }

    @Override
	public void action() {
        super.action();
        switch (getOption()) {
            case "Yes":
                myContent.myCharacter().setImage(1);
                break;
            case "No":
                myContent.myCharacter().setImage(0);
                break;
            default:
          }
  
	}
     
    // Add both the combo box and the label to the dashboard
    public void addToDashboard() {
        GameDashboard dashboard = Game.UI().dashboard();
        dashboard.addUIElement(textLabel);
        dashboard.addUIElement(this);
    }

}
