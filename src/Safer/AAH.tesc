# Global.Safer.P1.AAH
# berechnet AAH_cmd und AAH_State

events: EngageReady, SET_clock;

bvars: starting;

or AAH:
	basic AAH_Start;
	basic engage;
	basic Starting;
	basic active_axes;
	basic AAH_state_ignore_HCM;
	ref AAH_control_out in AAH_control_out.tesc type tesc;
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
		from AAH_state_toggle to AAH_state_timeout on [!(in(Global.AAH_state_toggle.ON_) && in(Global.Safer.P1.AAH.engage.PONCE))]; 
		from AAH_state_timeout to AAH_END on ~ do P1Ready;
end AAH;
