import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
    Pane plats;
    Circle ring;
    static int dx = 2;
    static int dy = 2;
    final double raadius = 25;
    private double x = raadius;
    private double y = raadius;
    Timeline animation;


    private static final int nupu_delta = 15;

    @Override
    public void start(Stage primaryStage) throws Exception {
        seadistaLava();
        looPall();
        looLiikuvKlots();
        //looTellised();
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

    private void looPall() {

        ring = new Circle(x, y, raadius);
        plats.getChildren().add(ring);
        ring.setFill(Color.BLACK);
        animation = new Timeline(new KeyFrame(Duration.millis(10), e -> liigutaPalli()));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();
    }


    protected void liigutaPalli() {
        System.out.println("xkoordinaat" + x);
        System.out.println("raadius" + raadius);
        System.out.println(x > plats.getWidth());
        System.out.println("platsi laius" + plats.getWidth());
        System.out.println("platsi kõrgus" + plats.getHeight());
        if (x < raadius || x > plats.getWidth() - raadius) {
            dx = dx * -1;
            System.out.println(dx);
        }
        if (y < raadius || y > plats.getHeight() - raadius) {
            dy = dy * -1;
            System.out.println(dy);
        }
        x += dx;
        y += dy;
        ring.setCenterX(x);
        ring.setCenterY(y);
    }

    private void LooTellised() {
        GridPane tellised = new GridPane();

    }

    private void looLiikuvKlots() {
        Rectangle klots = new Rectangle();
        klots.setX(450);
        klots.setY(760);
        klots.setWidth(150);
        klots.setHeight(25);
        klots.setArcWidth(20);
        klots.setArcHeight(20);
        plats.getChildren().add(klots);

        klots.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent nupp) {
                //if || ) {

                if (nupp.getCode().equals(KeyCode.RIGHT)) {
                    if (klots.getX() > plats.getWidth() - klots.getWidth()) {
                        klots.setX(klots.getX());
                    }
                    else {
                        klots.setX(klots.getX() + nupu_delta);
                        System.out.println("Parem ");
                    }
                }
                else if (nupp.getCode().equals(KeyCode.LEFT)) {
                    if (klots.getX() < 0) {
                        klots.setX(klots.getX());
                    }
                    else {
                        klots.setX(klots.getX() - nupu_delta);
                    }
                    System.out.println("Vasak ");
                }

            }

        });
        klots.setFocusTraversable(true);
    }


}