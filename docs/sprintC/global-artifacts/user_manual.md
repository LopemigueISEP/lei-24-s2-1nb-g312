# User Manual
## Cover

### Título da aplicação!!!!!


_LAPR2 – 2º semester of the 1st Year of the Degree in Computer Engineering_

Group 312 1NA/NB – _CodeSawyers_
![team logo.png](..%2F..%2F..%2F..%2F..%2FDownloads%2Fteam%20logo.png)

André Alexandre Magalhães Azevedo – _1230932_

Carlos Miguel Pereira Coelho – _1221808_

Miguel Ângelo Malheiro Lopes – _1222183_

Rui Gabriel Ribeiro Margarido – _1230420_

Tiago Leal Ferreira de Sá – _1201925_

## Table of contents
1. Glossary
2. Introduction
3. System Overview
4. Features/Functions

## 1. [Glossary](01.requirements-engineering/glossary.md)

## 2. Introduction
This user manual for green spaces management software created by CodeSawyers serves as a comprehensive guide designed to assist green spaces managers and administrators in effectively utilizing our application to streamline the management of outdoor environments.

This user manual is primarily addressed to green spaces managers and administrators with the responsibility of overseeing and maintaining outdoor areas such as parks, gardens, and recreational spaces. 

The purpose of this manual is to provide a detailed overview of the functionalities and features of our software, offering step-by-step instructions on how to navigate its interface, being acessible for all kind of users: detailed explanations of various modules and tools available within the application, including  task scheduling, resource allocation, inventory management, and reporting functionalities. Additionally, troubleshooting tips and best practices are provided to ensure smooth operation and optimal utilization of the software.

The product is a software solution to be developed and composed of a set of applications in Java that should accomplishes with the requirements.


## 3. System Overview
Our application is a comprehensive software solution designed for the management and maintenance of outdoor environments, having in mind that the main objectives are simplifying the management of green spaces through digital automation, always optimizing resource allocation which facilitates planning and maintenance strategies.

The main features are of our application are the possibility to register and manage collaborator's information, including skills ans jobs; possibility to generate automatically team proposals based on specific criterias; possibility to maintains vehicles records, including check-ups schedules and maintenance history.

The application is structured into various modules, specifying aspects of green spaces management. These modules are interlinked to ensure functionality and data flow throughout the system. 

Dependencies among functions include collaborator management with registration and assignment to tasks; skillsets required for various jobs and assignation to collaborators; automatically generatarion of team compositions based on specified criteria - required skills and team size; management of the registration of organization vehicles used in green spaces maintenance, and maintenance activities and schedules upcoming check-ups.



## 4. Features/functions 
Our applications has several features and functions. Next, we will shows each of them with a printscreen from our application and a brief explanation of how to use it correctly.
The application has three main menus for authentication (ADMIN, HRM and VFM), and each of them have different permissions in the application.

Admin Menu

![img.png](PrintScreens%20for%20User%20Manual/AdminMenu_1.png)


HRM Menu

![img.png](PrintScreens%20for%20User%20Manual/HRMmenu_1.png)


VFM Menu

![img.png](PrintScreens%20for%20User%20Manual/VFMMenu.png)


### 1. Register skills for collaborators | HRM
In this option the user can register skills and his descriptions. 

#### PrintScreen:
![img.png](PrintScreens%20for%20User%20Manual/US01_registerSkill.png)

#### Instructions of use (step-by-step):
After logging in with a previously defined username and password, the administrator or HRM should select the corresponding option (option number 1 for both), and then they will start the skill registration process. 
The user will be prompted to enter the skill name and description. 
The skill name should not contain special characters or digits, so if any of the requested data is inputted incorrectly, a message will appear indicating how the information should be inputted correctly. 
After entering the data, it will be displayed for confirmation by the user.
If the data is correct, the employee who registered the skill will be shown, along with a message confirming the creation of the skill.

### 2. Show registered skills | HRM
In this option the user can list all the skills registered in the system.

#### PrintScreen:
![img.png](PrintScreens%20for%20User%20Manual/US01_showRegisteredSkills.png)

#### Instructions of use (step-by-step):
After log in, with username and password previously defined, the user should type option number 2 and then a list of all registered skills appears.

### 3. Register jobs for collaborators | HRM
In this option the user can register jobs and his descriptions.
#### PrintScreen:
![img.png](PrintScreens%20for%20User%20Manual/US02_registerJob.png)

#### Instructions of use (step-by-step):
After logging in with a previously defined username and password, the administrator or HRM should select the corresponding option (option number 3 for both), and then they will start the skill registration process.
The user will be prompted to enter the job name and description.
The job name should not contain special characters or digits, so if any of the requested data is inputted incorrectly, a message will appear indicating how the information should be inputted correctly.
After entering the data, it will be displayed for confirmation by the user.
If the data is correct, the employee who registered the job will be shown, along with a message confirming the creation of the job.


### 4. Show registered jobs | HRM 
In this option the user can list all the jobs registered in the system.
#### PrintScreen:
![img.png](PrintScreens%20for%20User%20Manual/US02_showRegisteredJobs.png)


