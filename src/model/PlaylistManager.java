package model;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.ArrayList;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlaylistManager {
	private Playlist actPlaylist,shuffleplaylist,testplaylist1,testplaylist2,FullPlaylist;
	private ArrayList<Playlist>Playlisten;
	private ArrayList<Track> AllSongs,TP1Songs,TP2Songs;
	private ObservableList<String>Playlistnames;
	private ObservableList<String>Alltitles,TP1titles,TP2titles;



	public PlaylistManager() throws UnsupportedTagException, InvalidDataException, IOException {

		ObservableList<String>Songnames = FXCollections.observableArrayList();
		this.AllSongs = new ArrayList();
		this.TP1Songs = new ArrayList();
		this.TP2Songs = new ArrayList();
		this.Playlistnames = FXCollections.observableArrayList();
		this.Alltitles = FXCollections.observableArrayList();
		this.TP1titles = FXCollections.observableArrayList();
		this.TP2titles = FXCollections.observableArrayList();
		this.Playlisten = new ArrayList<Playlist>();
		this.FullPlaylist= new Playlist();
		this.shuffleplaylist = new Playlist();
		this.testplaylist1 = new Playlist();
		this.testplaylist2 = new Playlist();
		this.actPlaylist = new Playlist();
		File f = new File("src/songs");
		System.out.println(f.getAbsolutePath());
		String [] songArray = f.list();
		int anzahl = songArray.length;
		String line;
		for (int i=0;i<anzahl;i++) {
			line = songArray[i];
			Track song = new Track(f.getAbsolutePath()+"/"+line);
			AllSongs.add(song);
			Alltitles.add(song.getTitle());
		}
		for (int i=0;i<anzahl;i++){
			if(i<anzahl/2) {
				Track song = AllSongs.get(i);
				TP1Songs.add(song);
				TP1titles.add(song.getTitle());
			}
			else{
				Track song = AllSongs.get(i);
				TP2Songs.add(song);
				TP2titles.add(song.getTitle());
			}
		}
		testplaylist1.setPlaylistSongs(TP1Songs);
		testplaylist1.setSongnames(TP1titles);
		testplaylist1.setTitle("TestPlaylist Nr.1");
		testplaylist2.setPlaylistSongs(TP2Songs);
		testplaylist2.setSongnames(TP2titles);
		testplaylist2.setTitle("TestPlaylist Nr.2");
		FullPlaylist.setSongnames(Alltitles);
		FullPlaylist.setPlaylistSongs(AllSongs);
		FullPlaylist.setTitle("All songs");
		Playlisten.add(testplaylist1);
		Playlisten.add(testplaylist2);
		Playlisten.add(FullPlaylist);
		Playlistnames.add(testplaylist1.getTitle());
		Playlistnames.add(testplaylist2.getTitle());
		Playlistnames.add(FullPlaylist.getTitle());
		actPlaylist= testplaylist1;


	}

	public Playlist SelectPlaylist(String name) {
		String Playlistname = name;
		for(int i = 0;i<Playlisten.size();i++ ){
			if(Playlisten.get(i).getTitle()==Playlistname){
				actPlaylist = Playlisten.get(i);

			}
		}
		return actPlaylist;
	}


	public Playlist getActPlaylist() {
		return actPlaylist;

	}
	public Playlist getShuffleplaylist(){
		shuffleplaylist = actPlaylist;
		Collections.shuffle(shuffleplaylist.getPlaylistSongs());
		return shuffleplaylist;
	}

	public void createPlaylist(String Title,ArrayList<Track> songs) {
		ObservableList<String> songnames = FXCollections.observableArrayList();
		for(Track song:songs){
			songnames.add(song.getTitle());
			AllSongs.add(song);
		}
		Playlist createdPlaylist = new Playlist(Title, songs,songnames);
		Playlisten.add(createdPlaylist);
		Playlistnames.add(createdPlaylist.getTitle());
	}

	public void deletePlaylist(Playlist deletelist) {
		for(Playlist list:Playlisten){
			if(list.equals(deletelist)){
				Playlisten.remove(list);
			}
		}
	}

	public ObservableList<String> Playlistnames(){
		return Playlistnames;
	}
	public ArrayList<Playlist>getPlaylisten(){return  Playlisten;}



}
