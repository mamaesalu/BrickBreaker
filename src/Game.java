import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
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


public class Game {
    private Pane plats;
    private Pall pall;
    private Timeline animation;
    private Klots klots;
    private Tellised tellised;
    private Scene scene;
    private int level = 1;
    Label tase;
    Label skoorHetkel;
    private int skoor = 0;
    private Image levelxtaust;
    private Stage vajutaSpace;
    private double platsiLaius = 1000;

    public Game() {
        gamestage();
        playlevel();
    }

    private void gamestage() {
        plats = new Pane();
        scene = new Scene(plats, platsiLaius, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BrickBreakerOOP");
        stage.setResizable(false);
        stage.show();
    }

    private void playlevel(){
        kuvaTaustJaTase();
        looLiikuvKlots();
        looTellised();
        looPall();
    }

    private void kuvaTaustJaTase() {
        switch (level) {
            case 1:
                levelxtaust = new Image("level1taust.png");
                break;
            case 2:
                levelxtaust = new Image("level2taust.png");
                break;
            case 3:
                levelxtaust = new Image("level3taust.png");
        }
        BackgroundSize size = new BackgroundSize(BackgroundSize.AUTO,
                BackgroundSize.AUTO, false, false, true, true);
        BackgroundImage leveltaust = new BackgroundImage(levelxtaust,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, size);
        plats.setBackground(new Background(leveltaust));
        tase = new Label("Tase " + level);
        tase.setFont(Font.font("Calibri", 25));
        tase.setTextFill(Color.GREY);
        tase.setLayoutY(plats.getHeight()-100);
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
        LevelOver();
    }

    private void tellisteKontroll(){
        Rectangle tellis = tellised.kontrolliTellised(pall);
        if (tellis != null) {
            pall.muudaYsuunda();
            skoor++;
            plats.getChildren().remove(tellis);
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

    private void LevelOver() {
        if (tellised.size() == 0) {
            System.out.println("tellised otsas");
            if (level == 3) {
                youWin();
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

    private void youWin() {
        StackPane stack = new StackPane();
        Label teade = new Label("Hästi tehtud! Sinu võit skooriga " + skoor);
        teade.setFont(Font.font("Calibri", 46));
        stack.getChildren().add(teade);
        scene.setRoot(stack);
    }

    public void kontrolliKokkop6rget() {
        if (pall.getPallx() < pall.getPallr() || pall.getPallx() > plats.getWidth() - pall.getPallr()) {
            pall.muudaXsuunda();
        }
        if (pall.getPally() < pall.getPallr() || klots.pallP6rkab(pall)) {
            pall.muudaYsuunda();
        }
        else if (pall.getPally() > plats.getHeight()) {
            youLose();
            animation.stop();
        }
    }

    private void youLose() {
        StackPane stack = new StackPane();
        Label teade = new Label("Mäng läbi! Sinu skoor: " + skoor);
        teade.setFont(Font.font("Calibri", 46));
        stack.getChildren().add(teade);
        scene.setRoot(stack);
    }
}