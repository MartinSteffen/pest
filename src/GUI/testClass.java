package GUI;
class testClass{

GUIInterface gt;

    protected testClass(GUIInterface gt)
    {
	this.gt = gt;
	gt.OkDialog("Fehler","Noch nicht funktionsfähig");
	gt.userMessage("XXXXXX: Noch nicht funktionsfähig"); 
    }
}
