# US0012 - As a GSM, I want to import a .csv file containing lines with: Water Point X, Water Point Y, Distance


## 1. Requirements Engineering

### 1.1. User Story Description

As a Green Space Manager (GSM), I want to import csv files to study the planning of irrigation systems in the parks.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

> Transcribed: Planning and building irrigation systems in green parks are expensive and time consuming tasks. The use of computer systems and namely powerful
algorithms can save materials like pipes, reduce the time required for the planning and installation, and allow to create more efficient irrigation systems.

> Justification: Software to help the GSM to plan the irrigation systems in green parks, by calculating the best routes to be opened and the pipes
needed to be laid with a minimum accumulated cost, ensuring that all points are adequately supplied.


**From the client clarifications:**

> **Answer:** Will we be given the distance and/or costs between different water points? In US12 when we receive only the distance this value will be used as cost in the minimum accumulated cost algorithm developped in US13?
>
> **Question:** You only have one value assigned between different water points, called the "cost", that could mean a distance or something else.
 
> **Answer:** I'd like to confirm if, in the CSV file, "waterPointX" and "waterPointY" are always integers or they could be letters?
>
> **Question:** They could be letters or words (strings). They are any names that identify the irrigation point.


### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** The task reference must have at least 5 alphanumeric characters.
* **AC3:** When creating a task with an existing reference, the system must reject such operation and the user must be able to modify the typed reference.

### 1.4. Found out Dependencies

* There is a dependency on "US003 - Create a task category" as there must be at least one task category to classify the task being created.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * a reference
    * a designation 
    * an informal description
    * a technical description
    * an estimated duration
    * an estimated cost
	
* Selected data:
    * a task category 

**Output Data:**

* List of existing task categories
* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg/us006-system-sequence-diagram-alternative-one.svg)

#### Alternative Two

![System Sequence Diagram - Alternative Two](svg/us006-system-sequence-diagram-alternative-two.svg)

### 1.7 Other Relevant Remarks

* The created task stays in a "not published" state in order to distinguish from "published" tasks.