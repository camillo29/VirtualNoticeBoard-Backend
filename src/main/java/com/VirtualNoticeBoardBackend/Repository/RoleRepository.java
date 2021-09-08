package com.VirtualNoticeBoardBackend.Repository;

import com.VirtualNoticeBoardBackend.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
