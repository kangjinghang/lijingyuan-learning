package top.lijingyuan.junit5.learning;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

/**
 * JUnit5CustomLauncher
 *
 * @author <a href="kangjinghang@gmail.com">kangjinghang</a>
 * @date 2021-11-07
 * @since 1.0.0
 */
public class JUnit5CustomLauncher {

    public static void main(String[] args) {
        final LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage("top.lijingyuan.junit5.learning"), selectClass(JUnit5CustomLauncher.class))
                .build();
        Launcher launcher = LauncherFactory.create();
        final SummaryGeneratingListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
        listener.getSummary().printTo(new PrintWriter(System.out));
    }

}
