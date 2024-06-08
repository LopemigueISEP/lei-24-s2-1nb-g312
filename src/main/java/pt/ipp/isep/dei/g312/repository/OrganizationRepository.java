package pt.ipp.isep.dei.g312.repository;

import pt.ipp.isep.dei.g312.domain.Employee;
import pt.ipp.isep.dei.g312.domain.Organization;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrganizationRepository {

    private final List<Organization> organizations;
    /**
     * Constructs a new OrganizationRepository object with an empty list of organizations.
     */
    public OrganizationRepository() {
        organizations = new ArrayList<>();
    }
    /**
     * Retrieves the organization to which the specified employee belongs, if any.
     *
     * @param employee The employee whose organization is to be retrieved.
     * @return An Optional containing the organization of the specified employee if found, or an empty Optional otherwise.
     */
    public Optional<Organization> getOrganizationByEmployee(Employee employee) {

        Optional<Organization> returnOrganization = Optional.empty();

        for (Organization organization : organizations) {
            if (organization.employees(employee)) {
                returnOrganization = Optional.of(organization);
            }
        }

        return returnOrganization;
    }
    /**
     * Retrieves the organization that has an employee with the specified email address, if any.
     *
     * @param email The email address of the employee whose organization is to be retrieved.
     * @return An Optional containing the organization that has an employee with the specified email address if found, or an empty Optional otherwise.
     */
    public Optional<Organization> getOrganizationByEmployeeEmail(String email) {

        Optional<Organization> returnOrganization = Optional.empty();

        for (Organization organization : organizations) {
            if (organization.anyEmployeeHasEmail(email)) {
                returnOrganization = Optional.of(organization);
            }
        }

        return returnOrganization;
    }
    /**
     * Adds a new organization to the repository if it is valid and not already present.
     *
     * @param organization The organization to be added.
     * @return An Optional containing the added organization if the operation is successful, or an empty Optional otherwise.
     */
    public Optional<Organization> add(Organization organization) {

        Optional<Organization> newOrganization = Optional.empty();
        boolean operationSuccess = false;

        if (validateOrganization(organization)) {
            newOrganization = Optional.of(organization.clone());
            operationSuccess = organizations.add(newOrganization.get());
        }

        if (!operationSuccess) {
            newOrganization = Optional.empty();
        }

        return newOrganization;

    }
    /**
     * Validates if the given organization is not already present in the repository.
     *
     * @param organization The organization to validate.
     * @return True if the organization is not already present in the repository, false otherwise.
     */
    private boolean validateOrganization(Organization organization) {
        boolean isValid = !organizations.contains(organization);

        return isValid;
    }
}