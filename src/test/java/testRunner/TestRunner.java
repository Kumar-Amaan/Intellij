package testRunner;

import io.cucumber.junit.*;
import org.junit.runner.*;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features", glue = {
        "stepDefinition" }, plugin = "json:target/jsonReports/cucumber-report.json", monochrome = true, publish = true

)
public class TestRunner {
}
