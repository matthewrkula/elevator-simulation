[1mdiff --git a/bin/com/mattkula/se350/elevatorsimulator/building/Building.class b/bin/com/mattkula/se350/elevatorsimulator/building/Building.class[m
[1mindex 7182cf9..d2ba268 100644[m
Binary files a/bin/com/mattkula/se350/elevatorsimulator/building/Building.class and b/bin/com/mattkula/se350/elevatorsimulator/building/Building.class differ
[1mdiff --git a/src/com/mattkula/se350/elevatorsimulator/building/Building.java b/src/com/mattkula/se350/elevatorsimulator/building/Building.java[m
[1mindex e391454..9608ae8 100644[m
[1m--- a/src/com/mattkula/se350/elevatorsimulator/building/Building.java[m
[1m+++ b/src/com/mattkula/se350/elevatorsimulator/building/Building.java[m
[36m@@ -2,7 +2,6 @@[m [mpackage com.mattkula.se350.elevatorsimulator.building;[m
 [m
 import java.util.ArrayList;[m
 import java.util.Calendar;[m
[31m-import java.util.Date;[m
 import java.util.Random;[m
 [m
 import com.mattkula.se350.elevatorsimulator.elevator.Elevator;[m
[36m@@ -17,12 +16,9 @@[m [mpublic class Building {[m
 	[m
 	Random r; // TODO remove[m
 	[m
[31m-	private static Date timer;[m
[31m-	[m
 	public Building(int numOfFloors, int numOfElevators) throws InvalidArgumentException{[m
 		FloorManager.initialize(numOfFloors);[m
 		[m
[31m-		timer = new Date();[m
 		elevators = new ArrayList<Elevator>();[m
 		[m
 		for(int i=1; i <= numOfElevators; i++){[m
