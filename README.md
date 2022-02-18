# ATM Microservice Project

## Database Set-up
### 1. Create a directory to store MySQL docker data
```
mkdir /home/ubuntu/Development/docker_data/atm_project/mysql_datadir
```

### 2. Start MySQL docker container
```
docker run --name atm-service-mysql -v /home/ubuntu/Development/docker_data/atm_project/mysql_datadir:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=password1234 -e MYSQL_ROOT_HOST=% -p 3306:3306 -d mysql:latest
```

### 3. Execute database initialization scripts
    Run **sql/create_user.sql** against MySQL instance

## Starting Microservice Applications
### 1. Application Property Changes

Update **atm_config_server/src/main/resources/config/atm_user_service/atm_user_service.properties**

```
gateway.ip=192.168.1.110
```
This should match the IP address of the machine that the atm_api_gateway_service will run on.

You can repeat this for *[dev|prod].properties as well.

### 2. Start-up application in following sequence
    1. atm_config_server
    2. atm_discovery_service
    3. atm_api_gateway_service
    4. atm_user_service
    5. atm_service

### 3. Multiple instances of the following can be started, if required:
    - atm_api_gateway_service
    - atm_user_service
    - atm_service

## Postman Collection
- Import postman_collection\ATM Service.postman_collection.json as a collection 
- Import postman_collection\ATM Service.postman_environment.json as an environment

## Interacting with APIs using Postman

### Gateway IP
1. Set the **gateway_ip** environment variable in Postman to the IP of the machine running **atm_api_gateway_service**

### Create User & Obtain Token
1. Invoke **User Service/Create User** in Postman with desired field values

2. Obtain the value of the **userId** field from the response and set this as the value for the **userId** environment variable in Postman

3. Obtain the value of the **password** field used in the request and set this as the value for the **userPassword** environment variable in Postman

4. Invoke **User Service/Login** in Postman and copy the value of the response header **token**

5. Set this as the value of the **token** environment variable in Postman

### Balance, Deposit, Withdraw
- Invoke **ATM Service/Bank Account Balance** to view balance
- Invoke **ATM Service/Bank Account Withdraw** to withdraw money
- Invoke **ATM Service/Bank Account Deposit** to deposit money

## Technologies Used
- Spring Boot Web
- Spring Cloud Gateway
- Spring Cloud: Eureka Discovery Service
- JPA
- MySQL

## TODOs:
- Add OAuth2 to support Authorization
- Add Circuit Breaker
- Dockerize all web applications

## Other Notes
Eureka Console: [http://localhost:8010](http://localhost:8010/)