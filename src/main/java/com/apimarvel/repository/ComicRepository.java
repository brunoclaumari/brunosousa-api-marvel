package com.apimarvel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apimarvel.entities.Comic;

@Repository
public interface ComicRepository extends JpaRepository<Comic, Long>  {

}
