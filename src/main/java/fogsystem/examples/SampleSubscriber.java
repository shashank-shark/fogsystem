package fogsystem.examples;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZSocket;

public class SampleSubscriber {

    ZContext context = null;
    ZMQ.Socket subscriber = null;

    public SampleSubscriber(String address, String subscriptionMessage) {

        try {
            /*
             * Create the ZMQ Context
             */
            context = new ZContext();

            /*
             * Create Subscriber and subscribe to publisher
             */
            subscriber = context.createSocket(SocketType.SUB);
            subscriber.connect(address);
            subscriber.subscribe(subscriptionMessage);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void getPublisherMessages () {

        String message = null;
        JsonNode jsonNode = null;

        ObjectMapper objectMapper = new ObjectMapper();

        while (true) {
            message = subscriber.recvStr(0);

            // conver the recieved string to JsonNode
            try {
                jsonNode = objectMapper.readTree(message);
                System.out.println(jsonNode);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
