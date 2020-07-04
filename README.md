# Together

- Together is a free mobile application based social network for connecting enthusiastic people who are willing to learn new skill or make an achievement to start their journey together.
- Group is the umbrella where group members start learning something and monitor their productivity through group todo list and communicate using group chat.   

## Demo for the Application
<a href="https://drive.google.com/file/d/1RIhbQ_UpqT8Q3XMosOVDEV6HqsroSOqs/view?usp=sharing" target="_blank">**Together App Demo**</a> 


## How To Use
> Mobile App
- Open project file then open **Urls.java** change that line to your local host IP
   ``` java
   public static final String API_URL = "http://192.168.1.7:8000/api/";
   ```
- in **ChatFragment.java** change that line with your local host IP
    ``` java
    private String SERVER_PATH ="ws://192.168.1.7:3000";;
    ```
> Backend server
- Clone the object from <a href="https://github.com/SarahRefaat/Together" target="_blank">**TogetherBackendServer**</a> 
- Edit .env file set DB_NAME, DB_USERNAME, DB_PASSWORD to your mysql database info
- Execute that command 
  ```shell
  $ composer install package
  ```
- Open AppServiceProvider.php class
   ``` php
   use illuminate\Support\Facades\Schema;
   // in boot() put that line below
   Schema::defaultStringLength(191);
   ```
- Finally Excute those lines
  ```shell
  $ Php artisan key:generate  
  $ Php artisan migrate 
  $ Php artisan serve
  ```
> Chat server
- Clone the object from <a href="https://github.com/Hosam11/Chat-Server.git" target="_blank">**Chat-Server.git**</a> 
- Open command line in that directory and execute those commands
 ```shell
  $ npm i express
  $ Npm i mysql 
  $ Npm i websocket
  $ Node server.js
  ```
