package com.teamthree.conferencescheduler.service.api;

import com.teamthree.conferencescheduler.dto.session.SessionDto;
import com.teamthree.conferencescheduler.entities.Session;

public interface SessionService {

    Session createSession(SessionDto dto);
}
