package br.com.talitafavore.FlowerPower.repository;

import br.com.talitafavore.FlowerPower.model.PlantModel;
import br.com.talitafavore.FlowerPower.model.TypeModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<TypeModel, Long> {
    Page<TypeModel> findAll(Pageable pageable);
    Page<TypeModel> findByNameContainsIgnoreCase(String name, Pageable pageable);
}
