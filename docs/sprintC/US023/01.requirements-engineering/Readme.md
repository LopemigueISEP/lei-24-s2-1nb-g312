# US023 - As a GSM, I want to assign a Team to an entry in the Agenda.


## 1. Requirements Engineering

### 1.1. User Story Description

As an Green Space Manager, I want to assign a Team to a task entry in the Agenda.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

>	Transcribed: The Agenda is made up of entries that relate to a task (which was previously in the To-Do List), 
the team that will carry out the task, the vehicles/equipment assigned to the task, expected duration, and the status 
(Planned, Postponed, Canceled, Done).

> Justification: The GSM needs to assign teams to complete tasks that are listed in the Agenda. 

**From the client clarifications:**

> **Question:** Should we assume teams are a fixed set of people or they change frequently?
>
> **Answer:** Teams are a fixed set of people. They are created by the GSM and shouldn't vary frequently.

> **Question:** Should we check the team or the collaborators availability to be assigned to the task in the agenda?
>
> **Answer:** As the team is considered a fixed set of people, there is no need to check the availability of the 
collaborators, checking the team availability is enough.

> **Question:** How long should we rate the duration of tasks for team assignment?
> 
> **Answer:** The duration of the task should be rated in quarter or half a day for easier use.

### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC3:** The team must be available to carry out the task in the Agenda.
* **AC4:** A message must be sent to all team members informing them about the assignment.
* **AC5:** Different email services can send the message. These services must be defined through a configuration file 
to allow the use of different platforms (e.g. Gmail, DEI’s email service, etc.).

### 1.4. Found out Dependencies

* There is a dependency on "US005 - Generate a team proposal" as there must be teams previously created to be assigned 
to the tasks entries in the Agenda.
* There is a dependency on "US022 - Add a new entry in the Agenda" as there must be a task in the Agenda to be 
assigned to a team.

### 1.5 Input and Output Data

**Input Data:**

* Typed data:


* Selected data:
    * a task from the Agenda
    * a team to carry out the task

**Output Data:**

* (In)Success of the operation

### 1.6. System Sequence Diagram (SSD)

**_Other alternatives might exist._**

#### Alternative One

![System Sequence Diagram - Alternative One](svg%2Fus023-system-sequence-diagram-alternative-one.svg)

### 1.7 Other Relevant Remarks

