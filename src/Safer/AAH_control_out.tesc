# Global.Safer.P1.AAH.AAH_control_out
events:
	SET_AAH_cmd_roll_0,
	SET_AAH_cmd_roll_POS,
	SET_AAH_cmd_roll_NEG,
	SET_AAH_cmd_pitch_0,
	SET_AAH_cmd_pitch_POS,
	SET_AAH_cmd_pitch_NEG,
	SET_AAH_cmd_yaw_0,
	SET_AAH_cmd_yaw_POS,
	SET_AAH_cmd_yaw_NEG,
	AAH_cmd_End;

bvars:
	AAH_state_active_axes_roll,
	AAH_state_active_axes_pitch,
	AAH_state_active_axes_yaw;

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
