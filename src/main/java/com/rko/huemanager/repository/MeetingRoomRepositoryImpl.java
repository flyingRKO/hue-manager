package com.rko.huemanager.repository;

import com.querydsl.core.BooleanBuilder;
import com.rko.huemanager.domain.MeetingRoom;
import com.rko.huemanager.domain.QMeetingRoom;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.repository.support.QuerydslRepositorySupport;

import java.time.LocalDate;
import java.time.LocalTime;
import static com.rko.huemanager.domain.QMeetingRoom.meetingRoom;
public class MeetingRoomRepositoryImpl extends QuerydslRepositorySupport implements MeetingRoomCustomRepository {
    public MeetingRoomRepositoryImpl() {
        super(MeetingRoom.class);
    }
    @Override
    public boolean existsByDateAndTimeRange(LocalDate usageDate, LocalTime startTime, LocalTime endTime) {
        BooleanBuilder builder = new BooleanBuilder();

        builder.and(meetingRoom.usageDate.eq(usageDate))
                .and(meetingRoom.startTime.lt(endTime))
                .and(meetingRoom.endTime.gt(startTime))
                .and(meetingRoom.status.eq(ScheduleStatus.APPROVED));

        return getQueryFactory()
                .selectFrom(meetingRoom)
                .where(builder)
                .fetchCount() > 0;
    }
}
