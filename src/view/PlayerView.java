package view;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


public class PlayerView {
    public BorderPane Mp3PlayerView;
    public GridPane bottomGP,underGP;
    public VBox centerVB, topVB, bottomVB;
    public HBox SongsliderAndTime;
    public Main application;
    public boolean userInteraction,playling;
    public Button  playButton,pauseButton, volumeButton,muteButton, fwdButton, bckButton, playlist;
    public ToggleButton  shuffle, loop, shuffleactive, loopactive;
    public Slider volumeSlider, songSlider;
    public Label interpret, title, timestart,timeend;
    public Image img;
    public ImageView iv;




    public PlayerView(){
        this.iv = new ImageView();

        try {

            UIElements();

            //top
            songSlider.getStyleClass().add("progressbar");
            topVB.setAlignment(Pos.CENTER);
            topVB.setSpacing(30);
            topVB.setPadding(new Insets(20,20,0,20));
            SongsliderAndTime.getChildren().addAll(timestart,songSlider,timeend);
            SongsliderAndTime.setAlignment(Pos.CENTER);
            topVB.getChildren().addAll(new ImageViewPane(iv), SongsliderAndTime);


            // center


            title = new Label();
            title.getStyleClass().add("b");
            interpret = new Label();
            interpret.getStyleClass().add("k");

            centerVB.getChildren().addAll(title, interpret);
            centerVB.setAlignment(Pos.CENTER);


            // bottom

            volumeButton.getStyleClass().add("volumeButton");
            volumeButton.getStyleClass().add("volumeButton:hover");
            muteButton.getStyleClass().add("muteButton");
            muteButton.getStyleClass().add("muteButton:hover");
            bckButton.getStyleClass().add("bckButton");
            bckButton.getStyleClass().add("backButton:hover");
            playButton.getStyleClass().add("playButton");
            playButton.getStyleClass().add("playButton:hover");
            pauseButton.getStyleClass().add("pauseButton");
            pauseButton.getStyleClass().add("pauseButton:hover");
            fwdButton.getStyleClass().add("fwdButton");
            fwdButton.getStyleClass().add("fwdButton:hover");
            shuffle.getStyleClass().add("shuffleButton");
            shuffle.getStyleClass().add("shuffleButton:hover");
            shuffleactive.getStyleClass().add("shuffleButtonactive");
            shuffleactive.getStyleClass().add("shuffleButtonactive:hover");
            loop.getStyleClass().add("loopButton");
            loop.getStyleClass().add("loopButton:hover");
            loopactive.getStyleClass().add("loopButtonactive");
            loopactive.getStyleClass().add("loopButtonactive:hover");
            playlist.getStyleClass().add("playlistButton");
            playlist.getStyleClass().add("playlistButton:hover");
            volumeSlider.getStyleClass().add("volumeSlider");
            volumeSlider.setTooltip(new Tooltip("Volume"));


            bottomGP.setHgap(50);
            bottomGP.setVgap(20);
            bottomGP.setPadding(new Insets(0,55,15,20));
            bottomGP.add(bckButton, 2,1,1,1);
            bottomGP.add(playButton, 3,1,1,1);
            bottomGP.add(fwdButton,4,1,1,1);
            bottomGP.add(shuffle, 5,1,1,1);
            bottomGP.add(loop,1,1,1,1);
           /* bottomGP.add(volumeSlider,2,2,1,1);
            bottomGP.add(playlist,5,2);
            bottomGP.add(volumeButton,1,2);
            bottomGP.setColumnSpan(volumeSlider,3);*/
            bottomGP.setAlignment(Pos.CENTER);

            underGP.setPadding(new Insets(0,0,20,0));
            underGP.add(volumeButton,1,1);
            underGP.add(volumeSlider,2,1);
            underGP.add(playlist,5,1);
            underGP.setColumnSpan(volumeSlider,3);
            underGP.setAlignment(Pos.CENTER);

            bottomVB.getChildren().addAll(bottomGP,underGP);
            bottomVB.setAlignment(Pos.CENTER);


            Mp3PlayerView.setTop(topVB);
            Mp3PlayerView.setCenter(centerVB);
            Mp3PlayerView.setBottom(bottomVB);


            //Mp3PlayerView.setMaxSize(550,600);

        }

        catch(Exception e) {
            e.printStackTrace();

        }
    }

    private void UIElements () {
        SongsliderAndTime = new HBox();
        playButton = new Button();
        pauseButton= new Button();
        fwdButton = new Button();
        bckButton = new Button();
        volumeButton = new Button();
        muteButton = new Button();
        volumeSlider = new Slider(-50, 20, 0);
        songSlider = new Slider();
        shuffle = new ToggleButton();
        loop = new ToggleButton();
        loopactive = new ToggleButton();
        shuffleactive = new ToggleButton();
        Mp3PlayerView = new BorderPane();
        userInteraction= false;
        timestart = new Label("00:00");
        timeend = new Label("00:00");
        playlist = new Button();
        topVB = new VBox();
        centerVB = new VBox();
        bottomVB = new VBox();
        bottomGP = new GridPane();
        underGP = new GridPane();

    }

    public BorderPane GetMp3View(){
        return Mp3PlayerView;
    }

}
