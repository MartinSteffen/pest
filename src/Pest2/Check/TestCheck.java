// ****************************************************************************
//   Projekt:               PEST2
//   Klasse:                TestCheck
//   Autor:                 Tobias Kunz, Mario Thies
//
//
//   Letzte Aenderung von:  Tobias Kunz
//                          07.12.1998
//
// ****************************************************************************

import absyn.*;
import check.*;

public class TestCheck {

 public static void main(String args[]) {
  Statechart st = Example.getExample();
  ModelCheck mc = new ModelCheck();
  boolean result;

  result = mc.checkModel(st);
  System.out.println(result);

  result = false;
  }

}
