package ar.edu.unq.desapp.grupoB.backenddesappapi.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class DTOClassesShould {
    @Test
    public void matchNaming(){
        JavaClasses classes =new ClassFileImporter().importPackages("ar.edu.unq.desapp.grupoB.backenddesappapi.model.DTO");
        classes().should().haveSimpleNameEndingWith("DTO").check(classes);
    }
}
