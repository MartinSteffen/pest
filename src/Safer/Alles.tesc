# ES FEHLT NOCH DAS FREEZEN DER SCHALTER
# events: P1Ready, P2Ready;

and Global:
	or Safer:
# P1.tesc ------------------------------------------------------
# Global.Safer.P1
# Es fehlt noch das freezen der Schalter, damit eine énderung der Schalter wÑhrend eines Zyklus keinen Einfluò auf die Berechnung hat.

# events: P1_1Ready;

or P1:
	basic init;

# AAH.tesc -----------------------------------------------------
# Global.Safer.P1.AAH
# berechnet AAH_cmd und AAH_State

# events: EngageReady, SET_clock;

# bvars: starting;

or AAH:
	basic AAH_Start;

# AAH_control_out.tesc -----------------------------------------
# Global.Safer.P1.AAH.AAH_control_out
# events:
#	SET_AAH_cmd_roll_0,
#	SET_AAH_cmd_roll_POS,
#	SET_AAH_cmd_roll_NEG,
#	SET_AAH_cmd_pitch_0,
#	SET_AAH_cmd_pitch_POS,
#	SET_AAH_cmd_pitch_NEG,
#	SET_AAH_cmd_yaw_0,
#	SET_AAH_cmd_yaw_POS,
#	SET_AAH_cmd_yaw_NEG,
#	AAH_cmd_End;

# bvars:
#	AAH_state_active_axes_roll,
#	AAH_state_active_axes_pitch,
#	AAH_state_active_axes_yaw;

or AAH_control_out:
# berechnet ein AAH_cmd

	or roll:
		basic init;
		basic roll_start;
		basic roll;
		basic roll_end;

		defcon: init;
		transitions:
			from init to roll_start on ~ do SET_AAH_cmd_roll_0;
			from roll_start to roll_end on [AAH_state_active_axes_roll] do ROLLEnd;
			from roll_start to roll on [!AAH_state_active_axes_roll];
			from roll to roll_end on [in(Global.IRU_roll.POS)] do SET_AAH_cmd_roll_NEG, ROLLEnd;
			from roll to roll_end on [in(Global.IRU_roll.NEG)] do SET_AAH_cmd_roll_POS, ROLLEnd;
			from roll to roll_end on [in(Global.IRU_roll.0)] do ROLLEnd;
	end roll;


	or pitch:
		basic init;
		basic pitch_start;
		basic pitch;
		basic pitch_end;

		defcon: init;
		transitions:
			from init to pitch_start on ~ do
							SET_AAH_cmd_pitch_0;
			from pitch_start to pitch_end on [AAH_state_active_axes_pitch] do PITCHEnd;
			from pitch_start to pitch on [!AAH_state_active_axes_pitch];
			from pitch to pitch_end on [in(Global.IRU_pitch.POS)] do SET_AAH_cmd_pitch_NEG, PITCHEnd;
			from pitch to pitch_end on [in(Global.IRU_pitch.NEG)] do SET_AAH_cmd_pitch_POS, PITCHEnd;
			from pitch to pitch_end on [in(Global.IRU_pitch.0)] do PITCHEnd;
	end pitch;


	or yaw:
		basic init;
		basic yaw_start;
		basic yaw;
		basic yaw_end;

		defcon: init;
		transitions:
			from init to yaw_start on ~ do
							SET_AAH_cmd_yaw_0;
			from yaw_start to yaw_end on [AAH_state_active_axes_yaw] do AAH_cmd_End;
			from yaw_start to yaw on [!AAH_state_active_axes_yaw];
			from yaw to yaw_end on [in(Global.IRU_yaw.POS)] do SET_AAH_cmd_yaw_NEG, AAH_cmd_End;
			from yaw to yaw_end on [in(Global.IRU_yaw.NEG)] do SET_AAH_cmd_yaw_POS, AAH_cmd_End;
			from yaw to yaw_end on [in(Global.IRU_yaw.0)] do AAH_cmd_End;
	end yaw;


	defcon: roll;

	transitions:
		from roll to pitch on ROLLEnd;
		from pitch to yaw on PITCHEnd;

end AAH_control_out;
# AAH_control_out.tesc Ende --------------------------------------------

	basic engage;
	basic Starting;
	basic active_axes;
	basic AAH_state_ignore_HCM;
