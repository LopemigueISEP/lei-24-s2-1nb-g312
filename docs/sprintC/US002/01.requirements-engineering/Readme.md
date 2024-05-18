# US002 - Register a job 


## 1. Requirements Engineering

### 1.1. User Story Description

As an HRM, I want to register a job that a collaborator need to have.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>Transcribed:	_An employee has a main ocupation (job) and set off skills that enable him to perform certain tasks._
>
> Justification: An employee is given a position upon hiring, which HRM needs to register.


**From the client clarifications:**

> **Question:** (09/03/2024) What parameters should we receive?
>
> **Answer:** The job name. For example: gardener, electrician, designer, estimator, vehicle driver.

> **Question:** (09/03/2024) The job needs a description?
>
> **Answer:** It could be interesting because of the distance between the people who manage documentation and systems and between the people who are in the field, these types of descriptions facilitate the secretarial work.

> **Question:** (16/03/2024) Which other acceptance criteria can we consider? Using only characters?
>
> **Answer:** No digits, no special characters like hashtags or exclamation points. Can have spaces or dashes but nothing else.
>
> **Atualization:** On version 2.0 of the document provided from the client he clarified is previous answer and defined as acceptance criteria "A job name can't have special characters or digits"



### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** A job name can't have special characters or digits.
* **AC3:** The user should validate the data entered for the job to make sure it is correct.
* **AC4:** The Job name should be in capital letters, in order to prevent duplicates.

### 1.4. Found out Dependencies


### 1.5. Input and Output Data

**Input Data:**

* Typed data:
    * a job
    * a description 
    
	
**Output Data:**

* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)



![System Sequence Diagram - Alternative One](svg/us002-system-sequence-diagram-alternative-one.svg)



### 1.7 Other Relevant Remarks

