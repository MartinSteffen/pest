package Absyn;

public class Bassign extends Absyn {
    public Bvar s_blhs;
    public Guard s_brhs;
    public Bassign (Bvar l, Guard r) {
	s_blhs = l;
	s_brhs = r;
    };
};
    



