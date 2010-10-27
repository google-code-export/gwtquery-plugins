package gwtquery.plugins.droppable.client.draughtssample;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;

import gwtquery.plugins.droppable.client.draughtssample.resources.DraughtsResources;

public class DraughtsSample implements EntryPoint {

  public static HandlerManager EVENT_BUS = new HandlerManager(null);
  
	public void onModuleLoad() {
		DraughtsResources.INSTANCE.css().ensureInjected();		
	
		RootPanel.get("draughts").add(CheckerBoard.getInstance());
		RootPanel.get("score").add(new ScorePanel());
		
		GameController gameController = GameController.getInstance();
		gameController.reset();
		gameController.startGame();
	}

}
