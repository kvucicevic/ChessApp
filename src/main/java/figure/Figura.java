package figure;

import ChessGUI.Point;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Figura extends ImageView {

    public enum Boja
    {
        BELA,
        CRNA,

    }

    public Point pozicija;
    public Boja boja;


    public Figura(String imgName, Boja boja, int x, int y) {
        super(pronadjiPutanjuDoSlike(imgName, boja));
        prefWidth(70);
        prefHeight(70);
        this.boja = boja;
        pozicija = new Point(x, y);
    }

    private static Image pronadjiPutanjuDoSlike(String imgName, Boja boja) {

        String bojastr = "beli";
        if(boja == Boja.CRNA)
            bojastr = "crni";


        String str = "/" + bojastr + imgName + ".png";

        return new Image(Objects.requireNonNull(Figura.class.getResourceAsStream(str)), 70, 70, true, true);
        // /crniTop.png
    }

    public abstract ArrayList<Point> getMoguciPotezi();

    public boolean poljeJeMoguce(Point polje)
    {
        return getMoguciPotezi().contains(polje);
    }

    public abstract ArrayList<Point> getMogucaPoljaZaJedenje();
    //ako je kretanje isato koa jedenje

}
