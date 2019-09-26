package controller;


import view.Main;
import view.PlaylistView;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Mp3Player;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlaylistViewController {
    private Main application;
    private Mp3Player mp3Player;
    private Stage stage;
    private PlaylistView playlistView;
    private Button playlistscenechange,openMultipleMP3,createPlaylist;
    private FileChooser fileChooser;
    private ObservableList<String> SongList;
    private ObservableList<String> PlaylistList;
    private ListView<String> Songlistview;
    private ListView<String>PlaylistView;




    public PlaylistViewController(Main application, Mp3Player mp3Player, Stage primaryStage, PlaylistView playlistView){
        this.application = application;
        this.mp3Player = mp3Player;
        this.stage = primaryStage;
        this.playlistView = playlistView;
        this.playlistscenechange = playlistView.playlistscenechange;
        this.openMultipleMP3 = playlistView.openMultipleMP3;
        this.createPlaylist = playlistView.createPlaylist;
        this.fileChooser = new FileChooser();
        this.SongList = playlistView.SongList;
        this.Songlistview = playlistView.Songlistview;
        this.PlaylistList = playlistView.PlaylistList;
        this.PlaylistView = playlistView.PlaylistView;
        initialize();
    }
    public void initialize(){

        PlaylistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Songlistview.setItems(mp3Player.findPlaylist(newValue).getSongnames());
                Songlistview.setOnMouseClicked(event -> {
                    if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                        mp3Player.setActPlaylist(mp3Player.findPlaylist(newValue));
                        mp3Player.stop();
                        mp3Player.SelectSong(Songlistview.getSelectionModel().getSelectedIndex());
                        mp3Player.play();
                    }
                });
            }
        });


        openMultipleMP3.setOnAction(event -> {
            configureFileChooser(fileChooser);
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().add(extFilter);
            List<File> list =
                    fileChooser.showOpenMultipleDialog(stage);
            if (list != null) {
                for (File file : list) {
                    try {
                        mp3Player.addnewSong(file.getAbsolutePath());
                        Songlistview.setItems(mp3Player.getActPlaylist().getSongnames());
                    } catch (InvalidDataException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (UnsupportedTagException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        createPlaylist.addEventHandler(ActionEvent.ACTION, event -> application.switchScene("CreatePlaylist"));
        playlistscenechange.addEventHandler(ActionEvent.ACTION, event -> application.switchScene("Player"));

    }

    private static void configureFileChooser(final FileChooser fileChooser){
        fileChooser.setTitle("Open Playlist");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
    }
}
