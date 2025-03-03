import org.eclipse.paho.client.mqttv3.*
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence

fun main() {
    val broker = System.getenv("BROKER_URL") ?: "tcp://mosquitto:1883"
    val clientId = "Publisher"
    val topic = "meutopico"
    val messageContent = "Olá, MQTT!"

    val client = MqttClient(broker, clientId, MemoryPersistence())

    try {
        val options = MqttConnectOptions().apply {
            isCleanSession = false
        }

        client.connect(options)
        println("🔗 Conectado ao broker: $broker")

        val message = MqttMessage(messageContent.toByteArray()).apply {
            qos = 2
        }

        client.publish(topic, message)
        println("✅ Mensagem publicada no tópico '$topic': '$messageContent'")

        Thread.sleep(5000)

    } catch (e: MqttException) {
        println("❌ Erro ao publicar mensagem: ${e.reasonCode} - ${e.message}")
        e.printStackTrace()
    } finally {
        if (client.isConnected) {
            client.disconnect()
            println("🔌 Desconectado do broker.")
        }
    }
}
