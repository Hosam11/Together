# Together

- Together is a free mobile application based social network for connecting enthusiastic people who are willing to learn new skill or make an achievement to start their journey together.
- Group is the umbrella where group members start learning something and monitor their productivity through group todo list and communicate using group chat.   

## Demo for the Application
<a href="https://drive.google.com/file/d/1RIhbQ_UpqT8Q3XMosOVDEV6HqsroSOqs/view?usp=sharing" target="_blank">**Together App Demo Vedio**</a> 


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
  ## Screenshots
|  **Splach Screen** | Login  | Home  |
| :---: |:---:| :---:|
|  ![splach](https://user-images.githubusercontent.com/18370055/94636450-4d1d7d80-02d5-11eb-9f27-7331dc21e868.jpg) |  ![login](https://user-images.githubusercontent.com/18370055/94636444-4bec5080-02d5-11eb-8854-82dc99c1a516.jpg)  |  ![home](https://user-images.githubusercontent.com/18370055/94636457-4ee74100-02d5-11eb-9e34-8413a71e24b3.jpg) |
| Explore & Search Groups | Join Group| Notification|
| ![explore   search groups](https://user-images.githubusercontent.com/18370055/94636456-4ee74100-02d5-11eb-9628-1cd7fd40496f.jpg) | ![join to group](https://user-images.githubusercontent.com/18370055/94636460-4f7fd780-02d5-11eb-85b3-06d9369df96b.jpg) | ![notification](https://user-images.githubusercontent.com/18370055/94636448-4c84e700-02d5-11eb-9391-7298758fc0fd.jpg)|   
| Inside Group|Add Task| Done List |
| ![inside group](https://user-images.githubusercontent.com/18370055/94636458-4f7fd780-02d5-11eb-99af-6fe137508196.jpg) | ![add task](https://user-images.githubusercontent.com/18370055/94636452-4db61400-02d5-11eb-8858-b5b5c54b42bc.jpg) | ![done list](https://user-images.githubusercontent.com/18370055/94636454-4e4eaa80-02d5-11eb-8c24-47d9543a8403.jpg)|
| About Group Info |Request Join Group| Create Group |
| ![about group info](https://user-images.githubusercontent.com/18370055/94636451-4db61400-02d5-11eb-8a83-d1eba2cfd760.jpg) | ![request join to group](https://user-images.githubusercontent.com/18370055/94636449-4d1d7d80-02d5-11eb-9cc2-3f94c92f96ce.jpg)  | ![create group](https://user-images.githubusercontent.com/18370055/94636453-4e4eaa80-02d5-11eb-80b9-6e948ceb6805.jpg) |

