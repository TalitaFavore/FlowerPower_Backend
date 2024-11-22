package br.com.talitafavore.FlowerPower.service;

import br.com.talitafavore.FlowerPower.dto.TypeDto;
import br.com.talitafavore.FlowerPower.exception.ResourceNotFoundException;
import br.com.talitafavore.FlowerPower.mapper.CustomModelMapper;
import br.com.talitafavore.FlowerPower.model.TypeModel;
import br.com.talitafavore.FlowerPower.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    // Criar um novo tipo
    public TypeDto create(TypeDto typeDto) {
        TypeModel typeModel = CustomModelMapper.parseObject(typeDto, TypeModel.class);
        return CustomModelMapper.parseObject(typeRepository.save(typeModel), TypeDto.class);
    }

    // Encontrar tipo por ID
    public TypeDto findById(long id) {
        TypeModel typeModel = typeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo não encontrado!"));
        return CustomModelMapper.parseObject(typeModel, TypeDto.class);
    }

    // Atualizar um tipo existente
    public TypeDto update(TypeDto typeDto) {
        TypeModel typeModel = typeRepository.findById(typeDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tipo não encontrado!"));

        typeModel.setName(typeDto.getName());

        return CustomModelMapper.parseObject(typeRepository.save(typeModel), TypeDto.class);
    }

    // Deletar tipo por ID
    public void delete(long id) {
        TypeModel typeModel = typeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo não encontrado!"));
        typeRepository.delete(typeModel);
    }

    // Encontrar todos os tipos com paginação
    public Page<TypeDto> findAll(Pageable pageable) {
        Page<TypeModel> typesPage = typeRepository.findAll(pageable);
        return typesPage.map(type -> CustomModelMapper.parseObject(type, TypeDto.class));
    }
}
