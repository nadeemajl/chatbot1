package com.aws.poc.whatsapp.services;

import com.aws.poc.whatsapp.model.Region;
import com.aws.poc.whatsapp.model.User;

public interface MatchingService {
    boolean networkingMatch(User user, Region region, String area);


}
