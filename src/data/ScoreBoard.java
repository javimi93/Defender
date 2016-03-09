package data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class ScoreBoard {
	
	Game game;
	private long minutos=0;
	private long segundos=0;
	private int segundos1=0;
	private int segundos2=0;
	private long segundosaux=0;
	private int nPuntuacion=0;
	private int nPuntuacionAux=0;
	private int nPuntuacion1=0;
	private int nPuntuacion2=0;
	BufferedImage [] sprites;
	
	public ScoreBoard(Game game, BufferedImage [] sprites){
		this.game=game;
		this.sprites=sprites;
	}
	
	public void paint(Graphics2D g,long time) {
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

				Color myWhite = new Color(255, 255, 0); // Color white
				int rgb = myWhite.getRGB();
				BufferedImage img = null;
				//Blanco Amarillo Verde Naranja Rojo Rosa Morado Azul Cyan
				//255 255 255, 255 255 0, 0 255 0, 255 128 0, 255 0 0, 	255 0 127, 153 0 153, 0 0 255, 0 255 255
				img = sprites[27];

				for (int i = 0; i < 45; i++) {
					for (int j = 0; j < 40; j++) {
						int osa=img.getRGB(i, j);
						if(osa!=0){
							img.setRGB(i, j, rgb);
						}
					}
				}

				g.drawImage(img, game.getWidth()/2-50, 5, 45, 40, null);
				g.drawImage(sprites[(int)minutos+27], game.getWidth()/2+30-50, 5, 45, 40, null);
				g.drawImage(sprites[37], game.getWidth()/2+30+30-50, 5, 45, 40, null);
				g.drawImage(sprites[segundos1+27], game.getWidth()/2+30+30+30-50, 5, 45, 40, null);
				g.drawImage(sprites[segundos2+27], game.getWidth()/2+30+30+30+30-50, 5, 45, 40, null);

				//Se pinta la puntuacion
				g.drawImage(sprites[0], game.getWidth()/2-250, 0, 45, 40, null);
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
					g.drawImage(sprites[nPuntuacion1+27], game.getWidth()/2+40-250, 5, 45, 40, null);
				}
				g.drawImage(sprites[nPuntuacion2+27], game.getWidth()/2+30+40-250, 5, 45, 40, null);

				//Se pinta barra de separacion

				g.setColor(Color.WHITE);
				g.drawLine(0,50,game.getWidth(),50);
	}
	
	public void addPuntuacion(){
		nPuntuacion++;
	}

}
