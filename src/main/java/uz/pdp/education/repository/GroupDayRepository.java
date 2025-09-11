package uz.pdp.education.repository;

import org.springframework.stereotype.Repository;
import uz.pdp.education.entity.GroupDay;

@Repository
public interface GroupDayRepository extends GenericRepository<GroupDay, Long> {
}