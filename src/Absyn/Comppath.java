package Absyn;

public class Comppath extends Absyn {
    public Pathop s_pop;
    public Path   s_spath;
    public Comppath(Pathop op, Path p) {
	s_pop = op;
	s_spath = p;
    }
}