#	ref AAH_control_out in AAH_control_out.tesc type tesc;
	basic AAH_state_toggle;
	basic AAH_state_timeout;
	basic AAH_END;

	defcon: AAH_Start;

	transitions:
		from AAH_Start to engage on ~ do SET_engage;
		from engage to Starting on EngageReady do starting := in(Global.AAH_state_toggle.OFF) && in(Global.engage.STARTED);
		from Starting to active_axes on ~ do
					AAH_state_active_axes_roll := starting || (!in(Global.engage.OFF) && AAH_state_active_axes_roll  && in(Global.grip_cmd_roll.0)  || AAH_state_ignore_HCM_roll),
					AAH_state_active_axes_pitch := starting || (!in(Global.engage.OFF) && AAH_state_active_axes_pitch  && in(Global.grip_cmd_pitch.0) || AAH_state_ignore_HCM_pitch),
					AAH_state_active_axes_yaw := starting || (!in(Global.engage.OFF) && AAH_state_active_axes_yaw  && in(Global.grip_cmd_yaw.0) || AAH_state_ignore_HCM_yaw);
		from active_axes to AAH_state_ignore_HCM on [starting] do
					AAH_state_ignore_HCM_roll := !in(Global.grip_cmd_roll.0),
					AAH_state_ignore_HCM_pitch:= !in(Global.grip_cmd_pitch.0),
					AAH_state_ignore_HCM_yaw := !in(Global.grip_cmd_yaw.0);
		from active_axes to AAH_state_ignore_HCM on [!starting];
		from AAH_state_ignore_HCM to AAH_control_out;
		from AAH_control_out to AAH_state_toggle on AAH_cmd_End do SET_AAH_state_toggle;
		from AAH_state_toggle to AAH_state_timeout on [in(Global.AAH_state_toggle.ON_) && in(Global.engage.PONCE)] do SET_clock; # starte den Timer
		from AAH_state_toggle to AAH_state_timeout on [!(in(Global.AAH_state_toggle.ON_) && in(Global.engage.PONCE))]; 
		from AAH_state_timeout to AAH_END on ~ do P1Ready;
end AAH;
# AAH.tesc Ende -----------------------------------------------

