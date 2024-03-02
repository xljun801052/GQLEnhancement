package org.xlys.graphqlspqr.graphqlspqrwithspringboot.features.responseEncapsulation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO
 *
 * @author Administrator
 * @date 2024/3/2 8:12 AM
 */
@Data
@NoArgsConstructor
public class CustomizedGQLResponse<T> {

    private Integer code;

    private String desc;

    private Date date = new Date();

    private T data;

    private List<Map<String,Object>> errors;

    public CustomizedGQLResponse(Integer code, String desc, T data, List<Map<String,Object>> errors) {
        this.code = code;
        this.desc = desc;
        this.data = data;
        this.errors = errors;
    }
}
