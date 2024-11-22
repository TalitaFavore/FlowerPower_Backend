package br.com.talitafavore.FlowerPower.repository;

import br.com.talitafavore.FlowerPower.model.PlantModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<PlantModel, Long> {

    Page<PlantModel> findAll(Pageable pageable);
    Page<PlantModel> findByNameContainsIgnoreCase(String name, Pageable pageable);
}
