package br.com.talitafavore.FlowerPower.controller;

import br.com.talitafavore.FlowerPower.dto.PlantDto;
import br.com.talitafavore.FlowerPower.exception.CustomExceptionResponse;
import br.com.talitafavore.FlowerPower.service.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Plants", description = "Endpoint usado para operações que envolvem Plants")
@RestController
@RequestMapping("/api/plants")
public class PlantController {

    @Autowired
    private PlantService plantService;

    // Criação de uma nova planta
    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Cria ou cadastra uma nova Plant (Planta)", tags = {"Plants"},responses = {
            @ApiResponse(description = "CREATED", responseCode = "201", content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PlantDto.class)
            )}),
    })
    public ResponseEntity<PlantDto> create(@RequestBody PlantDto plantDto) {
        PlantDto plant = plantService.create(plantDto);
        return new ResponseEntity<>(plant, HttpStatus.CREATED);
    }


    // Busca uma planta por ID
    @GetMapping("/{id}")
    @Operation(summary = "Recuperar uma Plant (ou Planta) mediante um ID informado", tags = {"Plants"},
            responses = {
                    @ApiResponse(description = "Plant recuperada com sucesso!", responseCode = "200", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlantDto.class)
                    )}),
                    @ApiResponse(description = "Resource not found", responseCode = "404", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomExceptionResponse.class)
                    )})
            }
    )
    public ResponseEntity<PlantDto> findById(@PathVariable(name = "id") long id) {
        PlantDto plant = plantService.findById(id);
        return new ResponseEntity<>(plant, HttpStatus.OK);
    }

    // Atualização de uma planta existente

    @PutMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Atualiza uma Plant (Planta) existente", tags = {"Plants"},
            responses = {
                    @ApiResponse(description = "Plant atualizada com sucesso!", responseCode = "200", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PlantDto.class)
                    )}),
                    @ApiResponse(description = "Resource not found", responseCode = "404", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomExceptionResponse.class)
                    )}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomExceptionResponse.class)
                    )})
            }
    )
    public ResponseEntity<PlantDto> update(@RequestBody PlantDto plantDto) {
        PlantDto updatedPlant = plantService.update(plantDto);
        return new ResponseEntity<>(updatedPlant, HttpStatus.OK);
    }

    // Exclusão de uma planta pelo ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma Plant (Planta) pelo ID informado", tags = {"Plants"},
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Resource not found", responseCode = "404", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomExceptionResponse.class)
                    )}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomExceptionResponse.class)
                    )})
            }
    )
    public ResponseEntity<Void> delete(@PathVariable(name = "id") long id) {
        plantService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Listagem de todas as plantas com paginação e ordenação
    @GetMapping(produces = "application/json")
    @Operation(summary = "Lista todas as Plants (Plantas) com paginação e ordenação", tags = {"Plants"},
            responses = {
                    @ApiResponse(description = "Lista de Plants recuperada com sucesso!", responseCode = "200", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PagedModel.class)
                    )}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomExceptionResponse.class)
                    )})
            }
    )
    public ResponseEntity<PagedModel<EntityModel<PlantDto>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<PlantDto> assembler
    ) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        Page<PlantDto> plants = plantService.findAll(pageable);

        // Converte a página de PlantDto para PagedModel<EntityModel<PlantDto>>
        PagedModel<EntityModel<PlantDto>> pagedModel = assembler.toModel(plants);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }



}
