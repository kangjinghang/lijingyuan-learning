package top.lijingyuan.guava.learning.io;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * FilesTest
 *
 * @author <a href="kangjinghang@xueqiu.com">kangjinghang</a>
 * @date 2021-05-23
 * @since 1.0.0
 */
public class FilesTest {

    private final String SOURCE_FILE = "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/test/resources/io/source.txt";

    private final String TARGET_FILE = "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/test/resources/io/target.txt";

    @Test
    public void testCopyFileWithGuava() throws IOException {
        File sourceFile = new File(SOURCE_FILE);
        File targetFile = new File(TARGET_FILE);
        Files.copy(sourceFile, targetFile);
        assertThat(targetFile.exists(), equalTo(true));
        HashCode sourceHashCode = Files.asByteSource(sourceFile).hash(Hashing.sha256());
        HashCode targetHashCode = Files.asByteSource(targetFile).hash(Hashing.sha256());
        assertThat(sourceHashCode.toString(), equalTo(targetHashCode.toString()));
    }

    @Test
    public void testCopyWithJDKNio2() throws IOException {
        File targetFile = new File(TARGET_FILE);
        java.nio.file.Files.copy(Paths.get(SOURCE_FILE), Paths.get(TARGET_FILE), StandardCopyOption.REPLACE_EXISTING);
        assertThat(targetFile.exists(), equalTo(true));
    }

    @Test
    public void testMoveFile() throws IOException {
        File targetFile = new File(TARGET_FILE);
        File sourceFile = new File(SOURCE_FILE);
        try {
            Files.move(sourceFile, targetFile);
            assertThat(targetFile.exists(), equalTo(true));
            assertThat(sourceFile.exists(), equalTo(false));
        } finally {
            Files.move(targetFile, sourceFile);
        }
    }

    @Test
    public void testToString() throws IOException {
        String expected = "today we will share the guava io knowledge.\n" +
                "but only for the basic usage. if you wanted to get the more details information\n" +
                "please raed the guava document or source code.\n" +
                "\n" +
                "The guava source code is very cleanly and nice.";
        List<String> strings = Files.readLines(new File(SOURCE_FILE), StandardCharsets.UTF_8);
        String result = Joiner.on("\n").join(strings);
        assertThat(result, equalTo(expected));
    }

    @Test
    public void testProcessString() throws IOException {
        List<Integer> result = Files.asCharSource(new File(SOURCE_FILE), StandardCharsets.UTF_8).readLines(new LineProcessor<List<Integer>>() {

            private final List<Integer> lengthList = new ArrayList<>();

            @Override
            public boolean processLine(String line) throws IOException {
//                if (line.length() == 0) {
//                    return false;
//                }
                lengthList.add(line.length());
                return true;
            }

            @Override
            public List<Integer> getResult() {
                return lengthList;
            }
        });

        System.out.println(result);
    }

    @Test
    public void testSha() throws IOException {
        File sourceFile = new File(SOURCE_FILE);
//        Files.hash(sourceFile, Hashing.goodFastHash(128));
        HashCode hashCode = Files.asByteSource(sourceFile).hash(Hashing.sha256());
        System.out.println(hashCode);
    }

    @Test
    public void testFileWrite() throws IOException {
        final String testPath = "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/test/resources/io/testFileWrite.txt";
        File testFile = new File(testPath);
        testFile.deleteOnExit();
        String content = "content 1";
        Files.asCharSink(testFile, Charsets.UTF_8).write(content);
        String result = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(result, equalTo(content));

        String content2 = "content 2";
        Files.asCharSink(testFile, Charsets.UTF_8).write(content2);
        result = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(result, equalTo(content2));
    }

    @Test
    public void testFileAppend() throws IOException {
        final String testPath = "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/test/resources/io/testFileAppend.txt";
        File testFile = new File(testPath);
        testFile.deleteOnExit();
        String content = "content 1";
        CharSink charSink = Files.asCharSink(testFile, Charsets.UTF_8, FileWriteMode.APPEND);
        charSink.write(content);
        String actually = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actually, equalTo(content));

        String content2 = "content 2";
        charSink.write(content2);
        actually = Files.asCharSource(testFile, Charsets.UTF_8).read();
        assertThat(actually, equalTo(content.concat(content2)));
    }

    @Test
    public void testFileTouch() throws IOException {
        final String testPath = "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/test/resources/io/testFileTouch.txt";
        File testFile = new File(testPath);
        testFile.deleteOnExit();
        Files.touch(testFile);
        assertThat(testFile.exists(), equalTo(true));
    }

    @Test
    public void testRecursive() throws IOException {
        List<File> list = new ArrayList<>();
        final String path = "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/test/resources/test";
        recursiveList(new File(path), list);
        list.forEach(System.out::println);
    }

    private void recursiveList(File root, List<File> fileList) {
        if (root.isHidden()) {
            return;
        }
        fileList.add(root);
        if (root.isDirectory()) {
            for (File file : root.listFiles()) {
                recursiveList(file, fileList);
            }
        }
    }

    @Test
    public void testTreeFiles() throws IOException {
        final String path = "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/main";
        File root = new File(path);
        Iterable<File> files = Files.fileTraverser().depthFirstPreOrder(root);
        files.forEach(System.out::println);
    }

    @Test
    public void testDepthFirstPostOrder() throws IOException {
        final String path = "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/main";
        File root = new File(path);
        Iterable<File> files = Files.fileTraverser().depthFirstPostOrder(root);
        files.forEach(System.out::println);
    }

    @Test
    public void testBreadthFirst() throws IOException {
        final String path = "/Users/kangjinghang/workspace/lijingyuan/protobuf-learning/src/main";
        File root = new File(path);
        Iterable<File> files = Files.fileTraverser().breadthFirst(root);
        files.forEach(System.out::println);
    }

    @After
    public void tearDown() {
        File targetFile = new File(TARGET_FILE);
        if (targetFile.exists()) {
            targetFile.delete();
        }
    }

}
