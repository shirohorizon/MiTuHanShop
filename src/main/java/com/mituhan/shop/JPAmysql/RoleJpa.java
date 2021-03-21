package com.mituhan.shop.JPAmysql;

import com.mituhan.shop.model.RoleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpa extends CrudRepository<RoleModel, Long>,
        PagingAndSortingRepository<RoleModel,Long>, JpaRepository<RoleModel, Long> {
}
