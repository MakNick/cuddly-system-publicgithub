package com.topicus.CFPApplication.constants;

import java.util.Arrays;
import java.util.List;

public class PagingConstants {

    private static final int DEFAULT_LIMIT = 25;


    public static List<Integer> defaultPageConfigurations(int page, int limit){

        if (page > 0) page -= 1;
        if(page < 0 ) page = 0;
        if(limit < 0 || limit > 100) limit = DEFAULT_LIMIT;

        return Arrays.asList(page,limit);
    }
}
