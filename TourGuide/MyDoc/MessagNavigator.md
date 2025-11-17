# dev1 :  Endpoints 
1. 
    GET http://localhost:8080/getLocation?userName=internalUser0
    {
    "userId": "322c5e9e-f2a6-403c-a756-59881cb70db9",
    "location": {
    "longitude": -78.191289,
    "latitude": -34.74711
    },
    "timeVisited": "2025-11-05T10:52:50.890+00:00"
    }
11. GET http://localhost:8080/getUserLocations?userName=internalUser0 (endpoints à la fin)
    [
    {
    "userId": "322c5e9e-f2a6-403c-a756-59881cb70db9",
    "location": {
    "longitude": 136.12786515625936,
    "latitude": 5.629925360952058
    },
    "timeVisited": "2025-11-05T11:52:47.390+00:00"
    },
    {
    "userId": "322c5e9e-f2a6-403c-a756-59881cb70db9",
    "location": {
    "longitude": -154.47558884126354,
    "latitude": -53.17634993176239
    },
    "timeVisited": "2025-10-19T11:52:47.390+00:00"
    },
    {
    "userId": "322c5e9e-f2a6-403c-a756-59881cb70db9",
    "location": {
    "longitude": -79.70515246903794,
    "latitude": 24.838032169227162
    },
    "timeVisited": "2025-10-15T11:52:47.390+00:00"
    },
    {
    "userId": "322c5e9e-f2a6-403c-a756-59881cb70db9",
    "location": {
    "longitude": -78.191289,
    "latitude": -34.74711
    },
    "timeVisited": "2025-11-05T10:52:50.890+00:00"
    },
    {
    "userId": "322c5e9e-f2a6-403c-a756-59881cb70db9",
    "location": {
    "longitude": -61.051738,
    "latitude": -77.644614
    },
    "timeVisited": "2025-11-05T10:57:59.637+00:00"
    },
    {
    "userId": "322c5e9e-f2a6-403c-a756-59881cb70db9",
    "location": {
    "longitude": 44.398042,
    "latitude": 68.691497
    },
    "timeVisited": "2025-11-05T11:03:08.232+00:00"
    }
    ]

2. GET  http://localhost:8080/getNearbyAttractions?userName=internalUser0
   []

3. GET http://localhost:8080/getRewards?userName=internalUser0
   []

4. http://localhost:8080/getTripDeals?userName=internalUser0
   [
   {
   "name": "AdventureCo",
   "price": 606.99,
   "tripId": "322c5e9e-f2a6-403c-a756-59881cb70db9"
   },
   {
   "name": "Enterprize Ventures Limited",
   "price": 619.99,
   "tripId": "322c5e9e-f2a6-403c-a756-59881cb70db9"
   },
   {
   "name": "FlyAway Trips",
   "price": 137.99,
   "tripId": "322c5e9e-f2a6-403c-a756-59881cb70db9"
   },
   {
   "name": "Dancing Waves Cruselines and Partners",
   "price": 143.99,
   "tripId": "322c5e9e-f2a6-403c-a756-59881cb70db9"
   },
   {
   "name": "Dream Trips",
   "price": 604.99,
   "tripId": "322c5e9e-f2a6-403c-a756-59881cb70db9"
   }
   ]
