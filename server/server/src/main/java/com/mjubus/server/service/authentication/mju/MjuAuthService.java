package com.mjubus.server.service.authentication.mju;

import com.mjubus.server.dto.request.MjuAuthInfoRequest;
import com.mjubus.server.dto.response.MjuAuthInfoResponse;
import org.springframework.http.ResponseEntity;

public interface MjuAuthService {
    public MjuAuthInfoResponse getAuthInfo(MjuAuthInfoRequest request);
    public String changeUserRole(MjuAuthInfoResponse authInfoResponse);
}
