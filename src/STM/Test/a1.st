mk_system(mk_ha({"A1LOC_AT_PRESS","A1LOC_AT_SAFE","A1LOC_AT_TAB"}
               ,{mk_gstate("A1"
                          ,<mk_coord(0,0),mk_coord(0,16),mk_coord(24,16)
                           ,mk_coord(24,0)>
                          )
                ,mk_gstate("POS"
                          ,<mk_coord(23,5),mk_coord(0,5),mk_coord(0,12)
                           ,mk_coord(23,12)>
                          )
                ,mk_gstate("B3"
                          ,<mk_coord(12,9),mk_coord(12,9),mk_coord(13,9)
                           ,mk_coord(13,9)>
                          )
                ,mk_gstate("B2"
                          ,<mk_coord(11,9),mk_coord(11,9),mk_coord(11,9)
                           ,mk_coord(11,9)>
                          )
                ,mk_gstate("B1"
                          ,<mk_coord(2,9),mk_coord(2,9),mk_coord(2,10)
                           ,mk_coord(2,10)>
                          )
                ,mk_gstate("B4"
                          ,<mk_coord(20,9),mk_coord(20,9),mk_coord(21,9)
                           ,mk_coord(21,9)>
                          )
                ,mk_gstate("SAFE"
                          ,<mk_coord(19,8),mk_coord(19,8),mk_coord(22,8)
                           ,mk_coord(22,8)>
                          )
                ,mk_gstate("OK4TAB"
                          ,<mk_coord(10,8),mk_coord(10,8),mk_coord(13,8)
                           ,mk_coord(13,8)>
                          )
                ,mk_gstate("OK4PRESS"
                          ,<mk_coord(4,7),mk_coord(1,7),mk_coord(1,8)
                           ,mk_coord(4,8)>
                          )
                ,mk_gstate("CRASH"
                          ,<mk_coord(13,6),mk_coord(10,6),mk_coord(10,7)
                           ,mk_coord(13,7)>
                          )
                ,mk_gstate("BETWEEN2"
                          ,<mk_coord(8,11),mk_coord(8,10),mk_coord(5,10)
                           ,mk_coord(5,11)>
                          )
                ,mk_gstate("BETWEEN1"
                          ,<mk_coord(17,10),mk_coord(14,10),mk_coord(14,11)
                           ,mk_coord(17,11)>
                          )
                ,mk_gstate("MAG"
                          ,<mk_coord(23,5),mk_coord(0,5),mk_coord(0,0)
                           ,mk_coord(23,0)>
                          )
                ,mk_gstate("SWITCH_ON"
                          ,<mk_coord(0,4),mk_coord(4,4),mk_coord(4,3)
                           ,mk_coord(0,3)>
                          )
                ,mk_gstate("SWITCH_OFF"
                          ,<mk_coord(19,3),mk_coord(19,4),mk_coord(22,4)
                           ,mk_coord(22,3)>
                          )
                ,mk_gstate("M"
                          ,<mk_coord(0,15),mk_coord(0,12),mk_coord(23,12)
                           ,mk_coord(23,15)>
                          )
                ,mk_gstate("OFF"
                          ,<mk_coord(13,15),mk_coord(13,14),mk_coord(10,14)
                           ,mk_coord(10,15)>
                          )
                ,mk_gstate("GO_OUT"
                          ,<mk_coord(3,14),mk_coord(3,13),mk_coord(0,13)
                           ,mk_coord(0,14)>
                          )
                ,mk_gstate("GO_IN"
                          ,<mk_coord(21,13),mk_coord(19,13),mk_coord(19,14)
                           ,mk_coord(21,14)>
                          )
                ,mk_gstate("A1_A1"
                          ,<mk_coord(0,15),mk_coord(23,15),mk_coord(23,0)
                           ,mk_coord(0,0)>
                          )
                }
               ,"A1",{},{},{"A1_A1"}
               ,["A1_A1" :-> {"M","MAG","POS"},"M" :-> {"OFF","GO_OUT","GO_IN"}
                ,"MAG" :-> {"SWITCH_ON","SWITCH_OFF"},"A1" :-> {"A1_A1"}
                ,"POS"
                  :-> {"B3","B2","B1","B4","SAFE","OK4TAB","OK4PRESS","CRASH"
                      ,"BETWEEN2","BETWEEN1"}
                ]
               ,["A1_A1" :-> AND,"GO_IN" :-> BASIC,"SWITCH_OFF" :-> BASIC
                ,"M" :-> OR,"SWITCH_ON" :-> BASIC,"MAG" :-> OR,"OFF" :-> BASIC
                ,"OK4PRESS" :-> BASIC,"SAFE" :-> BASIC,"OK4TAB" :-> BASIC
                ,"GO_OUT" :-> BASIC,"CRASH" :-> BASIC,"A1" :-> OR,"POS" :-> OR
                ,"B1" :-> BASIC,"B2" :-> BASIC,"B3" :-> BASIC,"B4" :-> BASIC
                ,"BETWEEN1" :-> BASIC,"BETWEEN2" :-> BASIC
                ]
               ,["GO_IN"
                  :-> {mk_tr("OFF",{},{},mk_basicexpr("A1LOC_AT_SAFE")
                            ,mk_emptycond(""),mk_block(<>)
                            ,<mk_coord(20,14),mk_coord(19,14),mk_coord(18,14)
                             ,mk_coord(13,14)>
                            )
                      }
                ,"SWITCH_OFF"
                  :-> {mk_tr("SWITCH_ON",{},{},mk_emptyexpr("")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_compc(mk_compcond(ANDOP
                                                                                           ,mk_compc(mk_compcond(ANDOP
                                                                                                                ,mk_cinstate(mk_compinstate("ROBOT"
                                                                                                                                           ,<"ROT"
                                                                                                                                            ,"POS"
                                                                                                                                            ,"OK4TAB"
                                                                                                                                            >
                                                                                                                            )              )
                                                                                                                ,mk_cinstate(mk_compinstate("TABLE"
                                                                                                                                           ,<"LOADED">))
                                                                                                    )           )
                                                                                           ,mk_cinstate(mk_compinstate("TABLE"
                                                                                                                      ,<"OK4ROB">))
                                                                               )           )
                                                                      ,mk_cinstate(mk_compinstate("TABLE"
                                                                                                 ,<"TOP">))
                                                          )           )
                                                 ,mk_instate("OK4TAB")
                                     )           )
                            ,mk_block(<mk_egen("A1_CATCH")>)
                            ,<mk_coord(19,4),mk_coord(4,4)>
                            )
                      }
                ,"SWITCH_ON"
                  :-> {mk_tr("SWITCH_OFF",{},{},mk_emptyexpr("")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_cinstate(mk_compinstate("ROBOT"
                                                                            ,<"ROT"
                                                                             ,"POS"
                                                                             ,"OK4PRESSA1"
                                                                             >
                                                             )              )
                                                 ,mk_instate("OK4PRESS")
                                     )           )
                            ,mk_block(<mk_egen("A1_DROP")>)
                            ,<mk_coord(4,3),mk_coord(19,3)>
                            )
                      }
                ,"OFF"
                  :-> {mk_tr("GO_OUT",{},{},mk_emptyexpr("")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_cinstate(mk_compinstate("ROBOT"
                                                                                                 ,<"ROT"
                                                                                                  ,"M"
                                                                                                  ,"OFF"
                                                                                                  >
                                                                                  )              )
                                                                      ,mk_negcond(mk_instate("OK4PRESS"))
                                                          )           )
                                                 ,mk_compc(mk_compcond(OROP
                                                                      ,mk_compc(mk_compcond(ANDOP
                                                                                           ,mk_compc(mk_compcond(ANDOP
                                                                                                                ,mk_instate("SWITCH_OFF")
                                                                                                                ,mk_cinstate(mk_compinstate("ROBOT"
                                                                                                                                           ,<"ROT"
                                                                                                                                            ,"POS"
                                                                                                                                            ,"OK4TAB"
                                                                                                                                            >
                                                                                                    )           )           )              )
                                                                                           ,mk_negcond(mk_instate("OK4TAB"))
                                                                               )           )
                                                                      ,mk_compc(mk_compcond(ANDOP
                                                                                           ,mk_compc(mk_compcond(ANDOP
                                                                                                                ,mk_compc(mk_compcond(ANDOP
                                                                                                                                     ,mk_instate("SWITCH_ON")
                                                                                                                                     ,mk_cinstate(mk_compinstate("ROBOT"
                                                                                                                                                                ,<"ROT"
                                                                                                                                                                 ,"POS"
                                                                                                                                                                 ,"OK4PRESSA1"
                                                                                                                                                                 >
                                                                                                                         )           )           )              )
                                                                                                                ,mk_cinstate(mk_compinstate("PRESS"
                                                                                                                                           ,<"MIDDLE">))
                                                                                                    )           )
                                                                                           ,mk_negcond(mk_instate("OK4PRESS"))
                                     )           )        )           )        )           )
                            ,mk_block(<>)
                            ,<mk_coord(11,14),mk_coord(10,13),mk_coord(10,13)
                             ,mk_coord(3,13)>
                            )
                      ,mk_tr("GO_IN",{},{},mk_emptyexpr("")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_negcond(mk_instate("SAFE"))
                                                 ,mk_negcond(mk_compc(mk_compcond(OROP
                                                                                 ,mk_compc(mk_compcond(ANDOP
                                                                                                      ,mk_instate("SWITCH_OFF")
                                                                                                      ,mk_cinstate(mk_compinstate("ROBOT"
                                                                                                                                 ,<"ROT"
                                                                                                                                  ,"POS"
                                                                                                                                  ,"OK4TAB"
                                                                                                                                  >
                                                                                          )           )           )              )
                                                                                 ,mk_compc(mk_compcond(ANDOP
                                                                                                      ,mk_instate("SWITCH_ON")
                                                                                                      ,mk_cinstate(mk_compinstate("ROBOT"
                                                                                                                                 ,<"ROT"
                                                                                                                                  ,"POS"
                                                                                                                                  ,"OK4PRESSA1"
                                                                                                                                  >
                                     )           )          )        )           )        )           )           )              )
                            ,mk_block(<>)
                            ,<mk_coord(12,14),mk_coord(12,13),mk_coord(14,13)
                             ,mk_coord(19,13)>
                            )
                      }
                ,"OK4PRESS"
                  :-> {mk_tr("CRASH",{},{},mk_emptyexpr("")
                            ,mk_instate("GO_OUT"),mk_block(<>)
                            ,<mk_coord(3,7),mk_coord(10,7)>
                            )
                      ,mk_tr("BETWEEN2",{},{},mk_emptyexpr("")
                            ,mk_instate("GO_IN"),mk_block(<>)
                            ,<mk_coord(3,8),mk_coord(5,11)>
                            )
                      }
                ,"SAFE"
                  :-> {mk_tr("CRASH",{},{},mk_emptyexpr(""),mk_instate("GO_IN")
                            ,mk_block(<>),<mk_coord(19,8),mk_coord(13,7)>)
                      ,mk_tr("BETWEEN1",{},{},mk_emptyexpr("")
                            ,mk_instate("GO_OUT"),mk_block(<>)
                            ,<mk_coord(20,8),mk_coord(17,11)>
                            )
                      }
                ,"OK4TAB"
                  :-> {mk_tr("BETWEEN2",{},{},mk_emptyexpr("")
                            ,mk_instate("GO_OUT"),mk_block(<>)
                            ,<mk_coord(11,8),mk_coord(8,11)>
                            )
                      ,mk_tr("BETWEEN1",{},{},mk_emptyexpr("")
                            ,mk_instate("GO_IN"),mk_block(<>)
                            ,<mk_coord(12,8),mk_coord(14,11)>
                            )
                      }
                ,"GO_OUT"
                  :-> {mk_tr("OFF",{},{},mk_basicexpr("A1LOC_AT_PRESS")
                            ,mk_emptycond(""),mk_block(<>)
                            ,<mk_coord(2,14),mk_coord(3,14),mk_coord(4,14)
                             ,mk_coord(10,14)>
                            )
                      ,mk_tr("OFF",{},{},mk_basicexpr("A1LOC_AT_TAB")
                            ,mk_emptycond(""),mk_block(<>)
                            ,<mk_coord(2,14),mk_coord(2,14),mk_coord(3,14)
                             ,mk_coord(10,15)>
                            )
                      ,mk_tr("OFF",{},{},mk_emptyexpr("")
                            ,mk_negcond(mk_cinstate(mk_compinstate("ROBOT"
                                                                  ,<"ROT","M","OFF">)))
                            ,mk_block(<>)
                            ,<mk_coord(1,14),mk_coord(1,14),mk_coord(3,15)
                             ,mk_coord(10,15)>
                            )
                      }
                ,"B1"
                  :-> {mk_tr("OK4PRESS",{},{},mk_emptyexpr(""),mk_emptycond("")
                            ,mk_block(<>),<mk_coord(2,9),mk_coord(2,8)>)}
                ,"B2"
                  :-> {mk_tr("OK4TAB",{},{},mk_emptyexpr(""),mk_emptycond("")
                            ,mk_block(<>),<mk_coord(11,9),mk_coord(11,8)>)}
                ,"B3"
                  :-> {mk_tr("OK4TAB",{},{},mk_emptyexpr(""),mk_emptycond("")
                            ,mk_block(<>),<mk_coord(13,9),mk_coord(12,8)>)}
                ,"B4"
                  :-> {mk_tr("SAFE",{},{},mk_emptyexpr(""),mk_emptycond("")
                            ,mk_block(<>)
                            ,<mk_coord(21,9),mk_coord(21,8),mk_coord(21,8)>
                            )
                      }
                ,"BETWEEN1"
                  :-> {mk_tr("BETWEEN1",{},{},mk_emptyexpr(""),mk_emptycond("")
                            ,mk_block(<>),<mk_coord(15,11),mk_coord(16,11)>)
                      ,mk_tr("B4",{},{},mk_emptyexpr(""),mk_instate("GO_IN")
                            ,mk_block(<mk_egen("A1LOC_AT_SAFE")>)
                            ,<mk_coord(17,11),mk_coord(21,9)>
                            )
                      ,mk_tr("B3",{},{},mk_emptyexpr(""),mk_instate("GO_OUT")
                            ,mk_block(<mk_egen("A1LOC_AT_TAB")>)
                            ,<mk_coord(14,11),mk_coord(12,9)>
                            )
                      }
                ,"BETWEEN2"
                  :-> {mk_tr("BETWEEN2",{},{},mk_emptyexpr(""),mk_emptycond("")
                            ,mk_block(<>),<mk_coord(6,11),mk_coord(7,11)>)
                      ,mk_tr("B2",{},{},mk_emptyexpr(""),mk_instate("GO_IN")
                            ,mk_block(<mk_egen("A1LOC_AT_TAB")>)
                            ,<mk_coord(8,11),mk_coord(11,9)>
                            )
                      ,mk_tr("B1",{},{},mk_emptyexpr(""),mk_instate("GO_OUT")
                            ,mk_block(<mk_egen("A1LOC_AT_PRESS")>)
                            ,<mk_coord(5,11),mk_coord(2,10)>
                            )
                      }
                ]
               ,["M" :-> "OFF","MAG" :-> "SWITCH_OFF","A1" :-> "A1_A1"
                ,"POS" :-> "BETWEEN1"]
               ,{},{}
               )
         ,mk_ha({},{},"",{},{},{},[],[],[],[],{},{})
         ,["A1_A1" :-> {<"STATE#0","A1">},"MAG" :-> {<"STATE#0","A1","MAG">}
          ,"_CONNECTOR2" :-> {<"STATE#0","A1","MAG","_CONNECTOR2">}
          ,"SWITCH_ON" :-> {<"STATE#0","A1","MAG","SWITCH_ON">}
          ,"SWITCH_OFF" :-> {<"STATE#0","A1","MAG","SWITCH_OFF">}
          ,"B2" :-> {<"STATE#0","A1","POS","B2">}
          ,"B4" :-> {<"STATE#0","A1","POS","B4">}
          ,"OK4TAB" :-> {<"STATE#0","A1","POS","OK4TAB">}
          ,"CRASH" :-> {<"STATE#0","A1","POS","CRASH">}
          ,"BETWEEN1" :-> {<"STATE#0","A1","POS","BETWEEN1">}
          ,"BETWEEN2" :-> {<"STATE#0","A1","POS","BETWEEN2">}
          ,"OK4PRESS" :-> {<"STATE#0","A1","POS","OK4PRESS">}
          ,"SAFE" :-> {<"STATE#0","A1","POS","SAFE">}
          ,"B1" :-> {<"STATE#0","A1","POS","B1">}
          ,"B3" :-> {<"STATE#0","A1","POS","B3">}
          ,"_CONNECTOR1" :-> {<"STATE#0","A1","POS","_CONNECTOR1">}
          ,"POS" :-> {<"STATE#0","A1","POS">}
          ,"GO_OUT" :-> {<"STATE#0","A1","M","GO_OUT">}
          ,"GO_IN" :-> {<"STATE#0","A1","M","GO_IN">}
          ,"OFF" :-> {<"STATE#0","A1","M","OFF">}
          ,"_CONNECTOR0" :-> {<"STATE#0","A1","M","_CONNECTOR0">}
          ,"M" :-> {<"STATE#0","A1","M">},"A1" :-> {<"STATE#0">}
          ]
         )
