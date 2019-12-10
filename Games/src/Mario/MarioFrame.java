package Mario;

import java.awt.*;
import java.awt.event.*;

public class MarioFrame extends Frame implements Runnable, ActionListener, KeyListener, WindowListener {
	private static final long serialVersionUID = 1L;
	
	private MarioCanvas mc;
	private boolean jump = false;
	private boolean movingDown = false;
	private boolean movingLeft = false;
	private boolean movingRight = false;
	
	public MarioFrame() {
		setTitle("Mario_X");
		
		mc = new MarioCanvas();
		add(mc, BorderLayout.CENTER);
		
		//.addActionListener(this);
		addKeyListener(this);
		addWindowListener(this);
		
		setSize(500, 500);
		setVisible(true);
		
		Thread th = new Thread(this);
		MoveVertical moveV = new MoveVertical();
		MoveHorizontal moveH = new MoveHorizontal();
		
		th.start();
		moveV.start();
		moveH.start();
	}
	
	class MoveVertical extends Thread {
		@Override
		public void run() {
			while (true) {
				clock(1);
				for (; jump; clock(100)) mc.jump();
				for (; movingDown; clock(3)) mc.moveDown();
			}
		}
	}
	
	class MoveHorizontal extends Thread {
		@Override
		public void run() {
			while (true) {
				clock(1);
				for (; movingLeft; clock(2)) mc.moveLeft();
				for (; movingRight; clock(2)) mc.moveRight();
			}
		}
	}
	
	private void clock(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
	}
	
	//////// Runnable ////////
	@Override
	public void run() {
		while (true) {
			clock(100);
			requestFocusInWindow();
		}
	}
	
	//////// ActionListener ////////
	@Override
	public void actionPerformed(ActionEvent e) {}
	
	//////// KeyListener ////////
	@Override
	public void keyTyped(KeyEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
		
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
			case KeyEvent.VK_SPACE:
				jump = true;
				movingDown = false;
				break;
			
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				movingDown = true;
				jump = false;
				break;
			
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				mc.movingLeft = movingLeft = true;
				mc.movingRight = movingRight = false;
				break;
			
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				mc.movingRight = movingRight = true;
				mc.movingLeft = movingLeft = false;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		switch (e.getKeyCode()) {
		
			case KeyEvent.VK_UP:
			case KeyEvent.VK_W:
			case KeyEvent.VK_SPACE:
				jump = false;
				break;
			
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_S:
				movingDown = false;
				break;
			
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_A:
				mc.movingLeft = movingLeft = false;
				//惰性
				break;
			
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_D:
				mc.movingRight = movingRight = false;
				//惰性
				break;
		}
	}
	
	//////// WindowListener ////////
	@Override
	public void windowOpened(WindowEvent e) {}
	@Override
	public void windowClosing(WindowEvent e) { System.exit(0); }
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
}