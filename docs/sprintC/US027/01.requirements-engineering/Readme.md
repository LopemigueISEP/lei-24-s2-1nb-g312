# US027 - As a GSM, I need to list all green spaces managed by me.


## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I need to list all green spaces managed by me.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

> Transcribed: _Green Spaces Manager (GSM) - the person responsible for managing the green spaces in charge of the organization._ 

> Justification: The GSM must check if the green spaces under his management have changed. 

>The list of green spaces must be sorted by size in descending order (area in hectares should be used). 

[//]: # (> **Question:** Which is the unit of measurement used to estimate duration?)

[//]: # (>)

[//]: # (> **Answer:** Duration is estimated in days.)

[//]: # ()
[//]: # (> **Question:** Monetary data is expressed in any particular currency?)

[//]: # (>)

[//]: # (> **Answer:** Monetary data &#40;e.g. estimated cost of a task&#41; is indicated in POT &#40;virtual currency internal to the platform&#41;.)

### 1.3. Acceptance Criteria

* **AC1:** The list of green spaces must be sorted by size in descending order (area in hectares should be used). The sorting algorithm to be used by the application must be defined through a configuration file. At least two sorting algorithms should be available.
* **AC2:** The list of green spaces must show the typology of each green space.

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