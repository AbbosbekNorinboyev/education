package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.education.entity.Groups;

public interface GroupsRepository extends JpaRepository<Groups, Integer> {
    @Query(value = "delete from groups g where g.id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteGroupByTeacherId(Integer id);
}