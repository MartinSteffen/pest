# Global.Safer.P1.Grip
events:
	SET_grip_cmd_x_0,
	SET_grip_cmd_x_POS,
	SET_grip_cmd_x_NEG,
	SET_grip_cmd_y_0,
	SET_grip_cmd_y_POS,
	SET_grip_cmd_y_NEG,
	SET_grip_cmd_z_0,
	SET_grip_cmd_z_POS,
	SET_grip_cmd_z_NEG,
	SET_grip_cmd_pitch_0,
	SET_grip_cmd_pitch_POS,
	SET_grip_cmd_pitch_NEG,
	SET_grip_cmd_roll_0,
	SET_grip_cmd_roll_POS,
	SET_grip_cmd_roll_NEG,
	SET_grip_cmd_yaw_0,
	SET_grip_cmd_yaw_POS,
	SET_grip_cmd_yaw_NEG,
	P1_1Ready;
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
