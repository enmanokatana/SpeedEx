package org.example.server.controllers;

import lombok.RequiredArgsConstructor;
import org.example.server.Dtos.OptionDto;
import org.example.server.models.Option;
import org.example.server.models.ResponseDto;
import org.example.server.services.OptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/Option")
@RequiredArgsConstructor
public class OptionController {

    private final OptionService optionService;
    @GetMapping("/{id}")
    public ResponseDto GetOptionById(
            @PathVariable Long id
    ){
        return optionService.getOptionsById(id);

    }

    @PostMapping
    public ResponseDto CreateOption(
            @RequestBody OptionDto option
    ){

        return optionService.createOption(option);
    }
    @DeleteMapping("/{id}")
    public ResponseDto DeleteOption(
            @PathVariable Long id
    ){
        return optionService.deleteOption(id);
    }
    @PutMapping
    public ResponseDto ModifyOption(
            @RequestParam Option option
    ){
        return optionService.modifyOption(option);
    }



}
