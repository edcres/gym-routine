# gym-routine

App to organize weight lifting workouts.
Interesting Room database queries and setup are showcased

---

### Tools and skills used:

- MVVM architecture
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

- There are several tabs that list workouts of a specific group
  - One for all workouts and the rest are added by the user
- Each workout item displays the name, sets and reps.
  - The user can type in the value of the reps, weight, and the name of the workout
    - User input is saved to the dabase every time the user types
- In edit mode, workouts can be clicked to be deleted or edited in the edit screen
- Press the floating action button to add a new workout

---

### Edit Workout Screen:

- Can change the group or add a new group
- Can add or remove sets
- View/edit muscle targeted and notes of the workout item
- Can edit weights and reps
- Press the floating action button to save changes
