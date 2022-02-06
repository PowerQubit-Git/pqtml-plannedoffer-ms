package pt.tml.plannedoffer.export.strategies;

import com.opencsv.bean.BeanField;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.exceptions.CsvBadConverterException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.commons.collections.comparators.ComparableComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.collections.comparators.FixedOrderComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class HeaderColumnNameAndOrderMappingStrategy<T> extends HeaderColumnNameMappingStrategy<T>
{

    public HeaderColumnNameAndOrderMappingStrategy(Class<T> type)
    {
        setType(type);
    }

    public static String camelToSnake(String str)
    {
        // Regular Expression
        String regex = "([a-z])([A-Z]+)";

        // Replacement string
        String replacement = "$1_$2";

        // Replace the given regex
        // with replacement string
        // and convert it to lower case.
        str = str
                .replaceAll(
                        regex, replacement)
                .toLowerCase();

        // return string
        return str;
    }

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException
    {
        // overriding this method to allow us to preserve the header column name casing

        String[] header = super.generateHeader(bean);
        final int numColumns = headerIndex.findMaxIndex();
        if (numColumns == -1)
        {
            return header;
        }

        header = new String[numColumns + 1];

        BeanField beanField;
        for (int i = 0; i <= numColumns; i++)
        {
            beanField = findField(i);
            String columnHeaderName = extractHeaderName(beanField);
            header[i] = columnHeaderName;
        }
        return header;
    }

    @Override
    protected void loadFieldMap() throws CsvBadConverterException
    {
        // overriding this method to support setting column order by the custom `CsvBindByNameOrder` annotation
        if (writeOrder == null && type.isAnnotationPresent(CsvBindByNameOrder.class))
        {
            List<String> predefinedList = Arrays.stream(type.getAnnotation(CsvBindByNameOrder.class).value())
                    .map(String::toUpperCase).collect(Collectors.toList());
            //    .map((a)->camelToSnake(a)).collect(Collectors.toList());

            FixedOrderComparator fixedComparator = new FixedOrderComparator(predefinedList);
            fixedComparator.setUnknownObjectBehavior(FixedOrderComparator.UNKNOWN_AFTER);
            Comparator<String> comparator = new ComparatorChain(Arrays.asList(
                    fixedComparator,
                    new NullComparator(false),
                    new ComparableComparator()));
            setColumnOrderOnWrite(comparator);
        }
        super.loadFieldMap();
    }

    private String extractHeaderName(final BeanField beanField)
    {
        if (beanField == null || beanField.getField() == null)
        {
            return StringUtils.EMPTY;
        }

        if (beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class).length == 0)
        {
            // if no CsvBindByName annotation present use snake case
            return camelToSnake(beanField.getField().getName());
        }

        if (beanField.getField().isAnnotationPresent(CsvBindByName.class))
        {
            return beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class)[0].column();
        }
        else if (beanField.getField().isAnnotationPresent(CsvCustomBindByName.class))
        {
            return beanField.getField().getDeclaredAnnotationsByType(CsvCustomBindByName.class)[0].column();
        }
        return StringUtils.EMPTY;

    }

    public int compare(String a, String b)
    {
        if (camelToSnake(a).equals(camelToSnake(b)) || a.equals(b))
        {
            return 0;
        }
        return -1;
    }

}
