#PROJECT SPECIFICATION
The program simulates a taxi queueing service application, where groups of people queue for a journey in a taxi. For them to be able to book a taxi, they will wait for a window kiosk to become available and wait to be served in order of arrival. Each window will take a group of people, find the next available taxi, and assign the group to the taxi. The taxi will then start the journey so the cost can be calculated for the passengers. 

The program starts showing the inputs the taxi service will use for the simulation. These include:
- Number of window kiosks
- Number of groups in initial queue
- Range of passengers per group
    - Minimum group size
    - Maximum group size
- Number of taxis in initial queue
- Range of passengers allowed per taxi
    - Minimum taxi capacity
    - Maximum taxi capacity
- Processing time delay in seconds for the simulation

After these inputs are defined, the simulation can start. The window kiosks will get the next available group from the group queue and try to assign the group to a taxi. The taxi will take as many people from the group as it can and will start a journey with them. If there are people left from the group, a new taxi will be used to serve the rest, and so on. For each mile of the journey, the travelled distance and cost will be calculated, showing the progress of each of the journeys. If there are no available taxis left but there are still groups of people waiting in the group list, the queue of groups will remain waiting until there are more taxis available. If there are no more groups to process, but there are taxis available, the queue of taxis will remain on hold until a new group comes along. The program will end once all the journeys have finished and there are no more groups to process or no more available taxis.

Extensions of the application include:
- Number of window kiosks can be set.
- Range of passengers per groups can be set.
- Taxis have a limit of passengers, and this value can be set before the simulation as a range.
- The simulation processing time can be set at the beginning and can be manipulated during simulation with a slider to make it go faster or slower.
- Journeys keep running during the simulation to simulate the taxi journey, by calculating the cost and distance travelled while the journey is active.
- Groups can be added during simulation.
- Taxis can be added during simulation.
- Report with top 10 cheapest journeys that are finished in real time.
- Report with top 10 most expensive journeys that are finished in real time.
- Report with taxis that become free after journeys have finished.

##THREADS
Threads were used to simulate each window kiosk processing, and simulate each journey for a group in a taxi. To make lists, queues and the loggers thread-safe, the **synchronized** modifier was used every time a list was modified or accessed.

###Window kiosk
In window kiosk processing method, the next available group is taken from the queue (thread-safe), then the taxi is taken from the queue, and a journey is generated.

###Journey
In a journey, the computeCost method manages the process of the thread by computing the distance travelled and the cost generated by this.

##PATTERNS
For the implementation described in this document, several design patterns were used. The design pattern used for the core functionality was the Model-View-Controller (MVC) pattern. The basic MVC relationship diagram can be seen below. This was chosen as it provides a clear structure on how the simulation and its Graphical User Interface (GUI) should be implemented. The simulation itself is handled in the Model class and GUI is handled with several view classes, each of which display different information. The Controller handles any user interaction in the GUI and changes the Model class accordingly.

The Model is implemented in the TaxiServiceModel class, which controls the taxi service simulation i.e. the windows, journeys, taxis and passenger groups. The Controller is implemented in the TaxiServiceController, which contains several instances of the actionListener class. Each actionListener monitors a different button in the GUI and updates the model accordingly. There are multiple Views, each using the Observer pattern. Each View can be seen below with a brief description.
- CheapestJourneyListView: Observes the JourneyList, once it has changed, it displays the cheapest N journeys in the list. 
- DearestJourneyListView: Observes the JourneyList, once it has changed, it displays the dearest N journeys in the list.
- FreeTaxiListView: Observers the TaxiList, once it has changed, it displays the taxis that have become free after they finished their journeys.
- JourneyListView: Observes the JourneyList, once it has changed, it displays all the journeys in the list.
- GroupListView: Observes the GroupList, once it has changed, it displays all the currently waiting passenger groups.
- TaxiListView: Observes the JourneyList, once it has changed, it displays all the available taxis.
- TaxiServiceView: Observes the TaxiServiceModel, once it has changed, it redraws the main GUI window.

The Observer pattern is important as it allows an object to observe another object and be notified whenever the observed object changes. This allows the Views in the GUI to be updated independently and immediately whenever their observed object changes.

Another pattern that is used is the Singleton pattern. It is implemented in the Logger class, since there needs to be only one instance of the logger, which needs to be accessible to all the classes in the simulation, the Singleton pattern was the appropriate choice. The loggers were saved in a hashmap depending on the type of logger (INFO, ERROR, DEBUG).

The final pattern that is utilised is the factory pattern. This has been chosen as to minimize code reuse and allow more abstract methods to be used. The interfaces that are used for this pattern are:
- ObjectDequeManager: Introduces methods for the handling of a Queue list
- ObjectListManager: Introduces methods for the handling of a list
- ObjectMapManager: Introduces methods for the handling of a map
- ObjectObservable: Introduces methods for the handling of an observable object

An example of an interface is the object deque manager, which manages deques and a different one is the Object list manager. A basic difference between the two is the “get” and “getNext” which are used depending on the type of object that will use the interface (A list will probably get an item by its index, while a deque will just get the next available item).

##EXTENSIONS
###Number of window kiosks can be set.
The number of window kiosks are defined in the initial window. Each window will own its own thread and will process a group of the queue and assign a new taxi.
 
###Range of passengers per groups can be set.
Groups can have different sizes depending on the range that is set in the initial setup. Each new group will have a random number of passengers between this range. If the minimum and maximum sizes are set with the same values, all groups will have the same number of passengers.
 
###Taxis have a limit of passengers, and this value can be set before the simulation as a range.
Taxis will have a limited number of passengers that they can carry. If a window kiosk has a group with a size greater than the limit of the taxi, the passengers that can fit in the taxi will be assigned to the taxi, and the rest will be processed using the next available taxi.
 
###The simulation processing time can be set at the beginning and can be manipulated during simulation with a slider to make it go faster or slower.
The processing time delay can be set with the initial parameters. This delay is defined in seconds. Through the code (using the Thread.sleep method), this value is used to simulate either a busy window, or a journey that takes longer to finish.
 
###Journeys keep running during the simulation to simulate the taxi journey, by calculating the cost and distance travelled while the journey is active.
Journeys will have their own threads and will calculate both distance and cost during execution. In the GUI, these values will change for each journey and can be tracked during real time. This was achieved using MVC, observers, and threads.

###Groups can be added during simulation.
Using a button, a random group can be added during the simulation, to simulate a real-life scenario where more people enter a queue.
 
###Taxis can be added during simulation.
Using a button, a new taxi can be added to the taxi queue, to simulate a real-life scenario where there is a lot of demand and very few taxis. In this case, a taxi will be pulled from another queue that has been previously loaded from a file. In total, there are 100 available taxis loaded from a file at the beginning of the simulation. If at some point these taxis have been used, the program will not let the user add more taxis to the queue.

###Report with top 10 cheapest journeys that are finished in real time.
During the simulation, a list of the 10 cheapest journeys will appear after they journeys have finished (doesn’t wait until all of them have finished, just after one finishes).

###Report with top 10 most expensive journeys that are finished in real time.
During the simulation, a list of the 10 most expensive journeys will appear after they journeys have finished (doesn’t wait until all of them have finished, just after one finishes).

###Report with taxis that become free after journeys have finished.
During the simulation, a list of all the taxis that have finished their journeys will be shown.