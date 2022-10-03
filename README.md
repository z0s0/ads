Stack chosen: 
  - Runtime system: Cats-Effect 3
  - Persistence storage: postgresql(for simplicity)
  - DB access: Doobie
  - Http-server: Http4s
  - Http-client for simulation: Sttp
  - Db migrations: flyway
  - Docs generation: Tapir 

swagger docs: http://localhost:8080/docs/

DB Schema 
```  
  users
    id 
    username

  ads
    id 
    body
  
  impression
    id 
    user_id 
    ad_id 
    show_time 

  click 
    id 
    user_id 
    ad_id 
    action_time  
   