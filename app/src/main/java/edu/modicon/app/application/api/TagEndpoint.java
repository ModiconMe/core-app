package edu.modicon.app.application.api;

import edu.modicon.app.application.dto.tag.GetTagsResponse;
import org.springframework.web.bind.annotation.GetMapping;

public interface TagEndpoint {

    @GetMapping("/tags")
    GetTagsResponse getTags();
}
