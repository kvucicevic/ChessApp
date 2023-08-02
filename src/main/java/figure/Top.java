package figure;

import ChessGUI.Point;

import java.util.ArrayList;

public class Top extends Figura{

    final static String NAZIV_SLIKE = "Top";

    public Top(Boja boja, int x, int y)
    {
        super(NAZIV_SLIKE, boja, x, y);
    }

    @Override
    public ArrayList<Point> getMoguciPotezi() {

        ArrayList<Point> potezi = new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                potezi.add(new Point(pozicija.x, j));
                potezi.add(new Point(i, pozicija.y));
            }
        }
        return potezi;
    }

    @Override
    public ArrayList<Point> getMogucaPoljaZaJedenje() {
        return getMoguciPotezi();
    }

}
