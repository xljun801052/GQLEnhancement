package org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes.inheritedTypes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * TODO
 *
 * @author Administrator
 * @date 2024/1/26 4:51 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentType implements GrandType{

    private Integer pid;

    private String skinColor;

    private String hairColor;


}
