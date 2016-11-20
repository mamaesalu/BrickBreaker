import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
    int telliseId;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        seadistaLava();
        looLiikuvKlots();
        looTellised();
        looPall();
    }

    private void seadistaLava() {
        plats = new Pane();
        scene = new Scene(plats, 1000, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BrickBreaker");
        stage.show();
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

    public void looTellised() {
        tellised = new ArrayList<Rectangle>();
        int tellisteVahe = 8;
        int tellisteArv = 10;
        int ridadeArv = 5;
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
        animation.play();
    }

    protected void liigutaPalli() {
        kontrolliTellised();
        kontrolliKokkop6rget();
        x += dx;
        y += dy;
        pall.setCenterX(x);
        pall.setCenterY(y);
        isGameOver();
    }

    private void isGameOver() {
        if (tellised.size() == 0) {
            System.out.println("tellised otsas");
            youWin();
            animation.stop();
        }
    }

    private void youWin() {
        StackPane stack = new StackPane();
        Label teade = new Label("Hästi tehtud! Sinu võit!");
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
        Label teade = new Label("Mäng läbi!");
        teade.setFont(Font.font("Calibri", 46));
        stack.getChildren().add(teade);
        scene.setRoot(stack);
    }
}
