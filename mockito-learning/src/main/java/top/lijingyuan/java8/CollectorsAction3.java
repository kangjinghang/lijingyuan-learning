package top.lijingyuan.java8;

import sun.jvm.hotspot.utilities.Assert;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static top.lijingyuan.java8.CollectorIntroduce.menu;

/**
 * CollectorsAction3
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-07-07
 * @since 1.0.0
 */
public class CollectorsAction3 {

    public static void main(String[] args) {
        List<String> strings = Arrays.asList(
                "1000330890",
                "1000972426",
                "1001113994",
                "1002689162",
                "1002801290",
                "1003923338",
                "1007484810",
                "1007689098",
                "1009034890",
                "1010257546",
                "1010507146",
                "1011586186",
                "1011775370",
                "1011838602",
                "1012040330",
                "1012679562",
                "1013361034",
                "1013749642",
                "1014296458",
                "1015442826",
                "1015928714",
                "1016124042",
                "1016210058",
                "1016343946",
                "1016411786",
                "1017171082",
                "1018010762",
                "1018071946",
                "1019844490",
                "1019924106",
                "1020007562",
                "1020752266",
                "1022609290",
                "1025112458",
                "1025631626",
                "1025889930",
                "1026433930",
                "1026818442",
                "1027879818",
                "1028118410",
                "1028897674",
                "1029869962",
                "1030556298",
                "1031161482",
                "1032400778",
                "1034134922",
                "1034349706",
                "1034930314",
                "1035066762",
                "1035898250",
                "1037573002",
                "1038385546",
                "1039217034",
                "1039375498",
                "1039793290",
                "1040961418",
                "1041633930",
                "1041674890",
                "1041994122",
                "1044841098",
                "1044855946",
                "1045216650",
                "1045416330",
                "1045556362",
                "1046482058",
                "1046867082",
                "1047220874",
                "1047791754",
                "1051345546",
                "1051719818",
                "1055479434",
                "1055733898",
                "1057648266",
                "1060099978",
                "1060538506",
                "1060816778",
                "1063450250",
                "1063599498",
                "1063659914",
                "1063915146",
                "1064767370",
                "1065572746",
                "1066826634",
                "1066931850",
                "1067000458",
                "1069905034",
                "1069936522",
                "1071340938",
                "1071583114",
                "1072087946",
                "1073034634",
                "1073291914",
                "1074594698",
                "1075098250",
                "1075634826",
                "1075682442",
                "1076162442",
                "1078885770",
                "1079815306",
                "1080038538",
                "1001228180",
                "1002860948",
                "1003442580",
                "1004289172",
                "1005421460",
                "1006241172",
                "1006323092",
                "1006510484",
                "1006670996",
                "1006849684",
                "1007291284",
                "1007786388",
                "1008149396",
                "1008271764",
                "1008846740",
                "1008891540",
                "1009015444",
                "1011103892",
                "1011755924",
                "1012208788",
                "1012508308",
                "1013273236",
                "1015768980",
                "1017376916",
                "1020450964",
                "1021694612",
                "1022401172",
                "1024023700",
                "1025183380",
                "1025506452",
                "1026544788",
                "1028719252",
                "1028808340",
                "1029091220",
                "1029518484",
                "1029886356",
                "1031421844",
                "1031597460",
                "1032538260",
                "1034409108",
                "1034705812",
                "1036101780",
                "1036863380",
                "1037064340",
                "1037273748",
                "1037311124",
                "1038018964",
                "1039398804",
                "1040694164",
                "1041445012",
                "1042522516",
                "1043937172",
                "1045407892",
                "1045420692",
                "1047066516",
                "1047727508",
                "1050108052",
                "1050442644",
                "1051756692",
                "1053053844",
                "1055239572",
                "1055257492",
                "1056013460",
                "1056158612",
                "1056748436",
                "1057245588",
                "1058160020",
                "1059333012",
                "1060037012",
                "1061450644",
                "1061514132",
                "1062023572",
                "1062638228",
                "1063848084",
                "1065004436",
                "1066625940",
                "1067929748",
                "1068259732",
                "1069073300",
                "1069159828",
                "1070284692",
                "1070958740",
                "1072021140",
                "1072050068",
                "1072820628",
                "1075329940",
                "1075708564",
                "1076927636",
                "1080668308",
                "1081952148",
                "1082466196",
                "1084653204",
                "1084913556",
                "1086688916",
                "1086743700",
                "1087307412",
                "1087841428",
                "1088884884",
                "1090543764",
                "1091628180",
                "1000258974",
                "1002605982",
                "1003057310",
                "1004306846",
                "1005220766",
                "1005324446",
                "1010672030",
                "1011413918",
                "1012987038",
                "1013360286",
                "1013405342",
                "1015804574",
                "1016914590",
                "1017108894",
                "1017599646",
                "1018588062",
                "1019218590",
                "1021144734",
                "1021205406",
                "1021414558",
                "1021703326",
                "1023534494",
                "1023789470",
                "1023872414",
                "1024209822",
                "1024872350",
                "1026521246",
                "1026631838",
                "1029283230",
                "1032246686",
                "1033391518",
                "1033816222",
                "1034044318",
                "1034051742",
                "1036056734",
                "1036130206",
                "1036448670",
                "1036490910",
                "1038033566",
                "1038379934",
                "1039885726",
                "1040130718",
                "1040291230",
                "1040377246",
                "1042377886",
                "1044059038",
                "1044495262",
                "1048500638",
                "1049974686",
                "1050136222",
                "1051137182",
                "1052554910",
                "1053645214",
                "1054569886",
                "1055859614",
                "1058430622",
                "1059555742",
                "1059897758",
                "1060012702",
                "1060062110",
                "1060585118",
                "1060802206",
                "1061388702",
                "1061842846",
                "1062252958",
                "1063363230",
                "1064883358",
                "1065100958",
                "1066530974",
                "1067732638",
                "1068963230",
                "1069821342",
                "1070569886",
                "1070703518",
                "1071384478",
                "1072693406",
                "1072753822",
                "1073824158",
                "1073883550",
                "1074302110",
                "1075983262",
                "1076348062",
                "1076851358",
                "1079074462",
                "1079585694",
                "1080545950",
                "1081848734",
                "1082671262",
                "1082901662",
                "1083064734",
                "1083603870",
                "1084037022",
                "1084954782",
                "1084986526",
                "1085761182",
                "1085884318",
                "1087145118",
                "1087676318",
                "1087853470",
                "1088156830"
        );

        for (String str : strings) {
            char c = str.charAt(str.length() - 1);
            Long val = Long.valueOf(str);
            Assert.that(c - 48 == val % (val / 10 * 10), "error");
        }

        System.out.println(22L / 10 * 10);
        System.out.println(22L % 2);
        Map<Boolean, List<Long>> groupedMap = Arrays.asList(12L, 22L, 23L, 24L).stream().collect(
                Collectors.partitioningBy(uid -> Arrays.asList(1L, 2L, 3L).contains(uid % (uid / 10 * 10)))
        );
        System.out.println(groupedMap);
        groupedMap = Arrays.asList(12L, 22L, 23L, 24L).stream().collect(
                Collectors.groupingBy(uid -> Arrays.asList(1L, 2L, 3L).contains(uid % (uid / 10L)))
        );
        System.out.println(groupedMap);
        testPartitioningByWithPredicate();
        testPartitioningByWithPredicateAndCollector();
        testReducingByBinaryOperator();
        testReducingByBinaryOperatorAndIdentity();
        testReducingByBinaryOperatorAndIdentityAndFunction();
        testSummarizingDouble();
        testSummarizingInt();
        testSummarizingLong();
        testSummingLong();
        testSummingInt();
        testToCollection();
        testToConcurrentMap();
        testToConcurrentMapWithBinaryOperator();
        testToConcurrentMapWithBinaryOperatorAndSupplier();
        testToList();
        testToSet();
        testToMap();
        testToMapWithBinaryOperator();
        testToMapWithBinaryOperatorAndSupplier();
        testToMapAndCollectingAndThen();
    }

