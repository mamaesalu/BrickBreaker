import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pall extends Circle{
    private double r = 20;
    private double x = r+200;
    private double y = r+200;
    private static int dx = 2;
    private static int dy = 2;

    public Pall() {
        setCenterX(x);
        setCenterY(y);
        setRadius(r);
        setFill(Color.DARKBLUE);
        setStroke(Color.CORAL);
    }

    public void liigu(){
        x+=dx;
        setCenterX(x);
        y+=dy;
        setCenterY(y);
    }

    public void muudaYsuunda(){
        dy = dy * -1;
    }

    public void muudaXsuunda(){
        dx = dx * -1;
    }

    public void lisakiirust(){
        dx = Math.abs(dx)+ 1;//lisa palli liikumise kiirust iga leveli vahetusega
        dy = Math.abs(dy)+ 1;
    }

    public double getPallr(){
        return r;
    }

    public double getPallx(){
        return x;
    }

    public double getPally(){ return y; }
}