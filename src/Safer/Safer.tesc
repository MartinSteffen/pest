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
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_raw_grip_pos_vert_0;
		from POS to NEG on SET_raw_grip_pos_vert_NEG;
		# from POS to POS on SET_raw_grip_pos_vert_POS;
		# from 0 to 0 on SET_raw_grip_pos_vert_0;
		from 0 to NEG on SET_raw_grip_pos_vert_NEG;
		from 0 to POS on SET_raw_grip_pos_vert_POS;
		from NEG to 0 on SET_raw_grip_pos_vert_0;
		# from NEG to NEG on SET_raw_grip_pos_vert_NEG;
		from NEG to POS on SET_raw_grip_pos_vert_POS;
	end raw_grip_pos_vert;

	or raw_grip_pos_trans:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_raw_grip_pos_trans_0;
		from POS to NEG on SET_raw_grip_pos_trans_NEG;
		# from POS to POS on SET_raw_grip_pos_trans_POS;
		# from 0 to 0 on SET_raw_grip_pos_trans_0;
		from 0 to NEG on SET_raw_grip_pos_trans_NEG;
		from 0 to POS on SET_raw_grip_pos_trans_POS;
		from NEG to 0 on SET_raw_grip_pos_trans_0;
		# from NEG to NEG on SET_raw_grip_pos_trans_NEG;
		from NEG to POS on SET_raw_grip_pos_trans_POS;
	end raw_grip_pos_trans;


	or raw_grip_pos_horiz:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_raw_grip_pos_horiz_0;
		from POS to NEG on SET_raw_grip_pos_horiz_NEG;
		# from POS to POS on SET_raw_grip_pos_horiz_POS;
		# from 0 to 0 on SET_raw_grip_pos_horiz_0;
		from 0 to NEG on SET_raw_grip_pos_horiz_NEG;
		from 0 to POS on SET_raw_grip_pos_horiz_POS;
		from NEG to 0 on SET_raw_grip_pos_horiz_0;
		# from NEG to NEG on SET_raw_grip_pos_horiz_NEG;
		from NEG to POS on SET_raw_grip_pos_horiz_POS;
	end raw_grip_pos_horiz;

	or raw_grip_pos_twist:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_raw_grip_pos_twist_0;
		from POS to NEG on SET_raw_grip_pos_twist_NEG;
		# from POS to POS on SET_raw_grip_pos_twist_POS;
		# from 0 to 0 on SET_raw_grip_pos_twist_0;
		from 0 to NEG on SET_raw_grip_pos_twist_NEG;
		from 0 to POS on SET_raw_grip_pos_twist_POS;
		from NEG to 0 on SET_raw_grip_pos_twist_0;
		# from NEG to NEG on SET_raw_grip_pos_twist_NEG;
		from NEG to POS on SET_raw_grip_pos_twist_POS;
	end raw_grip_pos_twist;

	or grip_cmd_x:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_grip_cmd_x_0;
		from POS to NEG on SET_grip_cmd_x_NEG;
		# from POS to POS on SET_grip_cmd_x_POS;
		# from 0 to 0 on SET_grip_cmd_x_0;
		from 0 to NEG on SET_grip_cmd_x_NEG;
		from 0 to POS on SET_grip_cmd_x_POS;
		from NEG to 0 on SET_grip_cmd_x_0;
		# from NEG to NEG on SET_grip_cmd_x_NEG;
		from NEG to POS on SET_grip_cmd_x_POS;
	end grip_cmd_x;

	or grip_cmd_y:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_grip_cmd_y_0;
		from POS to NEG on SET_grip_cmd_y_NEG;
		# from POS to POS on SET_grip_cmd_y_POS;
		# from 0 to 0 on SET_grip_cmd_y_0;
		from 0 to NEG on SET_grip_cmd_y_NEG;
		from 0 to POS on SET_grip_cmd_y_POS;
		from NEG to 0 on SET_grip_cmd_y_0;
		# from NEG to NEG on SET_grip_cmd_y_NEG;
		from NEG to POS on SET_grip_cmd_y_POS;
	end grip_cmd_y;

	or grip_cmd_z:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_grip_cmd_z_0;
		from POS to NEG on SET_grip_cmd_z_NEG;
		# from POS to POS on SET_grip_cmd_z_POS;
		# from 0 to 0 on SET_grip_cmd_z_0;
		from 0 to NEG on SET_grip_cmd_z_NEG;
		from 0 to POS on SET_grip_cmd_z_POS;
		from NEG to 0 on SET_grip_cmd_z_0;
		# from NEG to NEG on SET_grip_cmd_z_NEG;
		from NEG to POS on SET_grip_cmd_z_POS;
	end grip_cmd_z;

	or grip_cmd_roll:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_grip_cmd_roll_0;
		from POS to NEG on SET_grip_cmd_roll_NEG;
		# from POS to POS on SET_grip_cmd_roll_POS;
		# from 0 to 0 on SET_grip_cmd_roll_0;
		from 0 to NEG on SET_grip_cmd_roll_NEG;
		from 0 to POS on SET_grip_cmd_roll_POS;
		from NEG to 0 on SET_grip_cmd_roll_0;
		# from NEG to NEG on SET_grip_cmd_roll_NEG;
		from NEG to POS on SET_grip_cmd_roll_POS;
	end grip_cmd_roll;

	or grip_cmd_pitch:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_grip_cmd_pitch_0;
		from POS to NEG on SET_grip_cmd_pitch_NEG;
		# from POS to POS on SET_grip_cmd_pitch_POS;
		# from 0 to 0 on SET_grip_cmd_pitch_0;
		from 0 to NEG on SET_grip_cmd_pitch_NEG;
		from 0 to POS on SET_grip_cmd_pitch_POS;
		from NEG to 0 on SET_grip_cmd_pitch_0;
		# from NEG to NEG on SET_grip_cmd_pitch_NEG;
		from NEG to POS on SET_grip_cmd_pitch_POS;
	end grip_cmd_pitch;

	or grip_cmd_yaw:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_grip_cmd_yaw_0;
		from POS to NEG on SET_grip_cmd_yaw_NEG;
		# from POS to POS on SET_grip_cmd_yaw_POS;
		# from 0 to 0 on SET_grip_cmd_yaw_0;
		from 0 to NEG on SET_grip_cmd_yaw_NEG;
		from 0 to POS on SET_grip_cmd_yaw_POS;
		from NEG to 0 on SET_grip_cmd_yaw_0;
		# from NEG to NEG on SET_grip_cmd_yaw_NEG;
		from NEG to POS on SET_grip_cmd_yaw_POS;
	end grip_cmd_yaw;

	or AAH_cmd_roll:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_AAH_cmd_roll_0;
		from POS to NEG on SET_AAH_cmd_roll_NEG;
		# from POS to POS on SET_AAH_cmd_roll_POS;
		# from 0 to 0 on SET_AAH_cmd_roll_0;
		from 0 to NEG on SET_AAH_cmd_roll_NEG;
		from 0 to POS on SET_AAH_cmd_roll_POS;
		from NEG to 0 on SET_AAH_cmd_roll_0;
		# from NEG to NEG on SET_AAH_cmd_roll_NEG;
		from NEG to POS on SET_AAH_cmd_roll_POS;
	end AAH_cmd_roll;

	or AAH_cmd_pitch:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_AAH_cmd_pitch_0;
		from POS to NEG on SET_AAH_cmd_pitch_NEG;
		# from POS to POS on SET_AAH_cmd_pitch_POS;
		# from 0 to 0 on SET_AAH_cmd_pitch_0;
		from 0 to NEG on SET_AAH_cmd_pitch_NEG;
		from 0 to POS on SET_AAH_cmd_pitch_POS;
		from NEG to 0 on SET_AAH_cmd_pitch_0;
		# from NEG to NEG on SET_AAH_cmd_pitch_NEG;
		from NEG to POS on SET_AAH_cmd_pitch_POS;
	end AAH_cmd_pitch;

	or AAH_cmd_yaw:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_AAH_cmd_yaw_0;
		from POS to NEG on SET_AAH_cmd_yaw_NEG;
		# from POS to POS on SET_AAH_cmd_yaw_POS;
		# from 0 to 0 on SET_AAH_cmd_yaw_0;
		from 0 to NEG on SET_AAH_cmd_yaw_NEG;
		from 0 to POS on SET_AAH_cmd_yaw_POS;
		from NEG to 0 on SET_AAH_cmd_yaw_0;
		# from NEG to NEG on SET_AAH_cmd_yaw_NEG;
		from NEG to POS on SET_AAH_cmd_yaw_POS;
	end AAH_cmd_yaw;

	# Die folgende States sind für das Speichern des Integrated Commands
	or IC_x:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_IC_x_0;
		from POS to NEG on SET_IC_x_NEG;
		# from POS to POS on SET_IC_x_POS;
		# from 0 to 0 on SET_IC_x_0;
		from 0 to NEG on SET_IC_x_NEG;
		from 0 to POS on SET_IC_x_POS;
		from NEG to 0 on SET_IC_x_0;
		# from NEG to NEG on SET_IC_x_NEG;
		from NEG to POS on SET_IC_x_POS;
	end IC_x;

	or IC_y:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_IC_y_0;
		from POS to NEG on SET_IC_y_NEG;
		# from POS to POS on SET_IC_y_POS;
		# from 0 to 0 on SET_IC_y_0;
		from 0 to NEG on SET_IC_y_NEG;
		from 0 to POS on SET_IC_y_POS;
		from NEG to 0 on SET_IC_y_0;
		# from NEG to NEG on SET_IC_y_NEG;
		from NEG to POS on SET_IC_y_POS;
	end IC_y;

	or IC_z:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_IC_z_0;
		from POS to NEG on SET_IC_z_NEG;
		# from POS to POS on SET_IC_z_POS;
		# from 0 to 0 on SET_IC_z_0;
		from 0 to NEG on SET_IC_z_NEG;
		from 0 to POS on SET_IC_z_POS;
		from NEG to 0 on SET_IC_z_0;
		# from NEG to NEG on SET_IC_z_NEG;
		from NEG to POS on SET_IC_z_POS;
	end IC_z;

	or IC_roll:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_IC_roll_0;
		from POS to NEG on SET_IC_roll_NEG;
		# from POS to POS on SET_IC_roll_POS;
		# from 0 to 0 on SET_IC_roll_0;
		from 0 to NEG on SET_IC_roll_NEG;
		from 0 to POS on SET_IC_roll_POS;
		from NEG to 0 on SET_IC_roll_0;
		# from NEG to NEG on SET_IC_roll_NEG;
		from NEG to POS on SET_IC_roll_POS;
	end IC_roll;

	or IC_pitch:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_IC_pitch_0;
		from POS to NEG on SET_IC_pitch_NEG;
		# from POS to POS on SET_IC_pitch_POS;
		# from 0 to 0 on SET_IC_pitch_0;
		from 0 to NEG on SET_IC_pitch_NEG;
		from 0 to POS on SET_IC_pitch_POS;
		from NEG to 0 on SET_IC_pitch_0;
		# from NEG to NEG on SET_IC_pitch_NEG;
		from NEG to POS on SET_IC_pitch_POS;
	end IC_pitch;

	or IC_yaw:
		basic POS;
		basic 0;
		basic NEG;
		defcon: 0;
		transitions:
		from POS to 0 on SET_IC_yaw_0;
		from POS to NEG on SET_IC_yaw_NEG;
		# from POS to POS on SET_IC_yaw_POS;
		# from 0 to 0 on SET_IC_yaw_0;
		from 0 to NEG on SET_IC_yaw_NEG;
		from 0 to POS on SET_IC_yaw_POS;
		from NEG to 0 on SET_IC_yaw_0;
		# from NEG to NEG on SET_IC_yaw_NEG;
		from NEG to POS on SET_IC_yaw_POS;
	end IC_yaw;

	or control_mode_switch:
		basic ROT;
		basic TRAN;
		defcon: ROT;
		transitions:
		from ROT to TRAN on SET_control_mode_switch_TRAN;
		from TRAN to ROT on SET_control_mode_switch_ROT;
	end control_mode_switch;

	or AAH_control_button:
		basic DOWN;
		basic UP;
		defcon: UP;
		transitions:
		from UP to DOWN on SET_AAH_control_button_DOWN;
		from DOWN to UP on SET_AAH_control_button_UP;
	end AAH_control_button;

	or engage:
	        # input:
	        #       AAH_control_button
	        #       AAH_state_active_axes_XXX
	        #       clock
	        # output: engage
	
	        basic OFF;
	        basic STARTED;
	        basic ON_;
	        basic PONCE;
	        basic CLOSING;
	        basic PTWICE;
        
	        defcon: OFF;

	        transitions:
		from OFF to STARTED on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;
		# from OFF to OFF on SET_engage[in(Global.AAH_control_button.UP)] do EngageReady;
	
		from STARTED to ON_ on SET_engage[in(Global.AAH_control_button.UP)] do EngageReady;
		# from STARTED to STARTED on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;

		from ON_ to OFF on SET_engage[in(Global.AAH_control_button.UP) && !AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw] do EngageReady;
		from ON_ to PONCE on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;
		# from ON_ to ON_ on SET_engage[!((in(Global.AAH_control_button.UP) && !AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw) || (in(Global.AAH_control_button.DOWN)))] do EngageReady;

		from PONCE to CLOSING on SET_engage[in(Global.AAH_control_button.UP)] do EngageReady;
		# from PONCE to PONCE on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;

		from CLOSING to PTWICE on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;
		from CLOSING to OFF on SET_engage[in(Global.AAH_control_button.UP) && !AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw] do EngageReady;
		from CLOSING to ON_ on SET_engage[in(Global.AAH_control_button.UP) && !(!AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw) && in(Global.clock.TIMEOUT)] do EngageReady;
		# from CLOSING to CLOSING on SET_engage[in(Global.AAH_control_button.UP) && !(!AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw) && !in(Global.clock.TIMEOUT)] do EngageReady;

		from PTWICE to OFF on SET_engage[in(Global.AAH_control_button.UP)] do EngageReady;
		# from PTWICE to PTWICE on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;

	end engage;

	or AAH_state_toggle:
	        basic OFF;
	        basic STARTED;
	        basic ON_;
	        basic PONCE;
	        basic CLOSING;
	        basic PTWICE;

	        defcon: OFF;

	        transitions:

	                from OFF to STARTED on SET_AAH_state_toggle[in(Global.engage.STARTED)];
	                from OFF to ON_ on SET_AAH_state_toggle[in(Global.engage.ON_)];
	                from OFF to PONCE on SET_AAH_state_toggle[in(Global.engage.PONCE)];
	                from OFF to CLOSING on SET_AAH_state_toggle[in(Global.engage.CLOSING)];
	                from OFF to PTWICE on SET_AAH_state_toggle[in(Global.engage.PTWICE)];
	                # from OFF to OFF on SET_AAH_state_toggle[in(Global.engage.OFF)];
	
	                # from STARTED to STARTED on SET_AAH_state_toggle[in(Global.engage.STARTED)];
	                from STARTED to ON_ on SET_AAH_state_toggle[in(Global.engage.ON_)];
	                from STARTED to PONCE on SET_AAH_state_toggle[in(Global.engage.PONCE)];
	                from STARTED to CLOSING on SET_AAH_state_toggle[in(Global.engage.CLOSING)];
	                from STARTED to PTWICE on SET_AAH_state_toggle[in(Global.engage.PTWICE)];
	                from STARTED to OFF on SET_AAH_state_toggle[in(Global.engage.OFF)];
	
	                from ON_ to STARTED on SET_AAH_state_toggle[in(Global.engage.STARTED)];
	                # from ON_ to ON_ on SET_AAH_state_toggle[in(Global.engage.ON_)];
	                from ON_ to PONCE on SET_AAH_state_toggle[in(Global.engage.PONCE)];
	                from ON_ to CLOSING on SET_AAH_state_toggle[in(Global.engage.CLOSING)];
	                from ON_ to PTWICE on SET_AAH_state_toggle[in(Global.engage.PTWICE)];
	                from ON_ to OFF on SET_AAH_state_toggle[in(Global.engage.OFF)];
	
	                from PONCE to STARTED on SET_AAH_state_toggle[in(Global.engage.STARTED)];
	                from PONCE to ON_ on SET_AAH_state_toggle[in(Global.engage.ON_)];
	                # from PONCE to PONCE on SET_AAH_state_toggle[in(Global.engage.PONCE)];
	                from PONCE to CLOSING on SET_AAH_state_toggle[in(Global.engage.CLOSING)];
	                from PONCE to PTWICE on SET_AAH_state_toggle[in(Global.engage.PTWICE)];
	                from PONCE to OFF on SET_AAH_state_toggle[in(Global.engage.OFF)];
	
	                from CLOSING to STARTED on SET_AAH_state_toggle[in(Global.engage.STARTED)];
	                from CLOSING to ON_ on SET_AAH_state_toggle[in(Global.engage.ON_)];
	                from CLOSING to PONCE on SET_AAH_state_toggle[in(Global.engage.PONCE)];
	                # from CLOSING to CLOSING on SET_AAH_state_toggle[in(Global.engage.CLOSING)];
	                from CLOSING to PTWICE on SET_AAH_state_toggle[in(Global.engage.PTWICE)];
	                from CLOSING to OFF on SET_AAH_state_toggle[in(Global.engage.OFF)];
	
	                from PTWICE to STARTED on SET_AAH_state_toggle[in(Global.engage.STARTED)];
	                from PTWICE to ON_ on SET_AAH_state_toggle[in(Global.engage.ON_)];
	                from PTWICE to PONCE on SET_AAH_state_toggle[in(Global.engage.PONCE)];
	                from PTWICE to CLOSING on SET_AAH_state_toggle[in(Global.engage.CLOSING)];
	                # from PTWICE to PTWICE on SET_AAH_state_toggle[in(Global.engage.PTWICE)];
	                from PTWICE to OFF on SET_AAH_state_toggle[in(Global.engage.OFF)];

	end AAH_state_toggle;

	or clock:
		basic OFF;
		basic TIMEOUT;
		basic START;
		basic STEP1;
		basic STEP2;
		basic STEP3;

		defcon: OFF;
		transitions:
			from OFF to START on SET_clock;
			from START to STEP1 on P2Ready;
			from STEP1 to STEP2 on P2Ready;
			from STEP2 to STEP3 on P2Ready;
			from STEP3 to TIMEOUT on P2Ready;
	end clock;

end Global;