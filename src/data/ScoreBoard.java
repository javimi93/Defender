package data;

import java.awt.BasicStroke;
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
	private int nPuntuacion3=0;
	private int nPuntuacion4=0;
	BufferedImage [] sprites;
	private int vidas=3;
	public Color color;
	private String chosenColour="Blanco";
	private int R=255;
	private int G=255;
	private int B=255;
	private int rgb=0;

	/*
	 * Constructor de la clase ScoreBoard que recibe el juego y los sprites por parametro.
	 */
	public ScoreBoard(Game game, BufferedImage [] sprites){
		this.game=game;
		this.sprites=sprites;
	}

	/*
	 * Metodo que pinta en pantalla la puntuacion del jugador y el tiempo de partida transcurrido
	 */
	public void paint(Graphics2D g,long time) {
		//Se dibuja el tiempo con sprites

		switch (vidas){
		case 3:
			g.drawImage(sprites[65], game.getWidth()/10, 0, 45, 40, null);
			g.drawImage(sprites[65], game.getWidth()/10+45, 0, 45, 40, null);
			break;

		case 2:
			g.drawImage(sprites[65], game.getWidth()/10, 0, 45, 40, null);
			break;

		}

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

		//Blanco Amarillo Verde Naranja Rojo Rosa Morado Azul Cyan
		//255 255 255, 255 255 0, 0 255 0, 255 128 0, 255 0 0, 	255 0 127, 153 0 153, 0 0 255, 0 255 255
		switch(chosenColour){
		case "Blanco"://255 255 255 
			color = new Color(R, G, B);
			B-=3;
			if(B==0){
				chosenColour="Amarillo";
			}
			break;
		case "Amarillo"://255 255 0
			color = new Color(R, G, B);
			R-=3;
			if(R==0){
				chosenColour="Verde";
			}
			break;
		case "Verde"://0 255 0
			color = new Color(R, G, B);
			R+=3;
			if(G!=128){
				G-=1;
			}
			if(R==255){
				chosenColour="Naranja";
			}
			break;
		case "Naranja"://255 128 0
			color = new Color(R, G, B);
			G-=2;
			if(G==0){
				chosenColour="Rojo";
			}
			break;
		case "Rojo"://255 0 0
			color = new Color(R, G, B);
			B+=1;
			if(B==127){
				chosenColour="Rosa";
			}
			break;
		case "Rosa"://255 0 127
			color = new Color(R, G, B);
			R-=3;
			if(B!=153){
				B+=2;
			}
			if(R==153){
				chosenColour="Morado";
			}
			break;
		case "Morado"://153 0 153
			color = new Color(R, G, B);
			R-=3;
			if(B!=255){
			B+=3;
			}
			if(R==0){
				chosenColour="Azul";
			}
			
			break;
		case "Azul"://0 0 255
			color = new Color(R, G, B);
			G+=3;
			if(G==255){
				chosenColour="Cyan";
			}
			break;
		case "Cyan"://0 255 255
			color = new Color(R, G, B);
			R+=3;
			if(R==255){
				chosenColour="Blanco";
			}
			break;

		}
		rgb = color.getRGB();
		
		g.drawImage(getImage(sprites[27]), game.getWidth()/2-50, 5, 45, 40, null);
		g.drawImage(getImage(sprites[(int)minutos+27]), game.getWidth()/2+30-50, 5, 45, 40, null);
		g.drawImage(getImage(sprites[37]), game.getWidth()/2+30+30-50, 5, 45, 40, null);
		g.drawImage(getImage(sprites[segundos1+27]), game.getWidth()/2+30+30+30-50, 5, 45, 40, null);
		g.drawImage(getImage(sprites[segundos2+27]), game.getWidth()/2+30+30+30+30-50, 5, 45, 40, null);

		//Se pinta la puntuacion

		nPuntuacionAux=nPuntuacion;

		nPuntuacion4 = nPuntuacionAux%10;

		nPuntuacionAux = nPuntuacionAux/10;
		nPuntuacion3= nPuntuacionAux%10;

 		nPuntuacionAux = nPuntuacionAux/10;
		nPuntuacion2 = nPuntuacionAux%10;

		nPuntuacionAux = nPuntuacionAux/10;
		nPuntuacion1 = nPuntuacionAux%10;

		if(nPuntuacion1>0){
			g.drawImage(getImage(sprites[nPuntuacion1+27]), game.getWidth()/10+35+20, 40, 45, 40, null);
		}
		if(nPuntuacion2>0 || nPuntuacion1>0){
			g.drawImage(getImage(sprites[nPuntuacion2+27]), game.getWidth()/10+20+35*2, 40, 45, 40, null);
		}
		if(nPuntuacion3>0 || nPuntuacion2>0 || nPuntuacion1>0){
			g.drawImage(getImage(sprites[nPuntuacion3+27]), game.getWidth()/10+20+35*3, 40, 45, 40, null);
		}
		g.drawImage(getImage(sprites[nPuntuacion4+27]), game.getWidth()/10+20+35*4, 40, 45, 40, null);

		//Se pinta barra de separacion

		g.setColor(Color.BLUE);
		g.setStroke(new BasicStroke(5));
		g.drawLine(0,80,game.getWidth(),80);
	}

	/*
	 * Metodo que incrementa la puntuacion del jugador
	 */
	public void addPuntuacion(){
		nPuntuacion+=50;
	}

	public int getVidas() {
		return vidas;
	}

	public void decreaseVidas() {
		vidas--;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public BufferedImage getImage(BufferedImage img){
		for (int i = 0; i < 45; i++) {
			for (int j = 0; j < 40; j++) {
				int colorAux=img.getRGB(i, j);
				if(colorAux!=0){
					img.setRGB(i, j, rgb);
				}
			}
		}
		return img;
	}

	public int getnPuntuacion() {
		return nPuntuacion;
	}

	public void setnPuntuacion(int nPuntuacion) {
		this.nPuntuacion = nPuntuacion;
	}

}
