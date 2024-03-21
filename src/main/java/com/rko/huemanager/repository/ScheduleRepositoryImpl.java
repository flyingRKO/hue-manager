package com.rko.huemanager.repository;

import com.querydsl.core.BooleanBuilder;
import com.rko.huemanager.domain.Schedule;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.domain.constant.ScheduleType;
import com.rko.huemanager.repository.support.QuerydslRepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

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

    @Override
    public Page<Schedule> findSearchSchedules(LocalDate startDate, LocalDate endDate, ScheduleType type, ScheduleStatus status, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (startDate != null && endDate == null) { builder.and(schedule.endDate.goe(startDate)); }
        else if (startDate == null && endDate != null) { builder.and(schedule.startDate.loe(endDate)); }
        else if (startDate != null && endDate != null) { builder.and(schedule.startDate.loe(endDate).and(schedule.endDate.goe(startDate))); }
        if (type != null) { builder.and(schedule.type.eq(type)); }
        if (status != null) { builder.and(schedule.status.eq(status)); }

        return applyPagination(pageable, query -> query
                .selectFrom(schedule)
                .where(builder));
    }
}
