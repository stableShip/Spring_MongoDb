
  
# quick start    
1. modify the mongodb uri in application.yml located in src/main/resources    
    
 2. run  `mvn spring-boot:run` to run the server    
    
 3. Request  
- Create User    
 ```  
 POST /api/users HTTP/1.1   
 Host: localhost:8090    
 Content-Type: application/json    
 cache-control: no-cache    
   {    
       "userName": "test1",    
       "password": "123456789"    
   }    
  ```  
- Get Users
```
 GET /api/users HTTP/1.1 
 Host: localhost:8090 
 Content-Type: application/json 
 cache-control: no-cache    
 ```
- Update User    
```
PUT /api/users HTTP/1.1    
 Host: localhost:8090    
 Content-Type: application/json    
 cache-control: no-cache    
 {    
   "id": "5c495fb8b0c19a0de4a21fb2",      
   "userName": "test3"    
 }    
 ```
- Delete User    
```
DELETE /api/users/test1 HTTP/1.1    
 Host: localhost:8090    
 Content-Type: application/json    
 cache-control: no-cache
 ```