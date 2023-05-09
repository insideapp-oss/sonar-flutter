package fr.insideapp.sonarqube.dart.lang.issues;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepositoryRule {

    public enum Severity {
        BLOCKER,
        CRITICAL,
        MAJOR,
        MINOR,
        INFO
    }

    public enum Type {
        CODE_SMELL,
        BUG,
        VULNERABILITY,
        SECURITY_HOTSPOT
    }

    @JsonProperty("key")
    public String key;

    @JsonProperty("name")
    public String name;

    @JsonProperty("severity")
    public Severity severity;

    @JsonProperty("description")
    public String description;

    @JsonProperty("type")
    public Type type;

    @JsonProperty("debt")
    public String debt;
}
