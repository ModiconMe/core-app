package edu.modicon.app.application.api.controller;

import edu.modicon.app.application.api.BaseController;
import edu.modicon.app.application.api.TagEndpoint;
import edu.modicon.app.application.dto.tag.GetTagsRequest;
import edu.modicon.app.application.dto.tag.GetTagsResponse;

public class TagController extends BaseController implements TagEndpoint {

    @Override
    public GetTagsResponse getTags() {
        return bus.executeQuery(new GetTagsRequest());
    }
}
