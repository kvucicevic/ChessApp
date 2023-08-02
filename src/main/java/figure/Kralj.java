package figure;

import ChessGUI.Point;

import java.util.ArrayList;

public class Kralj extends Figura{

    final static String NAZIV_SLIKE = "Kralj";

    public Kralj(Boja boja, int x, int y) {
        super(NAZIV_SLIKE, boja, x, y);
    }

    @Override
    public ArrayList<Point> getMoguciPotezi() {
        ArrayList<Point> potezi = new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(pozicija.x == i && ((pozicija.y == j+1) || (pozicija.y == j-1)))
                    potezi.add(new Point(i, j));
                if(pozicija.x == i+1 && ((pozicija.y == j+1) || (pozicija.y == j-1) || pozicija.y == j))
                    potezi.add(new Point(i, j));
                if(pozicija.x == i-1 && ((pozicija.y == j+1) || (pozicija.y == j-1) || pozicija.y == j))
                    potezi.add(new Point(i, j));
            }
        }
        if(boja == Boja.CRNA && pozicija.x == 4 && pozicija.y == 0) {
            potezi.add(new Point(6, 0));
            potezi.add(new Point(2, 0));
        }
        if(boja == Boja.BELA && pozicija.x == 4 && pozicija.y == 7) {
            potezi.add(new Point(6, 7));
            potezi.add(new Point(2, 7));
        }


        return potezi;
    }

    @Override
    public ArrayList<Point> getMogucaPoljaZaJedenje() {
        return getMoguciPotezi();
    }


}
