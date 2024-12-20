package my_game;

import java.util.HashMap;
import java.util.Map;

import shapes.AnimatedImage;
import shapes.AnimatedImage.AnimationRow;
import ui_elements.ScreenPoint;

public class Knight extends Character {
 
    private static final String[] SPRITE_SHEET_PATHS = {
        "resources/objects/knight/U_Run.png",
        "resources/objects/knight/D_Run.png",
        "resources/objects/knight/S_Run.png",
        "resources/objects/knight/S_Run.png",
        "resources/objects/knight/U_Attack.png",
        "resources/objects/knight/D_Attack.png",
        "resources/objects/knight/S_Attack.png",
        "resources/objects/knight/S_Attack.png"
    };

    private static final int FRAME_WIDTH = 96;   // Width of each frame
    private static final int FRAME_HEIGHT = 96;  // Height of each frame
    // private static final int TOTAL_FRAMES = 6;   // Number of frames per animation

    public Knight(ScreenPoint startLocation, String id, Direction direction) {
        super(
            startLocation, 
            id, 
            SPRITE_SHEET_PATHS, 
            FRAME_WIDTH, 
            FRAME_HEIGHT, 
            createFrameCountMap(),
            direction
        );
        this.isMoving = true;
    }

    private static Map<AnimationRow, Integer> createFrameCountMap() {
        Map<AnimationRow, Integer> frameCountMap = new HashMap<>();
        frameCountMap.put(AnimationRow.U_IDLE, 6);    // 6 frames for idle
        frameCountMap.put(AnimationRow.D_IDLE, 6);
        frameCountMap.put(AnimationRow.L_IDLE, 6);
        frameCountMap.put(AnimationRow.R_IDLE, 6);
        frameCountMap.put(AnimationRow.U_ATTACK, 6);  // 6 frames for attack
        frameCountMap.put(AnimationRow.D_ATTACK, 6);
        frameCountMap.put(AnimationRow.L_ATTACK, 6);
        frameCountMap.put(AnimationRow.R_ATTACK, 6);
        return frameCountMap;
    }

    @Override
    public void periodicUpdate() {
        move(0, -10);
        animatedImage.setAnimationRow(mapDirection(direction, isIdle));
        animatedImage.nextFrame();
    }
}
