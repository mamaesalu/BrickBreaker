import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Klots extends Rectangle{
    private static final int deltaX = 15;

    public Klots() {
        setX(450);
        setY(770);
        setWidth(150);
        setHeight(20);
        setArcWidth(10);
        setArcHeight(10);
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

    public boolean pallP6rkab(Circle pall){         //palli ja klotsi puude
        if (pall.intersects(getLayoutBounds())){
            return true;
        }
        return false;
    }

    public double getKlotsilaius(){
        return getWidth();
    }

    public double getKlotsX(){
        return getX();
    }
}