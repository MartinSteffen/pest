mk_system(mk_ha({"Write_hung","Write_hap","Write_disciplin","Write_a_w"
                ,"Switch_beep","Sub_weight","Sub_dic","Sub_app","Snack_icon"
                ,"Slap_icon","Sick_icon","rem_puh","Puh_mark","Play_icon"
                ,"Medicin_icon","Light_on","Light_off","Light_icon"
                ,"Init_count","Health_icon","Feed_icon","Duck_icon","Counter"
                ,"Clr_snack","Clr_sick","Clr_ponish","Clr_play","Clr_mood"
                ,"Clr_meter","Clr_med","Clr_light","Clr_hap","Clr_feed"
                ,"Clr_duck","Clr_displ","Clr_beh","Clr_aw","Clr_app","Bye_beeb"
                ,"Beep","Add_weight","Add_dic","Add_app","Wake_time","Time_out"
                ,"Time_food","Sleepy","Puh_time","jump_l","Jump_r","Dur_time"
                ,"Count_9","Count_8","Count_3","Count_2","Count_11","Count_10"
                ,"Count_1","Count_0","Clean_time","C_pressed","B_pressed"
                ,"A_pressed"
                }
               ,{"Icons","Disciplin","Weight","Happiness","Sleep_func"
                ,"Cleaning","Puh_puh","Hunger","TOP","Weight_age","Wait"
                ,"W_norm","W_dead","Very_sick","Very_hungry","Stuffed_state"
                ,"Stuffed","Snack_state","Snack","Sleep_dem","Slap_wait"
                ,"Skinny","Sick","S_dead","Ready","Punish","Play_wait"
                ,"Play_mode","Play","Pl_wait","Pl_mode","Pizza","P_dead"
                ,"Over_weight","Over_feed","Nothing","No_sleep","med_active"
                ,"More_food","Mood","Medicin","Med_state","Med_care","Light"
                ,"I_dead","Hyper","Hungry","Heavy_weight","Health_meter"
                ,"Happy","Hap_norm","H_dead","Good","Give_snack","Food_state"
                ,"Food_action","Food_act","Food_32","Food_31","Food_22"
                ,"Food_21","Food_2","Food_1","Fit","Feed_state","Feed"
                ,"Duck_32","Duck_31","Duck_22","Duck_21","Duck_12","Duck_11"
                ,"Duck","Du_wait_2","Du_wait_1","Disc_norm","Dis_wait","Digest"
                ,"Died","Dic_wait","Delite","Deadly","Dead","D_wait_2"
                ,"D_wait_1","Clean_need","Choice_2","Choice_1","Ch_2","Ch_1"
                ,"Candy","Behavior","Bastard","Bad","Awake","Asleep","Appetite"
                ,"Angry","Active","Act_snack","Act_sleep","Act_food","Act_duck"
                }
               ,"TOP",{},{}
               ,{"","Disc_norm","W_norm","Hap_norm","Awake","Wait","Nothing"
                ,"Fit"}
               ,["Sleep_func"
                  :-> {"Asleep","Awake","Sleep_dem","Act_sleep","S_dead","No_sleep"}
                ,"Puh_puh" :-> {"Nothing","Digest","Active","P_dead"}
                ,"Disciplin"
                  :-> {"Hyper","Good","Med_state","Died","Disc_norm","Duck_11"
                      ,"Duck_12","Food_1","Food_2","Slap_wait","Bad","Duck_21"
                      ,"Duck_22","Food_21","Food_22","Dis_wait","Bastard","Duck_31"
                      ,"Duck_32","Food_31","Food_32","Dic_wait"
                      }
                ,"Cleaning"
                  :-> {"Wait","Clean_need","Sick","med_active","Act_duck"
                      ,"Very_sick","Med_care","Deadly"}
                ,"Weight"
                  :-> {"W_norm","Over_weight","Heavy_weight","Skinny","Feed_state"
                      ,"Food_state","Snack_state","Candy","Stuffed_state","Pizza"
                      ,"Delite","W_dead","More_food"
                      }
                ,"Hunger"
                  :-> {"Fit","Food_action","Hungry","Stuffed","Food_act","Dead"
                      ,"Over_feed","Very_hungry","Act_food"}
                ,"Happiness"
                  :-> {"Hap_norm","Angry","Du_wait_1","Du_wait_2","Happy","D_wait_1"
                      ,"D_wait_2","Act_snack","Give_snack","H_dead","Play_mode"
                      ,"Pl_wait","Choice_1","Choice_2","Pl_mode","Play_wait","Ch_1"
                      ,"Ch_2"
                      }
                ,"TOP"
                  :-> {"Hunger","Puh_puh","Cleaning","Sleep_func","Happiness"
                      ,"Weight","Disciplin","Icons"}
                ,"Icons"
                  :-> {"Ready","Feed","Light","Duck","Health_meter","Behavior"
                      ,"Mood","Appetite","Weight_age","Punish","Play","Medicin"
                      ,"Snack","I_dead"
                      }
                ]
               ,["Pl_mode" :-> BASIC,"S_dead" :-> BASIC,"Angry" :-> BASIC
                ,"Bastard" :-> BASIC,"Happy" :-> BASIC,"Over_weight" :-> BASIC
                ,"W_dead" :-> BASIC,"Sleep_func" :-> OR,"Act_snack" :-> BASIC
                ,"Duck" :-> BASIC,"Bad" :-> BASIC,"Duck_11" :-> BASIC
                ,"Hyper" :-> BASIC,"Food_state" :-> BASIC,"Duck_12" :-> BASIC
                ,"Good" :-> BASIC,"Duck_21" :-> BASIC,"Duck_22" :-> BASIC
                ,"Sick" :-> BASIC,"Food_21" :-> BASIC,"Duck_31" :-> BASIC
                ,"Duck_32" :-> BASIC,"Food_31" :-> BASIC,"Food_22" :-> BASIC
                ,"Food_32" :-> BASIC,"Pizza" :-> BASIC,"Sleep_dem" :-> BASIC
                ,"Mood" :-> BASIC,"Over_feed" :-> BASIC,"Act_sleep" :-> BASIC
                ,"Pl_wait" :-> BASIC,"Med_care" :-> BASIC
                ,"med_active" :-> BASIC,"Stuffed_state" :-> BASIC
                ,"Stuffed" :-> BASIC,"Health_meter" :-> BASIC
                ,"Deadly" :-> BASIC,"Wait" :-> BASIC,"Med_state" :-> BASIC
                ,"Play" :-> BASIC,"Delite" :-> BASIC,"Nothing" :-> BASIC
                ,"Food_1" :-> BASIC,"Food_2" :-> BASIC,"Puh_puh" :-> OR
                ,"Asleep" :-> BASIC,"Play_mode" :-> BASIC,"More_food" :-> BASIC
                ,"Choice_1" :-> BASIC,"Active" :-> BASIC,"Choice_2" :-> BASIC
                ,"Du_wait_1" :-> BASIC,"Act_duck" :-> BASIC
                ,"Du_wait_2" :-> BASIC,"Disc_norm" :-> BASIC
                ,"Clean_need" :-> BASIC,"Act_food" :-> BASIC,"Disciplin" :-> OR
                ,"Food_act" :-> BASIC,"Digest" :-> BASIC,"Cleaning" :-> OR
                ,"Very_hungry" :-> BASIC,"Fit" :-> BASIC,"Dic_wait" :-> BASIC
                ,"Slap_wait" :-> BASIC,"Heavy_weight" :-> BASIC
                ,"Food_action" :-> BASIC,"D_wait_1" :-> BASIC,"Weight" :-> OR
                ,"Awake" :-> BASIC,"Hunger" :-> OR,"D_wait_2" :-> BASIC
                ,"Play_wait" :-> BASIC,"Happiness" :-> OR,"Dead" :-> BASIC
                ,"Candy" :-> BASIC,"Very_sick" :-> BASIC,"Behavior" :-> BASIC
                ,"Snack_state" :-> BASIC,"Snack" :-> BASIC,"W_norm" :-> BASIC
                ,"TOP" :-> AND,"Dis_wait" :-> BASIC,"Weight_age" :-> BASIC
                ,"Hap_norm" :-> BASIC,"Feed_state" :-> BASIC,"Feed" :-> BASIC
                ,"H_dead" :-> BASIC,"Ready" :-> BASIC,"No_sleep" :-> BASIC
                ,"Died" :-> BASIC,"I_dead" :-> BASIC,"Punish" :-> BASIC
                ,"Light" :-> BASIC,"Medicin" :-> BASIC,"Give_snack" :-> BASIC
                ,"Ch_1" :-> BASIC,"Appetite" :-> BASIC,"Icons" :-> OR
                ,"Skinny" :-> BASIC,"Ch_2" :-> BASIC,"Hungry" :-> BASIC
                ,"P_dead" :-> BASIC
                ]
               ,["Pl_mode"
                  :-> {mk_tr("Hap_norm",{},{},mk_basicexpr("Count_11")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Play_wait",{},{},mk_basicexpr("B_pressed")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_compc(mk_compcond(ANDOP
                                                                                           ,mk_negcond(mk_instate("Over_feed"))
                                                                                           ,mk_negcond(mk_instate("Sick"))
                                                                               )           )
                                                                      ,mk_negcond(mk_instate("Very_sick"))
                                                          )           )
                                                 ,mk_negcond(mk_instate("Act_sleep"))
                                     )           )
                            ,mk_block(<>)
                            )
                      }
                ,"Angry"
                  :-> {mk_tr("Act_snack",{},{},mk_basicexpr("Count_9")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Play_mode",{},{},mk_basicexpr("Count_10")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Bastard"
                  :-> {mk_tr("Dic_wait",{},{},mk_basicexpr("Count_8")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Happy"
                  :-> {mk_tr("D_wait_2",{},{},mk_basicexpr("Puh_time")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Over_weight"
                  :-> {mk_tr("Stuffed_state",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_egen("Add_weight"))
                      ,mk_tr("Pizza",{},{},mk_basicexpr("Count_9")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("W_norm",{},{},mk_basicexpr("Time_food")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Act_snack"
                  :-> {mk_tr("Angry",{},{},mk_basicexpr("Count_10")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Hap_norm",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Duck"
                  :-> {mk_tr("Health_meter",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Health_icon"),mk_egen("Clr_duck")
                                      ,mk_egen("Counter")>)
                            )
                      }
                ,"Bad"
                  :-> {mk_tr("Dis_wait",{},{},mk_basicexpr("Count_8")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Duck_31",{},{},mk_basicexpr("Puh_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Food_31",{},{},mk_basicexpr("Time_food")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Hyper"
                  :-> {mk_tr("Died",{},{},mk_basicexpr("Dur_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Good",{},{},mk_basicexpr("Puh_time")
                            ,mk_instate("Asleep"),mk_block(<>))
                      ,mk_tr("Died",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Delite"),mk_egen("Bye_beeb"))
                      ,mk_tr("Died",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("More_food"),mk_egen("Bye_beeb"))
                      ,mk_tr("Died",{},{},mk_basicexpr("Time_food")
                            ,mk_instate("Skinny"),mk_egen("Bye_beeb"))
                      ,mk_tr("Died",{},{},mk_basicexpr("Clean_time")
                            ,mk_instate("Very_sick"),mk_egen("Bye_beeb"))
                      ,mk_tr("Died",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Over_feed"),mk_egen("Bye_beeb"))
                      }
                ,"Duck_11"
                  :-> {mk_tr("Disc_norm",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      ,mk_tr("Duck_12",{},{},mk_basicexpr("Count_2")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Food_state"
                  :-> {mk_tr("W_norm",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Skinny",{},{},mk_basicexpr("Count_2")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Duck_21"
                  :-> {mk_tr("Bad",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      ,mk_tr("Duck_22",{},{},mk_basicexpr("Count_2")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Good"
                  :-> {mk_tr("Med_state",{},{},mk_basicexpr("Count_11")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_negcond(mk_instate("Sick"))
                                                 ,mk_negcond(mk_instate("Very_sick"))
                                     )           )
                            ,mk_block(<>)
                            )
                      ,mk_tr("Duck_11",{},{},mk_basicexpr("Puh_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Food_1",{},{},mk_basicexpr("Time_food")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Duck_12"
                  :-> {mk_tr("Good",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Duck_11",{},{},mk_basicexpr("Count_3")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Disc_norm",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      }
                ,"Sick"
                  :-> {mk_tr("med_active",{},{},mk_basicexpr("Count_11")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Very_sick",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Food_21"
                  :-> {mk_tr("Bad",{},{},mk_basicexpr("Time_out")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      ,mk_tr("Food_22",{},{},mk_basicexpr("Count_0")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Duck_22"
                  :-> {mk_tr("Duck_21",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Disc_norm",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Bad",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      }
                ,"Duck_31"
                  :-> {mk_tr("Bastard",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      ,mk_tr("Duck_32",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Duck_32"
                  :-> {mk_tr("Duck_31",{},{},mk_basicexpr("Count_2")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Bastard",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      ,mk_tr("Bad",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Food_22"
                  :-> {mk_tr("Food_21",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Bad",{},{},mk_basicexpr("Time_out")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      }
                ,"Food_31"
                  :-> {mk_tr("Bastard",{},{},mk_basicexpr("Time_out")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      ,mk_tr("Food_32",{},{},mk_basicexpr("Count_0")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Food_32"
                  :-> {mk_tr("Food_31",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Bastard",{},{},mk_basicexpr("Time_out")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      ,mk_tr("Bad",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Sleep_dem"
                  :-> {mk_tr("Act_sleep",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Awake",{},{},mk_basicexpr("Time_out")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Pizza"
                  :-> {mk_tr("Over_weight",{},{},mk_basicexpr("Count_10")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Heavy_weight",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Over_feed"
                  :-> {mk_tr("Stuffed",{},{}
                            ,mk_compe(mk_compexpr(OROP
                                                 ,mk_basicexpr("Time_out")
                                                 ,mk_basicexpr("Count_1")
                                     )           )
                            ,mk_emptycond(""),mk_block(<>)
                            )
                      ,mk_tr("Dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Bye_beeb"))
                      }
                ,"Mood"
                  :-> {mk_tr("Appetite",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Write_hung"),mk_egen("Clr_mood")
                                      ,mk_egen("Counter")>)
                            )
                      }
                ,"Med_care"
                  :-> {mk_tr("Sick",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Very_sick",{},{},mk_basicexpr("Count_0")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Pl_wait"
                  :-> {mk_tr("Choice_1",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Act_sleep"
                  :-> {mk_tr("Sleep_dem",{},{},mk_basicexpr("Count_2")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Asleep",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Light_off"))
                      ,mk_tr("Awake",{},{},mk_basicexpr("Time_out")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Awake",{},{},mk_basicexpr("Count_2")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("No_sleep",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Stuffed"
                  :-> {mk_tr("Over_feed",{},{},mk_basicexpr("Count_0")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Fit",{},{},mk_basicexpr("Time_food")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Beep"),mk_egen("Add_app")>)
                            )
                      }
                ,"Stuffed_state"
                  :-> {mk_tr("Heavy_weight",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"med_active"
                  :-> {mk_tr("Sick",{},{},mk_basicexpr("Count_0")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Clean_need",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Clr_sick"))
                      ,mk_tr("Very_sick",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Health_meter"
                  :-> {mk_tr("Behavior",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Write_disciplin")
                                      ,mk_egen("Clr_meter"),mk_egen("Counter")>)
                            )
                      }
                ,"Wait"
                  :-> {mk_tr("Deadly",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Delite"),mk_egen("Bye_beeb"))
                      ,mk_tr("Deadly",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("More_food"),mk_egen("Bye_beeb"))
                      ,mk_tr("Deadly",{},{},mk_basicexpr("Time_food")
                            ,mk_instate("Skinny"),mk_egen("Bye_beeb"))
                      ,mk_tr("Deadly",{},{},mk_basicexpr("Dur_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Clean_need",{},{},mk_basicexpr("Puh_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Deadly",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Over_feed"),mk_egen("Bye_beeb"))
                      }
                ,"Med_state"
                  :-> {mk_tr("Hyper",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Add_dic"))}
                ,"Play"
                  :-> {mk_tr("Medicin",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Medicin_icon"),mk_egen("Clr_play")
                                      ,mk_egen("Counter")>)
                            )
                      }
                ,"Nothing"
                  :-> {mk_tr("P_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Delite"),mk_egen("Bye_beeb"))
                      ,mk_tr("P_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("More_food"),mk_egen("Bye_beeb"))
                      ,mk_tr("P_dead",{},{},mk_basicexpr("Time_food")
                            ,mk_instate("Skinny"),mk_egen("Bye_beeb"))
                      ,mk_tr("P_dead",{},{},mk_basicexpr("Clean_time")
                            ,mk_instate("Very_sick"),mk_egen("Bye_beeb"))
                      ,mk_tr("P_dead",{},{},mk_basicexpr("Dur_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Active",{},{},mk_basicexpr("Count_0")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("P_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Over_feed"),mk_egen("Bye_beeb"))
                      }
                ,"Delite"
                  :-> {mk_tr("W_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Bye_beeb"))
                      ,mk_tr("Heavy_weight",{},{},mk_basicexpr("Count_10")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Heavy_weight",{},{},mk_basicexpr("Time_out")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Food_1"
                  :-> {mk_tr("Disc_norm",{},{},mk_basicexpr("Time_out")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      ,mk_tr("Food_2",{},{},mk_basicexpr("Count_0")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Food_2"
                  :-> {mk_tr("Good",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Add_dic"))
                      ,mk_tr("Food_1",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Disc_norm",{},{},mk_basicexpr("Time_out")
                            ,mk_emptycond(""),mk_egen("Sub_dic"))
                      }
                ,"Asleep"
                  :-> {mk_tr("S_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Delite"),mk_egen("Bye_beeb"))
                      ,mk_tr("S_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("More_food"),mk_egen("Bye_beeb"))
                      ,mk_tr("S_dead",{},{},mk_basicexpr("Time_food")
                            ,mk_instate("Skinny"),mk_egen("Bye_beeb"))
                      ,mk_tr("S_dead",{},{},mk_basicexpr("Dur_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Awake",{},{},mk_basicexpr("Wake_time")
                            ,mk_emptycond(""),mk_egen("Light_on"))
                      ,mk_tr("S_dead",{},{},mk_basicexpr("Clean_time")
                            ,mk_instate("Very_sick"),mk_egen("Bye_beeb"))
                      ,mk_tr("S_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Over_feed"),mk_egen("Bye_beeb"))
                      }
                ,"More_food"
                  :-> {mk_tr("W_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Bye_beeb"))
                      ,mk_tr("Heavy_weight",{},{},mk_basicexpr("Count_2")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Heavy_weight",{},{},mk_basicexpr("Time_out")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Play_mode"
                  :-> {mk_tr("Angry",{},{},mk_basicexpr("Count_11")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Pl_wait",{},{},mk_basicexpr("B_pressed")
                            ,mk_compc(mk_compcond(ANDOP
                                                 ,mk_compc(mk_compcond(ANDOP
                                                                      ,mk_compc(mk_compcond(ANDOP
                                                                                           ,mk_negcond(mk_instate("Over_feed"))
                                                                                           ,mk_negcond(mk_instate("Sick"))
                                                                               )           )
                                                                      ,mk_negcond(mk_instate("Very_sick"))
                                                          )           )
                                                 ,mk_negcond(mk_instate("Act_sleep"))
                                     )           )
                            ,mk_block(<>)
                            )
                      ,mk_tr("Choice_2",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Choice_1"
                  :-> {mk_tr("Hap_norm",{},{},mk_basicexpr("jump_l")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Angry",{},{},mk_basicexpr("Jump_r")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Active"
                  :-> {mk_tr("Nothing",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Digest",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Choice_2"
                  :-> {mk_tr("Angry",{},{},mk_basicexpr("jump_l")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Hap_norm",{},{},mk_basicexpr("Jump_r")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Du_wait_1"
                  :-> {mk_tr("Hap_norm",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Angry",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Du_wait_2",{},{},mk_basicexpr("Count_3")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Act_duck"
                  :-> {mk_tr("Wait",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("rem_puh"))}
                ,"Disc_norm"
                  :-> {mk_tr("Slap_wait",{},{},mk_basicexpr("Count_8")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Duck_21",{},{},mk_basicexpr("Puh_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Food_21",{},{},mk_basicexpr("Time_food")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Du_wait_2"
                  :-> {mk_tr("Angry",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Du_wait_1",{},{},mk_basicexpr("Count_2")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Clean_need"
                  :-> {mk_tr("Sick",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_egen("Sick_icon"))
                      ,mk_tr("Act_duck",{},{},mk_basicexpr("Count_2")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Act_food"
                  :-> {mk_tr("Hungry",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Sub_app"))
                      ,mk_tr("Very_hungry",{},{}
                            ,mk_compe(mk_compexpr(OROP
                                                 ,mk_basicexpr("Time_out")
                                                 ,mk_basicexpr("Count_1")
                                     )           )
                            ,mk_emptycond(""),mk_block(<>)
                            )
                      }
                ,"Food_act"
                  :-> {mk_tr("Stuffed",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Sub_app"))
                      ,mk_tr("Fit",{},{}
                            ,mk_compe(mk_compexpr(OROP
                                                 ,mk_basicexpr("Time_out")
                                                 ,mk_basicexpr("Count_1")
                                     )           )
                            ,mk_emptycond(""),mk_block(<>)
                            )
                      }
                ,"Digest"
                  :-> {mk_tr("Nothing",{},{},mk_basicexpr("Puh_time")
                            ,mk_instate("Hyper"),mk_egen("Puh_mark"))}
                ,"Very_hungry"
                  :-> {mk_tr("Dead",{},{},mk_basicexpr("Time_food")
                            ,mk_emptycond(""),mk_egen("Bye_beeb"))
                      ,mk_tr("Act_food",{},{},mk_basicexpr("Count_0")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Fit"
                  :-> {mk_tr("Dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Delite"),mk_egen("Bye_beeb"))
                      ,mk_tr("Dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("More_food"),mk_egen("Bye_beeb"))
                      ,mk_tr("Dead",{},{},mk_basicexpr("Time_food")
                            ,mk_instate("Skinny"),mk_egen("Bye_beeb"))
                      ,mk_tr("Dead",{},{},mk_basicexpr("Clean_time")
                            ,mk_instate("Very_sick"),mk_egen("Bye_beeb"))
                      ,mk_tr("Dead",{},{},mk_basicexpr("Dur_time")
                            ,mk_emptycond(""),mk_egen("Bye_beeb"))
                      ,mk_tr("Food_act",{},{},mk_basicexpr("Count_0")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Hungry",{},{},mk_basicexpr("Time_food")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Beep"),mk_egen("Add_app")>)
                            )
                      }
                ,"Heavy_weight"
                  :-> {mk_tr("Over_weight",{},{},mk_basicexpr("Time_food")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Delite",{},{},mk_basicexpr("Count_9")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("More_food",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Slap_wait"
                  :-> {mk_tr("Good",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Add_dic"))
                      ,mk_tr("Disc_norm",{},{},mk_basicexpr("Count_9")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Dic_wait"
                  :-> {mk_tr("Bastard",{},{},mk_basicexpr("Count_9")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Bad",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Add_dic"))
                      }
                ,"Food_action"
                  :-> {mk_tr("Fit",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Sub_app"))
                      ,mk_tr("Hungry",{},{}
                            ,mk_compe(mk_compexpr(OROP
                                                 ,mk_basicexpr("Time_out")
                                                 ,mk_basicexpr("Count_1")
                                     )           )
                            ,mk_emptycond(""),mk_block(<>)
                            )
                      }
                ,"D_wait_1"
                  :-> {mk_tr("D_wait_2",{},{},mk_basicexpr("Count_3")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Hap_norm",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Happy",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Awake"
                  :-> {mk_tr("Sleep_dem",{},{},mk_basicexpr("Sleepy")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Act_sleep",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"D_wait_2"
                  :-> {mk_tr("Hap_norm",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("D_wait_1",{},{},mk_basicexpr("Count_2")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Play_wait"
                  :-> {mk_tr("Ch_1",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Ch_2",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Very_sick"
                  :-> {mk_tr("Med_care",{},{},mk_basicexpr("Count_11")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Deadly",{},{},mk_basicexpr("Clean_time")
                            ,mk_emptycond(""),mk_egen("Bye_beeb"))
                      }
                ,"Candy"
                  :-> {mk_tr("W_norm",{},{},mk_basicexpr("Count_10")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Over_weight",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Snack_state"
                  :-> {mk_tr("W_norm",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Skinny",{},{},mk_basicexpr("Count_10")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Behavior"
                  :-> {mk_tr("Mood",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Write_hung"),mk_egen("Clr_beh")
                                      ,mk_egen("Counter")>)
                            )
                      }
                ,"Snack"
                  :-> {mk_tr("Play",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Clr_snack"),mk_egen("Counter")
                                      ,mk_egen("Play_icon")>)
                            )
                      }
                ,"W_norm"
                  :-> {mk_tr("W_dead",{},{},mk_basicexpr("Dur_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Skinny",{},{},mk_basicexpr("Time_food")
                            ,mk_instate("Hungry"),mk_egen("Sub_weight"))
                      ,mk_tr("Feed_state",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_egen("Add_weight"))
                      ,mk_tr("Candy",{},{},mk_basicexpr("Count_9")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("W_dead",{},{},mk_basicexpr("Clean_time")
                            ,mk_instate("Very_sick"),mk_egen("Bye_beeb"))
                      ,mk_tr("W_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Over_feed"),mk_egen("Bye_beeb"))
                      }
                ,"Hap_norm"
                  :-> {mk_tr("H_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Delite"),mk_egen("Bye_beeb"))
                      ,mk_tr("H_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("More_food"),mk_egen("Bye_beeb"))
                      ,mk_tr("H_dead",{},{},mk_basicexpr("Time_food")
                            ,mk_instate("Skinny"),mk_egen("Bye_beeb"))
                      ,mk_tr("H_dead",{},{},mk_basicexpr("Dur_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Du_wait_2",{},{},mk_basicexpr("Puh_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Give_snack",{},{},mk_basicexpr("Count_9")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Pl_mode",{},{},mk_basicexpr("Count_10")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("H_dead",{},{},mk_basicexpr("Clean_time")
                            ,mk_instate("Very_sick"),mk_egen("Bye_beeb"))
                      ,mk_tr("H_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Over_feed"),mk_egen("Bye_beeb"))
                      }
                ,"Weight_age"
                  :-> {mk_tr("Punish",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Counter"),mk_egen("Clr_aw")
                                      ,mk_egen("Slap_icon")>)
                            )
                      }
                ,"Feed"
                  :-> {mk_tr("Light",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Light_icon"),mk_egen("Clr_feed")
                                      ,mk_egen("Counter")>)
                            )
                      }
                ,"Dis_wait"
                  :-> {mk_tr("Bad",{},{},mk_basicexpr("Count_9")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Disc_norm",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_egen("Add_dic"))
                      }
                ,"Feed_state"
                  :-> {mk_tr("Over_weight",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("W_norm",{},{},mk_basicexpr("Count_2")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Ready"
                  :-> {mk_tr("Ready",{},{}
                            ,mk_compe(mk_compexpr(OROP
                                                 ,mk_basicexpr("C_pressed")
                                                 ,mk_basicexpr("SE_RESET")
                                     )           )
                            ,mk_emptycond(""),mk_egen("Clr_displ")
                            )
                      ,mk_tr("I_dead",{},{},mk_basicexpr("Dur_time")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Feed",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond(""),mk_egen("Feed_icon"))
                      ,mk_tr("I_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Delite"),mk_egen("Bye_beeb"))
                      ,mk_tr("I_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("More_food"),mk_egen("Bye_beeb"))
                      ,mk_tr("I_dead",{},{},mk_basicexpr("Time_food")
                            ,mk_instate("Skinny"),mk_egen("Bye_beeb"))
                      ,mk_tr("I_dead",{},{},mk_basicexpr("Clean_time")
                            ,mk_instate("Very_sick"),mk_egen("Bye_beeb"))
                      ,mk_tr("I_dead",{},{},mk_basicexpr("B_pressed")
                            ,mk_instate("Over_feed"),mk_egen("Bye_beeb"))
                      }
                ,"No_sleep"
                  :-> {mk_tr("Act_sleep",{},{},mk_basicexpr("Time_out")
                            ,mk_emptycond(""),mk_block(<>))}
                ,"Punish"
                  :-> {mk_tr("Snack",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Counter"),mk_egen("Clr_ponish")
                                      ,mk_egen("Snack_icon")>)
                            )
                      }
                ,"Light"
                  :-> {mk_tr("Duck",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Duck_icon"),mk_egen("Clr_light")
                                      ,mk_egen("Counter")>)
                            )
                      }
                ,"Medicin"
                  :-> {mk_tr("Feed",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Feed_icon"),mk_egen("Clr_med")
                                      ,mk_egen("Init_count")>)
                            )
                      }
                ,"Give_snack"
                  :-> {mk_tr("Happy",{},{},mk_basicexpr("B_pressed")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Hap_norm",{},{},mk_basicexpr("Count_10")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Ch_1"
                  :-> {mk_tr("Happy",{},{},mk_basicexpr("jump_l")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Hap_norm",{},{},mk_basicexpr("Jump_r")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Ch_2"
                  :-> {mk_tr("Hap_norm",{},{},mk_basicexpr("Jump_r")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Happy",{},{},mk_basicexpr("jump_l")
                            ,mk_emptycond(""),mk_block(<>))
                      }
                ,"Appetite"
                  :-> {mk_tr("Weight_age",{},{},mk_basicexpr("A_pressed")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Write_a_w"),mk_egen("Clr_app")
                                      ,mk_egen("Counter")>)
                            )
                      }
                ,"Skinny"
                  :-> {mk_tr("Food_state",{},{},mk_basicexpr("Count_1")
                            ,mk_emptycond(""),mk_egen("Add_weight"))
                      ,mk_tr("Snack_state",{},{},mk_basicexpr("Count_9")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("W_dead",{},{},mk_basicexpr("Time_food")
                            ,mk_emptycond(""),mk_egen("Bye_beeb"))
                      }
                ,"Hungry"
                  :-> {mk_tr("Food_action",{},{},mk_basicexpr("Count_0")
                            ,mk_emptycond(""),mk_block(<>))
                      ,mk_tr("Very_hungry",{},{},mk_basicexpr("Time_food")
                            ,mk_emptycond("")
                            ,mk_block(<mk_egen("Beep"),mk_egen("Add_app")>)
                            )
                      }
                ]
               ,["Sleep_func" :-> "Awake","Puh_puh" :-> "Nothing"
                ,"Disciplin" :-> "Disc_norm","Cleaning" :-> "Wait"
                ,"Weight" :-> "W_norm","Hunger" :-> "Fit"
                ,"Happiness" :-> "Hap_norm","Icons" :-> "Ready"
                ]
               ,{},{}
               )
         ,mk_ha({"A_pressed","B_pressed","C_pressed","Clean_time","Count_0"
                ,"Count_1","Count_10","Count_11","Count_2","Count_3","Count_8"
                ,"Count_9","Dur_time","Jump_r","jump_l","Puh_time","Sleepy"
                ,"Time_food","Time_out","Wake_time"
                }
               ,{},"",{},{},{},[],[],[],[],{},{}
               )
         ,[]
         )
