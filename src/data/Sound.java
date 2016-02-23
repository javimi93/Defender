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
	Clip sonido=null;
	
	/*
	 * Constructor que inicializa los sonidos disponibles.
	 */
	public Sound(){
		this.START="datos/start.wav";
		this.GAMEOVER="datos/game_over.wav";
	}
	
	/*
	 * Comienza la reproduccion del audio seleccionado
	 */
	public void start(String sound) {
		// Se obtiene un Clip de sonido
		try {
        sonido = AudioSystem.getClip();
        
        // Se carga con un fichero wav
        switch(sound){
        case "start":
			sonido.open(AudioSystem.getAudioInputStream(new File(START)));
			break;
        case "Game Over":
        	sonido.open(AudioSystem.getAudioInputStream(new File(GAMEOVER)));
        	break;
        }
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Comienza la reproducción
		sonido.start();
	}

}
