package model
        ;

import java.io.IOException;
import java.util.ArrayList;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import de.hsrm.mi.eibo.simpleplayer.SimpleAudioPlayer;
import de.hsrm.mi.eibo.simpleplayer.SimpleMinim;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;

public class Mp3Player {

    private Thread CheckPlayingTH;
    private SimpleAudioPlayer audioPlayer;
    private PlaylistManager PlaylistManager;
    private SimpleMinim minim;
    private Playlist actPlaylist;
    private int songIndex;
    private SimpleIntegerProperty time,index;
    private SimpleObjectProperty song;
    private boolean loop, paused;

    public Mp3Player() throws InvalidDataException, IOException, UnsupportedTagException {
        this.loop = false;
        this.paused = false;
        this.time = new SimpleIntegerProperty(0);
        this.actPlaylist = new Playlist();
        this.PlaylistManager = new PlaylistManager();
        this.actPlaylist = PlaylistManager.getActPlaylist();
        this.minim = new SimpleMinim(true);
        this.songIndex = 0;
        this.index = new SimpleIntegerProperty(0);
        this.song = new SimpleObjectProperty();
        loadSong();
    }

    /**
     * class Runnable extends Thread {
     * String song;
     * <p>
     * public Runnable(String song) {
     * this.song = song;
     * }
     * <p>
     * public void run() {
     * audioPlayer = minim.loadMP3File(song);
     * audioPlayer.play();
     * }
     * }
     * <p>
     * class ContiTH extends Thread {
     * SimpleAudioPlayer contiPlayer;
     * <p>
     * public ContiTH(SimpleAudioPlayer contiPlayer) {
     * this.contiPlayer = contiPlayer;
     * }
     * <p>
     * public void run() {
     * int pos = audioPlayer.position();
     * audioPlayer.play(pos);
     * <p>
     * }
     * }
     **/

    public void loadSong() {
        audioPlayer = minim.loadMP3File(actPlaylist.getTrack(songIndex).getSoundFile());
        index.set(songIndex);

    }
    public void setSong(Track track,int trackindex){
        audioPlayer= minim.loadMP3File(track.getSoundFile());
        index.set(trackindex);
    }

    public void SelectSong(int k){
        songIndex = k ;
        audioPlayer = minim.loadMP3File(actPlaylist.getTrack(songIndex).getSoundFile());
        index.set(songIndex);

    }

    public void closePlayer() {
        if (!paused&&CheckPlayingTH!=null) {
            CheckPlayingTH.interrupt();
            minim.stop();
        } else {
            minim.stop();
        }


    }
    public void play() {
        if(!paused&& audioPlayer.position() ==0){
        audioPlayer.play();
        paused= false;
        }
        else{
            int pos = audioPlayer.position();
            audioPlayer.play(pos);
            paused = false;
        }
        this.CheckPlayingTH = new Thread(() -> {
            while (audioPlayer.isPlaying() && !CheckPlayingTH.isInterrupted()) {
                try {
                    Thread.sleep(100);
                    Platform.runLater(() -> time.setValue(audioPlayer.position()));
                } catch (InterruptedException e) {
                    System.out.println("Sorry ich schlafe");
                    CheckPlayingTH.interrupt();
                }
            }
            if (!CheckPlayingTH.isInterrupted()) {
                if (!(audioPlayer.isPlaying()) && !paused) {
                    Platform.runLater(() -> nextSong());
                }
            }
        });
        this.CheckPlayingTH.start();
    }

    public SimpleAudioPlayer getAudioPlayer(){
        return  audioPlayer;
    }
    public void pause(){
        CheckPlayingTH.interrupt();
        audioPlayer.pause();
        paused = true;
    }


    public void nextSong() {
        if (songIndex == actPlaylist.numberofTracks()) {
            songIndex = 0;
            index.set(songIndex);

        } else {
            songIndex = songIndex + 1;
            index.set(songIndex);
        }
        if(CheckPlayingTH!=null) {
            CheckPlayingTH.interrupt();
        }
        stop();
        loadSong();
        play();

    }

    public void previousSong() {
        if (songIndex == 0) {
            songIndex = actPlaylist.numberofTracks();
            index.set(songIndex);
        } else {
            songIndex = songIndex - 1;
            index.set(songIndex);
        }
        if(CheckPlayingTH!=null) {
            CheckPlayingTH.interrupt();
        }
        stop();
        loadSong();
        play();
    }

    public void stop() {
        if(CheckPlayingTH!=null) {
            CheckPlayingTH.interrupt();
        }
        audioPlayer.pause();
    }


    public void loop() {
        if (loop) {
            audioPlayer.loop(songIndex);
        }else{
            audioPlayer.play();
        }
    }

    public void addnewSong(String path) throws InvalidDataException, IOException, UnsupportedTagException {
        Track newSong = new Track(path);
        actPlaylist.getPlaylistSongs().add(newSong);
        actPlaylist.getSongnames().add(newSong.getTitle());
    }

    public void shuffle() {
        stop();
        actPlaylist = PlaylistManager.getShuffleplaylist();
        loadSong();
        play();
    }


    public void volume(float value) {
        audioPlayer.setGain(value);

    }


    public void mute() {
        audioPlayer.mute();
    }

    public void unmute() {
        audioPlayer.unmute();
    }

    public Playlist getActPlaylist(){
        return actPlaylist;
    }
    public ObservableList<String> getPlaylisten(){
        return PlaylistManager.Playlistnames();
    }
    public Track getActSong(){
        return actPlaylist.getTrack(songIndex);
    }

    public PlaylistManager getPlaylistManager(){
        return PlaylistManager;
    }


    public SimpleIntegerProperty getTime(){
        return time;
    }

    public SimpleIntegerProperty getIndex(){
        return index;
    }
    public int getSongIndex(){
        return songIndex;
    }

    public void changeBySlider(double slidervalue){
        pause();
        //System.out.println("alte audioPlayer Position:"+audioPlayer.position());
        int songPosition= (int) slidervalue;
        audioPlayer.cue(songPosition);
        //System.out.println("mitgegebene Position:"+songPosition);
        //System.out.println("neue audioPlayer Position:"+audioPlayer.position());
        play();
    }
    public void setActPlaylist(Playlist playlist){
        actPlaylist= playlist;
        if(!audioPlayer.isPlaying()){
            loadSong();
        }
    }
    public void createPlaylist(String title, ArrayList<Track>songs){
        PlaylistManager.createPlaylist(title,songs);
    }

    public Playlist findPlaylist(String title){
        return PlaylistManager.SelectPlaylist(title);
    }

}
