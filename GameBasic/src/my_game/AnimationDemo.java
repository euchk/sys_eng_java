package my_game;

import javax.swing.JFrame;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import shapes.BufferedAnimatedImage;

public class AnimationDemo {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Buffered Image Animation");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Create an animated image using a spritesheet
        BufferedAnimatedImage animatedImage1 = new BufferedAnimatedImage(
                "resources/S_Run.png",  // Path to spritesheet
                96, 96,      // Frame width and height
                100, 100,                 // Position (X, Y)
                6                        // Total number of frames
        );

         // Create an animated image using a spritesheet
         BufferedAnimatedImage animatedImage2 = new BufferedAnimatedImage(
            "resources/4.png",  // Path to spritesheet
            70, 130,      // Frame width and height
            300, 400,                 // Position (X, Y)
            6                        // Total number of frames
    );

        // Timer to animate frames every 200ms
        Timer timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animatedImage1.nextFrame();
                animatedImage2.nextFrame();
            }
        });

        timer.start();

        // Add the animated image to the frame
        frame.add(animatedImage1);
        frame.add(animatedImage2);
        frame.setVisible(true);
    }
}
