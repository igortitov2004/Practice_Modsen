package com.example.practice_modsen_shop.services;

import java.util.List;

public interface ICrudService <RQ, RP>{

    RP getById(Long id);

    List<RP> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    RP save(RQ requestTo);

    RP delete(Long id);

    RP update(RQ requestTo);
}
