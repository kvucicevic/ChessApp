package application;

public interface Publisher {

    void addSub(Subscriber sub);
    void notifySubs(Object notification);
}
