# US029 - As a Collaborator, I want to record the completion of a task.

## 3. Design - User Story Realization

### 3.1. Rationale

| Interaction ID | Question: Which class is responsible for...            | Answer                 | Justification (with patterns)                       |
|:---------------|:-------------------------------------------------------|:-----------------------|:----------------------------------------------------|
| Step 1	        | 	... interacting with the actor?                       | CompleteTaskUI         | Pure Fabrication: responsible for user interactions |
| 	              | 	... coordinating the US?                              | CompleteTaskController | Controller                                          |
| Step 2         |                                                        |                        |                                                     |
| Step 3         | ... interacting with the actor?                        | CompleteTaskUI         | Pure Fabrication: responsible for user interactions |
|                | ... coordinating the US?                               | CompleteTaskController | Controller                                          |
|                | ... saving inputted data and record completion of task | Employee               | IE: owns task data                                  |
| Step 4         |                                                        |                        |                                                     |
| Step 5         | ... interacting with the actor?                        | CompleteTaskUI         | Pure Fabrication: responsible for user interactions |
|                | ... knowing the possible task status?                  | TaskStatus             | Enumeration                                         |
| Step 6         |                                                        |                        |                                                     |

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are:

* Task
* TaskStatus

Other software classes (i.e. Pure Fabrication) identified:

* TaskUI
* TaskController

## 3.2. Sequence Diagram (SD)

### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us029-sequence-diagram-full.svg)

## 3.3. Class Diagram (CD)

![Class Diagram](svg/us029-class-diagram.svg)