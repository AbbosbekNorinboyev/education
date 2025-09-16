package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}