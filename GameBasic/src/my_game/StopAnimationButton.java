package my_game;

import base.Game;
import my_base.MyContent;
import shapes.ShapeButton;

public class StopAnimationButton extends ShapeButton {

    MyContent content = (MyContent) Game.Content();

    public StopAnimationButton(int posX, int posY) {
        super(
            "start_animation_button",
            70, 
            70,
            posX,
            posY,
            "resources/buttons/button.png",
            "Stop Animation"
        );
    }

    @Override
    protected void onClick() {
        content.warrior().setIsDisabled(true);
    }

    @Override public void shapeRightClicked(String shapeID, int x, int y) {
        enableButton();
        content.warrior().setIsDisabled(false);
    }

}
