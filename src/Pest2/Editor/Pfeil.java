package editor;

import java.awt.*;

class Pfeil
{
    private int x1,y1,x2,y2;
    static double alpha = 30,alpha1;
    int b = 10;
    protected Pfeil(Graphics g,int px1,int py1,int px2,int py2)
    {
        x1 = px1;y1 = py1; x2=px2;y2 = py2;
        PfeilSpitze(g);
    }
    private double betrag(double d)
    {
        if (d < 0) return -d;
        return d;
    }
    private double toDeg(double d)
    {
        return ((d/Math.PI)*180);
    }
    private double toRad(double d)
    {
        return ((d/180.0)*Math.PI);
    }
    private int PfeilDir(int x_1,int y_1,int x_2,int y_2)
    {
        if ((x1<=x2) && (y1<=y2)) return 0;
        else
        if ((x1>=x2) && (y1<=y2)) return 1;
        else
        if ((x1>=x2) && (y1>=y2)) return 2;
        else return 3;
    }
    private void PfeilSpitze(Graphics g) //links-oben-> rechts-unten(über y-achse)
    {
        int p_x=0,p_y=0;
        double m = (double)(betrag(y2-y1))/(double)(betrag(x2-x1));
        alpha1 = toDeg(Math.atan((m)));
        int d_x = (int)(b*Math.cos(toRad(alpha1))); //delta x
        int d_y = (int)(b*Math.sin(toRad(alpha1))); //delta y
        switch (PfeilDir(x1,y1,x2,y2))
        {
            case 0:{p_x = x2 - d_x;
                    p_y = y2 - d_y;break;} // am schnitt(Drehpunkt)
            case 1:{p_x = x2 + d_x;
                    p_y = y2 - d_y;break;}
            case 2:{p_x = x2 + d_x;
                    p_y = y2 + d_y;break;}
            case 3:{p_x = x2 - d_x;
                    p_y = y2 + d_y;}
        }
        int x_1 =(int)((p_x - x2)*Math.cos(toRad(alpha)) - (p_y-y2) * Math.sin(toRad(alpha)) +x2);
	if (x_1 < 0) x_1 = x1;
        int y_1 =(int)((p_x - x2)*Math.sin(toRad(alpha)) + (p_y-y2) * Math.cos(toRad(alpha)) +y2);
	if (y_1 < 0) y_1 = y1;
        g.drawLine(x2,y2,x_1,y_1);
        int x_2 =(int)((p_x - x2)*Math.cos(toRad(-alpha)) - (p_y-y2) * Math.sin(toRad(-alpha)) +x2);
        int y_2 =(int)((p_x - x2)*Math.sin(toRad(-alpha)) + (p_y-y2) * Math.cos(toRad(-alpha)) +y2);
	if (x_2 < 0) x_2 = x2;
	if (y_2 < 0) y_2 = y2;
        g.drawLine(x2,y2,x_2,y_2);
        g.drawLine(x_1,y_1,x_2,y_2);
    }
}

