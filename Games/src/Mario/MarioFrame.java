package Mario;

import java.awt.*;
import java.awt.event.*;

public class MarioFrame extends Frame implements Runnable, ActionListener, KeyListener, MouseListener, WindowListener {
	private static final long serialVersionUID = 1L;
	
	private MarioCanvas mc;
	
	public MarioFrame() {
		setTitle("MarioX");
		
		mc = new MarioCanvas();
		add(mc, BorderLayout.CENTER);
		
		//.addActionListener(this);
		addMouseListener(this);
		addKeyListener(this);
		addWindowListener(this);
		
		setSize(500, 500);
		setVisible(true);
		
		Thread th = new Thread(this);
		th.start();
		
		while (true) requestFocusInWindow();
	}
	
	//////// Runnable ////////
	@Override
	public void run() {
		int clock = 0;
		boolean floating;
		while (true) {
			clock++;
			try { Thread.sleep(5); } catch (Exception e) {}
			
			floating = mc.gravity(clock);
			
			if (!floating) {
				clock = 0;
				mc.jumpPower = 0;
			}
		}
	}
	
	//////// ActionListener ////////
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	//////// MouseListener ////////
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	
	//////// KeyListener ////////
	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println("keyTyped");
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
		case KeyEvent.VK_SPACE:
			mc.jump();
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			mc.moveRight();
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			mc.crouch();
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			mc.moveLeft();
			break;
		}
		
		mc.repaint();
		//System.out.println("keyPressed");
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("keyReleased");
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