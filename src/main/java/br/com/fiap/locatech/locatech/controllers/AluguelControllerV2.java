package br.com.fiap.locatech.locatech.controllers;

import br.com.fiap.locatech.locatech.dtos.AluguelRequestDTO;
import br.com.fiap.locatech.locatech.entities.Aluguel;
import br.com.fiap.locatech.locatech.services.AluguelService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
/**
 * Esse arquivo exemplifica dois formatos de versionamento de API.
 * O primeiro seria duplicar a Controller, adicionando um préfixo da respectiva versão no path.
 * O segundo seria adicionar um header diretamente ao método, como no método saveAluguel
 * **/
@RestController
@RequestMapping("/v2/alugueis")
public class AluguelControllerV2 {

    private static final Logger logger = LoggerFactory.getLogger(AluguelControllerV2.class);

    private final AluguelService aluguelService;

    public AluguelControllerV2(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    // http://localhost:8080/alugueis?page=1&size=10
    @GetMapping
    public ResponseEntity<List<Aluguel>> findAllAlugueis(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        logger.info("Foi acessado o endpoint de veículos /alugueis");
        var alugueis = this.aluguelService.findAllAlugueis(page, size);
        return ResponseEntity.ok(alugueis);
    }

    // http://localhost:8080/alugueis/1
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Aluguel>> findAluguel(
            @PathVariable("id") Long id
    ) {
        logger.info("/alugueis/" + id);
        var aluguel = this.aluguelService.findAluguelById(id);
        return ResponseEntity.ok(aluguel);
    }

    @PostMapping(produces = "application/vnd.locatech.v2+json")
    public ResponseEntity<Void> saveAluguelV2(
            @Valid @RequestBody AluguelRequestDTO aluguel
    ) {
        logger.info("POST -> /alugueis");
        this.aluguelService.saveAluguel(aluguel);
        return ResponseEntity.status(201).build();
    }

    @PostMapping(produces = "application/vnd.locatech.v1+json")
    public ResponseEntity<Void> saveAluguelV1(
            @Valid @RequestBody AluguelRequestDTO aluguel
    ) {
        logger.info("POST -> /alugueis");
        this.aluguelService.saveAluguel(aluguel);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAluguel(
            @PathVariable("id") Long id,
            @RequestBody Aluguel aluguel
    ) {
        logger.info("PUT -> /alugueis/" + id);
        this.aluguelService.updateAluguel(aluguel, id);
        var status = HttpStatus.NO_CONTENT.value();
        return ResponseEntity.status(status).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAluguel(
            @PathVariable("id") Long id
    ) {
        logger.info("DELETE -> /alugueis/" + id);
        this.aluguelService.delete(id);
        return ResponseEntity.ok().build();
    }
}
