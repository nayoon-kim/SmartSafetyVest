

#include <SoftwareSerial.h>
#define BT_RXD 9//수신
#define BT_TXD 8//전송

SoftwareSerial bluetooth(8, 9);
//lcd
#include <Wire.h>                        // i2C 통신을 위한 라이브러리
#include<LiquidCrystal_I2C.h>
LiquidCrystal_I2C lcd(0x27,16,2);     // 접근주소: 0x3F or 0x27

//온습도
#include <DHT.h> 
#define DHTPIN 2 //digtal ~2 pin 
#define DHTTYPE DHT11 
DHT dht(DHTPIN, DHTTYPE);


//gas
#define         MQ_PIN   (A0)
#define         MQ_PIN_2 (A1)  
#define         RL_VALUE                     (5)     //define the load resistance on the board, in kilo ohms
#define         RO_CLEAN_AIR_FACTOR          (9.83)  

#define         GAS_LPG                      (0)
#define         GAS_CO                       (1)
#define         GAS_CH4                      (2)

#define         CALIBARAION_SAMPLE_TIMES     (50)    //define how many samples you are going to take in the calibration phase
#define         CALIBRATION_SAMPLE_INTERVAL  (100)   //define the time interal(in milisecond) between each samples in the
                                                     //cablibration phase
#define         READ_SAMPLE_INTERVAL         (50)    //define how many samples you are going to take in normal operation
#define         READ_SAMPLE_TIMES            (5)     //define the time interal(in milisecond) between each samples in


float Ro=10;
float Ro_2=10;
                    
const float           LPGCurve[3]  =  {2.3,0.51,-0.40}; //2.3,0.51,-0.47
const float           COCurve[3]  =  {2.3,0.72,-0.34};;
const float           CH4Curve[3]  =  {2.1,1.00,-0.20};   //1.8,1.15,-0.10

unsigned long previousMillis = 0; //이전시간
const long delayTime = 1000; //1초(1000) 대기시간


int moveNum=0;

//GPS         
#include <TinyGPS++.h>            
TinyGPSPlus gps;
//SoftwareSerial nss(3,4);
long lat1=(35.523961)*10000.0L; //9
long lng1=(129.372504)*10000.0L; //4
float lat2=lat1/10000.0;
float lng2=lng1/10000.0;


int move_Num=0;

char cmd='@';
char gas_cmd='@';

const PROGMEM int speakerpin = 12;
const PROGMEM int red = 10;
const PROGMEM int green = 11;
const PROGMEM int blue = 13;

const String id="7583033343935180B1E1";

String tt="";

boolean num=false;
String name1="";

char gps_w='@';

char buffer1[2]={};


void setup(void)
{
  //gas
  pinMode(MQ_PIN ,INPUT); 
  pinMode(MQ_PIN_2 ,INPUT); 
  
  Serial.begin(9600);
  //bluetooth
  bluetooth.begin(9600);
  //온습도
  dht.begin();
  Serial.println("power on!");
  
  pinMode(red, OUTPUT);
  pinMode(green, OUTPUT);
  pinMode(blue, OUTPUT);
//lcd

lcd.init();                      // LCD 초기화
// Print a message to the LCD.
lcd.backlight();                // 백라이트 켜기

  Ro=4.93; //already calculate
  Ro_2=5.14;
  
////GPS
//  nss.begin(9600);
//  nss.available();
}




