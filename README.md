# gym-routine

App to organize weight lifting workouts.
Interesting Room database queries and setup are showcased

---

### Tools and skills used:

- MVVM architecture
  - shared ViewModel
- Material Design
- Jetpack Navigation Component
- View Pager 2 tabs
  - The tabs are mutable
    - Meaning they can be added or removed at runtime
    - And the name can be edited
- Standard Bottom Sheet
- SQLite Room local storage
  - complex queries
  - 2 one-to-many relationships
- LiveData
  - Livedata Observers
  - Kotlin Flow
- Kotlin coroutines (for synchronous executions)
- EditText values stored in Room immediately as user types
- RecyclerView
  - Recyclerview inside a recyclerview (the list of sets inside the list of workouts)
  - The workout items' heights are resized to fill in the blanks in the view

---

### Workout List Screen:

<img align="center" width=180 src="https://user-images.githubusercontent.com/79296181/189302421-fc8c3df6-0505-4036-abc6-ddf58a360b38.gif" />

- There are several tabs that list workouts of a specific group
  - One for all workouts and the rest are added by the user
- Each workout item displays the name, sets and reps.
  - The user can type in the value of the reps, weight, and the name of the workout
    - Input is saved to the database as the user types (as the EditText text changes)
- In edit mode, workouts can be clicked to be deleted or edited in the edit screen
- Press the floating action button to add a new workout

---

### Edit Workout Screen:

<p align="left" style="display:flex">
  <img align="center" width=180 src="https://user-images.githubusercontent.com/79296181/189302442-84a3806a-611f-4fb6-bcd0-ea21a88b614f.gif" />
  <img align="center" width=180 src="https://user-images.githubusercontent.com/79296181/189302452-7955f6bc-caff-4bc0-b549-91d23086b099.gif" />
</p>

- Can change the group or add a new group
- Can add or remove sets
- View/edit muscle targeted and notes of the workout item
- Can edit weights and reps
- Press the floating action button to save changes
