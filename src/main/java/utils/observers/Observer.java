package utils.observers;

import utils.events.Event;

public interface Observer<E extends Event> {
    public void update(E e);
}
