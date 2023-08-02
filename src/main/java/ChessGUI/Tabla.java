package ChessGUI;

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
    private int flag = 0; // flag = 0 - beli je na potezu
    private int checkMateFlag = 0;
    private int checkFlag = 0;
    private NoJumpImplementation noJump = new NoJumpImplementation();
    private List<Polje> odigraniPotezi = new ArrayList<>();
    private List<Figura> pojedeneFig = new ArrayList<>();
    private List<Subscriber> subs = new ArrayList<>();
    private List<Point> invalidFields = new ArrayList<>();
    private List<Figura> figure = new ArrayList<>();


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

        if(isCheckMate())
            return;


        //logika za jedenje - drugi klik
        if(oznacenaFig != null) {
            Polje kliknutoPolje = getPoljeIspodFigure(f);

            if((flag == 0 && oznacenaFig.boja != Boja.BELA) || (flag == 1 && oznacenaFig.boja != Boja.CRNA)){
                System.out.println("invalid play queue");
                return;
            }

            if(removeFileds(oznacenaFig, oznacenaFig.getMogucaPoljaZaJedenje()).contains(kliknutoPolje.getPozicija())
                    && (oznacenaFig.boja != f.boja)){
                //System.out.println("INVALID FIELDS: " + invalidFields);
                if(invalidFields.contains(kliknutoPolje.getPozicija())){  // msm da mi ovo vise ne treba
                    return;
                }

                if(flag == 2 || f instanceof Kralj) {
                    oznacenaFig = f;
                    obojMogucaPolja(f);
                    return;
                }

                if(isCheck()){
                    if(f instanceof Kralj){

                    }
                }

                Figura fig = kliknutoPolje.getFigura();  // fig je pojedena figura
                pojedeneFig.add(fig);
                figure.remove(fig);
                notifySubs(fig);
                kliknutoPolje.obrisiFiguru();
                pomeriOznacenuFig(kliknutoPolje);
                return;

            }
        }

        if(isCheckMate())
            return;

        //selektuj figuru - prvi klik
        oznacenaFig = f;
        obojMogucaPolja(f);

    }

    public void redosledIgranja()
    {

        if(odigraniPotezi.isEmpty())
            return;

        if(oznacenaFig == null){
            return;
        }

        if(checkMateFlag == 1)
            return;

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

    private void poljeKliknuto(Polje polje)
    { //pomeranje figure

        osveziBojuPolja();

        if(checkMateFlag == 1) {
            System.out.println("CHECKMATE");
            return;
        }

        if(polje.imaFiguru()) {
            redosledIgranja();
            kliknutaFigura(polje.getFigura());
        } else {
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

    /// uslovi kad je kralj matiran
    private boolean kingMated(Kralj kralj)
    {
        if(!isFigAttacked(kralj))
            return false;

        int count = 0;

        for(Point p : removeFileds(kralj, kralj.getMoguciPotezi())){
            Polje polje = getPoljeAt(p.y, p.x);
            if(polje.imaFiguru()){
                if(polje.getFigura().boja == kralj.boja) {
                    count++;
                } else {
                    if(!figuraBranjena(polje.getFigura())){
                        count++;
                    }
                }
            } else {
                if(kralj.boja == Boja.BELA) {
                    if (isAttackedField(polje, Boja.CRNA)) {
                        count++;
                    }
                } else {
                    if (isAttackedField(polje, Boja.BELA)) {
                        count++;
                    }
                }

            }
        }
        System.out.println("count is: " + count);
        System.out.println("size is: " + removeFileds(kralj, kralj.getMoguciPotezi()).size());
        return count == removeFileds(kralj, kralj.getMoguciPotezi()).size();
    }

    /// provera da li je doslo do mata
    public boolean isCheckMate()
    {
        if(odigraniPotezi.size() > 3) {
            for(Figura fig : figure) {
                if (fig instanceof Kralj) {
                    if (kingMated((Kralj) fig)) {
                        checkMateFlag = 1;
                        System.out.println("CHECKMATE");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheck()
    {
        for(Figura kralj : figure){
            if(kralj instanceof Kralj && isFigAttacked(kralj)){
                checkFlag = 1;
                return true;
            }
        }
        checkFlag = 0;
        return false;
    }

    public ArrayList<Point> moguciPoteziSah(Figura attacker){

        ArrayList<Point> moguci = new ArrayList<>();

        for(Point attackerPos : removeFileds(attacker, attacker.getMoguciPotezi())){
            for(Figura defender : figure){
                if(defender.boja == attacker.boja)
                    continue;

                if(defender.getMoguciPotezi().contains(attackerPos)){
                    moguci.add(attackerPos);
                }

            }
        }

        return moguci;
    }
    public boolean isFigAttacked(Figura fig)
    {
        for(Figura attacker : figure){
            ArrayList<Point> remove = removeFileds(attacker, attacker.getMoguciPotezi());
            if(attacker.boja != fig.boja &&
                    remove.contains(getPoljeIspodFigure(fig).getPozicija())) {
                    return true;
            }
        }
        return false;
    }

    public boolean isAttackedField(Polje polje, Boja bojaNapadaca)
    {
        if(polje.imaFiguru()) {
            return true;
        } else {
            for(Figura fig : figure){
                if(fig.boja != bojaNapadaca)
                    continue;
                if(removeFileds(fig, fig.getMogucaPoljaZaJedenje()).contains(polje.getPozicija()) ||
                            removeFileds(fig, fig.getMoguciPotezi()).contains(polje.getPozicija())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean figuraBranjena(Figura fig)
    {
        for(Figura defender : figure){
            if(defender.boja == fig.boja){
                if(removeFileds(defender, defender.getMogucaPoljaZaJedenje()).contains(fig.pozicija)){
                    return true;
                }
            }
        }
        return false;
    }

    private void pomeriOznacenuFig(Polje polje)
    {

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

    private void pomeriFig(Figura f, Polje p)
    {
        getPoljeIspodFigure(f).obrisiFiguru();
        p.dodajFiguru(f);
    }

    private Polje getPoljeIspodFigure(Figura f)
    {
        return getPoljeAt(f.pozicija.y, f.pozicija.x);
    }

    private void obojMogucaPolja(Figura f)
    {

        if(checkMateFlag == 1) {
            System.out.println("CHECKMATE");
            return;
        }

        getPoljeAt(oznacenaFig.pozicija.y, oznacenaFig.pozicija.x).obojiPolje(Color.DARKRED);
        //invalidFields = noJump.noJump(f, this);

        for (Point p : removeFileds(f, f.getMoguciPotezi())) {
            Polje polje = getPoljeAt(p.y, p.x);
            assert polje != null;
            polje.obojiPolje(Color.SKYBLUE);
            if(polje.imaFiguru() && polje.getFigura().boja != f.boja) {
                polje.obojiPolje(Color.BLUE);
            }
        }

        if(f instanceof Pesak){
            for(Point p : f.getMogucaPoljaZaJedenje()){
                Polje polje = getPoljeAt(p.y, p.x);
                if(polje == null)
                    continue;
                if (polje.imaFiguru()) {
                    if(polje.getFigura().boja != f.boja)
                        polje.obojiPolje(Color.BLUE);
                } else {
                    if(odigraniPotezi.isEmpty())
                        return;
                    enPassant((Pesak)f);

                }

            }
        }

        getPoljeAt(oznacenaFig.pozicija.y, oznacenaFig.pozicija.x).obojiPolje(Color.DARKRED);

    }

    public ArrayList<Point> removeFileds(Figura f, ArrayList<Point> list)
    {
        invalidFields = noJump.noJump(f, this);

        ArrayList<Point> cpy = new ArrayList<>(list);
        Iterator<Point> iterator = cpy.iterator();
        while (iterator.hasNext()) {
            Point p = iterator.next();
            if (invalidFields.contains(p)) {
                iterator.remove();
            }
        }
        return cpy;

    }

    public void enPassant(Pesak jede)
    {
        if(odigraniPotezi.isEmpty())
            return;

        Polje polj = odigraniPotezi.get(odigraniPotezi.size()-1);

        if(!(polj.getFigura() instanceof Pesak)){
            return;
        }
        Pesak pojeden = (Pesak)polj.getFigura();

        //System.out.println("polja za jedenje: " + jede.getMogucaPoljaZaJedenje().get(0).y + jede.getMogucaPoljaZaJedenje().get(0).x);
        //System.out.println("polja za jedenje: " + jede.getMogucaPoljaZaJedenje().get(1).y + jede.getMogucaPoljaZaJedenje().get(1).x);
        /// OVO GORE JE KAKO TREBA


        if(pojeden.boja != jede.boja) {
            if(polj.getPozicija().y == 3 && pojeden.boja == Boja.CRNA) {  /// crni igra sad, a beli je dvostruko stao na 4
                if(jede.getMogucaPoljaZaJedenje().size() > 1 && jede.pozicija.y == pojeden.pozicija.y){
                    if(pojeden.pozicija.x == jede.getMogucaPoljaZaJedenje().get(1).x) {
                        getPoljeAt(jede.getMogucaPoljaZaJedenje().get(1).y, jede.getMogucaPoljaZaJedenje().get(1).x).obojiPolje(Color.BLUE);
                    } else {
                        getPoljeAt(jede.getMogucaPoljaZaJedenje().get(0).y, jede.getMogucaPoljaZaJedenje().get(0).x).obojiPolje(Color.BLUE);
                    }
                }

            }else if(polj.getPozicija().x == 4 && pojeden.boja == Boja.BELA){
                if(jede.getMogucaPoljaZaJedenje().size() > 1 && jede.pozicija.y == pojeden.pozicija.y){
                    if(pojeden.pozicija.x == jede.getMogucaPoljaZaJedenje().get(1).x) {
                        getPoljeAt(jede.getMogucaPoljaZaJedenje().get(1).y, jede.getMogucaPoljaZaJedenje().get(1).x).obojiPolje(Color.BLUE);
                    } else {
                        getPoljeAt(jede.getMogucaPoljaZaJedenje().get(0).y, jede.getMogucaPoljaZaJedenje().get(0).x).obojiPolje(Color.BLUE);
                    }
                }

            }

            /// ako je prethodni potez crni pesak na y=3 i pored njega (x+-1) postoji pesak druge boje
            /// ti pesaci mogu da pojedu na (y-1, x+-1)
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
        if(j == 0 && (i == 0 || i == 7)) {
            Top t = new Top(Boja.CRNA, i, j);
            getPoljeAt(j, i).dodajFiguru(t);
            figure.add(t);

        } if(j == 0 && (i == 1 || i == 6)) {
            Skakac s = new Skakac(Boja.CRNA, i, j);
            getPoljeAt(j, i).dodajFiguru(s);
            figure.add(s);

        } if(j == 0 && (i == 2 || i == 5)) {
            Lovac l = new Lovac(Boja.CRNA, i, j);
            getPoljeAt(j, i).dodajFiguru(l);
            figure.add(l);

        } if(j == 0 && i == 3) {
            Dama d = new Dama(Boja.CRNA, i, j);
            getPoljeAt(j,i).dodajFiguru(d);
            figure.add(d);

        } if(j == 0 && i == 4) {
            Kralj k = new Kralj(Boja.CRNA, i, j);
            getPoljeAt(j,i).dodajFiguru(k);
            figure.add(k);

        } if(j == 7 && (i == 0 || i == 7)) {
            Top t = new Top(Boja.BELA, i, j);
            getPoljeAt(j, i).dodajFiguru(t);
            figure.add(t);

        } if(j == 7 && (i == 1 || i == 6)) {
            Skakac s = new Skakac(Boja.BELA, i, j);
            getPoljeAt(j, i).dodajFiguru(s);
            figure.add(s);

        } if(j == 7 && (i == 2 || i == 5)) {
            Lovac l = new Lovac(Boja.BELA, i, j);
            getPoljeAt(j, i).dodajFiguru(l);
            figure.add(l);

        } if(j == 7 && i == 3) {
            Dama d = new Dama(Boja.BELA, i, j);
            getPoljeAt(j,i).dodajFiguru(d);
            figure.add(d);

        } if(j == 7 && i == 4) {
            Kralj k = new Kralj(Boja.BELA, i, j);
            getPoljeAt(j,i).dodajFiguru(k);
            figure.add(k);

        } if(j == 1) {
            Pesak p = new Pesak(Boja.CRNA, i, j);
            getPoljeAt(j,i).dodajFiguru(p);
            figure.add(p);

        } if(j == 6){
            Pesak p = new Pesak(Boja.BELA, i, j);
            getPoljeAt(j,i).dodajFiguru(p);
            figure.add(p);
        }

        postaviMouseCallbackZaSvaPolja();
    }

    @Override
    public void addSub(Subscriber sub)
    {
        subs.add(sub);
    }

    @Override
    public void notifySubs(Object notification)
    {
        for(Subscriber s : subs){
            s.update(notification);
        }
    }
}
