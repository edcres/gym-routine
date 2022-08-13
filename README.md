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
    - Meaning they can be dadded or removed at runtime
    - And the name can be edited
- Standard Bottom Sheet
- SQLite Room local storage
  - complex queries
  - 2 one-to-many relationships
- LiveData
  - Livedata Observers
  - Kotlin Flow
- Kotlin coroutines (for synchronous excecutions)
- EditText values stored in Room immediately as user types
- RecyclerView
  - Recyclerview inside a recyclerview (the list of sets inside the list of workouts)
  - The workout items' heights are resized to fill in the blanks in the view

---

### Workout List Screen:

<img align="center" width=180 src="https://user-images.githubusercontent.com/79296181/184086754-cdb2ebd6-3928-4f94-84eb-e9d3fe514ef7.gif" />

- There are several tabs that list workouts of a specific group
  - One for all workouts and the rest are added by the user
- Each workout item displays the name, sets and reps.
  - The user can type in the value of the reps, weight, and the name of the workout
    - Input is saved to the dabase as the user types (as the EditText text changes)
- In edit mode, workouts can be clicked to be deleted or edited in the edit screen
- Press the floating action button to add a new workout

---

### Edit Workout Screen:

<p align="left" style="display:flex">
  <img align="center" width=180 src="https://user-images.githubusercontent.com/79296181/184086797-79e00b1e-50f7-4379-a67f-1ada49586ac3.gif" />
  <img align="center" width=180 src="https://user-images.githubusercontent.com/79296181/184086816-a63c0f3b-8da1-4b41-9ffc-57605e3196c7.gif" />
</p>

- Can change the group or add a new group
- Can add or remove sets
- View/edit muscle targeted and notes of the workout item
- Can edit weights and reps
- Press the floating action button to save changes
