package gui;
class testClass{

GUIInterface gt;

    protected testClass(GUIInterface gt)
    {
	this.gt = gt;
	gt.OkDialog("Fehler","Noch nicht funktionsf�hig");
	gt.userMessage("XXXXXX: Noch nicht funktionsf�hig"); 
    }
}
