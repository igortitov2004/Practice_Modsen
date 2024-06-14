package com.example.practice_modsen_shop.run;

import java.util.List;
import java.util.Optional;

public interface RunRepository {

    List<Run> findAll();

    Optional<Run> findById(Integer id);

    void create(Run run);

    void update(Run run, Integer id);

    void delete(Integer id);

    int count();

    void saveAll(List<Run> runs);

    List<Run> findByLocation(String location);
}
