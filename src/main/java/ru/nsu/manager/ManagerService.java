package ru.nsu.manager;

import com.roytuts.jaxb.WorkerRequest;
import com.roytuts.jaxb.WorkerResponse;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2XmlMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import ru.nsu.manager.dto.CrackDTO;
import ru.nsu.manager.dto.StatusDTO;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ManagerService {
    public ManagerService(RabbitTemplate template) {
        this.template = template;
    }

    //    @Autowired
    private final RabbitTemplate template;
    private final HashMap<UUID, Status> statuses = new HashMap<>();

    @Value("${queue.name}")
    String queueName;
    @Value("${workers.count}")
    Long workersCount;
    @Value("${workers.alphabetVolume}")
    Long alphabetVolume;

    public UUID sendMessage(CrackDTO message) {
        UUID id = UUID.randomUUID();
        statuses.put(id, new Status());
        long partCount = 0;
        for (int i = 0; i <= message.getMaxLength(); i++) {
            partCount += (long) Math.pow(alphabetVolume, i);
        }
        partCount = (long) Math.ceil((float) partCount / workersCount);
        for (int i = 0; i < workersCount; i++) {
            WorkerRequest request = new WorkerRequest();
            request.setHash(message.getHash());
            request.setPartNumber(i);
            request.setPartCount(partCount);
            request.setId(id.toString());
            request.setMaxLength(message.getMaxLength());

            System.out.println("huiiii");
            template.convertAndSend("requestQueue", request);
        }
        return id;
    }
    public StatusDTO getStatus(String id) throws ExecutionException {
        UUID requestId = UUID.fromString(id);
        Status s = statuses.get(requestId);
        StatusDTO dto = new StatusDTO();
        if (s == null) {
            dto.setStatus("ERROR");
        }
        else {
            dto.setStatus(s.getStatus());
            dto.setData(s.getData());
        }
        return dto;
    }

    public void checkResults(WorkerResponse response) throws ExecutionException {
        System.out.println(response.getId());
        System.out.println(response.getStringList());
        if (!statuses.containsKey(UUID.fromString(response.getId())))
            return;
        statuses.get(UUID.fromString(response.getId())).increaseCount();
        if (statuses.get(UUID.fromString(response.getId())).getCount() == workersCount) {
            statuses.get(UUID.fromString(response.getId())).setStatus("READY");
        }
        statuses.get(UUID.fromString(response.getId())).getData().addAll(response.getStringList());
    }
}
