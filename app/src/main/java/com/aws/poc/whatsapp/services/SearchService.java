package com.aws.poc.whatsapp.services;

import org.json.JSONObject;

import java.util.Map;

public interface SearchService {
    public JSONObject query();
    public JSONObject queryByParameters(Map args);
    public String getUrl();
}
