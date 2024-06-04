# US024 - As a GSM, I want to Postpone an entry in the Agenda to a specific future date.

## 3. Design - User Story Realization 

### 3.1. Rationale


| Interaction ID                                               | Question: Which class is responsible for...            | Answer                            | Justification (with patterns)                                                                                 |
|:-------------------------------------------------------------|:-------------------------------------------------------|:----------------------------------|:--------------------------------------------------------------------------------------------------------------|
| Step 1: asks to postpone task in the agenda  		              | 	... instantiating the class that handles the UI?      | PostponeTaskInTheAgendaUI         | Pure Fabrication: responsible for user interactions  |
| 			  		                                                      | 	... coordinating the US?                              | PostponeTaskInTheAgendaController | Controller                                                                                                    |
| Step 2 : get list of tasks in the Agenda with Planned status | 	... fetching the list of planned tasks?               | PostponeTaskInTheAgendaController | Controller                                                          |
| 			  		                                                      | 		...displaying list?					                             | PostponeTaskInTheAgendaUI         | Pure Fabrication: responsible for user interactions                                                                               |
| Step 4 : check team availability and asks for new date 		    | 	...checks team availability? 						                   | PostponeTaskInTheAgendaController |Controller|
| 	                                                            | ...shows fundamental characteristics (requested data)? | PostponeTaskInTheAgendaUI         |Pure Fabrication: responsible for user interactions|
| Step 5 : requests task to be postponed to a new date  		     | 	...requests the update of a task in the agenda?       | PostponeTaskInTheAgendaController |Controller|
| 		                                                           | 	... edits task in the Agenda ?                        | Employee                          |IE: This repository stores all tasks in the Agenda.|
| 		                                                           | 	... saving the input data?					                       | agendaRepository                  |                                                                                                               |              
| Step 6 : display operation success  		                       | 	... informing operation success?                      | PostponeTaskInTheAgendaUI| Pure Fabrication: responsible for user interactions| 

### Systematization ##

According to the taken rationale, the conceptual classes promoted to software classes are: 

* Employee
* Task
* Agenda

Other software classes (i.e. Pure Fabrication) identified: 

* PostponeTaskInTheAgendaController  
* PostponeTaskInTheAgendaUI


## 3.2. Sequence Diagram (SD)


### Full Diagram

This diagram shows the full sequence of interactions between the classes involved in the realization of this user story.

![Sequence Diagram - Full](svg/us024-sequence-diagram-full.svg)



## 3.3. Class Diagram (CD)

![Class Diagram](svg/us024-class-diagram.svg)