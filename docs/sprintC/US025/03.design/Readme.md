# US025 - As a GSM, I want to Cancel an entry in the Agenda

## 3. Design - User Story Realization 

### 3.1. Rationale

| Interaction ID | Question: Which class is responsible for...  | Answer                      | Justification (with patterns)                                                                                 |
|:---------------|:---------------------------------------------|:----------------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1  		     | 	... interacting with the actor?             | CancelEntryAgendaUI         | Pure Fabrication: there is no reason to assign this responsibility to any existing class in the Domain Model. |
| 			  		        | 	... coordinating the US?                    | CancelEntryAgendaController | Controller                                                                                                    |
| 			  		        | ... knowing the user using the system?       | EmployeeRepository          | IE: knows the GSM                                                                                             |
| Step 2  		     | 		... show available agenda entries?					    | TaskRepository              | IE: Knows all entries (tasks).                                                                                |
| Step 3  		     | 	...validating selected data?                | AddEntryToDoListUI          | Pure Fabrication: validates fields                                                                            |
| Step 4  		     | 	                                            |                             |                                                                                                               |
| Step 5  		     | 	... updating Task?                          | Employee                    | IE: knows task                                                                                                |
| 		             | 	... validating all data (local validation)? | Task                        | IE: owns its data.                                                                                            | 
| Step 8  		     | 	... informing operation success?            | CancelEntryAgendaUI         | IE: is responsible for user interactions.                                                                     | 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

* Organization
* Task

Other software classes (i.e. Pure Fabrication) identified: 

* CreateTaskUI  
* CreateTaskController


## 3.2. Sequence Diagram (SD)

_**Note that SSD - Alternative Two is adopted.**_

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us025-sequence-diagram-full.svg)

### Split Diagrams

The following diagram shows the same sequence of interactions between the classes involved in the realization of this user story, but it is split in partial diagrams to better illustrate the interactions between the classes.

It uses Interaction Occurrence (a.k.a. Interaction Use).

![Sequence Diagram - split](svg/us006-sequence-diagram-split.svg)

**Get Task Category List Partial SD**

![Sequence Diagram - Partial - Get Task Category List](svg/us006-sequence-diagram-partial-get-task-category-list.svg)

**Get Task Category Object**

![Sequence Diagram - Partial - Get Task Category Object](svg/us006-sequence-diagram-partial-get-task-category.svg)

**Get Employee**

![Sequence Diagram - Partial - Get Employee](svg/us006-sequence-diagram-partial-get-employee.svg)

**Create Task**

![Sequence Diagram - Partial - Create Task](svg/us006-sequence-diagram-partial-create-task.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us025-class-diagram.svg)