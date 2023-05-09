package commons

class Rule {

    enum Severity {
        BLOCKER,
        CRITICAL,
        MAJOR,
        MINOR,
        INFO
    }

    enum Type {
        CODE_SMELL,
        BUG,
        VULNERABILITY,
        SECURITY_HOTSPOT
    }

    public String key;
    public String description;
    public Severity severity;
    public Type type;
    public String name;
    public String debt;

    Rule(
            String key,
            String description,
            Severity severity,
            Type type,
            String name,
            String debt
    ) {
        this.key = key
        this.description = description
        this.severity = severity
        this.type = type
        this.name = name
        this.debt = debt
    }

}