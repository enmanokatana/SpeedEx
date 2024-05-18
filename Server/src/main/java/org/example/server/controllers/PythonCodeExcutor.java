package org.example.server.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class PythonCodeExcutor {
    @PostMapping("/excute")
    public String excuteCode(
            @RequestBody String code
    ){
        String uniqueId = UUID.randomUUID().toString();
        String fileName = uniqueId + ".py";
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(code);
            writer.close();

            ProcessBuilder pb = new ProcessBuilder("docker", "run", "--rm", "-v", fileName + ":/tmp/code.py", "python:3.8", "python", "/tmp/code.py");
            Process process = pb.start();
            process.waitFor();

            return  new String(process.getInputStream().readAllBytes());
        }catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
