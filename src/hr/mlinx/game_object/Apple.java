package hr.mlinx.game_object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

import hr.mlinx.main.Handler;
import hr.mlinx.util.ID;
import hr.mlinx.util.Util;

public class Apple extends GameObject {

	public Apple(Point location, Handler handler) {
		super(location, ID.APPLE, handler);
		
		randomAvailableLocation();
	}

	@Override
	public void update() {
		checkCollisions();
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setStroke(new BasicStroke((float) (1 * Util.SCALE)));
		g2.setColor(Color.RED);
		g2.fillOval(location.x, location.y, SIZE, SIZE);
		g2.setColor(Color.YELLOW);
		g2.drawOval(location.x, location.y, SIZE, SIZE);
	}
	
	private void randomAvailableLocation() {
		handler.getGameObjects().forEach(go -> {
			if (go.getId() == ID.SNAKE) {
				Snake snake = (Snake) go;
				List<Point> freeSpaces = snake.getFreeSpaces();
				if (freeSpaces.isEmpty())
					location = new Point(-SIZE, -SIZE);
				else
					location = freeSpaces.get(Util.R.nextInt(freeSpaces.size()));
			}
		});
	}
	
	 @Override
	 protected void checkCollisions() {
		 handler.getGameObjects().forEach(go -> {
			 if (go.getId() == ID.SNAKE && go.getBounds().equals(getBounds()))
				 randomAvailableLocation();
		 });
	 }

}
