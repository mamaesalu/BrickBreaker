import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;


public class Tellised extends ArrayList {
    private int tellisteVahe = 8;
    private int tellisteSuurusY = 25;
    private static int tellisteArv = 3;
    private static int ridadeArv = 2;

    public Tellised(double platsiLaius) {
        double tellisteSuurusX = ((platsiLaius - ((tellisteArv + 1) * tellisteVahe)) / tellisteArv);
        for (int i = 0; i < ridadeArv; i++) {
            for (int j = 0; j < tellisteArv; j++) {
                Rectangle tellis = new Rectangle(tellisteSuurusX, tellisteSuurusY);
                double tellisX = tellisteVahe + j * (tellisteVahe + tellisteSuurusX);
                double tellisY = tellisteVahe + i * (tellisteVahe + tellisteSuurusY);
                tellis.setX(tellisX);
                tellis.setY(tellisY);
                tellis.setFill(Color.DARKOLIVEGREEN);
                add(tellis);
            }
        }
    }

    public Rectangle kontrolliTellised(Circle pall) {
        Iterator<Rectangle> tellisteIter = iterator();
        while (tellisteIter.hasNext()) {
            Rectangle seetellis = tellisteIter.next();
            if (pall.intersects(seetellis.getLayoutBounds())) {
                tellisteIter.remove();
                return seetellis;
            }
        }
        return null;
    }

    public void nextLevel(){
        tellisteArv = tellisteArv + 2; //lisa telliseid ritta +2
        ridadeArv++;//ridade arv +1
    }

    public void firstLevel(){
        tellisteArv = 3;
        ridadeArv =2;
    }
}

