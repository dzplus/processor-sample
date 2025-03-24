package life.duanzhang.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 给字段生成Get方法
 * @author duanzhang
 * {@code @description}
 * {@code @time} 2025/3/24 14:52
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.SOURCE)
public @interface DGetter {
}
