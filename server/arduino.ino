// * 금속센서 + 스위치 * //

int push_val = 0;
int metal_val = 0;

void setup(){
	Serial.begin(9600); // init serial 9600
	pinMode(5,INPUT); // push 
	pinMode(3,INPUT); // metal_sensor
} // end setup() 

void loop(){
  
	delay(100); // 0.1s delay
  
	push_val=digitalRead(5); // push digital signal read
	metal_val=digitalRead(3); // metal sensor digital signal read
  
	if(push_val==1){ // pushing switch
		if(metal_val==1){ // non_metal
			Serial.println(0);
			delay(2000); // 2second delay
		}else{ // metal
			Serial.println(1);
			delay(2000); // 2second delay
		}
	}
  
} // end loop() 