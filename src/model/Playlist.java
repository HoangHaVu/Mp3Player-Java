package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;

public class Playlist extends ArrayList<Playlist> {
	private static HashMap<String, Track> SongCollection;
	private String title;
	/*private Date creationDate;*/
	private ArrayList<Track> PlaylistSongs = new ArrayList();
	private ObservableList<String> Songnames = FXCollections.observableArrayList();

	public Playlist() {
		this.title = "Testplaylist";
	}
	public Playlist(String title,ArrayList<Track>songs,ObservableList<String>songtitles) {
		this.title = title;
		this.PlaylistSongs = songs;
		this.Songnames=songtitles;
	}
	public int numberofTracks(){
		return PlaylistSongs.size()-1;
	}

	public String getTitle(){
		return this.title;
	}

	public Track getTrack(int index) {
		return PlaylistSongs.get(index);
	}


	public void setSongnames(ObservableList<String>titles){
		this.Songnames= titles;
	}

	public void setPlaylistSongs (ArrayList <Track> SelectedSongs) {
		this.PlaylistSongs = SelectedSongs;
	}

	public ObservableList<String>getSongnames(){
		return Songnames;
	}

    public ArrayList<Track> getPlaylistSongs() {
        return PlaylistSongs;
    }

    public void setTitle(String name) {
		this.title = name;
	}


}