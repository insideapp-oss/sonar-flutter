package fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.executable;

import fr.insideapp.sonarqube.dart.lang.issues.dartanalyzer.AnalyzerOutput;
import org.junit.Test;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.internal.SensorContextTester;

import java.io.File;

import static org.assertj.core.api.Java6Assertions.assertThat;


public class AnalyzerExecutableTest {

    private static final String BASE_DIR = "executable";

    private static final String ANALYSIS_OPTIONS = "analysis_options.yaml";

    private class DummyAnalyzerExecutable extends AnalyzerExecutable {

        public DummyAnalyzerExecutable(SensorContext sensorContext) {
            super(sensorContext, AnalyzerOutput.Mode.DETECT);
        }

        @Override
        protected String getCommand() {
            return "echo";
        }

        @Override
        protected String[] getArgs() {
            return new String[]{"hello"};
        }
    }

    @Test
    public void analyzeShouldRunCommandAndReturnAnalyzerOutput() throws Exception {

        ClassLoader classLoader = getClass().getClassLoader();
        File baseDir = new File(classLoader.getResource(BASE_DIR).getFile());
        SensorContext context = SensorContextTester.create(baseDir);
        AnalyzerExecutable executable = new DummyAnalyzerExecutable(context);

        AnalyzerOutput output = executable.analyze();

        assertThat(output.getMode().name()).isEqualTo(AnalyzerExecutable.Mode.defaultMode.name());
        assertThat(output.getContent()).isEqualTo("hello\n");
        assertThat(new File(baseDir, ANALYSIS_OPTIONS)).exists();
    }

}

