mk_system(mk_ha({"Display_Nothing","Display_last_disp","Laser_hold","Time_4"
                ,"Get_nr","Get_time_left","Get_time","Laser_normal"
                ,"Laser_reverse","Laser_forward","Goto_nr_start","Goto_prev"
                ,"Dec_display","Inc_display","Goto_next","Goto_4","Goto_5"
                ,"Goto_6","Goto_7","Goto_8","Goto_9","Display__0","Goto_39"
                ,"Goto_38","Goto_37","Goto_36","Goto_35","Goto_34","Goto_3"
                ,"Display_49","Display_48","Display_47","Display_46"
                ,"Display_45","Display_44","Display_43","Display_42"
                ,"Display_41","Display_40","Display_39","Display_38"
                ,"Display_37","Display_36","Display_35","Display_34","Goto_20"
                ,"Goto_33","Goto_32","Goto_31","Goto_30","Goto_29","Goto_28"
                ,"Goto_27","Goto_26","Goto_25","Goto_24","Goto_23","Goto_22"
                ,"Goto_21","Goto_2","Display__9","Display__8","Display__7"
                ,"Display__6","Display__5","Display__4","Display__3"
                ,"Display_2","Display__2","Display_33","Display_32"
                ,"Display_31","Display_30","Display_29","Display_28"
                ,"Display_27","Display_26","Display_25","Display_24"
                ,"Display_23","Display_22","Display_21","Display_20"
                ,"Display_9","Display_8","Display_7","Display_6","Display_5"
                ,"Display_4","Display_3","Time_3","Time_2","Time_1800","Time_1"
                ,"Stop_motor","Stop_laser","Start_motor","Start_laser","Prev_P"
                ,"Next_P","Musik_ud_stop","Musik_ud","Laes_streng"
                ,"Goto_Random","Goto_19","Goto_18","Goto_17","Goto_16"
                ,"Goto_15","Goto_14","Goto_13","Goto_12","Goto_11","Goto_10"
                ,"Goto_1","Display__1","Display_UNKNOWN","Display_SIGN"
                ,"Display_ON","Display_OFF","Display_19","Display_18"
                ,"Display_17","Display_16","Display_15","Display_14"
                ,"Display_13","Display_12","Display_11","Display_10"
                ,"Display_1","PAUSE","MUTE","Timeout_4","DISPLAY"
                ,"SPOL_TILBAGE","SPOL_FREM","FREM","TILBAGE","NotOk"
                ,"Timeout_3","Timeout_2","Timeout_1800","Timeout_1"
                ,"Start_musik","Start_info","Slut_info","Random_Done","REPEAT"
                ,"RANDOM","PLAY","P","OFF","Lead_Out","Key_9_Pressed"
                ,"Key_8_Pressed","Key_7_Pressed","Key_6_Pressed"
                ,"Key_5_Pressed","Key_4_Pressed","Key_3_Pressed"
                ,"Key_2_Pressed","Key_1_Pressed","Key_0_Pressed","CD"
                }
               ,{"Main","Forward","Rewind","T_4","K_1","Random_Control","T_3"
                ,"Repeat_Control","T_1","K_2","K_3","K_0","T_1800","T_2","Disp"
                ,"TOP","Reset_T_4","Time4","Time4_reset","Mute_cd","Pause_cd"
                ,"Disp_ready","Cd_nr","Time_total","Time_nr","Spoler_tilbage"
                ,"Spoler_frem","Rewind_ready","Rewind_reset","Forward_ready"
                ,"Forward_reset","Repeat_reset","Random_reset","Reset_T_2"
                ,"Reset_T_1800","Time_1800_Reset","Time2_reset","Time2"
                ,"Time1800","Reset_T_3","Reset_T_1","Reset_K_3","Reset_K_2"
                ,"Reset_K_1","Reset_K_0","Stand_by_Key0","Key_09","Key_08"
                ,"Key_07","Key_06","Key_05","Key_04","Key_03","Key_02","Key_01"
                ,"Key_0","Stand_by_Key3","Key_39","Key_38","Key_37","Key_36"
                ,"Key_35","Key_34","Key_33","Key_32","Key_31","Key_30","Key_3"
                ,"Stand_by_Key2","Key_29","Key_28","Key_27","Key_26","Key_25"
                ,"Key_24","Key_23","Key_22","Key_21","Key_20","Key_2"
                ,"Time_reset","Time3_reset","Time3","Time","Stand_by_Key1"
                ,"Stand_By","Spinning","Repeat_on","Repeat_off","Random_on"
                ,"Random_off","Key_19","Key_18","Key_17","Key_16","Key_15"
                ,"Key_14","Key_13","Key_12","Key_11","Key_10","Key_1","CD_End"
                }
               ,"TOP",{},{}
               ,{"Stand_By","Forward_reset","Rewind_reset","Reset_T_4"
                ,"Reset_K_1","Random_reset","Reset_T_3","Repeat_reset"
                ,"Reset_T_1","Reset_K_2","Reset_K_3","Reset_K_0","Reset_T_1800"
                ,"Reset_T_2","Disp_ready"
                }
               ,["Main"
                  :-> {"Stand_By","Spinning","Spoler_frem","Spoler_tilbage"
                      ,"Pause_cd","Mute_cd","CD_End"}
                ,"Disp" :-> {"Disp_ready","Cd_nr","Time_nr","Time_total"}
                ,"Forward" :-> {"Forward_reset","Forward_ready"}
                ,"K_0"
                  :-> {"Reset_K_0","Stand_by_Key0","Key_0","Key_01","Key_02"
                      ,"Key_03","Key_04","Key_05","Key_06","Key_07","Key_08"
                      ,"Key_09"
                      }
                ,"K_1"
                  :-> {"Stand_by_Key1","Reset_K_1","Key_1","Key_11","Key_10"
                      ,"Key_12","Key_13","Key_14","Key_15","Key_16","Key_17"
                      ,"Key_18","Key_19"
                      }
                ,"K_2"
                  :-> {"Stand_by_Key2","Reset_K_2","Key_2","Key_21","Key_20"
                      ,"Key_22","Key_23","Key_24","Key_25","Key_26","Key_27"
                      ,"Key_28","Key_29"
                      }
                ,"K_3"
                  :-> {"Reset_K_3","Stand_by_Key3","Key_3","Key_31","Key_30"
                      ,"Key_32","Key_33","Key_34","Key_35","Key_36","Key_37"
                      ,"Key_38","Key_39"
                      }
                ,"Random_Control" :-> {"Random_reset","Random_off","Random_on"}
                ,"Repeat_Control" :-> {"Repeat_reset","Repeat_off","Repeat_on"}
                ,"T_1" :-> {"Reset_T_1","Time","Time_reset"}
                ,"T_2" :-> {"Reset_T_2","Time2_reset","Time2"}
                ,"T_3" :-> {"Reset_T_3","Time3_reset","Time3"}
                ,"T_4" :-> {"Reset_T_4","Time4_reset","Time4"}
                ,"Rewind" :-> {"Rewind_reset","Rewind_ready"}
                ,"TOP"
                  :-> {"Disp","T_2","T_1800","K_0","K_3","K_2","T_1"
                      ,"Repeat_Control","T_3","Random_Control","K_1","T_4","Rewind"
                      ,"Forward","Main"
                      }
                ,"T_1800" :-> {"Reset_T_1800","Time_1800_Reset","Time1800"}
                ]
               ,["Time2" :-> BASIC,"Mute_cd" :-> BASIC,"Time3" :-> BASIC
                ,"Time2_reset" :-> BASIC,"Time4" :-> BASIC
                ,"Time3_reset" :-> BASIC,"Disp_ready" :-> BASIC
                ,"Time4_reset" :-> BASIC,"Main" :-> OR,"Spinning" :-> BASIC
                ,"Reset_T_1" :-> BASIC,"Reset_T_2" :-> BASIC
                ,"Reset_T_3" :-> BASIC,"Forward_ready" :-> BASIC
                ,"Reset_T_4" :-> BASIC,"Time_nr" :-> BASIC,"Time" :-> BASIC
                ,"Disp" :-> OR,"Time_reset" :-> BASIC,"Time_total" :-> BASIC
                ,"Stand_By" :-> BASIC,"Forward" :-> OR
                ,"Forward_reset" :-> BASIC,"Time1800" :-> BASIC
                ,"Time_1800_Reset" :-> BASIC,"K_0" :-> OR,"K_1" :-> OR
                ,"K_2" :-> OR,"Random_on" :-> BASIC,"K_3" :-> OR
                ,"Rewind_ready" :-> BASIC,"Repeat_on" :-> BASIC
                ,"Reset_T_1800" :-> BASIC,"Random_Control" :-> OR
                ,"Repeat_Control" :-> OR,"Random_reset" :-> BASIC
                ,"Repeat_reset" :-> BASIC,"Pause_cd" :-> BASIC,"T_1" :-> OR
                ,"T_2" :-> OR,"Cd_nr" :-> BASIC,"T_3" :-> OR,"T_4" :-> OR
                ,"Key_10" :-> BASIC,"Rewind" :-> OR,"Key_01" :-> BASIC
                ,"Key_11" :-> BASIC,"Key_02" :-> BASIC,"Key_20" :-> BASIC
                ,"Key_12" :-> BASIC,"Rewind_reset" :-> BASIC,"Key_03" :-> BASIC
                ,"Key_30" :-> BASIC,"Key_21" :-> BASIC,"Key_13" :-> BASIC
                ,"Spoler_tilbage" :-> BASIC,"Stand_by_Key0" :-> BASIC
                ,"Key_04" :-> BASIC,"Key_31" :-> BASIC,"Key_22" :-> BASIC
                ,"Key_14" :-> BASIC,"Key_05" :-> BASIC,"Key_32" :-> BASIC
                ,"Key_23" :-> BASIC,"Stand_by_Key1" :-> BASIC
                ,"Key_15" :-> BASIC,"Key_06" :-> BASIC,"Key_33" :-> BASIC
                ,"Stand_by_Key2" :-> BASIC,"Key_24" :-> BASIC
                ,"Key_16" :-> BASIC,"Key_07" :-> BASIC
                ,"Stand_by_Key3" :-> BASIC,"Key_34" :-> BASIC
                ,"Key_25" :-> BASIC,"Key_17" :-> BASIC,"Key_08" :-> BASIC
                ,"Key_35" :-> BASIC,"Key_26" :-> BASIC,"Key_18" :-> BASIC
                ,"Key_09" :-> BASIC,"Key_36" :-> BASIC,"Key_27" :-> BASIC
                ,"Key_19" :-> BASIC,"Key_37" :-> BASIC,"Key_28" :-> BASIC
                ,"Key_29" :-> BASIC,"TOP" :-> AND,"Key_38" :-> BASIC
                ,"Key_39" :-> BASIC,"Key_0" :-> BASIC,"Key_1" :-> BASIC
                ,"Key_2" :-> BASIC,"Random_off" :-> BASIC,"Key_3" :-> BASIC
                ,"Repeat_off" :-> BASIC,"Reset_K_0" :-> BASIC,"T_1800" :-> OR
                ,"CD_End" :-> BASIC,"Reset_K_1" :-> BASIC,"Reset_K_2" :-> BASIC
                ,"Spoler_frem" :-> BASIC,"Reset_K_3" :-> BASIC
                ]
               ,["Mute_cd"
                  :-> {mk_tr("Spinning",{},{},mk_basicexpr("MUTE")
                            ,mk_emptycond(""),mk_egen("Musik_ud"))}
                ,"Time2"
                  :-> {mk_tr("Time2_reset",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Time3"
                  :-> {mk_tr("Time3_reset",{},{},mk_basicexpr("Timeout_3")
                            ,mk_emptycond(""),mk_egen("Display_last_disp"))}
                ,"Time4"
                  :-> {mk_tr("Time4_reset",{},{},mk_basicexpr("Timeout_4")
                            ,mk_emptycond(""),mk_egen("Display_last_disp"))}
                ,"Time2_reset"
                  :-> {mk_tr("Time2",{},{}
                            ,mk_compe(mk_compexpr(OROP
                                                 ,mk_basicexpr("Key_0_Pressed")
                                                 ,mk_compe(mk_compexpr(OROP
                                                                      ,mk_basicexpr("Key_1_Pressed")
                                                                      ,mk_compe(mk_compexpr(OROP
                                                                                           ,mk_basicexpr("Key_2_Pressed")
                                                                                           ,mk_compe(mk_compexpr(OROP
                                                                                                                ,mk_basicexpr("Key_3_Pressed")
                                                                                                                ,mk_compe(mk_compexpr(OROP
                                                                                                                                     ,mk_basicexpr("Key_4_Pressed")
                                                                                                                                     ,mk_compe(mk_compexpr(OROP
                                                                                                                                                          ,mk_basicexpr("Key_5_Pressed")
                                                                                                                                                          ,mk_compe(mk_compexpr(OROP
                                                                                                                                                                               ,mk_basicexpr("Key_7_Pressed")
                                                                                                                                                                               ,mk_compe(mk_compexpr(OROP
                                                                                                                                                                                                    ,mk_basicexpr("Key_6_Pressed")
                                                                                                                                                                                                    ,mk_compe(mk_compexpr(OROP
                                                                                                                                                                                                                         ,mk_basicexpr("Key_8_Pressed")
                                                                                                                                                                                                                         ,mk_basicexpr("Key_9_Pressed")
                                     )           )        )           )        )           )        )           )        )           )        )           )        )           )        )           )        )           )
                            ,mk_emptycond(""),mk_egen("Time_2")
                            )
                      }
                ,"Time3_reset"
                  :-> {mk_tr("Time3",{},{}
                            ,mk_compe(mk_compexpr(OROP
                                                 ,mk_basicexpr("RANDOM")
                                                 ,mk_basicexpr("REPEAT")
                                     )           )
                            ,mk_emptycond(""),mk_egen("Time_3")
                            )
                      }
                ,"Disp_ready"
                  :-> {mk_tr("Cd_nr",{},{},mk_basicexpr("CD"),mk_emptycond("")
                            ,mk_egen("Get_nr"))}
                ,"Time4_reset"
                  :-> {mk_tr("Time4",{},{},mk_basicexpr("NotOk")
                            ,mk_emptycond(""),mk_egen("Time_4"))}
                ,"Spinning"
                  :-> {mk_tr("Spoler_frem",{},{},mk_basicexpr("SPOL_FREM")
                            ,mk_emptycond(""),mk_egen("Laser_forward"))
                      ,mk_tr("Spoler_tilbage",{},{}
                            ,mk_basicexpr("SPOL_TILBAGE"),mk_emptycond("")
                            ,mk_egen("Laser_reverse")
                            )
                      ,mk_tr("Pause_cd",{},{},mk_basicexpr("PAUSE")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Musik_ud_stop")
                                      ,mk_egen("Laser_hold")>)
                            )
                      ,mk_tr("Mute_cd",{},{},mk_basicexpr("MUTE")
                            ,mk_emptycond(""),mk_egen("Musik_ud_stop"))
                      ,mk_tr("Stand_By",{},{},mk_basicexpr("OFF")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Musik_ud_stop")
                                      ,mk_egen("Stop_motor"),mk_egen("Stop_laser")>)
                            )
                      ,mk_tr("CD_End",{},{},mk_basicexpr("Lead_Out")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_instate("Random_off")
                                                 ,mk_instate("Repeat_off")
                                     )           )
                            ,mk_block(<mk_egen("Display_SIGN")
                                      ,mk_egen("Musik_ud_stop")
                                      ,mk_egen("Stop_laser"),mk_egen("Stop_motor")
                                      ,mk_egen("Time_1800")
                                      >
                            )        )
                      ,mk_tr("CD_End",{},{},mk_basicexpr("Random_Done")
                            ,mk_instate("Repeat_off")
                            ,mk_block(<mk_egen("Time_1800"),mk_egen("Stop_motor")
                                      ,mk_egen("Display_SIGN"),mk_egen("Stop_laser")
                                      ,mk_egen("Musik_ud_stop")
                                      >
                            )        )
                      }
                ,"Reset_T_1"
                  :-> {mk_tr("Time_reset",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Reset_T_2"
                  :-> {mk_tr("Time2_reset",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Reset_T_3"
                  :-> {mk_tr("Time3_reset",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Reset_T_4"
                  :-> {mk_tr("Time4_reset",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Time_nr"
                  :-> {mk_tr("Time_total",{},{},mk_basicexpr("DISPLAY")
                            ,mk_emptycond(""),mk_egen("Get_time_left"))}
                ,"Time"
                  :-> {mk_tr("Time_reset",{},{},mk_basicexpr("Timeout_1")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Time_reset"
                  :-> {mk_tr("Time",{},{},mk_basicexpr("TILBAGE")
                            ,mk_emptycond(""),mk_egen("Time_1"))}
                ,"Time_total"
                  :-> {mk_tr("Cd_nr",{},{},mk_basicexpr("DISPLAY")
                            ,mk_emptycond(""),mk_egen("Get_nr"))}
                ,"Stand_By"
                  :-> {mk_tr("Spinning",{},{},mk_basicexpr("CD")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Start_laser"),mk_egen("Start_motor")>)
                            )
                      }
                ,"Forward_reset"
                  :-> {mk_tr("Forward_ready",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Forward_ready",{},{},mk_basicexpr("NotOk")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Time1800"
                  :-> {mk_tr("Time_1800_Reset",{},{}
                            ,mk_basicexpr("Timeout_1800"),mk_emptycond("")
                            ,mk_block(<>)
                            )
                      }
                ,"Time_1800_Reset"
                  :-> {mk_tr("Time1800",{},{},mk_basicexpr("Lead_Out")
                            ,mk_negcond(mk_instate("Repeat_on"))
                            ,mk_egen("Time_1800")
                            )
                      }
                ,"Random_on"
                  :-> {mk_tr("Random_off",{},{},mk_basicexpr("RANDOM")
                            ,mk_negcond(mk_instate("Time3_reset"))
                            ,mk_egen("Display_OFF")
                            )
                      }
                ,"Repeat_on"
                  :-> {mk_tr("Repeat_off",{},{},mk_basicexpr("REPEAT")
                            ,mk_negcond(mk_instate("Time3_reset"))
                            ,mk_egen("Display_OFF")
                            )
                      }
                ,"Reset_T_1800"
                  :-> {mk_tr("Time_1800_Reset",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Random_reset"
                  :-> {mk_tr("Random_off",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Random_off",{},{},mk_basicexpr("CD")
                            ,mk_instate("Stand_By")
                            ,mk_block(<mk_egen("Start_laser"),mk_egen("Start_motor")>)
                            )
                      }
                ,"Repeat_reset"
                  :-> {mk_tr("Repeat_off",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Repeat_off",{},{},mk_basicexpr("CD")
                            ,mk_instate("Stand_By")
                            ,mk_block(<mk_egen("Start_laser"),mk_egen("Start_motor")>)
                            )
                      }
                ,"Pause_cd"
                  :-> {mk_tr("Spinning",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Laser_normal"),mk_egen("Musik_ud")>)
                            )
                      ,mk_tr("Spinning",{},{},mk_basicexpr("PAUSE")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Laser_normal"),mk_egen("Musik_ud")>)
                            )
                      }
                ,"Cd_nr"
                  :-> {mk_tr("Time_nr",{},{},mk_basicexpr("DISPLAY")
                            ,mk_emptycond(""),mk_egen("Get_time"))}
                ,"Key_10"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_10"),mk_egen("Goto_10")>)
                            )
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_10"),mk_egen("Goto_10")>)
                            )
                      }
                ,"Key_01"
                  :-> {mk_tr("Stand_by_Key0",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_1"),mk_egen("Goto_1")>)
                            )
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_1"),mk_egen("Goto_1")>)
                            )
                      }
                ,"Key_11"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_11"),mk_egen("Goto_11")>)
                            )
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_11"),mk_egen("Goto_11")>)
                            )
                      }
                ,"Key_02"
                  :-> {mk_tr("Stand_by_Key0",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_2"),mk_egen("Goto_2")>)
                            )
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_2"),mk_egen("Goto_2")>)
                            )
                      }
                ,"Key_20"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_20"),mk_egen("Goto_20")>)
                            )
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_20"),mk_egen("Goto_20")>)
                            )
                      }
                ,"Rewind_reset"
                  :-> {mk_tr("Rewind_ready",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Rewind_ready",{},{},mk_basicexpr("NotOk")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Key_03"
                  :-> {mk_tr("Stand_by_Key0",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_3"),mk_egen("Goto_3")>)
                            )
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_3"),mk_egen("Goto_3")>)
                            )
                      }
                ,"Key_30"
                  :-> {mk_tr("Stand_by_Key3",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_30"),mk_egen("Goto_30")>)
                            )
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_30"),mk_egen("Goto_30")>)
                            )
                      }
                ,"Key_21"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_21"),mk_egen("Goto_21")>)
                            )
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_21"),mk_egen("Goto_21")>)
                            )
                      }
                ,"Key_12"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_12"),mk_egen("Goto_12")>)
                            )
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_12"),mk_egen("Goto_12")>)
                            )
                      }
                ,"Spoler_tilbage"
                  :-> {mk_tr("Spinning",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond(""),mk_egen("Laser_normal"))}
                ,"Stand_by_Key0"
                  :-> {mk_tr("Key_0",{},{},mk_basicexpr("Key_0_Pressed")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_negcond(mk_instate("Key_1"))
                                                                      ,mk_negcond(mk_instate("Key_2"))
                                                          )           )
                                                 ,mk_negcond(mk_instate("Key_3"))
                                     )           )
                            ,mk_egen("Display__0")
                            )
                      }
                ,"Key_04"
                  :-> {mk_tr("Stand_by_Key0",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_4"),mk_egen("Goto_4")>)
                            )
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_4"),mk_egen("Goto_4")>)
                            )
                      }
                ,"Key_31"
                  :-> {mk_tr("Stand_by_Key3",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_31"),mk_egen("Goto_31")>)
                            )
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_31"),mk_egen("Goto_31")>)
                            )
                      }
                ,"Key_22"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_22"),mk_egen("Goto_22")>)
                            )
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_22"),mk_egen("Goto_22")>)
                            )
                      }
                ,"Key_13"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_13"),mk_egen("Goto_13")>)
                            )
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_13"),mk_egen("Goto_13")>)
                            )
                      }
                ,"Key_14"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_14"),mk_egen("Goto_14")>)
                            )
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_14"),mk_egen("Goto_14")>)
                            )
                      }
                ,"Key_05"
                  :-> {mk_tr("Stand_by_Key0",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_5"),mk_egen("Goto_5")>)
                            )
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_5"),mk_egen("Goto_5")>)
                            )
                      }
                ,"Key_32"
                  :-> {mk_tr("Stand_by_Key3",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_32"),mk_egen("Goto_32")>)
                            )
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_32"),mk_egen("Goto_32")>)
                            )
                      }
                ,"Key_23"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_23"),mk_egen("Goto_23")>)
                            )
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_23"),mk_egen("Goto_23")>)
                            )
                      }
                ,"Stand_by_Key1"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("NotOk")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Key_1",{},{},mk_basicexpr("Key_1_Pressed")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_negcond(mk_instate("Key_0"))
                                                                      ,mk_negcond(mk_instate("Key_2"))
                                                          )           )
                                                 ,mk_negcond(mk_instate("Key_3"))
                                     )           )
                            ,mk_egen("Display__1")
                            )
                      }
                ,"Key_15"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_15"),mk_egen("Goto_15")>)
                            )
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_15"),mk_egen("Goto_15")>)
                            )
                      }
                ,"Key_06"
                  :-> {mk_tr("Stand_by_Key0",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_6"),mk_egen("Goto_6")>)
                            )
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_6"),mk_egen("Goto_6")>)
                            )
                      }
                ,"Key_33"
                  :-> {mk_tr("Stand_by_Key3",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_33"),mk_egen("Goto_33")>)
                            )
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_33"),mk_egen("Goto_33")>)
                            )
                      }
                ,"Stand_by_Key2"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("NotOk")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Key_2",{},{},mk_basicexpr("Key_2_Pressed")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_negcond(mk_instate("Key_0"))
                                                                      ,mk_negcond(mk_instate("Key_1"))
                                                          )           )
                                                 ,mk_negcond(mk_instate("Key_3"))
                                     )           )
                            ,mk_egen("Display__2")
                            )
                      }
                ,"Key_24"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_24"),mk_egen("Goto_24")>)
                            )
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_24"),mk_egen("Goto_24")>)
                            )
                      }
                ,"Key_16"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_16"),mk_egen("Goto_16")>)
                            )
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_16"),mk_egen("Goto_16")>)
                            )
                      }
                ,"Key_07"
                  :-> {mk_tr("Stand_by_Key0",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_7"),mk_egen("Goto_7")>)
                            )
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_7"),mk_egen("Goto_7")>)
                            )
                      }
                ,"Stand_by_Key3"
                  :-> {mk_tr("Key_3",{},{},mk_basicexpr("Key_3_Pressed")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_negcond(mk_instate("Key_0"))
                                                                      ,mk_negcond(mk_instate("Key_1"))
                                                          )           )
                                                 ,mk_negcond(mk_instate("Key_2"))
                                     )           )
                            ,mk_egen("Display__3")
                            )
                      }
                ,"Key_34"
                  :-> {mk_tr("Stand_by_Key3",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_34"),mk_egen("Goto_34")>)
                            )
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_34"),mk_egen("Goto_34")>)
                            )
                      }
                ,"Key_25"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_25"),mk_egen("Goto_25")>)
                            )
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_25"),mk_egen("Goto_25")>)
                            )
                      }
                ,"Key_17"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_17"),mk_egen("Goto_17")>)
                            )
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_17"),mk_egen("Goto_17")>)
                            )
                      }
                ,"Key_08"
                  :-> {mk_tr("Stand_by_Key0",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_8"),mk_egen("Goto_8")>)
                            )
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_8"),mk_egen("Goto_8")>)
                            )
                      }
                ,"Key_35"
                  :-> {mk_tr("Stand_by_Key3",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_35"),mk_egen("Goto_35")>)
                            )
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_35"),mk_egen("Goto_35")>)
                            )
                      }
                ,"Key_26"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_26"),mk_egen("Goto_26")>)
                            )
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_26"),mk_egen("Goto_26")>)
                            )
                      }
                ,"Key_18"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_18"),mk_egen("Goto_18")>)
                            )
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_18"),mk_egen("Goto_18")>)
                            )
                      }
                ,"Key_09"
                  :-> {mk_tr("Stand_by_Key0",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_9"),mk_egen("Goto_9")>)
                            )
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_9"),mk_egen("Goto_9")>)
                            )
                      }
                ,"Key_36"
                  :-> {mk_tr("Stand_by_Key3",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_36"),mk_egen("Goto_36")>)
                            )
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_36"),mk_egen("Goto_36")>)
                            )
                      }
                ,"Key_27"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_27"),mk_egen("Goto_27")>)
                            )
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_27"),mk_egen("Goto_27")>)
                            )
                      }
                ,"Key_19"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_19"),mk_egen("Goto_19")>)
                            )
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_19"),mk_egen("Goto_19")>)
                            )
                      }
                ,"Key_37"
                  :-> {mk_tr("Stand_by_Key3",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_37"),mk_egen("Goto_37")>)
                            )
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_37"),mk_egen("Goto_37")>)
                            )
                      }
                ,"Key_28"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_28"),mk_egen("Goto_28")>)
                            )
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_28"),mk_egen("Goto_28")>)
                            )
                      }
                ,"Key_29"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_29"),mk_egen("Goto_29")>)
                            )
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_29"),mk_egen("Goto_29")>)
                            )
                      }
                ,"Key_38"
                  :-> {mk_tr("Stand_by_Key3",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_38"),mk_egen("Goto_38")>)
                            )
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_38"),mk_egen("Goto_38")>)
                            )
                      }
                ,"Key_39"
                  :-> {mk_tr("Stand_by_Key3",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_39"),mk_egen("Goto_39")>)
                            )
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_39"),mk_egen("Goto_39")>)
                            )
                      }
                ,"Key_0"
                  :-> {mk_tr("Key_01",{},{},mk_basicexpr("Key_1_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_1"))
                      ,mk_tr("Stand_by_Key0",{},{}
                            ,mk_basicexpr("Key_0_Pressed")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_negcond(mk_instate("Key_1"))
                                                                      ,mk_negcond(mk_instate("Key_2"))
                                                          )           )
                                                 ,mk_negcond(mk_instate("Key_3"))
                                     )           )
                            ,mk_egen("Display_UNKNOWN")
                            )
                      ,mk_tr("Key_02",{},{},mk_basicexpr("Key_2_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_2"))
                      ,mk_tr("Key_03",{},{},mk_basicexpr("Key_3_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_3"))
                      ,mk_tr("Key_04",{},{},mk_basicexpr("Key_4_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_4"))
                      ,mk_tr("Key_05",{},{},mk_basicexpr("Key_5_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_5"))
                      ,mk_tr("Key_06",{},{},mk_basicexpr("Key_6_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_6"))
                      ,mk_tr("Key_07",{},{},mk_basicexpr("Key_7_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_7"))
                      ,mk_tr("Key_08",{},{},mk_basicexpr("Key_8_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_8"))
                      ,mk_tr("Key_09",{},{},mk_basicexpr("Key_9_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_9"))
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond(""),mk_egen("Display_UNKNOWN"))
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond(""),mk_egen("Display_UNKNOWN"))
                      }
                ,"Key_1"
                  :-> {mk_tr("Key_11",{},{},mk_basicexpr("Key_1_Pressed")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_negcond(mk_instate("Key_0"))
                                                                      ,mk_negcond(mk_instate("Key_2"))
                                                          )           )
                                                 ,mk_negcond(mk_instate("Key_3"))
                                     )           )
                            ,mk_egen("Display_11")
                            )
                      ,mk_tr("Key_10",{},{},mk_basicexpr("Key_0_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_10"))
                      ,mk_tr("Key_12",{},{},mk_basicexpr("Key_2_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_12"))
                      ,mk_tr("Key_13",{},{},mk_basicexpr("Key_3_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_13"))
                      ,mk_tr("Key_14",{},{},mk_basicexpr("Key_4_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_14"))
                      ,mk_tr("Key_15",{},{},mk_basicexpr("Key_5_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_15"))
                      ,mk_tr("Key_16",{},{},mk_basicexpr("Key_6_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_16"))
                      ,mk_tr("Key_17",{},{},mk_basicexpr("Key_7_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_17"))
                      ,mk_tr("Key_18",{},{},mk_basicexpr("Key_8_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_18"))
                      ,mk_tr("Key_19",{},{},mk_basicexpr("Key_9_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_19"))
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_1"),mk_egen("Goto_1")>)
                            )
                      ,mk_tr("Stand_by_Key1",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_1"),mk_egen("Goto_1")>)
                            )
                      }
                ,"Key_2"
                  :-> {mk_tr("Key_21",{},{},mk_basicexpr("Key_1_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_21"))
                      ,mk_tr("Key_20",{},{},mk_basicexpr("Key_0_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_20"))
                      ,mk_tr("Key_22",{},{},mk_basicexpr("Key_2_Pressed")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_negcond(mk_instate("Key_0"))
                                                                      ,mk_negcond(mk_instate("Key_1"))
                                                          )           )
                                                 ,mk_negcond(mk_instate("Key_3"))
                                     )           )
                            ,mk_egen("Display_22")
                            )
                      ,mk_tr("Key_23",{},{},mk_basicexpr("Key_3_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_23"))
                      ,mk_tr("Key_24",{},{},mk_basicexpr("Key_4_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_24"))
                      ,mk_tr("Key_25",{},{},mk_basicexpr("Key_5_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_25"))
                      ,mk_tr("Key_26",{},{},mk_basicexpr("Key_6_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_26"))
                      ,mk_tr("Key_27",{},{},mk_basicexpr("Key_7_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_27"))
                      ,mk_tr("Key_28",{},{},mk_basicexpr("Key_8_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_28"))
                      ,mk_tr("Key_29",{},{},mk_basicexpr("Key_9_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_29"))
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_2"),mk_egen("Goto_2")>)
                            )
                      ,mk_tr("Stand_by_Key2",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_2"),mk_egen("Goto_2")>)
                            )
                      }
                ,"Random_off"
                  :-> {mk_tr("Random_on",{},{},mk_basicexpr("RANDOM")
                            ,mk_negcond(mk_instate("Time3_reset"))
                            ,mk_egen("Display_ON")
                            )
                      }
                ,"Key_3"
                  :-> {mk_tr("Key_31",{},{},mk_basicexpr("Key_1_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_31"))
                      ,mk_tr("Key_30",{},{},mk_basicexpr("Key_0_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_30"))
                      ,mk_tr("Key_32",{},{},mk_basicexpr("Key_2_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_32"))
                      ,mk_tr("Key_33",{},{},mk_basicexpr("Key_3_Pressed")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_negcond(mk_instate("Key_0"))
                                                                      ,mk_negcond(mk_instate("Key_1"))
                                                          )           )
                                                 ,mk_negcond(mk_instate("Key_2"))
                                     )           )
                            ,mk_egen("Display_33")
                            )
                      ,mk_tr("Key_34",{},{},mk_basicexpr("Key_4_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_34"))
                      ,mk_tr("Key_35",{},{},mk_basicexpr("Key_5_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_35"))
                      ,mk_tr("Key_36",{},{},mk_basicexpr("Key_6_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_36"))
                      ,mk_tr("Key_37",{},{},mk_basicexpr("Key_7_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_37"))
                      ,mk_tr("Key_38",{},{},mk_basicexpr("Key_8_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_38"))
                      ,mk_tr("Key_39",{},{},mk_basicexpr("Key_9_Pressed")
                            ,mk_emptycond(""),mk_egen("Display_39"))
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("Timeout_2")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_3"),mk_egen("Goto_3")>)
                            )
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Display_3"),mk_egen("Goto_3")>)
                            )
                      }
                ,"Repeat_off"
                  :-> {mk_tr("Repeat_on",{},{},mk_basicexpr("REPEAT")
                            ,mk_negcond(mk_instate("Time3_reset"))
                            ,mk_egen("Display_ON")
                            )
                      }
                ,"Reset_K_0"
                  :-> {mk_tr("Stand_by_Key0",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Stand_by_Key0",{},{},mk_basicexpr("NotOk")
                            ,mk_emptycond(""),mk_egen("Display_UNKNOWN"))
                      }
                ,"CD_End"
                  :-> {mk_tr("Spinning",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Start_motor"),mk_egen("Start_laser")>)
                            )
                      ,mk_tr("Stand_By",{},{},mk_basicexpr("Timeout_1800")
                            ,mk_emptycond(""),mk_egen("Display_Nothing"))
                      }
                ,"Reset_K_1"
                  :-> {mk_tr("Stand_by_Key1",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Spoler_frem"
                  :-> {mk_tr("Spinning",{},{},mk_basicexpr("PLAY")
                            ,mk_emptycond(""),mk_egen("Laser_normal"))}
                ,"Reset_K_2"
                  :-> {mk_tr("Stand_by_Key2",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Reset_K_3"
                  :-> {mk_tr("Stand_by_Key3",{},{},mk_basicexpr("CD")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Stand_by_Key3",{},{},mk_basicexpr("NotOk")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ]
               ,["Main" :-> "Stand_By","Disp" :-> "Disp_ready"
                ,"Forward" :-> "Forward_reset","K_0" :-> "Reset_K_0"
                ,"K_1" :-> "Reset_K_1","K_2" :-> "Reset_K_2"
                ,"K_3" :-> "Reset_K_3","Repeat_Control" :-> "Repeat_reset"
                ,"Random_Control" :-> "Random_reset","T_1" :-> "Reset_T_1"
                ,"T_2" :-> "Reset_T_2","T_3" :-> "Reset_T_3"
                ,"T_4" :-> "Reset_T_4","Rewind" :-> "Rewind_reset"
                ,"T_1800" :-> "Reset_T_1800"
                ]
               ,{},{}
               )
         ,mk_ha({"CD","Key_0_Pressed","Key_1_Pressed","Key_2_Pressed"
                ,"Key_3_Pressed","Key_4_Pressed","Key_5_Pressed"
                ,"Key_6_Pressed","Key_7_Pressed","Key_8_Pressed"
                ,"Key_9_Pressed","Lead_Out","OFF","P","PLAY","RANDOM","REPEAT"
                ,"Random_Done","Slut_info","Start_info","Start_musik"
                ,"Timeout_1","Timeout_1800","Timeout_2","Timeout_3","NotOk"
                ,"TILBAGE","FREM","SPOL_FREM","SPOL_TILBAGE","DISPLAY"
                ,"Timeout_4","MUTE","PAUSE"
                }
               ,{},"",{},{},{},[],[],[],[],{},{}
               )
         ,[]
         )
