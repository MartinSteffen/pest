# Global.Safer.P2.ThrusterSelection.Integrated_commands.NOT_All_axes_off
events: NAaoReday,
	CombineROTReady,
	SET_IC_x_0,
	SET_IC_x_POS,
	SET_IC_x_NEG,
	SET_IC_y_0,
	SET_IC_y_POS,
	SET_IC_y_NEG,
	SET_IC_z_0,
	SET_IC_z_POS,
	SET_IC_z_NEG,
	SET_IC_pitch_0,
	SET_IC_pitch_POS,
	SET_IC_pitch_NEG,
	SET_IC_roll_0,
	SET_IC_roll_POS,
	SET_IC_roll_NEG,
	SET_IC_yaw_0,
	SET_IC_yaw_POS,
	SET_IC_yaw_NEG;

or NOT_All_axes_off:
	basic Start;
	ref CombineROT in CombineROT.tesc type tesc;
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
		# überflüssig: from tmp5 to IC_set on [in(Global.grip_cmd_y.0)] do SET_IC_y_0;
		from tmp5 to IC_set on [in(Global.grip_cmd_y.POS)] do SET_IC_y_POS;
		from tmp5 to IC_set on [in(Global.grip_cmd_y.NEG)] do SET_IC_y_NEG;

		# Berechnung von IC_x
		from NO_ROT to tmp6 on [!in(Global.grip_cmd_x.0)] do SET_IC_y_0, SET_IC_z_0;
		# überflüssig: from tmp6 to IC_set on [in(Global.grip_cmd_x.0)] do SET_IC_x_0;
		from tmp6 to IC_set on [in(Global.grip_cmd_x.POS)] do SET_IC_x_POS;
		from tmp6 to IC_set on [in(Global.grip_cmd_x.NEG)] do SET_IC_x_NEG;

		from IC_set to NOT_All_axes_off_end on ~ do NAaoReady;
end NOT_All_axes_off;
