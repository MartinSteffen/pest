package Absyn;

public class Aseq {
    public Action head;
    public Aseq tail;
    public Aseq (Action h, Aseq tl) {head = h;tail = tl; };
};
