# US23 - As a GSM, I want to assign a Team to an entry in the Agenda

## 4. Tests

**Test 1:** Check that it is possible to assign a tem.

	@Test
    void testAssignTeam() {
        task.assignTeam(team);
        assertEquals(team, task.getAssignedTeam());
    }

**Test 2:** Check if tasks overlap in the agenda.


	@Test
    void testTaskOverlap() {
        // Create otherTask with the same start date as task's start date
        Task otherTask = new Task(greenSpace, "Mow Lawn", "Mow the lawn", TaskUrgency.MEDIUM, 60, TaskPosition.TODOLIST, 99998);
        otherTask.setTaskStartDate(startDate);
        otherTask.setEndDate();  // Ensure the end date is set

        // Set task's start date to 30 minutes after otherTask's start date
        task.setTaskStartDate(new Date(startDate.getTime() + 30 * 60 * 1000)); // 30 minutes after startDate
        task.setEndDate();  // Ensure the end date is set

        // Check that task overlaps with otherTask
        assertTrue(task.taskOverlap(otherTask));

        // Create nonOverlappingTask with start date 1 hour after task's end date
        Task nonOverlappingTask = new Task(greenSpace, "Plant Trees", "Plant new trees", TaskUrgency.LOW, 180, TaskPosition.TODOLIST, 99997);
        nonOverlappingTask.setTaskStartDate(new Date(task.getEndDate().getTime() + 3600 * 1000)); // 1 hour after task's end date
        nonOverlappingTask.setEndDate();  // Ensure the end date is set

        // Check that task does not overlap with nonOverlappingTask
        assertFalse(task.taskOverlap(nonOverlappingTask));
    }

**Test 3:** Check if it is possible to list tasks by green space.


@Test
void getTasksByGreenSpace() throws ParseException {

        Date task5startDate = dateFormat.parse("16/06/2024 - 14");
        Date task5EndDate = dateFormat.parse("17/06/2024 - 13");
        GreenSpace greenSpace12 = new GreenSpace("ABC", "asd", 1000, GreenSpaceTypology.MEDIUM, "asd");

        Task task12 = new Task("TaskSemNomeDeJeito", "Vou-me despedir para ficar a dormir o dia todo", 5, "Type A", greenSpace12,
                TaskUrgency.LOW, TaskStatus.PENDING, null, new ArrayList<>(), 5, task5startDate, task5EndDate, TaskPosition.AGENDA);

        taskRepository.addTask(task12);
        List<Task> result = taskRepository.getTasksByGreenSpace(greenSpace);


        assertNotEquals(5, result.size()); //All 5 starting tasks are assigned to this greenspace, the number 6 task12 is in another greenspace
        assertFalse(result.contains(task1)); //contains a task of the selected greenspace
        assertFalse(result.contains(task12)); // didn't contain a task of another greenspace


    }

## 5. Construction (Implementation)

### Class Employee

```java

@Test
void testAssignTeamToTask() {
    try {
        GreenSpace greenSpace = new GreenSpace("Parque da Cidade do Porto", "Porto", 99.6, GreenSpaceTypology.LARGE, "Green Space Manager");
        Task task = new Task(greenSpace, "Clean Park", "Clean the park area", TaskUrgency.HIGH, 120, TaskPosition.TODOLIST, 99999);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        List<Employee> teamMembers = new ArrayList<>();
        teamMembers.add(new Employee("Employee", dateFormat.parse("01/01/1950"), "employee@this.app", 919017113, dateFormat.parse("01/01/1968"), "246597858", "Porto", "12345678", "EMPLOYEE"));
        Team team = new Team(teamMembers);

        Optional<Task> assignedTask = Employee.assignTeamToTask(team, task);

        assertTrue(assignedTask.isPresent());
        assertEquals(TaskStatus.PLANNED, assignedTask.get().getStatus());
        assertEquals(team, assignedTask.get().getAssignedTeam());
    } catch (ParseException e) {
        raiseInvalidInput();
    }
}

```

## 6. Observations

n/a