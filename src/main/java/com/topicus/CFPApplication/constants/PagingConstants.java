<<<<<<< HEAD:src/main/java/com/topicus/CFPApplication/config/paging/PagingConstants.java
package com.topicus.CFPApplication.config.paging;
=======
package com.topicus.CFPApplication.constants;
>>>>>>> f07616e... E-mail systeem eruit gesloopt -Javiel:src/main/java/com/topicus/CFPApplication/constants/PagingConstants.java

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
