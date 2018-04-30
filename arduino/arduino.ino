netint aPin = 1;
float cora;

void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);

}

void loop() {
  // put your main code here, to run repeatedly:
  cora = analogRead(aPin);
  cora = cora/8.5;
  if (cora < 75 )
  {
    Serial.println(0);
  }
  else
  {
  Serial.println(cora);
  }
  delay(3000);
}
