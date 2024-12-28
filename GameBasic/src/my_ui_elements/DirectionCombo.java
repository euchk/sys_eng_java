package my_ui_elements;

import base.Game;
import my_base.MyContent;
import ui_elements.GameComboBox;

public class DirectionCombo extends GameComboBox{
    MyContent myContent;

    public DirectionCombo(int posX, int posY) {
        super("directionCombo", "Direction Policy", posX, posY, 160, 30, new String[] {"Left", "Right"});
        myContent = (MyContent) Game.Content();
        this.comboBox.setSelectedItem("Left");
    }

    public void setDirection(String direction) {
        this.comboBox.setSelectedItem(direction);
    }

    @Override
	public void action() {
        super.action();
        switch (getOption()) {
            case "Right":
                break;
            case "Left":
                break;
            default:
          }
  
	}


}
