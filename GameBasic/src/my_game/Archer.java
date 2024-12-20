package my_game;

import java.util.HashMap;
import java.util.Map;

import shapes.AnimatedImage.AnimationRow;
import ui_elements.ScreenPoint;

public class Archer extends Character {
 
    private static final String[] SPRITE_SHEET_PATHS = {
        "resources/objects/archer/U_Idle.png",
        "resources/objects/archer/D_Idle.png",
        "resources/objects/archer/S_Idle.png",
        "resources/objects/archer/S_Idle.png",
        "resources/objects/archer/U_Attack.png",
        "resources/objects/archer/D_Attack.png",
        "resources/objects/archer/S_Attack.png",
        "resources/objects/archer/S_Attack.png"
        };

    private static final int FRAME_WIDTH = 48;   // Width of each frame
    private static final int FRAME_HEIGHT = 48;  // Height of each frame
    // private static final int TOTAL_FRAMES = 6;   // Number of frames per animation

    public Archer(ScreenPoint startLocation, String id, Direction direction) {
        super(
            startLocation, 
            id, 
            SPRITE_SHEET_PATHS, 
            FRAME_WIDTH, 
            FRAME_HEIGHT, 
            createFrameCountMap(),
            direction
        );
    }

    private static Map<AnimationRow, Integer> createFrameCountMap() {
        Map<AnimationRow, Integer> frameCountMap = new HashMap<>();
        frameCountMap.put(AnimationRow.U_IDLE, 4);    // 4 frames for idle
        frameCountMap.put(AnimationRow.D_IDLE, 4);
        frameCountMap.put(AnimationRow.L_IDLE, 4);
        frameCountMap.put(AnimationRow.R_IDLE, 4);
        frameCountMap.put(AnimationRow.U_ATTACK, 6);  // 6 frames for attack
        frameCountMap.put(AnimationRow.D_ATTACK, 6);
        frameCountMap.put(AnimationRow.L_ATTACK, 6);
        frameCountMap.put(AnimationRow.R_ATTACK, 6);
        return frameCountMap;
    }
}
