package com.mjubus.server.service.login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mjubus.server.dto.response.LoginResponse;
import com.mjubus.server.enums.LoginStrategyName;

public interface LoginStrategy {

    LoginResponse login(String encryptedData);

    LoginStrategyName getStrategyName();
}
