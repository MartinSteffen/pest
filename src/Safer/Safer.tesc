# ES FEHLT NOCH DAS FREEZEN DER SCHALTER
# events: P1Ready, P2Ready;

and Global:
	or Safer:
	        	basic Init;
		ref P1 in P1.tesc type tesc;
			# Phase 1 berechnet Grip-Command und AAH-command

		ref P2 in P2.tesc type tesc;
			# Phase 2 mischt die beiden Commands und produziert einen entsprechenden Thruster-Output
        
		defcon: Init;
 
		transitions:
			from Init to P1 on;
			from P1 to P2 on P1Ready;
			from P2 to P1 on P2Ready;
	end Safer;

	or raw_grip_pos_vert:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_raw_grip_pos_vert_0;
		from POS to NEG on SET_raw_grip_pos_vert_NEG;
		from POS to POS on SET_raw_grip_pos_vert_POS;
		from 0 to 0 on SET_raw_grip_pos_vert_0;
		from 0 to NEG on SET_raw_grip_pos_vert_NEG;
		from 0 to POS on SET_raw_grip_pos_vert_POS;
		from NEG to 0 on SET_raw_grip_pos_vert_0;
		from NEG to NEG on SET_raw_grip_pos_vert_NEG;
		from NEG to POS on SET_raw_grip_pos_vert_POS;
	end raw_grip_pos_vert;

	or raw_grip_pos_trans:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_raw_grip_pos_trans_0;
		from POS to NEG on SET_raw_grip_pos_trans_NEG;
		from POS to POS on SET_raw_grip_pos_trans_POS;
		from 0 to 0 on SET_raw_grip_pos_trans_0;
		from 0 to NEG on SET_raw_grip_pos_trans_NEG;
		from 0 to POS on SET_raw_grip_pos_trans_POS;
		from NEG to 0 on SET_raw_grip_pos_trans_0;
		from NEG to NEG on SET_raw_grip_pos_trans_NEG;
		from NEG to POS on SET_raw_grip_pos_trans_POS;
	end raw_grip_pos_trans;


	or raw_grip_pos_horiz:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_raw_grip_pos_horiz_0;
		from POS to NEG on SET_raw_grip_pos_horiz_NEG;
		from POS to POS on SET_raw_grip_pos_horiz_POS;
		from 0 to 0 on SET_raw_grip_pos_horiz_0;
		from 0 to NEG on SET_raw_grip_pos_horiz_NEG;
		from 0 to POS on SET_raw_grip_pos_horiz_POS;
		from NEG to 0 on SET_raw_grip_pos_horiz_0;
		from NEG to NEG on SET_raw_grip_pos_horiz_NEG;
		from NEG to POS on SET_raw_grip_pos_horiz_POS;
	end raw_grip_pos_horiz;

	or raw_grip_pos_twist:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_raw_grip_pos_twist_0;
		from POS to NEG on SET_raw_grip_pos_twist_NEG;
		from POS to POS on SET_raw_grip_pos_twist_POS;
		from 0 to 0 on SET_raw_grip_pos_twist_0;
		from 0 to NEG on SET_raw_grip_pos_twist_NEG;
		from 0 to POS on SET_raw_grip_pos_twist_POS;
		from NEG to 0 on SET_raw_grip_pos_twist_0;
		from NEG to NEG on SET_raw_grip_pos_twist_NEG;
		from NEG to POS on SET_raw_grip_pos_twist_POS;
	end raw_grip_pos_twist;

	or grip_cmd_x:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_grip_cmd_x_0;
		from POS to NEG on SET_grip_cmd_x_NEG;
		from POS to POS on SET_grip_cmd_x_POS;
		from 0 to 0 on SET_grip_cmd_x_0;
		from 0 to NEG on SET_grip_cmd_x_NEG;
		from 0 to POS on SET_grip_cmd_x_POS;
		from NEG to 0 on SET_grip_cmd_x_0;
		from NEG to NEG on SET_grip_cmd_x_NEG;
		from NEG to POS on SET_grip_cmd_x_POS;
	end grip_cmd_x;

	or grip_cmd_y:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_grip_cmd_y_0;
		from POS to NEG on SET_grip_cmd_y_NEG;
		from POS to POS on SET_grip_cmd_y_POS;
		from 0 to 0 on SET_grip_cmd_y_0;
		from 0 to NEG on SET_grip_cmd_y_NEG;
		from 0 to POS on SET_grip_cmd_y_POS;
		from NEG to 0 on SET_grip_cmd_y_0;
		from NEG to NEG on SET_grip_cmd_y_NEG;
		from NEG to POS on SET_grip_cmd_y_POS;
	end grip_cmd_y;

	or grip_cmd_z:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_grip_cmd_z_0;
		from POS to NEG on SET_grip_cmd_z_NEG;
		from POS to POS on SET_grip_cmd_z_POS;
		from 0 to 0 on SET_grip_cmd_z_0;
		from 0 to NEG on SET_grip_cmd_z_NEG;
		from 0 to POS on SET_grip_cmd_z_POS;
		from NEG to 0 on SET_grip_cmd_z_0;
		from NEG to NEG on SET_grip_cmd_z_NEG;
		from NEG to POS on SET_grip_cmd_z_POS;
	end grip_cmd_z;

	or grip_cmd_roll:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_grip_cmd_roll_0;
		from POS to NEG on SET_grip_cmd_roll_NEG;
		from POS to POS on SET_grip_cmd_roll_POS;
		from 0 to 0 on SET_grip_cmd_roll_0;
		from 0 to NEG on SET_grip_cmd_roll_NEG;
		from 0 to POS on SET_grip_cmd_roll_POS;
		from NEG to 0 on SET_grip_cmd_roll_0;
		from NEG to NEG on SET_grip_cmd_roll_NEG;
		from NEG to POS on SET_grip_cmd_roll_POS;
	end grip_cmd_roll;

	or grip_cmd_pitch:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_grip_cmd_pitch_0;
		from POS to NEG on SET_grip_cmd_pitch_NEG;
		from POS to POS on SET_grip_cmd_pitch_POS;
		from 0 to 0 on SET_grip_cmd_pitch_0;
		from 0 to NEG on SET_grip_cmd_pitch_NEG;
		from 0 to POS on SET_grip_cmd_pitch_POS;
		from NEG to 0 on SET_grip_cmd_pitch_0;
		from NEG to NEG on SET_grip_cmd_pitch_NEG;
		from NEG to POS on SET_grip_cmd_pitch_POS;
	end grip_cmd_pitch;

	or grip_cmd_yaw:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_grip_cmd_yaw_0;
		from POS to NEG on SET_grip_cmd_yaw_NEG;
		from POS to POS on SET_grip_cmd_yaw_POS;
		from 0 to 0 on SET_grip_cmd_yaw_0;
		from 0 to NEG on SET_grip_cmd_yaw_NEG;
		from 0 to POS on SET_grip_cmd_yaw_POS;
		from NEG to 0 on SET_grip_cmd_yaw_0;
		from NEG to NEG on SET_grip_cmd_yaw_NEG;
		from NEG to POS on SET_grip_cmd_yaw_POS;
	end grip_cmd_yaw;

	or AAH_cmd_roll:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_AAH_cmd_roll_0;
		from POS to NEG on SET_AAH_cmd_roll_NEG;
		from POS to POS on SET_AAH_cmd_roll_POS;
		from 0 to 0 on SET_AAH_cmd_roll_0;
		from 0 to NEG on SET_AAH_cmd_roll_NEG;
		from 0 to POS on SET_AAH_cmd_roll_POS;
		from NEG to 0 on SET_AAH_cmd_roll_0;
		from NEG to NEG on SET_AAH_cmd_roll_NEG;
		from NEG to POS on SET_AAH_cmd_roll_POS;
	end AAH_cmd_roll;

	or AAH_cmd_pitch:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_AAH_cmd_pitch_0;
		from POS to NEG on SET_AAH_cmd_pitch_NEG;
		from POS to POS on SET_AAH_cmd_pitch_POS;
		from 0 to 0 on SET_AAH_cmd_pitch_0;
		from 0 to NEG on SET_AAH_cmd_pitch_NEG;
		from 0 to POS on SET_AAH_cmd_pitch_POS;
		from NEG to 0 on SET_AAH_cmd_pitch_0;
		from NEG to NEG on SET_AAH_cmd_pitch_NEG;
		from NEG to POS on SET_AAH_cmd_pitch_POS;
	end AAH_cmd_pitch;

	or AAH_cmd_yaw:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_AAH_cmd_yaw_0;
		from POS to NEG on SET_AAH_cmd_yaw_NEG;
		from POS to POS on SET_AAH_cmd_yaw_POS;
		from 0 to 0 on SET_AAH_cmd_yaw_0;
		from 0 to NEG on SET_AAH_cmd_yaw_NEG;
		from 0 to POS on SET_AAH_cmd_yaw_POS;
		from NEG to 0 on SET_AAH_cmd_yaw_0;
		from NEG to NEG on SET_AAH_cmd_yaw_NEG;
		from NEG to POS on SET_AAH_cmd_yaw_POS;
	end AAH_cmd_yaw;

	# Die folgende States sind fÅr das Speichern des Integrated Commands
	or IC_x:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_IC_x_0;
		from POS to NEG on SET_IC_x_NEG;
		from POS to POS on SET_IC_x_POS;
		from 0 to 0 on SET_IC_x_0;
		from 0 to NEG on SET_IC_x_NEG;
		from 0 to POS on SET_IC_x_POS;
		from NEG to 0 on SET_IC_x_0;
		from NEG to NEG on SET_IC_x_NEG;
		from NEG to POS on SET_IC_x_POS;
	end IC_x;

	or IC_y:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_IC_y_0;
		from POS to NEG on SET_IC_y_NEG;
		from POS to POS on SET_IC_y_POS;
		from 0 to 0 on SET_IC_y_0;
		from 0 to NEG on SET_IC_y_NEG;
		from 0 to POS on SET_IC_y_POS;
		from NEG to 0 on SET_IC_y_0;
		from NEG to NEG on SET_IC_y_NEG;
		from NEG to POS on SET_IC_y_POS;
	end IC_y;

	or IC_z:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_IC_z_0;
		from POS to NEG on SET_IC_z_NEG;
		from POS to POS on SET_IC_z_POS;
		from 0 to 0 on SET_IC_z_0;
		from 0 to NEG on SET_IC_z_NEG;
		from 0 to POS on SET_IC_z_POS;
		from NEG to 0 on SET_IC_z_0;
		from NEG to NEG on SET_IC_z_NEG;
		from NEG to POS on SET_IC_z_POS;
	end IC_z;

	or IC_roll:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_IC_roll_0;
		from POS to NEG on SET_IC_roll_NEG;
		from POS to POS on SET_IC_roll_POS;
		from 0 to 0 on SET_IC_roll_0;
		from 0 to NEG on SET_IC_roll_NEG;
		from 0 to POS on SET_IC_roll_POS;
		from NEG to 0 on SET_IC_roll_0;
		from NEG to NEG on SET_IC_roll_NEG;
		from NEG to POS on SET_IC_roll_POS;
	end IC_roll;

	or IC_pitch:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_IC_pitch_0;
		from POS to NEG on SET_IC_pitch_NEG;
		from POS to POS on SET_IC_pitch_POS;
		from 0 to 0 on SET_IC_pitch_0;
		from 0 to NEG on SET_IC_pitch_NEG;
		from 0 to POS on SET_IC_pitch_POS;
		from NEG to 0 on SET_IC_pitch_0;
		from NEG to NEG on SET_IC_pitch_NEG;
		from NEG to POS on SET_IC_pitch_POS;
	end IC_pitch;

	or IC_yaw:
		basic INIT;
		basic POS;
		basic 0;
		basic NEG;
		defcon: INIT;
		transitions:
		from INIT to 0 on;
		from POS to 0 on SET_IC_yaw_0;
		from POS to NEG on SET_IC_yaw_NEG;
		from POS to POS on SET_IC_yaw_POS;
		from 0 to 0 on SET_IC_yaw_0;
		from 0 to NEG on SET_IC_yaw_NEG;
		from 0 to POS on SET_IC_yaw_POS;
		from NEG to 0 on SET_IC_yaw_0;
		from NEG to NEG on SET_IC_yaw_NEG;
		from NEG to POS on SET_IC_yaw_POS;
	end IC_yaw;

	or control_mode_switch:
		basic INIT;
		basic ROT;
		basic TRAN;
		defcon: INIT;
		transitions:
		from INIT to ROT;
		from ROT to TRAN on SET_control_mode_switch_TRAN;
		from TRAN to ROT on SET_control_mode_switch_ROT;
	end control_mode_switch;

	or AAH_control_button:
		basic INIT;
		basic DOWN;
		basic UP;
		defcon: INIT;
		from INIT to UP;
		from UP to DOWN on SET_AAH_control_button_DOWN;
		from DOWN to UP on SET_AAH_control_button_UP;
	end AAH_control_button;

	or AAH_state_toggle:
	        basic init;
	        basic OFF;
	        basic STARTED;
	        basic ON;
	        basic PONCE;
	        basic CLOSING;
	        basic PTWICE;
	
	        defcon: init;
	
	        transitions:
	                from init to OFF; # initialwert.
	
	                from OFF to STARTED on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.STARTED)];
	                from OFF to ON on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.ON)];
	                from OFF to PONCE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PONCE)];
	                from OFF to CLOSING on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.CLOSING)];
	                from OFF to PTWICE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PTWICE)];
	                from OFF to OFF on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.OFF)];
	
	                from STARTED to STARTED on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.STARTED)];
	                from STARTED to ON on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.ON)];
	                from STARTED to PONCE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PONCE)];
	                from STARTED to CLOSING on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.CLOSING)];
	                from STARTED to PTWICE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PTWICE)];
	                from STARTED to OFF on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.OFF)];
	
	                from ON to STARTED on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.STARTED)];
	                from ON to ON on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.ON)];
	                from ON to PONCE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PONCE)];
	                from ON to CLOSING on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.CLOSING)];
	                from ON to PTWICE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PTWICE)];
	                from ON to OFF on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.OFF)];
	
	                from PONCE to STARTED on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.STARTED)];
	                from PONCE to ON on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.ON)];
	                from PONCE to PONCE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PONCE)];
	                from PONCE to CLOSING on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.CLOSING)];
	                from PONCE to PTWICE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PTWICE)];
	                from PONCE to OFF on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.OFF)];
	
	                from CLOSING to STARTED on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.STARTED)];
	                from CLOSING to ON on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.ON)];
	                from CLOSING to PONCE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PONCE)];
	                from CLOSING to CLOSING on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.CLOSING)];
	                from CLOSING to PTWICE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PTWICE)];
	                from CLOSING to OFF on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.OFF)];
	
	               from PTWICE to STARTED on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.STARTED)];
	               from PTWICE to ON on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.ON)];
	                from PTWICE to PONCE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PONCE)];
	                from PTWICE to CLOSING on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.CLOSING)];
	                from PTWICE to PTWICE on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.PTWICE)];
	                from PTWICE to OFF on SET_AAH_state_toggle[in(Global.Safer.P1.AAH.engage.OFF)];
	
	end AAH_state_toggle;

	# Global.Clock
	# Clock, f¸r timeout beim Berechnen des AAH-State-toggle
	# muﬂ auﬂerhalb des Safer liegen
	# wird mit jedem Zyklus gez‰hlt, wenn gestartet.
	
	
	or Clock:
		basic init;
		basic OFF;
		basic TIMEOUT;
		basic START;
		basic STEP1;
		basic STEP2;
		basic STEP3;
	
		defcon: init;
		transitions:
			from init to OFF;
			from OFF to START on SET_clock;
			from START to STEP1 on P2Ready;
			from STEP1 to STEP2 on P2Ready;
			from STEP2 to STEP3 on P2Ready;
			from STEP3 to TIMEOUT on P2Ready;
	end Clock;
	
end Global;