package gui;

import java.awt.*;

class ControlWindow
extends Canvas
{
    Rectangle rect[];
    String label[];
    Point rectPos[];
    boolean highLight[];
    int xSkala,ySkala;
    pest parent;
    Font font;


    ControlWindow(pest parent)
    {

	this.parent = parent;
	rect = new Rectangle[15];
	label = new String[15];
	rectPos = new Point[15];
	highLight = new boolean[12]; 

	label[0] = "Neu";
	label[1] = "Oeffnen";
	label[2] = "Statemate";
	label[3] = "TESC";
	label[4] = "SyntaxCheck";
	label[5] = "Editor";
	label[6] = "Codegenerator";
	label[7] = "Speichern";
	label[8] = "TESC";
	label[9] = "Crossreferenz";
	label[10] = "Simulator";
	label[11] = "Statechart";
	label[12] = "Import";
	label[13] = "Export";
	label[14] = "Neue Koordinaten";

			
	for (int i = 0 ; i < 4 ; ++i)
	    {
		highLight[i] = true;
	    }

	for (int i = 4 ; i < 12 ; ++i)
	    {
		highLight[i] = false;
	    }
	highLight[6] = true;

	repaint();
    }

	
    public void paint(Graphics g)
	{
	    setBackground(parent.color[parent.BgColorIndex]);

	    xSkala = getSize().width/12;
	    ySkala = getSize().height/8;

	    rectPos[0] = new Point(xSkala, 3*ySkala);
	    rectPos[1] = new Point(xSkala, 4*ySkala);	
	    rectPos[2] = new Point(3*xSkala, 5*ySkala);
	    rectPos[3] = new Point(3*xSkala, 6*ySkala);		
	    rectPos[4] = new Point(6*xSkala, 1*ySkala);
	    rectPos[5] = new Point(6*xSkala, 2*ySkala);
	    rectPos[6] = new Point(6*xSkala, 3*ySkala);
	    rectPos[7] = new Point(6*xSkala, 4*ySkala);
	    rectPos[8] = new Point(8*xSkala, 6*ySkala);
	    rectPos[9] = new Point((int)(9.5*xSkala),1*ySkala);
	    rectPos[10] = new Point((int)(9.5*xSkala), 2*ySkala);
	    rectPos[11] = new Point(0, 2*ySkala);
	    rectPos[12] = new Point(xSkala, 5*ySkala);	
	    rectPos[13] = new Point(6*xSkala, 5*ySkala);
	    rectPos[14] = new Point(xSkala , ySkala);
	    
	    for (int i = 0; i < 14; ++i)
		{
		    rect[i] = new Rectangle(rectPos[i].x,rectPos[i].y,xSkala*2,ySkala/2);

		}
	    rect[14] = new Rectangle(rectPos[14].x,rectPos[14].y,xSkala*3,ySkala/2);

	    
	    int xinset = xSkala/20;
	    int yinset = rect[0].height/2 + ySkala/20;
	    int letterSize = ((getSize().width/35) & (-4)) + 2;
	    g.setFont(new Font("Serif",Font.BOLD,letterSize));
	    FontMetrics fm = g.getFontMetrics();

	    for (int i = 0; i < 11 ; ++i)
		{
		    if (highLight[i])
			{
			    g.setColor(parent.color[parent.ActColorIndex]);
			}
		    else
			{
			    g.setColor(parent.color[parent.InactColorIndex]);
			}
		    g.drawString(label[i], rectPos[i].x + xSkala/3, rectPos[i].y +ySkala/2 - 2*xinset);
		    //g.drawRect(rectPos[i].x, rectPos[i].y, rect[i].width, rect[i].height);
		}
	    if (highLight[11])
		{
		    g.setColor(parent.color[parent.ActColorIndex]);
		}
	    else 
		{
		    g.setColor(parent.color[parent.InactColorIndex]);
		}
	    g.drawString(label[14], rectPos[14].x + xSkala/3, rectPos[14].y + ySkala/2 - 2*xinset);
	    

	    g.setColor(Color.black);
	    for (int i = 11; i < 14; ++i)
		{
		    g.drawString(label[i], rectPos[i].x + xSkala/3, rectPos[i].y +ySkala/2 - 2*xinset);
		    // g.drawRect(rectPos[i].x, rectPos[i].y, rect[i].width, rect[i].height);
		}


	    
	    g.drawLine(rect[11].x + xinset + xSkala, rect[11].y + 2*ySkala/3 , rect[12].x + xinset ,rect[12].y + yinset);
	    g.drawLine(rect[11].x + xSkala/3 + 4*xinset + fm.stringWidth("Statechart"), rect[11].y + yinset, rect[5].x + xinset, rect[5].y + yinset);
	    g.drawLine(rect[4].x + xinset, rect[4].y + yinset, rect[13].x + xinset, rect[13].y + yinset);
	    g.drawLine(rect[2].x + xinset, rect[2].y + yinset, rect[3].x + xinset, rect[3].y + yinset);
	    g.drawLine(8*xSkala + xinset, 5*ySkala + yinset, rect[8].x + xinset, rect[8].y + yinset);
	    g.drawLine(rect[5].x + 4*xinset + xSkala/3 + fm.stringWidth("Editor"), rect[5].y + yinset, rect[10].x + xinset, rect[10].y + yinset);
	    g.drawLine(rect[12].x +4* xinset +xSkala/3 + fm.stringWidth("Import"), rect[12].y + yinset, rect[2].x + xinset, rect[2].y + yinset);
	    g.drawLine(rect[13].x + 4* xinset + xSkala/3 + fm.stringWidth("Export"), rect[13].y + yinset, 8*xSkala + xinset, 5*ySkala + yinset);
	    g.drawLine(rect[4].x + 4*xinset + xSkala/3 + fm.stringWidth("SyntaxCheck"), rect[4].y + yinset, rect[9].x + xinset, rect[9].y + yinset);
	    g.drawLine(rect[11].x + xinset + xSkala , rect[11].y - ySkala/4 , rect[14].x + xinset , rect[14].y + yinset);
	    
	    for (int i = 0; i < 11; ++i )
		{		   
		    g.fillRect(rect[i].x , rect[i].y + rect[i].height/2 , 2*xinset , 2*xinset);		
		}
	    g.fillRect(rect[14].x , rect[14].y + rect[14].height/2 , 2*xinset , 2*xinset);
	    
	}

}
		    

	
