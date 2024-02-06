package org.xlys.graphqlspqr.graphqlspqrwithspringboot.config.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xlys.graphqlspqr.graphqlspqrwithspringboot.service.resolver.InheritedPropertiesFeatureResolver;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @author Administrator
 * @date 12/09/2023 11:01 AM
 */
@Configuration
public class GraphqlConfig {

    private static final String rootPackage = "org.xlys.graphqlspqr.graphqlspqrwithspringboot.gqltypes";

    @Resource
    InheritedPropertiesFeatureResolver inheritedPropertiesFeatureResolver;


//    @Autowired
//    private BookResolver bookResolver;
//
//    @Autowired
//    private AuthorResolver authorResolver;
//
//    @Autowired
//    private HobbyResolver hobbyResolver;

//    @Bean
//    public ExtensionProvider<GeneratorConfiguration, TypeMapper> customizedMapper() {
//        return (config, defaults) -> defaults.replace(ObjectTypeMapper.class, __ -> new CustomizedMapper());
//    }

    @Bean
    public GraphQL graphQL() {
        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withBasePackages(rootPackage)
//                .withOperationsFromSingletons(bookResolver,authorResolver,hobbyResolver)
//                .withOperationsFromSingleton(bookResolver,Book.class,new BeanResolverBuilder(rootPackage))
//                .withOperationsFromSingleton(authorResolver, Author.class,new BeanResolverBuilder(rootPackage))
//                .withOperationsFromSingleton(hobbyResolver, Hobby.class,new BeanResolverBuilder(rootPackage))
                .withOperationsFromSingleton(inheritedPropertiesFeatureResolver)
//                .withTypeMappers(new TypeMapper() {
//                    @Override
//                    public GraphQLOutputType toGraphQLType(AnnotatedType javaType, Set<Class<? extends TypeMapper>> mappersToSkip, TypeMappingEnvironment env) {
//                        return new  GraphQLOutputType() {
//                            @Override
//                            public String getName() {
//                                return "Int";
//                            }
//                        };
//                    }
//
//                    @Override
//                    public GraphQLInputType toGraphQLInputType(AnnotatedType javaType, Set<Class<? extends TypeMapper>> mappersToSkip, TypeMappingEnvironment env) {
//                        return null;
//                    }
//
//                    @Override
//                    public boolean supports(AnnotatedElement element, AnnotatedType type) {
//                        return false;
//                    }
//                })
//                .withOperationsFromSingleton(authorResolver)
//                .withOperationsFromSingleton(hobbyResolver)
                .generate();
        return new GraphQL.Builder(schema).build();
    }

//    @Bean
//    public ExtensionProvider<GeneratorConfiguration, TypeMapper> customTypeMappers() {
//        //Insert a custom mapper after the built-in IdAdapter (which is generally a safe position)
//        return (config, current) -> current.insertAfter(IdAdapter.class,
//                new TypeMapper() {
//                    @Override
//                    public GraphQLOutputType toGraphQLType(AnnotatedType javaType, OperationMapper operationMapper, Set<Class<? extends TypeMapper>> mappersToSkip, BuildContext buildContext) {
//                        return new GraphQLOutputType() {
//                            @Override
//                            public String getName() {
//                                return "Int";
//                            }
//                        };
//                    }
//
//                    @Override
//                    public GraphQLInputType toGraphQLInputType(AnnotatedType javaType, OperationMapper operationMapper, Set<Class<? extends TypeMapper>> mappersToSkip, BuildContext buildContext) {
//                        return new GraphQLInputType() {
//                            @Override
//                            public String getName() {
//                                return "Int";
//                            }
//                        };
//                    }
//
//                    @Override
//                    public boolean supports(AnnotatedType type) {
//                        return true;
//                    }
//                }
//        );
//    }

}
