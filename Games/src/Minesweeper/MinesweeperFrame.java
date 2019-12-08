package Minesweeper;

import java.awt.*;
import java.awt.event.*;

public class MinesweeperFrame extends Frame implements Runnable, ActionListener, MouseListener, WindowListener {
	private static final long serialVersionUID = 1L;/////////////////////////////
	private Label time_L;
	private int time = 0;
	
	private MinesweeperCanvas mc;
	
	private Panel panel;
	private Label label;
	private Choice level;
	private Button button;
	
	private boolean isRunning = false;
	private boolean first = true;
	
	private Thread th;
	private int[] bombs = {10, 40, 90, 160, 250};
	private int[] size = {10, 20, 30, 40, 50};
	private int[] times = {120, 180, 2400, 240, 300};

	////////////////////////////////////////////////////////
	private Label cheet;
	////////////////////////////////////////////////////////
	
	public MinesweeperFrame() {
		setTitle("MinesweeperY");
		setBackground(new Color(100, 100, 100));
		
		//////// Time ////////
		time_L = new Label("0", Label.CENTER);
		time_L.setFont(new Font("Serif", Font.BOLD, 40));
		add(time_L, BorderLayout.NORTH);
		
		//////// Canvas ////////
		mc = new MinesweeperCanvas();
		mc.addMouseListener(this);
		mc.repaint();
		add(mc, BorderLayout.CENTER);
		
		//////// Panel ////////
		panel = new Panel();
		add(panel, BorderLayout.SOUTH);
		//////// Label ////////
		label = new Label("Select level");
		label.setFont(new Font("SansSerif", Font.PLAIN, 15));
		panel.add(label);
		//////// Choice ////////
		level = new Choice();
		for (int i = 1; i <= 5; i++) level.add(String.valueOf(i));
		panel.add(level);
		//////// Button ////////
		button = new Button("START");
		button.setFont(new Font("SansSerif", Font.BOLD, 12));
		button.addActionListener(this);
		panel.add(button);
		
		///////////////////////
		cheet = new Label("Safe");
		cheet.setFont(new Font("SansSerif", Font.PLAIN, 15));
		panel.add(cheet);
		
		addWindowListener(this);
		setSize(600, 700);// X Y
		setVisible(true);
		pack();
	}

	//////// Runnable ////////
	@Override
	public void run() {
		time = times[level.getSelectedIndex()];
		
		while (0 < time && isRunning) {
			time_L.setText(String.valueOf(time--));
			try{Thread.sleep(1000);} catch (InterruptedException e) {}
		}
		
		time_L.setText(String.valueOf(time));
		if (isRunning) mc.gameover();
		mc.repaint();
		isRunning = false;
		button.setLabel("START");
	}
	
	//////// ActionListener ////////
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			if (first) {
				mc.ready = true;
				mc.creatField(size[level.getSelectedIndex()], bombs[level.getSelectedIndex()]);
				first = false;
			}
			
			if (isRunning) {
				button.setLabel("START");
				first = true;
			} else {
				button.setLabel("QUIT");
				th = new Thread(this);
				th.start();
			}
			isRunning = !isRunning;
		} else {
			System.out.println(e);
		}
		mc.repaint();
	}
	
	//////// MouseListener ////////
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {
		if (!isRunning) return;
		
		boolean clear = false;
		int y = e.getY();
		int x = e.getX();
		int b = e.getButton();
		
		if (b == 1) clear = mc.breakBlock(y, x);
		else if(b == 3) mc.flag(y, x);

		if (clear) {
			System.out.println("Clear!");
			isRunning = false;
			mc.clearGame();
		}
		mc.repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

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