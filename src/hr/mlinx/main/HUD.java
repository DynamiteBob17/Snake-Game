package hr.mlinx.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import hr.mlinx.game_object.Snake;
import hr.mlinx.util.ID;
import hr.mlinx.util.Renderable;
import hr.mlinx.util.Util;

public class HUD implements Renderable {

	private Handler handler;
	private int score;
	
	public HUD(Handler handler) {
		super();
		
		this.handler = handler;
	}

	@Override
	public void update() {
		handler.getGameObjects().forEach(go -> {
			if (go.getId() == ID.SNAKE) {
				Snake snake = (Snake) go;
				score = snake.getBody().size() - 1;
			}
		});
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setColor(Color.BLACK);
		int fontSize = (int) (20 * Util.SCALE);
		g2.setFont(new Font("Segoe", Font.BOLD, fontSize));
		g2.drawString("Score: " + score, (int) (Game.WIDTH / 2 - 50 * Util.SCALE), fontSize);
	}
	
}
