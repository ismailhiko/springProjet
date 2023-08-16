
# Project Title

this project represents a SpringBoot api that exposes three services:
  * one that allows to register a user
  * a second one that displays all the users' details
  * a thrid one that displays the details of a registered user

In this project i used h2 as my embedded database to save data and get data using postman requests, 


## API Reference

#### Get all users

```http
  GET /api/users
```


#### Get user

```http
  GET /api/users/${username}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `string` | **Required**. username of user to fetch |

#### Post user

```http
  POST /api/users/add
```
```
{
    "username": "ismail",
    "birthdate": "03/12/2000",
    "country": "France",
    "phone": "0769906179",
    "gender": "Male"
}
```

## Exceptions
A user can be added if he has all of this criterias:
  * is an adult
  * is a french resident
  * birthdate/country/username are not null
  * birthdate is in this format : dd/MM/yyyy



