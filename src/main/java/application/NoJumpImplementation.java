package application;

import figure.Dama;
import figure.Figura;
import figure.Lovac;
import figure.Top;

import java.util.ArrayList;

public class NoJumpImplementation implements NoJump{


    @Override
    public ArrayList<Point> noJump(Figura oznacenaFig, Tabla t) {

        ArrayList<Point> bezPreskakanja = new ArrayList<>();

        for(Point p : oznacenaFig.getMoguciPotezi()) {
            Polje polje = t.getPoljeAt(p.y, p.x);
            if(polje.imaFiguru()) {

                if (oznacenaFig instanceof Dama) {

                    /** Top logika za damu **/

                    if (p.x == oznacenaFig.pozicija.x && p.y > oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x == p.x && p1.y > p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else if (p.x == oznacenaFig.pozicija.x && p.y < oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x == p.x && p1.y < p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else if (p.x < oznacenaFig.pozicija.x && p.y == oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x < p.x && p1.y == p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x > p.x && p1.y == p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    }


                    /** Lovac logika za damu **/

                    if (p.x > oznacenaFig.pozicija.x && p.y > oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x > p.x && p1.y > p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else if (p.x > oznacenaFig.pozicija.x && p.y < oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x > p.x && p1.y < p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else if (p.x < oznacenaFig.pozicija.x && p.y > oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x < p.x && p1.y > p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else if (p.x < oznacenaFig.pozicija.x && p.y < oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x < p.x && p1.y < p.y) {
                                if (!p1.equals(polje.getPozicija())) {
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }
                    }

                } else if (oznacenaFig instanceof Lovac) {

                    if (p.x > oznacenaFig.pozicija.x && p.y > oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x > p.x && p1.y > p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else if (p.x > oznacenaFig.pozicija.x && p.y < oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x > p.x && p1.y < p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else if (p.x < oznacenaFig.pozicija.x && p.y > oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x < p.x && p1.y > p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else if (p.x < oznacenaFig.pozicija.x && p.y < oznacenaFig.pozicija.y){
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x < p.x && p1.y < p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    }

                } else if (oznacenaFig instanceof Top) {

                    if (p.x == oznacenaFig.pozicija.x && p.y > oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x == p.x && p1.y > p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else if (p.x == oznacenaFig.pozicija.x && p.y < oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x == p.x && p1.y < p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else if (p.x < oznacenaFig.pozicija.x && p.y == oznacenaFig.pozicija.y) {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x < p.x && p1.y == p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    } else {
                        for (Point p1 : oznacenaFig.getMoguciPotezi()) {
                            if (p1.x > p.x && p1.y == p.y) {
                                if (!p1.equals(polje.getPozicija())){
                                    bezPreskakanja.add(p1);
                                }
                            }
                        }

                    }
                }
            }

        }

        return bezPreskakanja;
    }
}
