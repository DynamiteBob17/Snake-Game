package hr.mlinx.game_object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import hr.mlinx.main.Game;
import hr.mlinx.main.Handler;
import hr.mlinx.util.ID;
import hr.mlinx.util.SoundPlayer;
import hr.mlinx.util.Util;

public class Snake extends GameObject {
	
	private static final int ARC = 5;
	private static final Color COLOR = new Color(0, 225, 217);
	private static final Color OUTLINE_COLOR = new Color(94, 0, 31);
	
	private SoundPlayer soundPlayer;
	
	private int deltaX;
	private int deltaY;
	private boolean tailAdded;
	private boolean victory;
	private List<Point> body;
	private List<Point> freeSpaces;
	private List<Point> moveQueue;

	public Snake(Handler handler) {
		super(null, ID.SNAKE, handler);
		
		soundPlayer = new SoundPlayer();
		
		initSnake();
	}
	
	private void initSnake() {
		location = new Point(Game.WIDTH / 2 - SIZE, Game.HEIGHT / 2 - SIZE);
		body = new LinkedList<>();
		body.add(new Point(location.x, location.y));
		freeSpaces = new LinkedList<>();
		Point p;
		for (int i = 0; i < Game.GRID_SIZE; ++i) {
			for (int j = 0; j < Game.GRID_SIZE; ++j) {
				p = new Point(i * SIZE, j * SIZE);
				freeSpaces.add(p);
			}
		}
		freeSpaces.remove(body.get(0));
		
		tailAdded = false;
		victory = false;
		
		deltaX = 0;
		deltaY = 0;
		moveQueue = new LinkedList<>();
	}

	@Override
	public void update() {
		nextMove();
		
		if (!tailAdded)
			freeSpaces.add(body.get(body.size() - 1));
		else
			tailAdded = false;
		
		location.x = location.x + deltaX * SIZE;
		location.y = location.y + deltaY * SIZE;
		body.add(0, new Point(location.x, location.y));
		freeSpaces.remove(body.get(0));
		body.remove(body.size() - 1);
		
		checkCollisions();
	}

	@Override
	public void render(Graphics2D g2) {
		g2.setStroke(new BasicStroke((float) (2 * Util.SCALE)));
		
		body.forEach(p -> {
			if (!p.equals(location))
				drawPart(g2, p, COLOR, OUTLINE_COLOR);
		});
		
		drawPart(g2, location, COLOR.darker(), OUTLINE_COLOR);
	}
	
	private void drawPart(Graphics2D g2, Point point, Color inside, Color outside) {
		g2.setColor(inside);
		g2.fillRoundRect(point.x, point.y, SIZE, SIZE,
						 (int) (ARC * Util.SCALE), (int) (ARC * Util.SCALE));
		g2.setColor(outside);
		g2.drawRoundRect(point.x, point.y, SIZE, SIZE,
						 (int) (ARC * Util.SCALE), (int) (ARC * Util.SCALE));
	}
	
	private void extendBody() {
		body.add(new Point(body.get(body.size() - 1).x, body.get(body.size() - 1).y));
		tailAdded = true;
		freeSpaces.remove(body.get(body.size() - 1));
	}
	
	public void up() {
		moveQueue.add(new Point(0, -1));
	}
	
	public void down() {
		moveQueue.add(new Point(0, 1));
	}
	
	public void left() {
		moveQueue.add(new Point(-1, 0));
	}
	
	public void right() {
		moveQueue.add(new Point(1, 0));
	}
	
	private void nextMove() {
		if (!moveQueue.isEmpty()) {
			Point move = moveQueue.get(0);
			
			if (!((deltaX != 0 && deltaX == -move.x)
				|| deltaY != 0 && deltaY == -move.y)) {
				deltaX = move.x;
				deltaY = move.y;
			}
			
			moveQueue.remove(move);
		}
	}
	
	@Override
	protected void checkCollisions() {
		if (freeSpaces.isEmpty()) {
			if (victory) {
				soundPlayer.victory();
				initSnake();
			} else {
				victory = true;
			}
		}
		
		if (!Game.BOUNDS.contains(getBounds())) {
			soundPlayer.defeat();
			initSnake();
		}
		
		Point head = body.get(0);
		for (int i = 1; i < body.size(); ++i) {
			if (head.equals(body.get(i))) {
				soundPlayer.defeat();
				initSnake();
			}
		}
		
		handler.getGameObjects().forEach(go -> {
			if (go.getId() == ID.APPLE && go.getBounds().equals(getBounds())) {
				extendBody();
				soundPlayer.eat(body.size() - 1);
			}
		});
	}
	
	public List<Point> getBody() {
		return body;
	}
	
	public List<Point> getFreeSpaces() {
		return freeSpaces;
	}

}
