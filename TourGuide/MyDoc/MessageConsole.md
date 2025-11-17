# dev1
1. message démarrage : $ mvn spring-boot:run
2025-11-05T11:41:54.606+01:00  INFO 25048 --- [           main] c.o.tourguide.TourGuideApplication       : Star
ting TourGuideApplication using Java 21.0.6 with PID 25048 (D:\data\IntellijiHome\IdeaProjects\MonTourGuide\target\classes started by Moi in D:\data\IntellijiHome\IdeaProjeting Servlet engine: [Apache Tomcat/10.1.10]
2025-11-05T11:41:56.455+01:00  INFO 25048 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
2025-11-05T11:41:56.457+01:00  INFO 25048 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 1813 ms  
2025-11-05T11:41:56.573+01:00  INFO 25048 --- [           main] c.o.tourguide.service.TourGuideService   : TestMode enabled
2025-11-05T11:41:56.827+01:00  INFO 25048 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 1 endpoint(s) beneath base path '/actuator'
2025-11-05T11:41:56.877+01:00  INFO 25048 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''      
2025-11-05T11:41:56.890+01:00  INFO 25048 --- [           main] c.o.tourguide.TourGuideApplication       : Started TourGuideApplication in 2.533 seconds (process running for 2.77)

2. Après avoir copié le fichier application.properties contenant l'affichage en mode DEBUG
   2025-11-05T12:10:52.049+01:00  INFO 20916 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat initialized with port(s): 8080 (http)
   2025-11-05T12:10:52.060+01:00  INFO 20916 --- [           main] o.apache.catalina.core.StandardService   : Starting service [Tomcat]
   2025-11-05T12:10:52.061+01:00  INFO 20916 --- [           main] o.apache.catalina.core.StandardEngine    : Starting Servlet engine: [Apache Tomcat/10.1.10]
   2025-11-05T12:10:52.145+01:00  INFO 20916 --- [           main] o.a.c.c.C.[Tomcat].[localhost].[/]       : Initializing Spring embedded WebApplicationContext
   2025-11-05T12:10:52.146+01:00  INFO 20916 --- [           main] w.s.c.ServletWebServerApplicationContext : Root WebApplicationContext: initialization completed in 912 ms2025-11-05T12:10:52.285+01:00  INFO 20916 --- [           main] c.o.tourguide.service.TourGuideService   : TestMode enabled
   2025-11-05T12:10:52.285+01:00 DEBUG 20916 --- [           main] c.o.tourguide.service.TourGuideService   : Initializing users
   2025-11-05T12:10:52.294+01:00 DEBUG 20916 --- [           main] c.o.tourguide.service.TourGuideService   : Created 100 internal test users.
   2025-11-05T12:10:52.294+01:00 DEBUG 20916 --- [           main] c.o.tourguide.service.TourGuideService   : Finished initializing users
   2025-11-05T12:10:52.298+01:00 DEBUG 20916 --- [pool-2-thread-1] c.o.tourguide.tracker.Tracker            : Begin Tracker. Tracking 100 users.
   2025-11-05T12:10:52.595+01:00  INFO 20916 --- [           main] o.s.b.a.e.web.EndpointLinksResolver      : Exposing 1 endpoint(s) beneath base path '/actuator'
   2025-11-05T12:10:52.658+01:00  INFO 20916 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''   
   2025-11-05T12:10:52.665+01:00  INFO 20916 --- [           main] c.o.tourguide.TourGuideApplication       : Started TourGuideApplication in 1.735 seconds (process running for 1.973)
   2025-11-05T12:11:00.648+01:00 DEBUG 20916 --- [pool-2-thread-1] c.o.tourguide.tracker.Tracker            : Tracker Time Elapsed: 8 seconds.
   2025-11-05T12:11:00.649+01:00 DEBUG 20916 --- [pool-2-thread-1] c.o.tourguide.tracker.Tracker            : Tracker sleeping
   2025-11-05T12:16:00.665+01:00 DEBUG 20916 --- [pool-2-thread-1] c.o.tourguide.tracker.Tracker            : Begin Tracker. Tracking 100 users.
   2025-11-05T12:16:08.959+01:00 DEBUG 20916 --- [pool-2-thread-1] c.o.tourguide.tracker.Tracker            : Tracker Time Elapsed: 8 seconds.
   2025-11-05T12:16:08.959+01:00 DEBUG 20916 --- [pool-2-thread-1] c.o.tourguide.tracker.Tracker            : Tracker sleeping
