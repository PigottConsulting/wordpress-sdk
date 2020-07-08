package consulting.pigott.wordpress.model;


import lombok.Data;

import java.util.ArrayList;

@Data
public class PagedResponse<T> extends ArrayList<T> {

    private Integer pages;
    private Integer total;

}
