package my_base;


import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import base.Game;
import base.GameCanvas;
import base.PeriodicLoop;
import my_game.Character;

public class MyPeriodicLoop extends PeriodicLoop {

	private MyContent content;
	private final Map<String, Runnable> activeTasks = new ConcurrentHashMap<>();

	public void addTask(String taskId, Runnable task) {
        activeTasks.put(taskId, task);
    }

	public void removeTask(String taskId) {
        activeTasks.remove(taskId);
    }
	
	public void setContent(MyContent content) {
		this.content = content;
	}
	
	@Override
	public void execute() {
		// Let the super class do its work first
		super.execute();
		
		// You can comment this line if you don't want the pokimon to move.
		redrawPokimon();

		// Iterate over all characters for animation and for health check (removes below zero)
		Iterator<Character> iterator = content.getAllCharacters().iterator();
		while (iterator.hasNext()) {
			Character character = iterator.next();
			character.periodicUpdate();
			if (character.getHealth() <= 0) {
				character.removeFromCanvas();
				iterator.remove(); // Safe removal
			}
		}
	


		// Run all active tasks
		activeTasks.forEach((taskId, task) -> {
			if (task != null) {
				try {
					task.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// Repaint canvas after all periodicUpdates 
		GameCanvas canvas = Game.UI().canvas();
		canvas.revalidate();
		canvas.repaint();
	}
	
	private void redrawPokimon() {
		content.pokimon().move();
	}

}
