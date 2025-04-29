package org.booking_hotel.jooq.codegen;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        try (InputStream is = new FileInputStream("run.properties")) {
            properties.load(is);
        }

        Boolean bookingHotelEnabled = Boolean.valueOf(properties.getProperty("booking_hotel.enabled", "false"));
        if (bookingHotelEnabled) {
            generate("booking_hotel.", properties);
        }
    }

    private static void generate(String prefix, Properties properties) throws Exception {
        String dbUsername = properties.getProperty(prefix + "db.user", "postgres");
        String dbPassword = properties.getProperty(prefix + "db.password", "postgres");
        String dbName = properties.getProperty(prefix + "db.name");
        String host = properties.getProperty(prefix + "db.host", "localhost");
        String port = properties.getProperty(prefix + "db.port", "5432");
        String destinationFolder = properties.getProperty(prefix + "destination");
        String destinationPackage = properties.getProperty(prefix + "package");
        boolean java8date = properties.getProperty(prefix + "java8date", "false").equals("true");
        boolean includeRoutines = properties.getProperty(prefix + "routine", "false").equals("true");

        Generator generator = new Generator()
                .withGenerate(new Generate().withJavaTimeTypes(java8date))
                .withStrategy(new Strategy()
                        .withName("org.booking_hotel.jooq.codegen.CustomGeneratorStrategy")
                )
                .withDatabase(new Database()
                                .withName("org.jooq.meta.postgres.PostgresDatabase")
                                .withIncludes(".*")
                                .withIncludeRoutines(includeRoutines)
                                .withExcludes("tmp_.* | scr_.* | pg_.*")
                                .withIncludeSequences(false)
                                .withSchemata(new SchemaMappingType().withInputSchema("public"))
//				.withForcedTypes(new ForcedType()
//					.withUserType(jsonType)
//					.withBinding(jsonBinding)
//					.withTypes(".*JSON.*")
//				)
                )
                .withTarget(new Target()
                        .withPackageName(destinationPackage)
                        .withDirectory(destinationFolder));
        System.out.printf("folder is %s", destinationFolder);

        System.getProperties().forEach((k, v) -> System.out.printf("key = %s, value = %s \n", k, v));

        Configuration cfg = new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver("org.postgresql.Driver")
                        .withUrl("jdbc:postgresql://" + host + ":" + port + "/" + dbName)
                        .withUser(dbUsername)
                        .withPassword(dbPassword))
                .withGenerator(generator);

        GenerationTool.generate(cfg);
    }
}
