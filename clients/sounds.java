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
/*sounds class with a play method which will take in a filename as a string parameter*/
public class sounds {

	public void play(String fileName) {
		
		if(fileName!=null) {						//aslong as the filename is not null
			File file= new File(fileName+".wav");	//then create a newfile with the filename and ".wav" as an extension
			try {
				AudioInputStream audio = AudioSystem.getAudioInputStream(file);	//obtains an Audioinputstream of the user defined format
				Clip clip=AudioSystem.getClip();								//AudioSystem retreives the audio file and assigns it to clip
				clip.open(audio);												//Open the clip and start the audio
				clip.start();													
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				System.out.println("ERROR");
				e.printStackTrace();	
			}
		}
	}
}

