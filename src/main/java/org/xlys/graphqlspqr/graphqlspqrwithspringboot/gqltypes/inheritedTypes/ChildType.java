package org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.inheritedTypes;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * TODO
 *
 * @author Administrator
 * @date 2024/1/26 4:54 PM
 */
@Data
@AllArgsConstructor
public class ChildType extends ParentType{

    private Integer cId;

    private String cName;

    private Double score;
}
