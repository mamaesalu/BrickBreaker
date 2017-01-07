import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import static javafx.application.Platform.exit;


public class Game {
    private Pane plats;
    private Pall pall;
    private Timeline animation;
    private Klots klots;
    private Tellised tellised;
    private Scene scene;
    private int level = 1;
    private Label tase;
    private Label skoorHetkel;
    private int skoor = 0;
    private Image levelxtaust;
    private Stage vajutaSpace;
    private double platsiLaius = 900;

    public Game() {
        gamestage();
        playlevel();
    }

    private void gamestage() {
        plats = new Pane();
        scene = new Scene(plats, platsiLaius, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BrickBreaker");
        stage.setResizable(false);
        stage.show();
    }

    private void playlevel(){
        kuvaTaustJaTase();
        looLiikuvKlots();
        looTellised();
        looPall();
        pauseGame();
    }

    private void kuvaTaustJaTase() {
        levelxtaust = new Image("level3taust.png");
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage leveltaust = new BackgroundImage(levelxtaust,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, size);
        plats.setBackground(new Background(leveltaust));
        tase = new Label("Tase " + level);
        tase.setFont(Font.font("Calibri", 25));
        tase.setTextFill(Color.GREY);
        tase.setLayoutY(plats.getHeight()-130);
        tase.setLayoutX(plats.getWidth()-100);
        plats.getChildren().add(tase);
    }

    private void looLiikuvKlots() {
        klots = new Klots();
        plats.getChildren().add(klots);
        klots.setOnKeyPressed(e -> {
            if (e.getCode() == (KeyCode.RIGHT)) {
                int suund = 0;
                klots.liigu(plats.getWidth(), suund);
            } else if (e.getCode() == (KeyCode.LEFT)) {
                int suund = 1;
                klots.liigu(plats.getWidth(), suund);
            }
        });
        klots.setFocusTraversable(true);
    }

    public void looTellised() {
        tellised = new Tellised(platsiLaius);
        plats.getChildren().addAll(tellised);
    }

    public void looPall() {
        pall = new Pall();
        plats.getChildren().add(pall);
        animation = new Timeline(new KeyFrame(Duration.millis(10), e -> liigutaPalli()));
        animation.setCycleCount(Animation.INDEFINITE);
        vajutaSpace();
    }

    private void vajutaSpace() {
        vajutaSpace = new Stage();
        VBox vbox = new VBox();
        Scene sceneVajutaSpace = new Scene(vbox, 300, 100);
        Label label = new Label("Tase " + level+ ".");
        Label label2 = new Label("Alustamiseks vajuta tühikut");
        Label label3 = new Label("Mängi vasak-parem nooleklahvidega");
        label.setFont(Font.font("Calibri", 24));
        vbox.getChildren().addAll(label, label2, label3);
        vajutaSpace.initStyle(StageStyle.UNDECORATED);
        vajutaSpace.setScene(sceneVajutaSpace);
        vajutaSpace.show();
        sceneVajutaSpace.setOnKeyPressed(e-> {
            if (e.getCode()== KeyCode.SPACE) {
                vajutaSpace.close();
                animation.play();
            }
        });
    }

    protected void liigutaPalli() {
        tellisteKontroll();
        kontrolliKokkop6rget();
        kuvaSkoor();
        pall.liigu();
        levelOver();
    }

    private void tellisteKontroll(){
        Rectangle tellis = tellised.kontrolliTellised(pall);
        if (tellis != null) {
            pall.muudaYsuunda();
            skoor++;
            plats.getChildren().remove(tellis);
        }
    }

    public void kontrolliKokkop6rget() {
        if (pall.getPallx() < pall.getPallr() || pall.getPallx() > plats.getWidth() - pall.getPallr()) {
            pall.muudaXsuunda();
        }
        if (pall.getPally() < pall.getPallr()) {
            pall.muudaYsuunda();
        }
        if (klots.pallP6rkab(pall)) {
            pall.muudaYklotsilt(klots.getKlotsX(), klots.getKlotsilaius());
        }
        else if (pall.getPally() > plats.getHeight()) {
            youLose();
            animation.stop();
        }
    }

    private void kuvaSkoor() {
        plats.getChildren().remove(skoorHetkel);
        skoorHetkel = new Label("Skoor " + skoor);
        skoorHetkel.setFont(Font.font("Calibri", 25));
        skoorHetkel.setTextFill(Color.GREY);
        skoorHetkel.setLayoutY(plats.getHeight()-100);
        skoorHetkel.setLayoutX(plats.getWidth()-100);
        plats.getChildren().add(skoorHetkel);
    }

    private void levelOver() {
        if (tellised.size() == 0) {
            System.out.println("tellised otsas");
            if (level == 3) {
                youWin();
                animation.stop();
            }
            else {
                level++;
                animation.stop();
                plats.getChildren().removeAll(pall, klots, tase);
                tellised.nextLevel();
                pall.lisakiirust();
                playlevel();
            }
        }
    }

    private void pauseGame(){
        scene.setOnKeyPressed(e -> {
                    if (e.getCode() == (KeyCode.P)) {           //pause "P"
                        animation.pause();
                    }
                    if (e.getCode() == (KeyCode.R)) {           //resume "R"
                        animation.play();
                    }
                }
            );
        Label pause = new Label("P - pause");                   //lisab mänguväljale info - paus P tähe all
        pause.setFont(Font.font("Calibri", 25));
        pause.setTextFill(Color.GREY);
        pause.setLayoutY(plats.getHeight()-130);
        plats.getChildren().add(pause);

        Label resume = new Label("R - resume");                 //lisab mänguväljale info - jätka mängu R tähe all
        resume.setFont(Font.font("Calibri", 25));
        resume.setTextFill(Color.GREY);
        resume.setLayoutY(plats.getHeight()-100);
        plats.getChildren().add(resume);
    }


    private void youWin() {
        StackPane stack = new StackPane();
        Label teade = new Label("Hästi tehtud! Sinu võit skooriga " + skoor);
        teade.setFont(Font.font("Calibri", 46));
        stack.getChildren().add(teade);
        scene.setRoot(stack);
    }

    private void youLose() {
        StackPane stack = new StackPane();
        Label teade = new Label("Mäng läbi! Sinu skoor: " + skoor);
        teade.setFont(Font.font("Calibri", 46));
        Button newGameBtn = new Button("Uus mäng");
        newGameBtn.setFont(Font.font("Calibri", 46));
        Button exitBtn = new Button("Välju");
        exitBtn.setFont(Font.font("Calibri", 46));
        stack.setAlignment(newGameBtn, Pos.BOTTOM_RIGHT);
        stack.setAlignment(exitBtn, Pos.BOTTOM_LEFT);
        stack.getChildren().addAll(teade, newGameBtn, exitBtn);
        scene.setRoot(stack);
        newGameBtn.setOnMouseClicked(e-> {
            scene.getWindow().hide();
            new Game();
        });
        newGameBtn.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                scene.getWindow().hide();
                tellised.firstLevel();
                pall.firstLevel();
                new Game();
            }
        });
        exitBtn.setOnMouseClicked(e-> {
            exit();
        });
        exitBtn.setOnKeyPressed(e-> {
            if (e.getCode() == KeyCode.ENTER) {
                exit();
            }
        });
    }
}