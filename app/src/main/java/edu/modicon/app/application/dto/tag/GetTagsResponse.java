package edu.modicon.app.application.dto.tag;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class GetTagsResponse {
    private final List<String> tags;

    @JsonCreator
    public GetTagsResponse(@JsonProperty("tags") List<String> tags) {
        this.tags = tags;
    }
}
