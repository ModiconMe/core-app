package edu.modicon.app.application.api;

import edu.modicon.app.infrastructure.bus.Bus;
import edu.modicon.app.infrastructure.security.AppUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

public abstract class BaseController {

    @Autowired
    protected Bus bus;

    protected AppUserDetails getCurrentUser() {
        return (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
