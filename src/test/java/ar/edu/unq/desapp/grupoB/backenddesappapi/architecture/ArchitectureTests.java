package ar.edu.unq.desapp.grupoB.backenddesappapi.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class ArchitectureTests {
    private JavaClasses baseClasses;
    @BeforeEach
    public void setup() {
        baseClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("ar.edu.unq.desapp.grupoB.backenddesappapi");
    }

    @Test
    public void dtoClassesShouldEndWithDTO(){
        classes().that().resideInAPackage("..model.DTO..")
                .should().haveSimpleNameEndingWith("DTO").check(baseClasses);
    }
    @Test
    public void serviceClassesShouldEndWithService(){
        classes().that().resideInAPackage("..services..")
                .should().haveSimpleNameEndingWith("Service").check(baseClasses);
    }

    @Test
    public void repositoryClassesShouldEndWithRepository(){
        classes().that().resideInAPackage("..repositories..")
                .should().haveSimpleNameEndingWith("Repository").check(baseClasses);
    }

    @Test
    public void layeredArchitectureShouldBeRespected(){
        layeredArchitecture()
                .layer("Controller").definedBy("..webservices..")
                .layer("Service").definedBy("..services..")
                .layer("Repository").definedBy("..repositories..")
                .layer("Security").definedBy("..model.Utils.security..")

                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Service").mayOnlyBeAccessedByLayers("Controller", "Service", "Security")
                .whereLayer("Repository").mayOnlyBeAccessedByLayers("Service")
                .check(baseClasses);
    }

    @Test
    public void serviceClassesShouldHaveSpringServiceAnnotation() {
        classes().that().resideInAPackage("..services..")
                .should().beAnnotatedWith("org.springframework.stereotype.Service")
            .check(baseClasses);
    }

    @Test
    public void controllerClassesShouldHaveSpringControllerAnnotation() {
        classes().that().resideInAPackage("..webservices..")
                .should().beAnnotatedWith("org.springframework.web.bind.annotation.RestController")
                .check(baseClasses);
    }
    @Test
    public void repositoryClassesShouldHaveRepositoryAnnotation(){
        classes().that().resideInAPackage("..repositories..")
                .should().beAnnotatedWith("org.springframework.stereotype.Repository")
                .check(baseClasses);
    }
}
