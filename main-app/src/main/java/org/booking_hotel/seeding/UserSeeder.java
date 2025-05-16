package org.booking_hotel.seeding; // You might want a 'seeding' package

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.booking_hotel.jooq.model.enums.UserRole;
import org.booking_hotel.jooq.model.tables.Users;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jooq.DSLContext;
import org.jooq.exception.DataAccessException;

import java.time.OffsetDateTime;

@ApplicationScoped
public class UserSeeder {

    // Configure the number of users to seed
    private static final int NUMBER_OF_USERS_TO_SEED = 100;
    private final Users u = Users.USERS.as("u");
    @Inject
    DSLContext dsl;

    @ConfigProperty(name = "quarkus.seeding.users.enabled", defaultValue = "false")
    boolean userSeedingEnabled; // Inject the configuration property

    @Transactional
    public void seed(@Observes StartupEvent ev) {
        if (userSeedingEnabled && isUserTableEmpty()) { // Check if seeding is enabled
            System.out.println("Seeding users table...");
            for (int i = 1; i <= NUMBER_OF_USERS_TO_SEED; i++) {
                try {
                    insertMockUser(i);
                } catch (DataAccessException e) {
                    System.err.println("Error seeding user " + i + ": " + e.getMessage());
                }
            }
            System.out.println("User seeding complete.");
        } else if (userSeedingEnabled && !isUserTableEmpty()) {
            System.out.println("User seeding enabled but table is not empty, skipping seeding.");
        } else {
            System.out.println("User seeding is disabled.");
        }
    }

    private boolean isUserTableEmpty() {
        // Check if the users table has any records
        return dsl.selectCount().from(u).fetchOne(0, int.class) == 0;
    }

    private void insertMockUser(int index) {
        String firstName = "TestFirstName" + index;
        String lastName = "TestLastName" + index;
        String email = "test" + index + "@example.com"; // Ensure unique emails
        String passwordHash = "hashed_password_" + index; // Placeholder

        // Assign roles based on index
        UserRole role = (index <= 5) ? UserRole.admin : UserRole.user;

        // Get current timestamp with time zone
        OffsetDateTime now = OffsetDateTime.now();

        // Use jOOQ to insert the data
        dsl.insertInto(u,
                        u.FIRST_NAME,
                        u.LAST_NAME,
                        u.EMAIL,
                        u.PASSWORD_HASH,
                        u.ROLE,
                        u.CREATED_AT,
                        u.UPDATED_AT
                )
                .values(
                        firstName,
                        lastName,
                        email,
                        passwordHash,
                        role,
                        now,
                        now
                )
                .execute();
    }
}
