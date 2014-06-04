Servo-Esc-Controller
====================

This piece of code intended to organize control of Servo engines or Electronic speed control circuits (ESC) directly from an ordinary Android phone via USB cable.


Usage: 

	1. Buy USB OTG cabel and connect it to your Android device.
	2. Download Arduino software from this repository and upload it to your arduino board.
	3. Download Android Wifi Esc Controller either from this repository and compile it with eclipse or download from Wifi Esc Controller by Kenzap Ltd from Google play.
	4. Connect Android device with Arduino board.
	5. Servo Controller should popup automatically.
	6. Move Sliders and Arduino TX LED should start blinking.
	7. Connect ESC or Servo white signal wire, for example, to Arduino pin 2, red +5v, black GND and start mooving PIN 2 slider.
	8. If nothing happens when you connect Arduino to your Adnroid device than either your device does not support USB Host mode or you have not connected a valid OTG USB cable.
