import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(PrestaShopTests.class);
        System.out.println("\nErrors: " + result.getFailureCount() + " out of " + result.getRunCount());
        List<Failure> failiures = result.getFailures();
        for (Failure failure:failiures) {
            System.out.println(failure);
        }
    }

}