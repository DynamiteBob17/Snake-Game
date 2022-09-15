package hr.mlinx.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import hr.mlinx.game_object.Apple;
import hr.mlinx.game_object.GameObject;
import hr.mlinx.game_object.Snake;
import hr.mlinx.util.Renderable;

public class Game extends Canvas implements Runnable, Renderable {
	private static final long serialVersionUID = 3680084252640835962L;
	
	public static final int GRID_SIZE = 20;
	public static final int WIDTH = GameObject.SIZE * GRID_SIZE;
	public static final int HEIGHT = GameObject.SIZE * GRID_SIZE;
	public static final Rectangle BOUNDS = new Rectangle(0, 0, WIDTH, HEIGHT);
	private static final Color ENDGROUND = new Color(135, 255, 165);
	
	private Thread thread;
	private boolean running;
	
	private Handler handler;
	private HUD hud;
	
	public Game() {
		super();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		Frame.set("Snake Game", this);
		
		thread = new Thread(this);
		handler = new Handler();
		hud = new HUD(handler);
		
		addKeyListener(new KeyInput(handler));
		
		handler.add(new Snake(handler));
		handler.add(new Apple(null, handler));
	}
	
	public synchronized void start() {
		running = true;
		thread.start();
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		requestFocus();
		long lastTime = System.nanoTime();
		double nsPerUpdate = 1_000_000_000d / 10;
		double delta = 0;
		long timeNow;
		boolean shouldRender;
		
		while (running) {
			timeNow = System.nanoTime();
			delta += (timeNow - lastTime) / nsPerUpdate;
			lastTime = timeNow;
			shouldRender = false;
			
			while (delta >= 1) {
				update();
				--delta;
				shouldRender = true;
			}
			
			if (shouldRender)
				render(null);
		}
	}
	
	@Override
	public void update() {
		handler.update();
		hud.update();
	}
	
	@Override
	public void render(Graphics2D NULL) {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics2D g2 = (Graphics2D) bs.getDrawGraphics();
		
		g2.setColor(ENDGROUND);
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						    RenderingHints.VALUE_ANTIALIAS_ON);
		
		handler.render(g2);
		hud.render(g2);
		
		g2.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		new Game().start();
	}
	
}
