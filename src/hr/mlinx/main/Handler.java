package hr.mlinx.main;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;

import hr.mlinx.game_object.GameObject;
import hr.mlinx.util.Renderable;

public class Handler implements Renderable {
	
	private List<GameObject> gameObjects;
	
	public Handler() {
		super();
		
		gameObjects = new LinkedList<>();
	}

	@Override
	public void update() {
		gameObjects.forEach(go -> {
			go.update();
		});
	}

	@Override
	public void render(Graphics2D g2) {
		gameObjects.forEach(go -> {
			go.render(g2);
		});
	}
	
	public void add(GameObject gameObject) {
		gameObjects.add(gameObject);
	}
	
	public void remove(GameObject gameObject) {
		gameObjects.remove(gameObject);
	}
	
	public List<GameObject> getGameObjects() {
		return gameObjects;
	}
	
}
