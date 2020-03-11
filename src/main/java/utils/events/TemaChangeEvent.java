package utils.events;

import domains.Tema;
import utils.ChangeOperation;

public class TemaChangeEvent implements Event {
    private ChangeOperation changeOperation;
    private Tema oldData;
    private Tema changedData;

    //pentru delete si sava
    public TemaChangeEvent(ChangeOperation changeOperation,Tema changedData) {
        this.changeOperation=changeOperation;
        this.changedData=changedData;
    }

    //pentru update
    public TemaChangeEvent(ChangeOperation changeOperation, Tema oldData, Tema changedData) {
        this.changeOperation = changeOperation;
        this.oldData = oldData;
        this.changedData = changedData;
    }

    public ChangeOperation getChangeOperation() {
        return changeOperation;
    }

    public Tema getOldData() {
        return oldData;
    }

    public Tema getChangedData() {
        return changedData;
    }
}