    private static void testPartitioningByWithPredicate() {
        System.out.println("testPartitioningByWithPredicate");

        Optional.ofNullable(menu.stream().collect(
                Collectors.partitioningBy(Dish::isVegetarian)
        )).ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testPartitioningByWithPredicateAndCollector() {
        System.out.println("testPartitioningByWithPredicateAndCollector");

        Optional.ofNullable(menu.stream().collect(
                Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.averagingInt(Dish::getCalories))
        )).ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testReducingByBinaryOperator() {
        System.out.println("testReducingByBinaryOperator");

        Optional.ofNullable(menu.stream().collect(
                Collectors.reducing(
                        BinaryOperator.maxBy(Comparator.comparingInt(Dish::getCalories))
                )
        )).ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testReducingByBinaryOperatorAndIdentity() {
        System.out.println("testReducingByBinaryOperatorAndIdentity");

        Optional.ofNullable(menu.stream().map(Dish::getCalories).collect(
                Collectors.reducing(
                        0, (a, b) -> a + b
                )
        )).ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testReducingByBinaryOperatorAndIdentityAndFunction() {
        System.out.println("testReducingByBinaryOperatorAndIdentityAndFunction");

        Optional.ofNullable(menu.stream().collect(
                Collectors.reducing(
                        0, Dish::getCalories, (a, b) -> a + b
                )
        )).ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testSummarizingDouble() {
        System.out.println("testSummarizingDouble");
        Optional.ofNullable(
                menu.stream().collect(Collectors.summarizingDouble(Dish::getCalories))
        )
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testSummarizingInt() {
        System.out.println("testSummarizingInt");
        Optional.ofNullable(
                menu.stream().collect(Collectors.summarizingInt(Dish::getCalories))
        )
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testSummarizingLong() {
        System.out.println("testSummarizingLong");
        Optional.ofNullable(
                menu.stream().collect(Collectors.summarizingLong(Dish::getCalories))
        )
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testSummingLong() {
        System.out.println("testSummingLong");
        Optional.ofNullable(
                menu.stream().collect(Collectors.summingLong(Dish::getCalories))
        )
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testSummingInt() {
        System.out.println("testSummingInt");
        Optional.ofNullable(
                menu.stream().collect(Collectors.summingInt(Dish::getCalories))
        )
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testToCollection() {
        System.out.println("testToCollection");
        Optional.ofNullable(
                menu.stream()
                        .filter(e -> e.getCalories() > 600)
                        .collect(Collectors.toCollection(LinkedList::new))
        )
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testToConcurrentMap() {
        System.out.println("testToConcurrentMap");
        Optional.ofNullable(
                menu.stream()
                        .collect(Collectors.toConcurrentMap(Dish::getName, Dish::getCalories))
        )
                .ifPresent(v -> {
                    System.out.println(v.getClass());
                    System.out.println(v);
                });
        System.out.println("------------------");
    }

    private static void testToConcurrentMapWithBinaryOperator() {
        System.out.println("testToConcurrentMapWithBinaryOperator");
        Optional.ofNullable(
                menu.stream()
                        .collect(Collectors.toConcurrentMap(Dish::getName, v -> 1L,
                                (a, b) -> a + b))
        )
                .ifPresent(v -> {
                    System.out.println(v.getClass());
                    System.out.println(v);
                });
        System.out.println("------------------");
    }

    private static void testToConcurrentMapWithBinaryOperatorAndSupplier() {
        System.out.println("testToConcurrentMapWithBinaryOperatorAndSupplier");
        Optional.ofNullable(
                menu.stream()
                        .collect(Collectors.toConcurrentMap(Dish::getName, v -> 1L,
                                (a, b) -> a + b, ConcurrentSkipListMap::new))
        )
                .ifPresent(v -> {
                    System.out.println(v.getClass());
                    System.out.println(v);
                });
        System.out.println("------------------");
    }

    private static void testToList() {
        System.out.println("testToList");
        Optional.ofNullable(
                menu.stream()
                        .collect(Collectors.toList())
        )
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testToSet() {
        System.out.println("testToSet");
        Optional.ofNullable(
                menu.stream()
                        .collect(Collectors.toSet())
        )
                .ifPresent(System.out::println);
        System.out.println("------------------");
    }

    private static void testToMap() {
        System.out.println("testToMap");
        Optional.ofNullable(
                menu.stream()
                        .collect(Collectors.toMap(Dish::getName, Dish::getCalories))
        )
                .ifPresent(v -> {
                    System.out.println(v.getClass());
                    System.out.println(v);
                });
        System.out.println("------------------");
    }

    private static void testToMapAndCollectingAndThen() {
        LongStream.rangeClosed(0, 10);
        System.out.println("testToMap");
        Optional.ofNullable(
                menu.stream()
                        .collect(Collectors.collectingAndThen(Collectors.toMap(Dish::getName, Dish::getCalories), Collections::unmodifiableMap))
        )
                .ifPresent(v -> {
                    System.out.println(v.getClass());
                    System.out.println(v);
                });
        System.out.println("------------------");
    }

    private static void testToMapWithBinaryOperator() {
        System.out.println("testToMapWithBinaryOperator");
        Optional.ofNullable(
                menu.stream()
                        .collect(Collectors.toMap(Dish::getName, v -> 1L,
                                (a, b) -> a + b))
        )
                .ifPresent(v -> {
                    System.out.println(v.getClass());
                    System.out.println(v);
                });
        System.out.println("------------------");
    }

    private static void testToMapWithBinaryOperatorAndSupplier() {
        System.out.println("testToMapWithBinaryOperatorAndSupplier");
        Optional.ofNullable(
                menu.stream()
                        .collect(Collectors.toMap(Dish::getName, v -> 1L,
                                (a, b) -> a + b, Hashtable::new))
        )
                .ifPresent(v -> {
                    System.out.println(v.getClass());
                    System.out.println(v);
                });
        System.out.println("------------------");
    }

}
