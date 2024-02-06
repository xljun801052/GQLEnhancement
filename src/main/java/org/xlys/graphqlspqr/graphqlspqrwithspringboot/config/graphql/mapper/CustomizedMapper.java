//package org.xlys.graphqlspqr.graphqlspqrwithspringboot.config.graphql.mapper;
//
//import graphql.schema.GraphQLFieldDefinition;
//import graphql.schema.GraphQLInputObjectType;
//import graphql.schema.GraphQLObjectType;
//import io.leangen.graphql.generator.BuildContext;
//import io.leangen.graphql.generator.mapping.TypeMappingEnvironment;
//import io.leangen.graphql.generator.mapping.common.ObjectTypeMapper;
//import io.leangen.graphql.metadata.strategy.value.InputFieldBuilderParams;
//import io.leangen.graphql.util.ClassUtils;
//import org.xlys.graphqlspqr.graphqlspqrwithspringboot.annotation.Invisible;
//
//import java.lang.reflect.AnnotatedType;
//import java.lang.reflect.Field;
//import java.util.Collections;
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//import static graphql.schema.GraphQLInputObjectType.newInputObject;
//
///**
// *
// * @author Administrator
// * @date 12/09/2023 11:04 AM
// */
//public class CustomizedMapper extends ObjectTypeMapper {
//
//
//    @Override
//    public GraphQLInputObjectType toGraphQLInputType(String typeName, AnnotatedType javaType, TypeMappingEnvironment env) {
//        BuildContext buildContext = env.buildContext;
//
//        GraphQLInputObjectType.Builder typeBuilder = newInputObject()
//                .name(typeName)
//                .description(buildContext.typeInfoGenerator.generateInputTypeDescription(javaType, buildContext.messageBundle));
//
//        InputFieldBuilderParams params = InputFieldBuilderParams.builder()
//                .withType(javaType)
//                .withEnvironment(buildContext.globalEnvironment)
//                .withConcreteSubTypes(buildContext.abstractInputHandler.findConcreteSubTypes(ClassUtils.getRawType(javaType.getType()), buildContext))
//                .build();
//        buildContext.inputFieldBuilder.getInputFields(params).forEach(field -> typeBuilder.field(env.operationMapper.toGraphQLInputField(field, buildContext)));
//        if (ClassUtils.isAbstract(javaType)) {
//            getTypeDiscriminatorField(params, buildContext).ifPresent(typeBuilder::field);
//        }
//
//        buildContext.directiveBuilder.buildInputObjectTypeDirectives(javaType, buildContext.directiveBuilderParams()).forEach(directive ->
//                typeBuilder.withAppliedDirective(env.operationMapper.toGraphQLAppliedDirective(directive, buildContext)));
//        typeBuilder.comparatorRegistry(buildContext.comparatorRegistry(javaType));
//        GraphQLInputObjectType type = typeBuilder.build();
//        buildContext.typeRegistry.registerMapping(type.getName(), javaType);
//        return type;
//    }
//
//    @Override
//    protected List<GraphQLFieldDefinition> getFields(String typeName, AnnotatedType javaType, TypeMappingEnvironment env) {
//        List<GraphQLFieldDefinition> fields = super.getFields(typeName, javaType, env);
//        try {
//            Class<?> targetClz = Class.forName(javaType.getType().getTypeName());
//            return fields.stream().filter(f -> {
//                boolean flag = false;
//                try {
//                    Field targetField = targetClz.getDeclaredField(f.getName());
//                    targetField.setAccessible(true);
//                    flag =  !targetField.isAnnotationPresent(Invisible.class);
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                }
//                return flag;
//            }).collect(Collectors.toList());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return fields;
//    }
//}
