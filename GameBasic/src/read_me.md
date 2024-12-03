
# Exercise 3

# Submitters: Yaacov Levy 201638640, Matan Jeffidof 039986633

AddButton: pressing add creates myCharacter along with a combo box and adds them to the canvas (with a sound effect). you can only press it once.
The combo box allows toggling between the characters image (with sound effect).
A text label is also added above the combo box and it is wrapped by a new class which inherits from UIElement because adding the default text (shape) to the dashboard wasn't possible.

Loop: each loop activates the characters' nextFrame() which makes it animate ("breathing").
note - we had to disable pokimon redraw() because it created graphical artifacts while looping with the animation and solving it seemed out of scope for now.

Mouse: dragging the character stops the animation and droping replays it.

Keyboard: arrow buttons move the character. the image changes according to the direction.
note - pressing 2 buttons at the same time (e.g: up+right) doesn't work. fixing it seemed out of scope for now.

ChangeButton: pressing change changes the character's location randomly (with a sound effect).

BoostButton: a "hidden" button is added when choosing "weapon" on the combo box. pressing it activates a one time animation (with sound effect). after that the character goes back to idle animation.

# Images and sound effect are taken from the great game Little Fighter 2
