import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {
    Pane plats;
    private static final int nupu_delta = 10;

    @Override
    public void start(Stage primaryStage) throws Exception {
        seadistaLava();
        looPall();
        looLiikuvKlots();
        //looTellised();
        //reageeriPuutele();
    }

    private void seadistaLava() {
        plats = new Pane();
        Scene scene = new Scene(plats, 1000, 800);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("BrickBreaker");
        stage.show();
    }

    private void looPall(){
        Circle ring = new Circle(12);
        plats.getChildren().add(ring);
        ring.setCenterX(500);
        ring.setCenterY(400);
        ring.setFill(Color.BLACK);
    }

    private void LooTellised() {
        GridPane tellised = new GridPane();

    }

    private void looLiikuvKlots() {
        Rectangle klots = new Rectangle();
        klots.setX(450);
        klots.setY(780);
        klots.setWidth(100);
        klots.setHeight(20);
        plats.getChildren().add(klots);
        klots.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent nupp) {
                if (nupp.getCode().equals(KeyCode.RIGHT)) {
                    klots.setX(klots.getX() + nupu_delta);
                    System.out.println("Parem " + nupp.getText());
                }
                else if (nupp.getCode().equals(KeyCode.LEFT)) {
                    klots.setX(klots.getX() - nupu_delta);
                    System.out.println("Vasak " + nupp.getText());
                }

            }

        });
        klots.setFocusTraversable(true);
    }

}
