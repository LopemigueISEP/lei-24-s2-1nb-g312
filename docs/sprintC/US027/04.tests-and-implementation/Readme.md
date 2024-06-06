# US006 - Create a Task 

## 4. Tests 

**Test 1:** Checks if returns the correct GreenSpace.

**Test 2:** Checks if returns a GreenSpace managed by another GSM.

**Test 3:** Checks if returns multiple GreenSpaces managed by the same GSM.

**Test 4:**	Checks if returns an empty list when the GSM didn't manage any GreenSpace.

       void getGreenSpaceManagedByMe() {
        GreenSpaceRepository repository = new GreenSpaceRepository();
        GreenSpace greenSpace3 = new GreenSpace("Parque Eduardo VII", "Lisboa", 25.0, "Urban Park", "Luís Montenegro");
        GreenSpace greenSpace2 = new GreenSpace("Passadiços do Paiva", "Arouca", 396.5, "Medium-Sized Park", "Marcelo");
        GreenSpace greenSpace1 = new GreenSpace("Peneda/Gerês", "Minho", 965.0, "Large-Sized Park", "Luís Montenegro");
        repository.addGreenSpace(greenSpace1);
        repository.addGreenSpace(greenSpace2);
        repository.addGreenSpace(greenSpace3);

        assertEquals(repository.getGreenSpaceManagedByMe("Marcelo").getFirst(),greenSpace2); //Teste 1 - verifica se devolve o greenSpace correto
        assertNotEquals(repository.getGreenSpaceManagedByMe("Luís Montenegro").getFirst(),greenSpace2); //Teste 2 - verifica se não devolve o greenspace gerido por outro ator

        // Teste 3 - Verifica se devolve múltiplos greenSpaces geridos pelo mesmo ator
        List<GreenSpace> greenSpacesLuis = repository.getGreenSpaceManagedByMe("Luís Montenegro");
        assertTrue(greenSpacesLuis.contains(greenSpace1));
        assertTrue(greenSpacesLuis.contains(greenSpace3));

        // Teste 4 - Verifica se devolve uma lista vazia quando o usuário não gere nenhum espaço verde
        assertTrue(repository.getGreenSpaceManagedByMe("João").isEmpty());
    }
	


## 5. Construction (Implementation)

### Class ListGreenSpacesManagedByMeController 

```java
public List<GreenSpace> getGreenSpacesManagedByMe(){

    List<GreenSpace> greenSpaceManagedByMeList= new ArrayList<>();
    Comparator<GreenSpace> comparator;

    try {

        greenSpaceManagedByMeList = greenSpaceRepository.getGreenSpaceManagedByMe(loggedInUser);

        switch (greenSpaceComparadorSelecionado){
            case "DESC_AREA":
                comparator = new GreenSpaceComparatorDescendingArea();
                break;

            case "NAME_ALPHA":
                comparator = new GreenSpaceComparatorNameAlphabetical();
                break;

            default:
                comparator = null;
        }

        if(comparator!=null) {
            Collections.sort(greenSpaceManagedByMeList, comparator);
        }

    }catch (Exception e){
        throw new RuntimeException("error in switch case in getGreenSpacesManagedByMe",e);
    }

    return greenSpaceManagedByMeList;
}
```

### Class Employee

```java
 public Employee(String name, Date birthDate, String email, int phoneNumber, Date admissionDate, String taxpayerNumber, String address, String docNumber, String job,List<Skill> skills) {

        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.taxpayerNumber = taxpayerNumber;
        this.address = address;
        this.docNumber = docNumber;
        this.admissionDate = admissionDate;
        this.email = email;
        this.job = job;
        this.skills = new ArrayList<>(skills);

    }
```


### Class GreenSpace

```java
public GreenSpace(String name, String address, double area, String typology, String greenSpaceManager) {
    this.name = name;
    this.address = address;
    this.area = area;
    this.typology = typology;
    this.greenSpaceManager = greenSpaceManager;
}
```


## 6. Integration and Demo 

n/a

## 7. Observations

n/a