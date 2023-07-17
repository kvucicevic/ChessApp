package application;

import figure.*;
import figure.Figura.Boja;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import observer.Publisher;
import observer.Subscriber;

import java.util.*;

public class Tabla extends GridPane implements Publisher {

    public Figura oznacenaFig;
    private final int velicinaPolja;
    private List<Polje> odigraniPotezi = new ArrayList<>();
    private List<Figura> pojedeneFig = new ArrayList<>();
    private int flag = 0; // flag = 0 - beli je na potezu
    private List<Subscriber> subs = new ArrayList<>();
    private NoJumpImplementation noJump = new NoJumpImplementation();
    private List<Point> invalidFields = new ArrayList<>();

    public Tabla (int velicinaTable)
    {
        super();
        ///this.velicinaTable = velicinaTable;
        velicinaPolja = velicinaTable/8;
    }

    public void nacrtajTablu()
    {
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
                    && (oznacenaFig.boja != f.boja)){
                System.out.println("INVALID FIELDS: " + invalidFields);
                if(invalidFields.contains(kliknutoPolje.getPozicija())){
                    return;
                }
                if(flag == 2) {
                    oznacenaFig = f;
                    obojMogucaPolja(f);
                    return;
                }
                Figura fig = kliknutoPolje.getFigura();
                pojedeneFig.add(fig);
                //System.out.println(pojedeneFig);
                notifySubs(fig);
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

        if(oznacenaFig == null){
            return;
        }

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
                if(!invalidFields.contains(polje.getPozicija())) {
                    pomeriOznacenuFig(polje);
                }
            }
            else {
                oznacenaFig = null;
            }

        }
    }



    private void pomeriOznacenuFig(Polje polje) {

        if((flag == 0 && oznacenaFig.boja != Boja.BELA) || (flag == 1 && oznacenaFig.boja != Boja.CRNA) || flag == 2){
            System.out.println("invalid play queue");
            return;
        }


        ///LOGIKA ZA ROKADU
        if(oznacenaFig instanceof Kralj) {
            if(oznacenaFig.boja == Boja.BELA && oznacenaFig.pozicija.x == 4
                    && oznacenaFig.pozicija.y == 7) {
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
        invalidFields = noJump.noJump(f, this);
        System.out.println(invalidFields);
        f.getMoguciPotezi().removeAll(invalidFields);
        f.getMogucaPoljaZaJedenje().removeAll(invalidFields);
        System.out.println(f.getMoguciPotezi());

        for (Point p : f.getMoguciPotezi()) {
            if(invalidFields.contains(p)){
                continue;
            }
            Polje polje = getPoljeAt(p.y, p.x);
            assert polje != null;
            polje.obojiPolje(Color.SKYBLUE);
            if(polje.imaFiguru() && polje.getFigura().boja != f.boja) {
                polje.obojiPolje(Color.BLUE);
            }
        }

    }

    public Polje getPoljeAt(int row, int column)
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

    @Override
    public void addSub(Subscriber sub) {
        subs.add(sub);
    }

    @Override
    public void notifySubs(Object notification) {
        for(Subscriber s : subs){
            s.update(notification);
        }
    }
}
