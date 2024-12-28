package my_base;

import base.PeriodicLoop;

public class MyPeriodicLoop extends PeriodicLoop {

	private MyContent content;
		
	public void setContent(MyContent content) {
		this.content = content;
	}
	
	@Override
	public void execute() {
		super.execute();

		if (content.gameControl() != null) {
			content.gameControl().gameStep();
		}
	}
}
