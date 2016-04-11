package data;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {

	private final String START;
	private final String GAMEOVER;
	private final String SHOOT;
	private final String EXPLOSION;
	Clip sonido=null;
	private boolean activo=false;

	/*
	 * Constructor que inicializa los sonidos disponibles.
	 */
	public Sound(){
		this.START="datos/sonidos/start.wav";
		this.GAMEOVER="datos/sonidos/game_over.wav";
		this.SHOOT="datos/sonidos/shoot.wav";
		this.EXPLOSION="datos/sonidos/explosion.wav";
	}

	/*
	 * Comienza la reproduccion del audio seleccionado
	 */
	public void start(String sound) {
		// Se obtiene un Clip de sonido
		try {
			sonido = AudioSystem.getClip();
			activo=true;

			// Se carga con un fichero wav
			switch(sound){
			case "start":
				sonido.open(AudioSystem.getAudioInputStream(new File(START)));
				break;
			case "Game Over":
				sonido.open(AudioSystem.getAudioInputStream(new File(GAMEOVER)));
				break;
			case "shoot":
				sonido.open(AudioSystem.getAudioInputStream(new File(SHOOT)));
				break;
			case "explosion":
				sonido.open(AudioSystem.getAudioInputStream(new File(EXPLOSION)));
				break;
			}
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Comienza la reproducción
		sonido.start();
	}

	public void stop(){
		// Se obtiene un Clip de sonido
		sonido.stop();
		activo=false;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
