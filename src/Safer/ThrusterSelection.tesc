# Global.Safer.P2.ThrusterSelection

# enthaltene Statecharts:
# All_axes_off
# Not_All_axes_off
# All_Thr_off
# BF_thrusters
# LRUD_thrusters
# BF_opt_delete
# LRUD_opt_delete
events: P2Ready, LRUD_opt_delete_Ready, BF_opt_delete_Ready;


or ThrusterSelection:
	or Integrated_commands:
		basic Start;
		basic All_axes_off;
		basic Not_All_axes_off;
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
