package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.education.entity.Groups;

import java.util.List;

@Repository
public interface GroupsRepository extends GenericRepository<Groups, Long> {
    @Query(value = "delete from groups g where g.id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void deleteGroupByTeacherId(Integer id);

    List<Groups> findAllByTeacherId(Long teacherId);

    List<Groups> findAllBySubjectId(Long subjectId);

    @Query(value = "select * from groups g" +
            "         left join group_support gs" +
            "                   on g.id = gs.group_id" +
            " where gs.support_id = ?1",
            nativeQuery = true)
    List<Groups> findAllBySupportTeacherId(Long supportTeacherId);

    List<Groups> findAllByStatus(String status);
}