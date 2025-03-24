package life.duanzhang.processor;


import javax.annotation.processing.Completion;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.Set;


/**
 * 给字段添加swagger
 * todo 未完工
 * @description
 * @author duanzhang
 * @time 2023/3/30 14:08
 */
public class AddSwagger implements Processor {
    /**
     返回此处理器识别的选项。处理工具的实现必须提供一种方法，
     用于将处理器特定选项与传递给工具本身的选项分开传递，
     */
    @Override
    public Set<String> getSupportedOptions() {
        return null;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return null;
    }

    /**
     * 返回这个处理器支持的源码版本
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return null;
    }

    /**
     * 使用处理环境初始化处理器。
     */
    @Override
    public void init(ProcessingEnvironment processingEnv) {

    }

    /**
     * 处理代码用的
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


        return false;
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
}