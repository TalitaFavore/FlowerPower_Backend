package br.com.talitafavore.FlowerPower.service;

import br.com.talitafavore.FlowerPower.dto.PlantDto;
import br.com.talitafavore.FlowerPower.exception.ResourceNotFoundException;
import br.com.talitafavore.FlowerPower.mapper.CustomModelMapper;
import br.com.talitafavore.FlowerPower.model.PlantModel;
import br.com.talitafavore.FlowerPower.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PlantService {

    @Autowired
    private PlantRepository plantRepository;

    // Criar uma nova planta
    public PlantDto create(PlantDto plantDto) {
        PlantModel plantModel = CustomModelMapper.parseObject(plantDto, PlantModel.class);
        return CustomModelMapper.parseObject(plantRepository.save(plantModel), PlantDto.class);
    }

    // Encontrar planta por ID
    public PlantDto findById(long id) {
        PlantModel plantModel = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta não encontrada!"));
        return CustomModelMapper.parseObject(plantModel, PlantDto.class);
    }

    // Atualizar uma planta existente
    public PlantDto update(PlantDto plantDto) {
        PlantModel plantModel = plantRepository.findById(plantDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Planta não encontrada!"));

        plantModel.setName(plantDto.getName());
        plantModel.setSubtitle(plantDto.getSubtitle());
        plantModel.setPrice(plantDto.getPrice());
        plantModel.setDescription(plantDto.getDescription());
        plantModel.setImg(plantDto.getImg());

        return CustomModelMapper.parseObject(plantRepository.save(plantModel), PlantDto.class);
    }

    // Deletar planta por ID
    public void delete(long id) {
        PlantModel plantModel = plantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Planta não encontrada!"));
        plantRepository.delete(plantModel);
    }

    // Encontrar todas as plantas com paginação e ordenação
    public Page<PlantDto> findAll(Pageable pageable) {
        Page<PlantModel> plantsPage = plantRepository.findAll(pageable);
        return plantsPage.map(plant -> CustomModelMapper.parseObject(plant, PlantDto.class));
    }
}
