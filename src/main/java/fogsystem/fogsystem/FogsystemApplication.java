package fogsystem.fogsystem;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

@SpringBootApplication
public class FogsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FogsystemApplication.class, args);

		ObjectMapper objectMapper = new ObjectMapper();

		try (ZContext context = new ZContext()) {

			ZMQ.Socket subscriber = context.createSocket(SocketType.SUB);
			subscriber.connect("tcp://localhost:40040");

			subscriber.subscribe("");

			JsonNode jsonNode = null;

			while (true) {
				String messageRecv = subscriber.recvStr(0);

				try {
					jsonNode = objectMapper.readTree(messageRecv);
				} catch (Exception exception) {
					exception.printStackTrace();
					System.out.println("JsonMappingException");
				}

				System.out.println(jsonNode);
			}
		}

	}
}