# Grip.tesc ---------------------------------------------------
# Global.Safer.P1.Grip
# events:
#	SET_grip_cmd_x_0,
#	SET_grip_cmd_x_POS,
#	SET_grip_cmd_x_NEG,
#	SET_grip_cmd_y_0,
#	SET_grip_cmd_y_POS,
#	SET_grip_cmd_y_NEG,
#	SET_grip_cmd_z_0,
#	SET_grip_cmd_z_POS,
#	SET_grip_cmd_z_NEG,
#	SET_grip_cmd_pitch_0,
#	SET_grip_cmd_pitch_POS,
#	SET_grip_cmd_pitch_NEG,
#	SET_grip_cmd_roll_0,
#	SET_grip_cmd_roll_POS,
#	SET_grip_cmd_roll_NEG,
#	SET_grip_cmd_yaw_0,
#	SET_grip_cmd_yaw_POS,
#	SET_grip_cmd_yaw_NEG,
#	P1_1Ready;
or Grip:
	# berechnet ein gueltiges Grip-Command
		basic Init;
		basic tmp1; # wird fuer die Fallunterscheidung der Werte einer Achse benutzt
		basic tmp2; # wird fuer die Fallunterscheidung der Werte einer Achse benutzt
		basic tmp3; # wird fuer die Fallunterscheidung der Werte einer Achse benutzt
		basic tmp4; # wird fuer die Fallunterscheidung der Werte einer Achse benutzt
		basic tmp5; # wird fuer die Fallunterscheidung der Werte einer Achse benutzt
		basic mode_check;
		basic GripEnd;

		defcon: Init;

		transitions:
			# die folgenden 6 Zeilen bilden
			# 	Init to mode_check on ~ do grip_cmd_x := raw_grip_pos_horiz
			# ab.
			from Init to tmp1 on [in(Global.raw_grip_pos_horiz.POS)] do SET_grip_cmd_x_POS;
			from Init to tmp1 on [in(Global.raw_grip_pos_horiz.0)] do SET_grip_cmd_x_0;
			from Init to tmp1 on [in(Global.raw_grip_pos_horiz.NEG)] do SET_grip_cmd_x_NEG;
			from tmp1 to mode_check on [in(Global.raw_grip_pos_twist.POS)] do SET_grip_cmd_pitch_POS;
			from tmp1 to mode_check on [in(Global.raw_grip_pos_twist.0)] do SET_grip_cmd_pitch_0;
			from tmp1 to mode_check on [in(Global.raw_grip_pos_twist.NEG)] do SET_grip_cmd_pitch_NEG;

			# der folgende Block bildet
			#	from mode_check to GripEnd on [control_mode_switch=ROT] do
			#		grip_cmd_y:=0,
			#		grip_cmd_z:=0,
			#		grip_cmd_roll:=raw_grip_pos_vert,
			#		grip_cmd_yaw:=raw_grip_pos_trans,
			#		P1_1Ready;
			# ab.
			from mode_check to tmp2 on [in(Global.control_mode_switch.ROT)] do
				SET_grip_cmd_y_0,
				SET_grip_cmd_z_0;
			from tmp2 to tmp3 on [in(Global.raw_grip_pos_vert.POS)] do SET_grip_cmd_roll_POS;
			from tmp2 to tmp3 on [in(Global.raw_grip_pos_vert.0)] do SET_grip_cmd_roll_0;
			from tmp2 to tmp3 on [in(Global.raw_grip_pos_vert.NEG)] do SET_grip_cmd_roll_NEG;
			from tmp3 to GripEnd on [in(Global.raw_grip_pos_trans.POS)] do SET_grip_cmd_yaw_POS, P1_1Ready;
			from tmp3 to GripEnd on [in(Global.raw_grip_pos_trans.0)] do SET_grip_cmd_yaw_0, P1_1Ready;
			from tmp3 to GripEnd on [in(Global.raw_grip_pos_trans.NEG)] do SET_grip_cmd_yaw_NEG, P1_1Ready;

			# der folgende Block bildet
			#	from mode_check to GripEnd on [!(control_mode_switch=ROT)] do
			#		grip_cmd_y:=raw_grip_pos_trans,
			#		grip_cmd_z:=raw_grip_pos_vert,
			#		grip_cmd_roll:=0,
			#		grip_cmd_yaw:=0,
			#		P1_1Ready;
			# ab.
			from mode_check to tmp4 on [!in(Global.control_mode_switch.ROT)] do
				SET_grip_cmd_roll_0,
				SET_grip_cmd_yaw_0;
			from tmp4 to tmp5 on [in(Global.raw_grip_pos_trans.POS)] do SET_grip_cmd_y_POS;
			from tmp4 to tmp5 on [in(Global.raw_grip_pos_trans.0)] do SET_grip_cmd_y_0;
			from tmp4 to tmp5 on [in(Global.raw_grip_pos_trans.NEG)] do SET_grip_cmd_y_NEG;
			from tmp5 to GripEnd on [in(Global.raw_grip_pos_vert.POS)] do SET_grip_cmd_z_POS, P1_1Ready;
			from tmp5 to GripEnd on [in(Global.raw_grip_pos_vert.0)] do SET_grip_cmd_z_0, P1_1Ready;
			from tmp5 to GripEnd on [in(Global.raw_grip_pos_vert.NEG)] do SET_grip_cmd_z_NEG, P1_1Ready;
end Grip;
# Grip.tesc Ende ----------------------------------------------
#	ref Grip in Grip.tesc type tesc;
#	ref AAH in AAH.tesc type tesc;

	defcon: init;

	transitions:
		from init to Grip on; # Hier fehlt noch das freezen der Schalter
		from Grip to AAH on P1_1Ready;
end P1;
# P1.tesc Ende ------------------------------------------------

# P2.tesc -----------------------------------------------------
# Global.Safer.P2

# events: P2Ready;

or P2:
	basic init;
# ThrusterSelection.tesc --------------------------------------

# Global.Safer.P2.ThrusterSelection

