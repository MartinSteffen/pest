package Absyn;

public class PathList {
    public Path head;
    public PathList tail;
    public PathList (Path  h, PathList tl) {
	head = h;
	tail = tl;
    };
};
