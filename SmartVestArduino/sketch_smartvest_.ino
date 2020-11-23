#include <SoftwareSerial.h>
#define BT_RXD 9//수신
#define BT_TXD 8//전송

//7583033343935180B1E1

SoftwareSerial bluetooth(8, 9);

//온습도
#include <DHT.h> 
#define DHTPIN 2 //digtal ~2 pin 
#define DHTTYPE DHT11 
DHT dht(DHTPIN, DHTTYPE);

//gas
const int GasPin = A0;
const int GasPin_7 = A1;

//GPS         
#include <TinyGPS++.h>            
TinyGPSPlus gps;
//SoftwareSerial nss(3,4);
double lat1=37.399463;
double lng1=127.120929;
int zero=0;

int i=0;


char cmd='b';

int speakerpin = 12;
int red = 10;
int green = 11;
int blue = 13;



void setup(void)
{
  //gas
  pinMode(GasPin ,INPUT); 
  pinMode(GasPin_7 ,INPUT); 
  
  Serial.begin(9600);
  //bluetooth
  bluetooth.begin(9600);
  //온습도
  dht.begin();
  Serial.println("power on!");
  
  pinMode(red, OUTPUT);
  pinMode(green, OUTPUT);
  pinMode(blue, OUTPUT);
  


////GPS
//  nss.begin(9600);
//  nss.available();
}


void loop(void)
{
  String arr="";
  if(bluetooth.available()){ // BT –> Data –> Serial
    cmd=bluetooth.read();
    }
  

 
  while(cmd=='a'){
  Serial.println(cmd);
  //tone(speakerpin,2000,1800);
  digitalWrite(red, HIGH);
  delay(500);
  digitalWrite(red, LOW);
  delay(100);
  
  digitalWrite(green, HIGH);
  delay(500);
  digitalWrite(green, LOW);
  delay(100);
  
  digitalWrite(blue, HIGH);
  delay(500);
  digitalWrite(blue, LOW);
  delay(100);
  if(bluetooth.available()){ // BT –> Data –> Serial
    cmd=bluetooth.read();
    }
  }
    cmd='b';
         
    float h = dht.readHumidity(); //습도 
    float t = dht.readTemperature(); //온도
    int gas_5=analogRead(GasPin);
    int gas_7=analogRead(GasPin_7);
      
    if (isnan(h) || isnan(t)) {
    Serial.println("Failed to read from DHT sensor!");
    arr=arr+"0,0";         
    }else{
    Serial.print("Temp: "); 
    Serial.print(t); 
    Serial.print(" C \n");  
    Serial.print("Humi: "); 
    Serial.print(h); 
    Serial.println(" %\t");   
    arr=String(t)+","+String(h)+",";   
    }
    
    if(isnan(gas_5) || isnan(gas_7)){ 
     Serial.println("Failed to read from gas sensor!");
    arr=arr+"0,0";   
    }else{
    Serial.print(gas_5);
    Serial.print(", ");
    Serial.println(gas_7);
    arr=arr+String(gas_5)+","+String(gas_7)+",";
    }



//GPS

//nss.listen();
//    gps.encode(nss.read());
    if (gps.location.isUpdated()){
      lat1=gps.location.lat();
      lng1=gps.location.lng();
    }
    Serial.print("Latitude= "); 
      Serial.print(lat1, 6);
      Serial.print(" Longitude= "); 
      Serial.println(lng1, 6);

    String latt="";
    String lngg="";  
    char TempString[10];  //  Hold The Convert Data
    dtostrf(lat1,6,6,TempString);
    latt = String(TempString);  // cast it to string from char 
    char TempString2[10];  //  Hold The Convert Data
    dtostrf(lng1,6,6,TempString2);
    lngg = String(TempString2);  // cast it to string from char   
      
      arr=arr+latt+","+lngg;
    Serial.println(arr); 
    
      if(Serial.available()){ // Serial –> Data –> BT
      bluetooth.write(Serial.read());
    }    
   bluetooth.print(arr);
   bluetooth.println();

   delay(1000);
        
}


      
