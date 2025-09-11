package uz.pdp.education.repository;

import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.AboutMe;

@Repository
public interface AboutMeRepository extends GenericRepository<AboutMe, Long> {
}