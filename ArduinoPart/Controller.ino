#include <Servo.h>

//PWM OUTPUT VARIABLES
Servo pin2;  
Servo pin3;                
Servo pin4; 
Servo pin5; 
Servo pin6; 
 
//SERIAL USB BUFFER
char inData[65]; // Allocate some space for the string
char inChar; // Where to store the character read
byte index = 0; // Index into array; where to store the character

void setup() {
  
  //START PIN OUTPUT
  pin2.attach(2);  // attaches the servo on pin 9 to the servo object 
  pin3.attach(3);
  pin4.attach(4);
  pin5.attach(5);
  pin6.attach(6);
  
  //SEND MINIMAL VAUE 1000, REGUIRED FOR ESC TO ARM (max is 2000)
  pin2.writeMicroseconds(1000);
  pin3.writeMicroseconds(1000);
  pin4.writeMicroseconds(1000);
  pin5.writeMicroseconds(1000);
  pin6.writeMicroseconds(1000);
  
  //INIT SERIAL COMMUNICATION WITH ANDROID
  Serial.begin(115200);
}

void loop() {
  
 
  if(Serial.available() > 0){
     
      //WAIT FOR INCOMMING DATA BUFFER TO FILL 
      if(index < 24){
             inChar = Serial.read(); // Read a character
             inData[index] = inChar; // Store it
             index++; // Increment where to write next
             if(inChar == 'X')
               index = 0;
             inData[index] = '\0'; // Null terminate the string
      }
   }
   if(index==24){
     
      index = 0;
      //STRING LOOKS LIKE "A100B96C20D0E" WHERE LETTERS ACT AS DELIMINATOR
      //STRING CAN CONTAIN SINGLE VALUE LIKE "B96C"
      String buf = String(inData);
      int ia = buf.indexOf("A", 0);
      int ib = buf.indexOf("B", 0);
      int ic = buf.indexOf("C", 0);
      int id = buf.indexOf("D", 0);
      int ie = buf.indexOf("E", 0);
      int iff = buf.indexOf("F", 0);
    
      Serial.print("|");
      //A?B
      if(ia!=-1 && ib!=-1){
        
        //1. CONVERT VALUE TO INTEGER
        //2. CONVERT 0-100 TO 1000 - 2000 
        //3. WRITE TO ARDUINO PIN NUMBER 2
        pin2.writeMicroseconds(map(buf.substring(ia+1, ib).toInt(), 1, 100, 1000, 2000));      
        //SEND RESPONSE TO ANDROID
        Serial.print("RESPONSE:OK");
      }
      //B?C
      if(ib!=-1 && ic!=-1){
        
        pin3.writeMicroseconds(map(buf.substring(ib+1, ic).toInt(), 1, 100, 1000, 2000));         
        //Serial.print("PIN3 OK");
      }
      //C?D
      if(ic!=-1 && id!=-1){

        pin4.writeMicroseconds(map(buf.substring(ic+1, id).toInt(), 1, 100, 1000, 2000));         
        //Serial.print("PIN4 OK");
      }
      //D?E
      if(id!=-1 && ie!=-1){

        pin5.writeMicroseconds(map(buf.substring(id+1, ie).toInt(), 1, 100, 1000, 2000));         
        //Serial.print("PIN5 OK");
      }
      //E?F
      if(ie!=-1 && iff!=-1){

        pin5.writeMicroseconds(map(buf.substring(ie+1, iff).toInt(), 1, 100, 1000, 2000));         
        //Serial.print("PIN6 OK");
      }
      
      Serial.print("|00");
      Serial.print("\n"); 
   }
}
void logg(){
  
}


