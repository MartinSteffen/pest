package Absyn;

import java.io.Serializable;

public class TrList implements Serializable { // List of transitions
    public Tr     head;
    public TrList tail;
    public TrList (Tr h, TrList tl) {
	head = h;
	tail = tl;
    };

    public Object clone () throws CloneNotSupportedException {
	TrList tailclone;
	if (tail != null) {tailclone = (TrList)tail.clone();} else {tailclone = null;};

	return new TrList((Tr)head.clone(), (TrList)tailclone);
    }
};

//----------------------------------------------------------------------
//	Abstract Syntax for PEST Statecharts
//	------------------------------------
//
//	$Id: TrList.java,v 1.6 1998-12-15 07:11:11 swtech01 Exp $
//
//	$Log: not supported by cvs2svn $
//	Revision 1.5  1998/12/11 17:43:02  swtech00
//	Cloneable
//
//	Revision 1.4  1998/12/01 09:37:17  swtech00
//	Small error corrected
//
//	Revision 1.3  1998/11/27 10:07:18  swtech20
//	Klasse TrList public und Kontruktor public
//
//	Revision 1.2  1998/11/26 16:32:27  swtech00
//	Id and Log extension
//
//
//----------------------------------------------------------------------
