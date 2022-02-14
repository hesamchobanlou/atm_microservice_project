# ATM Microservice Project

### MySQL Docker

#### Create a directory to store MySQL data
```
mkdir /home/ubuntu/Development/docker_data/atm_project/mysql_datadir
```

#### Spin-up MySQL container
```
docker run --name atm-service-mysql -v /home/ubuntu/Development/docker_data/atm_project/mysql_datadir:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=password1234 -e MYSQL_ROOT_HOST=% -p 3306:3306 -d mysql:latest
```