# enthaltene Statecharts:
# All_axes_off
# Not_All_axes_off
# All_Thr_off
# BF_thrusters
# LRUD_thrusters
# BF_opt_delete
# LRUD_opt_delete
# events: P2Ready, LRUD_opt_delete_Ready, BF_opt_delete_Ready;


or ThrusterSelection:
	or Integrated_commands:
		basic Start;
# All_axes_off.tesc -------------------------------------------
# Global.Safer.P2.ThrusterSelection.Integrated_commands.All_axes_off

# events: AaoReady,
#	SET_IC_x_0,
#	SET_IC_x_POS,
#	SET_IC_x_NEG,
#	SET_IC_y_0,
#	SET_IC_y_POS,
#	SET_IC_y_NEG,
#	SET_IC_z_0,
#	SET_IC_z_POS,
#	SET_IC_z_NEG,
#	SET_IC_pitch_0,
#	SET_IC_pitch_POS,
#	SET_IC_pitch_NEG,
#	SET_IC_roll_0,
#	SET_IC_roll_POS,
#	SET_IC_roll_NEG,
#	SET_IC_yaw_0,
#	SET_IC_yaw_POS,
#	SET_IC_yaw_NEG;

or All_axes_off:
	basic Start;
	basic tmp1;
	basic tmp2;
	basic tmp3;
	basic tmp4;
	basic tmp5;
	basic tmp6;
	basic NO_ROT;
	basic IC_set;
	basic All_axes_off_end;

	defcon: Start;

	transitions:
		from Start to tmp1 on [!in(Global.grip_cmd_roll.0) || !in(Global.grip_cmd_pitch.0) || !in(Global.grip_cmd_yaw.0)] do SET_IC_x_0, SET_IC_y_0, SET_IC_z_0;
		# Berechnung von IC_roll
		from tmp1 to tmp2 on [in(Global.grip_cmd_roll.0)] do SET_IC_roll_0;
		from tmp1 to tmp2 on [in(Global.grip_cmd_roll.NEG)] do SET_IC_roll_NEG;
		from tmp1 to tmp2 on [in(Global.grip_cmd_roll.POS)] do SET_IC_roll_POS;
		#Berechnung von IC_pitch
		from tmp2 to tmp3 on [in(Global.grip_cmd_pitch.0)] do SET_IC_pitch_0;
		from tmp2 to tmp3 on [in(Global.grip_cmd_pitch.NEG)] do SET_IC_pitch_NEG;
		from tmp2 to tmp3 on [in(Global.grip_cmd_pitch.POS)] do SET_IC_pitch_POS;
		#Berechnung von IC_yaw
		from tmp3 to IC_set on [in(Global.grip_cmd_yaw.0)] do SET_IC_yaw_0;
		from tmp3 to IC_set on [in(Global.grip_cmd_yaw.NEG)] do SET_IC_yaw_NEG;
		from tmp3 to IC_set on [in(Global.grip_cmd_yaw.POS)] do SET_IC_yaw_POS;
				
		from Start to NO_ROT on [!(!in(Global.grip_cmd_roll.0) || !in(Global.grip_cmd_pitch.0) || !in(Global.grip_cmd_yaw.0))] do SET_IC_roll_0, SET_IC_pitch_0, SET_IC_yaw_0;
		# Berechnung 

		# Berechnung von IC_z
		from NO_ROT to tmp4 on [in(Global.grip_cmd_x.0) && in(Global.grip_cmd_y.0)] do SET_IC_x_0, SET_IC_y_0;
		from tmp4 to IC_set on [in(Global.grip_cmd_z.0)] do SET_IC_z_0;
		from tmp4 to IC_set on [in(Global.grip_cmd_z.POS)] do SET_IC_z_POS;
		from tmp4 to IC_set on [in(Global.grip_cmd_z.NEG)] do SET_IC_z_NEG;

		# Berechnung von IC_y
		from NO_ROT to tmp5 on [in(Global.grip_cmd_x.0) && !in(Global.grip_cmd_y.0)] do SET_IC_x_0, SET_IC_z_0;
		# ¸berfl¸ssig: from tmp5 to IC_set on [in(Global.grip_cmd_y.0)] do SET_IC_y_0;
		from tmp5 to IC_set on [in(Global.grip_cmd_y.POS)] do SET_IC_y_POS;
		from tmp5 to IC_set on [in(Global.grip_cmd_y.NEG)] do SET_IC_y_NEG;

		# Berechnung von IC_x
		from NO_ROT to tmp6 on [!in(Global.grip_cmd_x.0)] do SET_IC_y_0, SET_IC_z_0;
		# ¸berfl¸ssig: from tmp6 to IC_set on [in(Global.grip_cmd_x.0)] do SET_IC_x_0;
		from tmp6 to IC_set on [in(Global.grip_cmd_x.POS)] do SET_IC_x_POS;
		from tmp6 to IC_set on [in(Global.grip_cmd_x.NEG)] do SET_IC_x_NEG;

		from IC_set to All_axes_off_end on ~ do AaoReady;
