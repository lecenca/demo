package client;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ClientListItem {
    private BooleanProperty send;
    private StringProperty IPPort;

    public ClientListItem(boolean send, String IPPort){
        this.send = new SimpleBooleanProperty(send);
        this.IPPort = new SimpleStringProperty(IPPort);
    }

    public BooleanProperty sendProperty(){
        return send;
    }

    public StringProperty IPPortProperty(){
        return IPPort;
    }
}