#### Instructions of use (step-by-step):
After log in, with username and password previously defined, the user should type option number 4 and then a list of all registered jobs appears.

### 5. Register collaborator with job and fundamental characteristics | HRM

In this option the user can register a collaborator with fundamental characteristics and a job. When registered, the system will store the data for the collaborator.
   
#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual/US03RegisterCollabStart.png)

#### Instructions of use (step-by-step):

After log in, with username and password previously defined, the user should type  option number 5 and then they will start the registration process of the new employee. The user will be asked to input the collaborator taxpayer number, name, birthdate, e-mail, phone number, admission date, address, ID doc type and select a job from the available ones. If any of the requested data is inputted incorrectly, a message will appear saying how the information should be inputted. When selecting the job, a list of registered jobs appear and the user only have to choose one. In the end, the user must confirm the inputted data and a message of success appears.
The first fundamental characteristic required is the taxpayer number because if the inputted number is already registered in the system, a error message appear with new option to select (each citizen working in Portugal have different taxpayer number).

![img.png](PrintScreens%20for%20User%20Manual/US03RegisterCollabOk.png)

If the taxpayer number is already registered in the system, this message occurs and the user must choose an option:

![img.png](PrintScreens%20for%20User%20Manual/US03TaxpayerNumberRegistered.png)

Example of an help message

![img.png](PrintScreens%20for%20User%20Manual/US03taxpayernumbercorrectinput.png)

## 6. Show registered collaborators | HRM

In this option the user can list all collaborators registered in the system.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual/ListEmployeesRegistered.png)

#### Instructions of use (step-by-step):

   After log in, with username and password previously defined, the user should type option number 6 and then a list of registered employees appears.

## 7. Assign skills to collaborators | HRM

In this option the user can assign registered skills to registered collaborators. When assigned, the system will store the skill in the collaborator's profile.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual/US04AssignMenu.png)

#### Instructions of use (step-by-step):

After log in, with username and password previously defined, the user should type option number 7 and then they will start assigning skills to collaborators registered in the system. The user should select the collaborator (or select 0 if he wants to cancel) and a list of available skills possible to be added to that collaborator appears. Then he selects which skill he wants to add. 

![img.png](PrintScreens%20for%20User%20Manual/Us04AddSkillCollab.png)

Next, the user is asked if he wants to add more skills. If yes, a new list will appear without the skill added previously. 

![img.png](PrintScreens%20for%20User%20Manual/Us04NoMoreSkillsToAdd.png)

Otherwise the user must confirm the skills added and a message of success appears.

![img.png](PrintScreens%20for%20User%20Manual/US04finalConfirmation.png)

If the user tries to select a collaborator or add a skill numbered different from the shown, an help message will appear and at the time of confirmation, if a character different from the suggested is inputted, an help message will appear.

![img_1.png](PrintScreens%20for%20User%20Manual/US04Error1.png)
![img_2.png](PrintScreens%20for%20User%20Manual/US04Error2.png)

## 8. Show collaborators and his skills | HRM

In this option the user can list all collaborators and skills associated registered in the system.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual/ListCollabSkills.png)

#### Instructions of use (step-by-step):

   After log in, with username and password previously defined, the user should type option number 8 and then a list of registered employees and associated skills appears. 

## 9. Generate team proposals automatically | HRM

In this option the user can generate team proposals automatically with registered collaborators. When created, the system will store the team in the system.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual/Us05Teams.png)

#### Instructions of use (step-by-step):

After log in, with username and password previously defined, the user should type option number 10 and then they will start generating a team. The user should select the number of minimum collaborators, number of maximum collaborators, the skill set needed for the team and then a message of sucess will appear.
![img.png](PrintScreens%20for%20User%20Manual/US05GenerateTeam1.png)
![img.png](PrintScreens%20for%20User%20Manual/US05GenerateTeam2.png)

Then, the user can accept, accept and modify or decline the team generated.

![img.png](PrintScreens%20for%20User%20Manual/US05Accept.png)
![img.png](PrintScreens%20for%20User%20Manual/US05TeamModify.png)
![img.png](PrintScreens%20for%20User%20Manual/US05DeclineTeam.png)

If it is impossible to generate a team, a message will appear.
![img.png](PrintScreens%20for%20User%20Manual/US05InsucessTeam.png)

## 10. Show teams | HFM

In this option the user can list all teams registered in the system.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual/ListTeams.png)

#### Instructions of use (step-by-step):

After log in, with username and password previously defined, the user should type option number 10 and then a list of teams appears.

## 11. Register vehicle | VFM

In this option the user can register a vehicle. When registered, the system will store the data for the vehicle.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual/US06RegisterVehicle.png)

#### Instructions of use (step-by-step):

After log in, with username and password previously defined, the user should type  option number 1 if VFM or 11 if Admin and then they will start the registration process of the new vehicle. The user will be asked to input the vehicle registration plate, brand, model, type, tare, gross weight, current kilometers, register date, acquisition date and checkup kilometers frequency. If any of the requested data is inputted incorrectly, a message will appear saying how the information should be inputted. In the end a message of success appears.
![img.png](PrintScreens%20for%20User%20Manual/US06CreateVehicle.png)

