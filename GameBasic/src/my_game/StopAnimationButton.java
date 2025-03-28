package my_game;

import base.Game;
import my_base.MyContent;
import shapes.ShapeButton;

public class StopAnimationButton extends ShapeButton {

    MyContent content = (MyContent) Game.Content();
    private int cooldownMs = 3000; // 3 seconds cooldown
    private long disabledUntil = 0;

    public StopAnimationButton(int posX, int posY) {
        super(
            "stop_animation_button",
            70, 
            70,
            posX,
            posY,
            "resources/buttons/button.png"
        );
        setText("Stop Animation");
        setDisableOnClick(true);
        setGlowEnabled(true);
    }

    @Override
    protected void onClick() {
        content.warrior().setIsDisabled(true);
        startCooldown();
    }

    private void startCooldown() {
        // Using currentTimeMillis is indifferent to loop frequency
        disabledUntil = System.currentTimeMillis() + cooldownMs;
    }

    public void updateTimer() {
        if (isDisabled()) {
            long remaining = disabledUntil - System.currentTimeMillis();
            if (remaining <= 0) {
                enableButton();
                content.warrior().setIsDisabled(false);
                setText("Stop Animation");
            } else {
                setText((remaining / 1000 + 1) + "s remaining");
            }
        }
    }
}
