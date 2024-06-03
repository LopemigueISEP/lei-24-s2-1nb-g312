# US021 - Add a new entry to the To-Do List 


## 1. Requirements Engineering

### 1.1. User Story Description

As a GSM, I want to add a new entry to the To-Do List.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	Each task is characterized by having a unique reference per organization, a designation, an informal and a technical description, an estimated duration and cost, as well as a task category.


>	The To-Do List comprises all the tasks required to be done in order to assure the proper functioning of the parks.

> The To-Do List comprises all the tasks required to be done in order to assure the proper functioning of the parks. These tasks
can be regular (e.g. pruning trees) or occasional (e.g. repairing a broken
equipment). They may also require a multidisciplinary team and the length
of the task can vary from a few minutes (e.g. replacing a light bulb) to weeks
(e.g. installing an irrigation system).

> The To-Do List comprises all pending tasks for all parks. The entries in
this list describe the required task, the degree of urgency (High, Medium,
and Low), and the approximate expected duration.

### 1.3. Acceptance Criteria

* **AC1:** The new entry must be associated with a green space managed by the GSM.
* **AC2:** The green space for the new entry should be chosen from a list presented to the GSM.


### 1.4. Found out Dependencies

* There is a dependency on "US020 -  Register a green space" as there must be at least one green space to respect **AC1**.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:
    * taskTitle
    * taskDescription
    * expectedDuration
	
* Selected data:
    * greenSpaces
    * urgency
      * High
      * Medium
      * Low

**Output Data:**

* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

![System Sequence Diagram](\svg\us021-system-sequence.svg)
