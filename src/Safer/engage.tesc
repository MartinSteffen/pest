# Global.Safer.P1.AAH.engage

events: EngageReady, SET_engage;
bvars: AAH_state_active_axes_pitch, AAH_state_active_axes_roll, AAH_state_active_axes_yaw;

or engage:
        # input:
        #       AAH_control_button
        #       AAH_state_active_axes_XXX
        #       clock
        # output: engage

        basic init;
        basic OFF;
        basic STARTED;
        basic ON_;
        basic PONCE;
        basic CLOSING;
        basic PTWICE;
        
        defcon: init;

        transitions:
	from init to OFF;

	from OFF to STARTED on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;
	from OFF to OFF on SET_engage[in(Global.AAH_control_button.UP)] do EngageReady;

	from STARTED to ON_ on SET_engage[in(Global.AAH_control_button.UP)] do EngageReady;
	from STARTED to STARTED on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;

	from ON_ to OFF on SET_engage[in(Global.AAH_control_button.UP) && !AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw] do EngageReady;
	from ON_ to PONCE on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;
	from ON_ to ON_ on SET_engage[!((in(Global.AAH_control_button.UP) && !AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw) || (in(Global.AAH_control_button.DOWN)))] do EngageReady;

	from PONCE to CLOSING on SET_engage[in(Global.AAH_control_button.UP)] do EngageReady;
	from PONCE to PONCE on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;

	from CLOSING to PTWICE on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;
	from CLOSING to OFF on SET_engage[in(Global.AAH_control_button.UP) && !AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw] do EngageReady;
	from CLOSING to ON_ on SET_engage[in(Global.AAH_control_button.UP) && !(!AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw) && in(Global.clock.TIMEOUT)] do EngageReady;
	from CLOSING to CLOSING on SET_engage[in(Global.AAH_control_button.UP) && !(!AAH_state_active_axes_roll && !AAH_state_active_axes_pitch && !AAH_state_active_axes_yaw) && !in(Global.clock.TIMEOUT)] do EngageReady;

	from PTWICE to OFF on SET_engage[in(Global.AAH_control_button.UP)] do EngageReady;
	from PTWICE to PTWICE on SET_engage[in(Global.AAH_control_button.DOWN)] do EngageReady;

end engage;
