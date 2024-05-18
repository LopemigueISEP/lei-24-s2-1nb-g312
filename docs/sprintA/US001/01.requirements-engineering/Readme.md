# US001 - Register a skill


## 1. Requirements Engineering

### 1.1. User Story Description

As a Human Resources Manager (HRM), I want to register skills that a collaborator may have.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

> Transcribed:	_An employee has a main ocupation (job) and set off skills that enable him to perform certain tasks._ 
> 
> Justification: Skill is the main element that allows HRM to allocate workers to teams to perform tasks.

**From the client clarifications:**

> **Question:** (09/03/2024) What parameters should we receive? 
>
> **Answer:** The skill name. For example: car driver, agricultural machinery operator, phytopharmaceutical applicator. 

> **Question:** (09/03/2024) The skill needs a description?
>
> **Answer:** It could be interesting because of the distance between the people who manage documentation and systems and between the people who are in the field, these types of descriptions facilitate the secretarial work.

> **Question:** (16/03/2024) Which other acceptance criteria can we consider? Using only characters?
>
> **Answer:** No digits, no special characters like hashtags or exclamation points. Can have spaces or dashes but nothing else.



### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** The field name can only have letters, no digits, no special characters like hashtags or exclamation points. Can have spaces or dashes but nothing else.
* **AC3:** It is necessary to validate the data entered for the skill to make sure it is correct.
* **AC4:** It shouldn't allow duplicated skills (e.g. avoid the same skill starting with or without capital letter). 

### 1.4. Found out Dependencies

* Should have skills to register.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * a skill
    * a description 
  
	
**Output Data:**

* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)


![System Sequence Diagram - Alternative One](svg/us001-system-sequence-diagram-alternative-one.svg)


### 1.7 Other Relevant Remarks

