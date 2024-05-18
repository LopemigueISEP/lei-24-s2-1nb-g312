# Supplementary Specification (FURPS+)

## Functionality



_Specifies functionalities that:  
&nbsp; &nbsp; (i) are common across several US/UC;  
&nbsp; &nbsp; (ii) are not related to US/UC, namely: Audit, Reporting and Security._

    - Features to support managing green spaces, including gardens and parks of varying sizes.
    - Management of multidisciplinary teams, allocation of these teams to green spaces, and tracking of their tasks.
    - Fleet, machine, and equipment management capabilities for carrying out tasks in the green spaces.
    - Optimization features for irrigation and lighting systems within green spaces.
    - Statistical analysis tools for measuring performance related to green spaces maintenance.
    - All those who wish to use the application must be authenticated with a password of seven alphanumeric characters,
      including three capital letters and two digits.
    - The System should be able to identify duplicates.

## Usability


_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._

    - The application documention must be in English language.
    - All the images/figures produced during the software development process should be recorded in SVG format.
    - The dates format should be DD/MM/AAAA.
    - The vehicle plates can be LL-NN-NN, NN-LL-NN, NN-NN-LL or LL-NN-LL.
    - User-friendly interfaces for various system users including Human Resources Manager, Vehicle and Equipment 
      Fleet Manager, Green Spaces Manager and Collaborators.
    - Provide documentation and training materials to help users get the most out of the software.


## Reliability

_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: 
 frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._

    - Business rules validation must be respected when recording and updating data.
    - Consistent and reliable record-keeping for tasks, teams, and green space maintenance of an agenda.
    - Dependable tracking and updating system for vehicles, machinery, and equipment maintenance.
    - The software must be able to detect errors and provide meaningful error messages to users.
    - The application should handle data without crashes or loss of information.


## Performance

_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._
 
    - The system should perform efficiently under the workload of managing numerous green spaces and associated tasks.
    - The system should be able to handle a large number of users, tasks, teams, equipments, machines and vehicles 
      simultaneously.
 


## Supportability

_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._

    
    - The development team must implement unit tests for all methods, except for  methods that implement Input/Output 
      operations.
    - Maintainable architecture that allows for easy updates and addition of new features.
    - Comprehensive documentation and in-line comments within the codebase.
    

## +


### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._

    - The class structure must be designed to allow easy maintenance and the addition of new features, following 
      the best OO practices.
    - The application must be developed in Java language using the IntelliJ IDE or NetBeans.
    - The application’s graphical interface is to be developed in JavaFX 11.
    - Adopt best practices for identifying requirements, and for OO software analysis and design.
    - Adopt recognized coding standards (e.g., CamelCase).
    - Use Javadoc to generate useful documentation for Java code.
    - The unit tests should be implemented using the JUnit 5 framework.
    - The JaCoCo plugin should be used to generate the coverage report.


### Implementation Constraints

_Specifies or constraints the code or construction of a system such
such as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._


    - The application should use object serialization to ensure data persistence between two runs of the application.

### Interface Constraints

_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._

(fill in here )

### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

(fill in here )