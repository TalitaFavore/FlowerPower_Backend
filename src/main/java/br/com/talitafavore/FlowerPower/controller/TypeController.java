package br.com.talitafavore.FlowerPower.controller;

import br.com.talitafavore.FlowerPower.dto.PlantDto;
import br.com.talitafavore.FlowerPower.dto.TypeDto;
import br.com.talitafavore.FlowerPower.exception.CustomExceptionResponse;
import br.com.talitafavore.FlowerPower.service.TypeService;
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

@Tag(name = "Types", description = "Endpoint usado para operações que envolvem Types")
@RestController
@RequestMapping("/api/types")
public class TypeController {

    @Autowired
    private TypeService typeService;

    // Criação de um novo tipo
    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Cria ou cadastra um novo Type (Tipo)", tags = {"Types"},responses = {
            @ApiResponse(description = "CREATED", responseCode = "201", content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TypeDto.class)
            )}),
    })
    public ResponseEntity<TypeDto> create(@RequestBody TypeDto typeDto) {
        TypeDto type = typeService.create(typeDto);
        return new ResponseEntity<>(type, HttpStatus.CREATED);
    }

    // Busca um tipo por ID
    @GetMapping("/{id}")
    @Operation(summary = "Recuperar um Type (ou Tipo) mediante um ID informado", tags = {"Types"},
            responses = {
                    @ApiResponse(description = "Type recuperado com sucesso!", responseCode = "200", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TypeDto.class)
                    )}),
                    @ApiResponse(description = "Resource not found", responseCode = "404", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomExceptionResponse.class)
                    )})
            }
    )
    public ResponseEntity<TypeDto> findById(@PathVariable(name = "id") long id) {
        TypeDto type = typeService.findById(id);
        return new ResponseEntity<>(type, HttpStatus.OK);
    }

    // Atualização de um tipo existente
    @PutMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Atualiza um Type (Tipo) existente", tags = {"Types"},
            responses = {
                    @ApiResponse(description = "Type atualizado com sucesso!", responseCode = "200", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TypeDto.class)
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
    public ResponseEntity<TypeDto> update(@RequestBody TypeDto typeDto) {
        TypeDto updatedType = typeService.update(typeDto);
        return new ResponseEntity<>(updatedType, HttpStatus.OK);
    }

    // Exclusão de um tipo pelo ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um Type (Tipo) pelo ID informado", tags = {"Types"},
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
        typeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Listagem de todos os tipos com paginação e ordenação
    @GetMapping(produces = "application/json")
    @Operation(summary = "Lista todos os Types (Tipos) com paginação e ordenação", tags = {"Types"},
            responses = {
                    @ApiResponse(description = "Lista de Types recuperada com sucesso!", responseCode = "200", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PagedModel.class)
                    )}),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CustomExceptionResponse.class)
                    )})
            }
    )
    public ResponseEntity<PagedModel<EntityModel<TypeDto>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            PagedResourcesAssembler<TypeDto> assembler
    ) {
        Sort.Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "name"));
        Page<TypeDto> types = typeService.findAll(pageable);

        // Converte a página de TypeDto para PagedModel<EntityModel<TypeDto>>
        PagedModel<EntityModel<TypeDto>> pagedModel = assembler.toModel(types);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }
}
