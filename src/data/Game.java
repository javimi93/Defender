package data;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

	private static boolean restart=true;
	private static boolean terminar=true;
	private static JLabel puntuacion;
	private int nPuntuacion=0;
	static JFrame frame;
	Ball ball = new Ball(this);
	Enemy enemy = new Enemy(this,ball);

	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				ball.keyPressed(e);
			}
		});
		setFocusable(true);
	}

	private void move() {
		ball.move();
	}


	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		ball.paint(g2d);
		enemy.paint(g2d);
	}

	public void gameOver() {
		//JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
		Object[] options = {"Accept","Retry"};
		int n = JOptionPane.showOptionDialog(frame,
				"Game Over",
				"Game Over",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				options,
				options[1]);
		restart=false;
		if(n==0){
			terminar=false;
		}
	}
	
	public void updatePuntuacion(){
		nPuntuacion++;
		puntuacion.setText("Enemigos Destruidos : "+nPuntuacion);
	}

	public static void main(String[] args) throws InterruptedException {
		while(terminar){
			puntuacion= new JLabel("Enemigos Destruidos : 0");
			frame = new JFrame("Defender");
			restart=true;
			Game game = new Game();
			int count=0;
			frame.add(game);
			frame.add(puntuacion, BorderLayout.NORTH);
			frame.setSize(400, 400);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			while (restart) {
				game.move();
				game.repaint();
				Thread.sleep(10);
				count++;
				if(game.ball.getEnemysACTIVOS() == 0 && count%100 == 0){
					game.ball.addEnemysACTIVOS();
					game.enemy.setPaint(true);
				}			
			}
			frame.dispose();
		}
	}

}
