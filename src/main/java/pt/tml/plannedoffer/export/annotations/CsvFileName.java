package pt.tml.plannedoffer.export.annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CsvFileName
{
    String value() default "";
}
