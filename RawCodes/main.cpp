#include <Windows.h>
#include <tchar.h>
#include "hardware.h"
#include "irdriver.h"
#include "Settings.h"
#include <conio.h>
#include <string.h>

int SHORT_SPACE = 550;
int LONG_SPACE = 1700;
int DELTA = 100;
int START = 20000;

int getCode(int time) {
	if (time > SHORT_SPACE - DELTA && time < SHORT_SPACE + DELTA) {
		return 0;
	}
	else if (time > LONG_SPACE - DELTA && time < LONG_SPACE + DELTA) {
		return 1;
	}

	return 2;
}

void main()
{
	//==========================
	Settings	settings;
	TCHAR		pluginPath[128];
	CIRDriver	irDriver;
	//==========================
	

	struct hardware *hw = NULL;

	SetCurrentDirectory(_T(".\\plugins\\"));

	if(settings.getPluginName(pluginPath)) {

		if(irDriver.loadPlugin(pluginPath)) {

			hw = irDriver.getHardware();

			if(hw==NULL ) {
				printf("The plugin doesn't export the required functions.");
				return;
			}
		}
		else {
			printf("Loading plugin failed.");
			return;
		}
	}
	else {
		printf("No valid plugins found.");
		return;
	}

	if(hw->rec_mode!=LIRC_MODE_MODE2 && hw->rec_mode!=LIRC_MODE_LIRCCODE) {
		printf("The plugin doesn't export any raw data.");
		return;
	}

	if(!irDriver.init()) {
		printf("Intialising plugin failed.");
		return;
	}

	if(hw->rec_mode==LIRC_MODE_MODE2) {

		while(1) {

			//==========
			lirc_t data;
			//==========

			while(hw->data_ready()) {

				data = hw->readdata(0);

				

				if(data&PULSE_BIT) {
					//printf("PULSE %i\n",data&PULSE_MASK);
				}
				else {
					int time = data&PULSE_MASK;
					if (time > START) {
						printf("\n");
					}
					else if (time < 2000) {
						printf("%i", getCode(data&PULSE_MASK));
					}

					//printf("SPACE %i",time);
				}
			}
			if(_kbhit()) break;	//user pressed a key

			Sleep(10);
		}

	}
	else {

		while(1) {

			//===========
			ir_code code;
			//===========

			while(hw->data_ready()) {

				code = hw->get_ir_code();

				printf("%I64x\n",code);

			}
			if(_kbhit()) break;	//user pressed a key

			Sleep(10);
		}

	}

	irDriver.deinit();
}