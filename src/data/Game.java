package data;

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

import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Game extends JPanel {

	private static boolean restart=true;
	private static boolean terminar=true;
	private static JLabel puntuacion;
	private static JLabel tiempo;
	private static BufferedImage[] sprites;
	private int nPuntuacion=0;
	private static Sound sound;
	private static JFrame frame;
	private static Craft craft;
	private final static int WIDTH = 50;
	private final static int HEIGHT = 40;
	static Enemy enemy;
	private static Vector<KeyEvent> pressedKeys;

	/*
	 * Constructor del juego que habilita la deteccion de teclas pulsadas y soltadas.
	 */
	public Game() {
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			/*
			 * Gestiona cuando se suelta una tecla, se elimina de la cola de teclas pulsadas.
			 */
			public void keyReleased(KeyEvent e) {
				for(int i=0; i<pressedKeys.size(); i++){
					if(pressedKeys.get(i).getKeyCode() == e.getKeyCode()){
						pressedKeys.remove(i);
					}
				}
				System.out.println("En el Released " + pressedKeys.size());
				craft.keyPressed(pressedKeys);
			}

			@Override
			/*
			 * Gestiona cuando se pulsa una tecla, se añade a la cola de teclas pulsadas.
			 */
			public void keyPressed(KeyEvent e) {
				System.out.println("En el Keypressed con " + pressedKeys.size());
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

	/*
	 * Mueve la nave.
	 */
	private void move() {
		craft.move();
	}
	
	/*
	 * Pinta en cada invocacion todos los elementos del juego.
	 */
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		craft.paint(g2d);
		enemy.paint(g2d,sprites);
		g.setColor(Color.WHITE);
		g.drawLine(0,this.getWidth(),200,800);	
		g.drawLine(200, 800, 400, 1000);
		g.drawLine(400, 1000, 600, 800);
		g.drawLine(600, 800, 800, 1000);
	}

	/*
	 * Metodo que invoca el modo Game Over.
	 */
	public void gameOver() {
		Object[] options = {"Accept","Retry"};
		sound.start("Game Over");
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

	/*
	 * Actualiza la puntuacion cada vez que se destruye un enemigo.
	 */
	public void updatePuntuacion(){
		nPuntuacion++;
		puntuacion.setText("Enemigos Destruidos : "+nPuntuacion);
	}

	/*
	 * Inicializa la tabla de los spirtes
	 */
	public static void tablaSprites(){
		BufferedImage bigImg;
		try {
			bigImg = ImageIO.read(new File("datos/sprites2.png"));

			final int rows = 9;
			final int cols = 10;
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
			e.printStackTrace();
		}
	}
	
	/*
	 * Main del juego que lo inicia.
	 */
	public static void main(String[] args) throws InterruptedException {
		tablaSprites();
		sound=new Sound();
		//Se inicia la pantalla de introduccion del juego.
		Game game = new Game();
		frame = new JFrame("Defender");
		JPanel menu= new JPanel();
		JLabel imgMenu=new JLabel(new ImageIcon("datos/intro.png"));
		game.setBackground(Color.BLACK);
		menu.add(imgMenu);
		menu.setBackground(Color.BLACK);
		frame.add(game);
		frame.add(menu);
		frame.setSize(1000,1000);
		frame.setVisible(true); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Se inicia el sonido de la intro del juego durante 3 segundos y medio.
		sound.start("start");
		Thread.sleep(3500);
		
		//Si no hay que terminar el juego
		while(terminar){
			//Se crea la pantalla del juego, con el tiempo transcurrido y la puntuacion
			long TInicio, TFin, time; //Variables para determinar el tiempo de ejecución
			TInicio = System.currentTimeMillis(); //Tomamos la hora en que inicio el algoritmo y la almacenamos en la variable inicio
			restart=true; 
			game = new Game();
			puntuacion= new JLabel("Enemigos Destruidos : 0");
			tiempo= new JLabel("Tiempo Transcurrido: 0");
			game.setBackground(Color.BLACK);
			puntuacion.setLocation(1000, 0);
			pressedKeys = new Vector<KeyEvent>();
			puntuacion.setForeground(Color.WHITE);
			tiempo.setForeground(Color.WHITE);
			game.add(puntuacion);
			game.add(tiempo);	
			craft = new Craft(game, sprites);
			enemy = new Enemy(game,craft);			
			frame.setSize(1000,1000);
			frame.add(game);
			frame.setVisible(true); 
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			int count=0;
			
			//Si el jugador decide reintentar.
			while (restart) {
				game.move();
				game.repaint();
				Thread.sleep(10);
				count++;
				
				//Renace el enemigo si ha muerto
				if(craft.getEnemysACTIVOS() == 0 && count%100 == 0){
					craft.addEnemysACTIVOS();
					enemy.setPaint(true);
				}
				TFin = System.currentTimeMillis(); //Se toma la hora en que finalizó el algoritmo y se almacena en la variable T
				time = TFin - TInicio; //Se calculan los milisegundos de diferencia
				//Muestra el tiempo de forma formateada
				tiempo.setText("Tiempo Transcurrido: "+String.format("%d min, %d sec", 
						TimeUnit.MILLISECONDS.toMinutes(time),
						TimeUnit.MILLISECONDS.toSeconds(time) - 
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
						));
			}
			frame.dispose();
		}
	}

}
