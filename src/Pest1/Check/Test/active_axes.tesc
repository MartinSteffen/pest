# TESC-File generiert durch TESC-Export
# Formatierung der Transitionlabel geht beim Export verloren.
# Transitionlabels sind i.a. vollstaendig geklammert (bis auf aeussere Klammern)

or ACTIVE_AXES:

     and ACTIVE_AXES_ACTIVE_AXES:

          or YAW:

               and nicht:

                    or FST:

                         basic B1;
                         basic B3;
                         basic B2;

                         defcon: B1;

                         transitions:
                              from B1 to B3 on [!(in(AAH_OFF))];
                              from B1 to B2 on [in(AAH_OFF)];

                    end FST;

                    or SCND:

                         basic B5;
                         basic B4;

                         defcon: B4;

                         transitions:
                              from B4 to B5 on [!((in(CMD.YAW.ZERO)||in(IGNORE.YAW.ON)))&&in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.YAW.nicht.FST.B2)];

                    end SCND;


               end nicht;

               or OFF:

                    basic A2;
                    basic A1;

                    defcon: A1;

                    transitions:
                         from A1 to A2 on [in(AAH_OFF)];

               end OFF;


               defcon: OFF;

               transitions:
                    from nicht to OFF on [in(CMD.YAW.ZERO)||in(IGNORE.YAW.ON)];
                    from nicht to OFF on [(in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.YAW.nicht.FST.B2)&&!(in(AAH_STARTED)))&&(OK_TO_CHANGE&&in(AAH_OFF))];
                    from nicht to OFF on [in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.YAW.nicht.FST.B3)&&(OK_TO_CHANGE&&in(AAH_OFF))];
                    from OFF to nicht on [!((in(CMD.YAW.ZERO)||in(IGNORE.YAW.ON)))&&OK_TO_CHANGE];
                    from OFF to nicht on [(in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.YAW.OFF.A1)&&in(AAH_STARTED))&&OK_TO_CHANGE];

          end YAW;

          or ROLL:

               and ROLL_ON:

                    or ROLL_ON_SCND:

                         basic ROLL_ON_SCND_B5;
                         basic ROLL_ON_SCND_B4;

                         defcon: ROLL_ON_SCND_B4;

                         transitions:
                              from ROLL_ON_SCND_B4 to ROLL_ON_SCND_B5 on [!((in(CMD.ROLL.ZERO)||in(IGNORE.ROLL.ON)))&&in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.ROLL.ROLL_ON.ROLL_ON_FST.ROLL_ON_FST_B2)];

                    end ROLL_ON_SCND;

                    or ROLL_ON_FST:

                         basic ROLL_ON_FST_B1;
                         basic ROLL_ON_FST_B3;
                         basic ROLL_ON_FST_B2;

                         defcon: ROLL_ON_FST_B1;

                         transitions:
                              from ROLL_ON_FST_B1 to ROLL_ON_FST_B3 on [!(in(AAH_OFF))];
                              from ROLL_ON_FST_B1 to ROLL_ON_FST_B2 on [in(AAH_OFF)];

                    end ROLL_ON_FST;


               end ROLL_ON;

               or ROLL_OFF:

                    basic ROLL_OFF_A2;
                    basic ROLL_OFF_A1;

                    defcon: ROLL_OFF_A1;

                    transitions:
                         from ROLL_OFF_A1 to ROLL_OFF_A2 on [in(AAH_OFF)];

               end ROLL_OFF;


               defcon: ROLL_OFF;

               transitions:
                    from ROLL_ON to ROLL_OFF on [in(CMD.ROLL.ZERO)||in(IGNORE.ROLL.ON)];
                    from ROLL_ON to ROLL_OFF on [(in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.ROLL.ROLL_ON.ROLL_ON_FST.ROLL_ON_FST_B2)&&!(in(AAH_STARTED)))&&(OK_TO_CHANGE&&in(AAH_OFF))];
                    from ROLL_ON to ROLL_OFF on [in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.ROLL.ROLL_ON.ROLL_ON_FST.ROLL_ON_FST_B3)&&(OK_TO_CHANGE&&in(AAH_OFF))];
                    from ROLL_OFF to ROLL_ON on [!((in(CMD.ROLL.ZERO)||in(IGNORE.ROLL.ON)))&&OK_TO_CHANGE];
                    from ROLL_OFF to ROLL_ON on [(in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.ROLL.ROLL_OFF.ROLL_OFF_A1)&&in(AAH_STARTED))&&OK_TO_CHANGE];

          end ROLL;

          or PITCH:

               and PITCH_ON:

                    or PITCH_ON_FST:

                         basic PITCH_ON_FST_B1;
                         basic PITCH_ON_FST_B3;
                         basic PITCH_ON_FST_B2;

                         defcon: PITCH_ON_FST_B1;

                         transitions:
                              from PITCH_ON_FST_B1 to PITCH_ON_FST_B3 on [!(in(AAH_OFF))];
                              from PITCH_ON_FST_B1 to PITCH_ON_FST_B2 on [in(AAH_OFF)];

                    end PITCH_ON_FST;

                    or PITCH_ON_SCND:

                         basic PITCH_ON_SCND_B5;
                         basic PITCH_ON_SCND_B4;

                         defcon: PITCH_ON_SCND_B4;

                         transitions:
                              from PITCH_ON_SCND_B4 to PITCH_ON_SCND_B5 on [!((in(CMD.PITCH.ZERO)||in(IGNORE.PITCH.ON)))&&in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.PITCH.PITCH_ON.PITCH_ON_FST.PITCH_ON_FST_B2)];

                    end PITCH_ON_SCND;


               end PITCH_ON;

               or PITCH_OFF:

                    basic PITCH_OFF_A2;
                    basic PITCH_OFF_A1;

                    defcon: PITCH_OFF_A1;

                    transitions:
                         from PITCH_OFF_A1 to PITCH_OFF_A2 on [in(AAH_OFF)];

               end PITCH_OFF;


               defcon: PITCH_OFF;

               transitions:
                    from PITCH_ON to PITCH_OFF on [in(CMD.PITCH.ZERO)||in(IGNORE.PITCH.ON)];
                    from PITCH_ON to PITCH_OFF on [(in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.PITCH.PITCH_ON.PITCH_ON_FST.PITCH_ON_FST_B2)&&!(in(AAH_STARTED)))&&(OK_TO_CHANGE&&in(AAH_OFF))];
                    from PITCH_ON to PITCH_OFF on [in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.PITCH.PITCH_ON.PITCH_ON_FST.PITCH_ON_FST_B3)&&(OK_TO_CHANGE&&in(AAH_OFF))];
                    from PITCH_OFF to PITCH_ON on [!((in(CMD.PITCH.ZERO)||in(IGNORE.PITCH.ON)))&&OK_TO_CHANGE];
                    from PITCH_OFF to PITCH_ON on [(in(ACTIVE_AXES.ACTIVE_AXES_ACTIVE_AXES.PITCH.PITCH_OFF.PITCH_OFF_A1)&&in(AAH_STARTED))&&OK_TO_CHANGE];

          end PITCH;


     end ACTIVE_AXES_ACTIVE_AXES;


     defcon: ACTIVE_AXES_ACTIVE_AXES;

end ACTIVE_AXES;

