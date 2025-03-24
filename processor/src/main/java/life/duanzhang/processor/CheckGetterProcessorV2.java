package life.duanzhang.processor;

import life.duanzhang.annotation.Check;

import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


/**
 * 这个处理器未激活 如果需要激活 到配置文件里打开
 * @author duanzhang
 * @description
 * @time 2023/3/30 14:08
 */
public class CheckGetterProcessorV2 implements Processor {
    boolean initialized = false;
    protected ProcessingEnvironment processingEnv;

    /**
     * 返回此处理器识别的选项。处理工具的实现必须提供一种方法，
     * 用于将处理器特定选项与传递给工具本身的选项分开传递，
     */
    @Override
    public Set<String> getSupportedOptions() {
        /*
         * 此处也可在类上用注解代替
         * @SupportedOptions("XX")
         */
        return Collections.emptySet();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        /*
         * 此处也可在类上用注解代替
         * @SupportedAnnotationTypes("icu.dzplus.annotation.AutoSwagger")
         */
        HashSet<String> strings = new HashSet<>();
        strings.add("icu.dzplus.annotation.AutoSwagger");
        return strings;
    }

    /**
     * 返回这个处理器支持的源码版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        /*
         * 此处也可在类上用注解代替
         * @SupportedSourceVersion(SourceVersion.RELEASE_10)
         */
        return SourceVersion.RELEASE_8;
    }

    /**
     * 使用处理环境初始化处理器。
     */
    @Override
    public void init(ProcessingEnvironment processingEnv) {
        if (initialized)
            throw new IllegalStateException("Cannot call init more than once.");
        Objects.requireNonNull(processingEnv, "Tool provided null ProcessingEnvironment");
        this.processingEnv = processingEnv;
        initialized = true;
    }

    /**
     * 处理代码用的
     */
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

    /**
     * Returns to the tool infrastructure an iterable of suggested
     * completions to an annotation.  Since completions are being asked
     * for, the information provided about the annotation may be
     * incomplete, as if for a source code fragment. A processor may
     * return an empty iterable.  Annotation processors should focus
     * their efforts on providing completions for annotation members
     * with additional validity constraints known to the processor, for
     * example an {@code int} member whose value should lie between 1
     * and 10 or a string member that should be recognized by a known
     * grammar, such as a regular expression or a URL.
     * 返回给工具基础设施一个建议的注解补全的可迭代对象，帮忙补全代码用的
     */
    @Override
    public Iterable<? extends Completion> getCompletions(Element element, AnnotationMirror annotation, ExecutableElement member, String userText) {
        return null;
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

