# TESC-File generiert durch TESC-Export
# Formatierung der Transitionlabel geht beim Export verloren.
# Transitionlabels sind i.a. vollstaendig geklammert (bis auf aeussere Klammern)

or TV:

     or TV_TV:

          basic STANDBY;
          and WORKING:

               or WORKING_SOUND:

                    basic MUTE;
                    basic SOUND_ON;

                    defcon: MUTE;

                    transitions:
                         from MUTE to SOUND_ON on SOUND;
                         from SOUND_ON to MUTE on MUTE;

               end WORKING_SOUND;

               or DISPLAY:

                    and SHOW:

                         or SM_CONTROL:

                              basic SM_CONTROL_SOUND;
                              basic REST;

                              defcon: REST;

                              transitions:
                                   from SM_CONTROL_SOUND to REST on MUTE;
                                   from REST to SM_CONTROL_SOUND  do SOUND;

                         end SM_CONTROL;

                         or CHANNELS:

                              basic CH2;
                              basic CH1;

                              defcon: CH1;

                              transitions:
                                   from CH2 to CH1 on ONE do MUTE;
                                   from CH2 to CH2 on TWO do MUTE;
                                   from CH1 to CH2 on TWO do MUTE;
                                   from CH1 to CH1 on ONE do MUTE;

                         end CHANNELS;


                    end SHOW;

                    basic VIDEOTEXT;

                    defcon: SHOW;

                    transitions:
                         from SHOW to VIDEOTEXT on TXT;
                         from VIDEOTEXT to SHOW on TXT;

               end DISPLAY;


          end WORKING;


          defcon: WORKING;

          transitions:
               from STANDBY to WORKING on TV_ON;
               from WORKING to STANDBY on TV_OFF;

     end TV_TV;


     defcon: TV_TV;

end TV;

