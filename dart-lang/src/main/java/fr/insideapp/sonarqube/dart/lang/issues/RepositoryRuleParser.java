package fr.insideapp.sonarqube.dart.lang.issues;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class RepositoryRuleParser {

    private final ObjectMapper objectMapper;

    public RepositoryRuleParser() {
        objectMapper = new ObjectMapper()
                .disable(FAIL_ON_UNKNOWN_PROPERTIES)
                .enable(SerializationFeature.INDENT_OUTPUT);
    }

    public List<RepositoryRule> parse(String resourceName) throws IOException {
        InputStream is =  getClass().getResourceAsStream(resourceName);
        if (is == null) {
            throw new IOException(String.format("JSON rule file not found in resources at %s", resourceName));
        }
        Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8);
        return Arrays.asList(objectMapper.readValue(reader, RepositoryRule[].class));
    }

}
