package my_ui_elements;

import java.awt.Color;
import javax.swing.JLabel;

import base.Game;
import base.GameDashboard;
import my_base.MyContent;
import ui_elements.GameComboBox;

public class EquipmentCombo extends GameComboBox{
    private TextLabelUIElement textLabel;
    MyContent myContent;

    public EquipmentCombo(int posX, int posY) {
        super("equipmentCombo", "Equipment", posX, posY, 160, 30, new String[] {"Armor", "Weapon"});
        
        // Create label for the combo box
        JLabel label = new JLabel("Equipment:");
        label.setBounds(posX, posY - 25, 200, 30); // Position the label above the combo box
        label.setForeground(Color.WHITE); 
        textLabel = new TextLabelUIElement("equipmentLabel", label, posX, posY - 25);
        
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
            case "Armor":
                myContent.myCharacter().setEquipment("Armor");
                myContent.boostButton().removeFromDashboard(); // Boost available only on weapon mode
                Game.audioPlayer().play("resources/audio/034.wav", 1);
                break;
            case "Weapon":
                myContent.myCharacter().setEquipment("Weapon");
                myContent.boostButton().addToDashboard(); // Boost available only on weapon mode
                Game.audioPlayer().play("resources/audio/028.wav", 1);
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
