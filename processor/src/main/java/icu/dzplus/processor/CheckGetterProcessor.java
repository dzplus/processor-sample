package icu.dzplus.processor;

import icu.dzplus.annotation.Check;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.Set;


/**
 * package foo;     // PackageElement
 * <p>
 * class Foo {      // TypeElement
 * int a;           // VariableElement
 * static int b;    // VariableElement
 * Foo () {}        // ExecutableElement
 * void setA (      // ExecutableElement
 * int newA         // VariableElement
 * ) {}
 * }
 */
@SupportedAnnotationTypes("icu.dzplus.annotation.Check")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CheckGetterProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement annotatedClass : ElementFilter.typesIn(roundEnv.getElementsAnnotatedWith(Check.class))) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
                    String.format("ready to check '%s'.", annotatedClass.getSimpleName()));
            for (VariableElement field : ElementFilter.fieldsIn(annotatedClass.getEnclosedElements())) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
                        String.format("ready to check '%s.%s'.", annotatedClass.getSimpleName(), field.getSimpleName()));
                if (!containsGetter(annotatedClass, field.getSimpleName().toString())) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            String.format("getter not found for '%s.%s'.", annotatedClass.getSimpleName(), field.getSimpleName()));
                }
            }
            for (ExecutableElement executableElement : ElementFilter.methodsIn(annotatedClass.getEnclosedElements())) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
                        String.format("ready to check '%s#%s'.", annotatedClass.getSimpleName(), executableElement.getSimpleName()));
            }
        }
        return true;
    }

    private static boolean containsGetter(TypeElement typeElement, String name) {
        String getter = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        for (ExecutableElement executableElement : ElementFilter.methodsIn(typeElement.getEnclosedElements())) {
            if (!executableElement.getModifiers().contains(Modifier.STATIC)
                    && executableElement.getSimpleName().toString().equals(getter)
                    && executableElement.getParameters().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