end All_axes_off;

# All_axes_off.tesc Ende --------------------------------------
# NOT_All_axes_off.tesc ---------------------------------------
# Global.Safer.P2.ThrusterSelection.Integrated_commands.NOT_All_axes_off
# events: NAaoReday,
#	CombineROTReady,
#	SET_IC_x_0,
#	SET_IC_x_POS,
#	SET_IC_x_NEG,
#	SET_IC_y_0,
#	SET_IC_y_POS,
#	SET_IC_y_NEG,
#	SET_IC_z_0,
#	SET_IC_z_POS,
#	SET_IC_z_NEG,
#	SET_IC_pitch_0,
#	SET_IC_pitch_POS,
#	SET_IC_pitch_NEG,
#	SET_IC_roll_0,
#	SET_IC_roll_POS,
#	SET_IC_roll_NEG,
#	SET_IC_yaw_0,
#	SET_IC_yaw_POS,
#	SET_IC_yaw_NEG;

or NOT_All_axes_off:
	basic Start;
# combineROT.tesc --------------------------------------------
# Global.Safer.P2.ThrusterSelection.Integrated_commands.NOT_All_axes_off.combineROT

# events: CombineROTReady;

or CombineROT:
	basic Start;
	basic roll;
	basic pitch;
	basic yaw;
	basic CombineROTend;
	basic use_AAH_cmd_roll;
	basic use_grip_cmd_roll;
	basic use_AAH_cmd_pitch;
	basic use_grip_cmd_pitch;
	basic use_AAH_cmd_yaw;
	basic use_grip_cmd_yaw;

	defcon: Start;

	transitions:
		# roll: entscheide, ob das AAH-Kommando oder das Grip-Kommando gilt.
		from Start to use_AAH_cmd_roll on [in(Global.grip_cmd_roll.0) || AAH_state_ignore_HCM_roll];
		from Start to use_grip_cmd_roll on [!(in(Global.grip_cmd_roll.0) || AAH_state_ignore_HCM_roll)];
		from use_AAH_cmd_roll to roll on [in(Global.AAH_cmd_roll.0)] do SET_IC_roll_0;
		from use_AAH_cmd_roll to roll on [in(Global.AAH_cmd_roll.POS)] do SET_IC_roll_POS;
		from use_AAH_cmd_roll to roll on [in(Global.AAH_cmd_roll.NEG)] do SET_IC_roll_NEG;
		from use_grip_cmd_roll to roll on [in(Global.grip_cmd_roll.0)] do SET_IC_roll_0;
		from use_grip_cmd_roll to roll on [in(Global.grip_cmd_roll.POS)] do SET_IC_roll_POS;
		from use_grip_cmd_roll to roll on [in(Global.grip_cmd_roll.NEG)] do SET_IC_roll_NEG;

		# pitch: entscheide, ob das AAH-Kommando oder das Grip-Kommando gilt.
		from Start to use_AAH_cmd_pitch on [in(Global.grip_cmd_pitch.0) || AAH_state_ignore_HCM_pitch];
		from Start to use_grip_cmd_pitch on [!(in(Global.grip_cmd_pitch.0) || AAH_state_ignore_HCM_pitch)];
		from use_AAH_cmd_pitch to pitch on [in(Global.AAH_cmd_pitch.0)] do SET_IC_pitch_0;
		from use_AAH_cmd_pitch to pitch on [in(Global.AAH_cmd_pitch.POS)] do SET_IC_pitch_POS;
		from use_AAH_cmd_pitch to pitch on [in(Global.AAH_cmd_pitch.NEG)] do SET_IC_pitch_NEG;
		from use_grip_cmd_pitch to pitch on [in(Global.grip_cmd_pitch.0)] do SET_IC_pitch_0;
		from use_grip_cmd_pitch to pitch on [in(Global.grip_cmd_pitch.POS)] do SET_IC_pitch_POS;
		from use_grip_cmd_pitch to pitch on [in(Global.grip_cmd_pitch.NEG)] do SET_IC_pitch_NEG;

		# yaw: entscheide, ob das AAH-Kommando oder das Grip-Kommando gilt.
		from Start to use_AAH_cmd_yaw on [in(Global.grip_cmd_yaw.0) || AAH_state_ignore_HCM_yaw];
		from Start to use_grip_cmd_yaw on [!(in(Global.grip_cmd_yaw.0) || AAH_state_ignore_HCM_yaw)];
		from use_AAH_cmd_yaw to yaw on [in(Global.AAH_cmd_yaw.0)] do SET_IC_yaw_0;
		from use_AAH_cmd_yaw to yaw on [in(Global.AAH_cmd_yaw.POS)] do SET_IC_yaw_POS;
		from use_AAH_cmd_yaw to yaw on [in(Global.AAH_cmd_yaw.NEG)] do SET_IC_yaw_NEG;
		from use_grip_cmd_yaw to yaw on [in(Global.grip_cmd_yaw.0)] do SET_IC_yaw_0;
		from use_grip_cmd_yaw to yaw on [in(Global.grip_cmd_yaw.POS)] do SET_IC_yaw_POS;
		from use_grip_cmd_yaw to yaw on [in(Global.grip_cmd_yaw.NEG)] do SET_IC_yaw_NEG;

		from yaw to CombineROTend on ~ do CombineROTReady;
