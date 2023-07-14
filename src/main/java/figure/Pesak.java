package figure;

import application.Point;

import java.util.ArrayList;

public class Pesak extends Figura{

    final static String NAZIV_SLIKE = "Pesak";

    public Pesak(Boja boja, int x, int y)
    {
        super(NAZIV_SLIKE, boja, x, y);
        this.boja = boja;
    }

    @Override
    public ArrayList<Point> getMoguciPotezi() {
        ArrayList<Point> potezi = new ArrayList<>();

        if(this.boja == Boja.CRNA)
        {

            potezi.add(new Point(pozicija.x, pozicija.y+1));
            if(pozicija.y == 1)
                potezi.add(new Point(pozicija.x, pozicija.y+2));

            return potezi;
        }
        //BELA

        potezi.add(new Point(pozicija.x, pozicija.y-1));
        if(pozicija.y == 6)
            potezi.add(new Point(pozicija.x, pozicija.y-2));


        return potezi;
    }

    @Override
    public ArrayList<Point> getMogucaPoljaZaJedenje() {
        ArrayList<Point> potezi = new ArrayList<>();

        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(boja == Boja.CRNA) {
                    if(pozicija.y == j-1 && ((pozicija.x == i+1) || (pozicija.x == i-1)))
                        potezi.add(new Point(i, j));
                }else {
                    if(pozicija.y == j+1 && ((pozicija.x == i+1) || (pozicija.x == i-1)))
                        potezi.add(new Point(i, j));
                }
            }
        }

        return potezi;
    }

}
