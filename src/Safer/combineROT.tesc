# Global.Safer.P2.ThrusterSelection.Integrated_commands.NOT_All_axes_off.combineROT

events: CombineROTReady;

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