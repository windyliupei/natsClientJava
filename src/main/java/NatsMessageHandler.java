import io.nats.client.Message;
import io.nats.client.MessageHandler;

public class NatsMessageHandler implements MessageHandler {
    @Override
    public void onMessage(Message msg) {
        System.out.print(msg.getData().length);
    }
}
