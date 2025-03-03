import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

fun main() {
    val broker = System.getenv("BROKER_URL") ?: "tcp://mosquitto:1883"
    val clientId = "Subscriber-" + System.currentTimeMillis()
    val topic = "meutopico"

    try {
        val client = MqttClient(broker, clientId, MemoryPersistence())

        val options = MqttConnectOptions().apply {
            isCleanSession = false
            isAutomaticReconnect = true
        }

        println("🔗 Conectando ao broker: $broker")
        client.connect(options)
        println("✅ Conectado ao MQTT!")

        client.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String, message: MqttMessage) {
                println("📥 Mensagem recebida no tópico '$topic': ${String(message.payload)} (QoS: ${message.qos}, Retained: ${message.isRetained})")
            }

            override fun connectionLost(cause: Throwable) {
                println("⚠️ Conexão perdida: ${cause.message}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken) {}
        })

        println("🔔 Inscrevendo-se no tópico: $topic")
        client.subscribe(topic, 2)
        println("🔊 Aguardando mensagens...")

        while (true) {
            Thread.sleep(5000)
        }

    } catch (e: Exception) {
        println("❌ Erro no subscriber: ${e.message}")
        e.printStackTrace()
    }
}
