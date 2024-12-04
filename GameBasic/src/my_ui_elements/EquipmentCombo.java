package my_ui_elements;

import java.awt.Color;
import javax.swing.JLabel;

import base.Game;
import base.GameDashboard;
import my_base.MyContent;
import my_game.MyCharacter.Equipment;
import ui_elements.GameComboBox;

public class EquipmentCombo extends GameComboBox{
    private TextLabelUIElement textLabel;
    private GameDashboard dashboard = Game.UI().dashboard();
    MyContent myContent;

    public EquipmentCombo(int posX, int posY) {
        super("equipmentCombo", "Equipment", posX, posY, 160, 30, new String[] {"Armor", "Weapon"});
        
        // Create text label for the combo box
        // label will be used to init TextLabelUIElement
        JLabel label = new JLabel("Equipment:");
        label.setBounds(posX, posY - 25, 200, 30); // Position the label above the combo box
        label.setForeground(Color.WHITE); 
        textLabel = new TextLabelUIElement("equipmentLabel", label, posX, posY - 25);
        
        myContent = (MyContent) Game.Content();
        this.comboBox.setSelectedItem("Armor");
    }

    @Override
	public void action() {
        super.action();
        switch (getOption()) {
            case "Armor":
                myContent.myCharacter().setEquipment(Equipment.ARMOR);
                myContent.boostButton().removeFromDashboard(); // Boost available only on weapon mode
                Game.audioPlayer().play("resources/audio/034.wav", 1);
                break;
            case "Weapon":
                myContent.myCharacter().setEquipment(Equipment.WEAPON);
                myContent.boostButton().addToDashboard(); // Boost available only on weapon mode
                Game.audioPlayer().play("resources/audio/028.wav", 1);
                break;
            default:
          }
  
	}
     
    // Add both the combo box and the label to the dashboard
    public void addToDashboard() {
        dashboard.addUIElement(textLabel);
        dashboard.addUIElement(this);
    }

}
