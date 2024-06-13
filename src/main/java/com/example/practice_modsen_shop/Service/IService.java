package com.example.practice_modsen_shop.Service;

import java.util.List;

public interface IService<RQ, RP> {

    public RP save(RQ item);

    public List<RP> getAll(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    public RP update(RQ item);

    public RP delete(Long id);

    public RP getById(Long id);
}
