package hr.mlinx.game_object;

import java.awt.Point;
import java.awt.Rectangle;

import hr.mlinx.main.Handler;
import hr.mlinx.util.ID;
import hr.mlinx.util.Renderable;
import hr.mlinx.util.Util;

public abstract class GameObject implements Renderable {
	
	public static final int SIZE = (int) (24 * Util.SCALE);
	
	protected Point location;
	protected ID id;
	protected Handler handler;
	
	public GameObject(Point location, ID id, Handler handler) {
		super();
		
		this.location = location;
		this.id = id;
		this.handler = handler;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(location.x, location.y, SIZE, SIZE);
	}
	
	public ID getId() {
		return id;
	}
	
	protected abstract void checkCollisions();
	
}
