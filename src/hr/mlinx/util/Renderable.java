package hr.mlinx.util;

import java.awt.Graphics2D;

public interface Renderable {
	
	public abstract void update();
	public abstract void render(Graphics2D g2);
	
}
