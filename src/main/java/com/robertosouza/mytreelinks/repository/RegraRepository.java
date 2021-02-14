package com.robertosouza.mytreelinks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.robertosouza.mytreelinks.entity.RegraEntity;

@Repository
public interface RegraRepository extends JpaRepository<RegraEntity, Long> {

}
