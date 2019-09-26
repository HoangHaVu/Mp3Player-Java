package model;
import java.io.IOException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Track {
	private String title;
	private int length;
	private String albumTitle;
	private String interpret;
	private String soundFile;
	private byte[] cover;
	private Mp3File mp3File;

	

	public Track(String Songroot) throws UnsupportedTagException, InvalidDataException, IOException {
			this.soundFile = Songroot;
			this.mp3File = new Mp3File(soundFile);
			this.title=mp3File.getId3v2Tag().getTitle();
			this.interpret = mp3File.getId3v2Tag().getArtist();
			this.albumTitle= mp3File.getId3v2Tag().getAlbum();
			this.length = (int) mp3File.getLengthInSeconds();
			this.cover =mp3File.getId3v2Tag().getAlbumImage() ;
		}


	public String getTitle() {
		return title;
	}

	public Mp3File getMp3File(){
		return mp3File;
	}

	public String getSoundFile() {
		return soundFile;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public int getLength() {
		return length;
	}

	public String getInterpret(){
		return interpret;
	}

	public byte[] getCover(){
		return cover;
	}

	public String getSongLenthMinutes(){
		String time= null;
		long Seconds = getMp3File().getLengthInSeconds()%60;
		long Minutes = getMp3File().getLengthInSeconds()/60;
		if(Seconds>9){
			time = Minutes+":"+Seconds;
		}
		else{
			time= Minutes+":0"+Seconds;
		}

		return time;
	}


}
