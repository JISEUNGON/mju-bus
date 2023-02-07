package com.mjubus.server.service.login;

import com.mjubus.server.dto.response.LoginResponse;
import com.mjubus.server.enums.LoginStrategyName;

public interface LoginStrategy {

    LoginResponse login(String encryptedData);

    LoginStrategyName getStrategyName();
}
