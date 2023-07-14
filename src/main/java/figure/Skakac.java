package figure;

import application.Point;

import java.util.ArrayList;

public class Skakac extends Figura{

    final static String NAZIV_SLIKE = "Skakac";

    public Skakac(Boja boja, int x, int y) {
        super(NAZIV_SLIKE, boja, x, y);
    }

    @Override
    public ArrayList<Point> getMoguciPotezi() {
        ArrayList<Point> potezi = new ArrayList<>();


        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(pozicija.x == i-2 && ((pozicija.y == j+1) || (pozicija.y == j-1)))
                    potezi.add(new Point(i, j));
                if(pozicija.x == i+2 && ((pozicija.y == j+1) || (pozicija.y == j-1)))
                    potezi.add(new Point(i, j));
                if(pozicija.x == i-1 && ((pozicija.y == j+2) || (pozicija.y == j-2)))
                    potezi.add(new Point(i, j));
                if(pozicija.x == i+1 && ((pozicija.y == j+2) || (pozicija.y == j-2)))
                    potezi.add(new Point(i, j));
            }
        }

        return potezi;
    }

    @Override
    public ArrayList<Point> getMogucaPoljaZaJedenje() {
        return getMoguciPotezi();
    }

}
