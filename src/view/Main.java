package view;

import controller.CreatePlaylistViewController;
import controller.PlayerViewController;
import controller.PlaylistViewController;
import model.Mp3Player;
import model.Track;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.*;
import java.util.Properties;


public class Main extends Application {
    private Mp3Player mp3Player;
    private PlayerView playerView;
    private PlaylistView playlistView;
    private CreatePlaylistView createPlaylistView;
    private CreatePlaylistViewController createPlaylistViewController;
    private PlaylistViewController playlistViewController;
    private PlayerViewController playerViewController;
    private Stage primaryStage;
    private Scene PlayerScene;
    private Scene PlaylistScene;
    private Scene CreatePLScene;

    public Main() throws InvalidDataException, IOException, UnsupportedTagException {
        this.mp3Player = new Mp3Player();
        this.playerView = new PlayerView();
        this.playlistView = new PlaylistView(mp3Player);
        this.createPlaylistView = new CreatePlaylistView();
        this.PlayerScene = new Scene(playerView.GetMp3View(),575,650);
        this.PlaylistScene = new Scene(playlistView.getPlaylistView(), 600, 600);
        this.CreatePLScene = new Scene(createPlaylistView.getCreateListPane(),600,600);
        PlaylistScene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        PlayerScene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
        CreatePLScene.getStylesheets().add(getClass().getResource("stylesheet.css").toExternalForm());
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage= primaryStage;
        this.playerViewController = new PlayerViewController(this,mp3Player,primaryStage,playerView);
        this.playlistViewController = new PlaylistViewController(this,mp3Player,primaryStage,playlistView);
        this.createPlaylistViewController = new CreatePlaylistViewController(this,mp3Player,primaryStage,createPlaylistView);
        primaryStage.setTitle("MP3 Player");
        try (InputStream in = new FileInputStream("save.properties")) {
            Properties prop = new Properties();
            prop.load(in);
            System.out.println("####Properties.stringPropertyNames usage####");
            for (String property : prop.stringPropertyNames()) {
                String value = prop.getProperty(property);
                System.out.println(property + "=" + value);
            }
            if(prop.getProperty("Scene")!=null||prop.getProperty("Playlist")!=null||prop.getProperty("Song")!=null||prop.getProperty("SongIndex")!=null){
                if(prop.getProperty("Scene").equals("PlayerScene")){
                    primaryStage.setScene(PlayerScene);
                }else{
                    primaryStage.setScene(PlaylistScene);
                }
                if(prop.getProperty("Playlist").equals(mp3Player.getActPlaylist().getTitle())){
                    mp3Player.setActPlaylist(mp3Player.findPlaylist(prop.getProperty("Playlist")));
                }else{}
                for(Track track:mp3Player.getActPlaylist().getPlaylistSongs())
                    if(prop.getProperty("Song").equals(track.getTitle())){
                        mp3Player.setSong(track, Integer.parseInt(prop.getProperty("SongIndex")));
                    }
            }else {
                System.out.println("Da ist nichts in der File drin");
                primaryStage.setScene(PlayerScene);}

        } catch (IOException e) {
            System.out.println("Es wurden noch keine Properties abgespeichert");
            primaryStage.setScene(PlayerScene);
        }

        //primaryStage.setScene(PlayerScene);
        primaryStage.show();


        primaryStage.setOnCloseRequest(event -> {
            try (OutputStream out = new FileOutputStream("save.properties")) {
                Properties properties = new Properties();
                if(primaryStage.getScene().equals(PlayerScene)){
                    properties.setProperty("Scene", "PlayerScene");
                }else{
                    properties.setProperty("Scene", "PlaylistScene");
                }
                properties.setProperty("Playlist",mp3Player.getActPlaylist().getTitle());
                properties.setProperty("Song",mp3Player.getActSong().getTitle());
                properties.setProperty("SongIndex", String.valueOf(mp3Player.getSongIndex()));
                properties.store(out, "This is a sample for java properties");

            } catch (IOException e) {
                e.printStackTrace();
            }
            mp3Player.closePlayer();
        });
    }


    public void switchScene(String Scenename){
        switch (Scenename){
            case "Playlisten":
                primaryStage.setScene(PlaylistScene);
                break;
            case "Player":
                playerViewController.changeableGuiElements(mp3Player.getActSong());
                primaryStage.setScene(PlayerScene);
                break;
            case"CreatePlaylist":
                primaryStage.setScene(CreatePLScene);
        }
    }
}
