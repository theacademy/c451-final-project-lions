package org.example.ingestion;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


// Canonical skill list with aliases. Maps any recognized alias (lowercase)
// to its canonical form. Used by SkillExtractor to normalize extracted skills.
// Example: "reactjs", "react.js", "react" all map to "react".

public class CanonicalSkills {

    public static final Map<String, String> ALIAS_TO_CANONICAL;

    static {
        Map<String, String> m = new HashMap<>();

        // Languages
        put(m, "java", "java");
        put(m, "python", "python");
        put(m, "javascript", "javascript", "js", "ecmascript");
        put(m, "typescript", "typescript", "ts");
        put(m, "scala", "scala");
        put(m, "go", "go", "golang");
        put(m, "ruby", "ruby");
        put(m, "c++", "c++", "cpp");
        put(m, "c#", "c#", "csharp", ".net", "dotnet");
        put(m, "swift", "swift");
        put(m, "kotlin", "kotlin");
        put(m, "perl", "perl");
        put(m, "bash", "bash", "shell");
        put(m, "sql", "sql");
        put(m, "html", "html");
        put(m, "css", "css");

        // Frontend frameworks
        put(m, "react", "react", "reactjs", "react.js");
        put(m, "angular", "angular", "angularjs");
        put(m, "vue", "vue", "vuejs", "vue.js");

        // Backend frameworks
        put(m, "spring", "spring", "spring boot", "springboot");
        put(m, "hibernate", "hibernate");
        put(m, "jpa", "jpa");
        put(m, "django", "django");
        put(m, "flask", "flask");
        put(m, "express", "express", "expressjs", "express.js");

        // Databases
        put(m, "postgresql", "postgresql", "postgres");
        put(m, "mysql", "mysql");
        put(m, "mongodb", "mongodb", "mongo");
        put(m, "oracle", "oracle");
        put(m, "redis", "redis");
        put(m, "sybase", "sybase");
        put(m, "db2", "db2");

        // Big data / streaming
        put(m, "kafka", "kafka", "apache kafka");
        put(m, "spark", "spark", "apache spark");
        put(m, "hive", "hive");
        put(m, "hadoop", "hadoop");

        // Infra / DevOps
        put(m, "docker", "docker");
        put(m, "kubernetes", "kubernetes", "k8s");
        put(m, "jenkins", "jenkins");
        put(m, "git", "git");
        put(m, "linux", "linux");
        put(m, "unix", "unix");
        put(m, "terraform", "terraform");
        put(m, "ansible", "ansible");
        put(m, "ci/cd", "ci/cd", "cicd");

        // Cloud
        put(m, "aws", "aws", "amazon web services");
        put(m, "azure", "azure", "microsoft azure");
        put(m, "gcp", "gcp", "google cloud", "google cloud platform");

        // Observability
        put(m, "grafana", "grafana");
        put(m, "dynatrace", "dynatrace");
        put(m, "appdynamics", "appdynamics");

        // Testing
        put(m, "junit", "junit");
        put(m, "mockito", "mockito");
        put(m, "selenium", "selenium");

        // Practices
        put(m, "agile", "agile");
        put(m, "scrum", "scrum");
        put(m, "tdd", "tdd", "test-driven development", "test driven development");
        put(m, "oop", "oop", "object-oriented programming", "object oriented programming");
        put(m, "microservices", "microservices");
        put(m, "rest", "rest", "rest api", "restful");
        put(m, "graphql", "graphql");
        put(m, "devops", "devops");
        put(m, "sre", "sre", "site reliability engineering");

        // ML, data, ai
        put(m, "machine learning", "machine learning", "ml");
        put(m, "tensorflow", "tensorflow");
        put(m, "pytorch", "pytorch");

        ALIAS_TO_CANONICAL = Collections.unmodifiableMap(m);
    }


    // Helper to register a canonical skill with its aliases.
    // The canonical name itself is automatically registered as an alias of itself.
    private static void put(Map<String, String> m, String canonical, String... aliases) {
        for (String alias : aliases) {
            m.put(alias.toLowerCase(), canonical);
        }
    }

    private CanonicalSkills() {
        // utility class — no instances
    }
}