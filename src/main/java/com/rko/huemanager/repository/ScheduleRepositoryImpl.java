package com.rko.huemanager.repository;

import com.rko.huemanager.domain.Schedule;
import com.rko.huemanager.repository.support.QuerydslRepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.rko.huemanager.domain.QSchedule.schedule;

public class ScheduleRepositoryImpl extends QuerydslRepositorySupport implements ScheduleCustomRepository {

    public ScheduleRepositoryImpl() {
        super(Schedule.class);
    }

    @Override
    public Page<Schedule> findByEmployeeId(Long employeeId, Pageable pageable) {
        return applyPagination(pageable, query -> query
                .selectFrom(schedule)
                .where(schedule.employee.id.eq(employeeId)));
    }
}
