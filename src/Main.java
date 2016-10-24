import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;


public class Main extends Application {
    Pane plats;
    Circle ring;
    static int dx = 2;
    static int dy = 2;
    final double raadius = 20;
    private double x = raadius;
    private double y = raadius;
    Timeline animation;
    Rectangle klots;
    ArrayList<Rectangle> tellised;

    private static final int nupu_delta = 15;

    @Override
    public void start(Stage primaryStage) throws Exception {
        seadistaLava();
        looPall();
        looLiikuvKlots();
        looTellised();
        //reageeriKokkupuutele();
    }

    private void seadistaLava() {
        plats = new Pane();
        Scene scene = new Scene(plats, 1000, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BrickBreaker");
        stage.show();
        System.out.println(plats.getBoundsInLocal());
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
        klots.setOnKeyPressed(keyEvent-> {
            KeyCode key = keyEvent.getCode();
            if (key == (KeyCode.RIGHT)) {
                System.out.println("Parem ");
                if (klots.getX() > plats.getWidth() - klots.getWidth()) {
                    klots.setX(klots.getX());
                }
                else {
                    klots.setX(klots.getX() + nupu_delta);
                }
            }
            else if (key == (KeyCode.LEFT)) {
                if (klots.getX() < 0) {
                    klots.setX(klots.getX());
                }
                else {
                    klots.setX(klots.getX() - nupu_delta);
                }
                System.out.println("Vasak ");
            }
        });
        klots.setFocusTraversable(true);
    }

    private void looTellised() {
        ArrayList<Rectangle> tellised = new ArrayList<Rectangle>();

        int tellisteVahe = 8;
        int tellisteArv = 10;
        int ridadeArv = 5;
        double tellisteSuurusX = ((plats.getWidth() - ((tellisteArv + 1) * tellisteVahe)) / tellisteArv);
        int tellisteSuurusY = 25;

        for (int i = 0; i < ridadeArv; i++) {
            for (int j = 0; j < tellisteArv; j++) {
                Rectangle tellis = new Rectangle(tellisteSuurusX, tellisteSuurusY);
                double tellisX = tellisteVahe + j * (tellisteVahe + tellisteSuurusX);
                double tellisY = tellisteVahe + i * (tellisteVahe + tellisteSuurusY);
                tellis.setX(tellisX);
                tellis.setY(tellisY);
                tellis.setFill(Color.DARKOLIVEGREEN);
                tellised.add(tellis);
                plats.getChildren().add(tellis);
            }
        }
        System.out.println(tellised);
    }

    private void looPall() {

        ring = new Circle(x, y, raadius);
        plats.getChildren().add(ring);
        ring.setFill(Color.DARKBLUE);
        ring.setStroke(Color.CORAL);
        animation = new Timeline(new KeyFrame(Duration.millis(10), e -> liigutaPalli()));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }

    protected void liigutaPalli() {
        if (x < raadius || x > plats.getWidth() - raadius) {
            dx = dx * -1;
        }
        if (y < raadius || ring.intersects(klots.getLayoutBounds())) {
            dy = dy * -1;
        }
        else if (y > plats.getHeight()) {
            animation.stop();
        }

        x += dx;
        y += dy;
        ring.setCenterX(x);
        ring.setCenterY(y);
    }
}