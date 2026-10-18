package ru.uniteam.models;

import java.util.HashMap;
import java.util.Map;

public enum Skill{
    PYTHON(1, "Python"),
    JAVA(2, "Java"),
    KOTLIN(3, "Kotlin"),
    GROOVY(4, "Groovy"),
    GO(5, "Go"),
    RUST(6, "Rust"),
    C(7, "C"),
    CPP(8, "C++"),
    C_SHARP(9, "C#"),
    JAVASCRIPT(10, "JavaScript"),
    TYPESCRIPT(11, "TypeScript"),
    PHP(12, "PHP"),
    RUBY(13, "Ruby"),
    SWIFT(14, "Swift"),
    SCALA(15, "Scala"),
    PERL(16, "Perl"),
    R(17, "R"),
    MATLAB(18, "MATLAB"),
    DART(19, "Dart"),
    ZIG(20, "Zig"),

    SPRING_BOOT(21, "Spring Boot"),
    SPRING_MVC(22, "Spring MVC"),
    SPRING_DATA_JPA(23, "Spring Data JPA"),
    SPRING_SECURITY(24, "Spring Security"),
    DJANGO(25, "Django"),
    FLASK(26, "Flask"),
    FASTAPI(27, "FastAPI"),
    EXPRESS(28, "Express.js"),
    NEST_JS(29, "NestJS"),
    REACT(30, "React"),
    ANGULAR(31, "Angular"),
    VUE(32, "Vue.js"),
    NEXT_JS(33, "Next.js"),
    GIN(34, "Gin"),
    ECHO(35, "Echo"),
    ASP_NET(36, "ASP.NET Core"),
    LARAVEL(37, "Laravel"),
    RUBY_ON_RAILS(38, "Ruby on Rails"),

    POSTGRESQL(39, "PostgreSQL"),
    MYSQL(40, "MySQL"),
    MONGODB(41, "MongoDB"),
    REDIS(42, "Redis"),
    ELASTICSEARCH(43, "Elasticsearch"),
    CASSANDRA(44, "Cassandra"),
    SQLITE(45, "SQLite"),
    ORACLE(46, "Oracle"),
    SQL_SERVER(47, "SQL Server"),
    CLICKHOUSE(48, "ClickHouse"),
    COUCHDB(49, "CouchDB"),
    NEO4J(50, "Neo4j"),

    DOCKER(51, "Docker"),
    KUBERNETES(52, "Kubernetes"),
    JENKINS(53, "Jenkins"),
    GIT(54, "Git"),
    GITHUB_ACTIONS(55, "GitHub Actions"),
    GITLAB_CI(56, "GitLab CI"),
    MAVEN(57, "Maven"),
    GRADLE(58, "Gradle"),
    LINUX(59, "Linux"),
    BASH(60, "Bash"),
    TERRAFORM(61, "Terraform"),
    ANSIBLE(62, "Ansible"),
    PROMETHEUS(63, "Prometheus"),
    GRAFANA(64, "Grafana"),
    NGINX(65, "Nginx"),
    APACHE(66, "Apache"),
    RABBITMQ(67, "RabbitMQ"),
    KAFKA(68, "Kafka"),
    ZOOKEEPER(69, "Zookeeper"),
    SWAGGER(70, "Swagger/OpenAPI"),

    AWS(71, "Amazon Web Services"),
    AZURE(72, "Microsoft Azure"),
    GOOGLE_CLOUD(73, "Google Cloud Platform"),
    YANDEX_CLOUD(74, "Yandex Cloud"),
    LINODE(75, "Linode"),
    HEROKU(76, "Heroku"),
    NETLIFY(77, "Netlify"),
    VERCEL(78, "Vercel"),

    ANDROID(79, "Android"),
    IOS(80, "iOS"),
    FLUTTER(81, "Flutter"),
    REACT_NATIVE(82, "React Native"),
    XAMARIN(83, "Xamarin"),

    TENSORFLOW(84, "TensorFlow"),
    PYTORCH(85, "PyTorch"),
    SCIKIT_LEARN(86, "scikit-learn"),
    PANDAS(87, "Pandas"),
    NUMPY(88, "NumPy"),
    JUPYTER(89, "Jupyter"),
    OPENCV(90, "OpenCV"),
    KERAS(91, "Keras"),

    JUNIT(92, "JUnit"),
    TESTNG(93, "TestNG"),
    SELENIUM(94, "Selenium"),
    CYPRESS(95, "Cypress"),
    MOCKITO(96, "Mockito"),
    POSTMAN(97, "Postman"),

    AGILE(98, "Agile"),
    SCRUM(99, "Scrum"),
    KANBAN(100, "Kanban"),
    DEVOPS(101, "DevOps"),
    MICROSERVICES(102, "Microservices"),
    REST_API(103, "REST API"),
    GRAPHQL(104, "GraphQL"),
    WEB_SOCKETS(105, "WebSockets"),
    CQRS(106, "CQRS"),
    EVENT_SOURCING(107, "Event Sourcing"),
    TDD(108, "Test-Driven Development"),
    BDD(109, "Behavior-Driven Development");

    private final int id;
    private final String title;
    private static final Map<Integer, Skill> skills = new HashMap<>();

    static {
        for(Skill skill: Skill.values()){
            skills.put(skill.id, skill);
        }
    }
    Skill(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
    public static Skill getSkillByTitle(String title) {
        if (title == null) {
            return null;
        }
        String searchTitle = title.toLowerCase();
        for (Skill skill : Skill.values()) {
            if (skill.title.toLowerCase().equals(searchTitle)) {
                return skill;
            }
        }
        return null;
    }

    public static boolean skillWithThisTitleExists(String title) {
        return getSkillByTitle(title) != null;
    }

    public static Skill searchSkillById(int id){
        return skills.get(id);
    }

    public static boolean skillWithThisIdExists(int id){
        return searchSkillById(id) != null;
    }


}