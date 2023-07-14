package figure;

import application.Point;

import java.util.ArrayList;

public class Dama extends Figura{

    final static String NAZIV_SLIKE = "Dama";

    public Dama(Boja boja, int x, int y)
    {
        super(NAZIV_SLIKE, boja, x, y);
    }

    @Override
    public ArrayList<Point> getMoguciPotezi(){

        ArrayList<Point> potezi = new ArrayList<>();

        int zbir = pozicija.x + pozicija.y;
        int razlika = pozicija.x - pozicija.y;

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                potezi.add(new Point(pozicija.x, j));
                potezi.add(new Point(i, pozicija.y));
                if(zbir == (i+j) || razlika == (j-i))
                    potezi.add(new Point(j, i));
            }
        }
        return potezi;
    }

    @Override
    public ArrayList<Point> getMogucaPoljaZaJedenje() {
        return getMoguciPotezi();
    }

}
