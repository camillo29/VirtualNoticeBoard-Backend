package com.VirtualNoticeBoardBackend.Repository;

import com.VirtualNoticeBoardBackend.Model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
}
