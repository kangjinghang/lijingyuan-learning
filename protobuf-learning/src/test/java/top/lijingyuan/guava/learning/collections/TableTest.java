package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * TableTest
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-05-30
 * @since 1.0.0
 */
public class TableTest {

    @Test
    public void testCreate() {
        HashBasedTable<String, String, String> table = HashBasedTable.create();
        table.put("Language", "Java", "1.8");
        table.put("Language", "Scala", "2.3");
        table.put("Database", "Oracle", "12C");
        table.put("Database", "Mysql", "8.0");
        System.out.println(table);

        Map<String, String> language = table.row("Language");
        assertThat(language.containsKey("Java"), equalTo(true));
        assertThat(table.row("Language").get("Java"), equalTo("1.8"));
        // Map<String,Map<String,String>>
        Map<String, String> result = table.column("Java");
        System.out.println(result);

        Set<Table.Cell<String, String, String>> cells = table.cellSet();
        System.out.println(cells);
    }

    @Test
    public void tableTest() {
        Table<String, Integer, String> aTable = HashBasedTable.create();

        for (char a = 'A'; a <= 'C'; ++a) {
            for (Integer b = 1; b <= 3; ++b) {
                aTable.put(Character.toString(a), b, String.format("%c%d", a, b));
            }
        }

        System.out.println(aTable.column(2));
        System.out.println(aTable.row("B"));
        System.out.println(aTable.get("B", 2));

        System.out.println(aTable.contains("D", 1));
        System.out.println(aTable.containsColumn(3));
        System.out.println(aTable.containsRow("C"));
        System.out.println(aTable.columnMap());
        System.out.println(aTable.rowMap());

        System.out.println(aTable.remove("B", 3));
    }

}
