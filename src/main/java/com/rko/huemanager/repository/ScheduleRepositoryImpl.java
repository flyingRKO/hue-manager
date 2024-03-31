package com.rko.huemanager.repository;

import com.querydsl.core.BooleanBuilder;
import com.rko.huemanager.domain.Schedule;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.domain.constant.ScheduleType;
import com.rko.huemanager.repository.support.QuerydslRepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
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
    public Page<Schedule> findSearchSchedules(LocalDate startDate, LocalDate endDate, ScheduleType type, ScheduleStatus status, String name, String position, String department, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        if (startDate != null && endDate == null) { builder.and(schedule.endDate.goe(startDate)); }
        else if (startDate == null && endDate != null) { builder.and(schedule.startDate.loe(endDate)); }
        else if (startDate != null && endDate != null) { builder.and(schedule.startDate.loe(endDate).and(schedule.endDate.goe(startDate))); }
        if (type != null) { builder.and(schedule.type.eq(type)); }
        if (status != null) { builder.and(schedule.status.eq(status)); }
        if (name != null){ builder.and(schedule.employee.name.containsIgnoreCase(name)); }
        if (position != null){ builder.and(schedule.employee.position.containsIgnoreCase(position)); }
        if (department != null){ builder.and(schedule.employee.department.containsIgnoreCase(department)); }


        return applyPagination(pageable, query -> query
                .selectFrom(schedule)
                .where(builder));
    }

    @Override
    public Page<Schedule> findDailySchedules(LocalDate date, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(schedule.startDate.loe(date).and(schedule.endDate.goe(date)));
        builder.and(schedule.status.eq(ScheduleStatus.APPROVED));

        return applyPagination(pageable, query -> query
                .selectFrom(schedule)
                .where(builder));
    }

    @Override
    public Page<Schedule> findWeeklySchedules(LocalDate date, Pageable pageable) {
        LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = date.with(DayOfWeek.SUNDAY);

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(schedule.startDate.loe(endOfWeek).and(schedule.endDate.goe(startOfWeek)));
        builder.and(schedule.status.eq(ScheduleStatus.APPROVED));

        return applyPagination(pageable, query -> query
                .selectFrom(schedule)
                .where(builder));
    }

    @Override
    public Page<Schedule> findMonthlySchedules(LocalDate date, Pageable pageable) {
        LocalDate startOfMonth = date.withDayOfMonth(1);
        LocalDate endOfMonth = date.withDayOfMonth(date.lengthOfMonth());

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(schedule.startDate.loe(endOfMonth).and(schedule.endDate.goe(startOfMonth)));
        builder.and(schedule.status.eq(ScheduleStatus.APPROVED));

        return applyPagination(pageable, query -> query
                .selectFrom(schedule)
                .where(builder));
    }
}
