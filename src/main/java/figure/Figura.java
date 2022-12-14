package figure;

import javax.swing.text.html.ImageView;
import java.awt.*;
import java.util.ArrayList;

public abstract class Figura extends ImageView {

    public enum Boja
    {
        BELA,
        CRNA,
        SIVA,
    }

    protected Image slika;
    public Point pozicija;
    public Boja boja;


    public Figura(String imgName, Boja boja, int x, int y) {
        super(pronadjiPutanjuDoSlike(imgName, boja));
        prefWidth(70);
        prefHeight(70);
        this.boja = boja;
        pozicija = new Point(x, y);
    }

    public Image getSlika() {
        return slika;
    }

    private static String pronadjiPutanjuDoSlike(String imgName, Boja boja)
    {
        String bojastr = "beli";
        if(boja == Boja.CRNA )
            bojastr = "crni";

        return "res/" + bojastr + "/" + imgName + ".png"; // res/crni/skakac.png
    }

    public abstract ArrayList<Point> getMoguciPotezi();


    public boolean poljeJeMoguce(Point polje)
    {
        return getMoguciPotezi().contains(polje);
    }

    public abstract ArrayList<Point> getMogucaPoljaZaJedenje();
    //ako je kretanje isato koa jedenje
}
