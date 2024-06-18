package com.modsen.practice.service;

import java.util.List;

public interface ICrudService <RQ, RP>{

    RP getById(Long id);

    List<RP> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    RP save(RQ requestTo);

    RP delete(Long id);

    RP update(RQ requestTo);
}
