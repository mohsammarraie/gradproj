
1- Project setup:
*The database is a dmp file in the database folder. To import database, type in command line: imp
*Database login credential is included in ConnectionDao.js
*You can access the database using sql developer.
*You can use glassfish server 4 and set it up with Netbeans IDE.

*You need to create a username and password for each user type (admin, driver or/and student).

2- Abstract:

In this project we represent a Bus management system centered on the eco-system of GJU. The proposed project would be an
extension of the MyGJU system.

This web app has 3 main actors: admin, driver and student. An admin inputs or creates 'Objects' to be used later in the app.
These objects are:

A- Buses
B- Students
C- Drivers
D- Routes
E- Stops
F- Schedules

From these objects, an admin can create 'trips' that will be eventually be viewed and tracked by students.
The process of creating trips starts from the admin. The admin enters into the app: buses information, driver information 
(Name, ID and other attributes), student information (Name, ID and other attributes), stops names (Name), routes names
(source, destination and route code). All of the above objects have unique values for each entry and information has to be 
entered  in English and in Arabic (when required) and all entries in each object category can be edited or removed by the 
admin later on. In addition to that, we have implemented checks and regulations for data entry for each object category so 
that all data in  our data base is consistent and up to standard.

After data entry is completed, the admin then can assign drivers to buses and this is an exclusive relationship as we have
allowed only one driver to be assigned to one bus and to keep everything in order we have implemented a check that prevents 
the admin from removing any driver that is assigned to bus and vice versa (to remove a driver or bus the admin has to first
unassign the assigned driver to that bus). Another check was implemented to prevent from assigned drivers that are already 
assigned to other buses.

The next step is assigning stops to routes (or unassign) and then creating (one or more) schedules for these routes by giving
each stop an arrival time (a check was implemented to assure that each stop time is later than the stop time of the stop before
it to prevent time conflicts).

Next step is to assign a bus to each route schedule and here a bus can be assigned to more than one route schedule as long as
the departure time and arrival time of the schedules assigned to this bus do not conflict. To make sure of this we have created
another check that informs the admin if such a conflict occurs and provides the conflicting schedules information for the admin
to resolve.

Now that schedules are assigned to drivers, a driver can now sign into his account in the app using a mobile device, because in
order for the tracking mechanism to work, it needs to access the built-in GPS in mobile devices. Then the driver can view his
assigned schedules in a table with all relevant information and this is done in a way such that each schedule is highlighted in
green if the current time is in the range of the departure time of this schedule. If some schedules are too early to start then
they are colored red and if the driver has missed the range of the departure time of a schedule then it is colored orange and 
considered late (the admin is notified of this) the driver can start a schedule (if colored green or orange) which is now called
'Trip' and a record of all relevant information of this trip is saved to our database (like actual departure time) and can now be
viewed and tracked by students and admins.

The driver now can either 'end the trip' (if the destination is reached) which will save additional information to the database
(like actual arrival time) or cancel the trip (this means the trip has not reached its destination and the admin is notified of this).
Now that we have trips of different routes and schedules and by different drivers, student can sign into their accounts and view all
available 'ongoing' trips and its relevant information and they are able to track each trip on the map. In addition to this,
students can also submit ratings and reviews of trips based on criteria we have created which are then viewed by admins.

The cycle concludes with the admins again as they receive feedback in the form of reports created automatically by the app from
trips that include useful data to use for future optimization.

The reports page in our app will contain every piece of data about the trip available which means it will be very large that is why
we have implemented a filtering mechanic similar to that of MyGJU course sections filters. This help the admin pinpoint his exact
need when analyzing past performance of the bus transit system.

Another form of feedback is of course the rating and written reviews submitted by students.
