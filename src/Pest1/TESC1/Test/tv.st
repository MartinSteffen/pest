mk_system(mk_ha({"TV_OFF","TV_ON","TXT","SOUND","MUTE","TWO","ONE"}
               ,{mk_gstate("TV"
                          ,<mk_coord(0,0),mk_coord(0,19200)
                           ,mk_coord(25000,19200),mk_coord(25000,0)>
                          )
                ,mk_gstate("WORKING"
                          ,<mk_coord(452,18336),mk_coord(20022,18336)
                           ,mk_coord(20022,328),mk_coord(452,328)>
                          )
                ,mk_gstate("SOUND_ON"
                          ,<mk_coord(14279,1389),mk_coord(14279,2639)
                           ,mk_coord(12279,2639),mk_coord(12279,1389)>
                          )
                ,mk_gstate("MUTE"
                          ,<mk_coord(5879,1381),mk_coord(7879,1381)
                           ,mk_coord(7879,2631),mk_coord(5879,2631)>
                          )
                ,mk_gstate("WORKING_SOUND"
                          ,<mk_coord(452,4810),mk_coord(20022,4810)
                           ,mk_coord(20022,328),mk_coord(452,328)>
                          )
                ,mk_gstate("VIDEOTEXT"
                          ,<mk_coord(15869,13279),mk_coord(19323,13279)
                           ,mk_coord(19323,8798),mk_coord(15869,8798)>
                          )
                ,mk_gstate("SHOW"
                          ,<mk_coord(1644,16198),mk_coord(11964,16198)
                           ,mk_coord(11964,5426),mk_coord(1644,5426)>
                          )
                ,mk_gstate("REST"
                          ,<mk_coord(9000,13000),mk_coord(11000,13000)
                           ,mk_coord(11000,14500),mk_coord(9000,14500)>
                          )
                ,mk_gstate("SM_CONTROL_SOUND"
                          ,<mk_coord(9000,7000),mk_coord(11000,7000)
                           ,mk_coord(11000,8500),mk_coord(9000,8500)>
                          )
                ,mk_gstate("SM_CONTROL"
                          ,<mk_coord(6824,16198),mk_coord(6824,5426)
                           ,mk_coord(11964,5426),mk_coord(11964,16198)>
                          )
                ,mk_gstate("CH1"
                          ,<mk_coord(5364,13228),mk_coord(5364,14478)
                           ,mk_coord(3364,14478),mk_coord(3364,13228)>
                          )
                ,mk_gstate("CH2"
                          ,<mk_coord(5500,7000),mk_coord(5500,8500)
                           ,mk_coord(3500,8500),mk_coord(3500,7000)>
                          )
                ,mk_gstate("CHANNELS"
                          ,<mk_coord(6824,16198),mk_coord(6824,5426)
                           ,mk_coord(1644,5426),mk_coord(1644,16198)>
                          )
                ,mk_gstate("DISPLAY"
                          ,<mk_coord(452,4810),mk_coord(20022,4810)
                           ,mk_coord(20022,18336),mk_coord(452,18336)>
                          )
                ,mk_gstate("STANDBY"
                          ,<mk_coord(21790,13320),mk_coord(24709,13320)
                           ,mk_coord(24709,8839),mk_coord(21790,8839)>
                          )
                ,mk_gstate("TV_TV"
                          ,<mk_coord(252,18978),mk_coord(24821,18978)
                           ,mk_coord(24821,157),mk_coord(252,157)>
                          )
                }
               ,"TV",{},{},{"TV_TV"}
               ,["SHOW" :-> {"SM_CONTROL","CHANNELS"}
                ,"WORKING_SOUND" :-> {"MUTE","SOUND_ON"}
                ,"CHANNELS" :-> {"CH2","CH1"}
                ,"DISPLAY" :-> {"SHOW","VIDEOTEXT"}
                ,"SM_CONTROL" :-> {"SM_CONTROL_SOUND","REST"}
                ,"WORKING" :-> {"WORKING_SOUND","DISPLAY"},"TV" :-> {"TV_TV"}
                ,"TV_TV" :-> {"STANDBY","WORKING"}
                ]
               ,["SHOW" :-> AND,"SOUND_ON" :-> BASIC
                ,"SM_CONTROL_SOUND" :-> BASIC,"WORKING_SOUND" :-> OR
                ,"CHANNELS" :-> OR,"STANDBY" :-> BASIC,"DISPLAY" :-> OR
                ,"SM_CONTROL" :-> OR,"WORKING" :-> AND,"TV" :-> OR
                ,"TV_TV" :-> OR,"MUTE" :-> BASIC,"CH1" :-> BASIC
                ,"VIDEOTEXT" :-> BASIC,"CH2" :-> BASIC,"REST" :-> BASIC
                ]
               ,["SHOW"
                  :-> {mk_tr("VIDEOTEXT",{},{},mk_basicexpr("TXT")
                            ,mk_emptycond(""),mk_block(<>)
                            ,<mk_coord(11964,12580),mk_coord(15869,12580)>
                            ,<mk_coord(13402,12909)>
                            )
                      }
                ,"SOUND_ON"
                  :-> {mk_tr("MUTE",{},{},mk_basicexpr("MUTE"),mk_emptycond("")
                            ,mk_block(<>)
                            ,<mk_coord(12279,1808),mk_coord(9908,1068)
                             ,mk_coord(7879,1808)>
                            ,<mk_coord(9332,534)>
                            )
                      }
                ,"SM_CONTROL_SOUND"
                  :-> {mk_tr("REST",{},{},mk_basicexpr("MUTE"),mk_emptycond("")
                            ,mk_block(<>)
                            ,<mk_coord(9332,8500),mk_coord(8222,10936)
                             ,mk_coord(9291,13000)>
                            ,<mk_coord(7647,10895)>
                            )
                      }
                ,"STANDBY"
                  :-> {mk_tr("WORKING",{},{},mk_basicexpr("TV_ON")
                            ,mk_emptycond(""),mk_block(<>)
                            ,<mk_coord(21790,9949),mk_coord(20022,9949)>
                            ,<mk_coord(20147,10168)>
                            )
                      }
                ,"WORKING"
                  :-> {mk_tr("STANDBY",{},{},mk_basicexpr("TV_OFF")
                            ,mk_emptycond(""),mk_block(<>)
                            ,<mk_coord(20022,12292),mk_coord(21790,12292)>
                            ,<mk_coord(20084,12473)>
                            )
                      }
                ,"MUTE"
                  :-> {mk_tr("SOUND_ON",{},{},mk_basicexpr("SOUND")
                            ,mk_emptycond(""),mk_block(<>)
                            ,<mk_coord(7879,2302),mk_coord(9702,3001)
                             ,mk_coord(12279,2343)>
                            ,<mk_coord(9221,3189)>
                            )
                      }
                ,"CH1"
                  :-> {mk_tr("CH2",{},{},mk_basicexpr("TWO"),mk_emptycond("")
                            ,mk_block(<mk_egen("MUTE")>)
                            ,<mk_coord(3741,13228),mk_coord(2795,10977)
                             ,mk_coord(3823,8500)>
                            ,<mk_coord(1932,11265)>
                            )
                      ,mk_tr("CH1",{},{},mk_basicexpr("ONE"),mk_emptycond("")
                            ,mk_block(<mk_egen("MUTE")>)
                            ,<mk_coord(3700,14478),mk_coord(3905,15253)
                             ,mk_coord(4769,15294),mk_coord(4974,14478)>
                            ,<mk_coord(3947,14715)>
                            )
                      }
                ,"VIDEOTEXT"
                  :-> {mk_tr("SHOW",{},{},mk_basicexpr("TXT"),mk_emptycond("")
                            ,mk_block(<>)
                            ,<mk_coord(15869,9743),mk_coord(11964,9743)>
                            ,<mk_coord(13402,10072)>
                            )
                      }
                ,"CH2"
                  :-> {mk_tr("CH1",{},{},mk_basicexpr("ONE"),mk_emptycond("")
                            ,mk_block(<mk_egen("MUTE")>)
                            ,<mk_coord(4974,8500),mk_coord(5838,11059)
                             ,mk_coord(4686,13228)>
                            ,<mk_coord(4440,9702)>
                            )
                      ,mk_tr("CH2",{},{},mk_basicexpr("TWO"),mk_emptycond("")
                            ,mk_block(<mk_egen("MUTE")>)
                            ,<mk_coord(3700,7000),mk_coord(3864,6372)
                             ,mk_coord(4933,6372),mk_coord(5221,7000)>
                            ,<mk_coord(3247,5755)>
                            )
                      }
                ,"REST"
                  :-> {mk_tr("SM_CONTROL_SOUND",{},{},mk_emptyexpr("")
                            ,mk_emptycond(""),mk_block(<mk_egen("SOUND")>)
                            ,<mk_coord(10483,13000),mk_coord(10483,8500)>
                            ,<mk_coord(9414,9785)>
                            )
                      }
                ]
               ,["WORKING_SOUND" :-> "MUTE","CHANNELS" :-> "CH1"
                ,"DISPLAY" :-> "SHOW","SM_CONTROL" :-> "REST","TV" :-> "TV_TV"
                ,"TV_TV" :-> "WORKING"
                ]
               ,{},{}
               )
         ,mk_ha({},{},"",{},{},{},[],[],[],[],{},{})
         ,["SM_CONTROL_SOUND"
            :-> {<"STATE#0","TV","WORKING","DISPLAY","SHOW","SM_CONTROL","SOUND">}
          ,"TV_TV" :-> {<"STATE#0","TV">}
          ,"WORKING" :-> {<"STATE#0","TV","WORKING">}
          ,"_CONNECTOR3" :-> {<"STATE#0","TV","WORKING","SOUND","_CONNECTOR3">}
          ,"MUTE" :-> {<"STATE#0","TV","WORKING","SOUND","MUTE">}
          ,"SOUND_ON" :-> {<"STATE#0","TV","WORKING","SOUND","SOUND_ON">}
          ,"VIDEOTEXT" :-> {<"STATE#0","TV","WORKING","DISPLAY","VIDEOTEXT">}
          ,"CHANNELS"
            :-> {<"STATE#0","TV","WORKING","DISPLAY","SHOW","CHANNELS">}
          ,"_CONNECTOR1"
            :-> {<"STATE#0","TV","WORKING","DISPLAY","SHOW","CHANNELS","_CONNECTOR1">}
          ,"CH2"
            :-> {<"STATE#0","TV","WORKING","DISPLAY","SHOW","CHANNELS","CH2">}
          ,"CH1"
            :-> {<"STATE#0","TV","WORKING","DISPLAY","SHOW","CHANNELS","CH1">}
          ,"REST"
            :-> {<"STATE#0","TV","WORKING","DISPLAY","SHOW","SM_CONTROL","REST">}
          ,"_CONNECTOR2"
            :-> {<"STATE#0","TV","WORKING","DISPLAY","SHOW","SM_CONTROL"
                 ,"_CONNECTOR2">}
          ,"SM_CONTROL"
            :-> {<"STATE#0","TV","WORKING","DISPLAY","SHOW","SM_CONTROL">}
          ,"SHOW" :-> {<"STATE#0","TV","WORKING","DISPLAY","SHOW">}
          ,"_CONNECTOR6"
            :-> {<"STATE#0","TV","WORKING","DISPLAY","_CONNECTOR6">}
          ,"DISPLAY" :-> {<"STATE#0","TV","WORKING","DISPLAY">}
          ,"STANDBY" :-> {<"STATE#0","TV","STANDBY">}
          ,"_CONNECTOR5" :-> {<"STATE#0","TV","_CONNECTOR5">}
          ,"TV" :-> {<"STATE#0">}
          ,"WORKING_SOUND" :-> {<"STATE#0","TV","WORKING","SOUND">}
          ]
         )
