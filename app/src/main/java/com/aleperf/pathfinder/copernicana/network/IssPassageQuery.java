package com.aleperf.pathfinder.copernicana.network;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.IssPassage;

import java.util.List;

public class IssPassageQuery {

    String message;
    List<IssPassage> response;

    public List<IssPassage> getPassages(){
        return response;
    }
}