void loop(void)
{ 
  Serial.println(analogRead(MQ_PIN)); 
 Serial.println(analogRead(MQ_PIN_2)); 

previousMillis=millis();
  char text;
  String arr="";
  arr+=id+",";  

  if(bluetooth.available()){ // BT –> Data –> Serial
     cmd=bluetooth.read();
     if(cmd=='!'){
//    Serial.readBytes(buffer1,2);
     Serial.println(cmd);
     }
    }  
         
  if(num==false){
      text=(char)cmd;
      if(text!='*'&&text!='@'&&text!='!'){
        name1+=text;
        while(bluetooth.available()){
          if(text!='*'&&text!='@'&&text!='!'){
          text=(char)bluetooth.read();
          name1+=text;
          delay(5);
          }
       }
       name1.trim();
        Serial.println(name1);
        lcd.clear();
        lcd.setCursor(0,0);
        lcd.print(name1);
        delay(10);
        num=true;
      }
   } 
  

  if(gas_cmd=='*'){
  tone(speakerpin,2000,1144);
    digitalWrite(blue, HIGH);
    digitalWrite(red, LOW);
    digitalWrite(green, LOW);
 }else if(cmd=='!'){
  //  tone(speakerpin,2000,1144);
  digitalWrite(red, HIGH);
  digitalWrite(green, LOW);
  digitalWrite(blue, LOW);
  cmd='@';
  }else{
  digitalWrite(red, LOW);
  digitalWrite(green, LOW);
  digitalWrite(blue, LOW);
  }
  
  //GPS

//nss.listen();
//    gps.encode(nss.read());
    
    if (gps.location.isUpdated()){
      lat2=gps.location.lat();
      lng2=gps.location.lng();        
    }
//      if(move_Num%20<10){
//      lat2-=0.00005;
//      lng2-=0.00015;
//      move_Num++;
//      }else if((move_Num%20)>=10&& (move_Num%20)<20){
//      lat2+=0.00005;
//      lng2+=0.00015;
//      move_Num++;
//     }

    int leng=13;
    int moo=move_Num%leng;
    if(moo==1){ //안쪽 왼쪽위 2
      lat2=35.524670; //35.524670, 129.372015
      lng2=129.372015;
      }else if(moo==2){ //안쪽 왼쪽아래 3
      lat2=35.523980; //35.523980, 129.371728
      lng2=129.371728;
     }else if(moo<1){ //바깥 위 1 //35.524479, 129.372650
      lat2=35.524479; 
      lng2=129.372650;
     }else if(moo==3){ //바깥 4 35.523767, 129.372259
      lat2=35.522780;
      lng2=129.371647;      
      }else if(moo==4){ //바깥 5 35.523941, 129.372385
        lat2=35.522058;
        lng2=129.370834;    //35.521961, 129.371434
      }else if(moo==7){ //바깥 6
      lat2=35.521612;
      lng2=129.373076;
      }else if(moo==5){ //바깥 6-1
      lat2=35.521961;
      lng2=129.371434;
      }else if(moo==6){ //바깥 6-2
      lat2=35.521883;
      lng2=129.372164;
      }else if(moo==8){ //바깥 7
      lat2=35.522433;
      lng2=129.373130; 
      }else if(moo==9){ //바깥 7-1
      lat2=35.522701;
      lng2=129.372547;
      }
      else if(moo==10){ //바깥 8
      lat2=35.523496;
      lng2=129.372236;
      }
      else if(moo==11){ //바깥 9
      lat2=35.524039;
      lng2=129.372389;
      }else{
      lat2=35.524272;
      lng2=129.372649;
      }


      
    Serial.print(move_Num); 
    move_Num++;
    String latt="";
    String lngg="";  
    latt=String(lat2,5);
    lngg=String(lng2,5);
    

      
      arr+=latt+","+lngg+",";



   
int ch4=MQGetGasPercentage(MQRead(MQ_PIN)/Ro,GAS_CH4);
if(ch4<0||ch4>10000)
  ch4=10000;
int lpg=MQGetGasPercentage(MQRead(MQ_PIN)/Ro,GAS_LPG);
if(lpg<0||lpg>10000)
  lpg=10000;
int co=MQGetGasPercentage(MQRead(MQ_PIN_2)/Ro_2,GAS_CO) ;
if(co<0||co>10000)
  co=10000;

   Serial.print("CH4:");
   Serial.print(ch4);
   Serial.print( "ppm" );
   Serial.print("    ");
   
   Serial.print("LPG:");
   Serial.print(lpg);
   Serial.print( "ppm" );
   Serial.print("    ");   

   Serial.print("CO:");
   Serial.print(co);
   Serial.print( "ppm" );
   Serial.print("    ");
   Serial.println();   

   arr=arr+String(ch4)+","+String(lpg)+","+String(co)+",";
   
         
    float h = dht.readHumidity(); //습도 
    float t = dht.readTemperature(); //온도

  if (isnan(h) || isnan(t)) {
    Serial.println("Failed to read from DHT sensor!");
    arr=arr+"24,38,";         
    }else{
    arr+=String(t)+","+String(h)+",";   
    }

   //좋음 0 주의 1 나쁨 2
   if(ch4>1000){
    arr+="2,";
  }else if((ch4>500&&ch4<=1000)){
    arr+="1,";
    }else{
    arr+="0,";
    } 
    
    if(lpg>70){
    arr+="2,";
  }else if((lpg>40 && lpg<=70)){
    arr+="1,";
    }else{
    arr+="0,";
    } 

    if(co>1000 || co<0){
    arr+="2,";
  }else if((co>500 && co<=1000)){
    arr+="1,";
    }else{
    arr+="0,";
    } 

    
    if(t>40 || t<0){
    arr+="2,";
  }else if((t>35 && h<=40)||(h>0&&h<10)){
    arr+="1,";
    }else{
    arr+="0,";
    } 

   if(h>80 || h<10){
    arr+="2,";
  }else if((h>70 && h<=80)||(h>=10&&h<20)){
    arr+="1,";
    }else{
    arr+="0,";
    } 


      
   if(ch4>1000|| lpg>70|| co>1000){
    gas_cmd='*';
    arr+="1";
  }else{
    gas_cmd='@';
    arr+="0";
      } 


   Serial.println(arr); 
   bluetooth.print(arr);
   bluetooth.println();

   delay(100);
   

    
   unsigned long currentMillis = millis();
   Serial.println(currentMillis-previousMillis);     
}


float MQResistanceCalculation(int raw_adc)
{
  return ( ((float)RL_VALUE*(1023-raw_adc)/raw_adc));
}


float MQRead(int mq_pin)
{
  int i;
  float rs=0;
 
  for (i=0;i<READ_SAMPLE_TIMES;i++) {
    rs += MQResistanceCalculation(analogRead(mq_pin));
    delay(READ_SAMPLE_INTERVAL);
  }
 
  rs = rs/READ_SAMPLE_TIMES;
 
  return rs; 
}


int MQGetGasPercentage(float rs_ro_ratio, int gas_id)
{
  if ( gas_id == GAS_LPG ) {
     return MQGetPercentage(rs_ro_ratio,LPGCurve);
  } else if ( gas_id == GAS_CO ) {
     return MQGetPercentage(rs_ro_ratio,COCurve);
    } else {
     return MQGetPercentage(rs_ro_ratio,CH4Curve);
  } 
 
  return 0;
}
 
int  MQGetPercentage(float rs_ro_ratio, float *pcurve)
{
  return (pow(10,( ((log(rs_ro_ratio)-pcurve[1])/pcurve[2]) + pcurve[0])));
}

      
