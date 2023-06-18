package com.dev.springbatch.repository;

import com.dev.springbatch.entity.Gas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GasRepository extends JpaRepository<Gas, Integer> {

}
