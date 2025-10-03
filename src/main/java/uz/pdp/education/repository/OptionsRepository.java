package uz.pdp.education.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.education.entity.Options;

import java.util.List;

@Repository
public interface OptionsRepository extends JpaRepository<Options, Long> {
    List<Options> findAllByQuestionId(Long questionId);

    @Transactional
    @Modifying
    @Query(value = "delete from options as o where o.question_id = ?1", nativeQuery = true)
    void deleteAllByQuestionId(Long questionId);
}