The first data required is the registration plate because if the inputted data is already registered in the system, a error message appear saying that the vehicle already exists.
![img.png](PrintScreens%20for%20User%20Manual/US06VehicleRegistered.png)

## 12. Register vehicle check-up | VFM

In this option the user can register a check-up for a vehicle. When registered, the system will store the date and kilometers of the last check-up of a vehicle in order to estimate when the vehicle need's to do a new check-up.

#### Instructions of use (step-by-step):

After log in, with username and password previously defined, the Administrator or VFM, should type option 12 "Register Check-up" in the Main Menu. The user will be asked to input the vehicle plate number, date of the check-up and the vehicle kilometers at the check-up. 
**

#### PrintScreen:
![img.png](PrintScreens%20for%20User%20Manual%2Fus7%20-%20Main%20menu.png)

* If the vehicle is not registered in the system, a message will appear saying "Vehicle not found. Please register the vehicle first.". 

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus7%20-%20registration%20failed.png)

* If the vehicle is registered in the system, a message will appear saying "Check-Up registered successfully."

#### PrintScreen:

  ![img.png](PrintScreens%20for%20User%20Manual%2Fus7%20-%20registration%20success.png)

## 13. List vehicles needing check-up | VFM
    
In this option the user can ask the system to list all the vehicles that need a check-up. The system will check each registered vehicle next check-up target kilometers, and list vehicles that are within 5% of that target for the next check-up and vehicles overdue to check-up.

#### Instructions of use (step-by-step):

After log in, with username and password previously defined, the Administrator or VFM, should type option 13 "List Vehicles due to Check-up" in the Main Menu.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus8%20-%20Main%20menu.png)

* If there are no vehicles needing check-up, a message will appear saying "There are no vehicles due for a check-up at this time.".

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus8%20-%20ListVehiclesDueToCheckUp%20failed.png)

* If there are vehicles needing check-up, the system will print a table with each vehicle information: registration plate, brand, model, current kilometers, check-up km frequency, km at last check-u and km at next check-up".

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus8%20-%20ListVehiclesDueToCheckUp%20success.png)

## 14. Import CSV for Planning and Building Irrigation System | ???

#### PrintScreen:

#### Instructions of use (step-by-step):


## 15. Planning and Building Irrigation System | ???

In this option the user can plan the building of an irrigation system. The system will calculate the best route to build the irrigation system using the Kruskal Algorithm. At the end it will generate two png files of the input irrigation system scheme (input graph) and the output proposed irrigation path with the least cost (minimum cost tree subgraph), it also generates a csv text file with the minimum cost tree and the calculations of graph dimension, graph order and total cost.

#### Instructions of use (step-by-step):

After log in, with username and password previously defined, the Administrator or QAM, should type option 15 "Planning and Building Irrigation System" in the Main Menu.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus13%20-%20Main%20menu.png)

* The system will ask the user to choose one an imported csv files with the irrigation system scheme (original graph) from a list.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus13%20-%20choose%20file%20available%20to%20run%20kruskal%20algorithm.png)

* If there are no imported csv files in the system, it will show a message saying "No CSV files available, import CSV file first.".

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus13%20-%20No%20files%20available%20to%20run%20kruskal%20algorithm.png)

* After choosing a file from the list, the system will run the Kruskal Algorithm and generate the output files.
***
* The system will generate a png file with the input irrigation system scheme (input graph).

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus13%20-%20kruskal%20input%20graph.png)

* The system will generate a png file with the output irrigation system minimum cost tree (output graph).

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus13%20-%20kruskal%20output%20graph.png)

* The system will generate a csv file with the minimum cost tree and the calculations of graph dimension, graph order and total cost.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus13%20-%20kruskal%20output%20csv.png)

## 16. Import Group Files and Run-Time Testing Kruskal Algorithm

#### PrintScreen:

This option imports a group of csv files with input graphs to run the kruskal algorithm on them and record the runtime necessary to run each file. The files will have an ascending number of vertices, in order to analyze the algorithm performance with different csv files size (number of vertices in each graph).

#### Instructions of use (step-by-step):
    
After log in, with username and password previously defined, the Administrator or QAM, should type option 16 "Import Group Files and Run-Time Testing Kruskal Algorithm" in the Main Menu.

#### PrintScreen:
![img.png](PrintScreens%20for%20User%20Manual%2Fus14%20-%20Main%20menu.png)

* Type the folder path with the csv files

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus14%20-%20folder%20path.png)

* If the folder path is invalid, the system will show a message saying "Folder nor found, or is not a directory." If the folder has no files, the system will ask you the folder path again.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus14%20-%20path%20error.png)

* The system will import each csv file from the folder and run the kruskal algorithm on them, recording the runtime and number of vertices of each file. Then the system will print the results of each file, sorted by the number of vertices.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus14%20-%20text%20results.png)

* The system will generate a png file with a graph (x axis: number of vertices, y axis: runtime) showing the results of number of vertices/runtime for each file.

#### PrintScreen:

![img.png](PrintScreens%20for%20User%20Manual%2Fus14%20-%20graph%20results.png)

