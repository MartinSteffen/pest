package Absyn;

public class Aseq extends Absyn {
    public Action head;
    public Aseq tail;
    public Aseq (Action h, Aseq tl) {head = h;tail = tl; };
};
