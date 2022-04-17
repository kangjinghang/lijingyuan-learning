package top.lijingyuan.guava.learning.collections;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * MultiMapsTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-30
 * @since 1.0.0
 */
public class MultiMapsTest {

    @Test
    public void testCreate() {
        LinkedListMultimap<String, String> multimap = LinkedListMultimap.create();
        HashMap<String, String> hashMap = Maps.newHashMap();
        hashMap.put("1", "1");
        hashMap.put("1", "2");
        assertThat(hashMap.size(), equalTo(1));

        multimap.put("1", "1");
        multimap.put("1", "2");
        assertThat(multimap.size(), equalTo(2));

        System.out.println(multimap.get("1"));
    }

    static class StudentScore {
        int courseId;
        int score;
    }

    Map<String, List<StudentScore>> studentScoreMap = new HashMap<>();

    @Test
    public void testStudentScore() {

        for (int i = 10; i < 20; i++) {
            StudentScore studentScore = new StudentScore();
            studentScore.courseId = 1001 + i;
            studentScore.score = 100 - i;
            addStudentScore("peida", studentScore);
        }

        System.out.println("StudentScoreMap:" + studentScoreMap.size());
        System.out.println("StudentScoreMap:" + studentScoreMap.containsKey("peida"));

        System.out.println("StudentScoreMap:" + studentScoreMap.containsKey("jerry"));
        System.out.println("StudentScoreMap:" + studentScoreMap.size());
        System.out.println("StudentScoreMap:" + studentScoreMap.get("peida").size());

        List<StudentScore> StudentScoreList = studentScoreMap.get("peida");
        if (StudentScoreList != null && StudentScoreList.size() > 0) {
            for (StudentScore stuScore : StudentScoreList) {
                System.out.println("stuScore one:" + stuScore.courseId + " score:" + stuScore.score);
            }
        }
    }

    public void addStudentScore(final String stuName, final StudentScore studentScore) {
        List<StudentScore> stuScore = studentScoreMap.computeIfAbsent(stuName, k -> new ArrayList<>());
        stuScore.add(studentScore);
    }

    @Test
    public void teststuScoreMultimap() {
        Multimap<String, StudentScore> scoreMultimap = ArrayListMultimap.create();
        for (int i = 10; i < 20; i++) {
            StudentScore studentScore = new StudentScore();
            studentScore.courseId = 1001 + i;
            studentScore.score = 100 - i;
            scoreMultimap.put("peida", studentScore);
        }
        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.keys());
    }

    @Test
    public void teststuScoreMultimap2() {
        Multimap<String, StudentScore> scoreMultimap = ArrayListMultimap.create();
        for (int i = 10; i < 20; i++) {
            StudentScore studentScore = new StudentScore();
            studentScore.courseId = 1001 + i;
            studentScore.score = 100 - i;
            scoreMultimap.put("peida", studentScore);
        }
        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.keys());

        for (Map.Entry<String, StudentScore> entry : scoreMultimap.entries()) {
            System.out.println(entry.getKey() + "\t" + entry.getValue().courseId + "\t" + entry.getValue().score);
        }

        Collection<StudentScore> studentScore = scoreMultimap.get("peida");
        studentScore.clear();
        StudentScore studentScoreNew = new StudentScore();
        studentScoreNew.courseId = 1034;
        studentScoreNew.score = 67;
        studentScore.add(studentScoreNew);

        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.keys());

    }

    @Test
    public void teststuScoreMultimap3() {
        Multimap<String, StudentScore> scoreMultimap = ArrayListMultimap.create();
        for (int i = 10; i < 20; i++) {
            StudentScore studentScore = new StudentScore();
            studentScore.courseId = 1001 + i;
            studentScore.score = 100 - i;
            scoreMultimap.put("peida", studentScore);
        }
        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.keys());

        Collection<StudentScore> studentScore = scoreMultimap.get("peida");
        StudentScore studentScore1 = new StudentScore();
        studentScore1.courseId = 1034;
        studentScore1.score = 67;
        studentScore.add(studentScore1);

        StudentScore studentScore2 = new StudentScore();
        studentScore2.courseId = 1045;
        studentScore2.score = 56;
        scoreMultimap.put("jerry", studentScore2);

        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.keys());


        for (StudentScore stuScore : scoreMultimap.values()) {
            System.out.println("stuScore one:" + stuScore.courseId + " score:" + stuScore.score);
        }

        scoreMultimap.remove("jerry", studentScore2);
        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.get("jerry"));

        scoreMultimap.put("harry", studentScore2);
        scoreMultimap.removeAll("harry");
        System.out.println("scoreMultimap:" + scoreMultimap.size());
        System.out.println("scoreMultimap:" + scoreMultimap.get("harry"));
    }

}