end CombineROT;
# combineROT.tesc Ende ---------------------------------------
#	ref CombineROT in combineROT.tesc type tesc;
	basic tmp1;
	basic tmp2;
	basic tmp3;
	basic tmp4;
	basic tmp5;
	basic tmp6;
	basic NO_ROT;
	basic IC_set;
	basic NOT_All_axes_off_end;

	defcon: Start;

	transitions:
		from Start to CombineROT on [!in(Global.grip_cmd_roll.0) || !in(Global.grip_cmd_pitch.0) || !in(Global.grip_cmd_yaw.0)] do SET_IC_x_0, SET_IC_y_0, SET_IC_z_0;
		from CombineROT to IC_set on CombineROTReady;

		from Start to tmp1 on [!(!in(Global.grip_cmd_roll.0) || !in(Global.grip_cmd_pitch.0) || !in(Global.grip_cmd_yaw.0))];

		# Berechnung von IC_roll
		from tmp1 to tmp2 on [in(Global.grip_cmd_roll.0)] do SET_IC_roll_0;
		from tmp1 to tmp2 on [in(Global.grip_cmd_roll.POS)] do SET_IC_roll_POS;
		from tmp1 to tmp2 on [in(Global.grip_cmd_roll.NEG)] do SET_IC_roll_NEG;

		# Berechnung von IC_pitch
		from tmp2 to tmp3 on [in(Global.grip_cmd_pitch.0)] do SET_IC_pitch_0;
		from tmp2 to tmp3 on [in(Global.grip_cmd_pitch.POS)] do SET_IC_pitch_POS;
		from tmp2 to tmp3 on [in(Global.grip_cmd_pitch.NEG)] do SET_IC_pitch_NEG;

		# Berechnung von IC_yaw
		from tmp3 to NO_ROT on [in(Global.grip_cmd_yaw.0)] do SET_IC_yaw_0;
		from tmp3 to NO_ROT on [in(Global.grip_cmd_yaw.POS)] do SET_IC_yaw_POS;
		from tmp3 to NO_ROT on [in(Global.grip_cmd_yaw.NEG)] do SET_IC_yaw_NEG;

		# Berechnung von IC_z
		from NO_ROT to tmp4 on [in(Global.grip_cmd_x.0) && in(Global.grip_cmd_y.0)] do SET_IC_x_0, SET_IC_y_0;
		from tmp4 to IC_set on [in(Global.grip_cmd_z.0)] do SET_IC_z_0;
		from tmp4 to IC_set on [in(Global.grip_cmd_z.POS)] do SET_IC_z_POS;
		from tmp4 to IC_set on [in(Global.grip_cmd_z.NEG)] do SET_IC_z_NEG;

		# Berechnung von IC_y
		from NO_ROT to tmp5 on [in(Global.grip_cmd_x.0) && !in(Global.grip_cmd_y.0)] do SET_IC_x_0, SET_IC_z_0;
		# ÅberflÅssig: from tmp5 to IC_set on [in(Global.grip_cmd_y.0)] do SET_IC_y_0;
		from tmp5 to IC_set on [in(Global.grip_cmd_y.POS)] do SET_IC_y_POS;
		from tmp5 to IC_set on [in(Global.grip_cmd_y.NEG)] do SET_IC_y_NEG;

		# Berechnung von IC_x
		from NO_ROT to tmp6 on [!in(Global.grip_cmd_x.0)] do SET_IC_y_0, SET_IC_z_0;
		# ÅberflÅssig: from tmp6 to IC_set on [in(Global.grip_cmd_x.0)] do SET_IC_x_0;
		from tmp6 to IC_set on [in(Global.grip_cmd_x.POS)] do SET_IC_x_POS;
		from tmp6 to IC_set on [in(Global.grip_cmd_x.NEG)] do SET_IC_x_NEG;

		from IC_set to NOT_All_axes_off_end on ~ do NAaoReady;
