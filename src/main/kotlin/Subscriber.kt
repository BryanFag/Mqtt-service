import org.eclipse.paho.client.mqttv3.*

fun main() {
    val broker = "tcp://localhost:1883"
    val clientId = "MeuListenerMQTT"
    val topic = "delivery"

    try {
        val client = MqttClient(broker, clientId, null)

        val options = MqttConnectOptions().apply {
            isCleanSession = true
        }

        println("Conectando ao broker: $broker")
        client.connect(options)
        println("Conectado ao MQTT!")

        client.subscribe(topic) { _, message ->
            println("RX: ${String(message.payload)}")
        }

        println("Ouvindo mensagens no tópico: $topic... (Pressione CTRL+C para sair)")

        while (true) {
            Thread.sleep(1000)
        }

    } catch (e: MqttException) {
        println("❌ Erro: ${e.message}")
    }
}
