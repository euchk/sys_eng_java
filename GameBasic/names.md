
# Submitters: Yaacov Levy 201638640, Matan Jeffidof 039986633

# Ex3 details

Adding the character: when pressing 'add' it creates myCharacter along with a combo box and adds them to the canvas (with a sound effect). you can only press it 'add' once.
The combo box allows toggling between the characters image (weapon or armor).
A text label is also added above the combo box to the dashboard. dashboard doesn't support adding shapes so we created TextLabelUIElement which extends UIElement and not shape.

Animation: we used the periodic loop to activate nextFrame() which loops though the image array and gives a feeling of a "breathing" character.
note - we had to disable pokimon redraw() because it created random graphical artifacts (character freeze and clones).

Mouse: dragging the character stops the animation and dropping it replays it.

Keyboard: arrow buttons move the character (UP, DOWN, LEFT, RIGHT). we used mirror images for the opposite directions.
note1 - pressing 2 buttons at the same time (e.g: up+right) doesn't work. fixing it seemed out of scope for now.
note2 - at first we thought of dynamicly creating mirror images to avoid storing double images but it seemed out of scope for now.

Change: pressing 'change' changes the character's position to a random location (for lack of a better idea...).

Boost: a "hidden" button is added when choosing "weapon" on the combo box. pressing it activates a one time animation. after that the character goes back to idle animation.

# Images and sound effect are taken from the great game Little Fighter 2 (2004).
