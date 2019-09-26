package model;
import java.io.IOException;
import java.util.Scanner;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

public class KeyboardController {

	private static final String PAUSE = "pause";
	private static final String PLAY = "play";
	private static final String STOP = "stop";
	private static final String LOOP = "loop";
	private static final String SHUFFLE = "shuffle";
	private static final String NEXT = "next";
	private static final String PREVIOUS = "previous";
	private static final String VOLUME = "volume";
	private static final String MUTE = "mute";
	private static final String UNMUTE = "unmute";
	private static final String GETPLAYLISTEN = "getPlaylisten";
	private static final String SELECTPLAYLIST = "selectPlaylist";
	private static boolean loop;

	public KeyboardController(){
		this.loop=false;
	}

	public static void main(String[] args) throws UnsupportedTagException, InvalidDataException, IOException {


		Mp3Player mp3Player;
		mp3Player = new Mp3Player();

		boolean activeSes = true;
		String command[];
		String line = new String();
		Scanner scan = new Scanner(System.in);

		System.out.println("Willkommen bei Hongis Mp3Player!");
		do {
			System.out.println("Bitte geben Sie einen Befehl ein:");
			line = scan.nextLine();
			command = line.split(" ");
			activeSes = doCommand(command, mp3Player);

		} while (activeSes);

	}

	public static boolean doCommand(String[] command, Mp3Player mp3Player) {
		if (command[0].equalsIgnoreCase(PAUSE)) {
			mp3Player.stop();
			System.out.println("Das Lied wurde pausiert");
			return true;
		}
		if (command[0].equalsIgnoreCase(PLAY)) {
			mp3Player.play();
			return true;
		}
		if (command[0].equalsIgnoreCase(STOP)) {
			mp3Player.stop();
			System.out.println("Danke für das Nutzen unseres Mp3 Players!");
			return false;
		}

		if (command[0].equalsIgnoreCase(VOLUME)) {
			float lautstärke = Float.parseFloat(command[1]);
			mp3Player.volume(lautstärke);
			return true;
		}
		if(command[0].equalsIgnoreCase(NEXT)){
			mp3Player.nextSong();
		}
		if(command[0].equalsIgnoreCase(PREVIOUS)){
			mp3Player.previousSong();
		}
		if(command[0].equalsIgnoreCase(LOOP)){
			if(!loop){
				loop=true;
			}else{
				loop=false;
			}
			mp3Player.loop();
		}
		if(command[0].equalsIgnoreCase(SHUFFLE)){
			mp3Player.shuffle();
		}

		if(command[0].equalsIgnoreCase(GETPLAYLISTEN)){
			for(Playlist playlist:mp3Player.getPlaylistManager().getPlaylisten()){
				System.out.println(playlist.getTitle());
			}
		}
		if(command[0].equalsIgnoreCase(SELECTPLAYLIST)){
			mp3Player.getPlaylistManager().SelectPlaylist(command[1]);
		}

		if (command[0].equalsIgnoreCase(MUTE)) {
			mp3Player.mute();
			return true;
		}
		if (command[0].equalsIgnoreCase(UNMUTE)) {
			mp3Player.unmute();
			return true;
		}
		 else {
			System.out.println("Bitte geben Sie einen gültigen Befehl ein");
			return true;
		}

		// switch (command [0]){
		// case PAUSE:
		// mp3Player.stop();
		// break;
		//
		// case PLAY:mp3Player.play(command[1]);
		// break;
		//
		// case STOP:mp3Player.stop();
		// return false;
		//
		// case VOLUME:
		// float lautstärke = Float.parseFloat(command[1]);
		// mp3Player.volume(lautstärke);
		// break;
		//
		// case BALANCE:
		// float zahl = Float.parseFloat(command[1]);
		// mp3Player.balance(zahl);
		// break;
		//
		// default:
		// System.out.println("Bitte geben Sie einen gültigen Befehl ein");
		// }

	}

}
