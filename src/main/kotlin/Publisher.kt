import org.eclipse.paho.client.mqttv3.*
import java.util.Scanner

fun main() {
    val broker = "tcp://localhost:1883"
    val clientId = "MeuPublisherMQTT"
    val topic = "delivery"

    try {
        val client = MqttClient(broker, clientId, null)

        val options = MqttConnectOptions().apply {
            isCleanSession = true
        }

        client.setCallback(object : MqttCallback {
            override fun messageArrived(topic: String?, message: MqttMessage?) {
                println("TX: ${String(message?.payload ?: ByteArray(0))}")
            }

            override fun connectionLost(cause: Throwable?) {
                println("❌ Conexão perdida: ${cause?.message}")
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                println("Mensagem entregue!")
                println("Digite a mensagem para enviar (digite 'sair' para desconectar):")
            }
        })

        println("Conectando ao broker: $broker")
        client.connect(options)
        println("Conectado ao MQTT!")

        val scanner = Scanner(System.`in`)

        println("Digite a mensagem para enviar (digite 'sair' para desconectar):")
        while (true) {
            val messageContent = scanner.nextLine()

            if (messageContent.equals("sair", ignoreCase = true)) {
                println("Desconectando...")
                client.disconnect()
                println("Desconectado do broker!")
                break
            }

            val message = MqttMessage(messageContent.toByteArray())

            println("Bytes da mensagem: ${message.payload.contentToString()}")

            client.publish(topic, message)
            println("TX '$topic': $messageContent")

        }

    } catch (e: MqttException) {
        println("❌ Erro: ${e.message}")
    }
}
