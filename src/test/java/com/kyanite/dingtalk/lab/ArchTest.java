package com.kyanite.dingtalk.lab;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.kyanite.dingtalk.lab");

        noClasses()
            .that()
            .resideInAnyPackage("com.kyanite.dingtalk.lab.service..")
            .or()
            .resideInAnyPackage("com.kyanite.dingtalk.lab.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.kyanite.dingtalk.lab.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
