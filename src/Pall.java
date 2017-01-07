import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Pall extends Circle{
    private double r = 17;  //raadius
    private double x = r+200;
    private double y = r+200;
    private static double dx = 1;   //liikumise kiirus x
    private static double dy = 2.3; //liikumise kiirus y

    public Pall() {
        setCenterX(x);
        setCenterY(y);
        setRadius(r);
        setFill(Color.DARKBLUE);
        setStroke(Color.CORAL);
    }

    public void liigu(){                    //animatsioonis palli liikumine
        x+=dx;
        setCenterX(x);
        y+=dy;
        setCenterY(y);
    }

    public void muudaYsuunda(){
        dy =  dy * -1;
    }

    public void muudaYklotsilt(double klotsX, double klotsiLaius){ //1/3, 2/3 ja 3/3 klotsist annab pallile erineva põrkenurga
        if (x >= klotsX && x < (klotsX + klotsiLaius/3)){
            dy =  dy * -1.03;
            }
        if ((klotsX + klotsiLaius/3) <= x &&  x < (klotsX + 2*klotsiLaius/3)){
            dy =  dy * -1;
            }
        if (x >= (klotsX + 2*klotsiLaius/3)){
            dy =  dy * -0.97;
            }
    }

    public void muudaXsuunda(){
        dx = dx * -1;
    }           //suunamuutus külgseinte puutel

    public void lisakiirust(){
        dx = Math.abs(dx)+ 0.5;                           //lisa palli liikumise kiirust (iga leveli vahetusega)
        dy = Math.abs(dy)+ 1;
    }

    public double getPallr(){
        return r;
    }

    public double getPallx(){
        return x;
    }

    public double getPally(){ return y; }

    public void firstLevel(){                        //staatilise muutuja algväärtuse taastamine mängu taasalustamiseks
        dx = 1;
        dy = 2.3;
    }
}