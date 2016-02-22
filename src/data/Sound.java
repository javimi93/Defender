package data;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	
	private String start;
	private String gameOver;
	Clip sonido=null;
	
	public Sound(){
		this.start="datos/start.wav";
		this.gameOver="datos/game_over.wav";
	}

	public void start(String sound) {
		// Se obtiene un Clip de sonido
		try {
        sonido = AudioSystem.getClip();
        
        // Se carga con un fichero wav
        switch(sound){
        case "start":
			sonido.open(AudioSystem.getAudioInputStream(new File(start)));
			break;
        case "Game Over":
        	sonido.open(AudioSystem.getAudioInputStream(new File(gameOver)));
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
