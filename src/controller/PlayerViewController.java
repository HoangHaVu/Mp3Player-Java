package controller;

import view.Main;
import view.PlayerView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Mp3Player;
import model.Track;

import java.io.ByteArrayInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class PlayerViewController {
    private Main application;
    private Mp3Player mp3Player;
    private Stage stage;
    private GridPane bottomGP,underGP;
    private PlayerView playerView;
    private boolean userInteraction,playling;
    private Button  playButton,pauseButton, volumeButton,muteButton, fwdButton, bckButton, playlist;
    public ToggleButton  shuffle, loop, shuffleactive, loopactive;
    public Slider volumeSlider, songSlider;
    private Label interpret, title, timestart,timeend;
    private Image img;
    private ImageView iv;
    private boolean playing;
    public final DateFormat TIMEFORMAT = new SimpleDateFormat("mm:ss");



    public PlayerViewController(Main application, Mp3Player mp3Player, Stage primaryStage, PlayerView playerView){
        this.application = application;
        this.mp3Player = mp3Player;
        this.stage = primaryStage;
        this.playerView = playerView;
        this.bottomGP = playerView.bottomGP;
        this.underGP = playerView.underGP;
        this.playButton = playerView.playButton;
        this.pauseButton = playerView.pauseButton;
        this.volumeButton = playerView.volumeButton;
        this.muteButton = playerView.muteButton;
        this.fwdButton = playerView.fwdButton;
        this.bckButton = playerView.bckButton;
        this.playlist = playerView.playlist;
        this.loop = playerView.loop;
        this.shuffle = playerView.shuffle;
        this.shuffleactive = playerView.shuffleactive;
        this.loopactive = playerView.loopactive;
        this.volumeSlider = playerView.volumeSlider;
        this.songSlider = playerView.songSlider;
        this.interpret = playerView.interpret;
        this.title = playerView.title;
        this.timestart= playerView.timestart;
        this.timeend = playerView.timeend;
        this.img = playerView.img;
        this.iv = playerView.iv;
        this.playing = mp3Player.getAudioPlayer().isPlaying();
        initialize();

    }
    public void initialize(){
        changeableGuiElements(mp3Player.getActSong());
        mp3Player.getIndex().addListener(((observable, oldValue, newValue) -> changeableGuiElements(mp3Player.getActPlaylist().getTrack((Integer) newValue))));
        mp3Player.getTime().addListener(((observable, oldValue, newValue) -> {if(!userInteraction) setTimeLabel((Integer) newValue);}));
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> mp3Player.volume((float)volumeSlider.getValue()));
        songSlider.valueProperty().addListener((observable, oldValue, newValue) ->{if(userInteraction) mp3Player.changeBySlider(songSlider.getValue());});
        songSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userInteraction= true;
                System.out.println(userInteraction);
            }
        });
        songSlider.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                userInteraction= false;
                System.out.println(userInteraction);

            }
        });
        playButton.addEventHandler(ActionEvent.ACTION, event -> {
            mp3Player.play();
            bottomGP.getChildren().remove(playButton);
            bottomGP.add(pauseButton,3,1);
            playling = true;
        });

        pauseButton.addEventHandler(ActionEvent.ACTION, event -> {
            mp3Player.stop();
            bottomGP.getChildren().remove(pauseButton);
            bottomGP.add(playButton,3,1);
            playling = false;
        });

        fwdButton.addEventHandler(ActionEvent.ACTION, event -> {
            mp3Player.nextSong();
            if(!playling){
                bottomGP.getChildren().remove(playButton);
                bottomGP.add(pauseButton,3,1);
                playling = true;
            }
        });

        bckButton.addEventHandler(ActionEvent.ACTION, event -> {
            mp3Player.previousSong();
            if(!playling){
                bottomGP.getChildren().remove(playButton);
                bottomGP.add(pauseButton,3,1);
                playling = true;
            }
        });
        loop.addEventHandler(ActionEvent.ACTION, event ->{
            mp3Player.loop();
            bottomGP.getChildren().remove(loop);
            bottomGP.add(loopactive,1,1);
            if(!playling){
                bottomGP.getChildren().remove(playButton);
                bottomGP.add(pauseButton,3,1);
                playling = true;
            }
        });
        loopactive.addEventHandler(ActionEvent.ACTION, event ->{
            mp3Player.loop();
            bottomGP.getChildren().remove(loopactive);
            bottomGP.add(loop,1,1);
        });

        shuffle.addEventHandler(ActionEvent.ACTION, event ->{
            mp3Player.shuffle();
            changeableGuiElements(mp3Player.getActSong());
            bottomGP.getChildren().remove(shuffle);
            bottomGP.add(shuffleactive,5,1);
            if(!playling){
                bottomGP.getChildren().remove(playButton);
                bottomGP.add(pauseButton,3,1);
                playling = true;
            }

        });
        shuffleactive.addEventHandler(ActionEvent.ACTION, event ->{
            mp3Player.shuffle();
            changeableGuiElements(mp3Player.getActSong());
            bottomGP.getChildren().remove(shuffleactive);
            bottomGP.add(shuffle,5,1);


        });

        volumeButton.addEventHandler(ActionEvent.ACTION,event -> {
            mp3Player.mute();
            underGP.getChildren().remove(volumeButton);
            underGP.add(muteButton,1,1);
        });
        muteButton.addEventHandler(ActionEvent.ACTION,event -> {
            mp3Player.unmute();
            underGP.getChildren().remove(muteButton);
            underGP.add(volumeButton,1,1);
        });
        playlist.addEventHandler(ActionEvent.ACTION,event -> {
            application.switchScene("Playlisten");
        });
    }

    private void setTimeLabel(Integer position) {
        songSlider.setMax(mp3Player.getActSong().getMp3File().getLengthInMilliseconds());
        timestart.setText(TIMEFORMAT.format(position));
        songSlider.valueProperty().setValue(position);
        // System.out.println(songSlider.getValue());
    }

    public void changeableGuiElements(Track song) {

        try {
            interpret.setText(song.getInterpret());
            title.setText(song.getTitle());
            img = new Image(new ByteArrayInputStream(song.getCover()));
            timeend.setText(song.getSongLenthMinutes());


        } catch (Exception e) {
            System.out.println("Kein Bild geladen");
        } finally {
            if (song.getInterpret()==null) {
                interpret.setText("Unbekannter Interpret");
            }
            if(song.getTitle()== null){
                title.setText("Unbekannter Titel");
            }
            if (song.getCover()==null) {
                img = new Image("/images/nocover.png");
            }
            if(song.getSongLenthMinutes()==null){
                timeend.setText("xx:xx");
            }
        }
        iv.setFitWidth(300);
        iv.setFitHeight(400);
        iv.setImage(img);
    }
}
