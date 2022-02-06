package pt.tml.plannedoffer.export.strategies;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CsvBindByNameOrder
{
    String[] value() default {};
}
