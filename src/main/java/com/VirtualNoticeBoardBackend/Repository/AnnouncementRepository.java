package com.VirtualNoticeBoardBackend.Repository;

import com.VirtualNoticeBoardBackend.Model.Address;
import com.VirtualNoticeBoardBackend.Model.Announcement;
import com.VirtualNoticeBoardBackend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findAllByAddress(Address address);
    List<Announcement> findAllByUser(User user);
}
