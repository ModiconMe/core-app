package edu.modicon.app.application.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import edu.modicon.app.application.request.UserLoginRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;

class UserLoginRequestTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    void toJson() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        UserLoginRequest dto = new UserLoginRequest("test@mail.com", "password");

        // when
        String json = objectMapper.writeValueAsString(dto);

        // then
        System.out.println(json);
    }

    @Test
    void toObjectError() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "\"\":{{\"email\":\"test\",\"password\":\"\"}}";

        // when
        UserLoginRequest dto = objectMapper.readValue(json, UserLoginRequest.class);
        var violations = validator.validate(dto);

        // then
        Iterator<ConstraintViolation<UserLoginRequest>> iterator = violations.iterator();
        assertThat(violations).hasSize(3);
        assertThat("должно иметь формат адреса электронной почты")
                .isEqualTo(iterator.next().getMessage());
        assertThat("размер должен находиться в диапазоне от 8 до 64")
                .isEqualTo(iterator.next().getMessage());
        assertThat("не должно быть пустым")
                .isEqualTo(iterator.next().getMessage());
    }

    @Test
    void toObject() throws JsonProcessingException {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "{\"email\":\"test@mail.com\",\"password\":\"password\"}";

        // when
        UserLoginRequest dto = objectMapper.readValue(json, UserLoginRequest.class);

        // then
        System.out.println(dto);
    }
}