# US013 - As a GSM, I want to apply an algorithm that returns the routes to be opened and pipes needed to be laid with a minimum accumulated cost, ensuring that all points are adequately supplied. 


## 1. Requirements Engineering

### 1.1. User Story Description

As a Green Space Manager (GSM), I want to plan the irrigation systems installation in green parks, by calculating the best routes 
to be opened and the pipes needed to be laid with a minimum accumulated cost.

### 1.2. Customer Specifications and Clarifications 

**From the specifications document:**

> Transcribed: Planning and building irrigation systems in green parks are expensive and time consuming tasks. The use of computer systems and namely powerful
algorithms can save materials like pipes, reduce the time required for the planning and installation, and allow to create more efficient irrigation systems.

> Justification: Software to help the GSM to plan the irrigation systems in green parks, by calculating the best routes to be opened and the pipes 
needed to be laid with a minimum accumulated cost, ensuring that all points are adequately supplied.
 
**From the client clarifications:**

> **Question:** Is there any specific tool we should use for the graph drawing and visualization (24/04/2024)?
>
> **Answer:** You may use any package you want. Suggestions of students: Graphviz, GraphStream, ...

> **Question:** What is the output of US13 (24/04/2024)?
>
> **Answer:** Subgraph in csv file format with the minimum cost to supply all points.

> **Question:** Is there any other parameter that we should consider in the graph (24/04/2024)?
>
> **Answer:** Just subgraph and it's cost

> **Question:** Can we use ArrayLists (23/04/2024)?
>
> **Answer:** Yes, you can!

> **Question:** The vertexes given will have coordinates (16/04/2024)?
>
> **Answer:** The csv files we will provide for US13 do not provide the coordinates of the vertices, only the 
 information mentioned in US12.

> **Question:** In the csv file, the "waterPointX" and "waterPointY" are always numbers (17/04/2024)?
>
> **Answer:** They can be letters or words (Strings). They are any name that identifies the point of irrigation.

> **Question:** The cost in the csv files will be an interger (17/04/2024)?
>
> **Answer:** No, it can be a double, as it can have decimal cases.

### 1.3. Acceptance Criteria

* **AC1:** All required fields must be filled in.
* **AC2:** The vertexes must be Strings (can be numbers, letters or words).
* **AC3:** Cost must be doubles, as they can have decimal cases in the csv file.

### 1.4. Found out Dependencies

* There is a dependency on "US012 - As a GSM, I want to import a .csv file containing lines with: Water Point X, 
Water Point Y, Distance", as the graph data must be imported to the system before using the algorithm.

### 1.5 Input and Output Data

**Input Data:**
  * csv file with the graph data: 
    * vertexes (string)
    * edges (string)
    * costs (double)

* Typed data:
  
	
* Selected data:
    * csv file

**Output Data:**

* Tree of minimum cost in a csv file
* Graphic representation of the graph (tree)

### 1.6. System Sequence Diagram (SSD)


#### Alternative One


#### Alternative Two


### 1.7 Other Relevant Remarks

