import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Klots extends Rectangle{
    private static final int deltaX = 15;

    public Klots() {
        setX(450);
        setY(760);
        setWidth(150);
        setHeight(25);
        setArcWidth(20);
        setArcHeight(20);
    }

    public void liigu(double platsiLaius, int suund){ //suund: 0-paremale,1-vasakule
        double x = getX();
        if (suund == 0) {
            if (x > platsiLaius - getWidth()) {
                setX(x);
            } else {setX(x + deltaX);}
        }
        else if (suund == 1){
            if (x < 0) {
                setX(x);
            } else {setX(x - deltaX);}
        }
    }

    public boolean pallP6rkab(Circle pall){
        if (pall.intersects(getLayoutBounds())){
            return true;
        }
        return false;
    }
}