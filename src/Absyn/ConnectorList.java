package Absyn;

public class ConnectorList {
    public Connector head;
    public ConnectorList  tail;
    public ConnectorList(Connector h, ConnectorList tl) {
	head = h;
	tail = tl;
    }
}

