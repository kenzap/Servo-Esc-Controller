Servo-Esc-Controller
====================

This piece of code intended to organize control of Servo engines or Electronic speed control circuits (ESC) directly from an ordinary Android phone via USB cable.


Usage: 

	1. Buy USB OTG cabel.
	2. Download Arduino software from this repository and upload it to your arduino board.
	3. Download Android Wifi Esc Controller either from this repository and compile it with eclipse or download from Wifi Esc Controller by Kenzap Ltd from Google play.
	4. Connect Android device with Arduino board using UDB OTG cable.
	5. Servo Controller should popup automatically.
	6. Move Sliders and Arduino TX LED should start blinking.
	7. Connect ESC or Servo white signal wire, for example, to Arduino pin 2, red +5v, black GND and start mooving PIN 2 slider.
	8. If nothing happens when you connect Arduino to your Adnroid device than either your device does not support USB Host mode or you have not connected a valid OTG USB cable.
	
Compiling with Eclipse:

This app is based on https://github.com/mik3y/usb-serial-for-android Serial communication library. You shoul add it as a library when building yourself.

1. Download https://github.com/mik3y/usb-serial-for-android
2. Ecipse -> New Project -> Android Application Project -> Browse ... usb-serial-for-android and copy it to your working directory.
3. Right click on Servo Controller project in eclipse -> Properties -> Android -> Library -> Add -> UsbSerialLibrary


