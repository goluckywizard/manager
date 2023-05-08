package ru.nsu.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.manager.ManagerService;
import ru.nsu.manager.dto.CrackDTO;
import ru.nsu.manager.dto.RequestDTO;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/hash")
public class OuterController {
    @Autowired
    ManagerService managerService;

    @PostMapping("/crack")
    ResponseEntity<?> crackHash(@RequestBody CrackDTO dto) {
//        System.out.println(dto.getHash());
//        System.out.println(dto.getMaxLength());
//        System.out.println("qwertyui");
        UUID id = managerService.sendMessage(dto);
        return ResponseEntity.ok(new RequestDTO(id.toString()));
    }
    @GetMapping("/status")
    ResponseEntity<?> getStatus(@RequestParam String requestId) throws ExecutionException {
        return ResponseEntity.ok(managerService.getStatus(requestId));
    }
}
