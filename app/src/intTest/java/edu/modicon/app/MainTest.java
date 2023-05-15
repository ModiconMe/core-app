package edu.modicon.app;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = MainTest.class)
class MainTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    void applicationContext() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    void shouldReturn_whenDoSmth_withCondition() {
        // given
        SomeService someService = new SomeService();
        // when
        someService.hello();

        // then
    }
}
