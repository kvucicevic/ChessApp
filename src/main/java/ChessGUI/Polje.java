package ChessGUI;

import figure.Figura;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Polje extends StackPane {

    private Paint originalnaBoja;
    private Rectangle pozadina;
    private Point pozicija;

    public Polje(float velicina, int x, int y)
    {
        pozicija = new Point(x, y);

        if((y+x)%2 == 1)
            originalnaBoja = Color.GRAY;
        else
            originalnaBoja = Color.WHITE;

        pozadina = new Rectangle(velicina, velicina, originalnaBoja);
        getChildren().add(pozadina);

    }

    public void dodajFiguru(Figura figura)
    {
        if(getChildren().size() == 1) {
            getChildren().add(figura);
            figura.pozicija.x = this.pozicija.x;
            figura.pozicija.y = this.pozicija.y;
        }

    }

    public void obrisiFiguru()
    {
        if(getChildren().size() == 2)
            getChildren().remove(1);
    }

    public Figura getFigura()
    {
        if(getChildren().size() != 2)
            return null;
        return (Figura)getChildren().get(1);
    }

    public boolean imaFiguru()
    {
        return getFigura() != null;
    }

    public void obojiPolje(Paint novaBoja)
    {
        pozadina.setFill(novaBoja);
    }

    public void vratiBoju()
    {
        obojiPolje(originalnaBoja);
    }

    public Point getPozicija() {
        return pozicija;
    }

}
