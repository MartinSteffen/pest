// Program automatically created by TESC-Export.
//

event (Write_hung,Write_hap,Write_disciplin,Write_a_w,Switch_beep,Sub_weight,Sub_dic,Sub_app,Snack_icon,Slap_icon,Sick_icon,rem_puh,Puh_mark,Play_icon,Medicin_icon,Light_on,Light_off,Light_icon,Init_count,Health_icon,Feed_icon,Duck_icon,Counter,Clr_snack,Clr_sick,Clr_ponish,Clr_play,Clr_mood,Clr_meter,Clr_med,Clr_light,Clr_hap,Clr_feed,Clr_duck,Clr_displ,Clr_beh,Clr_aw,Clr_app,Bye_beeb,Beep,Add_weight,Add_dic,Add_app,Wake_time,Time_out,Time_food,Sleepy,Puh_time,jump_l,Jump_r,Dur_time,Count_9,Count_8,Count_3,Count_2,Count_11,Count_10,Count_1,Count_0,Clean_time,C_pressed,B_pressed,A_pressed)

and TOP
    or Hunger
        basic Fit
        basic Food_action
        basic Hungry
        basic Stuffed
        basic Food_act
        basic Dead
        basic Over_feed
        basic Very_hungry
        basic Act_food
        from Fit to Dead on B_pressed[in(TOP.Weight.Delite)] do Bye_beeb
        from Fit to Dead on B_pressed[in(TOP.Weight.More_food)] do Bye_beeb
        from Fit to Dead on Time_food[in(TOP.Weight.Skinny)] do Bye_beeb
        from Fit to Dead on Clean_time[in(TOP.Cleaning.Very_sick)] do Bye_beeb
        from Fit to Dead on Dur_time do Bye_beeb
        from Fit to Food_act on Count_0
        from Fit to Hungry on Time_food do (Beep,Add_app)
        from Food_action to Fit on B_pressed do Sub_app
        from Food_action to Hungry on (Time_out | Count_1)
        from Hungry to Food_action on Count_0
        from Hungry to Very_hungry on Time_food do (Beep,Add_app)
        from Stuffed to Over_feed on Count_0
        from Stuffed to Fit on Time_food do (Beep,Add_app)
        from Food_act to Stuffed on B_pressed do Sub_app
        from Food_act to Fit on (Time_out | Count_1)
        from Over_feed to Stuffed on (Time_out | Count_1)
        from Over_feed to Dead on B_pressed do Bye_beeb
        from Very_hungry to Dead on Time_food do Bye_beeb
        from Very_hungry to Act_food on Count_0
        from Act_food to Hungry on B_pressed do Sub_app
        from Act_food to Very_hungry on (Time_out | Count_1)
        default Fit
    end Hunger
    or Puh_puh
        basic Nothing
        basic Digest
        basic Active
        basic P_dead
        from Nothing to P_dead on B_pressed[in(TOP.Weight.Delite)] do Bye_beeb
        from Nothing to P_dead on B_pressed[in(TOP.Weight.More_food)] do Bye_beeb
        from Nothing to P_dead on Time_food[in(TOP.Weight.Skinny)] do Bye_beeb
        from Nothing to P_dead on Clean_time[in(TOP.Cleaning.Very_sick)] do Bye_beeb
        from Nothing to P_dead on Dur_time
        from Nothing to Active on Count_0
        from Nothing to P_dead on B_pressed[in(TOP.Hunger.Over_feed)] do Bye_beeb
        from Digest to Nothing on Puh_time[in(TOP.Disciplin.Hyper)] do Puh_mark
        from Active to Nothing on Count_1
        from Active to Digest on B_pressed
        default Nothing
    end Puh_puh
    or Cleaning
        basic Wait
        basic Clean_need
        basic Sick
        basic med_active
        basic Act_duck
        basic Very_sick
        basic Med_care
        basic Deadly
        from Wait to Deadly on B_pressed[in(TOP.Weight.Delite)] do Bye_beeb
        from Wait to Deadly on B_pressed[in(TOP.Weight.More_food)] do Bye_beeb
        from Wait to Deadly on Time_food[in(TOP.Weight.Skinny)] do Bye_beeb
        from Wait to Deadly on Dur_time
        from Wait to Clean_need on Puh_time
        from Wait to Deadly on B_pressed[in(TOP.Hunger.Over_feed)] do Bye_beeb
        from Clean_need to Sick on Clean_time do Sick_icon
        from Clean_need to Act_duck on Count_2
        from Sick to med_active on Count_11
        from Sick to Very_sick on Clean_time
        from med_active to Sick on Count_0
        from med_active to Clean_need on B_pressed do Clr_sick
        from med_active to Very_sick on Clean_time
        from Act_duck to Wait on B_pressed do rem_puh
        from Very_sick to Med_care on Count_11
        from Very_sick to Deadly on Clean_time do Bye_beeb
        from Med_care to Sick on B_pressed
        from Med_care to Very_sick on Count_0
        default Wait
    end Cleaning
    or Sleep_func
        basic Asleep
        basic Awake
        basic Sleep_dem
        basic Act_sleep
        basic S_dead
        basic No_sleep
        from Asleep to S_dead on B_pressed[in(TOP.Weight.Delite)] do Bye_beeb
        from Asleep to S_dead on B_pressed[in(TOP.Weight.More_food)] do Bye_beeb
        from Asleep to S_dead on Time_food[in(TOP.Weight.Skinny)] do Bye_beeb
        from Asleep to S_dead on Dur_time
        from Asleep to Awake on Wake_time do Light_on
        from Asleep to S_dead on Clean_time[in(TOP.Cleaning.Very_sick)] do Bye_beeb
        from Asleep to S_dead on B_pressed[in(TOP.Hunger.Over_feed)] do Bye_beeb
        from Awake to Sleep_dem on Sleepy
        from Awake to Act_sleep on Count_1
        from Sleep_dem to Act_sleep on Count_1
        from Sleep_dem to Awake on Time_out
        from Act_sleep to Sleep_dem on Count_2
        from Act_sleep to Asleep on B_pressed do Light_off
        from Act_sleep to Awake on Time_out
        from Act_sleep to Awake on Count_2
        from Act_sleep to No_sleep on B_pressed
        from No_sleep to Act_sleep on Time_out
        default Awake
    end Sleep_func
    or Happiness
        basic Hap_norm
        basic Angry
        basic Du_wait_1
        basic Du_wait_2
        basic Happy
        basic D_wait_1
        basic D_wait_2
        basic Act_snack
        basic Give_snack
        basic H_dead
        basic Play_mode
        basic Pl_wait
        basic Choice_1
        basic Choice_2
        basic Pl_mode
        basic Play_wait
        basic Ch_1
        basic Ch_2
        from Hap_norm to H_dead on B_pressed[in(TOP.Weight.Delite)] do Bye_beeb
        from Hap_norm to H_dead on B_pressed[in(TOP.Weight.More_food)] do Bye_beeb
        from Hap_norm to H_dead on Time_food[in(TOP.Weight.Skinny)] do Bye_beeb
        from Hap_norm to H_dead on Dur_time
        from Hap_norm to Du_wait_2 on Puh_time
        from Hap_norm to Give_snack on Count_9
        from Hap_norm to Pl_mode on Count_10
        from Hap_norm to H_dead on Clean_time[in(TOP.Cleaning.Very_sick)] do Bye_beeb
        from Hap_norm to H_dead on B_pressed[in(TOP.Hunger.Over_feed)] do Bye_beeb
        from Angry to Act_snack on Count_9
        from Angry to Play_mode on Count_10
        from Du_wait_1 to Hap_norm on B_pressed
        from Du_wait_1 to Angry on Clean_time
        from Du_wait_1 to Du_wait_2 on Count_3
        from Du_wait_2 to Angry on Clean_time
        from Du_wait_2 to Du_wait_1 on Count_2
        from Happy to D_wait_2 on Puh_time
        from D_wait_1 to D_wait_2 on Count_3
        from D_wait_1 to Hap_norm on Clean_time
        from D_wait_1 to Happy on B_pressed
        from D_wait_2 to Hap_norm on Clean_time
        from D_wait_2 to D_wait_1 on Count_2
        from Act_snack to Angry on Count_10
        from Act_snack to Hap_norm on B_pressed
        from Give_snack to Happy on B_pressed
        from Give_snack to Hap_norm on Count_10
        from Play_mode to Angry on Count_11
        from Play_mode to Pl_wait on B_pressed[(((!in(TOP.Hunger.Over_feed) & !in(TOP.Cleaning.Sick)) & !in(TOP.Cleaning.Very_sick)) & !in(TOP.Sleep_func.Act_sleep))]
        from Play_mode to Choice_2 on B_pressed
        from Pl_wait to Choice_1 on A_pressed
        from Choice_1 to Hap_norm on jump_l
        from Choice_1 to Angry on Jump_r
        from Choice_2 to Angry on jump_l
        from Choice_2 to Hap_norm on Jump_r
        from Pl_mode to Hap_norm on Count_11
        from Pl_mode to Play_wait on B_pressed[(((!in(TOP.Hunger.Over_feed) & !in(TOP.Cleaning.Sick)) & !in(TOP.Cleaning.Very_sick)) & !in(TOP.Sleep_func.Act_sleep))]
        from Play_wait to Ch_1 on A_pressed
        from Play_wait to Ch_2 on B_pressed
        from Ch_1 to Happy on jump_l
        from Ch_1 to Hap_norm on Jump_r
        from Ch_2 to Hap_norm on Jump_r
        from Ch_2 to Happy on jump_l
        default Hap_norm
    end Happiness
    or Weight
        basic W_norm
        basic Over_weight
        basic Heavy_weight
        basic Skinny
        basic Feed_state
        basic Food_state
        basic Snack_state
        basic Candy
        basic Stuffed_state
        basic Pizza
        basic Delite
        basic W_dead
        basic More_food
        from W_norm to W_dead on Dur_time
        from W_norm to Skinny on Time_food[in(TOP.Hunger.Hungry)] do Sub_weight
        from W_norm to Feed_state on Count_1 do Add_weight
        from W_norm to Candy on Count_9
        from W_norm to W_dead on Clean_time[in(TOP.Cleaning.Very_sick)] do Bye_beeb
        from W_norm to W_dead on B_pressed[in(TOP.Hunger.Over_feed)] do Bye_beeb
        from Over_weight to Stuffed_state on Count_1 do Add_weight
        from Over_weight to Pizza on Count_9
        from Over_weight to W_norm on Time_food
        from Heavy_weight to Over_weight on Time_food
        from Heavy_weight to Delite on Count_9
        from Heavy_weight to More_food on Count_1
        from Skinny to Food_state on Count_1 do Add_weight
        from Skinny to Snack_state on Count_9
        from Skinny to W_dead on Time_food do Bye_beeb
        from Feed_state to Over_weight on B_pressed
        from Feed_state to W_norm on Count_2
        from Food_state to W_norm on B_pressed
        from Food_state to Skinny on Count_2
        from Snack_state to W_norm on B_pressed
        from Snack_state to Skinny on Count_10
        from Candy to W_norm on Count_10
        from Candy to Over_weight on B_pressed
        from Stuffed_state to Heavy_weight on B_pressed
        from Pizza to Over_weight on Count_10
        from Pizza to Heavy_weight on B_pressed
        from Delite to W_dead on B_pressed do Bye_beeb
        from Delite to Heavy_weight on Count_10
        from Delite to Heavy_weight on Time_out
        from More_food to W_dead on B_pressed do Bye_beeb
        from More_food to Heavy_weight on Count_2
        from More_food to Heavy_weight on Time_out
        default W_norm
    end Weight
    or Disciplin
        basic Hyper
        basic Good
        basic Med_state
        basic Died
        basic Disc_norm
        basic Duck_11
        basic Duck_12
        basic Food_1
        basic Food_2
        basic Slap_wait
        basic Bad
        basic Duck_21
        basic Duck_22
        basic Food_21
        basic Food_22
        basic Dis_wait
        basic Bastard
        basic Duck_31
        basic Duck_32
        basic Food_31
        basic Food_32
        basic Dic_wait
        from Hyper to Died on Dur_time
        from Hyper to Good on Puh_time[in(TOP.Sleep_func.Asleep)]
        from Hyper to Died on B_pressed[in(TOP.Weight.Delite)] do Bye_beeb
        from Hyper to Died on B_pressed[in(TOP.Weight.More_food)] do Bye_beeb
        from Hyper to Died on Time_food[in(TOP.Weight.Skinny)] do Bye_beeb
        from Hyper to Died on Clean_time[in(TOP.Cleaning.Very_sick)] do Bye_beeb
        from Hyper to Died on B_pressed[in(TOP.Hunger.Over_feed)] do Bye_beeb
        from Good to Med_state on Count_11[(!in(TOP.Cleaning.Sick) & !in(TOP.Cleaning.Very_sick))]
        from Good to Duck_11 on Puh_time
        from Good to Food_1 on Time_food
        from Med_state to Hyper on B_pressed do Add_dic
        from Disc_norm to Slap_wait on Count_8
        from Disc_norm to Duck_21 on Puh_time
        from Disc_norm to Food_21 on Time_food
        from Duck_11 to Disc_norm on Clean_time do Sub_dic
        from Duck_11 to Duck_12 on Count_2
        from Duck_12 to Good on B_pressed
        from Duck_12 to Duck_11 on Count_3
        from Duck_12 to Disc_norm on Clean_time do Sub_dic
        from Food_1 to Disc_norm on Time_out do Sub_dic
        from Food_1 to Food_2 on Count_0
        from Food_2 to Good on B_pressed do Add_dic
        from Food_2 to Food_1 on Count_1
        from Food_2 to Disc_norm on Time_out do Sub_dic
        from Slap_wait to Good on B_pressed do Add_dic
        from Slap_wait to Disc_norm on Count_9
        from Bad to Dis_wait on Count_8
        from Bad to Duck_31 on Puh_time
        from Bad to Food_31 on Time_food
        from Duck_21 to Bad on Clean_time do Sub_dic
        from Duck_21 to Duck_22 on Count_2
        from Duck_22 to Duck_21 on Count_1
        from Duck_22 to Disc_norm on B_pressed
        from Duck_22 to Bad on Clean_time do Sub_dic
        from Food_21 to Bad on Time_out do Sub_dic
        from Food_21 to Food_22 on Count_0
        from Food_22 to Food_21 on Count_1
        from Food_22 to Bad on Time_out do Sub_dic
        from Dis_wait to Bad on Count_9
        from Dis_wait to Disc_norm on B_pressed do Add_dic
        from Bastard to Dic_wait on Count_8
        from Duck_31 to Bastard on Clean_time do Sub_dic
        from Duck_31 to Duck_32 on Count_1
        from Duck_32 to Duck_31 on Count_2
        from Duck_32 to Bastard on Clean_time do Sub_dic
        from Duck_32 to Bad on B_pressed
        from Food_31 to Bastard on Time_out do Sub_dic
        from Food_31 to Food_32 on Count_0
        from Food_32 to Food_31 on Count_1
        from Food_32 to Bastard on Time_out do Sub_dic
        from Food_32 to Bad on B_pressed
        from Dic_wait to Bastard on Count_9
        from Dic_wait to Bad on B_pressed do Add_dic
        default Disc_norm
    end Disciplin
    or Icons
        basic Ready
        basic Feed
        basic Light
        basic Duck
        basic Health_meter
        basic Behavior
        basic Mood
        basic Appetite
        basic Weight_age
        basic Punish
        basic Play
        basic Medicin
        basic Snack
        basic I_dead
        from Ready to Ready on (C_pressed | SE_RESET) do Clr_displ
        from Ready to I_dead on Dur_time
        from Ready to Feed on A_pressed do Feed_icon
        from Ready to I_dead on B_pressed[in(TOP.Weight.Delite)] do Bye_beeb
        from Ready to I_dead on B_pressed[in(TOP.Weight.More_food)] do Bye_beeb
        from Ready to I_dead on Time_food[in(TOP.Weight.Skinny)] do Bye_beeb
        from Ready to I_dead on Clean_time[in(TOP.Cleaning.Very_sick)] do Bye_beeb
        from Ready to I_dead on B_pressed[in(TOP.Hunger.Over_feed)] do Bye_beeb
        from Feed to Light on A_pressed do (Light_icon,Clr_feed,Counter)
        from Light to Duck on A_pressed do (Duck_icon,Clr_light,Counter)
        from Duck to Health_meter on A_pressed do (Health_icon,Clr_duck,Counter)
        from Health_meter to Behavior on A_pressed do (Write_disciplin,Clr_meter,Counter)
        from Behavior to Mood on A_pressed do (Write_hung,Clr_beh,Counter)
        from Mood to Appetite on A_pressed do (Write_hung,Clr_mood,Counter)
        from Appetite to Weight_age on A_pressed do (Write_a_w,Clr_app,Counter)
        from Weight_age to Punish on A_pressed do (Counter,Clr_aw,Slap_icon)
        from Punish to Snack on A_pressed do (Counter,Clr_ponish,Snack_icon)
        from Play to Medicin on A_pressed do (Medicin_icon,Clr_play,Counter)
        from Medicin to Feed on A_pressed do (Feed_icon,Clr_med,Init_count)
        from Snack to Play on A_pressed do (Clr_snack,Counter,Play_icon)
        default Ready
    end Icons
end TOP