end NOT_All_axes_off;

# NOT_All_axes_off.tesc Ende ----------------------------------


#		ref All_axes_off in All_axes_off.tesc type tesc;
#		ref Not_All_axes_off in NOT_All_axes_off.tesc type tesc;
		basic Integrated_commands_END;

		defcon: Start;

		transitions:
			from Start to All_axes_off on [!AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw];
			from Start to NOT_All_axes_off on [!(!AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw)];
			from All_axes_off to Integrated_commands_END on AaoReady do Int_cmd_Ready;
			from NOT_All_axes_off to Integrated_commands_END on NAaoReady do Int_cmd_Ready;
	end Integrated_commands;
		
	or Selected_thrusters:
		basic Start;
		basic All_Thr_off;
		basic BF_thrusters;
		basic LRUD_thrusters;
		basic All_thrusters;
		basic BF_opt_delete;
		basic BF_final;
		basic LRUD_final;
		basic LRUD_opt_delete;
		basic Ende;

		defcon: Start;

		transitions:
			from Start to All_Thr_off;
			from All_Thr_off to BF_thrusters on All_thr_off_Ready;
			from BF_thrusters to LRUD_thrusters on BF_thrusters_Ready;
			from LRUD_thrusters to All_thrusters on LRUD_thrusters_Ready;
			from All_thrusters to BF_opt_delete on [!in(Global.IC_roll.0)];
			from All_thrusters to BF_final on [in(Global.IC_roll.0)];
			from BF_opt_delete to BF_final on BF_opt_delete_Ready;
			from BF_final to LRUD_final on [in(Global.IC_pitch.0) && in(Global.IC_yaw.0)];
			from BF_final to LRUD_opt_delete on [!(in(Global.IC_pitch.0) && in(Global.IC_yaw.0))];
			from LRUD_opt_delete to LRUD_final on LRUD_opt_delete_Ready;
			from LRUD_final to Ende on ~ do P2Ready;
	end Selected_thrusters;

	defcon: Integrated_commands;
	transitions:
		from Integrated_commands to Selected_thrusters on Int_cmd_Ready;
end ThrusterSelection;
# ThrusterSelection.tesc Ende ---------------------------------
#	ref ThrusterSelection in ThrusterSelection.tesc type tesc;

	defcon: init;

	transitions:
		from init to ThrusterSelection on;
end P2;
# P2/tesc Ende ------------------------------------------------

 
	        	basic Init;
#		ref P1 in P1.tesc type tesc;
			# Phase 1 berechnet Grip-Command und AAH-command

#		ref P2 in P2.tesc type tesc;
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

	# Die folgende States sind f¸r das Speichern des Integrated Commands
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
