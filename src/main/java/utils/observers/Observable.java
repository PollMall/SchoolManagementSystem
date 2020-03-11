package utils.observers;

import utils.events.Event;

public interface Observable<E extends Event> {
    public void addObserver(Observer<E> event);
    public void removeObserver(Observer<E> event);
    public void notifyAll(E e);
}
