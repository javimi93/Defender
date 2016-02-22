package data;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

	private static boolean restart=true;
	private static boolean terminar=true;
	private static JLabel puntuacion;
	private static BufferedImage[] sprites;
	private int nPuntuacion=0;
	static JFrame frame;
	static JPanel panel;
	static Craft craft;
	private final static int WIDTH = 25;
	private final static int HEIGHT = 20;
	static Enemy enemy;
	private static Vector<KeyEvent> pressedKeys;

	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				for(int i=0; i<pressedKeys.size(); i++){
					if(pressedKeys.get(i).getKeyCode() == e.getKeyCode()){
						pressedKeys.remove(i);
					}
				}
				craft.keyPressed(pressedKeys);
			}

			@Override
			public void keyPressed(KeyEvent e) {
				boolean add = true;
				for(int i=0; i<pressedKeys.size(); i++){
					if(pressedKeys.get(i).getKeyCode() == e.getKeyCode()){
						add=false;
					}
				}
				if(add){
					pressedKeys.add(e);
				}
				craft.keyPressed(pressedKeys);
			}
		});
		setFocusable(true);
	}

	private void move() {
		craft.move();
	}


	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		craft.paint(g2d);
		enemy.paint(g2d,sprites);
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

	public static void tablaSprites(){
		BufferedImage bigImg;
		try {
			bigImg = ImageIO.read(new File("datos/sprite.png"));

			// The above line throws an checked IOException which must be caught.


			final int rows = 5;
			final int cols = 21;
			sprites = new BufferedImage[rows * cols];

			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < cols; j++)
				{
					sprites[(i * cols) + j] = bigImg.getSubimage(
							j * WIDTH,
							i * HEIGHT,
							WIDTH,
							HEIGHT
							);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		tablaSprites();
		while(terminar){
			Game game = new Game();
			puntuacion= new JLabel("Enemigos Destruidos : 0");
			pressedKeys = new Vector<KeyEvent>();
			frame = new JFrame("Defender");
			game.setBackground(Color.BLACK);
			restart=true;
			craft = new Craft(game, sprites);
			enemy = new Enemy(game,craft);
			int count=0;
			frame.setSize(1000,1000);
			frame.add(game);
			//			frame.getContentPane().setBackground(Color.black);
			frame.setVisible(true);
			frame.add(puntuacion, BorderLayout.NORTH);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			while (restart) {
				game.move();
				game.repaint();
				Thread.sleep(10);
				count++;
				if(craft.getEnemysACTIVOS() == 0 && count%100 == 0){
					craft.addEnemysACTIVOS();
					enemy.setPaint(true);
				}		
			}
			frame.dispose();
		}
	}

}
