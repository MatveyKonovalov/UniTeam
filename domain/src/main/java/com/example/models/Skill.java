package com.example.models;

public enum Skill {
    PYTHON("Python"),
    JAVA("Java"),
    KOTLIN("Kotlin"),
    GROOVY("Groovy"),
    GO("Go"),
    RUST("Rust"),
    C("C"),
    CPP("C++"),
    C_SHARP("C#"),
    JAVASCRIPT("JavaScript"),
    TYPESCRIPT("TypeScript"),
    PHP("PHP"),
    RUBY("Ruby"),
    SWIFT("Swift"),
    SCALA("Scala"),
    PERL("Perl"),
    R("R"),
    MATLAB("MATLAB"),
    DART("Dart"),
    ZIG("Zig"),

    SPRING_BOOT("Spring Boot"),
    SPRING_MVC("Spring MVC"),
    SPRING_DATA_JPA("Spring Data JPA"),
    SPRING_SECURITY("Spring Security"),
    DJANGO("Django"),
    FLASK("Flask"),
    FASTAPI("FastAPI"),
    EXPRESS("Express.js"),
    NEST_JS("NestJS"),
    REACT("React"),
    ANGULAR("Angular"),
    VUE("Vue.js"),
    NEXT_JS("Next.js"),
    GIN("Gin"),
    ECHO("Echo"),
    ASP_NET("ASP.NET Core"),
    LARAVEL("Laravel"),
    RUBY_ON_RAILS("Ruby on Rails"),

    POSTGRESQL("PostgreSQL"),
    MYSQL("MySQL"),
    MONGODB("MongoDB"),
    REDIS("Redis"),
    ELASTICSEARCH("Elasticsearch"),
    CASSANDRA("Cassandra"),
    SQLITE("SQLite"),
    ORACLE("Oracle"),
    SQL_SERVER("SQL Server"),
    CLICKHOUSE("ClickHouse"),
    COUCHDB("CouchDB"),
    NEO4J("Neo4j"),

    DOCKER("Docker"),
    KUBERNETES("Kubernetes"),
    JENKINS("Jenkins"),
    GIT("Git"),
    GITHUB_ACTIONS("GitHub Actions"),
    GITLAB_CI("GitLab CI"),
    MAVEN("Maven"),
    GRADLE("Gradle"),
    LINUX("Linux"),
    BASH("Bash"),
    TERRAFORM("Terraform"),
    ANSIBLE("Ansible"),
    PROMETHEUS("Prometheus"),
    GRAFANA("Grafana"),
    NGINX("Nginx"),
    APACHE("Apache"),
    RABBITMQ("RabbitMQ"),
    KAFKA("Kafka"),
    ZOOKEEPER("Zookeeper"),
    SWAGGER("Swagger/OpenAPI"),

    AWS("Amazon Web Services"),
    AZURE("Microsoft Azure"),
    GOOGLE_CLOUD("Google Cloud Platform"),
    YANDEX_CLOUD("Yandex Cloud"),
    LINODE("Linode"),
    HEROKU("Heroku"),
    NETLIFY("Netlify"),
    VERCEL("Vercel"),

    ANDROID("Android"),
    IOS("iOS"),
    FLUTTER("Flutter"),
    REACT_NATIVE("React Native"),
    XAMARIN("Xamarin"),

    TENSORFLOW("TensorFlow"),
    PYTORCH("PyTorch"),
    SCIKIT_LEARN("scikit-learn"),
    PANDAS("Pandas"),
    NUMPY("NumPy"),
    JUPYTER("Jupyter"),
    OPENCV("OpenCV"),
    KERAS("Keras"),

    JUNIT("JUnit"),
    TESTNG("TestNG"),
    SELENIUM("Selenium"),
    CYPRESS("Cypress"),
    MOCKITO("Mockito"),
    POSTMAN("Postman"),

    AGILE("Agile"),
    SCRUM("Scrum"),
    KANBAN("Kanban"),
    DEVOPS("DevOps"),
    MICROSERVICES("Microservices"),
    REST_API("REST API"),
    GRAPHQL("GraphQL"),
    WEB_SOCKETS("WebSockets"),
    CQRS("CQRS"),
    EVENT_SOURCING("Event Sourcing"),
    TDD("Test-Driven Development"),
    BDD("Behavior-Driven Development");

    private final String title;

    Skill(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static Skill getSkillByTitle(String title){
        Skill[] skills = Skill.values();
        title = title.toLowerCase();

        for (Skill skill: skills){
            if (skill.title.toLowerCase().equals(title)){
                return skill;
            }
        }
        return null;
    }

    public static boolean skillWithThisTitleExists(String title){
        return getSkillByTitle(title) != null;
    }
}