# US024 - As a GSM, I want to Postpone an entry in the Agenda to a specific future date.

## 4. Tests 

**Test 1:**  Check if its possible to change the start date of a task

	@Test
    void testSetTaskStartDate() {
        task.setTaskStartDate(startDate);
        assertEquals(startDate, task.getStartDate());
    }
	

**Test 2:** Test getting list of tasks by GreenSpace

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
void testPostponedTask() {
    try {
        GreenSpace greenSpace = new GreenSpace("Parque da Cidade do Porto", "Porto", 99.6, GreenSpaceTypology.LARGE, "Green Space Manager");
        Task task = new Task(greenSpace, "Clean Park", "Clean the park area", TaskUrgency.HIGH, 120, TaskPosition.TODOLIST, 99999);
        Date newStartDate = dateFormat.parse("01/01/2025");

        task.setTaskStartDate(newStartDate);
        task.setEndDate();
        task.setTaskStatus(TaskStatus.POSTPONED);
        task.assignTeam(null);
        task.clearVehicleList();

        assertEquals(TaskStatus.POSTPONED, task.getStatus());
        assertEquals(newStartDate, task.getStartDate());
        assertNull(task.getAssignedTeam());
        assertTrue(task.getAssignedVehicles().isEmpty());
    } catch (ParseException e) {
        raiseInvalidInput();
    }
}
```




## 7. Observations

n/a