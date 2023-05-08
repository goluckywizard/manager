package ru.nsu.manager.controller;

import com.roytuts.jaxb.WorkerResponse;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.manager.ManagerService;

import java.util.concurrent.ExecutionException;

@Controller
public class InnerController {
    ManagerService managerService;

    public InnerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @RabbitListener(queues = "responseQueue")
    public void getTask(WorkerResponse response) {
        System.out.println(response.getStringList());
        try {
            managerService.checkResults(response);
        } catch (ExecutionException e) {
            System.err.println(e.getMessage());
        }
    }
}
