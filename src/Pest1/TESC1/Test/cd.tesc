# TESC-File generiert durch TESC-Export
# Formatierung der Transitionlabel geht beim Export verloren.
# Transitionlabels sind i.a. vollstaendig geklammert (bis auf aeussere Klammern)

and TOP:

     or Disp:

          basic Disp_ready;
          basic Cd_nr;
          basic Time_nr;
          basic Time_total;

          defcon: Disp_ready;

          transitions:
               from Disp_ready to Cd_nr on CD do Get_nr;
               from Cd_nr to Time_nr on DISPLAY do Get_time;
               from Time_nr to Time_total on DISPLAY do Get_time_left;
               from Time_total to Cd_nr on DISPLAY do Get_nr;

     end Disp;

     or T_2:

          basic Reset_T_2;
          basic Time2_reset;
          basic Time2;

          defcon: Reset_T_2;

          transitions:
               from Reset_T_2 to Time2_reset on CD;
               from Time2_reset to Time2 on Key_0_Pressed||(Key_1_Pressed||(Key_2_Pressed||(Key_3_Pressed||(Key_4_Pressed||(Key_5_Pressed||(Key_7_Pressed||(Key_6_Pressed||(Key_8_Pressed||Key_9_Pressed)))))))) do Time_2;
               from Time2 to Time2_reset on Timeout_2;

     end T_2;

     or T_1800:

          basic Reset_T_1800;
          basic Time_1800_Reset;
          basic Time1800;

          defcon: Reset_T_1800;

          transitions:
               from Reset_T_1800 to Time_1800_Reset on CD;
               from Time_1800_Reset to Time1800 on Lead_Out[!(in(TOP.Repeat_Control.Repeat_on))] do Time_1800;
               from Time1800 to Time_1800_Reset on Timeout_1800;

     end T_1800;

     or K_0:

          basic Reset_K_0;
          basic Stand_by_Key0;
          basic Key_0;
          basic Key_01;
          basic Key_02;
          basic Key_03;
          basic Key_04;
          basic Key_05;
          basic Key_06;
          basic Key_07;
          basic Key_08;
          basic Key_09;

          defcon: Reset_K_0;

          transitions:
               from Reset_K_0 to Stand_by_Key0 on CD;
               from Reset_K_0 to Stand_by_Key0 on NotOk do Display_UNKNOWN;
               from Stand_by_Key0 to Key_0 on Key_0_Pressed[(!(in(TOP.K_1.Key_1))&&!(in(TOP.K_2.Key_2)))&&!(in(TOP.K_3.Key_3))] do Display__0;
               from Key_0 to Key_01 on Key_1_Pressed do Display_1;
               from Key_0 to Stand_by_Key0 on Key_0_Pressed[(!(in(TOP.K_1.Key_1))&&!(in(TOP.K_2.Key_2)))&&!(in(TOP.K_3.Key_3))] do Display_UNKNOWN;
               from Key_0 to Key_02 on Key_2_Pressed do Display_2;
               from Key_0 to Key_03 on Key_3_Pressed do Display_3;
               from Key_0 to Key_04 on Key_4_Pressed do Display_4;
               from Key_0 to Key_05 on Key_5_Pressed do Display_5;
               from Key_0 to Key_06 on Key_6_Pressed do Display_6;
               from Key_0 to Key_07 on Key_7_Pressed do Display_7;
               from Key_0 to Key_08 on Key_8_Pressed do Display_8;
               from Key_0 to Key_09 on Key_9_Pressed do Display_9;
               from Key_0 to Stand_by_Key0 on Timeout_2 do Display_UNKNOWN;
               from Key_0 to Stand_by_Key0 on PLAY do Display_UNKNOWN;
               from Key_01 to Stand_by_Key0 on Timeout_2 do Display_1, Goto_1;
               from Key_01 to Stand_by_Key0 on PLAY do Display_1, Goto_1;
               from Key_02 to Stand_by_Key0 on Timeout_2 do Display_2, Goto_2;
               from Key_02 to Stand_by_Key0 on PLAY do Display_2, Goto_2;
               from Key_03 to Stand_by_Key0 on Timeout_2 do Display_3, Goto_3;
               from Key_03 to Stand_by_Key0 on PLAY do Display_3, Goto_3;
               from Key_04 to Stand_by_Key0 on Timeout_2 do Display_4, Goto_4;
               from Key_04 to Stand_by_Key0 on PLAY do Display_4, Goto_4;
               from Key_05 to Stand_by_Key0 on Timeout_2 do Display_5, Goto_5;
               from Key_05 to Stand_by_Key0 on PLAY do Display_5, Goto_5;
               from Key_06 to Stand_by_Key0 on Timeout_2 do Display_6, Goto_6;
               from Key_06 to Stand_by_Key0 on PLAY do Display_6, Goto_6;
               from Key_07 to Stand_by_Key0 on Timeout_2 do Display_7, Goto_7;
               from Key_07 to Stand_by_Key0 on PLAY do Display_7, Goto_7;
               from Key_08 to Stand_by_Key0 on Timeout_2 do Display_8, Goto_8;
               from Key_08 to Stand_by_Key0 on PLAY do Display_8, Goto_8;
               from Key_09 to Stand_by_Key0 on Timeout_2 do Display_9, Goto_9;
               from Key_09 to Stand_by_Key0 on PLAY do Display_9, Goto_9;

     end K_0;

     or K_3:

          basic Reset_K_3;
          basic Stand_by_Key3;
          basic Key_3;
          basic Key_31;
          basic Key_30;
          basic Key_32;
          basic Key_33;
          basic Key_34;
          basic Key_35;
          basic Key_36;
          basic Key_37;
          basic Key_38;
          basic Key_39;

          defcon: Reset_K_3;

          transitions:
               from Reset_K_3 to Stand_by_Key3 on CD;
               from Reset_K_3 to Stand_by_Key3 on NotOk;
               from Stand_by_Key3 to Key_3 on Key_3_Pressed[(!(in(TOP.K_0.Key_0))&&!(in(TOP.K_1.Key_1)))&&!(in(TOP.K_2.Key_2))] do Display__3;
               from Key_3 to Key_31 on Key_1_Pressed do Display_31;
               from Key_3 to Key_30 on Key_0_Pressed do Display_30;
               from Key_3 to Key_32 on Key_2_Pressed do Display_32;
               from Key_3 to Key_33 on Key_3_Pressed[(!(in(TOP.K_0.Key_0))&&!(in(TOP.K_1.Key_1)))&&!(in(TOP.K_2.Key_2))] do Display_33;
               from Key_3 to Key_34 on Key_4_Pressed do Display_34;
               from Key_3 to Key_35 on Key_5_Pressed do Display_35;
               from Key_3 to Key_36 on Key_6_Pressed do Display_36;
               from Key_3 to Key_37 on Key_7_Pressed do Display_37;
               from Key_3 to Key_38 on Key_8_Pressed do Display_38;
               from Key_3 to Key_39 on Key_9_Pressed do Display_39;
               from Key_3 to Stand_by_Key3 on Timeout_2 do Display_3, Goto_3;
               from Key_3 to Stand_by_Key3 on PLAY do Display_3, Goto_3;
               from Key_31 to Stand_by_Key3 on Timeout_2 do Display_31, Goto_31;
               from Key_31 to Stand_by_Key3 on PLAY do Display_31, Goto_31;
               from Key_30 to Stand_by_Key3 on Timeout_2 do Display_30, Goto_30;
               from Key_30 to Stand_by_Key3 on PLAY do Display_30, Goto_30;
               from Key_32 to Stand_by_Key3 on Timeout_2 do Display_32, Goto_32;
               from Key_32 to Stand_by_Key3 on PLAY do Display_32, Goto_32;
               from Key_33 to Stand_by_Key3 on Timeout_2 do Display_33, Goto_33;
               from Key_33 to Stand_by_Key3 on PLAY do Display_33, Goto_33;
               from Key_34 to Stand_by_Key3 on Timeout_2 do Display_34, Goto_34;
               from Key_34 to Stand_by_Key3 on PLAY do Display_34, Goto_34;
               from Key_35 to Stand_by_Key3 on Timeout_2 do Display_35, Goto_35;
               from Key_35 to Stand_by_Key3 on PLAY do Display_35, Goto_35;
               from Key_36 to Stand_by_Key3 on Timeout_2 do Display_36, Goto_36;
               from Key_36 to Stand_by_Key3 on PLAY do Display_36, Goto_36;
               from Key_37 to Stand_by_Key3 on Timeout_2 do Display_37, Goto_37;
               from Key_37 to Stand_by_Key3 on PLAY do Display_37, Goto_37;
               from Key_38 to Stand_by_Key3 on Timeout_2 do Display_38, Goto_38;
               from Key_38 to Stand_by_Key3 on PLAY do Display_38, Goto_38;
               from Key_39 to Stand_by_Key3 on Timeout_2 do Display_39, Goto_39;
               from Key_39 to Stand_by_Key3 on PLAY do Display_39, Goto_39;

     end K_3;

     or K_2:

          basic Stand_by_Key2;
          basic Reset_K_2;
          basic Key_2;
          basic Key_21;
          basic Key_20;
          basic Key_22;
          basic Key_23;
          basic Key_24;
          basic Key_25;
          basic Key_26;
          basic Key_27;
          basic Key_28;
          basic Key_29;

          defcon: Reset_K_2;

          transitions:
               from Stand_by_Key2 to Stand_by_Key2 on NotOk;
               from Stand_by_Key2 to Key_2 on Key_2_Pressed[(!(in(TOP.K_0.Key_0))&&!(in(TOP.K_1.Key_1)))&&!(in(TOP.K_3.Key_3))] do Display__2;
               from Reset_K_2 to Stand_by_Key2 on CD;
               from Key_2 to Key_21 on Key_1_Pressed do Display_21;
               from Key_2 to Key_20 on Key_0_Pressed do Display_20;
               from Key_2 to Key_22 on Key_2_Pressed[(!(in(TOP.K_0.Key_0))&&!(in(TOP.K_1.Key_1)))&&!(in(TOP.K_3.Key_3))] do Display_22;
               from Key_2 to Key_23 on Key_3_Pressed do Display_23;
               from Key_2 to Key_24 on Key_4_Pressed do Display_24;
               from Key_2 to Key_25 on Key_5_Pressed do Display_25;
               from Key_2 to Key_26 on Key_6_Pressed do Display_26;
               from Key_2 to Key_27 on Key_7_Pressed do Display_27;
               from Key_2 to Key_28 on Key_8_Pressed do Display_28;
               from Key_2 to Key_29 on Key_9_Pressed do Display_29;
               from Key_2 to Stand_by_Key2 on Timeout_2 do Display_2, Goto_2;
               from Key_2 to Stand_by_Key2 on PLAY do Display_2, Goto_2;
               from Key_21 to Stand_by_Key2 on Timeout_2 do Display_21, Goto_21;
               from Key_21 to Stand_by_Key2 on PLAY do Display_21, Goto_21;
               from Key_20 to Stand_by_Key2 on Timeout_2 do Display_20, Goto_20;
               from Key_20 to Stand_by_Key2 on PLAY do Display_20, Goto_20;
               from Key_22 to Stand_by_Key2 on Timeout_2 do Display_22, Goto_22;
               from Key_22 to Stand_by_Key2 on PLAY do Display_22, Goto_22;
               from Key_23 to Stand_by_Key2 on Timeout_2 do Display_23, Goto_23;
               from Key_23 to Stand_by_Key2 on PLAY do Display_23, Goto_23;
               from Key_24 to Stand_by_Key2 on Timeout_2 do Display_24, Goto_24;
               from Key_24 to Stand_by_Key2 on PLAY do Display_24, Goto_24;
               from Key_25 to Stand_by_Key2 on Timeout_2 do Display_25, Goto_25;
               from Key_25 to Stand_by_Key2 on PLAY do Display_25, Goto_25;
               from Key_26 to Stand_by_Key2 on Timeout_2 do Display_26, Goto_26;
               from Key_26 to Stand_by_Key2 on PLAY do Display_26, Goto_26;
               from Key_27 to Stand_by_Key2 on Timeout_2 do Display_27, Goto_27;
               from Key_27 to Stand_by_Key2 on PLAY do Display_27, Goto_27;
               from Key_28 to Stand_by_Key2 on Timeout_2 do Display_28, Goto_28;
               from Key_28 to Stand_by_Key2 on PLAY do Display_28, Goto_28;
               from Key_29 to Stand_by_Key2 on Timeout_2 do Display_29, Goto_29;
               from Key_29 to Stand_by_Key2 on PLAY do Display_29, Goto_29;

     end K_2;

     or T_1:

          basic Reset_T_1;
          basic Time;
          basic Time_reset;

          defcon: Reset_T_1;

          transitions:
               from Reset_T_1 to Time_reset on CD;
               from Time to Time_reset on Timeout_1;
               from Time_reset to Time on TILBAGE do Time_1;

     end T_1;

     or Repeat_Control:

          basic Repeat_reset;
          basic Repeat_off;
          basic Repeat_on;

          defcon: Repeat_reset;

          transitions:
               from Repeat_reset to Repeat_off on CD;
               from Repeat_reset to Repeat_off on CD[in(TOP.Main.Stand_By)] do Start_laser, Start_motor;
               from Repeat_off to Repeat_on on REPEAT[!(in(TOP.T_3.Time3_reset))] do Display_ON;
               from Repeat_on to Repeat_off on REPEAT[!(in(TOP.T_3.Time3_reset))] do Display_OFF;

     end Repeat_Control;

     or T_3:

          basic Reset_T_3;
          basic Time3_reset;
          basic Time3;

          defcon: Reset_T_3;

          transitions:
               from Reset_T_3 to Time3_reset on CD;
               from Time3_reset to Time3 on RANDOM||REPEAT do Time_3;
               from Time3 to Time3_reset on Timeout_3 do Display_last_disp;

     end T_3;

     or Random_Control:

          basic Random_reset;
          basic Random_off;
          basic Random_on;

          defcon: Random_reset;

          transitions:
               from Random_reset to Random_off on CD;
               from Random_reset to Random_off on CD[in(TOP.Main.Stand_By)] do Start_laser, Start_motor;
               from Random_off to Random_on on RANDOM[!(in(TOP.T_3.Time3_reset))] do Display_ON;
               from Random_on to Random_off on RANDOM[!(in(TOP.T_3.Time3_reset))] do Display_OFF;

     end Random_Control;

     or K_1:

          basic Stand_by_Key1;
          basic Reset_K_1;
          basic Key_1;
          basic Key_11;
          basic Key_10;
          basic Key_12;
          basic Key_13;
          basic Key_14;
          basic Key_15;
          basic Key_16;
          basic Key_17;
          basic Key_18;
          basic Key_19;

          defcon: Reset_K_1;

          transitions:
               from Stand_by_Key1 to Stand_by_Key1 on NotOk;
               from Stand_by_Key1 to Key_1 on Key_1_Pressed[(!(in(TOP.K_0.Key_0))&&!(in(TOP.K_2.Key_2)))&&!(in(TOP.K_3.Key_3))] do Display__1;
               from Reset_K_1 to Stand_by_Key1 on CD;
               from Key_1 to Key_11 on Key_1_Pressed[(!(in(TOP.K_0.Key_0))&&!(in(TOP.K_2.Key_2)))&&!(in(TOP.K_3.Key_3))] do Display_11;
               from Key_1 to Key_10 on Key_0_Pressed do Display_10;
               from Key_1 to Key_12 on Key_2_Pressed do Display_12;
               from Key_1 to Key_13 on Key_3_Pressed do Display_13;
               from Key_1 to Key_14 on Key_4_Pressed do Display_14;
               from Key_1 to Key_15 on Key_5_Pressed do Display_15;
               from Key_1 to Key_16 on Key_6_Pressed do Display_16;
               from Key_1 to Key_17 on Key_7_Pressed do Display_17;
               from Key_1 to Key_18 on Key_8_Pressed do Display_18;
               from Key_1 to Key_19 on Key_9_Pressed do Display_19;
               from Key_1 to Stand_by_Key1 on Timeout_2 do Display_1, Goto_1;
               from Key_1 to Stand_by_Key1 on PLAY do Display_1, Goto_1;
               from Key_11 to Stand_by_Key1 on Timeout_2 do Display_11, Goto_11;
               from Key_11 to Stand_by_Key1 on PLAY do Display_11, Goto_11;
               from Key_10 to Stand_by_Key1 on Timeout_2 do Display_10, Goto_10;
               from Key_10 to Stand_by_Key1 on PLAY do Display_10, Goto_10;
               from Key_12 to Stand_by_Key1 on Timeout_2 do Display_12, Goto_12;
               from Key_12 to Stand_by_Key1 on PLAY do Display_12, Goto_12;
               from Key_13 to Stand_by_Key1 on Timeout_2 do Display_13, Goto_13;
               from Key_13 to Stand_by_Key1 on PLAY do Display_13, Goto_13;
               from Key_14 to Stand_by_Key1 on Timeout_2 do Display_14, Goto_14;
               from Key_14 to Stand_by_Key1 on PLAY do Display_14, Goto_14;
               from Key_15 to Stand_by_Key1 on Timeout_2 do Display_15, Goto_15;
               from Key_15 to Stand_by_Key1 on PLAY do Display_15, Goto_15;
               from Key_16 to Stand_by_Key1 on Timeout_2 do Display_16, Goto_16;
               from Key_16 to Stand_by_Key1 on PLAY do Display_16, Goto_16;
               from Key_17 to Stand_by_Key1 on Timeout_2 do Display_17, Goto_17;
               from Key_17 to Stand_by_Key1 on PLAY do Display_17, Goto_17;
               from Key_18 to Stand_by_Key1 on Timeout_2 do Display_18, Goto_18;
               from Key_18 to Stand_by_Key1 on PLAY do Display_18, Goto_18;
               from Key_19 to Stand_by_Key1 on Timeout_2 do Display_19, Goto_19;
               from Key_19 to Stand_by_Key1 on PLAY do Display_19, Goto_19;

     end K_1;

     or T_4:

          basic Reset_T_4;
          basic Time4_reset;
          basic Time4;

          defcon: Reset_T_4;

          transitions:
               from Reset_T_4 to Time4_reset on CD;
               from Time4_reset to Time4 on NotOk do Time_4;
               from Time4 to Time4_reset on Timeout_4 do Display_last_disp;

     end T_4;

     or Rewind:

          basic Rewind_reset;
          basic Rewind_ready;

          defcon: Rewind_reset;

          transitions:
               from Rewind_reset to Rewind_ready on CD;
               from Rewind_reset to Rewind_ready on NotOk;

     end Rewind;

     or Forward:

          basic Forward_reset;
          basic Forward_ready;

          defcon: Forward_reset;

          transitions:
               from Forward_reset to Forward_ready on CD;
               from Forward_reset to Forward_ready on NotOk;

     end Forward;

     or Main:

          basic Stand_By;
          basic Spinning;
          basic Spoler_frem;
          basic Spoler_tilbage;
          basic Pause_cd;
          basic Mute_cd;
          basic CD_End;

          defcon: Stand_By;

          transitions:
               from Stand_By to Spinning on CD do Start_laser, Start_motor;
               from Spinning to Spoler_frem on SPOL_FREM do Laser_forward;
               from Spinning to Spoler_tilbage on SPOL_TILBAGE do Laser_reverse;
               from Spinning to Pause_cd on PAUSE do Musik_ud_stop, Laser_hold;
               from Spinning to Mute_cd on MUTE do Musik_ud_stop;
               from Spinning to Stand_By on OFF do Musik_ud_stop, Stop_motor, Stop_laser;
               from Spinning to CD_End on Lead_Out[in(TOP.Random_Control.Random_off)&&in(TOP.Repeat_Control.Repeat_off)] do Display_SIGN, Musik_ud_stop, Stop_laser, Stop_motor, Time_1800;
               from Spinning to CD_End on Random_Done[in(TOP.Repeat_Control.Repeat_off)] do Time_1800, Stop_motor, Display_SIGN, Stop_laser, Musik_ud_stop;
               from Spoler_frem to Spinning on PLAY do Laser_normal;
               from Spoler_tilbage to Spinning on PLAY do Laser_normal;
               from Pause_cd to Spinning on PLAY do Laser_normal, Musik_ud;
               from Pause_cd to Spinning on PAUSE do Laser_normal, Musik_ud;
               from Mute_cd to Spinning on MUTE do Musik_ud;
               from CD_End to Spinning on PLAY do Start_motor, Start_laser;
               from CD_End to Stand_By on Timeout_1800 do Display_Nothing;

     end Main;


end TOP;

