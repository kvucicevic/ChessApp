package observer;

import observer.Subscriber;

public interface Publisher {

    void addSub(Subscriber sub);
    void notifySubs(Object notification);
}
