# Global.Safer.P2.ThrusterSelection.Integrated_commands.All_axes_off

events: AaoReady,
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
		# ÅberflÅssig: from tmp5 to IC_set on [in(Global.grip_cmd_y.0)] do SET_IC_y_0;
		from tmp5 to IC_set on [in(Global.grip_cmd_y.POS)] do SET_IC_y_POS;
		from tmp5 to IC_set on [in(Global.grip_cmd_y.NEG)] do SET_IC_y_NEG;

		# Berechnung von IC_x
		from NO_ROT to tmp6 on [!in(Global.grip_cmd_x.0)] do SET_IC_y_0, SET_IC_z_0;
		# ÅberflÅssig: from tmp6 to IC_set on [in(Global.grip_cmd_x.0)] do SET_IC_x_0;
		from tmp6 to IC_set on [in(Global.grip_cmd_x.POS)] do SET_IC_x_POS;
		from tmp6 to IC_set on [in(Global.grip_cmd_x.NEG)] do SET_IC_x_NEG;

		from IC_set to All_axes_off_end on ~ do AaoReady;
end All_axes_off;
