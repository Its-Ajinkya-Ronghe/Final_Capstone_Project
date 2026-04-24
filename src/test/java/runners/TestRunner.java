package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterSuite;
import steps.HybridSteps;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "steps",
        plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class TestRunner extends AbstractTestNGCucumberTests {
        @Override
        @DataProvider(parallel = false)
        public Object[][] scenarios() {
                return super.scenarios();
        }

        @AfterSuite
        public void executeJMeterCLI() {
                try {
                        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

                        String projectPath = System.getProperty("user.dir");

                        String jmxPath = projectPath + "/src/test/resources/jmeter/LoadTest.jmx";
                        String jtlPath = projectPath + "/target/jmeter_results_" + timestamp + ".jtl";
                        String htmlReportDir = projectPath + "/target/HTML_Report_" + timestamp;

                        // 🔥 VALIDATION (VERY IMPORTANT)
                        if (HybridSteps.latestAccountId == null) {
                                System.out.println(HybridSteps.latestAccountId);
                                throw new RuntimeException("❌ AccountId is NULL → JMeter cannot run");
                        }

                        System.out.println("Using Data:");
                        System.out.println("Username: " + HybridSteps.latestUsername);
                        System.out.println("Password: " + HybridSteps.latestPassword);
                        System.out.println("AccountId: " + HybridSteps.latestAccountId);

                        String command = String.format(
                                "jmeter -n -t \"%s\" -l \"%s\" -e -o \"%s\" " +
                                        "-Jusername=%s -Jpassword=%s -JaccountId=%s",
                                jmxPath, jtlPath, htmlReportDir,
                                HybridSteps.latestUsername,
                                HybridSteps.latestPassword,
                                HybridSteps.latestAccountId
                        );

                        System.out.println("Running JMeter: " + command);

                        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", command);
                        pb.redirectErrorStream(true);

                        Process process = pb.start();

                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getInputStream())
                        );

                        String line;
                        while ((line = reader.readLine()) != null) {
                                System.out.println(line);
                        }

                        int exitCode = process.waitFor();

                        if (exitCode == 0) {
                                System.out.println("✅ JMeter Success → " + htmlReportDir);
                        } else {
                                System.out.println("❌ JMeter Failed → Exit Code: " + exitCode);
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
}