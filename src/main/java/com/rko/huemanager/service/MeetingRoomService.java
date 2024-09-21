package com.rko.huemanager.service;

import com.rko.huemanager.domain.Employee;
import com.rko.huemanager.domain.MeetingRoom;
import com.rko.huemanager.domain.constant.ScheduleStatus;
import com.rko.huemanager.dto.request.MeetingRoomRequest;
import com.rko.huemanager.dto.response.MeetingRoomDto;
import com.rko.huemanager.exception.ErrorCode;
import com.rko.huemanager.exception.HueManagerException;
import com.rko.huemanager.repository.EmployeeRepository;
import com.rko.huemanager.repository.MeetingRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MeetingRoomService {
    private final MeetingRoomRepository meetingRoomRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public void createMeetingRoom(Employee employee, MeetingRoomRequest request) {

        boolean isRoomBooked = meetingRoomRepository.existsByDateAndTimeRange(
                request.usageDate(),
                request.startTime(),
                request.endTime()
        );

        if (isRoomBooked) {
            throw new HueManagerException(ErrorCode.MEETING_ROOM_ALREADY_BOOKED);
        }

        MeetingRoom meetingRoom = MeetingRoom.of(
                request.usageDate(),
                request.startTime(),
                request.endTime(),
                employee,
                request.usagePurpose(),
                ScheduleStatus.PENDING
        );
        meetingRoomRepository.save(meetingRoom);
    }

    @Transactional
    public void updateMeetingRoom(Employee employee, Long meetingRoomId, MeetingRoomRequest request) {

        MeetingRoom meetingRoom = meetingRoomRepository.findById(meetingRoomId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.MEETING_ROOM_NOT_FOUND, String.format("meetingRoomId is %s", meetingRoomId)));

        if (!meetingRoom.getEmployee().getId().equals(employee.getId())) {
            throw new HueManagerException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        if (!meetingRoom.getStatus().equals(ScheduleStatus.PENDING)) {
            throw new HueManagerException(ErrorCode.INVALID_STATUS);
        }

        meetingRoom.setUsageDate(request.usageDate());
        meetingRoom.setStartTime(request.startTime());
        meetingRoom.setEndTime(request.endTime());
        meetingRoom.setUsagePurpose(request.usagePurpose());
    }

    @Transactional
    public void deleteMeetingRoom(Employee employee, Long meetingRoomId) {
        MeetingRoom meetingRoom = meetingRoomRepository.findById(meetingRoomId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.MEETING_ROOM_NOT_FOUND, String.format("meetingRoomId is %s", meetingRoomId)));

        if (!meetingRoom.getEmployee().getId().equals(employee.getId())) {
            throw new HueManagerException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        meetingRoomRepository.delete(meetingRoom);
    }

    @Transactional(readOnly = true)
    public MeetingRoomDto getMeetingRoom(Employee employee, Long meetingRoomId) {
        MeetingRoom meetingRoom = meetingRoomRepository.findById(meetingRoomId)
                .orElseThrow(() -> new HueManagerException(ErrorCode.MEETING_ROOM_NOT_FOUND, String.format("meetingRoomId is %s", meetingRoomId)));

        if (!meetingRoom.getEmployee().getId().equals(employee.getId())) {
            throw new HueManagerException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        return MeetingRoomDto.from(meetingRoom);
    }

    @Transactional(readOnly = true)
    public Page<MeetingRoomDto> getAllMeetingRooms(Employee employee, Pageable pageable) {
        Page<MeetingRoom> meetingRooms = meetingRoomRepository.findAllByEmployee(employee, pageable);
        return meetingRooms.map(MeetingRoomDto::from);
    }
}
