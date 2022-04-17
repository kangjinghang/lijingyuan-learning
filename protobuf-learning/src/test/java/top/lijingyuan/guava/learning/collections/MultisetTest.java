package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MultisetTest {

    @Test
    public void testWordCount() {
        String strWorld = "wer|dffd|ddsa|dfd|dreg|de|dr|ce|ghrt|cf|gt|ser|tg|ghrt|cf|gt|" +
                "ser|tg|gt|kldf|dfg|vcd|fg|gt|ls|lser|dfr|wer|dffd|ddsa|dfd|dreg|de|dr|" +
                "ce|ghrt|cf|gt|ser|tg|gt|kldf|dfg|vcd|fg|gt|ls|lser|dfr";
        String[] words = strWorld.split("\\|");
        Map<String, Integer> countMap = new HashMap<>();
        for (String word : words) {
            countMap.merge(word, 1, Integer::sum);
        }
        System.out.println("countMap：");
        for (String key : countMap.keySet()) {
            System.out.println(key + " count：" + countMap.get(key));
        }
    }

    @Test
    public void testMultsetWordCount() {
        String strWorld = "wer|dfd|dd|dfd|dda|de|dr";
        String[] words = strWorld.split("\\|");
        Multiset<String> wordsMultiset = HashMultiset.create();
        wordsMultiset.addAll(Arrays.asList(words));
        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }
    }

    @Test
    public void testMultsetWordCase() {
        String strWorld = "wer|dfd|dd|dfd|dda|de|dr";
        String[] words = strWorld.split("\\|");
        Multiset<String> wordsMultiset = HashMultiset.create();
        wordsMultiset.addAll(Arrays.asList(words));

        //System.out.println("wordsMultiset："+wordsMultiset);

        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }

        if (!wordsMultiset.contains("peida")) {
            wordsMultiset.add("peida", 2);
        }
        System.out.println("============================================");
        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }


        if (wordsMultiset.contains("peida")) {
            wordsMultiset.setCount("peida", 23);
        }

        System.out.println("============================================");
        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }

        if (wordsMultiset.contains("peida")) {
            wordsMultiset.setCount("peida", 23, 45);
        }

        System.out.println("============================================");
        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }

        if (wordsMultiset.contains("peida")) {
            wordsMultiset.setCount("peida", 44, 67);
        }

        System.out.println("============================================");
        for (String key : wordsMultiset.elementSet()) {
            System.out.println(key + " count：" + wordsMultiset.count(key));
        }
    }

}