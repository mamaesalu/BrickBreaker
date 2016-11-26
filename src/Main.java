import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Iterator;


public class Main extends Application {
    Pane plats;
    Circle pall;
    static int dx = 2;
    static int dy = 2;
    final double raadius = 20;
    private double x = raadius+200;
    private double y = raadius+200;
    Timeline animation;
    Rectangle klots;
    Rectangle tellis;
    ArrayList<Rectangle> tellised;
    private static final int nupu_delta = 15;
    int tellisteArv = 3;
    int ridadeArv = 2;
    Scene scene;
    int level = 1;
    Label tase;
    Label skoorHetkel;
    int skoor = 0;
    Image levelxtaust;
    Stage vajutaSpace;

    @Override
    public void start(Stage primaryStage) throws Exception {
        seadistaLava();
        kuvaTaustJaTase();
        looLiikuvKlots();
        looTellised(tellisteArv, ridadeArv);
        looPall();
    }

    private void seadistaLava() {
        plats = new Pane();
        scene = new Scene(plats, 1000, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BrickBreaker");
        stage.setResizable(false);
        stage.show();
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
        klots = new Rectangle();
        klots.setX(450);
        klots.setY(760);
        klots.setWidth(150);
        klots.setHeight(25);
        klots.setArcWidth(20);
        klots.setArcHeight(20);
        plats.getChildren().add(klots);
        klots.setOnKeyPressed(keyEvent -> {
            KeyCode key = keyEvent.getCode();
            if (key == (KeyCode.RIGHT)) {
                if (klots.getX() > plats.getWidth() - klots.getWidth()) {
                    klots.setX(klots.getX());
                } else {
                    klots.setX(klots.getX() + nupu_delta);
                }
            } else if (key == (KeyCode.LEFT)) {
                if (klots.getX() < 0) {
                    klots.setX(klots.getX());
                } else {
                    klots.setX(klots.getX() - nupu_delta);
                }
            }
        });
        klots.setFocusTraversable(true);
    }

    public void looTellised(int tellisteArv, int ridadeArv) {
        tellised = new ArrayList<Rectangle>();
        int tellisteVahe = 8;
        double tellisteSuurusX = ((plats.getWidth() - ((tellisteArv + 1) * tellisteVahe)) / tellisteArv);
        int tellisteSuurusY = 25;

        for (int i = 0; i < ridadeArv; i++) {
            for (int j = 0; j < tellisteArv; j++) {
                tellis = new Rectangle(tellisteSuurusX, tellisteSuurusY);
                double tellisX = tellisteVahe + j * (tellisteVahe + tellisteSuurusX);
                double tellisY = tellisteVahe + i * (tellisteVahe + tellisteSuurusY);
                tellis.setX(tellisX);
                tellis.setY(tellisY);
                tellis.setId(Integer.toString((i+1)*(j+1)));
                tellis.setFill(Color.DARKOLIVEGREEN);
                tellised.add(tellis);
            }
        }
        plats.getChildren().addAll(tellised);
        System.out.println(tellised);
        System.out.println(tellised.size() + " tellist platsil");
    }

    public void looPall() {
        pall = new Circle(x, y, raadius);
        plats.getChildren().add(pall);
        pall.setFill(Color.DARKBLUE);
        pall.setStroke(Color.CORAL);
        animation = new Timeline(new KeyFrame(Duration.millis(10), e -> liigutaPalli()));
        animation.setCycleCount(Animation.INDEFINITE);
        vajutaSpace();
        scene.addEventHandler(KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        if (e.getCode()== KeyCode.SPACE) {
                            animation.play();
                        }
                    }
                });
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
        vajutaSpace.setScene(sceneVajutaSpace);
        vajutaSpace.show();
        sceneVajutaSpace.addEventHandler(KeyEvent.KEY_PRESSED,
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e) {
                        if (e.getCode()== KeyCode.SPACE) {
                            vajutaSpace.close();
                            animation.play();
                        }
                    }
                });
    }

    protected void liigutaPalli() {
        kontrolliTellised();
        kontrolliKokkop6rget();
        kuvaSkoor();
        x += dx;
        y += dy;
        pall.setCenterX(x);
        pall.setCenterY(y);
        isLevelOver();
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

    private void isLevelOver() {
        if (tellised.size() == 0) {
            System.out.println("tellised otsas");
            if (level == 3) {
                youWin();
            }
            else {
                level++;
                System.out.println(level);
                animation.stop();
                plats.getChildren().remove(pall);
                plats.getChildren().remove(klots);
                plats.getChildren().remove(tase);
                tellisteArv = tellisteArv + 2;
                ridadeArv++;
                x = raadius+200;
                y = raadius+200;
                dx = Math.abs(dx)+ 1;
                dy = Math.abs(dy)+ 1;
                kuvaTaustJaTase();
                looLiikuvKlots();
                looTellised(tellisteArv, ridadeArv);
                looPall();
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

    private void kontrolliTellised() {
        Iterator<Rectangle> tellisteIter = tellised.iterator();
        while (tellisteIter.hasNext()) {
            Rectangle seetellis = tellisteIter.next();
            if (pall.intersects(seetellis.getLayoutBounds())) {
                dy = dy * -1;
                skoor++;
                tellisteIter.remove();
                plats.getChildren().remove(seetellis);
            }
        }
    }

    public void kontrolliKokkop6rget() {

        if (x < raadius || x > plats.getWidth() - raadius) {
            dx = dx * -1;
        }
        if (y < raadius || pall.intersects(klots.getLayoutBounds())) {
            dy = dy * -1;
        }
        else if (y > plats.getHeight()) {
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
