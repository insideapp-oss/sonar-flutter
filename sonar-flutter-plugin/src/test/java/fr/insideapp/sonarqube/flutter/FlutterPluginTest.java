package fr.insideapp.sonarqube.flutter;

import org.junit.Test;
import org.sonar.api.Plugin;
import org.sonar.api.SonarEdition;
import org.sonar.api.SonarQubeSide;
import org.sonar.api.SonarRuntime;
import org.sonar.api.internal.SonarRuntimeImpl;
import org.sonar.api.utils.Version;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FlutterPluginTest {

    @Test
    public void define() {

        SonarRuntime sonarRuntime = SonarRuntimeImpl.forSonarQube(Version.create(7, 9), SonarQubeSide.SERVER, SonarEdition.COMMUNITY);
        Plugin.Context context = new Plugin.Context(sonarRuntime);


        FlutterPlugin plugin = new FlutterPlugin();
        plugin.define(context);

        List<?> extensions = context.getExtensions();
        assertThat(extensions).hasSize(14);


    }
}
