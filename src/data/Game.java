package data;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
	//private static int minutos1=0;
	//private static int minutos2=0;
	private static int segundos1=0;
	private static int segundos2=0;
	private static long TInicio; //Variables para determinar el tiempo de ejecución
	private static long TFin;
	private static long time;
	private static long minutos=0;
	private static long segundos=0;
	private static long segundosaux=0;
	private static boolean terminar=true;
	//private static JLabel puntuacion;
	private static Dimension dim;
	//private static JLabel tiempo;
	private static BufferedImage[] sprites;
	private int nPuntuacion=0;
	private int nPuntuacionAux=0;
	private static int nPuntuacion1=0;
	private static int nPuntuacion2=0;
	static Sound sound;
	private static JFrame frame;
	private static Craft craft;
	static Villager villager;
	private static int WIDTH = 45;
	private final static int HEIGHT = 40;
	static Enemy enemy;

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
			 * Gestiona cuando se suelta una tecla.
			 */
			public void keyReleased(KeyEvent e) {
				craft.keyReleased(e);
			}

			@Override
			/*
			 * Gestiona cuando se pulsa una tecla.
			 */
			public void keyPressed(KeyEvent e) {
				craft.keyPressed(e);
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
		villager.paint(g2d, sprites);



		//Se dibuja el tiempo con sprites

		minutos=TimeUnit.MILLISECONDS.toMinutes(time);
		segundos= TimeUnit.MILLISECONDS.toSeconds(time) - 
				TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));
		if(segundos!=segundosaux){
			segundos2++;
		}	
		segundosaux=segundos;

		if(segundos2==10){
			segundos1++;
			segundos2=0;
		}
		if(segundos1==6){
			segundos1=0;
			segundos2=0;
		}

		Color myWhite = new Color(255, 0, 0); // Color white
		int rgb = myWhite.getRGB();
	    BufferedImage img = null;
		img = sprites[27];

		/*for (int i = 0; i < 45; i++) {
		    for (int j = 0; j < 40; j++) {
		        img.setRGB(i, j, rgb);
		    }
		}*/
		
		g.drawImage(img, (int)dim.getWidth()/2-50, 5, 45, 40, null);
		g.drawImage(sprites[(int)minutos+27], (int)dim.getWidth()/2+30-50, 5, 45, 40, null);
		g.drawImage(sprites[37], (int)dim.getWidth()/2+30+30-50, 5, 45, 40, null);
		g.drawImage(sprites[segundos1+27], (int)dim.getWidth()/2+30+30+30-50, 5, 45, 40, null);
		g.drawImage(sprites[segundos2+27], (int)dim.getWidth()/2+30+30+30+30-50, 5, 45, 40, null);

		//Se pinta la puntuacion
		g.drawImage(sprites[0], (int)dim.getWidth()/2-250, 0, 45, 40, null);
		if(nPuntuacion!=nPuntuacionAux){
			nPuntuacion2++;
		}
		nPuntuacionAux=nPuntuacion;
		
		if(nPuntuacion2==10){
			nPuntuacion1++;
			nPuntuacion2=0;
		}
		if(nPuntuacion1==10){
			nPuntuacion1=0;
			nPuntuacion2=0;
		}
		if(nPuntuacion1 > 0){
			g.drawImage(sprites[nPuntuacion1+27], (int)dim.getWidth()/2+40-250, 5, 45, 40, null);
		}
		g.drawImage(sprites[nPuntuacion2+27], (int)dim.getWidth()/2+30+40-250, 5, 45, 40, null);

		//Se pinta barra de separacion
		
		g.setColor(Color.WHITE);
		g.drawLine(0,50,(int)dim.getWidth(),50);

		g.drawLine(-1920+craft.getX(), 960, -1000+craft.getX(), 800);
		g.drawLine(-600+craft.getX(), 800, -800+craft.getX(), 960);
		g.drawLine(-400+craft.getX(), 960, -600+craft.getX(), 800);
		g.drawLine(-200+craft.getX(), 800, -400+craft.getX(), 960);
		g.drawLine(0+craft.getX(),960,-200+craft.getX(),800);	

		g.drawLine(0+craft.getX(),960,200+craft.getX(),800);	
		g.drawLine(200+craft.getX(), 800, 400+craft.getX(), 960);
		g.drawLine(400+craft.getX(), 960, 600+craft.getX(), 800);
		g.drawLine(600+craft.getX(), 800, 800+craft.getX(), 960);
		g.drawLine(800+craft.getX(), 960, 1000+craft.getX(), 800);
	}

	/*
	 * Metodo que invoca el modo Game Over.
	 */
	public void gameOver() {
		Object[] options = {"Accept","Retry"};
		sound.start("Game Over");
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	}

	/*
	 * Inicializa la tabla de los spirtes
	 */
	public static void tablaSprites(){
		BufferedImage bigImg;
		try {
			bigImg = ImageIO.read(new File("datos/imagenes/sprites1.png"));

			int rows = 67;
			int cols = 1;
			sprites = new BufferedImage[70 * cols];

			for (int i = 0; i < rows; i++)
			{
				for (int j = 0; j < cols; j++)
				{
					sprites[(i * cols) + j] = bigImg.getSubimage(
							i * WIDTH,
							j * HEIGHT,
							WIDTH,
							HEIGHT
							);

				}
			}

			WIDTH=65;
			bigImg = ImageIO.read(new File("datos/imagenes/sprites2.png"));
			sprites[68] = bigImg.getSubimage(0 * WIDTH,0 * HEIGHT,WIDTH,HEIGHT);
			sprites[69] = bigImg.getSubimage(1 * WIDTH,0 * HEIGHT,WIDTH,HEIGHT);
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
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		//Se inicia la pantalla de introduccion del juego.
		Game game = new Game();
		frame = new JFrame("Defender");
		JPanel menu= new JPanel();
		ImageIcon icon = new ImageIcon("datos/imagenes/intro.png");
		Image image = icon.getImage().getScaledInstance((int)dim.getWidth(), (int)dim.getHeight(), Image.SCALE_SMOOTH);
		icon.setImage(image);
		JLabel imgMenu=new JLabel(icon);
		menu.add(imgMenu);
		menu.setBackground(Color.BLACK);
		frame.add(game);
		frame.add(menu);
		frame.setSize(dim);
		frame.setVisible(true); 
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//Se inicia el sonido de la intro del juego durante 3 segundos y medio.
		sound.start("start");
		Thread.sleep(3500);

		//Si no hay que terminar el juego
		while(terminar){
			//Se crea la pantalla del juego, con el tiempo transcurrido y la puntuacion
			segundos1=0;
			segundos2=0;
			nPuntuacion1=0;
			nPuntuacion2=0;
			TInicio = System.currentTimeMillis(); //Tomamos la hora en que inicio el algoritmo y la almacenamos en la variable inicio
			restart=true; 
			game = new Game();
			//puntuacion= new JLabel("Enemigos Destruidos : 0");
			//tiempo= new JLabel(new ImageIcon(sprites[10]));
			game.setBackground(Color.BLACK);
			//puntuacion.setLocation(1000, 0);
			//puntuacion.setForeground(Color.WHITE);
			//tiempo.setForeground(Color.WHITE);
			//game.add(puntuacion);
			//game.add(tiempo);
			villager= new Villager(dim.getWidth(),craft);
			craft = new Craft(game, sprites,villager);
			enemy = new Enemy(game,craft);			
			frame.setSize(dim);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			frame.add(game);
			frame.setVisible(true); 
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			int count=0;

			//Si el jugador decide reintentar.
			while (restart) {
				game.move();
				game.repaint();
				TFin = System.currentTimeMillis(); //Se toma la hora en que finalizó el algoritmo y se almacena en la variable T
				time = TFin - TInicio; //Se calculan los milisegundos de diferencia
				Thread.sleep(10);
				count++;

				//Renace el enemigo si ha muerto
				if(craft.getEnemysACTIVOS() == 0 && count%100 == 0){
					craft.addEnemysACTIVOS();
					enemy.setPaint(true);
				}

				//Muestra el tiempo de forma formateada
				/*tiempo.setText("Tiempo Transcurrido: "+String.format("%d min, %d sec", 
						TimeUnit.MILLISECONDS.toMinutes(time),
						TimeUnit.MILLISECONDS.toSeconds(time) - 
						TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
						));*/
			}
		}
		frame.dispose();
	}

}
