#include <ESP8266WiFi.h>
#include <WebSocketsServer.h>

#define IN1 D3
#define PHOTO_DIODE_PIN D5
#define LED_PIN1 D7
#define LED_PIN2 D8

// Configuración WiFi
const char* ssid = "Totalplay-FFA5_EXT"; 
const char* password = "FFA51AB1yT99wTQn"; 

// WebSockets
WebSocketsServer webSocket = WebSocketsServer(81);

volatile unsigned long pulseCount = 0;
bool motorActivadoFlag = false;
unsigned long lastTime = 0;

void IRAM_ATTR countPulse() {
  if(motorActivadoFlag) pulseCount++;
}

void setup() {
  Serial.begin(9600);
  pinMode(IN1, OUTPUT);
  pinMode(PHOTO_DIODE_PIN, INPUT_PULLUP);
  pinMode(LED_PIN1, OUTPUT);
  pinMode(LED_PIN2, OUTPUT);
  attachInterrupt(digitalPinToInterrupt(PHOTO_DIODE_PIN), countPulse, RISING);

  // Conexión WiFi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("\nConectado! IP: " + WiFi.localIP().toString());

  // WebSocket
  webSocket.begin();
  webSocket.onEvent(webSocketEvent);
}

void loop() {
  // Maneja el WebSocket
  webSocket.loop();

  // Control de activacion desde Serial
  if (Serial.available() > 0) {
    String command = Serial.readStringUntil('\n');
    command.trim();
    handleDeviceCommand(command);
  }

  // Enviar datos cada segundo
  if (millis() - lastTime >= 1000) {
    unsigned long frequency = pulseCount;
    unsigned long rpm = frequency * 60; // Asumiendo 1 pulso por revolución

    // Enviar datos por WebSocket
    String jsonData = "{\"freq\":" + String(frequency) + ",\"rpm\":" + String(rpm) + "}";
    webSocket.broadcastTXT(jsonData);

    pulseCount = 0;
    lastTime = millis();
  }
}

// WebSocket events
void webSocketEvent(uint8_t num, WStype_t type, uint8_t* payload, size_t length) {
  switch(type) {
    case WStype_TEXT:
      handleDeviceCommand((char*)payload);
      break;
    case WStype_DISCONNECTED:
      Serial.printf("[%u] Desconectado\n", num);
      break;
    case WStype_CONNECTED:
      Serial.printf("[%u] Conectado desde IP: %s\n", num, webSocket.remoteIP(num).toString().c_str());
      break;
  }
}

// Control del motor a través de WebSocket o Serial
void handleDeviceCommand(String command) {
  command.trim();
  command.toUpperCase();

  if(command == "ACTIVADO") {
    digitalWrite(IN1, HIGH);
    motorActivadoFlag = true;
    Serial.println("Motor ACTIVADO");
    webSocket.broadcastTXT("{\"status\":\"MOTOR_ACTIVADO\"}");
  }else if(command == "DESACTIVADO") {
    digitalWrite(IN1, LOW);
    motorActivadoFlag = false;
    Serial.println("Motor DESACTIVADO");
    webSocket.broadcastTXT("{\"status\":\"MOTOR_DESACTIVADO\"}");
  }else if(command == "LED1ON"){
    digitalWrite(LED_PIN1, HIGH);
    Serial.println("LED 1 Encendido");
    webSocket.broadcastTXT("{\"status\":\"LED1_ACTIVADO\"}");
  }else if(command == "LED1OFF"){
    digitalWrite(LED_PIN1, LOW);
    Serial.println("LED 1 Apagado");
    webSocket.broadcastTXT("{\"status\":\"LED1_DESACTIVADO\"}");
  }else if(command == "LED2ON"){
    digitalWrite(LED_PIN2, HIGH);
    Serial.println("LED 2 Encendido");
    webSocket.broadcastTXT("{\"status\":\"LED2_ACTIVADO\"}");
  }else if(command == "LED2OFF"){
    digitalWrite(LED_PIN2, LOW);
    Serial.println("LED 2 Apagado");
    webSocket.broadcastTXT("{\"status\":\"LED2_DESACTIVADO\"}");
  }
}
