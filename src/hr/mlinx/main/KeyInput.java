package hr.mlinx.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import hr.mlinx.game_object.Snake;
import hr.mlinx.util.ID;

public class KeyInput extends KeyAdapter {
	
	private Handler handler;
	
	public KeyInput(Handler handler) {
		super();
		
		this.handler = handler;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		handler.getGameObjects().forEach(go -> {
			if (go.getId() == ID.SNAKE) {
				Snake snake = (Snake) go;
				
				if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W)
					snake.up();
				else if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S)
					snake.down();
				else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_A)
					snake.left();
				else if (key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_D)
					snake.right();
			}
		});
	}

}
