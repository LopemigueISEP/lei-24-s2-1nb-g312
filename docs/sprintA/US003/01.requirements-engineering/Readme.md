# US003 - Register a collaborator with a job and fundamental characteristics

## 1. Requirements Engineering

### 1.1. User Story Description

As an HRM, I want to register a collaborator with a job and fundamental
characteristics.

### 1.2. Customer Specifications and Clarifications 
**From the specifications document:**

> Transcribed: _MS has a wide range of employees who carry out the most varied tasks in the context
of managing green spaces._
> 
> Justification: Indicates the diverse roles within the organization, highlighting the importance of managing employees effectively to handle various tasks related to green spaces.

> Transcribed: _A collaborator is a person who is an employee of the organization and carries out
design, construction and/or maintenance tasks for green areas, depending on their
skills._
> 
> Justification: Clarifies the role and responsibilities of collaborators, emphasizing their involvement in tasks related to green spaces.

> Transcribed: _Job is an employee main occupation, for which the employee is hired. Some job examples are designer, estimator, gardener, electrician or bricklayer._
>
> Justification: Defines a job within the organization, highlighting its importance in defining an employee's main occupation and responsibilities.

**From the client clarifications:**
> Question: Which fundamental characteristics of the collaborator are essential for registration? (16th March 2024)
> 
> Answer: Name, date of birth, address, identification card type and number (Citizen Card, Passport, Identity Card), VAT number, contact (e-mail and/or mobile number) and admission date.

> Question: Can a collaborator be hired for more than one job? (16th March 2024)
> 
> Answer: No, legally each collaborator can only be hired for one job.

> Question: Can HRM change the admission date? (16th March 2024)
> 
> Answer: Yes.

> Question:  Is there any limitation regarding the length of the name of the collaborator? (27th March 2024)
>
> Answer: According to the Portuguese law a name should contain at maximum six words;

> Question:  Should we consider valid only the birthdates in which the collaborator has more than 18 years? (27th March 2024)
>
> Answer: Yes.

> Question:   What is the format for the numbers from the id doc types? (27th March 2024)
>
> Answer: Each doc type has specific formats like taxpayer number, Citizen Card ou passport.

> Question: What should be the accepted format for the emails? Should only specific email services be accepted? (2nd April 2024)
>
> Answer: A valid email address consists of an email prefix and an email domain, both in acceptable formats.
The prefix appears to the left of the @ symbol. The domain appears to the right of the @ symbol.

### 1.3. Acceptance Criteria
* **AC1:** All mandatory fields must be filled in (name, birthdate, admission date, address, contact info (mobile and email), ID doc type and respective number).
* **AC2:** Collaborator's job must be predefined by the organization.
* **AC3:** Each collaborator can only be associated with one job.
* **AC4:** Collaborator must be over 18 years old and admission date must be after birthdate.
* **AC5:** Full name of collaborator can have maximum of 6 words.
* **AC6:** E-mail must have a prefix followed by the symbol "@" and then a domain.
* **AC7:** The VAT number must have 9 digits and is unique for each collaborator.



### 1.4. Found out Dependencies

There is a dependency on "US02 - Register a job that a collaborator need to have" as there must be at least one job to associate with a collaborator.

### 1.5 Input and Output Data
**Input Data**

* Typed data:
    * Name
    * Birthdate
    * Admission date
    * Identification document number (Citizen Card, Passport, Identity Card)
    * VAT number
    * Address
    * Contact info (mobile and email)

* Selected data:
    * Job

**Output Data:**

* (In)Successul assignment of job to collaborator

### 1.6. System Sequence Diagram (SSD)

![US03-SSD](svg\US03-SSD-System_Sequence_Diagram__SSD__US03.svg)

### 1.7 Other Relevant Remarks
If the collaborator's job is not available in the predefined list, the HRM should be able to request the addition of a new position to the system.
