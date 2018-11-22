package com.aws.poc.whatsapp.controller;

import com.aws.poc.whatsapp.model.*;
import com.aws.poc.whatsapp.services.DefaultMatchingService;
import com.aws.poc.whatsapp.services.MatchingService;

public class NetworkingMatch {
   MatchingService matchingService = new DefaultMatchingService();

   public void match(User user, Region region, String area){
       matchingService.networkingMatch(user, region, area);
   }
}
