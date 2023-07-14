package application;

import figure.*;
import figure.Figura.Boja;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Tabla extends GridPane {

    public Figura oznacenaFig;
    ///private int velicinaTable;
    private int velicinaPolja;
    private List<Polje> odigraniPotezi = new ArrayList<>();

    private int flag = 0; // flag = 0 - beli je na potezu

    //private Polje selektovanoPolje = null;

    public Tabla (int velicinaTable)
    {
        super();
        ///this.velicinaTable = velicinaTable;
        velicinaPolja = velicinaTable/8;
    }

    public void nacrtajTablu() {
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                add(new Polje(velicinaPolja, j, i), j,i);

    }

    public void postaviFigure()
    {
        for(int i = 0; i < 8; i++)
            for(int j = 0; j < 8; j++)
                postaviFiguru(j, i);
    }

    private void postaviMouseCallbackZaSvaPolja()
    {
        for(Node polje : getChildren())
        {
            polje.onMouseClickedProperty().set(e -> {
                Polje p = ((Polje)e.getSource());
                poljeKliknuto(p);
            });

        }
    }


    private void osveziBojuPolja()
    {
        for(Node node : getChildren())
            ((Polje)node).vratiBoju();
    }

    private void kliknutaFigura(Figura f)
    {
        //logika za jedenje - drugi klik
        if(oznacenaFig != null) {
            Polje kliknutoPolje = getPoljeIspodFigure(f);

            if((flag == 0 && oznacenaFig.boja != Boja.BELA) || (flag == 1 && oznacenaFig.boja != Boja.CRNA)){
                System.out.println("invalid play queue");
                return;
            }

            if(oznacenaFig.getMogucaPoljaZaJedenje().contains(kliknutoPolje.getPozicija())
                    && (oznacenaFig.boja != f.boja)) { // sacuvaj pojedenu figuru u listu, po zelji
                if(flag == 2) {
                    oznacenaFig = f;
                    obojMogucaPolja(f);
                    return;
                }
                kliknutoPolje.obrisiFiguru();
                pomeriOznacenuFig(kliknutoPolje);
                return;

            }
        }

        //selektuj figuru - prvi klik
        oznacenaFig = f;
        obojMogucaPolja(f);

    }

    public void redosledIgranja() {

        if(odigraniPotezi.isEmpty())
            return;


        System.out.println(oznacenaFig);
        System.out.println();

        if(odigraniPotezi.get(odigraniPotezi.size()-1).getFigura().boja == Boja.CRNA
                && oznacenaFig.boja == Boja.BELA) {
            flag = 0;
            System.out.println("crni je na potezu");
        }else if(odigraniPotezi.get(odigraniPotezi.size()-1).getFigura().boja == Boja.BELA
                && oznacenaFig.boja == Boja.CRNA){
            System.out.println("beli je na potezu");
            flag = 1;

        } else {  /// ovo je slucaj prvog klika, selekta figure
            flag = 2;
        }
    }

    private void poljeKliknuto(Polje polje) { //pomeranje figure

        osveziBojuPolja();

        if(polje.imaFiguru()) {
            redosledIgranja();
            kliknutaFigura(polje.getFigura());
        }
        else {
            if(oznacenaFig == null)
                return;

            redosledIgranja();
            if(oznacenaFig.poljeJeMoguce(polje.getPozicija())) { //dodajemo figuru na polje, pomeramo je
                pomeriOznacenuFig(polje);
            }
            else {
                oznacenaFig = null;
            }

        }
    }

    /*
    private ArrayList<Point> nemogucePreskakanje() {
    	ListIterator<Point> iter = oznacenaFig.getMoguciPotezi().listIterator();
    	ArrayList<Point> updateMoguciPot = new ArrayList<>();
    	while(iter.hasNext()) {
    		Point p1 = iter.next();
    		Polje p2 = getPoljeAt(p1.y, p1.x); //polje koje se nalzai u mogucim poljima figure
    		if(p2.imaFiguru()) {
    			if(p2.getFigura().boja == oznacenaFig.boja) {
    				iter.remove();
    				System.out.println("iterator: " + iter.toString());
    				//break;
    				iter.forEachRemaining(updateMoguciPot :: add);
    				return updateMoguciPot;
    			}

    		}
    		//iter.forEachRemaining(updateMoguciPot :: add);         //ovde ce da brise polje za pesaka
			//return updateMoguciPot;
    	}
    	return oznacenaFig.getMoguciPotezi();
    }
    */

    private void pomeriOznacenuFig(Polje polje) {

        if((flag == 0 && oznacenaFig.boja != Boja.BELA) || (flag == 1 && oznacenaFig.boja != Boja.CRNA) || flag == 2){
            System.out.println("invalid play queue");
            return;
        }


        ///LOGIKA ZA ROKADU - ne radi
        if(oznacenaFig instanceof Kralj) {
            if(oznacenaFig.boja == Boja.BELA && oznacenaFig.pozicija.x == 4
                    && oznacenaFig.pozicija.y == 7) {
                    System.out.println(polje.getPozicija().x);
                    System.out.println(polje.getPozicija().y);
                    if(polje == getPoljeAt(7, 6)) {
                        Figura top = getPoljeAt(7,7).getFigura();
                        pomeriFig(top, getPoljeAt(7, 5));
                    } else if (polje == getPoljeAt(7, 2)){
                        Figura top = getPoljeAt(7,0).getFigura();
                        pomeriFig(top, getPoljeAt(7, 3));
                    }

            } else {
                if(oznacenaFig.pozicija.x == 4
                        && oznacenaFig.pozicija.y == 0) {
                    if(polje == getPoljeAt(0, 6)) {
                        Figura top = getPoljeAt(0,7).getFigura();
                        pomeriFig(top, getPoljeAt(0, 5));
                    } else if (polje == getPoljeAt(0, 2)){
                        Figura top = getPoljeAt(0,0).getFigura();
                        pomeriFig(top, getPoljeAt(0, 3));
                    }
                }
            }

        }

        pomeriFig(oznacenaFig, polje);
        //getPoljeIspodFigure(oznacenaFig).obrisiFiguru();
        //polje.dodajFiguru(oznacenaFig);
        odigraniPotezi.add(polje);

    }

    private void pomeriFig(Figura f, Polje p){
        getPoljeIspodFigure(f).obrisiFiguru();
        p.dodajFiguru(f);
    }

    private Polje getPoljeIspodFigure(Figura f) {
        return getPoljeAt(f.pozicija.y, f.pozicija.x);
    }

    private void obojMogucaPolja(Figura f)
    {
        ///for (Point p : nemogucePreskakanje())
        ///    getPoljeAt(p.y, p.x).obojiPolje(Color.BLUE);

        getPoljeAt(oznacenaFig.pozicija.y, oznacenaFig.pozicija.x).obojiPolje(Color.DARKRED);

        for (Point p : f.getMoguciPotezi()) {
            Polje polje = getPoljeAt(p.y, p.x);
            if(polje != null && polje.imaFiguru() && polje.getFigura().boja != f.boja)
                polje.obojiPolje(Color.BLUE);
        }
    }

    private Polje getPoljeAt(int row, int column)
    {
        for (Node node : getChildren())
            if(getRowIndex(node) == row && getColumnIndex(node) == column)
                return (Polje)node;
        return null;
    }

    private void postaviFiguru(int j, int i)
    {
        if(j == 0 && (i == 0 || i == 7))
            getPoljeAt(j, i).dodajFiguru(new Top(Boja.CRNA, i, j));

        if(j == 0 && (i == 1 || i == 6))
            getPoljeAt(j,i).dodajFiguru(new Skakac(Boja.CRNA, i, j));

        if(j == 0 && (i == 2 || i == 5))
            getPoljeAt(j,i).dodajFiguru(new Lovac(Boja.CRNA, i, j));

        if(j == 0 && i == 3)
            getPoljeAt(j,i).dodajFiguru(new Dama(Boja.CRNA, i, j));

        if(j == 0 && i == 4)
            getPoljeAt(j,i).dodajFiguru(new Kralj(Boja.CRNA, i, j));

        if(j == 7 && (i == 0 || i == 7))
            getPoljeAt(j,i).dodajFiguru(new Top(Boja.BELA, i, j));

        if(j == 7 && (i == 1 || i == 6))
            getPoljeAt(j,i).dodajFiguru(new Skakac(Boja.BELA, i, j));

        if(j == 7 && (i == 2 || i == 5))
            getPoljeAt(j,i).dodajFiguru(new Lovac(Boja.BELA, i, j));

        if(j == 7 && i == 3)
            getPoljeAt(j,i).dodajFiguru(new Dama(Boja.BELA, i, j));

        if(j == 7 && i == 4)
            getPoljeAt(j,i).dodajFiguru(new Kralj(Boja.BELA, i, j));

        if(j == 1)
            getPoljeAt(j,i).dodajFiguru(new Pesak(Boja.CRNA, i, j));

        if(j == 6)
            getPoljeAt(j,i).dodajFiguru(new Pesak(Boja.BELA, i, j));

        postaviMouseCallbackZaSvaPolja();
    }
}

// kad se klikne na odredjenu figuru, poziva se metoda obojMogucaPolja za trenutnaFig


	/*
	private boolean poljeJeSelektovano(Polje polje) {  // vraca true ako je polje na kojem je figura kliknuto
		if(poljeKliknuto(polje) == true) {
			return true;
		}
		return false;
	}
	*/
