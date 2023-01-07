package clients;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.*;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class sounds {

	public void play(String fileName) {
		if(fileName!=null) {	
			try {
				Player playmp3;
				FileInputStream fis =new FileInputStream(fileName);
				playmp3 = new Player(fis);
				playmp3.play();
			} catch (JavaLayerException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	
		}
//		if(fileName!=null) {
//			File file= new File(fileName+".wav");
//			try {
//				AudioInputStream audio = AudioSystem.getAudioInputStream(file);
//				Clip clip=AudioSystem.getClip();
//				clip.open(audio);
//				clip.start();
//			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
//				System.out.println("ERROR");
//				e.printStackTrace();
//			
//			}
//		}
	}
}

